/**
 * 
 */
package com.mydev.dao;
import java.util.List;

import com.mydev.entity.FieldBean;
import com.mydev.entity.TableBean;

/**
 * 
 * 项目名字:mydev<br>
 * 类描述:<br>
 * 创建人:wengmd<br>
 * 创建时间:2015年3月30日<br>
 * 修改人:<br>
 * 修改时间:2015年3月30日<br>
 * 修改备注:<br>
 * 
 * @version 0.2<br>
 */
public interface DatabaseDao {
	public void connect(String node, String username, String password, String keyspace);
	public List<TableBean> getTables();
	public List<FieldBean> getFields(String tableName)  throws Exception;
	/**
	 * 数据库类型转为java类型
	 * @param dbtype
	 * @return
	 */
	public Class toJavaType(FieldBean bean, String dbtype) throws Exception;
}
