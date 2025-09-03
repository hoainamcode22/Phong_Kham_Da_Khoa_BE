package com.example.phong_kham_da_khoa.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/** Tiện ích thời gian dùng chung (mặc định múi giờ VN). */
public final class DateUtils {

    private DateUtils() {}

    public static final ZoneId VN_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");
    public static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter ISO_DATETIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // ===== Now & zone helpers =====
    public static LocalDateTime nowVnDateTime() { return LocalDateTime.now(VN_ZONE); }
    public static LocalDate nowVnDate() { return LocalDate.now(VN_ZONE); }
    public static ZonedDateTime toVn(LocalDateTime ldt) { return ldt.atZone(VN_ZONE); }

    // ===== Parse / Format =====
    public static LocalDate parseIsoDate(String s) { return LocalDate.parse(s, ISO_DATE); }
    public static LocalDateTime parseIsoDateTime(String s) { return LocalDateTime.parse(s, ISO_DATETIME); }
    public static String formatIsoDate(LocalDate d) { return d.format(ISO_DATE); }
    public static String formatIsoDateTime(LocalDateTime dt) { return dt.format(ISO_DATETIME); }

    // ===== Range helpers =====
    public static LocalDateTime startOfDay(LocalDate d) { return d.atStartOfDay(); }
    public static LocalDateTime endOfDay(LocalDate d) { return d.atTime(LocalTime.MAX).withNano(0); }

    /** Kiểm tra 2 khoảng [start1,end1) và [start2,end2) có overlap không. */
    public static boolean isOverlap(LocalDateTime s1, LocalDateTime e1, LocalDateTime s2, LocalDateTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }

    /** Cộng tháng an toàn (nếu ngày vượt quá cuối tháng thì lùi về cuối tháng). */
    public static LocalDate addMonthsSafe(LocalDate date, int months) {
        LocalDate tmp = date.plusMonths(months);
        int lastDay = tmp.lengthOfMonth();
        int day = Math.min(date.getDayOfMonth(), lastDay);
        return LocalDate.of(tmp.getYear(), tmp.getMonth(), day);
    }
}
