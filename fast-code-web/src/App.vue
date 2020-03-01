<template>
  <div id="app">
    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="表配置" key="1">
        <TableConfig></TableConfig>
      </a-tab-pane>
      <a-tab-pane tab="代码生成" key="2" forceRender>
        <CodeGeneration></CodeGeneration>
        </a-tab-pane>
<!--      <a-tab-pane tab="元数据配置" key="3">-->
<!--        <MetadataConfig></MetadataConfig>-->
<!--      </a-tab-pane>-->
<!--      <a-tab-pane tab="模板管理" key="4">-->
<!--        <TemplateManagement></TemplateManagement>-->
<!--      </a-tab-pane>-->
    </a-tabs>
  </div>
</template>

<script>

import TableConfig from "@/components/TableConfig";
import CodeGeneration from "@/components/CodeGeneration";
export default {
  name: 'App',
  components: {
    CodeGeneration,
    TableConfig
  },data(){
    return {
      action:"1"
    }
  }

}
</script>

