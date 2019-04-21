package gxnu.newkids.api;


import gxnu.newkids.dao.WordsDao;
import gxnu.newkids.entity.Wordciba;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("user")
public class test {

    protected static Logger logger= LoggerFactory.getLogger(test.class);
    @Resource
    private WordsDao wordsDao;


    @RequestMapping(value = {"/test1","t1"}, method = RequestMethod.GET)
    public String te()
    {

        logger.info("访问Controller");
        logger.error("123");

        return "Hello world!";
    }

    @RequestMapping(value = "/ciba", method = RequestMethod.GET)
    public Wordciba wordsciba()
    {

        logger.info("ciba");

        Wordciba wciba = wordsDao.getWordcibaByWord("take");

        System.out.println(wciba.toString());

        return  wciba;
    }

    @RequestMapping(value = "/w", method = RequestMethod.POST)
    public Wordciba getword(@RequestBody Wordciba wordciba)
    {

        Map<String, Object> param = new HashMap<>();
        String s = wordciba.getExchange().toString();
        logger.info("s");
        param.put("wcb", wordciba);
        Wordciba wciba = wordsDao.getWordcibaByWord("take");

        System.out.println(wciba.toString());

        return  wciba;
    }
}
