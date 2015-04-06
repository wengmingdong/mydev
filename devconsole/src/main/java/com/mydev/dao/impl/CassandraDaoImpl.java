/**
 * 
 */
package com.mydev.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.mydev.dao.DatabaseDao;
import com.mydev.entity.FieldBean;
import com.mydev.entity.TableBean;
import com.mydev.entity.UserCustomField;

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
@Repository
public class CassandraDaoImpl implements DatabaseDao {
	private static final Logger logger = LoggerFactory
			.getLogger(CassandraDaoImpl.class);
	private Cluster cluster;
	private Session session;
	
	private String keyspace;
	
	public static final Map<String, Class> DBTYPE = new HashMap<String, Class>();
	
	static {
		DBTYPE.put("org.apache.cassandra.db.marshal.UTF8Type", String.class);
		DBTYPE.put("org.apache.cassandra.db.marshal.Int32Type", Integer.class);
		DBTYPE.put("org.apache.cassandra.db.marshal.ListType", List.class);
		DBTYPE.put("org.apache.cassandra.db.marshal.MapType", Map.class);
		DBTYPE.put("org.apache.cassandra.db.marshal.UserType", UserCustomField.class);
	}
	
	protected Session getSession() {
		return this.session;
	}
	
	@Override
	public void connect(String node, String username, String password, String keyspace) {
		this.keyspace = keyspace;
		cluster = Cluster.builder()
				.addContactPoint(node).withCredentials(username, password)
				.build();
		Metadata metadata = cluster.getMetadata();
		logger.info("Connected to cluster: \n", metadata.getClusterName());
		
		for ( Host host : metadata.getAllHosts() ) {
			logger.info("Datatacenter: " + host.getDatacenter() + ";" + host.getAddress() + ";" + host.getRack());
		}
		
		session = cluster.connect();
		
		
	}
	
	@Override
	public Class toJavaType(String dbtype) throws Exception {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(dbtype))
			throw new Exception("dbtype param is null");		
		Class javaCls = DBTYPE.get(dbtype);
		if (javaCls == null) {
			//用户自定义类型
			if (dbtype.indexOf("org.apache.cassandra.db.marshal.UserType") >= 0) {
				javaCls = UserCustomField.class;
			} else 
				throw new Exception(dbtype + " javaClass  is null");
		} 
		return javaCls;
	}
		 
	public void close() {
		 session.close();
		 cluster.close();
	}
	
	@Override
	public List<TableBean> getTables() {
		// TODO Auto-generated method stub
		Clause clause = QueryBuilder.eq("keyspace_name", keyspace); 
		Select select = QueryBuilder.select().from("system", "schema_columnfamilies");
		select.where(clause);
//		BoundStatement bindStatement = 
//				session.prepare(
//				"select * from system.schema_columnfamilies where keyspace_name=?")
//				.bind(keyspace);
//				session.execute(bindStatement);
		ResultSet results = session.execute(select);
		List<Row> rows = results.all();
		List<TableBean>rt = new ArrayList<TableBean>();
		for (Row row: rows) {
			TableBean bean = new TableBean();
			bean.setName(row.getString("columnfamily_name"));
			bean.setComment(row.getString("comment"));
			rt.add(bean);
		}
		return rt;
	}
	
	@Override
	public List<FieldBean> getFields(String tableName) throws Exception {
		// TODO Auto-generated method stub
		
		Select select = QueryBuilder.select("column_name", "type", "validator").from("system", "schema_columns");
		select.where(QueryBuilder.eq("keyspace_name", keyspace)).and(QueryBuilder.eq("columnfamily_name", tableName));
		ResultSet results = session.execute(select);
		
		List<Row> rows = results.all();
		List<FieldBean>rt = new ArrayList<FieldBean>();
		for (Row row: rows) {
			FieldBean bean = new FieldBean();
			bean.setFieldName(row.getString("column_name"));
			//bean.setFieldType(fieldType);
			String dbtype = row.getString("validator");
			bean.setFieldType(toJavaType(dbtype));
			rt.add(bean);
		}
		return rt;
	}
	
	public void validSession() throws Exception {
		if (session == null)
			throw new Exception("session is null");
	}
}
