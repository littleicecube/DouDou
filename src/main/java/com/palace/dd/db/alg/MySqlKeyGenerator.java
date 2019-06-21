package com.palace.dd.db.alg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import io.shardingjdbc.core.keygen.KeyGenerator;

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
    public synchronized Number generateKey() {
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (Exception e) {
            return System.currentTimeMillis();
        }
        return System.currentTimeMillis();
    }
}
