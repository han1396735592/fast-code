<#include "../global/javaDefine.ftl">
package ${servicePackageName};

<@importEntity></@importEntity>
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ${config.metaData.author}
* @date ${.now}
*/
public interface ${serviceClassName} extends IService<${entityClassName}> {

}

