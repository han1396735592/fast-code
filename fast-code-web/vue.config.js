module.exports = {
    pluginOptions: {
        proxy: {
            enabled: true,
            context: '/api',
            options: {
                target: 'http://localhost:8080',
                changeOrigin: true,
                ws:true,                                            //websocket
                pathRewrite:{
                    '^/api':''
                }
            }
        }
    }
}