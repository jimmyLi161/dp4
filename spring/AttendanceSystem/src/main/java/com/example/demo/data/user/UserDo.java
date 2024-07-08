package com.example.demo.data.user;

import com.example.demo.data.employee.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户类，用于数据库操作
 * @author 冯昱中
 * @since 1.0
 * @version 1.0
 * @see User
 */
public class UserDo extends User implements RowMapper<User>
{
    private String password;
    public UserDo()
    {
        super();
    }


    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        UserDo userDo = new UserDo();
        userDo.setId(rs.getLong("id"));
        userDo.setName(rs.getString("name"));
        userDo.setPassword(rs.getString("password"));
        userDo.setAuth(rs.getInt("auth"));
        userDo.setType(rs.getInt("userType"));
        return userDo;
    }
}
