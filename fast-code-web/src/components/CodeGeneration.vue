<template>
    <div>
        <a-form>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 11 }" label="选择表">
                <a-checkbox-group v-model="form.tableList">
                    <a-row>
                        <a-col :span="6" v-for="item in tableList" style="margin-bottom: 10px" :key="item">
                            <a-checkbox :value="item">{{item}}</a-checkbox>
                        </a-col>
                    </a-row>
                </a-checkbox-group>
            </a-form-item>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 11 }" label="选择表">
                <a-checkbox-group v-model="form.templateList">
                    <a-row>
                        <a-col :span="6" v-for="item in templateList" style="margin-bottom: 10px" :key="item">
                            <a-checkbox :value="item">{{item}}</a-checkbox>
                        </a-col>
                    </a-row>
                </a-checkbox-group>
            </a-form-item>
            <a-form-item :label-col="{ span: 3 }" :wrapper-col="{ span: 11 }" label="保存路径">
                <a-input v-model="form.savePath"/>
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 21 ,offset: 2}">
                <a-button
                        :disabled="!(form.tableList  && form.templateList && form.tableList.length>0 && form.templateList.length>0 )"
                        @click="submit" type="primary">
                    生成
                </a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    export default {
        name: "CodeGeneration",
        data() {
            return {
                form: {
                    save: true
                },
                tableList: [],
                templateList: []
            }
        },
        beforeMount() {
            this.$axios.get("/db/list").then(res => {
                console.log(res)
                this.tableList = res.data.data
            })
            this.$axios.get("/getTemplates").then(res => {
                console.log(res)
                this.templateList = res.data.data
            })
        }, methods: {
            submit() {
                this.$axios.post("/generate/project", this.form).then(res => {
                    console.log(res)
                    this.$message.info("生成成功")
                }).finally(() => {
                    this.form = {
                        save: true
                    }
                })
            }
        }

    }
</script>

<style scoped>

</style>