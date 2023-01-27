package ml.stargirls.maia.paper.command.part;

import ml.stargirls.command.CommandContext;
import ml.stargirls.command.exception.ArgumentParseException;
import ml.stargirls.command.part.ArgumentPart;
import ml.stargirls.command.part.CommandPart;
import ml.stargirls.command.stack.ArgumentStack;
import ml.stargirls.maia.paper.command.CommandHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ComponentPart
	implements ArgumentPart {
	private static final List<String> SUGGESTIONS;

	static {
		SUGGESTIONS = new ArrayList<>();

		// colors
		NamedTextColor.NAMES.keys()
			.forEach(colorKey -> SUGGESTIONS.add("<" + colorKey + ">"));
		SUGGESTIONS.add("<#hex>");
		SUGGESTIONS.add("<rainbow[!][phase]>");
		SUGGESTIONS.add("<gradient:[color]:[color...]>");

		// decorations
		SUGGESTIONS.add("<b>");
		SUGGESTIONS.add("<i>");
		SUGGESTIONS.add("<u>");
		SUGGESTIONS.add("<st>");
		SUGGESTIONS.add("<obf>");
		SUGGESTIONS.add("<newline>");

		// click actions
		SUGGESTIONS.add("<click:copy_to_clipboard:value>");
		SUGGESTIONS.add("<click:open_url:url>");
		SUGGESTIONS.add("<click:run_command:command>");
		SUGGESTIONS.add("<click:suggest_command:command>");
		SUGGESTIONS.add("<click:change_page:page>");
		SUGGESTIONS.add("<insertion:text>");

		// hover actions
		SUGGESTIONS.add("<hover:show_text:value>");
		SUGGESTIONS.add("<hover:show_item:value>");
		SUGGESTIONS.add("<hover:show_entity:value>");

		// keybind
		SUGGESTIONS.add("<keybind:keybind>");
	}

	private final String name;

	public ComponentPart(String name) {
		this.name = name;
	}

	@Override
	public List<?> parseValue(
		CommandContext commandContext,
		ArgumentStack argumentStack,
		CommandPart commandPart
	) throws ArgumentParseException {
		String value = argumentStack.next();
		Component component = MiniMessage.miniMessage()
			                      .deserialize(value);
		return Collections.singletonList(component);
	}

	@Override
	public List<String> getSuggestions(
		CommandContext commandContext,
		ArgumentStack stack
	) {
		String value = CommandHelper.extractLastArg(stack);

		if (value == null) {
			return SUGGESTIONS;
		}

		String lastArg = value.toLowerCase(Locale.ROOT);

		return SUGGESTIONS.stream()
			       .filter(suggestion -> suggestion.startsWith(lastArg))
			       .toList();
	}

	@Override
	public String getName() {
		return name;
	}
}
