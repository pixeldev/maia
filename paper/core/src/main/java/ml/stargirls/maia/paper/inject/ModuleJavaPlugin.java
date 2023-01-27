package ml.stargirls.maia.paper.inject;

import com.google.inject.Module;
import ml.stargirls.maia.inject.ProtectedBinder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ModuleJavaPlugin
	extends JavaPlugin
	implements InjectedPlugin {

	private final Module module;

	public ModuleJavaPlugin(@NotNull Module module) {
		this.module = module;
	}

	@Override
	public void configure(@NotNull ProtectedBinder binder) {
		binder.install(module);
	}
}
