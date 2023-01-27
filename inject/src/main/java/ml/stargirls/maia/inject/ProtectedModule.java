package ml.stargirls.maia.inject;

import com.google.inject.Binder;
import com.google.inject.Module;

public abstract class ProtectedModule
	implements Module,
	           ForwardingProtectedBinder {

	private final Object moduleKey;
	private ProtectedBinder binder;

	protected ProtectedModule(Object moduleKey) {
		this.moduleKey = moduleKey;
	}

	protected ProtectedModule() {
		this(null);
	}

	/**
	 * Use this method to normally configure the module.
	 */
	protected void configure() {

	}

	@Override
	public final ProtectedBinder forwardedBinder() {
		return binder();
	}

	@Override
	public final void configure(Binder binder) {
		ProtectedBinder previous = this.binder;

		this.binder = ProtectedBinderImpl.current(binder);

		try {
			if (this.binder == null) {
				binder.skipSources(ProtectedModule.class)
					.addError(ProtectedModule.class.getSimpleName() + " must be installed with a " +
					          ProtectedBinder.class.getSimpleName());
			} else {
				configure();
			}
		} finally {
			this.binder = previous;
		}
	}

	protected final ProtectedBinder binder() {
		if (binder == null) {
			throw new IllegalStateException("Binder is only usable during configuration");
		}

		return binder;
	}

	@Override
	public int hashCode() {
		return moduleKey == null ?
		       super.hashCode() :
		       moduleKey.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (moduleKey == null) {
			return super.equals(object);
		}

		return object != null &&
		       getClass() == object.getClass() &&
		       moduleKey.equals(((ProtectedModule) object).moduleKey);
	}
}
