package ${packageName};

import java.util.List;
import lombok.Data;
/**
*  @author ${author}
*/
@Data
public class ${className} {
<#list attrs as attr>
private ${attr.type} ${attr.name};
</#list>
}