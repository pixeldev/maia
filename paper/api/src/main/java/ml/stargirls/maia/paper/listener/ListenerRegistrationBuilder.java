package ml.stargirls.maia.paper.listener;

import ml.stargirls.maia.paper.power.PowerProcess;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collection;

public class ListenerRegistrationBuilder {
	@Contract(pure = true, value = "_ -> new")
	public static ListenerRegistrationBuilder create(int size) {
		return new ListenerRegistrationBuilder(size);
	}

	private final Collection<Class<? extends Listener>> listeners;

	private ListenerRegistrationBuilder(int size) {
		this.listeners = new ArrayList<>(size);
	}

	@Contract(pure = true, value = "_ -> this")
	public ListenerRegistrationBuilder add(Class<? extends Listener> listenerClass) {
		this.listeners.add(listenerClass);
		return this;
	}

	public PowerProcess build() {
		return (plugin, injector) -> {
			PluginManager pluginManager = Bukkit.getPluginManager();

			for (Class<? extends Listener> listenerClass : this.listeners) {
				Listener listener = injector.getInstance(listenerClass);
				pluginManager.registerEvents(
					listener,
					plugin
				);
			}
		};
	}
}
