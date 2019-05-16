package gxnu.newkids.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gxnu.newkids.entity.*;
import gxnu.newkids.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("func")
public class FunctionAPI {
    protected static Logger logger= LoggerFactory.getLogger(FunctionAPI.class);

    @Resource
    private UserService uservice;
    @Resource
    private WordService wservice ;
    @Resource
    private PaperService pservice;
    @Resource
    private WordbookService wbservice;


    //初始化
    @RequestMapping(value = {"/init"}, method = RequestMethod.GET)
    public Map indexInit(@RequestParam String open_id){
        Map map = new HashMap();
        Map map1 = wbservice.getAllWb();

        map.put("status", 1);

       // List<Wordbook> wb_list = (List<Wordbook>) map1.get("res");

    /*    Map map3 = uservice.getUserCheckTimes(open_id);
        int hasStudyDay = (int)map3.get("times");

     //   map.put("wbs",wb_list);
        map.put("hasStudyDay",hasStudyDay);
        map.put("cibakey","9D579F3386B678CF309BC6AD2B03A376");
*/
        return map;
    }

    //获取所有单词书
    @RequestMapping(value = {"/getallwb"}, method = RequestMethod.GET)
    public Map getAllWordbook(){

        Map map = wbservice.getAllWb();

        return map;
    }


    //获取用户学习状态，包括单词书，已背单词
    @RequestMapping(value = {"/getuserstudy"}, method = RequestMethod.GET)
    public Map getUserStudy(@RequestParam String open_id){

        Map map =  wbservice.getUserStudy(open_id);

        Map map2 = uservice.getUserCheckTimes(open_id);
        int hasStudyDay = (int)map2.get("times");

        map.put("hasStudyDay",hasStudyDay);
        map.put("cibakey","9D579F3386B678CF309BC6AD2B03A376");

        return map;
    }

    //用户设置单词书
    @RequestMapping(value = {"/usersetwb"}, method = RequestMethod.GET)
    public Map updateWordbook(@RequestParam String open_id,@RequestParam String wb_id){
        logger.info("用户设置单词书,open_id is {} , wb_id is {}",open_id,wb_id);

        Map map = wbservice.userSetWb(open_id,wb_id);


        return map;
    }

    //用户获取一组单词
    @RequestMapping(value = {"/getwordgroup"}, method = RequestMethod.GET)
    public Map getWordGroup(@RequestParam String open_id,String wb_id,int size){
        logger.info("用户获取单词组,open_id is {}",open_id);

        Map map =  wservice.getWordGroup(open_id,wb_id,3,size);

        return map;
    }

    //用户获取一组历史单词
    @RequestMapping(value = {"/gethiswordgroup"}, method = RequestMethod.GET)
    public Map getHisWordGroup(@RequestParam String open_id,String wb_id){
        logger.info("用户获取单词组,open_id is {}",open_id);

        Map map =  wservice.getHisWordGroup(open_id,-1,-1,10);

        return map;
    }

    //用户获取一个单词详情
    @RequestMapping(value = {"/getwordciba"}, method = RequestMethod.GET)
    public Map getWordciba(@RequestParam String word){

        Map map =  wservice.getWordciba(word);

        return map;
    }



    //用户一个单词背诵结束
    @RequestMapping(value = {"/finishoneword"}, method = RequestMethod.GET)
    public Map finishOneWord(@RequestParam String open_id,@RequestParam String wb_id,@RequestParam String word_type,@RequestParam String wordLogs){
        logger.info("用户背诵完一个单词,open_id is {}",open_id);

        JSONObject json = JSONObject.parseObject(wordLogs);

        WordLogs w = JSON.parseObject(wordLogs,WordLogs.class);
        System.out.println(w.toString());

        List<WordLogs> wordLogs_list = new ArrayList<WordLogs>();
        wordLogs_list.add(w);

        Map map =  wservice.finishWords(open_id,wb_id,word_type,wordLogs_list);

        return map;
    }

    @RequestMapping(value = {"/finishwords"}, method = RequestMethod.POST)
    public Map finishWords(@RequestBody JSONObject req){

        logger.info("用户背诵完一组单词,req is {}",req);

        String open_id = req.get("open_id").toString();
        String word_type = req.get("word_type").toString();
        String words = req.get("words").toString();
        String wb_id = req.get("wb_id").toString();

        List<WordLogs> wordLogs = new ArrayList<WordLogs>(JSON.parseArray(words,WordLogs.class));

        Map map =  wservice.finishWords(open_id,wb_id,word_type,wordLogs);

        return map;
    }

    //用户获取一组文章
    @RequestMapping(value = {"/getpapergroup"}, method = RequestMethod.GET)
    public Map getPaperGroup(@RequestParam String open_id,@RequestParam String[] words){
        logger.info("用户获取文章组,open_id is {}",open_id);

        return pservice.getPapersByWords(open_id,words);
    }

    //用户获取一篇文章
    @RequestMapping(value = {"/getonepaper"}, method = RequestMethod.GET)
    public Map getOnePaper(@RequestParam String p_id){
        logger.info("用户获取一篇文章,p_id is {}",p_id);

        return pservice.getOnePaper(p_id);


    }
    @RequestMapping(value = {"/getreadinglogs"}, method = RequestMethod.GET)
    public Map getReadingLogs(@RequestParam String open_id,@RequestParam String p_id){
        logger.info("用户获取阅读记录,p_id is {}",p_id);

        return pservice.getReadingLogs(open_id,p_id);
    }

    //用户文章阅读完毕
    @RequestMapping(value = {"/finishpaper"}, method = RequestMethod.GET)
    public Map finishPaper(@RequestParam String rlJson, @RequestParam String p_id, @RequestParam float p_score){
        logger.info("用户阅读文章结束, p_id is {}",p_id);

        ReadingLogs readingLogs = JSON.parseObject(rlJson,ReadingLogs.class);

        Map map = pservice.finishPaper(readingLogs,p_id,p_score);

        return map;
    }

    @RequestMapping(value = {"/checkin"}, method = RequestMethod.GET)
    public Map checkIn(@RequestParam String openId){
        logger.info("用户自动打卡, open_id is {}",openId);

        return uservice.UserCheckIn(openId);
    }

    @RequestMapping(value = {"/getcibaword"}, method = RequestMethod.GET)
    public Map getCibaWord(@RequestParam String w,@RequestParam String type){
        logger.info("获取词霸单词, word is {}",w);

        return wservice.getCibaWord(w,type);
    }

    @RequestMapping(value = {"/searchwords"}, method = RequestMethod.GET)
    public Map searchWords(@RequestParam String w){

        return wservice.searchWord(w);
    }



}
