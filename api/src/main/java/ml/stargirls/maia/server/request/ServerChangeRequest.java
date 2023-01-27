package ml.stargirls.maia.server.request;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record ServerChangeRequest(
	@NotNull UUID playerId, @NotNull String destination,
	boolean approved
) {

	public static final String CHANNEL_ID = "player-server-change";
}
