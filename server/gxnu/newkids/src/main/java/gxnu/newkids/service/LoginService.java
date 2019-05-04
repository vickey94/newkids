package gxnu.newkids.service;


import com.alibaba.fastjson.JSON;
import gxnu.newkids.WXMP;
import gxnu.newkids.api.test;
import gxnu.newkids.dao.UserDao;
import gxnu.newkids.dao.WordDao;

import gxnu.newkids.entity.UserSession;
import gxnu.newkids.util.AesCbcUtil;
import gxnu.newkids.util.HttpRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录处理
 **/
@Service
public class LoginService {

    protected static Logger logger= LoggerFactory.getLogger(LoginService.class);

    @Resource
    private UserDao userDao;

    @Autowired
    WXMP wxmp;

    /**
     * 根据前台传来的code和后台的appid appsecret 去获取openid
     * 访问微信后台拿取openid session_key等
     * 查询数据库中是否有次openid,有则更新，无则插入
     */
    public Map login(String code, String rawData, String signature , String encryptedData, String iv) {

        Map map = new HashMap();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            logger.info("用户登录code为空");
            return map;
        }

        //请求参数https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        String params = "appid=" + wxmp.getAppid() + "&secret=" + wxmp.getAppsecret() + "&js_code=" + code + "&grant_type=authorization_code";
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);

        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);

        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String open_id = (String) json.get("openid");

        /** 2、对encryptedData加密数据进行AES解密**/
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");

            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");

                JSONObject userInfoJSON = JSONObject.fromObject(result);

                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);

                UserSession userSession = new UserSession();
                userSession.setOpen_id((String) userInfoJSON.get("openId"));
                userSession.setUuid((String)userInfoJSON.get("unionId"));
                userSession.setSkey(null);
                userSession.setCreate_time(new Timestamp(System.currentTimeMillis()));
                userSession.setLast_visit_time(new Timestamp(System.currentTimeMillis()));
                userSession.setSession_key(session_key);
                userSession.setUser_info(userInfo.toString());
                userDao.insertUserSession(userSession);

                logger.info("用户登录成功 {}",userInfo.toString());

                return map;
            }
        } catch (Exception e) {
            logger.error("用户登录失败，解密失败！");
            e.printStackTrace();
        }

        map.put("status", 0);
        map.put("msg", "解密失败");

        return map;
    }

    public Map visit(String open_id){
        Map map = new HashMap();
        UserSession userSession = new UserSession();
        userSession.setOpen_id(open_id);
        userSession.setLast_visit_time(new Timestamp(System.currentTimeMillis()));

        userDao.userVisit(userSession);

        map.put("status", 0);
        map.put("msg", "访问失败");
        return map;
    }

}
