package code.gen;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import code.config.ConfigurationValue;
import code.model.User;
import code.util.JDBCUtils;
import code.util.Utils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/30.
 */
@Component
public class MyGenerator {


    //public static String basePath = "E:\\aaaProjrct\\CodeGenerate\\src\\main\\resources\\ftls";
    private static final int FEILDL_ENGTH = 1;// 统一定义截取字符串的长度为多少字母
    private static Configuration config = new Configuration(new Version("2.3.23"));
    static {
        try {
            config.setDirectoryForTemplateLoading(new File(ConfigurationValue.basePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        //generatorByEntity();

    }



    public static void generatorByEntity(Object object) throws Exception{
        ClassInfo info = new ClassInfo(object.getClass());
        boolean bl = JDBCUtils.isExistTable(info.getBigClassName());
        if(!bl){
            // 创建表
            createTable(info);
        }
        // 生成xml文件
        createMyBatisXML(info, "mapperxml.ftl",  ConfigurationValue.XML + "{1}Mapper.xml");

        // mapper接口
        createMapper(info, "Mapper.ftl", ConfigurationValue.mapper + "{1}Mapper.java");


        // 生成Service接口
        createService(info, "service.ftl", ConfigurationValue.service + "I{1}Service.java");

        // 生成Service实现类
        createServiceImpl(info, "serviceImpl.ftl", ConfigurationValue.serviceImpl + "{1}ServiceImpl.java");

        //生成dao接口
        createDao(info,"dao.ftl",ConfigurationValue.dao + "{1}Dao.java");
    }



    private static void createMapper(ClassInfo info, String templateFile, String targetFile) throws Exception {
        createFile(info, templateFile, targetFile);
        System.out.println("生成" + info.getBigClassName() + "Mapper接口");
    }

    private static void createService(ClassInfo info, String templateFile, String targetFile) throws Exception {
        createFile(info, templateFile, targetFile);
        System.out.println("生成I" + info.getBigClassName() + "Service接口");
    }


    private static void createServiceImpl(ClassInfo info, String templateFile, String targetFile) throws Exception {
        createFile(info, templateFile, targetFile);
        System.out.println("生成" + info.getBigClassName() + "ServiceImpl实现");
    }

    private static void createDao(ClassInfo info, String templateFile, String targetFile) throws Exception {
        createFile(info, templateFile, targetFile);
        System.out.println("生成I" + info.getBigClassName() + "dao接口");
    }
    private static void createController(ClassInfo info, String templateFile, String targetFile) throws Exception {
        createFile(info, templateFile, targetFile);
        System.out.println("生成" + info.getBigClassName() + "Controller文件");
    }

    // 数据库表的创建
    private static void createTable(ClassInfo info) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(info.getBigClassName());
        LinkedHashMap<String, String> propertys = info.getFieldMap();
        ListIterator<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(propertys.entrySet())
                .listIterator(propertys.size());
        sql.append(" ( ");
        while (list.hasPrevious()) {
            Map.Entry<String, String> entry = list.previous();
            if ("id".equals(entry.getKey())) {
                // 主键拼接
                sql.append("id " + "BIGINT PRIMARY KEY auto_increment ,");
            } else {
                // 非主键拼接
                sql.append(Utils.javaType2MySqlType(entry));
            }
        }
        sql.append(" ); ");
        sql.deleteCharAt(sql.lastIndexOf(","));
        // 创建连接
        Connection con = JDBCUtils.getConnection();
        PreparedStatement ps = con.prepareStatement(sql.toString());
        boolean result = ps.execute();
        if (result) {
            throw new RuntimeException("创建表" + info.getBigClassName() + "异常！！！");
        } else {
            System.out.println("创建表" + info.getBigClassName() + "成功！");
        }
        con.close();
        ps.close();
    }

    // 其他java文件的创建
    private static void createFile(ClassInfo info, String templateFile, String targetFile) throws Exception {
        Template template = config.getTemplate(templateFile);
        targetFile = MessageFormat.format(targetFile, info.getBasePackage().replace(".", "/"), info.getBigClassName());
        System.out.println(templateFile);
        System.out.println(targetFile);
        File file = new File(targetFile);
        // 如果文件存在则报错，不会覆盖以前的文件
        if (file.exists()) {
            throw new RuntimeException(file.getName() + "已经存在！");
        }
        File parentFile = file.getParentFile();
        // 创建文件目录
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        template.process(info, new FileWriter(file));
    }

    // Mybatis文件的创建
    private static void createMyBatisXML(ClassInfo info, String templateFile, String targetFile) throws Exception {

        LinkedHashMap<String, String> propertys = info.getFieldMap();
        LinkedHashMap<String, String> importFieldMap = info.getImportFieldMap();
        List<String> genericFieldList = info.getGenericFieldList();
        List<AssociationObject> association = info.getAssociation();
        StringBuilder insert1 = new StringBuilder().append("insert into " + info.getBigClassName() + "  (");
        StringBuilder insert2 = new StringBuilder().append("  values( ");

        StringBuilder delete = new StringBuilder().append("delete from " + info.getBigClassName() + " where id =#{id}");

        StringBuilder update = new StringBuilder().append("update " + info.getBigClassName() + " set ");

        StringBuilder selectselectByPrimaryKey = new StringBuilder().append("select ");

        ListIterator<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(propertys.entrySet())
                .listIterator(propertys.size());
        while (list.hasPrevious()) {

            String key = list.previous().getKey();

            System.out.println(key);
            String value = propertys.get(key);
            if (Utils.judgeType(value)) {
                // 插入时不需要主键
                if (!key.equals("id")) {
                    genericFieldList.add(key);
                    insert1.append(" " + key + " ,");
                    insert2.append(" #{" + key + "} ,");
                    update.append(" " + key + "=#{" + key + "}, ");
                }
                selectselectByPrimaryKey
                        .append(info.getSmallClassName().subSequence(0, MyGenerator.FEILDL_ENGTH) + "." + key + ", ");
            } else {
                // 外键关联的相关属性
                AssociationObject associationObject = new AssociationObject();
                associationObject.setColumnPrefix(key.substring(0, MyGenerator.FEILDL_ENGTH) + "_");
                associationObject.setProperty(key);
                associationObject.setJavaType(value);
                association.add(associationObject);
                insert1.append(" " + key + "_id ,");
                insert2.append(" #{" + key + ".id} ,");

                update.append(" " + key + "_id=#{" + key + ".id}, ");
                int index = value.lastIndexOf(".");
                value = value.substring(index + 1);

                importFieldMap.put(key, value);

            }
        }
        int index = insert1.lastIndexOf(",");
        String str1 = insert1.substring(0, index) + " )";
        index = insert2.lastIndexOf(",");
        String str2 = insert2.substring(0, index) + " )";
        // 增加
        info.setInsert(str1 + str2);
        System.out.println("增加操作  " + str1 + str2);

        // 删除
        info.setDelete(delete.toString());
        System.out.println("删除操作  " + delete.toString());

        index = update.lastIndexOf(",");
        String subUpdate = update.substring(0, index);
        subUpdate = subUpdate + " where id=#{id}";
        // 更改操作
        info.setUpdate(subUpdate);
        System.out.println("更改操作  " + subUpdate);
        // 判断是否有外键
        if (importFieldMap.size() <= 0) {
            index = selectselectByPrimaryKey.lastIndexOf(",");
            String str = selectselectByPrimaryKey.substring(0, index);

            // 条数的查询
            String queryForCount = "select count(" + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH)
                    + ".id" + ") from " + info.getBigClassName() + " "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH);
            info.setQueryForCount(queryForCount);
            System.out.println("查询条数  " + queryForCount);

            // 查询结果集
            info.setQueryListData(str + " from " + info.getSmallClassName() + " "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH));
            System.out.println("查询所有  " + str + " from " + info.getSmallClassName() + " "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH));
            // 分页相关
            info.setLimit("limit #{currentPage},#{pageSize}");

