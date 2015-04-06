/**
 * 
 */
package com.mydev.entity;

/**
 * 
 * 项目名字:nd esp<br>
 * 类描述:<br>
 * 创建人:wengmd<br>
 * 创建时间:2015年4月2日<br>
 * 修改人:<br>
 * 修改时间:2015年4月2日<br>
 * 修改备注:<br>
 * 
 * @version 0.2<br>
 */
public class FieldBean implements java.io.Serializable {
	//private static final Map fieldJavaType = new 
	private String fieldName;
	private Class fieldType;
	private String comment;
	private boolean primaryKey;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	public Class getFieldType() {
		return fieldType;
	}
	public void setFieldType(Class fieldType) {
		this.fieldType = fieldType;
	}
	
	public String getFieldTypeStr() {
		return fieldType.getName();
	}
	
}
