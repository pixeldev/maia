package ml.stargirls.maia.paper.inject;

import com.google.gson.Gson;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ml.stargirls.maia.inject.ProtectedModule;
import ml.stargirls.maia.server.ServerInfo;
import ml.stargirls.storage.redis.connection.JedisInstance;
import ml.stargirls.storage.redis.messenger.RedisMessenger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class RedisMessengerModule
	extends ProtectedModule {
	private final String parentChannel;

	public RedisMessengerModule(@NotNull String parentChannel) {
		this.parentChannel = parentChannel;
	}

	@Provides
	@Singleton
	public RedisMessenger provideMessenger(
		@NotNull final Executor executor,
		@NotNull final Gson gson,
		@NotNull final ServerInfo serverInfo,
		@NotNull final JedisInstance jedisInstance
	) {
		return new RedisMessenger(
			parentChannel,
			serverInfo.serverIdentifier(),
			executor,
			gson,
			jedisInstance
		);
	}
}
