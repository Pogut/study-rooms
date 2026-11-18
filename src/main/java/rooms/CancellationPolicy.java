package rooms;

import java.time.LocalDateTime;

/** Decides whether a booking can still be cancelled. */
public interface CancellationPolicy {
    boolean canCancel(Booking booking, LocalDateTime now);
}
