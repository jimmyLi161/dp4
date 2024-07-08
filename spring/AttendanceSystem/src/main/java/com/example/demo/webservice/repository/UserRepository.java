package com.example.demo.webservice.repository;

import com.example.demo.data.user.UserDo;
import com.example.demo.data.user.User;
import com.example.demo.data.user.UserBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据库仓库
 * @author 魏金旭
 * @author 冯昱中
 * @since 1.0
 * @version 1.0
 */
@Repository
public class UserRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("userRowMapper")
    private RowMapper<UserDo> rowMapper;

    public UserDo queryUserById(long id)
    {
        String sql = "SELECT * FROM `user` WHERE id=? ";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<UserDo> queryAllUser()
    {
        String sql = "SELECT * FROM `user`";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void insertUser(UserBo user)
    {
        String sql = "INSERT INTO `user` (id, `name`, password , auth, userType) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword(),user.getAuth(),user.getType());
    }

    public User addUserInfo(User user){
        String sql = "INSERT INTO `user` (id, `name`, password , auth, userType) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), 123456, user.getAuth(), user.getType());
        return getUserInfoByUserID(user.getId());
    }

    public User getUserInfoByUserID(long userID){
        String sql = "select * from user where id = ?";
        return jdbcTemplate.queryForObject(sql, new UserDo(), userID);
    }

    public UserDo updateUserById(long id, UserBo user)
    {
        String sql = "UPDATE `user` set `name`= ? ,password = ?,auth = ?,userType= ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getAuth(),user.getType(), id);
        return queryUserById(id);
    }

    public void deleteUserById(long id)
    {
        String sql = "DELETE FROM `user` WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
