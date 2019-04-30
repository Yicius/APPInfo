package cn.appsys.dao.devuser;

import cn.appsys.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

public interface DevUserMapper {
    //根据devCode获取用户记录
    public DevUser getLoginUser(@Param("devCode")String devCode);
}
