const fs = require('fs');
const path = require('path');
const webpack = require('webpack');

function resolve(dir) {
  return path.join(__dirname, dir);
}
//此处为添加的配置
let pagodaUiDirsName = fs
  .readdirSync(resolve('node_modules'))
  .filter(dirName => /pagoda-ui/.test(dirName));

module.exports = {
  transpileDependencies: pagodaUiDirsName,
  devServer: {
    historyApiFallback: true,
    headers: {
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Credentials': 'true'
    },
    proxy: {
      '/api': {
        // target: 'http://192.168.2.48:9001/',
        target: 'http://192.168.7.218:9001/',
        changeOrigin: true,
        pathRewrite: {
          "^/api": ''
        }
      }
    }
  },
  configureWebpack: {
    output: {
      jsonpFunction: 'webpackJsonp-portal'
    },
    optimization: {
      runtimeChunk: false
    },
    plugins: [
      new webpack.DefinePlugin({
        BUILD_CONFIG: process.env.BUILD_CONFIG
      })
    ]
  }
};
