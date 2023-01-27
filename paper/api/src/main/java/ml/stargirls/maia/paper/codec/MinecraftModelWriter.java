package ml.stargirls.maia.paper.codec;

import ml.stargirls.storage.codec.ModelWriter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface MinecraftModelWriter<This extends ModelWriter<This, ReadType>, ReadType>
	extends ModelWriter<This, ReadType> {
	@Contract("_, _ -> this")
	This writeDetailedUuid(@NotNull String key, @Nullable UUID uuid);

	@Contract("_, _ -> this")
	This writeDetailedUuids(@NotNull String key, @Nullable Collection<@NotNull UUID> uuids);

	@Nullable
	ReadType writeDetailedUuid(@Nullable UUID uuid);

	@Contract("_, _ -> this")
	This writeComponent(@NotNull String key, @Nullable Component component);
}
