/**
 * 
 */
package com.mydev.entity;

/**
 * 
 * 项目名字:mydev<br>
 * 类描述:表结构<br>
 * 创建人:wengmd<br>
 * 创建时间:2015年4月2日<br>
 * 修改人:<br>
 * 修改时间:2015年4月2日<br>
 * 修改备注:<br>
 * 
 * @version 0.2<br>
 */
public class TableBean implements java.io.Serializable {
	/**
	 * 表名称
	 */
	private String name;
	/**
	 * 表描述
	 */
	private String comment;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
		
}
