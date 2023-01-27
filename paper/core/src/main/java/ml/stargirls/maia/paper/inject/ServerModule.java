package ml.stargirls.maia.paper.inject;

import com.google.inject.AbstractModule;
import com.mongodb.client.MongoClient;
import ml.stargirls.maia.inject.ProtectedBinder;
import ml.stargirls.maia.server.ServerInfo;
import ml.stargirls.storage.redis.connection.JedisInstance;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerModule
	extends AbstractModule {
	private final Plugin[] plugins;
	private final int threads;
	private final String serverIdentifier;
	private final JedisInstance jedisInstance;
	private final MongoClient mongoClient;

	public ServerModule(
		@NotNull Plugin[] plugins,
		int threads,
		@NotNull String serverIdentifier,
		@NotNull JedisInstance jedisInstance,
		@NotNull MongoClient mongoClient
	) {
		this.plugins = plugins;
		this.threads = threads;
		this.serverIdentifier = serverIdentifier;
		this.jedisInstance = jedisInstance;
		this.mongoClient = mongoClient;
	}

	@Override
	public void configure() {
		bind(Executor.class).toInstance(Executors.newFixedThreadPool(threads));
		bind(ServerInfo.class).toInstance(new ServerInfo(serverIdentifier));
		bind(JedisInstance.class).toInstance(jedisInstance);
		bind(MongoClient.class).toInstance(mongoClient);

		for (Plugin plugin : plugins) {
			if (plugin instanceof InjectedPlugin injectedPlugin) {
				ProtectedBinder.create(binder())
					.install(new PluginModule(injectedPlugin));
			}
		}
	}
}
