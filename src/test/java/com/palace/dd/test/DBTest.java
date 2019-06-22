package com.palace.dd.test;

import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.palace.dd.db.MainSharding;

public class DBTest {

	@Before
	public void bef() {
		Hosts.host();
	}
	@Test
	public void userTest() throws Exception {
		DataSource ds = MainSharding.getShardingDataSource();
		String sql ="SELECT * from tbBorrowIntent where lBorrowerId = 18010421 and lId = 180100000718";
		JdbcTemplate tmp = new JdbcTemplate(ds);
		Map<String,Object> map = tmp.queryForMap(sql);
		System.out.println(map.get("lBorrowerId"));
	}
}
