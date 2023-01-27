package ml.stargirls.maia.paper.notifier;

import ml.stargirls.maia.paper.notifier.notification.Notification;
import ml.stargirls.storage.redis.channel.RedisChannel;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class GlobalMessageNotifier
	extends LocalMessageNotifier {
	@Inject private RedisChannel<Notification> globalMessageChannel;

	@Override
	public void sendNotification(@NotNull Notification notification) {
		if (internalSend(notification)) {
			return;
		}

		globalMessageChannel.sendMessage(notification);
	}
}
