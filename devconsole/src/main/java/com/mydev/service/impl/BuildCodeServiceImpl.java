/**
 * 
 */
package com.mydev.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydev.core.TempBuilder;
import com.mydev.dao.DatabaseDao;
import com.mydev.entity.FieldBean;
import com.mydev.entity.TableBean;
import com.mydev.entity.rt.CreateCodeResultBean;
import com.mydev.service.BuildCodeService;

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
@Service
public class BuildCodeServiceImpl implements BuildCodeService {
	@Autowired
	private DatabaseDao databaseDao;
	

	
	@Override
	public void connect(String node, String username, String password,
			String keyspace) {
		// TODO Auto-generated method stub
		databaseDao.connect(node, username, password, keyspace);
		
	}
		
	@Override
	public List<TableBean> getTables() {
		// TODO Auto-generated method stub
		return databaseDao.getTables();
	}
	
	@Override
	public List<CreateCodeResultBean> createCode(List<String> tableNames) throws Exception {
		// TODO Auto-generated method stub
		if (tableNames == null || tableNames.size() <= 0)
			throw new Exception("没有可生成的表");
		for (String tableName: tableNames) {
			createCode(tableName);
		}
		return null;
	}
	@Override
	public CreateCodeResultBean createCode(String tableName) throws Exception {
		// TODO Auto-generated method stub
		List<FieldBean> fields = databaseDao.getFields(tableName);
		Map<String, Object> outMapKey = new HashMap<String, Object>();
		outMapKey.put("bean", tableName);
		outMapKey.put("fields", fields);
		TempBuilder tempBuilder = new TempBuilder();	
		tempBuilder.build(getFtlPath(), getOutPath(), outMapKey);		
		return null;
	}
	
	protected String getFtlPath() {
		return "E:/work/edu/project/code_factory/";
	}
	
	protected String getOutPath() {
		return "E:/work/edu/project/code_factory/target/";
	}
	
	public DatabaseDao getDatabaseDao() {
		return databaseDao;
	}


	public void setDatabaseDao(DatabaseDao databaseDao) {
		this.databaseDao = databaseDao;
	}
}
