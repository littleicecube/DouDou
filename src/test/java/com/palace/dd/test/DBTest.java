package com.palace.dd.test;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.palace.dd.db.MainSharding;

public class DBTest {

	DataSource ds;
	JdbcTemplate tmp;
	long lBorrowerId = 18010421;
	long lBorrowIntentId = 180100000718l;
	@Before
	public void bef() throws Exception {
		Hosts.host();
		ds = MainSharding.getShardingDataSource();
		tmp  = new JdbcTemplate(ds);
	}
	@Test
	public void dbTest() throws Exception {
		String sql ="SELECT * from tbBorrowIntent where lBorrowerId = ? and lId = ?";
		Map<String,Object> map = tmp.queryForMap(sql,lBorrowerId,lBorrowIntentId);
		System.out.println(map.get("lBorrowerId"));
	}
	@Test
	public void billTest() {
		String sql =" select * from tbBorrowerBill  where lBorrowerId = ? limit 1";
		Map<String,Object> map = tmp.queryForMap(sql,lBorrowerId);
		System.out.println(map.get("lId"));
	}
	
	@Test
	public void dbJoinTest() {
		String sql =" select bill.* from tbBorrowIntent bo "
				+ "		join tbBorrowerBill bill on bo.lId = bill.lBorrowIntentId "
				+ "		where bo.lBorrowerId = ? and bo.lId = ? limit 1";
		sql ="select * from tbBorrowIntent bo " + 
				"					left join tbBorrowerBill bill on bo.lId = bill.lBorrowIntentId " + 
				"						where bo.lBorrowerId = ? and bo.lId = ? limit 1";
		long ll = (long) tmp.queryForMap(sql,lBorrowerId,lBorrowIntentId).getOrDefault("lId", 0);
		System.out.println(ll);
	}
	
}
