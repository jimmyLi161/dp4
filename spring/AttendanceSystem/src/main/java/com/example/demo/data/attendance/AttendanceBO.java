package com.example.demo.data.attendance;

/**
 * @author 毕凯斌
 * @since 1.0.0
 */

public class AttendanceBO extends Attendance
{
    public AttendanceBO()
    {
        super();
    }
    public static Attendance convert(Attendance attendance){
        Attendance attendanceInfo = new AttendanceBO();
        attendanceInfo.setEmpID(attendance.getEmpID());
        attendanceInfo.setAttendanceID(attendance.getAttendanceID());
        attendanceInfo.setClockTime(attendance.getClockTime());
        return attendanceInfo;
    }
}
