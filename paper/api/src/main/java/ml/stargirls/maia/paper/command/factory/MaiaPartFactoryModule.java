package ml.stargirls.maia.paper.command.factory;

import ml.stargirls.command.annotated.part.AbstractModule;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class MaiaPartFactoryModule
	extends AbstractModule {
	@Override
	public void configure() {
		bindFactory(Component.class, new ComponentPartFactory());
		bindFactory(TextColor.class, new TextColorPartFactory());
	}
}
