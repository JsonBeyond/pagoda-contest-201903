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

	public void setCreateDate(java.util.Date createDate) {
		set("createDate", createDate);
	}
	
	public java.util.Date getCreateDate() {
		return get("createDate");
	}

	public void setUpdateDate(java.util.Date updateDate) {
		set("updateDate", updateDate);
	}
	
	public java.util.Date getUpdateDate() {
		return get("updateDate");
	}

	public void setPlatformType(java.lang.String platformType) {
		set("platformType", platformType);
	}
	
	public java.lang.String getPlatformType() {
		return getStr("platformType");
	}

	public void setFunctionType(java.lang.String functionType) {
		set("functionType", functionType);
	}
	
	public java.lang.String getFunctionType() {
		return getStr("functionType");
	}

	public void setMsgType(java.lang.String msgType) {
		set("msgType", msgType);
	}
	
	public java.lang.String getMsgType() {
		return getStr("msgType");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}
	
	public java.lang.String getTitle() {
		return getStr("title");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}
	
	public java.lang.String getUrl() {
		return getStr("url");
	}

	public void setPicUrl(java.lang.String picUrl) {
		set("picUrl", picUrl);
	}
	
	public java.lang.String getPicUrl() {
		return getStr("picUrl");
	}

	public void setOthers(java.lang.String others) {
		set("others", others);
	}
	
	public java.lang.String getOthers() {
		return getStr("others");
	}

}