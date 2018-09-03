package code.gen;

import code.model.TableInformation;
import code.util.JDBCUtils;
import code.util.Utils;
import lombok.Data;

import java.util.*;

/**
 * Created by Administrator on 2018/9/3.
 */
@Data
public class EntityClassInfo {
   private String author;
   private String className;
   private String basePackage;
   List<TableInformation> entityList;
    public EntityClassInfo() {
        entityList = new ArrayList<>();
    }
}
