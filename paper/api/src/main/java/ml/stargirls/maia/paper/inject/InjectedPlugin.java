package ml.stargirls.maia.paper.inject;

import ml.stargirls.maia.inject.ProtectedBinder;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a plugin which can be injected by the current {@link com.google.inject.Injector}
 * instance.
 * <p>
 * It allows you to expose and use public dependencies.
 */
public interface InjectedPlugin
	extends Plugin {

	/**
	 * Used to configure dependency injection for this plugin.
	 *
	 * @param binder
	 * 	the binder to configure
	 */
	void configure(@NotNull ProtectedBinder binder);
}
