<#include "../global/javaDefine.ftl">
package ${serviceImplPackageName};

<@importEntity></@importEntity>
<@importDao></@importDao>
<@importService></@importService>
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author ${config.metaData.author}
* @date ${.now}
*/
@Service
public class ${serviceImplclassName} extends ServiceImpl<${daoClassName},${entityClassName}> implements ${serviceClassName} {
}
