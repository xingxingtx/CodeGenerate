package code.gen;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/31.
 */
@Data
public class TableAndEntity {
    private String column;
    private String property;

    public TableAndEntity() {
    }

    public TableAndEntity(String property,String column) {
        this.column = column;
        this.property = property;
    }
}
