package ml.stargirls.maia.paper.notifier.notification;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ml.stargirls.maia.paper.codec.json.MinecraftJsonReader;
import ml.stargirls.maia.paper.codec.json.MinecraftJsonWriter;
import ml.stargirls.storage.codec.ModelCodec;
import ml.stargirls.storage.util.Validate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Inserting;
import net.kyori.adventure.text.minimessage.tag.PreProcess;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class NotificationCodec
	implements
	JsonSerializer<Notification>,
	JsonDeserializer<Notification> {
	private static final byte INSERTING = 0;
	private static final byte PRE_PROCESS = 1;

	private static final ModelCodec.Writer<TagResolver, JsonObject> TAG_WRITER = tagResolver -> {
		if (!(tagResolver instanceof TagResolver.Single singleTagResolver)) {
			return new JsonObject();
		}

		MinecraftJsonWriter writer = MinecraftJsonWriter.create()
			                             .writeString("key", singleTagResolver.key());

		String value;
		byte type;

		if (singleTagResolver.tag() instanceof Inserting insertingTag) {
			value = GsonComponentSerializer.gson()
				        .serialize(insertingTag.value());
			type = INSERTING;
		} else if (singleTagResolver.tag() instanceof PreProcess preProcessTag) {
			value = preProcessTag.value();
			type = PRE_PROCESS;
		} else {
			return new JsonObject();
		}

		return writer.writeString("value", value)
			       .writeNumber("type", type)
			       .end();
	};

	private static final ModelCodec.Reader<TagResolver, JsonObject, MinecraftJsonReader>
		TAG_READER =
		reader -> {
			Number typeNumber = reader.readNumber("type");

			if (typeNumber == null) {
				return TagResolver.empty();
			}

			String key = Validate.notNull(reader.readString("key"), "key");
			String value = Validate.notNull(reader.readString("value"), "value");

			return switch (typeNumber.byteValue()) {
				case INSERTING -> {
					Component component = GsonComponentSerializer.gson()
						                      .deserialize(value);
					yield Placeholder.component(key, component);
				}
				case PRE_PROCESS -> Placeholder.parsed(key, value);
				default -> TagResolver.empty();
			};
		};

	@Override
	public @NotNull Notification deserialize(
		@NotNull final JsonElement json,
		@NotNull final Type typeOfT,
		@NotNull final JsonDeserializationContext context
	) throws JsonParseException {
		MinecraftJsonReader reader = MinecraftJsonReader.create(json.getAsJsonObject());

		String path = Validate.notNull(reader.readString("path"), "path");
		String mode = reader.readString("mode");
		String filterId = reader.readString("filterId");
		Collection<UUID> targets = reader.readDetailedUuids("targets", HashSet::new);
		List<TagResolver> resolvers = Validate.notNull(
			reader.readCollection("resolvers", TAG_READER, ArrayList::new),
			"resolvers");

		return new Notification(targets, mode, path, filterId, resolvers);
	}

	@Override
	public @NotNull JsonElement serialize(
		@NotNull final Notification src,
		@NotNull final Type typeOfSrc,
		@NotNull final JsonSerializationContext context
	) {
		return
			MinecraftJsonWriter
				.create()
				.writeString("path", src.path())
				.writeString("mode", src.mode())
				.writeString("filterId", src.filterId())
				.writeDetailedUuids("targets", src.targets())
				.writeCollection("resolvers", src.tagResolvers(), TAG_WRITER)
				.end();
	}
}
