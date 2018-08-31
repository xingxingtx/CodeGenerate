package code.gen;

import lombok.Data;

/**
 * Created by weipeng on 2018/8/30.
 * 外键对象简单封装
 */
@Data
public class AssociationObject {
    private String property;
    private String columnPrefix;
    private String javaType;
}
