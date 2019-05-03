package cn.appsys.service.developer;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Resource
    private AppVersionMapper mapper;
    @Resource
    private AppInfoMapper appInfoMapper;

    @Override
    public List<AppVersion> getAppVersionList(Integer appId) throws Exception {
        return mapper.getAppVersionList(appId);
    }

    @Override
    public boolean appsysadd(AppVersion appVersion) throws Exception {
        boolean flag = false;
        Integer versionId=null;
        if(mapper.add(appVersion)>0){
            versionId =appVersion.getId();
            flag=true;
        }
        if(appInfoMapper.updateVersionId(versionId, appVersion.getAppId()) > 0 && flag){
            flag = true;
        }
        return flag;
    }

    @Override
    public AppVersion getAppVersionById(Integer id) throws Exception {
        return mapper.getAppVersionById(id);
    }

    @Override
    public boolean modify(AppVersion appVersion) throws Exception {
        boolean flag = false;
        if(mapper.modify(appVersion) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteApkFile(Integer id) throws Exception {
        boolean flag = false;
        if(mapper.deleteApkFile(id) > 0){
            flag = true;
        }
        return flag;
    }
}
