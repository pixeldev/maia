package ml.stargirls.maia.paper.command.part;

import ml.stargirls.command.CommandContext;
import ml.stargirls.command.exception.ArgumentParseException;
import ml.stargirls.command.part.ArgumentPart;
import ml.stargirls.command.part.CommandPart;
import ml.stargirls.command.stack.ArgumentStack;

import java.util.Collections;
import java.util.List;

public abstract class AsyncCompletablePart
	implements ArgumentPart {
	private final String name;

	public AsyncCompletablePart(String name) {
		this.name = name;
	}

	@Override
	public List<?> parseValue(
		CommandContext commandContext,
		ArgumentStack argumentStack,
		CommandPart commandPart
	) throws ArgumentParseException {
		return Collections.singletonList(argumentStack.next());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public abstract List<String> getSuggestions(CommandContext commandContext, ArgumentStack stack);
}
