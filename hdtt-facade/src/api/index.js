import {
  curry,
  pipe,
  map
} from 'ramda';

import {
  Message,
  Loading
} from 'element-ui';

import fetch from '@/config/fetch';
import {
  protocol
} from '@/config/utils';
import {
  optionsTranslator,
  traverseBaseData,
  baseDataKeyTranslator,
  admitSettingDataTranslator,
  admitSettingDataRecover,
  serviceCardDataTranslator,
  serviceCardDataRecover,
  keysTranslator,
  optionsRecover
} from '@/utils/translator';

import mem from 'mem';

/**
 * 接口缓存-5分钟
 */
const cacheFetch = mem(fetch, {
  cachePromiseRejection: false,
  maxAge: 1000 * 60 * 5
})

const curriedOptionsTranslator = curry(optionsTranslator);
/**
 * 提交tree组件时数据转换转为(codename)
 */
const submitDataTransfer = () => {
  const curriedTraverseBaseData = curry(traverseBaseData);
  const translatorCallBack = curry(optionsRecover)('name', 'code');
  const curriedKeysTranslator = curry(keysTranslator)(translatorCallBack);
  const translatorObject = curriedTraverseBaseData(curriedKeysTranslator(['mappingPackObj', 'mappingSubObj']));
  return translatorObject;
}

let loadingInstance;
let loadingInstanceNumber = 0;

fetch.aopSend((config) => {
  if (config.data.loading === false) {
    delete config.data.loading;
    return config;
  }
  loadingInstance = Loading.service({
    fullscreen: true,
    background: 'hsla(0,0%,100%,.4)'
  });
  loadingInstanceNumber++;
  return config;
});

fetch.aopReceive(response => {
  loadingInstanceNumber && loadingInstanceNumber--;
  if (loadingInstanceNumber <= 0 && loadingInstance) {
    loadingInstance.close();
  }
  return response;
});


fetch.aopSend(config => {
  // config.data = {
  //   [config.type === 'GET' ? 'queryInfo' : 'configInfo']: {
  //     ...config.data
  //   },
  //   protocol
  // };
  config.data = {...config.data}
  return config;
});

fetch.aopReceive(response => {
  if (response.errorCode) {
    Message.error(response.description);
    throw new Error(response.description);
  }
  return response;
});

// 创建场景demo数据
export const login = (params) => fetch('/hdtt/account/login', params, 'post');
// 更新场景demo数据
export const updateSceneDemoData = (params) => fetch('/scene/scene-config/demo-data', params, 'PUT');

// 场景发布
export const postScenePublish = (params, id) => fetch(`/scene/scene-config/scene-publish/${id}`, params, 'POST');
