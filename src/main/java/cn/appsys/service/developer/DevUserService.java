package cn.appsys.service.developer;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
    //登陆验证
    public DevUser login(String devCode,String password);
}
