package com.example.demo.webservice.service.employee;

import com.example.demo.data.employee.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;
/**
 * @author 李璟昕
 * @since 1.0.0
 */
public interface EmployeeService {
    List<Employee> getAllEmployeeInfo();
    Employee getEmployeeInfoByEmpID(long empID);
    Employee addEmployeeInfo(Employee employee);
    Employee editEmployeeInfo(Employee employee);
    ResponseEntity deleteEmployeeInfo(long empID);
    ResponseEntity<byte[]> getAttendanceReport();
}
