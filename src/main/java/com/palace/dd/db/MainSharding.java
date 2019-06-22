package com.palace.dd.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import com.palace.dd.db.alg.DBShardingAlg;
import com.palace.dd.db.alg.TableShardingAlg;

public class MainSharding {
	
	static String[] tables = new String[] {"tbBorrowIntent","tbBorrowerBill"};
	public static DataSource getShardingDataSource() throws SQLException {
	    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
	    StringBuilder sb = new StringBuilder();
	    for(String table : tables) {
	    	shardingRuleConfig.getTableRuleConfigs().add(getTableConfiguration(table));
	    	sb.append(table).append(",");
	    }
	    sb.deleteCharAt(sb.length()-1);
	    shardingRuleConfig.getBindingTableGroups().add(sb.toString());
	    return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, getProp());
	}
	private static int DBNum = 20;
	public static TableRuleConfiguration getTableConfiguration(String table) {
		StringBuilder sb = new StringBuilder();
		for(int i =0;i<DBNum;i++) {
			String tb = "P2P"+i+"."+table.trim()+"${"+i*100+".."+(i*100+99)+"},";
			sb.append(tb);
		}
		sb.deleteCharAt(sb.length()-1);
		System.out.println("###Table:"+sb.toString());
		TableRuleConfiguration result = new TableRuleConfiguration(table.toLowerCase(),sb.toString());
		String shardingCol = "lBorrowerId";
		result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(shardingCol,new TableShardingAlg(),null));
		result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(shardingCol,new DBShardingAlg(),null));
		return result;
	}
   public static Map<String, DataSource> createDataSourceMap() {
    	Map<String, DataSource> resultMap = new HashMap<>();
    	for(int i =0;i<DBNum;i++) {
    		String dataSourceName = "P2P"+i;
    		BasicDataSource result = new BasicDataSource();
		    result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
		    result.setUrl(String.format("jdbc:mysql://testmysql:3306/%s", dataSourceName));
		    result.setUsername("writedafy");
		    result.setPassword("writeDafy!@#$");
	        resultMap.put(dataSourceName, result);
    	}
        return resultMap;
    }
    public static Properties getProp() {
    	Properties pp = new Properties();
    	pp.put("sql.show", true);
    	return pp;
    }
    
	public static class SimpleKeyGenerator{
	}
    
}
