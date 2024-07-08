package com.example.demo.data.holidays;
/**
 * @author 毕凯斌
 * @since 1.0.0
 * @version 1.1.0
 * 修改数据库，假期修改为病假、事假、年假等种类
 */
public class HolidaysVO extends Holidays{
    public HolidaysVO(){
        super();
    }
    public static Holidays convert(Holidays holidays){
        Holidays holidaysInfo = new HolidaysVO();
        holidaysInfo.setEmpID(holidays.getEmpID());
        holidaysInfo.setYearHolidays(holidays.getYearHolidays());
        holidaysInfo.setPrivateHolidays(holidays.getPrivateHolidays());
        holidaysInfo.setSickHolidays(holidays.getSickHolidays());
        return holidaysInfo;
    }
    public static Object[] HolidaysList(Holidays holidays){
        Object[] holidaysList = {holidays.getYearHolidays(), holidays.getPrivateHolidays(),
                holidays.getSickHolidays(), holidays.getEmpID()};
        return holidaysList;
    }
}
