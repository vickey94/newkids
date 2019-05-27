package gxnu.newkids.service;

import gxnu.newkids.dao.WordDao;
import gxnu.newkids.entity.Baseword;
import gxnu.newkids.entity.BasewordWlogs;
import gxnu.newkids.entity.WordLogs;
import gxnu.newkids.util.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

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

        /**历史单词**/
        List<BasewordWlogs> hisWords = getHisWordGroup(open_id,wb_id,bw_diff,size/2);

        size = size - hisWords.size();

        /**新单词**/
        List<Baseword> basewords = wordDao.getWordGroup(open_id,wb_id,bw_diff,500);

        List<Baseword> bws = new ArrayList<>();

        int length = basewords.size();

        if(length >= size)
        for(int i = 0 ; i < size ; i++){
            int index = (int)(Math.random()*length);
            bws.add(basewords.get(index));
        }
        else{
            for(int i = 0 ; i < length ; i++){
                bws.add(basewords.get(i));
            }
        }
        map.put("newWords",bws);
        map.put("hisWords",hisWords);

        return map;
    }

    //获取一组历史单词
    public List<BasewordWlogs> getHisWordGroup(String open_id, String wb_id, int bw_diff, int size){

        List hisWordsMap = wordDao.getHisWordGroup(open_id,wb_id,bw_diff,7,size);

        List<BasewordWlogs> hisWords = new ArrayList<BasewordWlogs>();

        Iterator it = hisWordsMap.iterator();
        while (it.hasNext()){

            Map w = (Map) it.next();
            WordLogs wl = new WordLogs();
            wl.setW_logs_id((Integer)w.get("w_logs_id"));
            wl.setWb_id(w.get("wb_id")+"");
            wl.setOpen_id(w.get("open_id")+"");
            wl.setBw_id(w.get("bw_id")+"");
            wl.setW_score((Integer)w.get("w_score"));
            wl.setW_spend_time((Integer)w.get("w_spend_time"));
            wl.setW_last_time((Timestamp)w.get("w_last_time"));

            BasewordWlogs bwl = new BasewordWlogs();

            bwl.setBw_id(w.get("bw_id")+"");
            bwl.setWord(w.get("word")+"");
            bwl.setBw_freq((Integer)w.get("bw_freq"));
            bwl.setBw_diff((Integer)w.get("bw_diff"));
            bwl.setBw_count((Integer)w.get("bw_count"));
            bwl.setBw_ack_rate((Integer)w.get("bw_ack_rate"));
            bwl.setWordLogs(wl);

            hisWords.add(bwl);
        }

        return hisWords;
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

        }else if(word_type.equals("2")){
            logger.info("更新用户历史词汇");
            wordDao.updateWordLogs(wordLogs);

            //更新用户本单词书已背单词个数，这里直接按照List.size更新，减少更新次数
            int size = 0;
            for(int i = 0 ; i < wordLogs.size();i++){
                if(wordLogs.get(i).getW_score() >= 7){
                    size++;
                }
            }
            if(size > 0)
            wordDao.updateUserWbRate(wordLogs.size(),open_id,wb_id);
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

        List<Map> words = wordDao.searchWords(word);

        List<String> wordMeans = new ArrayList<>();

        for(int i = 0 ; i <words.size();i++){
            if( i > 2 ) break;;
            String w = words.get(i).get("word")+"";
            String m = (String) getCibaWord(w,"json").get("res");
            wordMeans.add(m);
        }

        map.put("status", 1);
        map.put("res",words);
        map.put("wordMs",wordMeans);

        return map;
    }



}
