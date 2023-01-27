package ml.stargirls.maia.paper.command.part;

import ml.stargirls.command.CommandContext;
import ml.stargirls.command.exception.ArgumentParseException;
import ml.stargirls.command.part.ArgumentPart;
import ml.stargirls.command.part.CommandPart;
import ml.stargirls.command.stack.ArgumentStack;
import ml.stargirls.maia.paper.command.CommandHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TextColorPart
	implements ArgumentPart {
	private final String name;

	public TextColorPart(String name) {
		this.name = name;
	}

	@Override
	public List<?> parseValue(
		CommandContext commandContext,
		ArgumentStack argumentStack,
		CommandPart commandPart
	) throws ArgumentParseException {
		String key = argumentStack.next()
			             .toLowerCase(Locale.ROOT);
		TextColor color = NamedTextColor.NAMES.value(key);

		if (color == null) {
			color = TextColor.fromHexString(key);

			if (color == null) {
				throw new ArgumentParseException(Component.translatable("unknown.color"));
			}
		}

		return Collections.singletonList(color);
	}

	@Override
	public List<String> getSuggestions(
		CommandContext commandContext,
		ArgumentStack stack
	) {
		String key = CommandHelper.extractLastArg(stack);
		Collection<String> namedKeys = NamedTextColor.NAMES.keys();

		if (key == null) {
			List<String> suggestions = new ArrayList<>(namedKeys.size());
			suggestions.addAll(namedKeys);
			suggestions.add("#");
			return suggestions;
		}

		key = key.toLowerCase(Locale.ROOT);
		List<String> suggestions = new ArrayList<>(namedKeys.size());

		for (String namedKey : namedKeys) {
			if (namedKey.startsWith(key)) {
				suggestions.add(namedKey);
			}
		}

		return suggestions;
	}

	@Override
	public String getName() {
		return name;
	}
}
