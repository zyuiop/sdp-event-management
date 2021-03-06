package ch.epfl.sweng.eventmanager.notifications;

import android.app.Notification;
import android.content.Context;
import ch.epfl.sweng.eventmanager.repository.data.Event;

/**
 * Alerts the user a day after the event ended for him to give his feedback on the event
 */
public class JoinedEventFeedbackStrategy extends NotificationStrategy<Event> {
    private static final long ONE_DAY = 86_400_000; //24H in millis
    private static final String descriptionText = "Rate this event !";

    public JoinedEventFeedbackStrategy(Context context) {
        super(context);
    }

    @Override
    void scheduleNotification(Event event) {
        // get Notification based on scheduled item
        Notification notification = getNotificationFromEvent(event);

        long timeUntil = SchedulerHelper.getTimeTo(event.getEndDateAsDate()) + ONE_DAY;

        if (timeUntil >= 0)
            SchedulerHelper.scheduleNotification(context, event.getId(), notification, SchedulerHelper.getTimeTo(event.getEndDateAsDate()) + ONE_DAY);
    }

    @Override
    void unscheduleNotification(Event event) {
        SchedulerHelper.unscheduleNotification(context, event.getId());
    }

    private Notification getNotificationFromEvent(Event event) {
        return NotificationBuilder.getFeedbackNotificationFrom(context, event.getName(), descriptionText, event.getId());
    }
}
