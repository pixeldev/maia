package ml.stargirls.maia.paper.translation;

import ml.stargirls.message.send.MessageSender;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ComponentMessageSender
	implements MessageSender<CommandSender> {
	@Override
	public void send(
		@NotNull CommandSender sender,
		@NotNull String mode,
		@NotNull Component message
	) {
		if (sender instanceof Player player) {
			switch (mode) {
				case SendingModes.TITLE -> player.sendTitlePart(TitlePart.TITLE, message);
				case SendingModes.ACTIONBAR -> player.sendActionBar(message);
				case SendingModes.PING -> {
					playSound(player, org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING);
					player.sendMessage(message);
				}
				case SendingModes.ERROR -> {
					playSound(player, org.bukkit.Sound.BLOCK_NOTE_BLOCK_BASS);
					player.sendMessage(message);
				}
				default -> player.sendMessage(message);
			}
			return;
		}

		sender.sendMessage(message);
	}

	private void playSound(@NotNull Player player, @NotNull org.bukkit.Sound sound) {
		player.playSound(Sound.sound(sound, Sound.Source.AMBIENT, 1F, 1F));
	}
}
