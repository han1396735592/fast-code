<#include "../global/javaDefine.ftl">
package ${packageName};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
<@importEntity></@importEntity>

/**
* @author ${config.metaData.author}
* @date ${.now}
*/
@Mapper
public interface ${daoClassName} extends BaseMapper<${entityClassName}> {

}
