package gxnu.newkids.api;

import gxnu.newkids.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginAPI {

    protected static Logger logger= LoggerFactory.getLogger(LoginAPI.class);

    @Resource
    private LoginService loginService ;

    //登录验证
    @RequestMapping(value = {"/valid"}, method = RequestMethod.GET)
    public Map loginValid(@RequestParam String code, @RequestParam String rawData, @RequestParam String signature , @RequestParam String encryptedData, @RequestParam String iv)
    {
        logger.info("用户登录,{}",code);

        Map map = loginService.login(code,rawData, signature , encryptedData, iv);

        return map;
    }

    //用户访问小程序
    @RequestMapping(value = {"/visit"}, method = RequestMethod.GET)
    public Map loginVisit(@RequestParam String open_id)
    {

        logger.info("用户访问,{}",open_id);

        Map map = loginService.visit(open_id);

        return map;
    }
}
