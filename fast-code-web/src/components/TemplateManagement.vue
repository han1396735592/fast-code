<template>
    <div>模板管理
        <div>{{templateList}}</div>
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
            this.$axios.get("/fastCode/getTemplates").then(res => {
                console.log(res)
                this.templateList = res.data.data
            })

        }
    }
</script>

<style scoped>

</style>