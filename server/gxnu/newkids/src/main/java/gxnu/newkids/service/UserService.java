package gxnu.newkids.service;

import gxnu.newkids.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    protected static Logger logger= LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserDao userDao;

    public Map UserCheckIn(String open_id){
        Map map = new HashMap();

        userDao.userCheck(open_id);

        map.put("status", 1);
        map.put("msg", "打卡成功");
        return map;
    }

    public Map getUserCheckTimes(String open_id){
        Map map = new HashMap();

        int times = userDao.getUserCheckTimes(open_id);

        map.put("status", 1);
        map.put("times", times);

        return map;
    }
}
