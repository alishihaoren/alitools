package com.zn.learn.basic.ExpressionEngine;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    private static final String timePatten1 = "yyyy-MM-dd'T'HH:mm:ss Z";
    public static final String INFLUXDB_TIME_PATTEN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String INFLUXDB_TIME_MILLI_PATTEN = "yyyy-MM-dd'T'HH:mm:ss.S'Z'";
    public static final String STANDARD_TIME_PATTEN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String TDV2_TIME_PATTEN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat(STANDARD_TIME_PATTEN);

    private Long UtcTimeZone2TimeStamp(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(INFLUXDB_TIME_PATTEN);
        return sdf.parse(dateStr).getTime();
    }

    public static synchronized Long influxDBDate2MIlLITimeStamp(String UTCStr) throws ParseException {
        Date date = null;
        DateFormat ft = new SimpleDateFormat(INFLUXDB_TIME_MILLI_PATTEN);
        date = ft.parse(UTCStr);
        return date.getTime();
    }

    public static synchronized String TDDate2MIlLITimeStamp(String GMTStr) throws ParseException {
        Date date = null;
        DateFormat ft = SIMPLEDATEFORMAT;
        date = ft.parse(GMTStr);
        return String.valueOf(date.getTime());
    }

    public static synchronized Long influxDBDate2TimeStamp(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(INFLUXDB_TIME_PATTEN);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.parse(dateStr).getTime();
    }

    /**
     * 日期转时间戳
     *
     * @param dataStr 日期，格式：yyyy-MM-dd HH:mm:ss.SSS
     * @return
     * @throws ParseException
     */
    public static synchronized Long TDV2Date2Timestamp(String dataStr) throws ParseException {
        if (dataStr.contains(":")) {
            SimpleDateFormat sdf = new SimpleDateFormat(TDV2_TIME_PATTEN);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            return sdf.parse(dataStr).getTime();
        }
        return Long.valueOf(dataStr);
    }

    /**
     * 日期转时间戳
     *
     * @param dataStr 日期，格式：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static synchronized Long TDV2Date2Timestamp1(String dataStr) throws ParseException {
        if (dataStr.contains(":")) {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            return sdf.parse(dataStr).getTime();
        }
        return Long.valueOf(dataStr);
    }

    public static synchronized String timestamp2DateString(Long timestamp, String pattern) {
        SimpleDateFormat sdf8 = new SimpleDateFormat(pattern);
        return sdf8.format(new Date(timestamp));
    }

    public static synchronized String timestamp2CST(Long timestamp) {
        SimpleDateFormat sdf8 = SIMPLEDATEFORMAT;
        sdf8.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//设置时区为东八区
        return sdf8.format(new Date(timestamp));
    }

    public static synchronized Date CST2Timestamp(String timestamp) throws Exception {
        SimpleDateFormat sdf8 = SIMPLEDATEFORMAT;
        sdf8.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//设置时区为东八区
        return sdf8.parse(timestamp);
    }

    public static synchronized String CST2DateString(String date) throws Exception {
        return timestamp2CST(CST2Timestamp(date).getTime());
    }

    public static boolean dataFormateLegal(String dateStr, String dataformatPatten) {
        SimpleDateFormat sdf = new SimpleDateFormat(dataformatPatten);
        try {
            Date legalData = sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean dateBiggerCompare(String startTimeStr, String endTimeStr, String datePatten) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(datePatten);
        Date startDateStr = sdf.parse(startTimeStr);
        Date endDateStr = sdf.parse(endTimeStr);
        return startDateStr.before(endDateStr) ? true : false;
    }

    public static Long parseDateToLong(SimpleDateFormat sf, String dateStr) {
        try {
            Date parse = sf.parse(dateStr);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
//            throw new BusinessException(CommonConstants.GlobalErrorCode.PARAM_PARSE_ERROR);
            return  0L;
        }
    }

    public static synchronized Long TDV2Date2TimeStamp(String dataStr) throws ParseException {
        if (dataStr.contains("T")) {
            SimpleDateFormat sdf = SIMPLEDATEFORMAT;
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            return sdf.parse(dataStr).getTime();
        }
        if (dataStr.contains(":")) {
            SimpleDateFormat sdf = new SimpleDateFormat(TDV2_TIME_PATTEN);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            return sdf.parse(dataStr).getTime();
        }

        return Long.valueOf(dataStr);
    }

    public static synchronized String timeStamp2CST(Long timeStamp) {
        SimpleDateFormat sdf8 = SIMPLEDATEFORMAT;
        sdf8.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//设置时区为东八区
        return sdf8.format(new Date(timeStamp));
    }

    public static synchronized Date CST2TimeStamp(String timeStamp) throws Exception {
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf8.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//设置时区为东八区
        return sdf8.parse(timeStamp);
    }

    public static synchronized Date CST2TimeStampByString(String timeStamp) {
        try {
            SimpleDateFormat sdf8 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            sdf8.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//设置时区为东八区
            return sdf8.parse(timeStamp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得当前日期
     *
     * @return
     */
    public static synchronized Date getNow() {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();
        return currDate;
    }


    /**
     * 日期转换为字符串 格式自定义
     *
     * @param date
     * @param f
     * @return
     */
    public static synchronized String dateStr(Date date, String f) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(f);
        String str = format.format(date);
        return str;
    }


    /**
     * 日期转换为字符串 格式自定义
     *
     * @param date
     * @param f
     * @return
     */
    public static synchronized Date dateFrom(Date date, String f) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(f);
        String str = format.format(date);
        return date;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static synchronized String dateStr1(Date date) {
        return dateStr(date, "yyyy-MM-dd HH:mm:ss");

    }

    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date
     * @return
     */
    public static synchronized String dateStr2(Date date) {
        return dateStr(date, TDV2_TIME_PATTEN);

    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static synchronized String dateStr3(Date date) {
        return dateStr(date, "yyyy-MM-dd");

    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static synchronized String dateStrByLongTime(Long time) {
        Date date = new Date(time);
        return dateStr(date, YYYYMMDDHHMMSS);

    }

    /**
     * yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String dateStr7(Date date) {
        return dateStr(date, "yyyyMMdd");
    }

    /**
     * 获取yyyy-MM-dd的格式时间
     *
     * @param dateStr
     * @return
     * @throws
     */
    public static Date getDateByDateStr(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取yyyy-MM-dd的格式时间
     *
     * @param dateStr
     * @return
     * @throws
     */
    public static Date getDateByDateStr1(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取yyyy-MM-dd的格式时间
     *
     * @param dateStr
     * @return
     * @throws
     */
    public static Date getDateByDateStr2(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 前/后?分秒
     */
    public static Date rollSecond(Date d, Long minute) {
        return new Date((long) (d.getTime() + (minute * 1000)));
    }

    /**
     * 前/后?分秒
     */
    public static Date rollSecond(Date d, int minute) {
        return new Date((long) (d.getTime() + (minute * 1000)));
    }

    /**
     * 前/后?分钟
     */
    public static Date rollMinute(Date d, Double minute) {
        return new Date((long) (d.getTime() + minute * 60 * 1000));
    }

    /**
     * 前/后?分钟
     */
    public static Date rollMinute(Date d, int minute) {
        return new Date((long) (d.getTime() + minute * 60 * 1000));
    }

    /**
     * 前/后?小时
     */
    public static Date rollHours(Date d, int hour) {
        return new Date(d.getTime() + hour * 60 * 60 * 1000);
    }


    /**
     * 前/后?天
     */
    public static Date rollDay(Date d, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    /**
     * 前/后?月
     */
    public static Date rollMon(Date d, int mon) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MONTH, mon);
        return cal.getTime();
    }

    /**
     * 前/后?年
     */
    public static Date rollYear(Date d, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    /**
     * 计算两个时间相差多少个年
     *
     * @return
     * @throws ParseException
     */
    public static int yearsBetween(String start, String end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(sdf.parse(start));
        endDate.setTime(sdf.parse(end));
        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));
    }

    /**
     * 计算两个时间相差多少个年
     *
     * @return
     * @throws ParseException
     */
    public static int yearsBetween(long start, long end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(sdf.parse(start + ""));
        endDate.setTime(sdf.parse(end + ""));
        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));
    }


    /**
     * 获取两个日期相差的月数
     *
     * @param d1 较大的日期
     * @param d2 较小的日期
     * @return 如果d1>d2返回 月数差 否则返回0
     */
    public static int monthsBetween(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) {return 0;};
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 计算date2 - date1之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(dateStr7(date1));
            Date d2 = sdf.parse(dateStr7(date2));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf((time2 - time1) / 86400000L));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 计算date2 - date1之间相差的天数
     *
     * @param startDt
     * @param endDt
     * @return
     */
    public static int daysBetween(String startDt, String endDt) {
        Date date1 = getDateByDateStr(startDt);
        Date date2 = getDateByDateStr(endDt);
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(dateStr7(date1));
            Date d2 = sdf.parse(dateStr7(date2));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf((time2 - time1) / 86400000L));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算date2 - date1之间相差的分钟
     *
     * @param date1
     * @param date2
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int minutesBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        // date1.setSeconds(0);
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        if (time2 - time1 <= 0) {
            return 0;
        } else {
            return Integer.parseInt(String.valueOf((time2 - time1) / 60000L)) + 1;
        }

    }

    /**
     * 计算date2 - date1之间相差的秒
     *
     * @param date1
     * @param date2
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int secondBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        // date1.setSeconds(0);
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        if (time2 - time1 <= 0) {
            return 0;
        } else {
            return Integer.parseInt(String.valueOf((time2 - time1) / 1000L)) + 1;
        }
    }

    /**
     * 计算date2 - date1之间相差的毫秒
     *
     * @param date1
     * @param date2
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int millisecondBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        if (time2 - time1 <= 0) {
            return 0;
        } else {
            return Integer.parseInt(String.valueOf((time2 - time1)));
        }
    }

    public static List<Long> getTimeLong(String startTime, String endTime, String interval) throws Exception {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(interval);
        Long timeDimension = Long.valueOf(matcher.replaceAll("").trim());
        //单位
        String unit = interval.replace(timeDimension + "", "");
        switch (unit) {
            case "s":
                timeDimension = timeDimension * 1000;
                break;
            case "m":
                timeDimension = timeDimension * (60 * 1000);
                break;
            case "h":
                timeDimension = timeDimension * (60 * 60 * 1000);
                break;
            case "d":
                timeDimension = timeDimension * (24 * 60 * 60 * 1000);
                break;
            case "w":
                timeDimension = timeDimension * (7 * 24 * 60 * 60 * 1000);
                break;
            case "M":
                timeDimension = timeDimension * (30 * 24 * 60 * 60 * 1000);
                break;
            case "y":

                timeDimension = timeDimension * (365 * 24 * 60 * 60 * 1000);
                break;
            default:
                break;
        }

        long sTime = CST2TimeStamp(startTime).getTime();
        long eTime = CST2TimeStamp(endTime).getTime();
        long count = (eTime - sTime) / timeDimension;
        List<Long> timeList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            timeList.add(sTime + (i * timeDimension));
        }
        return timeList;
    }

    /**
     * 获取时间间隔数
     *
     * @param interval
     * @return
     * @throws ParseException
     */
    public static Long getTimeLongByInterval(String interval) throws ParseException {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(interval);
        Long timeDimension = Long.valueOf(matcher.replaceAll("").trim());
        //单位
        String unit = interval.replace(timeDimension + "", "");
        switch (unit) {
            case "s":
                timeDimension = timeDimension * 1000;
                break;
            case "m":
                timeDimension = timeDimension * (60 * 1000);
                break;
            case "h":
                timeDimension = timeDimension * (60 * 60 * 1000);
                break;
            case "d":
                timeDimension = timeDimension * (24 * 60 * 60 * 1000);
                break;
            case "w":
                timeDimension = timeDimension * (7 * 24 * 60 * 60 * 1000);
                break;
            case "M":
                timeDimension = timeDimension * (30 * 24 * 60 * 60 * 1000);
                break;
            case "y":

                timeDimension = timeDimension * (365 * 24 * 60 * 60 * 1000);
                break;
            default:
                break;
        }
        return timeDimension;
    }

    /**
     * 获取时间单位
     *
     * @param time
     * @return
     */
    public static String getDateUnit(String time) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(time);
        Long timeDimension = Long.valueOf(matcher.replaceAll("").trim());
        //单位
        String unit = time.replace(timeDimension + "", "");
        return unit;
    }

    /**
     * 获取时间数字
     *
     * @param
     * @return
     */
    public static Long getDateNum(String time) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(time);
        Long timeDimension = Long.valueOf(matcher.replaceAll("").trim());
        return timeDimension;
    }

    /**
     * 前/后?年
     */
    public static String analysis(String dateStr) {
        String[] date = dateStr.split("-");
        int time = Integer.valueOf(date[0]);
        String unit = date[1].toUpperCase();
        //解析时间
        StringBuilder str = new StringBuilder(time);
        //有效周期
        if ("Y".equals(unit)) {
            str.append("年");
        } else if ("M".equals(unit)) {
            str.append("月");
        } else if ("D".equals(unit)) {
            str.append("日");
        } else {
//            throw new BusinessException("时间格式错误");
        }
        return str.toString();
    }


    /**
     * 解析 1-D 的时间
     *
     * @param dateStr
     * @return
     */
    public static String dateStr5(String dateStr) {
        String[] strings = dateStr.split("-");
        int time = Integer.valueOf(strings[0]);
        String unit = strings[1].toUpperCase();
        //解析时间
        Date date = new Date();
        //有效周期
        if ("Y".equals(unit)) {
            date = DateUtils.rollYear(new Date(), -time);
        } else if ("M".equals(unit)) {
            date =DateUtils.rollMon(new Date(), -time);
        } else if ("D".equals(unit)) {
            date = rollDay(new Date(), -time);
        } else if ("H".equals(unit)) {
            date = rollHours(new Date(), -time);
        } else if ("MI".equals(unit)) {
            date = rollMinute(new Date(), -time);
        } else if ("S".equals(unit)) {
            date = rollSecond(new Date(), -time);
        }
        return dateStr3(date);
    }

    /**
     * 解析 1-D 的时间
     *
     * @param dateStr
     * @return
     */
    public static long dateStr6(String dateStr) {
        String[] strings = dateStr.split("-");
        int time = Integer.valueOf(strings[0]);
        String unit = strings[1].toUpperCase();
        //解析时间
        Date date = new Date();
        //有效周期
        if ("Y".equals(unit)) {
            date = rollYear(new Date(), -time);
        } else if ("M".equals(unit)) {
            date = rollMon(new Date(), -time);
        } else if ("D".equals(unit)) {
            date = rollDay(new Date(), -time);
        } else if ("H".equals(unit)) {
            date = rollHours(new Date(), -time);
        } else if ("MI".equals(unit)) {
            date = rollMinute(new Date(), -time);
        } else if ("S".equals(unit)) {
            date = rollSecond(new Date(), -time);
        }
        return date.getTime();
    }

    /**
     * localDateTime 转 date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 解析 1-D 的时间
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }


    /**
     * yyyy-MM-dd HH:mm:ss 转东八区时间戳
     *
     * @param date
     * @return
     */
    public static Long dateTimeToCST2Timestamp(String date) throws Exception {
        String dateStr = "";

        try {
            if (date.indexOf(".000") > -1) {
                String[] ss1 = date.split(" ");
                dateStr = ss1[0] + "T" + ss1[1] + "+08:00";
            } else if (date.indexOf(".00") > -1) {
                String[] ss1 = date.split(" ");
                dateStr = ss1[0] + "T" + ss1[1] + "0+08:00";
            } else if (date.indexOf(".0") > -1) {
                String[] ss1 = date.split(" ");
                dateStr = ss1[0] + "T" + ss1[1] + "00+08:00";
            } else if (date.indexOf("T") > -1 && date.indexOf("+08:00") > -1) {
                dateStr = date;
            } else if (date.indexOf(" ") > -1) {
                String[] ss1 = date.split(" ");
                dateStr = ss1[0] + "T" + ss1[1] + ".000+08:00";
            }
        } catch (Exception e) {
            dateStr = date;
        }


        return CST2Timestamp(dateStr).getTime();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(timestamp2DateString(System.currentTimeMillis(), TDV2_TIME_PATTEN));

        String ss = "2022-08-31T00:00:00.000";
        ss = "2022-08-24T17:42:32.531+08:00";

        System.out.println(dateTimeToCST2Timestamp(ss));


//        System.out.println(dateStr5("1-y"));
    }
}
