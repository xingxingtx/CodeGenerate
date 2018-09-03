package code.model;

import java.util.List;
import lombok.Data;
/**
*  @author ${author}
*/
@Data
public class ${className}model {
<#list entityList as attr>
private ${attr.columnType} ${attr.columnName};
</#list>
}