package gxnu.newkids.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("t")
public class testController {

    protected static Logger logger= LoggerFactory.getLogger(testController.class);

    @RequestMapping(value = {"/test1","t1"}, method = RequestMethod.GET)
    public String te()
    {

        logger.info("访问test Controller");


        return "Hello world!";
    }
}
