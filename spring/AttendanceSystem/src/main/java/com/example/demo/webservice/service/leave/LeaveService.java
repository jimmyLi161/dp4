package com.example.demo.webservice.service.leave;

import com.example.demo.data.leave.Leave;
import org.springframework.http.ResponseEntity;

import java.util.List;
/**
 * @author 李璟昕
 * @since 1.0.0
 */
public interface LeaveService {
    List<Leave> getAllLeaveInfo();
    List<Leave> getLeaveInfoByEmpID(Long empID, Long appID);
    Leave addLeaveInfo(Leave leaveVO);
    Leave editLeaveInfo(Leave leaveVO);
    ResponseEntity deleteLeaveInfo(long appID);
}
