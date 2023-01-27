package ml.stargirls.maia.paper.notifier.redis;

import ml.stargirls.maia.paper.notifier.MessageNotifier;
import ml.stargirls.maia.paper.notifier.notification.Notification;
import ml.stargirls.storage.redis.channel.RedisChannel;
import ml.stargirls.storage.redis.channel.RedisChannelListener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;

@ApiStatus.Internal
public class NotificationChannelListener
	implements RedisChannelListener<Notification> {
	@Inject
	@Named("local")
	private MessageNotifier messageNotifier;

	@Override
	public void listen(
		@NotNull RedisChannel<Notification> channel,
		@NotNull String server,
		@NotNull Notification object
	) {
		messageNotifier.sendNotification(object);
	}
}
