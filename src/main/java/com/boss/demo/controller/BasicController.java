package com.boss.demo.controller;

import com.boss.demo.commons.businessEnum.ErrorStatus;
import com.boss.demo.commons.data.PageResult;
import com.boss.demo.commons.data.ParamsConversion;
import com.boss.demo.commons.data.ResponseResult;
import com.boss.demo.commons.util.ListUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.buf.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：通用接口
 */
@RestController
@Api(tags = "通用接口")
@RequestMapping(value = "api/v1/basic")
public class BasicController {
    //在多数据源的情况需要指定要注入的bean名称，否则会冲突
    @Qualifier("masterSqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @GetMapping("queryData")
    @ApiOperation(value = "通用查询接口", notes = "支持过滤、排序等功能", position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapperId", value = "SQL配置的完整限定名称（namespace.id)", example = "com.xxx.xxx.selectList", required = true, dataType = "string"),
            @ApiImplicitParam(name = "orderBy", value = "排序，如[name asc,age desc]", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isList", value = "是否列表，true：列表，false：实体", defaultValue = "true", dataType = "boolean")
    })
    public ResponseResult queryData(@RequestParam String mapperId, @RequestParam(required = false, defaultValue = "true") boolean isList, HttpServletRequest request){
        Map<String, Object> params = ParamsConversion.httpToMap(request);
        Object orderBy = params.get("orderBy");
        List<Object> data = sqlSessionTemplate.selectList(mapperId,orderBy);
        if(isList){
            if(orderBy != null){
                List<String> filedNames = new ArrayList<>();
                List<String> orderMode = new ArrayList<>();
                for (String e : Arrays.asList((String[])orderBy)) {
                    filedNames.add(e.split(" ")[0]);
                    orderMode.add(e.split(" ")[1]);
                }
                ListUtil.sort(data, filedNames, orderMode);
            }
            return ResponseResult.success(data);
        }
        if(data.size() > 0) return ResponseResult.success(data.get(0));
        return ResponseResult.success(null,"数据为空");
    }

    //排序功能暂时有点问题：部分版本tomcat会拦截排序的参数，解决办法目前有：在前端做处理，将参数orderBy放在body请求体中
    @GetMapping("pagingData")
    @ApiOperation(value = "通用分页查询接口", notes = "同时支持过滤、分页、排序等功能", position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mapperId", value = "SQL配置的完整限定名称（namespace.id)", example = "com.xxx.xxx.selectList", required = true, dataType = "string"),
            @ApiImplicitParam(name = "pageSize", value = "页容量",defaultValue = "10",dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "页码",defaultValue = "1",dataType = "int"),
            @ApiImplicitParam(name = "orderBy", value = "排序，如[name asc,age desc]", dataType = "string", paramType = "query")
    })
    public ResponseResult<PageResult> pagingData(
            @RequestParam String mapperId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNum,
            HttpServletRequest req) {
        Map<String, Object> params = ParamsConversion.httpToMap(req);
        Object orderBy = params.get("orderBy");
        Page<Map<String, Object>> page = PageHelper.startPage(
                pageNum,
                pageSize,
                orderBy == null ? "" : StringUtils.join((String[]) orderBy)
        );
        List<Object> data= sqlSessionTemplate.selectList(mapperId, params);
        return ResponseResult.success(new PageResult<>(page.getTotal(), data, page.getPages(), page.getPageSize(), page.getPageNum()));
    }


    @RequestMapping(value = "execute", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
    @ApiOperation(value = "通用增删改接口（POST：新增，DELETE：删除，PUT：修改）", position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mappingId", value = "SQL配置的完整限定名称（namespace.id），如：com.xxx.xxx.update", required = true, dataType = "string")
    })
    public ResponseResult execute(
            @RequestParam String mapperId,
            HttpServletRequest req) {
        String method = req.getMethod();
        Map<String, Object> params = ParamsConversion.httpToMap(req);
        if (method.equalsIgnoreCase(RequestMethod.POST.toString())) {
            Integer result = sqlSessionTemplate.insert(mapperId, params);
            return result > 0 ? ResponseResult.success(params) : ResponseResult.failure(ErrorStatus.ERROR);
        } else if (method.equalsIgnoreCase(RequestMethod.DELETE.toString())) {
            Integer result = sqlSessionTemplate.delete(mapperId, params);
            return result > 0 ? ResponseResult.success() : ResponseResult.failure(ErrorStatus.ERROR);
        } else {
            Integer result = sqlSessionTemplate.update(mapperId, params);
            return result > 0 ? ResponseResult.success() : ResponseResult.failure(ErrorStatus.ERROR);
        }
    }


}
