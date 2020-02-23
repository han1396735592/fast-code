<#--定义变量-->

<#global tableInfo=tableInfo>
<#global tool=tool>
<#global dbInfo=dbInfo>

<#global tableName=tableInfo.name>
<#global fileName></#global>
<#global savePath></#global>
<#global urlPrefix=tableName?replace("_","/")>
<#macro setFilePath path fileName>
    ${config.setSavePath(path+"/"+fileName)}
</#macro>