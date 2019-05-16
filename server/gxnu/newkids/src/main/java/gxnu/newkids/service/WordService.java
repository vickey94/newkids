package gxnu.newkids.service;

import gxnu.newkids.dao.WordDao;
import gxnu.newkids.entity.Baseword;
import gxnu.newkids.entity.WordLogs;
import gxnu.newkids.util.HttpRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringEscapeUtils.*;

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


    public Map getCibaWord(String word,String type)  {
        Map map = new HashMap();

        String params = "key=9D579F3386B678CF309BC6AD2B03A376&type="+type+"&w="+word.toLowerCase() ;
        //发送请求
        String sr ;

            sr = HttpRequest.sendGetUTF8("http://dict-co.iciba.com/api/dictionary.php", params);
        if(type.toLowerCase().equals("json")){
            sr = unescapeJava(sr);
        }

      //  System.out.println(sr);

        map.put("status", 1);
        map.put("res",sr);

        return map;
    }

    public Map searchWord(String word){
        Map map = new HashMap();

        List<String> words = wordDao.searchWords(word);

        System.out.println(words.size());

        map.put("status", 1);
        map.put("res",words);

        return map;
    }



}
