package ml.stargirls.maia.paper.power;

import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface PowerProcess {
	void execute(@NotNull Plugin plugin, @NotNull Injector injector) throws Exception;
}
