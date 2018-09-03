package code.util;

import code.model.TableInformation;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by weipeng on 2018/8/30.
 */
@Component
@Configuration
public class JDBCUtils {
    public   static String driver;
    public   static String url;
    public   static String userName;
    public   static String possWord;
    public   static  String databaseName;
    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, possWord);
        }catch (SQLException s){
        }catch (ClassNotFoundException c){
        }
        return connection;
    }
    /*
    * 判断表createTableName是否已经存在
    */
    public static boolean isExistTable(String createTableName){
        String sql = "SELECT table_name FROM information_schema.TABLES WHERE table_name ='"+createTableName+"' AND TABLE_SCHEMA = '"+databaseName+"';";
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet rs = null;
        boolean result= false;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            if(rs.next()){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(connection,rs,statement);
        }
        return result;
    }

    /*
    *获取表的信息
    */
    public static Map<String, List<TableInformation>> getTableInformation(String tableName){
        Map<String, List<TableInformation>> tableMap = new HashMap<String, List<TableInformation>>();
        //tableName为空时取所有表的字段
        if(StringUtils.isEmpty(tableName)){
            tableName = "%";
        }
        //1. JDBC连接MYSQL
        Connection conn = getConnection();
        ResultSet tableRet = null;
        DatabaseMetaData metaData = null;
        try {
            //2. 下面就是获取表的信息。
             metaData = conn.getMetaData();
             tableRet = metaData.getTables(null,"%",tableName,new String[]{"TABLE"});
            //3. 提取表的名字。
            while(tableRet.next()) {
                List<TableInformation> list = new ArrayList<>();
                //4. 提取表内的字段的名字和类型
                String columnName;
                String columnType;
                ResultSet colRet = metaData.getColumns(null, "%", tableRet.getString("TABLE_NAME"), "%");
                while (colRet.next()) {
                    TableInformation tableInformation = new TableInformation();
                    //列名
                    columnName = colRet.getString("COLUMN_NAME");
                    //类型名称
                    columnType = colRet.getString("TYPE_NAME");
                    //列的大小
                    int datasize = colRet.getInt("COLUMN_SIZE");
                    //小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null
                    int digits = colRet.getInt("DECIMAL_DIGITS");
                    //是否允许使用 NULL
                    int nullable = colRet.getInt("NULLABLE");
                    tableInformation.setColumnName(columnName);
                    tableInformation.setColumnType(columnType);
                    tableInformation.setDataSize(datasize);
                    tableInformation.setDigits(digits);
                    tableInformation.setNullAble(nullable);
                    list.add(tableInformation);
                }
                tableMap.put(tableRet.getString("TABLE_NAME"),list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            closeConnection(conn,tableRet,null);
        }
        return tableMap;
    }

    public static void closeConnection(Connection connection, PreparedStatement preparedStatement){
        if (null != connection){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != preparedStatement){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeConnection(Connection connection, ResultSet resultSet, Statement  statement){
        if (null != resultSet){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != statement){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(null != connection){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Value("${JDBCUtils.driver}")
    public  void setDriver(String driver) {
        JDBCUtils.driver = driver;
    }
    @Value("${JDBCUtils.url}")
    public  void setUrl(String url) {
        JDBCUtils.url = url;
    }
    @Value("${JDBCUtils.userName}")
    public  void setUserName(String userName) {
        JDBCUtils.userName = userName;
    }
    @Value("${JDBCUtils.possWord}")
    public  void setPossWord(String possWord) {
        JDBCUtils.possWord = possWord;
    }
    @Value("${JDBCUtils.databaseName}")
    public  void setDatabaseName(String databaseName) {
        JDBCUtils.databaseName = databaseName;
    }
}
