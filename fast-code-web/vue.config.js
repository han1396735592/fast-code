module.exports = {
    pluginOptions: {
        proxy: {
            enabled: true,
            context: '/fastCode',
            options: {
                target: 'http://localhost:8080',
                changeOrigin: true,
                ws:true,                                            //websocket
                pathRewrite:{
                    '^/fastCode':'/fastCode'
                }
            }
        }
    }
}