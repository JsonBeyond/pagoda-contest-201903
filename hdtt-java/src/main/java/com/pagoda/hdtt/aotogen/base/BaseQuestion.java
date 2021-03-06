package com.pagoda.hdtt.aotogen.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseQuestion<M extends BaseQuestion<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 创建时间
	 */
	public void setCraeteDate(java.util.Date craeteDate) {
		set("craeteDate", craeteDate);
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCraeteDate() {
		return get("craeteDate");
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
	 * 知识库标题
	 */
	public void setQuestion(java.lang.String question) {
		set("question", question);
	}
	
	/**
	 * 知识库标题
	 */
	public java.lang.String getQuestion() {
		return getStr("question");
	}

	/**
	 * 知识库内容
	 */
	public void setAnswer(java.lang.String answer) {
		set("answer", answer);
	}
	
	/**
	 * 知识库内容
	 */
	public java.lang.String getAnswer() {
		return getStr("answer");
	}

	/**
	 * 创建人
	 */
	public void setCreater(java.lang.String creater) {
		set("creater", creater);
	}
	
	/**
	 * 创建人
	 */
	public java.lang.String getCreater() {
		return getStr("creater");
	}

	/**
	 * 关联问题id列表,逗号分隔
	 */
	public void setRelationQuestionId(java.lang.String relationQuestionId) {
		set("relationQuestionId", relationQuestionId);
	}
	
	/**
	 * 关联问题id列表,逗号分隔
	 */
	public java.lang.String getRelationQuestionId() {
		return getStr("relationQuestionId");
	}

	/**
	 * 权重
	 */
	public void setWeight(java.lang.Integer weight) {
		set("weight", weight);
	}
	
	/**
	 * 权重
	 */
	public java.lang.Integer getWeight() {
		return getInt("weight");
	}

	/**
	 * 点赞数
	 */
	public void setPriseCount(java.lang.Integer priseCount) {
		set("priseCount", priseCount);
	}
	
	/**
	 * 点赞数
	 */
	public java.lang.Integer getPriseCount() {
		return getInt("priseCount");
	}

	/**
	 * 是否已删除(0:未删除   1:已删除)
	 */
	public void setDelete(java.lang.Integer delete) {
		set("delete", delete);
	}
	
	/**
	 * 是否已删除(0:未删除   1:已删除)
	 */
	public java.lang.Integer getDelete() {
		return getInt("delete");
	}

}
