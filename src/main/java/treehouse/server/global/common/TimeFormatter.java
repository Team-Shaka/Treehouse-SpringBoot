package treehouse.server.global.common;

import java.time.Duration;
import java.time.LocalDateTime;

// 특정 게시글/댓글의 작성 시간을 현재 시간과 비교하여 문자열로 변환하는 유틸리티 클래스
public class TimeFormatter {

    private TimeFormatter() {
    }

    public static String format(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        long seconds = duration.getSeconds();
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (seconds < 60) {
            return seconds + "초 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else {
            return days + "일 전";
        }
    }
}
