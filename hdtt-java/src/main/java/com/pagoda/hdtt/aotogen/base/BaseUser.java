package com.pagoda.hdtt.aotogen.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 年龄
	 */
	public void setAge(java.lang.Integer age) {
		set("age", age);
	}
	
	/**
	 * 年龄
	 */
	public java.lang.Integer getAge() {
		return getInt("age");
	}

	/**
	 * 账号
	 */
	public void setPhone(java.lang.String phone) {
		set("phone", phone);
	}
	
	/**
	 * 账号
	 */
	public java.lang.String getPhone() {
		return getStr("phone");
	}

	/**
	 * 密码
	 */
	public void setPassword(java.lang.String password) {
		set("password", password);
	}
	
	/**
	 * 密码
	 */
	public java.lang.String getPassword() {
		return getStr("password");
	}

	/**
	 * 用户昵称
	 */
	public void setNickName(java.lang.String nickName) {
		set("nickName", nickName);
	}
	
	/**
	 * 用户昵称
	 */
	public java.lang.String getNickName() {
		return getStr("nickName");
	}

	/**
	 * 状态(0:无效  1:有效)
	 */
	public void setStatus(java.lang.String status) {
		set("status", status);
	}
	
	/**
	 * 状态(0:无效  1:有效)
	 */
	public java.lang.String getStatus() {
		return getStr("status");
	}

	/**
	 * 用户头像地址
	 */
	public void setIcon(java.lang.String icon) {
		set("icon", icon);
	}
	
	/**
	 * 用户头像地址
	 */
	public java.lang.String getIcon() {
		return getStr("icon");
	}

}
