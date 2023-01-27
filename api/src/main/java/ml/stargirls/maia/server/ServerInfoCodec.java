package ml.stargirls.maia.server;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class ServerInfoCodec
	implements JsonSerializer<ServerInfo>, JsonDeserializer<ServerInfo> {
	@Override
	public ServerInfo deserialize(
		@NotNull final JsonElement json,
		@NotNull final Type typeOfT,
		@NotNull final JsonDeserializationContext context
	) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		String serverIdentifier = object.get("serverIdentifier")
			                          .getAsString();
		return new ServerInfo(serverIdentifier);
	}

	@Override
	public JsonElement serialize(
		@NotNull final ServerInfo src,
		@NotNull final Type typeOfSrc,
		@NotNull final JsonSerializationContext context
	) {
		JsonObject object = new JsonObject();
		object.addProperty("serverIdentifier", src.serverIdentifier());
		return object;
	}
}
