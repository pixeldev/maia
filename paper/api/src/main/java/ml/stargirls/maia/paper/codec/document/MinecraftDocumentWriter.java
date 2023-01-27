package ml.stargirls.maia.paper.codec.document;

import ml.stargirls.maia.paper.codec.MinecraftModelWriter;
import ml.stargirls.storage.codec.DelegateObjectModelWriter;
import ml.stargirls.storage.model.Model;
import ml.stargirls.storage.mongo.MongoModelService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public class MinecraftDocumentWriter
	extends DelegateObjectModelWriter<MinecraftDocumentWriter, Document>
	implements MinecraftModelWriter<MinecraftDocumentWriter, Document> {
	protected final Document document;

	protected MinecraftDocumentWriter(Document document) {
		this.document = document;
	}

	public static MinecraftDocumentWriter create() {
		return new MinecraftDocumentWriter(new Document());
	}

	public static MinecraftDocumentWriter create(@NotNull Document document) {
		return new MinecraftDocumentWriter(document);
	}

	public static MinecraftDocumentWriter create(@NotNull Model model) {
		return create().writeString(MongoModelService.ID_FIELD, model.getId());
	}

	@Override
	public MinecraftDocumentWriter writeObject(@NotNull String field, @Nullable Object value) {
		document.append(field, value);
		return this;
	}

	@Override
	public MinecraftDocumentWriter writeComponent(
		@NotNull String key,
		@Nullable Component component
	) {
		if (component == null) {
			return writeObject(key, null);
		}

		return writeObject(
			key,
			GsonComponentSerializer.gson()
				.serialize(component));
	}

	@Override
	public MinecraftDocumentWriter writeDetailedUuid(@NotNull String key, @Nullable UUID uuid) {
		return writeObject(key, writeDetailedUuid(uuid));
	}

	@Override
	public MinecraftDocumentWriter writeDetailedUuids(
		@NotNull String key,
		@Nullable Collection<@NotNull UUID> collection
	) {
		if (collection == null) {
			return writeObject(key, null);
		}

		return writeObject(
			key,
			collection.stream()
				.map(uuid -> {
					Document document = writeDetailedUuid(uuid);

					if (document == null) {
						throw new IllegalArgumentException(
							"Failed to write '" + key + "' field because of null UUID");
					}

					return document;
				})
				.toList()
		);
	}

	@Override
	public Document writeDetailedUuid(@Nullable UUID uuid) {
		if (uuid == null) {
			return null;
		}

		Document uuidDocument = new Document();
		uuidDocument.put("most", uuid.getMostSignificantBits());
		uuidDocument.put("least", uuid.getLeastSignificantBits());
		return uuidDocument;
	}

	@Override
	public @NotNull Document current() {
		return this.document;
	}

	@Override
	public @NotNull Document end() {
		return this.document;
	}
}
