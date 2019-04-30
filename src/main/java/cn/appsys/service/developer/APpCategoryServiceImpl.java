package cn.appsys.service.developer;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class APpCategoryServiceImpl implements AppCategoryService {
    @Resource
    private AppCategoryMapper mapper;

    @Override
    public List<AppCategory> getAppCategoryListByParentId(Integer parentId) throws Exception {
        return mapper.getAppCategoryListByParentId(parentId);
    }
}
