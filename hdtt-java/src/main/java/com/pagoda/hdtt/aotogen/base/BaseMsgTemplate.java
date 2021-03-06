package com.pagoda.hdtt.aotogen.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMsgTemplate<M extends BaseMsgTemplate<M>> extends Model<M> implements IBean {

	public void setTemplateID(java.lang.Integer templateID) {
		set("templateID", templateID);
	}
	
	public java.lang.Integer getTemplateID() {
		return getInt("templateID");
	}

	/**
	 * 创建时间
	 */
	public void setCreateDate(java.util.Date createDate) {
		set("createDate", createDate);
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateDate() {
		return get("createDate");
	}

	/**
	 * 更新时间
	 */
	public void setUpdateDate(java.util.Date updateDate) {
		set("updateDate", updateDate);
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateDate() {
		return get("updateDate");
	}

	/**
	 * 平台类型(dingding:钉钉;  wechat:微信; wswork:企业微信;  sms:短信;  email:邮件)
	 */
	public void setPlatformType(java.lang.String platformType) {
		set("platformType", platformType);
	}
	
	/**
	 * 平台类型(dingding:钉钉;  wechat:微信; wswork:企业微信;  sms:短信;  email:邮件)
	 */
	public java.lang.String getPlatformType() {
		return getStr("platformType");
	}

	/**
	 * 接口功能类型(11: 钉钉_工作通知消息;  12:钉钉_群消息  13:钉钉_普通消息  待补充..)
	 */
	public void setFunctionType(java.lang.String functionType) {
		set("functionType", functionType);
	}
	
	/**
	 * 接口功能类型(11: 钉钉_工作通知消息;  12:钉钉_群消息  13:钉钉_普通消息  待补充..)
	 */
	public java.lang.String getFunctionType() {
		return getStr("functionType");
	}

	/**
	 * 消息类型(11: 钉钉_文本消息   12:钉钉_图片消息  13:钉钉_语言消息 14:钉钉_文件消息  15:钉钉_链接消息  16:钉钉_OA消息  17:钉钉_markdown消息  18:钉钉_卡片消息)
	 */
	public void setMsgType(java.lang.String msgType) {
		set("msgType", msgType);
	}
	
	/**
	 * 消息类型(11: 钉钉_文本消息   12:钉钉_图片消息  13:钉钉_语言消息 14:钉钉_文件消息  15:钉钉_链接消息  16:钉钉_OA消息  17:钉钉_markdown消息  18:钉钉_卡片消息)
	 */
	public java.lang.String getMsgType() {
		return getStr("msgType");
	}

	/**
	 * 标题
	 */
	public void setTitle(java.lang.String title) {
		set("title", title);
	}
	
	/**
	 * 标题
	 */
	public java.lang.String getTitle() {
		return getStr("title");
	}

	/**
	 * 消息内容
	 */
	public void setContent(java.lang.String content) {
		set("content", content);
	}
	
	/**
	 * 消息内容
	 */
	public java.lang.String getContent() {
		return getStr("content");
	}

	/**
	 * 调整链接
	 */
	public void setUrl(java.lang.String url) {
		set("url", url);
	}
	
	/**
	 * 调整链接
	 */
	public java.lang.String getUrl() {
		return getStr("url");
	}

	/**
	 * 图片链接
	 */
	public void setPicUrl(java.lang.String picUrl) {
		set("picUrl", picUrl);
	}
	
	/**
	 * 图片链接
	 */
	public java.lang.String getPicUrl() {
		return getStr("picUrl");
	}

	/**
	 * 消息其他数据
	 */
	public void setOthers(java.lang.String others) {
		set("others", others);
	}
	
	/**
	 * 消息其他数据
	 */
	public java.lang.String getOthers() {
		return getStr("others");
	}

}
