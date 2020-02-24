const vm = new Vue({
    el: "#page"
    ,
    beforeMount() {
        const that = this
        axios.get("/fastCode/db/list").then(res => {
            that.tableList = res.data.data.map(item => {
                return {
                    value: item,
                    label: item
                }
            })
        })
        axios.get("/fastCode/getTemplates").then(res => {
            that.templateList = res.data.data.map(item => {
                return {
                    value: item,
                    label: item
                }
            })
        })
        axios.get("/fastCode/tableConfig/get").then(res => {
            that.tableConfigInfo = res.data.data
        })
        axios.get("/fastCode/columnConfig/get").then(res => {
            that.columnConfigInfo = res.data.data
        })

    },
    computed: {},
    data: {
        tableList: null,
        templateList: null,
        previewCode: null,
        gen: {},
        pre: {},
        tableConfigInfo: null,
        columnConfigInfo: null,
        tableInfo: null,
    },
    watch: {
        'pre.table': (table) => {
            if (table) {
                axios.get(`/fastCode/db/table/${table}`).then(res => {
                    vm.tableInfo = res.data.data
                })
            }
        },
    },
    methods: {
        submit(e) {
            e.preventDefault();
            const that = this
            axios.post("/fastCode/generate/project", {...that.gen, group: that.group, save: true}).then(res => {
                console.log(res)
                that.$message.info("生成成功")
            })
        },
        previewSubmit() {
            const that = this
            that.pre.data = JSON.parse(that.data || "{}")
            axios.post("/fastCode/preview/template", {...that.pre, ...that.group}).then(res => {
                console.log(res)
                that.previewCode = res.data
                that.$message.info("预览成功")
            })
        },
        saveSubmit() {
            const that = this
            axios.post(`/fastCode/db/table/${that.tableInfo.name}/save`, that.tableInfo.ext).then(res => {
                that.$message.info("保存成功")
            })
        }
    }
});