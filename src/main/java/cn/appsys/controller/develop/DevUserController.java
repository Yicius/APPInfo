package cn.appsys.controller.develop;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/dev")
public class DevUserController {
    private Logger logger = Logger.getLogger(DevUserController.class);
    @Autowired
    private DevUserService service;

    @RequestMapping(value = "/login")
    public String login(){
        logger.info("======================+Login ");
        return "devlogin";
    }

    @RequestMapping(value = "/dologin")
    public String dologin(@RequestParam String devCode,
                          @RequestParam String devPassword,
                          HttpSession session, HttpServletRequest request){
        logger.info("======================+doLogin ");
        DevUser user=null;
        user = service.login(devCode,devPassword);
        if(user!=null){
            session.setAttribute(Constants.DEV_USER_SESSION,user);
            return "redirect:/dev/flatform/main";
        }else {
            request.setAttribute("error","用户民或密码不正确");
            return "devlogin";
        }
    }

    @RequestMapping(value = "/flatform/main")
    public String main(HttpSession httpSession){
        if(httpSession.getAttribute(Constants.DEV_USER_SESSION)==null){
            return "redirect:/dev/login";
        }
        return "developer/main";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute(Constants.DEV_USER_SESSION);
        return "devlogin";
    }


}
