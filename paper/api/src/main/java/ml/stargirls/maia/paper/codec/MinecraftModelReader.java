package ml.stargirls.maia.paper.codec;

import ml.stargirls.storage.codec.ModelReader;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

public interface MinecraftModelReader<This extends ModelReader<This, ReadType>, ReadType>
	extends ModelReader<This, ReadType> {
	@Nullable
	UUID readDetailedUuid(@NotNull String key);

	@Nullable
	UUID readDetailedUuid(@NotNull ReadType document);

	@Nullable
	Component readComponent(@NotNull String key);

	@Nullable <C extends Collection<UUID>> C readDetailedUuids(
		@NotNull String field,
		@NotNull Function<Integer, C> factory
	);
}
