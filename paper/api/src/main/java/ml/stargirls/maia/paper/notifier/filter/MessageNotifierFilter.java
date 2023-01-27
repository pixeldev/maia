package ml.stargirls.maia.paper.notifier.filter;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface MessageNotifierFilter {

	@NotNull String getId();

	boolean isDenied(@NotNull Player player);
}
