package code.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/31.
 */
@Configuration
public class ConfigurationValue {
    //代码生成所配置的模板路径
    public static String basePath;
    //代码生成所配置的文件XML生成路径
    public static String XML;
    //代码生成所配置的文件Mapper生成路径
    public static String mapper;
    //代码生成所配置的文件Service生成路径
    public static String service;
    //代码生成所配置的文件ServiceImpl生成路径
    public static String serviceImpl;
    //代码生成所配置的文件dao生成路径
    public static String dao;

    @Value("${configuration.basePath}")
    public  void setBasePath(String basePath) {
        ConfigurationValue.basePath = basePath;
    }

    @Value("${configuration.xml}")
    public  void setXML(String XML) {
        ConfigurationValue.XML = XML;
    }

    @Value("${configuration.mapper}")
    public  void setMapper(String mapper) {
        ConfigurationValue.mapper = mapper;
    }

    @Value("${configuration.service}")
    public  void setService(String service) {
        ConfigurationValue.service = service;
    }

    @Value("${configuration.serviceImpl}")
    public  void setServiceImpl(String serviceImpl) {
        ConfigurationValue.serviceImpl = serviceImpl;
    }

    @Value("${configuration.dao}")
    public  void setDao(String dao) {
        ConfigurationValue.dao = dao;
    }
}
