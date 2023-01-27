package ml.stargirls.maia.paper.notifier.notification;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * This class is used to store notification data.
 */
public record Notification(
	@Nullable Collection<UUID> targets, @Nullable String mode,
	@NotNull String path, @Nullable String filterId,
	@NotNull List<@NotNull TagResolver> tagResolvers
) {
	public static @NotNull Notification globalFilteringIn(
		@Nullable String mode,
		@NotNull String path,
		@NotNull String filterId,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		return new Notification(null, mode, path, filterId, Arrays.asList(tagResolvers));
	}

	public static @NotNull Notification globalIn(
		@Nullable String mode,
		@NotNull String path,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		return new Notification(null, mode, path, null, Arrays.asList(tagResolvers));
	}

	public static @NotNull Notification globalFiltering(
		@NotNull String path,
		@NotNull String filterId,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		return new Notification(null, null, path, filterId, Arrays.asList(tagResolvers));
	}

	public static @NotNull Notification global(
		@NotNull String path,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		return new Notification(null, null, path, null, Arrays.asList(tagResolvers));
	}

	public static @NotNull Notification targetsFilteringIn(
		@NotNull Collection<@NotNull UUID> targets,
		@Nullable String mode,
		@NotNull String path,
		@NotNull String filterId,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		return new Notification(
			new ArrayList<>(targets),
			mode,
			path,
			filterId,
			Arrays.asList(tagResolvers));
	}

	public static @NotNull Notification targetsIn(
		@NotNull Collection<@NotNull UUID> targets,
		@Nullable String mode,
		@NotNull String path,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		return new Notification(
			new ArrayList<>(targets),
			mode,
			path,
			null,
			Arrays.asList(tagResolvers));
	}

	public static @NotNull Notification targetsFiltering(
		@NotNull Collection<@NotNull UUID> targets,
		@NotNull String path,
		@NotNull String filterId,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		return new Notification(
			new ArrayList<>(targets),
			null,
			path,
			filterId,
			Arrays.asList(tagResolvers)
		);
	}

	public static @NotNull Notification targets(
		@NotNull Collection<@NotNull UUID> targets,
		@NotNull String path,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		return new Notification(
			new ArrayList<>(targets),
			null,
			path,
			null,
			Arrays.asList(tagResolvers)
		);
	}

	public static @NotNull Notification personalIn(
		@NotNull UUID target,
		@Nullable String mode,
		@NotNull String path,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		Collection<UUID> targets = new HashSet<>(1);
		targets.add(target);
		return new Notification(
			targets,
			mode,
			path,
			null,
			Arrays.asList(tagResolvers)
		);
	}

	public static @NotNull Notification personal(
		@NotNull UUID target,
		@NotNull String path,
		@NotNull TagResolver @NotNull ... tagResolvers
	) {
		Collection<UUID> targets = new HashSet<>(1);
		targets.add(target);
		return new Notification(
			targets,
			null,
			path,
			null,
			Arrays.asList(tagResolvers)
		);
	}
}