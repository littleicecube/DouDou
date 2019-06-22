package com.palace.dd.db.alg;

import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

public class TableShardingAlg implements PreciseShardingAlgorithm {
	public TableShardingAlg() {
		
	}
	@Override
	public String doSharding(Collection availableTargetNames,PreciseShardingValue shardingValue) {
		 for (Object each : availableTargetNames) {
			 	Object shardingVal = shardingValue.getValue();
			 	String strVal = "";
			 	if(shardingVal instanceof Long) {
			 		strVal = (Long)shardingVal/10000+"";
			 	}else {
			 		strVal = (Integer)shardingVal/10000+"";
			 	}
			 	String key = each.toString();
			 	if(key.endsWith(strVal)) {
			 		return key;
			 	}
	        }
		 return null;
	}
}