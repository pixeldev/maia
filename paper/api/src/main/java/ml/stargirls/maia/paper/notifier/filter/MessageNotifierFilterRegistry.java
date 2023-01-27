package ml.stargirls.maia.paper.notifier.filter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageNotifierFilterRegistry {
	private final Map<String, MessageNotifierFilter> filters = new ConcurrentHashMap<>();

	public void registerFilter(@NotNull MessageNotifierFilter filter) {
		filters.put(filter.getId(), filter);
	}

	public @Nullable MessageNotifierFilter removeFilter(@NotNull String id) {
		return filters.remove(id);
	}

	public @Nullable MessageNotifierFilter getFilter(@Nullable String id) {
		if (id == null) {
			return null;
		}

		return filters.get(id);
	}
}
