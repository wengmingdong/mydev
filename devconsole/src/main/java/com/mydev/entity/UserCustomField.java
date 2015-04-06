/**
 * 
 */
package com.mydev.entity;

import java.util.Map;

/**
 * 
 * 项目名字:mydev<br>
 * 类描述:用户自定义类型<br>
 * 创建人:wengmd<br>
 * 创建时间:2015年4月3日<br>
 * 修改人:<br>
 * 修改时间:2015年4月3日<br>
 * 修改备注:<br>
 * 
 * @version 0.2<br>
 */
public class UserCustomField extends FieldBeanBase implements java.io.Serializable {
	private String ClasssName;
	
	@Override
	public String getFieldTypeStr() {
		// TODO Auto-generated method stub
		return ClasssName;
	}
}
