package ml.stargirls.maia.paper.command;

import ml.stargirls.command.Namespace;
import ml.stargirls.command.bukkit.BukkitCommandManager;
import ml.stargirls.command.stack.ArgumentStack;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
public interface CommandHelper {
	static CommandSender extractSender(@NotNull Namespace namespace) {
		return namespace.getObject(CommandSender.class, BukkitCommandManager.SENDER_NAMESPACE);
	}

	static @Nullable String extractLastArg(@NotNull ArgumentStack stack) {
		return stack.hasNext() ?
		       stack.next() :
		       null;
	}
}
