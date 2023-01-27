package ml.stargirls.maia.paper.notifier;

import ml.stargirls.maia.paper.notifier.notification.Notification;
import ml.stargirls.message.MessageHandler;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to notify a message to multiple recipients at the same time.
 */
public interface MessageNotifier
	extends MessageHandler {

	/**
	 * Notify a message to multiple recipients.
	 *
	 * @param notification
	 * 	the notification to send
	 */
	void sendNotification(@NotNull Notification notification);
}
