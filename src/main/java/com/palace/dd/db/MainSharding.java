package com.palace.dd.db;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;

public class MainSharding {
	
	static String[] tables = new String[] {"aaa","aaa"};
    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        StringBuilder sb = new StringBuilder();
        for(String table : tables) {
        	shardingRuleConfig.getTableRuleConfigs().add(getTableConfiguration(table));
        	sb.append(table).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        shardingRuleConfig.getBindingTableGroups().add(sb.toString());
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig,new HashMap<String, Object>(), new Properties());
    }
    
    public static TableRuleConfiguration getTableConfiguration(String table) {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable(table);
        result.setActualDataNodes("P2P${0..19}."+table+"${0..1999}");
        result.setKeyGeneratorColumnName(SimpleKeyGenerator.class.getName());
        result.setKeyGeneratorColumnName("lId");
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(table,new PreciseShardingAlgorithm<Comparable<?>>() {
			@Override
			public String doSharding(Collection<String> availableTargetNames,
					PreciseShardingValue<Comparable<?>> shardingValue) {
				return null;
			}
		}.getClass().getName()));
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(table,new PreciseShardingAlgorithm<Comparable<?>>() {
			@Override
			public String doSharding(Collection<String> availableTargetNames,
					PreciseShardingValue<Comparable<?>> shardingValue) {
				return null;
			}
		}.getClass().getName()));
        return result;
    }
    Map<String, DataSource> createDataSourceMap() {
    	Map<String, DataSource> resultMap = new HashMap<>();
    	for(int i =0;i<19;i++) {
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
    
    
  public static class SimpleKeyGenerator{
    	
    }
    
}
