<#include "../global/javaDefine.ftl">
package ${packageName};

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* ${className} (${tableName })表实体类
* @author ${config.metaData.author}
* @date ${.now}
*/
@EqualsAndHashCode(callSuper = true)
@TableName("${tableName}")
@Data
public class ${entityClassName} extends Model<${entityClassName}> {
<#list tableInfo.columnInfoList as columnInfo>
    /**
    *   ${columnInfo.comment}
    */
    private ${columnInfo.javaType}  ${tool.toFieldName(columnInfo.name)};
</#list>
}
