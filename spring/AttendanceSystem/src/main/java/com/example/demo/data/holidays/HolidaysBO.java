package com.example.demo.data.holidays;
/**
 * @author 毕凯斌
 * @since 1.0.0
 * @version 1.1.0
 * 修改数据库，假期修改为病假、事假、年假等种类
 */
public class HolidaysBO extends Holidays{
    public HolidaysBO(){
        super();
    }
    public static Holidays convert(Holidays holidays){
        Holidays holidaysInfo = new HolidaysBO();
        holidaysInfo.setEmpID(holidays.getEmpID());
        holidaysInfo.setYearHolidays(holidays.getYearHolidays());
        holidaysInfo.setPrivateHolidays(holidays.getPrivateHolidays());
        holidaysInfo.setSickHolidays(holidays.getSickHolidays());
        return holidaysInfo;
    }
}
