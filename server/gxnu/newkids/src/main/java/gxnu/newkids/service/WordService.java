package gxnu.newkids.service;

import gxnu.newkids.dao.WordDao;
import gxnu.newkids.entity.Baseword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 单词服务层
 * */
@Service
public class WordService {

    private static Logger logger= LoggerFactory.getLogger(WordbookService.class);
    @Resource
    private WordDao wordDao;

    //获取一组单词
    public Map getWordGroup(String open_id,String wb_id,int bw_diff,int size){
        Map map = new HashMap();

        map.put("status", 1);

        List<Baseword> basewords = wordDao.getWordGroup(open_id,wb_id,bw_diff,size);

        map.put("res",basewords);

        return map;
    }

    //获取一组单词,包括词霸释义
    public Map getWordcibaGroup(String open_id,String wb_id,int bw_diff,int size){
        Map map = new HashMap();

        map.put("status", 1);

        List wordciba = wordDao.getWordcibaGroup(open_id,wb_id,bw_diff,size);
        map.put("res",wordciba);

        return map;
    }

    //获取一个单词详细释义
    public Map getWordciba(String word){
        Map map = new HashMap();

        map.put("status", 1);

        List res= wordDao.getWordciba(word);
        map.put("res",res);

        return map;
    }




}
