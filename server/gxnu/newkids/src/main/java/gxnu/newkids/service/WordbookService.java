package gxnu.newkids.service;

import gxnu.newkids.dao.WordbookDao;
import gxnu.newkids.entity.UserWb;
import gxnu.newkids.entity.Wordbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordbookService {

    private static Logger logger= LoggerFactory.getLogger(WordbookService.class);
    @Resource
    private WordbookDao wordbookDao;

    public Map getAllWb(String wb_id){

        Map map = new HashMap();

        map.put("status", 1);

        List<Wordbook> wb_list = wordbookDao.selectWb(wb_id);

        map.put("wbs",wb_list);

        return map;
    }

    public Map getWbByOpen_id(String open_id){

        Map map = new HashMap();

        List<UserWb> userWbs = wordbookDao.selectUserWb(open_id,null,3);

        map.put("userWbs",userWbs);

        map.put("status", 1);

        return map;
    }

    public Map userSetWb(String open_id,String wb_id){
        Map map = new HashMap();

        UserWb userWb = new UserWb();
        userWb.setOpen_id(open_id);
        userWb.setWb_id(wb_id);
        userWb.setU_wb_status(0);

        map.put("status", 1);

        //如果已经有未背诵完的单词书，则设置为-1
        wordbookDao.updateWbStatus(userWb.getOpen_id(),null,-1);

        //查询是否有本单词书，wb_status表示无论什么状态
        List<UserWb> userWbs = wordbookDao.selectUserWb(userWb.getOpen_id(),userWb.getWb_id(),3);
        if( userWbs.size() > 0 ){
            wordbookDao.updateWbStatus(userWb.getOpen_id(),userWb.getWb_id(),0);
        }else{
            wordbookDao.insertWb(userWb);
        }
        return map;

    }




}
