package com.example.demo.webservice.service.attendance;

import com.example.demo.data.attendance.Attendance;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.List;
/**
 * @author 李璟昕
 * @since 1.0.0
 */
public interface AttendanceService {
    List<Attendance> getAllAttendance();
    List<Attendance> getAttendanceByID(Long empID, Long attendanceID);
    ResponseEntity importAttendance(File file);
    ResponseEntity deleteAttendance(long attendanceID);
}
