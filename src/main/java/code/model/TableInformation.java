package code.model;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/31.
 */
@Data
public class TableInformation {
    //列名
    private  String columnName;
    //类型名称
    private  String columnType;
    //列的大小
    private int dataSize;
    //小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null
    private int digits ;
    //是否允许使用 NULL
    private int nullAble ;

}
