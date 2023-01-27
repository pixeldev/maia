package ml.stargirls.maia.inject;

import com.google.inject.Binder;

public interface ProtectedBinder
	extends ForwardingPrivateBinder {

	Binder globalBinder();

	@Override
	default ProtectedBinder withSource(Object source) {
		return new ProtectedBinderImpl(
			globalBinder().withSource(source),
			forwardedBinder().withSource(source)
		);
	}

	@Override
	default ProtectedBinder skipSources(Class<?>... classesToSkip) {
		return new ProtectedBinderImpl(
			globalBinder().skipSources(classesToSkip),
			forwardedBinder().skipSources(classesToSkip)
		);
	}

	static ProtectedBinder create(Binder parent) {
		return new ProtectedBinderImpl(parent, parent.newPrivateBinder());
	}
}
