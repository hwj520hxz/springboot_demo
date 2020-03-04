package com.boss.demo.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson.JSON;
import com.boss.demo.commons.annotations.LoggerOperator;
import com.boss.demo.commons.data.ResponseResult;
import com.boss.demo.commons.exception.BusinessException;
import com.boss.demo.commons.util.ExcelPathUtils;
import com.boss.demo.commons.util.ExportExcelUtil;
import com.boss.demo.entity.User;
import com.boss.demo.service.user.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 创建人：何伟杰
 * @version 版本号：V1.0
 * @ClassName 类名：UserController
 * @Description 功能说明：
 * @date 创建日期：2020/1/10
 * @time 创建时间：15:46
 */

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;


    @LoggerOperator(description = "用户查询")
    @ApiOperation(value = "查询用户",notes = "查询用户信息",httpMethod = "GET")
    @GetMapping(value = "/queryUser")
//    @CrossOrigin
    public ResponseResult queryUser(Integer pageNum,Integer pageSize){
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<User> userList = userService.queryUser();
            PageInfo<User> pageInfo = new PageInfo<>(userList);
            return ResponseResult.success(pageInfo);
        } catch (BusinessException e){
            return ResponseResult.error();
        }
    }

    @ApiOperation(value = "根据ID查询用户",notes = "根据ID查询用户信息",httpMethod = "GET")
    @GetMapping(value = "/queryUserById")
    public List<User> queryUserById(String userId){
        return userService.queryUserById(userId);
    }

    @GetMapping(value = "/queryUser2")
    public List<User> queryUser2(){
        return userService.queryUser2();
    }


//    /**
//     * 功能说明：导出用户列表（因与easypoi找不到匹配的版本，会导致版本冲突，暂时屏蔽掉）
//     **/
//    @RequestMapping(value = "export")
//    public void exportEvent(HttpServletResponse response) {
//        //excel标题
//        String title = "用户信息表";
//        //excel表名
//        String [] headers = {"姓名", "邮箱", "电话"};
//        //excel文件名
//        String fileName = title + System.currentTimeMillis()+".xls";
//        List<User> userList = userService.queryUser();
//        String content[][] = new String[userList.size()][];
//        for(int i = 0;i < userList.size(); i++){
//            content[i] = new String[headers.length];
//            content[i][0] = userList.get(i).getUserName();
//            content[i][1] = userList.get(i).getUserEmail();
//            content[i][2] = userList.get(i).getUserTel();
//        }
//        //创建HSSFWorkbook
//        HSSFWorkbook wb = ExportExcelUtil.getHSSFWorkbook(title, headers, content);
//        //响应到客户端
//        try {
//            ResponseUtil.setResponseHeader(response, fileName);
//            OutputStream os = response.getOutputStream();
//            wb.write(os);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }




    /**
     * @Description: 用户信息报表 导出
     * 注意点：是一片买哪个easypoi有以下讲究：1.表头可以直接在模板中写死，否则就需要从数据库查询，数据库返回的map是一个list，刚好可以
     * 匹配easypoi的遍历循环；如果手动使用hashmap的话，因为hashmap没有实现list接口，无法进行map的转换，会报错，而从数据库查询的map刚好
     * 是一个List<map<,,>>的格式，符合easypoi的循环遍历。
     **/
    @GetMapping("userMessageExport")
    public void userMessageExport(String deptId, String officeId, String agenId, String keyword, String status, String isTemp, String roleCode,  String authRoleCode, HttpServletResponse response) throws BusinessException, FileNotFoundException {

        String title = "用户信息表";
        List<Map<String,Object>> userMap = userService.queryUsers();
        Map<String,Object> data = new HashMap<>();
        data.put("title",title);
        data.put("list",userMap);
        TemplateExportParams param = new TemplateExportParams(ExcelPathUtils.convertTemplatePath("excelTemplates/用户报表.xlsx"),true);
        try {
            Workbook book = ExcelExportUtil.exportExcel(param, data);
            ExcelPathUtils.export(response, book, "用户信息表");
        } catch (Exception e) {
            System.out.println("导出失败:" + e);
        }
    }




}
