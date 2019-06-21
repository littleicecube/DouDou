package com.palace.dd.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

public class MainSharding {

	static class PreciseShardingAlgorithmExa implements PreciseShardingAlgorithm{

		@Override
		public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
			// TODO Auto-generated method stub
			return null;
		}
		 
	}
    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
        shardingRuleConfig.getBroadcastTables().add("t_config");
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new PreciseShardingAlgorithmExa()));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig,null);
    }
    
    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
            KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("","order_id");
            return result;
    }
    
    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order");
        result.setActualDataNodes("ds${0..1}.t_order${0..1}");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }
    
    TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order_item");
        result.setActualDataNodes("ds${0..1}.t_order_item${0..1}");
        return result;
    }
    
    
    static int DBNum = 20;
    public static List<TableRule> getTableRule(DataSourceRule dsr){
    	String[] tableArr = new String[] {"tbBorrowIntent","tbBorrowerBill"};
    	List<TableRule> retList = new ArrayList();
    	for(String table : tableArr) {
    		List<String> list = new ArrayList<>();
	    	for(int i=0;i<DBNum*100;i++) {
	    		list.add(table+i);
	    	}
	    	retList.add(TableRule.builder(table).actualTables(list).dataSourceRule(dsr).build());
    	}
    	return retList;
    }
    static int DBNum = 20;
	public static Map<String,DataSource> createDataSourceMap(){
		Map<String,DataSource> map=new HashMap<String,DataSource>();
		for(int i=0;i<DBNum;i++) {
			String name = "P2P"+i;
			map.put(name,createDataSource(name));
		}
		return map;
	}
	
	public static DataSource createDataSource(String ds){
		BasicDataSource bs=new BasicDataSource();
		bs.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
		bs.setUrl(String.format("jdbc:mysql://TestHost:3306/%s", ds));
		bs.setUsername("writedafy");
		bs.setPassword("writeDafy!@#$");
		return bs;
	}
	
	public void testStringF(){
		String s = String.format("string/%sbing%saaa%s", "name1","name2","name3"); 
		System.out.println(s);
	}
}
