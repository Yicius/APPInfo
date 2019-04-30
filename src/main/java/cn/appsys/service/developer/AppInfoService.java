package cn.appsys.service.developer;

import cn.appsys.pojo.AppInfo;

import java.util.List;

public interface AppInfoService {
    /**
     * 根据条件查询出app列表
     *
     * @param querySoftwareName
     * @param queryStatus
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @param queryFlatformId
     * @param devId
     * @param currentPageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryStatus,
                                        Integer queryCategoryLevel1, Integer queryCategoryLevel2,
                                        Integer queryCategoryLevel3, Integer queryFlatformId,
                                        Integer devId, Integer currentPageNo, Integer pageSize) throws Exception;

    /**
     * 根据条件查询appInfo表记录数
     *
     * @param querySoftwareName
     * @param queryStatus
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @param queryFlatformId
     * @param devId
     * @return
     * @throws Exception
     */
    public int getAppInfoCount(String querySoftwareName, Integer queryStatus,
                               Integer queryCategoryLevel1, Integer queryCategoryLevel2,
                               Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId) throws Exception;
    /**
     * 根据id、apkName查找appInfo
     * @param id
     * @return
     * @throws Exception
     */
    public AppInfo getAppInfo(Integer id,String APKName)throws Exception;

    /**
     * 新增app
     * @param appInfo
     * @return
     * @throws Exception
     */
    public boolean add(AppInfo appInfo) throws Exception;
}
