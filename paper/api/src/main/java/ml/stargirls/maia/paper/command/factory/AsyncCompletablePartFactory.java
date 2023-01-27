package ml.stargirls.maia.paper.command.factory;

import ml.stargirls.command.annotated.part.PartFactory;
import ml.stargirls.command.part.CommandPart;
import ml.stargirls.maia.paper.command.part.AsyncCompletable;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

public class AsyncCompletablePartFactory
	implements PartFactory {
	private final Map<Class<? extends PartFactory>, PartFactory> factories;

	public AsyncCompletablePartFactory(Map<Class<? extends PartFactory>, PartFactory> factories) {
		this.factories = factories;
	}

	@Override
	public CommandPart createPart(String name, List<? extends Annotation> list) {
		AsyncCompletable asyncCompletable = null;

		for (Annotation annotation : list) {
			if (annotation instanceof AsyncCompletable completable) {
				asyncCompletable = completable;
			}
		}

		if (asyncCompletable == null) {
			throw new IllegalStateException("AsyncCompletable annotation not found");
		}

		PartFactory partFactory = factories.get(asyncCompletable.value());

		if (partFactory == null) {
			throw new IllegalStateException("No part factory found for " + asyncCompletable.value());
		}

		return partFactory.createPart(name, list);
	}
}
