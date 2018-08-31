package code.controller;

import code.gen.MyGenerator;
import code.model.User;
import code.util.JDBCUtils;
import code.util.JsonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/8/31.
 */
@RestController
public class GenerateController {
    @Autowired
    private MyGenerator myGenerator;
    @Autowired
    private JDBCUtils jdbcUtils;
    @RequestMapping("/api/getTablesInformation")
    public String getTablesInformation(@RequestParam(value = "tableName",required = false) String tableName){
        return new JsonResponseData(true,"数据表信息",1,"数据表信息",jdbcUtils.getTableInformation(tableName)).toString();
    }

    @RequestMapping("/api/generatorCode")
    public String generatorCode(){
        try {
            myGenerator.generatorByEntity(new User());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResponseData(true,"代码生成",1,"代码生成",null).toString();
    }
}
