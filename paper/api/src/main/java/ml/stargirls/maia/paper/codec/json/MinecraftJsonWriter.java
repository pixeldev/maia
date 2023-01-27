package ml.stargirls.maia.paper.codec.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ml.stargirls.maia.paper.codec.MinecraftModelWriter;
import ml.stargirls.storage.codec.ModelCodec;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public class MinecraftJsonWriter
	implements MinecraftModelWriter<MinecraftJsonWriter, JsonObject> {

	private final JsonObject jsonObject;

	protected MinecraftJsonWriter(@NotNull final JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public static MinecraftJsonWriter create() {
		return new MinecraftJsonWriter(new JsonObject());
	}

	public static MinecraftJsonWriter create(@NotNull final JsonObject jsonObject) {
		return new MinecraftJsonWriter(jsonObject);
	}

	@Override
	public MinecraftJsonWriter writeDetailedUuid(
		@NotNull final String key,
		@Nullable final UUID uuid
	) {
		if (uuid == null) {
			return this;
		}

		jsonObject.add(key, writeDetailedUuid(uuid));
		return this;
	}

	@Override
	public MinecraftJsonWriter writeDetailedUuids(
		@NotNull final String key,
		@Nullable final Collection<@NotNull UUID> uuids
	) {
		if (uuids == null) {
			return this;
		}

		JsonArray array = new JsonArray(uuids.size());

		for (UUID uuid : uuids) {
			JsonObject serializedUuid = writeDetailedUuid(uuid);

			if (serializedUuid == null) {
				continue;
			}

			array.add(serializedUuid);
		}

		jsonObject.add(key, array);
		return this;
	}

	@Override
	public @Nullable JsonObject writeDetailedUuid(@Nullable final UUID uuid) {
		if (uuid == null) {
			return null;
		}

		JsonObject serializedUuid = new JsonObject();
		serializedUuid.addProperty("least", uuid.getLeastSignificantBits());
		serializedUuid.addProperty("most", uuid.getMostSignificantBits());
		return serializedUuid;
	}

	@Override
	public MinecraftJsonWriter writeComponent(
		@NotNull final String key,
		@Nullable final Component component
	) {
		if (component == null) {
			return this;
		}

		jsonObject.add(
			key,
			GsonComponentSerializer.gson()
				.serializeToTree(component));
		return this;
	}

	@Override
	public MinecraftJsonWriter writeThis(
		@NotNull final String key,
		@Nullable final JsonObject value
	) {
		jsonObject.add(key, value);
		return this;
	}

	@Override
	public MinecraftJsonWriter writeUuid(@NotNull final String field, @Nullable final UUID uuid) {
		if (uuid == null) {
			return this;
		}

		jsonObject.addProperty(field, uuid.toString());
		return this;
	}

	@Override
	public MinecraftJsonWriter writeString(
		@NotNull final String field,
		@Nullable final String value
	) {
		if (value == null) {
			return this;
		}

		jsonObject.addProperty(field, value);
		return this;
	}

	@Override
	public MinecraftJsonWriter writeNumber(
		@NotNull final String field,
		@Nullable final Number value
	) {
		if (value == null) {
			return this;
		}

		jsonObject.addProperty(field, value);
		return this;
	}

	@Override
	public MinecraftJsonWriter writeBoolean(
		@NotNull final String field,
		@Nullable final Boolean value
	) {
		if (value == null) {
			return this;
		}

		jsonObject.addProperty(field, value);
		return this;
	}

	@Override
	public <T> MinecraftJsonWriter writeObject(
		@NotNull final String field,
		@Nullable final T child,
		final ModelCodec.@NotNull Writer<T, JsonObject> writer
	) {
		if (child == null) {
			return this;
		}

		jsonObject.add(field, writer.serialize(child));
		return this;
	}

	@Override
	public <T> MinecraftJsonWriter writeRawCollection(
		@NotNull final String field,
		@Nullable final Collection<T> children
	) {
		if (children == null) {
			return this;
		}

		JsonArray array = new JsonArray(children.size());

		for (T child : children) {
			if (child == null) {
				continue;
			}

			array.add(child.toString());
		}

		jsonObject.add(field, array);
		return this;
	}

	@Override
	public <T> MinecraftJsonWriter writeCollection(
		@NotNull final String field,
		@Nullable final Collection<T> children,
		final ModelCodec.@NotNull Writer<T, JsonObject> writer
	) {
		if (children == null) {
			return this;
		}

		JsonArray array = new JsonArray(children.size());

		for (T child : children) {
			array.add(writer.serialize(child));
		}

		jsonObject.add(field, array);
		return this;
	}

	@Override
	public MinecraftJsonWriter writeObject(
		@NotNull final String field,
		@Nullable final Object value
	) {
		return this;
	}

	@Override
	public @NotNull JsonObject current() {
		return jsonObject;
	}

	@Override
	public @NotNull JsonObject end() {
		return jsonObject;
	}
}
