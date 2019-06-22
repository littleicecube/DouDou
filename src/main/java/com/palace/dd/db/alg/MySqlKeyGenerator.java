package com.palace.dd.db.alg;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * <p></p>
 * Created by @author zhezhiyong@163.com on 2018/12/26.
 */
public class MySqlKeyGenerator implements KeyGenerator {

    private DataSource dataSource;
    private String sql = "insert into t_generate_key()values();";

    public MySqlKeyGenerator() {
    }


	@Override
	public Object generate(Object target, Method method, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
}
