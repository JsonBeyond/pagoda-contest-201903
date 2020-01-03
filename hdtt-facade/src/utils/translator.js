import {
  map,
  forEach,
  prop,
  filter,
  propEq,
  curry,
  forEachObjIndexed
} from 'ramda';

const label = 'label';
const value = 'value';

const keysTranslator = (translator, keys, data) => {
  const isObject = !keys;
  const fn = keys ? forEach : forEachObjIndexed;

  fn((value, index) => {
    const key = isObject ? index : value;
    data[key] = translator(data[key]);
  }, keys || data);
};

const optionsRecover = (lab, val, data) => {
  if (!data) return;
  return {
    ...data,
    [lab]: data[label],
    [val]: data[value]
  };
};

/**
 * 转换option
 * @param {object} 字段
 */
const optionsTranslator = (lab, val, data) => {
  if (!data) return;
  return {
    ...data,
    [label]: data[lab],
    [value]: data[val]
  };
};

const treeTranslator = (lab, val, chil, data) => {
  for(let i=0; i<data.length; i++){
    let obj = {
      label: data[i][lab],
      value: data[i][val]
    }
    if (data[i].children && data[i].children.length) {
      obj.children = data[i][chil];
      data[i] = obj;
      treeTranslator(lab, val, chil, data[i].children)
    }
    data[i] = obj;
  }
};
/**
 * 转换场景基础数据key
 * @param {object} 场景基础数据
 */
const baseDataKeyTranslator = ({
  coopServiceClassify,
  coopServiceIndustry,
  coopServiceLabel,
  coopServiceObject,
  coopServiceRole
}) => ({
  serviceTypeOptions: coopServiceClassify,
  serviceIndustryOptions: coopServiceIndustry,
  serviceSubjectOptions: coopServiceObject,
  serviceRoleOptions: coopServiceRole,
  serviceTagOptions: coopServiceLabel
});

/**
 * string key 换成 object {key, value}
 * @param {array} sources
 * @param {array} keys
 */
const getKeys2ObjectList = (sources, keys) => {
  const data = [];

  const traverse = (key, array) => {
    forEach(source => {
      const {
        children,
        ...obj
      } = source;
      if (obj.value === key) {
        data.push({
          key: obj.value,
          value: obj.label
        });
      } else if (children) {
        traverse(key, children);
      }
    }, array);
  };

  forEach(key => {
    traverse(key, sources);
  }, keys);
  return data;
};

/**
 * {value}[] 转换 [value]
 * @param {array} source
 * @param {string} key
 */
const getObjects2KeyList = (source, key = 'key') => {
  return map(prop(key), source);
};

/**
 * 基础数据转换
 * @param {function} translator 转换函数
 * @param {array} sourceData 源数据
 */
const traverseBaseData = (translator, sourceData) => {
  return map(data => {
    if (!data) return null;
    if (!data.children) return {
      ...data,
      ...translator(data)
    };

    return {
      ...data,
      ...translator(data),
      children: traverseBaseData(translator, data.children)
    };
  }, sourceData);
};

/**
 * 获取对象数组内的某个值
 * @param {string} key
 * @param {string} value
 */
const listFilter = curry((key, value) => filter(propEq(key, value)));

/**
 * 管理员设置-信息项转换
 * @param {object} param0
 */
const admitSubInfoTranslator = ({
  msgContent,
  msgSendModeList,
  remindConfigUserFlag
} = {}) => ({
  tip: remindConfigUserFlag,
  msgType: msgSendModeList,
  content: msgContent
});

/**
 * 管理员设置-信息项解析
 * @param {object} param0
 */
const admitSubInfoRecover = ({
  tip,
  msgType,
  content
} = {}) => ({
  msgContent: content,
  msgSendModeList: msgType,
  remindConfigUserFlag: tip
});

const admitRoleTranslator = ({
  partuserConfigContentList,
  partuserConfigType,
  ...data
}) => ({
  ...data,
  tags: partuserConfigType,
  content: partuserConfigContentList
});

const admitRoleRecover = ({
  tags,
  content,
  ...data
}) => ({
  ...data,
  partuserConfigContentList: content,
  partuserConfigType: tags
});

/**
 * 管理员设置-数据转换
 * @param {object} sourceData
 */
const admitSettingDataTranslator = sourceData => {
  const {
    partusers,
    subInfo,
    ...data
  } = sourceData;

  const participantRole = listFilter('participantRole');
  const getWorker = participantRole('executor');
  const getExecutor = participantRole('supervisor');

  const translator = curry((fn, list) => map(fn, list));
  const roleTranslator = translator(admitRoleTranslator);

  return {
    ...data,
    subInfoFormData: admitSubInfoTranslator(subInfo || {}),
    workTableData: roleTranslator(getWorker(partusers || [])),
    superviseTableData: roleTranslator(getExecutor(partusers || []))
  };
};

/**
 * 管理员设置-数据解析
 * @param {object} sourceData
 */
const admitSettingDataRecover = sourceData => {
  const {
    workTableData,
    superviseTableData,
    subInfoFormData,
    ...data
  } = sourceData;

  const translator = curry((fn, key, value, list) =>
    map(data => ({
      ...fn(data),
      [key]: value
    }), list)
  );
  const roleRecover = translator(admitRoleRecover)('participantRole');

  return {
    ...data,
    partusers: [
      ...roleRecover('executor', workTableData),
      ...roleRecover('supervisor', superviseTableData)
    ],
    subInfo: admitSubInfoRecover(subInfoFormData)
  };
};

const serviceCardTranslator = ({
  sceneServiceCardCoNodeInfoName,
  sceneServiceCardCoNodeInfoID,
  ...data
}) => ({
  ...data,
  name: sceneServiceCardCoNodeInfoName,
  id: sceneServiceCardCoNodeInfoID
});

const serviceCardRecover = ({
  name,
  id,
  ...data
}) => ({
  ...data,
  sceneServiceCardCoNodeInfoName: name,
  sceneServiceCardCoNodeInfoID: id
});

const serviceCardDataTranslator = sourceData => {
  return map(serviceCardTranslator, sourceData);
};

const serviceCardDataRecover = sourceData => {
  return map(serviceCardRecover, sourceData);
};

export {
  keysTranslator,
  optionsTranslator,
  optionsRecover,
  traverseBaseData,
  baseDataKeyTranslator,
  getKeys2ObjectList,
  getObjects2KeyList,
  listFilter,
  admitSubInfoRecover,
  admitSubInfoTranslator,
  admitSettingDataRecover,
  admitSettingDataTranslator,
  serviceCardDataTranslator,
  serviceCardDataRecover,
  treeTranslator
};