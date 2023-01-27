package ml.stargirls.maia.paper.inject;

import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import ml.stargirls.maia.inject.ProtectedModule;
import ml.stargirls.maia.paper.concurrent.PluginExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class PluginModule
	extends ProtectedModule {
	private final InjectedPlugin plugin;

	public PluginModule(@NotNull InjectedPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void configure() {
		bind(Plugin.class).toInstance(plugin);
		bind(PluginExecutor.class).in(Scopes.SINGLETON);
		plugin.configure(binder());
	}

	@Provides
	@Singleton
	@NotNull
	public Logger provideLogger() {
		return plugin.getSLF4JLogger();
	}
}
