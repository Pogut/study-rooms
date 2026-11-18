package rooms;

import java.time.Duration;
import java.time.LocalDateTime;

/** A booking can be cancelled until a set number of minutes before it starts. */
public class NoticeCancellationPolicy implements CancellationPolicy {
    private final long noticeMinutes;

    public NoticeCancellationPolicy(long noticeMinutes) {
        this.noticeMinutes = noticeMinutes;
    }

    @Override
    public boolean canCancel(Booking booking, LocalDateTime now) {
        return Duration.between(now, booking.getStart()).toMinutes() >= noticeMinutes;
    }
}
