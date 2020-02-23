<#include "define.ftl">
<#--    java的-->
<#global className=tool.toClassName(tableInfo.name)+config.metaData[config.template].classSuffix>
<#global backPackageName=config.metaData.package>
<#global packageName=backPackageName+"."+config.metaData[config.template].packageSuffix>
<#--className-->
<#global entityClassName=tool.toClassName(tableInfo.name)+config.metaData.entity.classSuffix>
<#global daoClassName=tool.toClassName(tableInfo.name)+config.metaData.dao.classSuffix>
<#global serviceClassName=tool.toClassName(tableInfo.name)+config.metaData.service.classSuffix>
<#global serviceImplclassName=tool.toClassName(tableInfo.name)+config.metaData.serviceImpl.classSuffix>
<#global controllerClassName=tool.toClassName(tableInfo.name)+config.metaData.controller.classSuffix>
<#--packageName-->
<#global entityPackageName=backPackageName+"."+config.metaData.entity.packageSuffix>
<#global daoPackageName=backPackageName+"."+config.metaData.dao.packageSuffix>
<#global servicePackageName=backPackageName+"."+config.metaData.service.packageSuffix>
<#global serviceImplPackageName=backPackageName+"."+config.metaData.serviceImpl.packageSuffix>
<#global controllerPackageName=backPackageName+"."+config.metaData.controller.packageSuffix>
<#--导入定义-->
<#macro importEntity>
    import ${entityPackageName}.${entityClassName};<#lt >
</#macro>
<#macro importDao>
    import ${daoPackageName}.${daoClassName};<#lt >
</#macro>
<#macro importService>
    import ${servicePackageName}.${serviceClassName};<#lt >
</#macro>
<#macro importServiceImpl>
    import ${serviceImplPackageName}.${serviceImplclassName};<#lt >
</#macro>
<#assign fileName=className+config.metaData[config.template].fileSuffix>

<#if config.savePath?? && config.savePath!="">
    <#assign savePath=config.savePath+"/"+packageName?replace(".","/")>
<#else>
    <#assign savePath=config.metaData.path+"/"+packageName?replace(".","/")>
</#if>

<@setFilePath path=savePath fileName=fileName/>