            str = str + " from " + info.getSmallClassName() + " where "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH) + ".id=#{id}";

            // 根据主键的查询
            info.setSelectByPrimaryKey(str);
            System.out.println("主键查询  " + str);

        } else {
            Set<Entry<String, String>> entrySet = importFieldMap.entrySet();
            for (Entry<String, String> entry : entrySet) {
                selectselectByPrimaryKey.append(entry.getKey().substring(0, MyGenerator.FEILDL_ENGTH) + ".id as "
                        + entry.getKey().substring(0, MyGenerator.FEILDL_ENGTH) + "_id ,");
            }
            index = selectselectByPrimaryKey.lastIndexOf(",");
            String str = selectselectByPrimaryKey.substring(0, index);
            str = str + " from " + info.getBigClassName() + "  "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH);
            for (Entry<String, String> entry : entrySet) {
                str = str + " left join " + entry.getValue() + " "
                        + entry.getKey().substring(0, MyGenerator.FEILDL_ENGTH) + " on " + "("
                        + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH) + "." + entry.getKey()
                        + "_id=" + entry.getKey().substring(0, MyGenerator.FEILDL_ENGTH) + ".id)";
            }

            String queryForCount = "select count(" + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH)
                    + ".id" + ") from " + info.getBigClassName() + " "
                    + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH);
            info.setQueryForCount(queryForCount);
            System.out.println("查询条数  " + queryForCount);

            // 查询结果集
            info.setQueryListData(str);
            System.out.println("查询所有  " + str);
            // 分页相关
            info.setLimit("limit #{start},#{pageSize}");

            str = str + " where " + info.getSmallClassName().substring(0, MyGenerator.FEILDL_ENGTH) + ".id=#{id}";

            // 根据主键的查询
            info.setSelectByPrimaryKey(str);
            System.out.println("主键查询  " + str);
        }

        Template template = config.getTemplate(templateFile);
        targetFile = MessageFormat.format(targetFile, info.getBasePackage().replace(".", "/"), info.getBigClassName());
        File file = new File(targetFile);
        File parentFile = file.getParentFile();
        // 创建文件目录
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        // 生成xml文件
        template.process(info, new FileWriter(file));
    }

}
