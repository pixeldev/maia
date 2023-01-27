package ml.stargirls.maia.paper.command.factory;

import ml.stargirls.command.annotated.part.PartFactory;
import ml.stargirls.command.part.CommandPart;
import ml.stargirls.maia.paper.command.part.TextColorPart;

import java.lang.annotation.Annotation;
import java.util.List;

public class TextColorPartFactory
	implements PartFactory {
	@Override
	public CommandPart createPart(String name, List<? extends Annotation> list) {
		return new TextColorPart(name);
	}
}
