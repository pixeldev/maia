package ml.stargirls.maia.paper.notifier;

import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import ml.stargirls.maia.inject.ProtectedModule;
import ml.stargirls.maia.paper.notifier.notification.Notification;
import ml.stargirls.maia.paper.notifier.redis.NotificationChannelListener;
import ml.stargirls.storage.redis.channel.RedisChannel;
import ml.stargirls.storage.redis.messenger.RedisMessenger;

public class MessageNotifierModule
	extends ProtectedModule {
	@Override
	public void configure() {
		bind(MessageNotifier.class)
			.annotatedWith(Names.named("local"))
			.to(LocalMessageNotifier.class)
			.in(Scopes.SINGLETON);

		bind(MessageNotifier.class)
			.to(GlobalMessageNotifier.class)
			.in(Scopes.SINGLETON);
	}

	@Provides
	@Singleton
	public RedisChannel<Notification> get(
		RedisMessenger messenger,
		NotificationChannelListener listener
	) {
		return messenger.getChannel("notification", Notification.class)
			       .addListener(listener);
	}
}
