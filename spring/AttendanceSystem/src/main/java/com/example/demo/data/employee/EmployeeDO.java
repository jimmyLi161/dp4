package com.example.demo.data.employee;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author 毕凯斌
 * @since 1.0.0
 * @version 1.1.0
 * 修改数据库，新增员工生日、性别等信息
 */
public class EmployeeDO extends Employee implements RowMapper<Employee> {
    public EmployeeDO(){
        super();
    }
    @Override
    public Employee mapRow(ResultSet rs, int i) throws SQLException {
        Employee employeeDO = new EmployeeDO();
        employeeDO.setEmpID(rs.getLong("empId"));
        employeeDO.setEmpName(rs.getString("empName"));
        employeeDO.setBirthday(rs.getDate("birthday"));
        employeeDO.setGetJobDay(rs.getDate("getJobDay"));
        employeeDO.setGender(rs.getString("gender"));
        employeeDO.setDeptID(rs.getLong("deptId"));
        employeeDO.setDeptName(rs.getString("deptName"));
        employeeDO.setAuth(rs.getInt("auth"));
        return employeeDO;
    }
}
