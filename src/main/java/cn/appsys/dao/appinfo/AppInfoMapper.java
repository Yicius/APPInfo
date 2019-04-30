package cn.appsys.dao.appinfo;

import cn.appsys.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppInfoMapper {
    public List<AppInfo> getAppInfoList(@Param(value="softwareName")String querySoftwareName,
                                        @Param(value="status")Integer queryStatus,
                                        @Param(value="categoryLevel1")Integer queryCategoryLevel1,
                                        @Param(value="categoryLevel2")Integer queryCategoryLevel2,
                                        @Param(value="categoryLevel3")Integer queryCategoryLevel3,
                                        @Param(value="flatformId")Integer queryFlatformId,
                                        @Param(value="devId")Integer devId,
                                        @Param(value="from")Integer currentPageNo,
                                        @Param(value="pageSize")Integer pageSize)throws Exception;

    public int getAppInfoCount(@Param(value="softwareName")String querySoftwareName,
                               @Param(value="status")Integer queryStatus,
                               @Param(value="categoryLevel1")Integer queryCategoryLevel1,
                               @Param(value="categoryLevel2")Integer queryCategoryLevel2,
                               @Param(value="categoryLevel3")Integer queryCategoryLevel3,
                               @Param(value="flatformId")Integer queryFlatformId,
                               @Param(value="devId")Integer devId)throws Exception;

    public AppInfo getAppInfo(@Param(value="id")Integer id,@Param(value="APKName")String APKName)throws Exception;
    public int add(AppInfo appInfo) throws Exception;
}
