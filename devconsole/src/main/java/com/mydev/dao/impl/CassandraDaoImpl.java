/**
 * 
 */
package com.mydev.dao.impl;

import java.sql.Timestamp;
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
	private static final String DBLIST = "org.apache.cassandra.db.marshal.ListType";
	private static final String DBMAP = "org.apache.cassandra.db.marshal.MapType";
	private Cluster cluster;
	private Session session;
	
	private String keyspace;
	
	public static final Map<String, Class> DBTYPE = new HashMap<String, Class>();
	
	static {
		DBTYPE.put("org.apache.cassandra.db.marshal.UTF8Type", String.class);
		DBTYPE.put("org.apache.cassandra.db.marshal.Int32Type", Integer.class);
		DBTYPE.put(DBLIST, List.class);
		DBTYPE.put(DBMAP, Map.class);
		DBTYPE.put("org.apache.cassandra.db.marshal.UserType", UserCustomField.class);
		DBTYPE.put("org.apache.cassandra.db.marshal.BytesType", Byte[].class);
		DBTYPE.put("org.apache.cassandra.db.marshal.LongType", Long.class);
		DBTYPE.put("org.apache.cassandra.db.marshal.TimestampType", Timestamp.class);
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
	public Class toJavaType(FieldBean bean, String dbtype) throws Exception {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(dbtype))
			throw new Exception("dbtype param is null");		
		Class javaCls = DBTYPE.get(dbtype);
		if (javaCls == null) {
			//用户自定义类型
			if (dbtype.indexOf("org.apache.cassandra.db.marshal.UserType") >= 0) {
				javaCls = UserCustomField.class;
			} else if (dbtype.indexOf(DBLIST) >= 0) {
				String subDbtype = dbtype.substring(DBLIST.length() + 1, dbtype.length() - 1);
				Class subClass = toJavaType(bean, subDbtype);
				javaCls = List.class;
				bean.setFieldTypeStr("List<" + subClass.getSimpleName() + ">");
			} else if (dbtype.indexOf(DBMAP) >= 0) {
				String subDbtype = dbtype.substring(DBMAP.length() + 1, dbtype.length() - 1);
				String[] subDbtypes = subDbtype.split(",");
				Class subClassKey = toJavaType(bean, subDbtypes[0]);
				Class subClassVal = toJavaType(bean, subDbtypes[1]);
				bean.setFieldTypeStr("Map<" + subClassKey.getSimpleName() + "," + subClassVal.getSimpleName() + ">");
			} else 
				throw new Exception(dbtype + " javaClass  is null");
		} 
		bean.setFieldType(javaCls);
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
			bean.setFieldName(fieldNameDelUnderline(row.getString("column_name")));
			//bean.setFieldType(fieldType);
			String dbtype = row.getString("validator");
			toJavaType(bean, dbtype);
			bean.setComment("请注解");
			rt.add(bean);
		}
		return rt;
	}
	
	protected String fieldNameDelUnderline(String fieldName) {
		String[] fieldNameArr = fieldName.split("_");
		if (fieldNameArr.length <= 0)
			return fieldName;
		StringBuilder rt = new StringBuilder();
		int i = 0;
		for (String s: fieldNameArr) {
			if (i == 0) {
				rt.append(s);
			} else { 
				rt.append(StringUtils.capitalize(s));
			}
			i++;
		}
		return rt.toString();
	}
	
	public void validSession() throws Exception {
		if (session == null)
			throw new Exception("session is null");
	}
}
