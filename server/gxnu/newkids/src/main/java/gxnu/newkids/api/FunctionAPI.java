package gxnu.newkids.api;


import gxnu.newkids.service.PaperService;
import gxnu.newkids.service.WordService;
import gxnu.newkids.service.WordbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("func")
public class FunctionAPI {
    protected static Logger logger= LoggerFactory.getLogger(FunctionAPI.class);

    @Resource
    private WordService wservice ;
    @Resource
    private PaperService pservice;
    @Resource
    private WordbookService wbservice;

    //获取所有单词书
    @RequestMapping(value = {"/getallwb"}, method = RequestMethod.GET)
    public Map getAllWordbook(){

        Map map = wbservice.getAllWb(null);

        return map;
    }


    //获取用户单词书状态
    @RequestMapping(value = {"/getuserwb"}, method = RequestMethod.GET)
    public Map getWbByOpenId(@RequestParam String open_id){

        Map map = wbservice.getWbByOpen_id(open_id);

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
    public Map getWordGroup(@RequestParam String open_id,String wb_id){
        logger.info("用户获取单词组,open_id is {}",open_id);

        Map map =  wservice.getWordGroup(open_id,wb_id,3,10);

        return map;
    }

    //用户获取一个单词详情
    @RequestMapping(value = {"/getwordciba"}, method = RequestMethod.GET)
    public Map getWordciba(@RequestParam String word){

        Map map =  wservice.getWordciba(word);

        return map;
    }


    //用户获取一组文章
    @RequestMapping(value = {"/getpapergroup"}, method = RequestMethod.GET)
    public Map getPaperGroup(@RequestParam String open_id){
        logger.info("用户获取文章组,open_id is {}",open_id);
        Map map = new HashMap();

        return map;
    }

    //用户一组单词背诵结束
    @RequestMapping(value = {"/finishwords"}, method = RequestMethod.GET)
    public Map finishWords(@RequestParam String open_id,@RequestParam String words){
        logger.info("用户背诵完一组单词,open_id is {}",open_id);
        Map map = new HashMap();

        return map;
    }

    //用户文章阅读完毕
    @RequestMapping(value = {"/finishpaper"}, method = RequestMethod.GET)
    public Map finishPaper(@RequestParam String open_id,@RequestParam String p_id,@RequestParam String r_start_time,@RequestParam String r_end_time){
        logger.info("用户阅读文章结束,open_id is {} , p_id is {}",open_id,p_id);
        Map map = new HashMap();

        return map;
    }


}
