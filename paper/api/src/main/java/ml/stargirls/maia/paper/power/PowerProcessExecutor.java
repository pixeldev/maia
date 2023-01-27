package ml.stargirls.maia.paper.power;

import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PowerProcessExecutor {
	private final List<PowerProcess> processes;

	private PowerProcessExecutor(int size) {
		this.processes = new ArrayList<>(size);
	}

	public static PowerProcessExecutor create(int size) {
		return new PowerProcessExecutor(size);
	}

	@Contract(pure = true, value = "_ -> this")
	public PowerProcessExecutor add(@NotNull PowerProcess process) {
		this.processes.add(process);
		return this;
	}

	public void execute(@NotNull Plugin plugin, @NotNull Injector injector) throws Exception {
		for (PowerProcess process : this.processes) {
			process.execute(plugin, injector);
		}
	}
}
