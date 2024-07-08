package com.example.demo.data.holidays;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author 毕凯斌
 * @since 1.0.0
 * @version 1.1.0
 * 修改数据库，假期修改为病假、事假、年假等种类
 */
public class HolidaysDO extends Holidays implements RowMapper<Holidays> {
    public HolidaysDO(){
        super();
    }
    public static Holidays convert(Holidays holidays){
        Holidays holidaysInfo = new HolidaysDO();
        holidaysInfo.setEmpID(holidays.getEmpID());
        holidaysInfo.setYearHolidays(holidays.getYearHolidays());
        holidaysInfo.setPrivateHolidays(holidays.getPrivateHolidays());
        holidaysInfo.setSickHolidays(holidays.getSickHolidays());
        return holidaysInfo;
    }
    @Override
    public Holidays mapRow(ResultSet rs, int i) throws SQLException {
        Holidays holidaysDo = new HolidaysDO();
        holidaysDo.setEmpID(rs.getLong("empId"));
        holidaysDo.setYearHolidays(rs.getInt("yearHoliday"));
        holidaysDo.setPrivateHolidays(rs.getInt("privateHoliday"));
        holidaysDo.setSickHolidays(rs.getInt("sickHoliday"));
        return holidaysDo;
    }
}
