package ml.stargirls.maia.paper.command.part;

import ml.stargirls.command.annotated.part.PartFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsyncCompletable {
	Class<? extends PartFactory> value();
}
