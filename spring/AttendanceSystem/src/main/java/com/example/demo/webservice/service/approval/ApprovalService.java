package com.example.demo.webservice.service.approval;

import com.example.demo.data.approval.Approval;
import org.springframework.http.ResponseEntity;

import java.util.List;
/**
 * @author 李璟昕
 * @since 1.0.0
 * @version 1.1.0
 * 新增获取员工权限的函数
 */
public interface ApprovalService {
    List<Approval> getAllApproval();
    List<Approval> getApprovalByID(Long empID, Long appID);
    Approval addApproval(Approval approval);
    Approval editApproval(Approval approval);
    ResponseEntity deleteApproval(long appID, long empID);
    int getApprovalType(Approval approval);
}
