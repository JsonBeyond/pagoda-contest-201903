package com.pagoda.hdtt.aotogen.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseDialog<M extends BaseDialog<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
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
	 * 用户id
	 */
	public void setUserId(java.lang.Integer userId) {
		set("userId", userId);
	}
	
	/**
	 * 用户id
	 */
	public java.lang.Integer getUserId() {
		return getInt("userId");
	}

}
