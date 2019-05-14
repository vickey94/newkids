package gxnu.newkids.api;


import gxnu.newkids.dao.WordDao;
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
    private WordDao wordDao;


    @RequestMapping(value = {"/test1","t1"}, method = RequestMethod.GET)
    public String te()
    {

        logger.info("访问Controller");
        logger.error("123");

        return "Hello world!";
    }


}
