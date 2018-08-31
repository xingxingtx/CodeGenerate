package code.util;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by weipeng on 2018/8/30.
 */
public class Utils {
    private Utils(){}
    /*
    * java 类型的判断,只判断了常用类型
    */
    public static boolean judgeType(String type) {
        String javaType = "";
        if (type.contains(".")) {
            //类型的截取
            int lastIndexOf = type.lastIndexOf(".");
            javaType = type.substring(lastIndexOf + 1);
        } else {
            javaType = type;
        }
        switch (javaType) {
            case "int":
            case "Integer":
            case "long":
            case "Long":
            case "double":
            case "Double":
            case "float":
            case "Float":
            case "String":
            case "BigDecimal":
            case "Boolean":
            case "boolean":
            case "Date":
                return true;
            default:
                return false;
        }
    }
    /*
    *  将java中的类型转换为数据中的类型
    */
    public static String javaType2MySqlType(Map.Entry<String, String> entry) {
        String javaType = "";
        if (entry.getValue().contains(".")) {
            // 类型的截取
            int lastIndexOf = entry.getValue().lastIndexOf(".");
            javaType = entry.getValue().substring(lastIndexOf + 1);
        } else {
            javaType = entry.getValue();
        }
        switch (javaType) {
            case "String":
                return entry.getKey() + " VARCHAR(255) ,";
            case "byte[]":
                return entry.getKey() + " BLOB ,";
            case "int":
                return entry.getKey() + " INTEGER ,";
            case "Integer":
                return entry.getKey() + " INTEGER ,";
            case "Boolean":
                return entry.getKey() + " BIT ,";
            case "boolean":
                return entry.getKey() + " BIT ,";
            case "Long":
                return entry.getKey() + " BIGINT ,";
            case "long":
                return entry.getKey() + " BIGINT ,";
            case "float":
                return entry.getKey() + " FLOAT ,";
            case "Float":
                return entry.getKey() + " FLOAT ,";
            case "double":
                return entry.getKey() + " DOUBLE ,";
            case "Double":
                return entry.getKey() + " DOUBLE ,";
            case "BigDecimal":
                return entry.getKey() + " DECIMAL ,";
            case "Date":
                return entry.getKey() + " DATETIME,";
            default:
                return entry.getKey() + "_id Long,";
        }
    }
    public static String tableNameToEntityName(String tableName){
        String reName = null;
        if(tableName.contains("_")){
            String[] strings = tableName.split("_");
            reName = strings[0];
            for(int i = 0;i < strings.length; i++){
                if(i > 0){
                    reName += upperCase(strings[i]);
                }
            }
        }else {
            return tableName;
        }
        return reName;
    }

    /*
    * 将实体类的驼峰命名转成数据库习惯命名：例如：userName ---> user_name
    */
    public static String entityNameToTableName(String entityName){
       StringBuilder sb = new StringBuilder();
        for(int i = 0; i < entityName.length(); i++) {
            char c = entityName.charAt(i);
            if (!Character.isLowerCase(c)) {
               sb.append("_");
            }
            sb.append(c);
        }
        return sb.toString().toLowerCase();
    }

    /*
    *
    * 大写首字母
    */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

}
