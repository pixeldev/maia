package ml.stargirls.maia.paper.notifier;

import ml.stargirls.maia.paper.notifier.filter.MessageNotifierFilter;
import ml.stargirls.maia.paper.notifier.filter.MessageNotifierFilterRegistry;
import ml.stargirls.maia.paper.notifier.notification.Notification;
import ml.stargirls.message.MessageHandler;
import ml.stargirls.message.config.ConfigurationHandle;
import ml.stargirls.message.source.MessageSource;
import ml.stargirls.message.track.TrackingContext;
import ml.stargirls.message.util.ReplacePack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class LocalMessageNotifier
	implements MessageNotifier {
	@Inject private MessageHandler messageHandler;
	@Inject private MessageNotifierFilterRegistry filterRegistry;

	@Override
	public void sendNotification(@NotNull final Notification notification) {
		internalSend(notification);
	}

	protected boolean internalSend(@NotNull final Notification notification) {
		Collection<UUID> targets = notification.targets();
		String mode = notification.mode();
		String path = notification.path();
		List<TagResolver> tagResolvers = notification.tagResolvers();
		MessageNotifierFilter filter = filterRegistry.getFilter(notification.filterId());

		if (targets == null) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (filter == null) {
					send(player, mode, path, tagResolvers);
					continue;
				}

				if (filter.isDenied(player)) {
					continue;
				}

				send(player, mode, path, tagResolvers);
			}

			return false;
		} else {
			boolean success = true;
			Iterator<UUID> targetsIterator = targets.iterator();

			while (targetsIterator.hasNext()) {
				UUID target = targetsIterator.next();
				Player player = Bukkit.getPlayer(target);

				if (player == null) {
					success = false;
					continue;
				}

				targetsIterator.remove();

				if (filter == null) {
					send(player, mode, path, tagResolvers);
					continue;
				}

				if (filter.isDenied(player)) {
					continue;
				}

				send(player, mode, path, tagResolvers);
			}

			return success;
		}
	}

	private void send(
		Player player,
		@Nullable String mode,
		String path,
		List<TagResolver> tagResolvers
	) {
		if (mode != null) {
			messageHandler.sendReplacingIn(player, mode, path, tagResolvers);
		} else {
			messageHandler.sendReplacing(player, path, tagResolvers);
		}
	}

	@Override
	public Component format(@Nullable Object entity, @NotNull String text) {
		return messageHandler.format(entity, text);
	}

	@Override
	public Component format(@NotNull TrackingContext context, @NotNull String path) {
		return messageHandler.format(context, path);
	}

	@Override
	public List<Component> formatMany(@NotNull TrackingContext context, @NotNull String path) {
		return messageHandler.formatMany(context, path);
	}

	@Override
	public Component format(
		@Nullable Object entity,
		@NotNull String path,
		@NotNull ReplacePack replacements,
		@NotNull Object @NotNull [] jitEntities
	) {
		return messageHandler.format(entity, path, replacements, jitEntities);
	}

	@Override
	public List<Component> formatMany(
		@Nullable Object entity,
		@NotNull String path,
		@NotNull ReplacePack replacements,
		@NotNull Object @NotNull [] jitEntities
	) {
		return messageHandler.formatMany(entity, path, replacements, jitEntities);
	}

	@Override
	public Component getMessage(@NotNull String path) {
		return messageHandler.getMessage(path);
	}

	@Override
	public List<Component> getMessages(@NotNull String path) {
		return messageHandler.getMessages(path);
	}

	@Override
	public Component get(
		@Nullable Object entity,
		@NotNull String path,
		@NotNull Object @NotNull ... jitEntities
	) {
		return messageHandler.get(entity, path, jitEntities);
	}

	@Override
	public Component replacing(
		@Nullable Object entity,
		@NotNull String path,
		@NotNull TagResolver @NotNull ... replacements
	) {
		return messageHandler.replacing(entity, path, replacements);
	}

	@Override
	public Component replacing(
		@Nullable Object entity,
		@NotNull String path,
		@NotNull List<@NotNull TagResolver> replacements
	) {
		return messageHandler.replacing(entity, path, replacements);
	}

	@Override
	public List<Component> getMany(
		@Nullable Object entity,
		@NotNull String messagePath,
		@NotNull Object @NotNull ... jitEntities
	) {
		return messageHandler.getMany(entity, messagePath, jitEntities);
	}

	@Override
	public List<Component> replacingMany(
		@Nullable Object entity,
		@NotNull String messagePath,
		@NotNull TagResolver @NotNull ... replacements
	) {
		return messageHandler.replacingMany(entity, messagePath, replacements);
	}

	@Override
	public List<Component> replacingMany(
		@Nullable Object entity,
		@NotNull String messagePath,
		@NotNull List<@NotNull TagResolver> replacements
	) {
		return messageHandler.replacingMany(entity, messagePath, replacements);
	}

	@Override
	public Object resolve(@Nullable Object entity) {
		return messageHandler.resolve(entity);
	}

	@Override
	public String getLanguage(@Nullable Object entity) {
		return messageHandler.getLanguage(entity);
	}

	@Override
	public @NotNull ConfigurationHandle getConfig() {
		return messageHandler.getConfig();
	}

	@Override
	public @NotNull MessageSource getSource() {
		return messageHandler.getSource();
	}

	@Override
	public void dispatch(
		@NotNull Object entityOrEntities,
		@NotNull String path,
		@NotNull String mode,
		@NotNull ReplacePack replacements,
		@NotNull Object @NotNull ... jitEntities
	) {
		messageHandler.dispatch(entityOrEntities, path, mode, replacements, jitEntities);
	}
}
