package ml.stargirls.maia.paper.concurrent;

import com.google.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class PluginExecutor
	implements Executor {
	private static final long NOW_DELAY = 0L;
	private static final long NO_PERIOD = -1L;

	@Inject private Plugin plugin;

	@Override
	public void execute(@NotNull Runnable command) {
		run(command);
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public int run(@NotNull Runnable task) {
		return Bukkit.getScheduler()
			       .runTaskLater(plugin, task, NOW_DELAY)
			       .getTaskId();
	}

	public int runAsync(@NotNull Runnable task) {
		return Bukkit.getScheduler()
			       .runTaskLaterAsynchronously(plugin, task, NOW_DELAY)
			       .getTaskId();
	}

	public int runLater(@NotNull Runnable task, long delay) {
		return Bukkit.getScheduler()
			       .runTaskTimer(plugin, task, delay, NO_PERIOD)
			       .getTaskId();
	}

	public int runLaterAsync(@NotNull Runnable task, long delay) {
		return Bukkit.getScheduler()
			       .runTaskTimerAsynchronously(plugin, task, delay, NO_PERIOD)
			       .getTaskId();
	}

	public int timer(@NotNull Runnable task, long delay, long period) {
		return Bukkit.getScheduler()
			       .runTaskTimer(plugin, task, delay, period)
			       .getTaskId();
	}

	public int timerAsync(@NotNull Runnable command, long delay, long period) {
		return Bukkit.getScheduler()
			       .runTaskTimerAsynchronously(plugin, command, delay, period)
			       .getTaskId();
	}
}
