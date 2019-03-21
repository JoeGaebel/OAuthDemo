module.exports = {
    devServer: {
        port: 8081,
        proxy: {
            '^/api': {
                target: 'http://localhost:8080',
                secure: false,
            },
        }
    }
};
