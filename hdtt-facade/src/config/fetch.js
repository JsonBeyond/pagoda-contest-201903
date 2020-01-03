// import {
//   baseUrl
// } from './env';
const baseUrl = 'api'

function queryParams(params) {
  console.log(params)
  return Object.keys(params)
    .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
    .join('&');
}

const beforeQueue = [];
const afterQueue = [];

const add = (arr, cb) => arr.push(cb);

const remove = (arr, cb) => arr.forEach((fn, i) => fn === cb && arr.splice(0, i));

const aopSend = cb => add(beforeQueue, cb);

const aopReceive = cb => add(afterQueue, cb);

const removeAopSend = cb => remove(beforeQueue, cb);

const removeAopReceive = cb => remove(afterQueue, cb);

const aopHooks = (queue, data) =>
  queue.reduce((config, fn) => fn(config) || config, data);

const isFetch = method => method === 'fetch' && window.fetch;

const setHeaders = (obj, headers) =>
  Object.keys(headers).forEach(header => obj.setRequestHeader(header, headers[header]));

const ajax = async (url = '', data = {}, type = 'GET', method = 'fetch') => {
  type = type.toUpperCase();
  url = baseUrl + url;

  const {
    data: aopData,
    ...aopConfig
  } = aopHooks(beforeQueue, {
    type,
    data,
    headers: isFetch(method) ? {
      Accept: 'application/json',
      'Content-Type': 'application/json',
      'Cache-Control': 'no-cache'
    } : {
      'Content-type': 'application/x-www-form-urlencoded'
    }
  });

  if (type == 'GET') {
    // 转化get参数
    url += (url.indexOf('?') === -1 ? '?' : '&') + queryParams(aopData);
  }

  if (isFetch(method)) {
    let requestConfig = {
      credentials: 'include',
      method: type,
      mode: 'cors',
      cache: 'force-cache',
      ...aopConfig
    };

    if (type === 'POST' || type === 'PUT' || type === 'DELETE') {
      Object.defineProperty(requestConfig, 'body', {
        value: JSON.stringify(aopData)
      });
    }

    try {
      const response = await fetch(url, requestConfig);
      const responseJson = await response.json();
      return aopHooks(afterQueue, responseJson);
    } catch (error) {
      throw new Error(error);
    }
  } else {
    return new Promise((resolve, reject) => {
      let requestObj;
      if (window.XMLHttpRequest) {
        requestObj = new XMLHttpRequest();
      } else {
        // eslint-disable-next-line no-undef
        requestObj = new ActiveXObject();
      }

      let sendData = '';
      if (type === 'POST' || type === 'PUT' || type === 'DELETE') {
        sendData = JSON.stringify(data);
      }

      requestObj.open(type, url, true);

      setHeaders(requestObj, aopConfig.headers);

      requestObj.send(sendData);

      requestObj.onreadystatechange = () => {
        if (requestObj.readyState == 4) {
          if (requestObj.status == 200) {
            let obj = requestObj.response;
            if (typeof obj !== 'object') {
              obj = JSON.parse(obj);
            }
            resolve(aopHooks(afterQueue, obj));
          } else {
            reject(requestObj);
          }
        }
      };
    });
  }
};

ajax.aopSend = aopSend;

ajax.aopReceive = aopReceive;

ajax.removeAopSend = removeAopSend;

ajax.removeAopReceive = removeAopReceive;

export default ajax;