package ml.stargirls.maia.server.request;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.UUID;

public class ServerChangeRequestCodec
	implements JsonSerializer<ServerChangeRequest>, JsonDeserializer<ServerChangeRequest> {
	@Override
	public ServerChangeRequest deserialize(
		@NotNull final JsonElement json,
		@NotNull final Type typeOfT,
		@NotNull final JsonDeserializationContext context
	) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		UUID playerId = context.deserialize(object.get("playerId"), UUID.class);
		String destination = object.get("destination")
			                     .getAsString();
		boolean approved = object.get("approved")
			                   .getAsBoolean();
		return new ServerChangeRequest(playerId, destination, approved);
	}

	@Override
	public JsonElement serialize(
		@NotNull final ServerChangeRequest src,
		@NotNull final Type typeOfSrc,
		@NotNull final JsonSerializationContext context
	) {
		JsonObject object = new JsonObject();
		object.add("playerId", context.serialize(src.playerId()));
		object.addProperty("destination", src.destination());
		object.addProperty("approved", src.approved());
		return object;
	}
}
