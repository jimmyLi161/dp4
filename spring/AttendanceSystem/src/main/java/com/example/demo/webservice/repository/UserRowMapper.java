package com.example.demo.webservice.repository;

import com.example.demo.data.user.UserDo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 魏金旭
 * @author 冯昱中
 * @since 1.0
 * @version 1.1
 * 删除多余属性
 */
@Component
public class UserRowMapper implements RowMapper<UserDo>
{
    @Override
    public UserDo mapRow(ResultSet rs, int i) throws SQLException
    {
        UserDo userDo = new UserDo();
        userDo.setId(rs.getLong("id"));
        userDo.setName(rs.getString("name"));
        userDo.setPassword(rs.getString("password"));
        userDo.setAuth(rs.getInt("auth"));
        userDo.setType(rs.getInt("userType"));
        return userDo;
    }
}