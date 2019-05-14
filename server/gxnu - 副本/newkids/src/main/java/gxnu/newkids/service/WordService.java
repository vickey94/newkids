package gxnu.newkids.service;

import gxnu.newkids.dao.WordDao;
import gxnu.newkids.entity.Baseword;
import gxnu.newkids.entity.WordLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

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

        List<Baseword> basewords = wordDao.getWordGroup(open_id,wb_id,bw_diff,500);

        List<Baseword> res = new ArrayList<>();


        int length = basewords.size();

        if(length == 500) //当剩余不到500个时，取消随机
        for(int i = 0 ; i < size ; i++){
            int index = (int)(Math.random()*length);
            res.add(basewords.get(index));
        }else{
           res = wordDao.getWordGroup(open_id,wb_id,bw_diff,size);
        }

        map.put("res",res);

        return map;
    }

    //获取一组历史单词
    public Map getHisWordGroup(String open_id,int bw_diff,int w_sorce,int size){
        Map map = new HashMap();

        map.put("status", 1);

        List wordciba = wordDao.getHisWordGroup(open_id,bw_diff,w_sorce,size);
        map.put("res",wordciba);

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


    /**
     * 背完一组单词
     * 1.插入历史记录
     * 2.更新单词背诵历史
     * **/
    public Map finishWords(String open_id,String wb_id,String word_type,List<WordLogs> wordLogs){
        Map map = new HashMap();

        map.put("status", 1);

        if(word_type.equals("1")){
            logger.info("新增用户历史词汇");
            wordDao.insertWordLogs(wordLogs);
            wordDao.updateBaseWordsCount(wordLogs);
            //更新用户本单词书已背单词个数，这里直接按照List.size更新，减少更新次数
            wordDao.updateUserWbRate(wordLogs.size(),open_id,wb_id);
        }else if(word_type.equals("2")){
            logger.info("更新用户历史词汇");
            wordDao.updateWordLogs(wordLogs);
        }

        return map;
    }




}
