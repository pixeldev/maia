package ml.stargirls.maia.paper.command;

import com.google.inject.Injector;
import ml.stargirls.command.CommandManager;
import ml.stargirls.command.annotated.AnnotatedCommandTreeBuilder;
import ml.stargirls.command.annotated.AnnotatedCommandTreeBuilderImpl;
import ml.stargirls.command.annotated.CommandClass;
import ml.stargirls.command.annotated.builder.AnnotatedCommandBuilderImpl;
import ml.stargirls.command.annotated.part.Key;
import ml.stargirls.command.annotated.part.PartFactory;
import ml.stargirls.command.annotated.part.PartInjector;
import ml.stargirls.command.annotated.part.defaults.DefaultsModule;
import ml.stargirls.command.bukkit.factory.BukkitModule;
import ml.stargirls.command.paper.PaperCommandManager;
import ml.stargirls.maia.paper.command.factory.AsyncCompletablePartFactory;
import ml.stargirls.maia.paper.command.factory.InjectablePartFactory;
import ml.stargirls.maia.paper.command.factory.MaiaPartFactoryModule;
import ml.stargirls.maia.paper.command.part.AsyncCompletable;
import ml.stargirls.maia.paper.power.PowerProcess;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistrationPowerProcess
	implements PowerProcess {
	private Collection<Class<? extends CommandClass>> commandClasses;
	private Collection<Class<? extends PartFactory>> asyncCompletableFactories;
	private Collection<Class<? extends InjectablePartFactory>> injectablePartFactories;

	public CommandRegistrationPowerProcess(
		@NotNull Collection<Class<? extends CommandClass>> commandClasses,
		@Nullable Collection<Class<? extends PartFactory>> asyncCompletableFactories,
		@Nullable Collection<Class<? extends InjectablePartFactory>> injectablePartFactories
	) {
		this.commandClasses = commandClasses;
		this.asyncCompletableFactories = asyncCompletableFactories;
		this.injectablePartFactories = injectablePartFactories;
	}

	@Override
	public void execute(@NotNull Plugin plugin, @NotNull Injector injector) {
		CommandManager commandManager = new PaperCommandManager(plugin);
		commandManager.setUsageBuilder(injector.getInstance(TranslatableUsageBuilder.class));
		commandManager.getTranslator()
			.setProvider(injector.getInstance(TranslatableTranslationProvider.class));

		PartInjector partInjector = PartInjector.create();
		partInjector.install(new DefaultsModule());
		partInjector.install(new BukkitModule());
		partInjector.install(new MaiaPartFactoryModule());

		if (injectablePartFactories != null) {
			for (Class<? extends InjectablePartFactory> injectablePartFactory :
				injectablePartFactories) {
				InjectablePartFactory partFactory = injector.getInstance(injectablePartFactory);
				partInjector.bindFactory(partFactory.getKey(), partFactory);
			}

			injectablePartFactories = null;
		}

		if (asyncCompletableFactories != null) {
			Map<Class<? extends PartFactory>, PartFactory> factories = new HashMap<>(
				asyncCompletableFactories.size());

			for (Class<? extends PartFactory> partFactoryClass : asyncCompletableFactories) {
				factories.put(partFactoryClass, injector.getInstance(partFactoryClass));
			}

			partInjector.bindFactory(
				new Key(String.class, AsyncCompletable.class),
				new AsyncCompletablePartFactory(factories));

			asyncCompletableFactories = null;
		}

		AnnotatedCommandTreeBuilder treeBuilder = new AnnotatedCommandTreeBuilderImpl(
			new AnnotatedCommandBuilderImpl(partInjector),
			(clazz, parent) -> injector.getInstance(clazz)
		);

		for (Class<? extends CommandClass> commandClass : commandClasses) {
			CommandClass instance = injector.getInstance(commandClass);
			commandManager.registerCommands(treeBuilder.fromClass(instance));
		}

		commandClasses = null;
	}
}
