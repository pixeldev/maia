package ml.stargirls.maia.server;

import ml.stargirls.storage.model.Model;
import org.jetbrains.annotations.NotNull;

public record ServerInfo(@NotNull String serverIdentifier)
	implements Model {

	public static final ServerInfo PROXY_SERVER =
		new ServerInfo("proxy");

	public static final String TABLE_NAME = "server-info";

	@Override
	public @NotNull String getId() {
		return serverIdentifier;
	}
}
