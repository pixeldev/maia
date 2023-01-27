package ml.stargirls.maia.inject;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.PrivateBinder;

import javax.annotation.Nullable;

public class ProtectedBinderImpl
	implements ProtectedBinder {

	private static final ThreadLocal<ProtectedBinder> CURRENT =
		new ThreadLocal<>();

	public static @Nullable ProtectedBinder current(Binder binder) {
		if (binder instanceof ProtectedBinder protectedBinder) {
			return protectedBinder;
		}

		ProtectedBinder current = CURRENT.get();

		if (current != null && current.forwardedBinder() == binder) {
			return current;
		}

		return null;
	}

	private static final Class<?>[] CLASSES_TO_SKIP = new Class<?>[] {
		ForwardingBinder.class,
		ForwardingPrivateBinder.class,
		ProtectedBinder.class,
		ProtectedBinderImpl.class
	};

	private final Binder globalBinder;
	private final PrivateBinder localBinder;

	protected ProtectedBinderImpl(
		Binder globalBinder,
		PrivateBinder localBinder
	) {
		this.globalBinder = globalBinder.skipSources(CLASSES_TO_SKIP);
		this.localBinder = (localBinder instanceof ProtectedBinder protectedBinder ?
		                    protectedBinder.forwardedBinder() :
		                    localBinder).skipSources(CLASSES_TO_SKIP);
	}

	@Override
	public PrivateBinder forwardedBinder() {
		return localBinder;
	}

	@Override
	public Binder globalBinder() {
		return globalBinder;
	}

	@Override
	public void install(Module module) {
		ProtectedBinder previous = CURRENT.get();
		CURRENT.set(this);

		try {
			localBinder.install(module);
		} finally {
			CURRENT.set(previous);
		}
	}
}
