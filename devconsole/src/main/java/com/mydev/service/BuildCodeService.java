/**
 * 
 */
package com.mydev.service;

import java.util.List;

import com.mydev.entity.TableBean;
import com.mydev.entity.rt.CreateCodeResultBean;

/**
 * 
 * 项目名字:代码创建<br>
 * 类描述:<br>
 * 创建人:wengmd<br>
 * 创建时间:2015年3月30日<br>
 * 修改人:<br>
 * 修改时间:2015年3月30日<br>
 * 修改备注:<br>
 * 
 * @version 0.2<br>
 */
public interface BuildCodeService {
	public void connect(String node, String username, String password, String keyspace);
	public List<TableBean> getTables();
	public List<CreateCodeResultBean> createCode(List<String> tableNames) throws Exception;
	public CreateCodeResultBean createCode( String tableName) throws Exception;
}
