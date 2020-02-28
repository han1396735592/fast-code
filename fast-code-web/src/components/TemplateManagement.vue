<template>
    <div>
        <a-tabs defaultActiveKey="1" type="card">
            <a-tab-pane v-for="(template,index) in templateList" :tab="template" :key="index">
                <a-card>
                    <a-textarea :rows="35" :value="template"></a-textarea>
                </a-card>
            </a-tab-pane>
        </a-tabs>
        <a-empty v-if="templateList.length==0"/>
    </div>

</template>

<script>
    export default {
        name: "TemplateManagement",
        data() {
            return {
                templateList: []
            }
        },
        beforeMount() {
            this.$axios.get("/getTemplates").then(res => {
                console.log(res)
                this.templateList = res.data.data
            })

        }
    }
</script>

<style scoped>

</style>