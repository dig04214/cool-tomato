package com.wp.chatbot.global.util;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    public static LocalDateTime stringToLocalDateTime(String date){
        if(date.length() == 10) date+=" 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date,formatter);
        return localDateTime;
    }
}
