package ml.stargirls.maia.paper.command.factory;

import ml.stargirls.command.annotated.part.Key;
import ml.stargirls.command.annotated.part.PartFactory;

public interface InjectablePartFactory
	extends PartFactory {
	Key getKey();
}
