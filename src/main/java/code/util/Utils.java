package code.util;

import java.util.Map;

/**
 * Created by Administrator on 2018/8/30.
 */
public class Utils {
    private Utils(){}
    /*
    * java 类型的判断,只判断了常用类型
    */
    public static boolean judgeType(String type) {
        switch (type) {
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


}
