package ml.stargirls.maia.paper.command;

import ml.stargirls.command.annotated.CommandClass;
import ml.stargirls.command.annotated.part.PartFactory;
import ml.stargirls.maia.paper.command.factory.InjectablePartFactory;
import ml.stargirls.maia.paper.power.PowerProcess;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class CommandRegistrationBuilder {
	public static CommandRegistrationBuilder create(
		int commands,
		int asyncCompletableFactories,
		int injectableFactories
	) {
		return new CommandRegistrationBuilder(commands, asyncCompletableFactories,
		                                      injectableFactories);
	}

	private final Collection<Class<? extends CommandClass>> commandClasses;
	private final Collection<Class<? extends PartFactory>> asyncCompletableFactories;
	private final Collection<Class<? extends InjectablePartFactory>> injectableFactories;

	private CommandRegistrationBuilder(
		int commands,
		int asyncCompletableFactories,
		int injectableFactories
	) {
		commandClasses = new ArrayList<>();
		this.asyncCompletableFactories = asyncCompletableFactories > 0 ?
		                                 new ArrayList<>(asyncCompletableFactories) :
		                                 null;
		this.injectableFactories = injectableFactories > 0 ?
		                           new ArrayList<>(injectableFactories) :
		                           null;
	}

	@Contract(pure = true, value = "_ -> this")
	public CommandRegistrationBuilder addCommand(@NotNull Class<? extends CommandClass> commandClass) {
		commandClasses.add(commandClass);
		return this;
	}

	@Contract(pure = true, value = "_ -> this")
	public CommandRegistrationBuilder addAsyncCompletableFactory(
		@NotNull Class<? extends PartFactory> asyncCompletableFactory
	) {
		asyncCompletableFactories.add(asyncCompletableFactory);
		return this;
	}

	@Contract(pure = true, value = "_ -> this")
	public CommandRegistrationBuilder addInjectablePartFactory(
		@NotNull Class<? extends InjectablePartFactory> injectablePartFactory
	) {
		injectableFactories.add(injectablePartFactory);
		return this;
	}

	public PowerProcess build() {
		return new CommandRegistrationPowerProcess(
			commandClasses,
			asyncCompletableFactories,
			injectableFactories
		);
	}
}
