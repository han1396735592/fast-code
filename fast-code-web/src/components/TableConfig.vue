<template>
    <div>
        <a-row>
            <a-col span="6">
                <a-card title="选择表">
                    <a-radio-group v-model="tableName">
                        <a-row>
                            <a-col :span="12" v-for="item in tableList" :key="item">
                                <a-radio :value="item">{{item}}</a-radio>
                            </a-col>
                        </a-row>
                    </a-radio-group>

                </a-card>
            </a-col>
            <a-col span="6" v-if="(tableInfo && tableName && tableConfigInfo)?true:false">
                <a-card title="基础信息">
                    <a-form-item :label="item.label" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }"
                                 v-for="(item,key) in tableConfigInfo" :key="key">
                        <template v-if="item.type==='select'">
                            <a-select style="width: 100%" v-model="tableInfo.ext[key]">
                                <a-select-option
                                        :value="option" :key="option"
                                        v-for="option in item.selectList">
                                    {{option}}
                                </a-select-option>
                            </a-select>
                        </template>

                        <template v-else-if="item.type==='switch'">
                            <a-switch v-model="tableInfo.ext[key]"/>
                        </template>
                        <template v-else-if="item.type==='textarea'">
                            <a-textarea v-model="tableInfo.ext[key]"></a-textarea>
                        </template>
                        <template v-else-if="item.type==='radio'">
                            <a-switch v-model="tableInfo.ext[key]"/>
                        </template>
                        <template v-else-if="item.type==='input'">
                            <a-input v-model="tableInfo.ext[key]"/>
                        </template>
                        <template v-else>
                            <a-input v-model="tableInfo.ext[key]"/>
                        </template>

                    </a-form-item>
                    <hr>
                    <a-form-item :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }" label="选择模板">
                        <a-select style="width: 100%" v-model="template">
                            <a-select-option
                                    :value="option" :key="option"
                                    v-for="option in templateList">
                                {{option}}
                            </a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 20 ,offset: 4 }">
                        <a-button type="primary" @click="save">
                            保存信息
                        </a-button>
                        <a-button :disabled="(template && tableName && tableInfo) ?false:true" @click="preview"
                                  style="margin-left: 30px" type="primary">
                            预览
                        </a-button>
                    </a-form-item>
                </a-card>
            </a-col>
            <a-col span="12" v-if="tableInfo && tableName">
                <a-card title="字段信息配置">
                    <a-tabs style="height: 600px">
                        <a-tab-pane :key="index" :tab="columnInfo.name"
                                    v-for="(columnInfo,index) in tableInfo.columnInfoList">
                            <a-form-item :label="item.label" :label-col="{ span: 4 }"
                                         :wrapper-col="{ span: 12 }"
                                         v-for="(item,key) in columnConfigInfo" :key="key">
                                <template v-if="item.type==='select'">
                                    <a-select style="width: 100%"
                                              v-model="tableInfo.ext.columns[columnInfo.name][key]">
                                        <a-select-option :value="option" :key="option"
                                                         v-for="option in item.selectList">
                                            {{option}}
                                        </a-select-option>
                                    </a-select>
                                </template>
                                <template v-else-if="item.type==='switch'">
                                    <a-switch v-model="tableInfo.ext.columns[columnInfo.name][key]"/>
                                </template>
                                <template v-else-if="item.type==='textarea'">
                                    <a-textarea
                                            v-model="tableInfo.ext.columns[columnInfo.name][key]"></a-textarea>
                                </template>
                                <template v-else-if="item.type==='radio'">
                                    <a-switch v-model="tableInfo.ext.columns[columnInfo.name][key]"/>
                                </template>
                                <template v-else-if="item.type==='input'">
                                    <a-input v-model="tableInfo.ext.columns[columnInfo.name][key]"/>
                                </template>
                                <template v-else>
                                    <a-input
                                            v-model="tableInfo.ext.columns[columnInfo.name][key]"/>
                                </template>
                            </a-form-item>

                        </a-tab-pane>
                    </a-tabs>
                </a-card>
            </a-col>
        </a-row>
        <a-modal
                title="代码预览"
                :visible="previewCode"
                @ok="previewCode=null"
                @cancel="previewCode=null"
                width="800px"
        >
           <a-textarea v-model="previewCode" :rows="30"></a-textarea>
        </a-modal>
    </div>

</template>

<script>
    export default {
        name: "TableConfig",
        components: {},
        data() {
            return {
                tableName: null,
                tableInfo: {},
                tableList: [],
                tableConfigInfo: null,
                columnConfigInfo: null,
                templateList: [],
                template: null,
                previewCode: null
            }
        },
        beforeMount() {
            this.$axios.get("/db/list").then(res => {
                console.log(res)
                this.tableList = res.data.data
            })
            this.$axios.get("/tableConfig/get").then(res => {
                this.tableConfigInfo = res.data.data
            })
            this.$axios.get("/columnConfig/get").then(res => {
                this.columnConfigInfo = res.data.data
            })
            this.$axios.get("/getTemplates").then(res => {
                console.log(res)
                this.templateList = res.data.data
            })
        },
        methods: {
            save() {
                this.$axios.post(`/db/table/${this.tableInfo.name}/save`, this.tableInfo.ext).then(() => {
                    this.$message.info("保存成功")
                })
            },
            preview() {
                this.$axios.post(`/preview/template`, {
                    table:this.tableName,
                    template:this.template
                }).then((res) => {
                    this.$message.info("预览成功")
                    console.log(res.data)
                    this.previewCode=res.data
                })
            }
        },
        watch: {
            tableName(table) {
                if (table) {
                    this.$axios.get(`/db/table/${table}`).then(res => {
                        let tableInfo = res.data.data
                        const {tableConfigInfo, columnConfigInfo} = this
                        if (tableConfigInfo) {
                            Object.keys(tableConfigInfo).forEach(function (key) {
                                if ((tableInfo.ext[key]) === undefined && tableConfigInfo[key] && tableConfigInfo[key].default) {
                                    tableInfo.ext[key] = tableConfigInfo[key].default
                                }
                            });
                        }
                        if (columnConfigInfo && tableInfo.ext.columns) {
                            Object.keys(tableInfo.ext.columns).forEach(function (k) {

                                Object.keys(columnConfigInfo).forEach(key => {

                                    if ((tableInfo.ext.columns[k][key]) === undefined && columnConfigInfo[key].default) {
                                        console.log("ok")
                                        console.log(columnConfigInfo[key])
                                        tableInfo.ext.columns[k][key] = columnConfigInfo[key].default
                                    }
                                })
                            });
                        }
                        this.tableInfo = tableInfo
                    })
                }
            }
        }

    }
</script>

<style scoped>

</style>