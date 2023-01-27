package ml.stargirls.maia.paper.codec.document;

import ml.stargirls.maia.paper.codec.MinecraftModelReader;
import ml.stargirls.storage.mongo.codec.AbstractDocumentReader;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class MinecraftDocumentReader
	extends AbstractDocumentReader<MinecraftDocumentReader>
	implements MinecraftModelReader<MinecraftDocumentReader, Document> {
	protected MinecraftDocumentReader(@NotNull Document document) {
		super(document, MinecraftDocumentReader::create);
	}

	public static MinecraftDocumentReader create(@NotNull Document document) {
		return new MinecraftDocumentReader(document);
	}

	@Override
	public @Nullable UUID readDetailedUuid(@NotNull String key) {
		Document uuidDocument = document.get(key, Document.class);

		if (uuidDocument == null) {
			return null;
		}

		return readDetailedUuid(uuidDocument);
	}

	@Override
	public @Nullable UUID readDetailedUuid(@NotNull Document document) {
		Long most = document.getLong("most");

		if (most == null) {
			return null;
		}

		Long least = document.getLong("least");

		if (least == null) {
			return null;
		}

		return new UUID(most, least);
	}

	@Override
	public @Nullable Component readComponent(@NotNull String key) {
		String serialized = document.getString(key);

		if (serialized == null) {
			return null;
		}

		return GsonComponentSerializer.gson()
			       .deserialize(serialized);
	}

	@Override
	public <C extends Collection<UUID>> @Nullable C readDetailedUuids(
		@NotNull String field,
		@NotNull Function<Integer, C> factory
	) {
		List<Document> documents = document.getList(field, Document.class);

		if (documents == null) {
			return null;
		}

		C uuids = factory.apply(documents.size());

		for (Document uuidDocument : documents) {
			UUID uuid = readDetailedUuid(uuidDocument);

			if (uuid != null) {
				uuids.add(uuid);
			}
		}

		return uuids;
	}
}
