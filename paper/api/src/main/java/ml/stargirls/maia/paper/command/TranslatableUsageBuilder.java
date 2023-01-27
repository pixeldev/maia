package ml.stargirls.maia.paper.command;

import ml.stargirls.command.CommandContext;
import ml.stargirls.command.usage.DefaultUsageBuilder;
import ml.stargirls.message.MessageHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

public class TranslatableUsageBuilder
	extends DefaultUsageBuilder {
	@Inject private MessageHandler messageHandler;

	@Override
	public Component getUsage(CommandContext context) {
		Component usage = super.getUsage(context);
		CommandSender sender = CommandHelper.extractSender(context);

		if (sender == null) {
			return usage;
		}

		return messageHandler.replacing(
			sender,
			"commands.usage",
			Placeholder.component("usage", usage));
	}
}
