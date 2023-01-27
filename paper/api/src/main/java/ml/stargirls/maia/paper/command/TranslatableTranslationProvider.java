package ml.stargirls.maia.paper.command;

import ml.stargirls.command.Namespace;
import ml.stargirls.command.translator.TranslationArgumentResolver;
import ml.stargirls.command.translator.TranslationProvider;
import ml.stargirls.message.MessageHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;

public class TranslatableTranslationProvider
	implements TranslationProvider {
	@Inject private MessageHandler messageHandler;

	@Override
	public Component getTranslation(
		Namespace namespace,
		TranslatableComponent translatableComponent
	) {
		CommandSender sender = CommandHelper.extractSender(namespace);
		return messageHandler.get(
			sender,
			"commands." + translatableComponent.key(),
			TranslationArgumentResolver.createResolver(translatableComponent)
		);
	}
}
