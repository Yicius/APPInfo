package cn.appsys.controller.develop;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.service.developer.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/dev/flatform/app")
public class AppInfoController {
    @Resource
    private AppInfoService service;
    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private AppCategoryService appCategoryService;

    private Logger logger=Logger.getLogger(AppInfoController.class);

    @RequestMapping(value = "/list")
    public String getAppInfoList(Model model, HttpSession session,
                                 @RequestParam(value = "querySoftwareName",required = false)String querySoftwareName,
                                 @RequestParam(value = "queryStatus",required = false)String _queryStatus,
                                 @RequestParam(value = "queryCategoryLevel1",required = false)String _queryCategoryLevel1,
                                 @RequestParam(value = "queryCategoryLevel2",required = false)String _queryCategoryLevel2,
                                 @RequestParam(value = "queryCategoryLevel3",required = false)String _queryCategoryLevel3,
                                 @RequestParam(value = "queryFlatformId",required = false)String _queryFlatformId,
                                 @RequestParam(value = "pageIndex",required = false)String pageIndex
                                 ){
        logger.info("getAppInfoList -- > querySoftwareName: " + querySoftwareName);
        logger.info("getAppInfoList -- > queryStatus: " + _queryStatus);
        logger.info("getAppInfoList -- > queryCategoryLevel1: " + _queryCategoryLevel1);
        logger.info("getAppInfoList -- > queryCategoryLevel2: " + _queryCategoryLevel2);
        logger.info("getAppInfoList -- > queryCategoryLevel3: " + _queryCategoryLevel3);
        logger.info("getAppInfoList -- > queryFlatformId: " + _queryFlatformId);
        logger.info("getAppInfoList -- > pageIndex: " + pageIndex);

        Integer devId = ((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId();
        List<AppInfo> appInfoList =null;
        List<DataDictionary> statusList=null;
        List<DataDictionary> flatFormList=null;
        //列出一级分类列表
        List<AppCategory> categoryLevel1List=null;
        List<AppCategory> categoryLevel2List=null;
        List<AppCategory> categoryLevel3List=null;
        //页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        Integer currentPageNo=1;

        if(pageIndex !=null){
            try {
                currentPageNo = Integer.valueOf(pageIndex);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Integer queryStatus = null;
        if(_queryStatus!=null&&!("").equals(_queryStatus)){
            queryStatus=Integer.parseInt(_queryStatus);
        }
        Integer queryCategoryLevel1 = null;
        if(_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")){
            queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
        }
        Integer queryCategoryLevel2 = null;
        if(_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")){
            queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
        }
        Integer queryCategoryLevel3 = null;
        if(_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")){
            queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
        }
        Integer queryFlatformId = null;
        if(_queryFlatformId != null && !_queryFlatformId.equals("")){
            queryFlatformId = Integer.parseInt(_queryFlatformId);
        }
        //总数量表
        int totalCount=0;
        try {
            totalCount=service.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //总页数
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();
        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }
        try {
            appInfoList = service.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, currentPageNo, pageSize);
            statusList = this.getDataDictionaryList("APP_STATUS");
            flatFormList = this.getDataDictionaryList("APP_FLATFORM");
            categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("appInfoList", appInfoList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("flatFormList", flatFormList);
        model.addAttribute("categoryLevel1List", categoryLevel1List);
        model.addAttribute("pages", pages);
        model.addAttribute("queryStatus", queryStatus);
        model.addAttribute("querySoftwareName", querySoftwareName);
        model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
        model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
        model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
        model.addAttribute("queryFlatformId", queryFlatformId);

        return "developer/appinfolist";
    }

    public List<DataDictionary> getDataDictionaryList(String status){
        List<DataDictionary> dataDictionaryList = null;
        try {
            dataDictionaryList = dataDictionaryService.getDataDictionaryList(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataDictionaryList;
    }

    //根据paramId查询出相应的
    @RequestMapping(value = "/categoryLevelList", method = RequestMethod.GET)
    @ResponseBody
    public void getAppCategoryList(@RequestParam String pid, HttpServletResponse response) throws IOException {
        logger.debug("getAppCategoryList pid==================");
        if(("").equals(pid)){
            pid=null;
        }
        String s = JSON.toJSONString(getCategoryList(pid));
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(s);
    }

    public List<AppCategory> getCategoryList(String pid){
        List<AppCategory> appCategoryList =null;
        try {
            appCategoryList= appCategoryService.getAppCategoryListByParentId(pid == null ? null : Integer.parseInt(pid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appCategoryList;
    }

    @RequestMapping(value = "/appinfoadd",method = RequestMethod.GET)
    public String appinfoadd(){
        return "developer/appinfoadd";
    }

    @RequestMapping(value = "apkexist.json",method = RequestMethod.GET)
    @ResponseBody
    public void apkNameIsExist(@RequestParam String APKName,HttpServletResponse response) throws IOException {
        HashMap<String,String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(APKName)){
            resultMap.put("APKName","empty");
        }else {
            AppInfo appInfo = null;
            try {
                appInfo =  service.getAppInfo(null,APKName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(null!=appInfo){
                resultMap.put("APKName","exist");
            }else {
                resultMap.put("APKName","noexist");
            }
        }
       String s= JSONArray.toJSONString(resultMap);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(s);
    }

    @RequestMapping(value = "datadictionarylist.json",method = RequestMethod.GET)
    @ResponseBody
    public void getDataDictionary(@RequestParam String tcode,HttpServletResponse response) throws IOException {
       String s= JSONArray.toJSONString(this.getDataDictionaryList(tcode));
        PrintWriter writer = response.getWriter();
        writer.print(s);

    }

    @RequestMapping(value="/appinfoaddsave",method=RequestMethod.POST)
    public String addSave(AppInfo appInfo,HttpSession session,HttpServletRequest request,
                          @RequestParam(value="a_logoPicPath",required= false) MultipartFile attach){

        String logoPicPath =  null;
        String logoLocPath =  null;
        if(!attach.isEmpty()){
            String path = request.getServletContext().getRealPath("/statics/uploadfiles");
            logger.info("uploadFile path: " + path);
            String oldFileName = attach.getOriginalFilename();//原文件名

            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            int filesize = 500000;
            if(attach.getSize() > filesize){//上传大小不得超过 50k
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
                return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                    ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
                String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
                File targetFile = new File(path,fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
                    return "developer/appinfoadd";
                }
                logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                logoLocPath = path+ File.separator+fileName;
            }else{
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
                return "developer/appinfoadd";
            }
        }
        appInfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setCreationDate(new Date());
        appInfo.setLogoPicPath(logoPicPath);
        appInfo.setLogoLocPath(logoLocPath);
        appInfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setStatus(1);
        try {
            if(service.add(appInfo)){
                return "redirect:/dev/flatform/app/list";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "developer/appinfoadd";
    }

    @RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
    @ResponseBody
    public List<AppCategory> getAppCategoryList (@RequestParam String pid){
        logger.debug("getAppCategoryList pid ============ " + pid);
        if("".equals(pid)){pid = null;}
        return getCategoryList(pid);
    }
}
