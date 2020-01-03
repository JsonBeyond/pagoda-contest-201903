/**
 * 配置编译环境和线上环境之间的切换
 * 
 * baseUrl: 域名地址
 * routerMode: 路由模式
 * baseImgPath: 图片存放地址
 * 
 */
const env = process.env.VUE_APP_BUILD_ENV;
// eslint-disable-next-line no-undef
const buildConfig = BUILD_CONFIG;
let routerMode = 'history';
let configMode;

if (buildConfig && buildConfig.apiConfig) {
  configMode = buildConfig.apiConfig;
} else {
  configMode = {
    development: {
       baseUrl: 'https://tsceneweb.3ycloud.com',
      // baseUrl: 'http://192.168.2.48:8081',
      // baseUrl: 'http://192.168.10.208:8080',
      baseImgPath: '/img/',
      uploadUrl: 'https://tsceneweb.3ycloud.com/files/upload'
    },
    debugging: {
      baseUrl: 'https://tsceneweb.3ycloud.com',
      baseImgPath: '/img/',
      uploadUrl: 'https://tsceneweb.3ycloud.com/files/upload'
    },
    test: {
      baseUrl: 'https://tpsceneweb.3ycloud.com',
      baseImgPath: '/img/',
      uploadUrl: 'https://tpsceneweb.3ycloud.com/files/upload'
    }
  };
}

const {
  uploadUrl,
  baseUrl,
  baseImgPath
} = configMode[env];

export {
  routerMode,
  baseUrl,
  baseImgPath,
  uploadUrl
};