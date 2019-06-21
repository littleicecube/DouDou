package com.palace.dd.db;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class DB {

	static DataSourceTransactionManager tx;
	static DataSource ds;
	static JdbcTemplate tem;
	static {
		//ds = MainSharding.getShardingDataSource();
		tx = new DataSourceTransactionManager(ds);
		tem = new JdbcTemplate(ds);
	}
	 
	public static Map<String,Object> queryForMap(String sql){
		List<Map<String,Object>> list = tem.queryForList(sql);
		return list.get(0);
	}
	public  static Map<String,Object> queryForMap(String sql,Object ...obj){
		List<Map<String,Object>> list = tem.queryForList(sql,obj);
		return list.get(0);
	}
	public  static List<Map<String,Object>> queryFormList(String sql){
		return tem.queryForList(sql);
	}
	public  static List<Map<String,Object>> queryFormList(String sql,Object ...obj){
		return tem.queryForList(sql,obj);
	}
	public  static int update(String sql ) {
		return tem.update(sql);
	}
	public  static int update(String sql,Object ...obj) {
		return tem.update(sql,obj);
	}
}
