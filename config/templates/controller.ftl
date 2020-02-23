<#include "../global/javaDefine.ftl">
package ${packageName};

<@importEntity></@importEntity>
<@importService></@importService>
import cn.qqhxj.fastcode.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("${urlPrefix}")
@RestController
public class ${controllerClassName} {
@Autowired
private ${serviceClassName} ${serviceClassName?uncap_first};

@PostMapping("/add")
public Result add(@RequestBody ${entityClassName} ${entityClassName?uncap_first}) {
return Result.ok(${serviceClassName?uncap_first}.save(${entityClassName?uncap_first}));
}

@GetMapping("/del")
public Result del(@RequestParam String id) {
return Result.ok(${serviceClassName?uncap_first}.removeById(id));
}

@PostMapping("/update")
public Result update(@RequestBody ${entityClassName} ${entityClassName?uncap_first}) {
return Result.ok(${serviceClassName?uncap_first}.updateById(${entityClassName?uncap_first}));
}

@GetMapping("/get")
public Result<${entityClassName}> get(@RequestParam String id) {
return Result.ok(${serviceClassName?uncap_first}.getById(id));
}

}