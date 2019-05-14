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

    public Map getAllWb(){

        Map map = new HashMap();

        map.put("status", 1);

        List<Wordbook> wb_list = wordbookDao.selectWb(null);

        map.put("res",wb_list);

        return map;
    }

    public Map getUserStudy(String open_id){

        Map map = new HashMap();
        List<Wordbook> wb_list = wordbookDao.selectWb(null);
        List<UserWb> userWbs = wordbookDao.selectUserWb(open_id,null,3);

        UserWb userWb = null;
        for(int i = 0 ; i < userWbs.size();i++ ){
            if(userWbs.get(i).getU_wb_status() == 0){
                userWb = userWbs.get(i);
                break;
            }
        }

        int wordsNum = 0;

        int wb_size = wb_list.size();
        for(int i = 0 ; i <wb_size;i++ ){
            if(userWb.getWb_id().equals(wb_list.get(i).getWb_id())){
                wordsNum = wb_list.get(i).getWb_num();
                break;
            }
        }

        int hasStudyNum = userWb.getU_wb_rate();

        map.put("userWbs",userWbs);
        map.put("userWb",userWb);
        map.put("wordsNum",wordsNum);
        map.put("hasStudyNum",hasStudyNum);
        map.put("status", 1);

        return map;
    }

    public Map userSetWb(String open_id,String wb_id){
        Map map = new HashMap();

        if(open_id.equals("null")||wb_id.equals("null")){
            map.put("status", 0);
            map.put("msg", "输入值有空");
            return map;
        }

        UserWb userWb = new UserWb();
        userWb.setOpen_id(open_id);
        userWb.setWb_id(wb_id);
        userWb.setU_wb_status(0);
        userWb.setU_wb_rate(0);


        //如果已经有未背诵完的单词书，则设置为-1
        wordbookDao.updateWbStatus(userWb.getOpen_id(),null,-1);

        //查询是否有本单词书，wb_status表示无论什么状态
        List<UserWb> userWbs = wordbookDao.selectUserWb(userWb.getOpen_id(),userWb.getWb_id(),3);
        if( userWbs.size() > 0 ){
            wordbookDao.updateWbStatus(userWb.getOpen_id(),userWb.getWb_id(),0);
        }else{
            wordbookDao.insertWb(userWb);
        }

        map.put("status", 1);
        return map;

    }




}
