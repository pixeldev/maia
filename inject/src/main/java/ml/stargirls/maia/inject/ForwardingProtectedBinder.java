package ml.stargirls.maia.inject;

import com.google.inject.Binder;

public interface ForwardingProtectedBinder
	extends ProtectedBinder,
	        ForwardingPrivateBinder {

	@Override
	ProtectedBinder forwardedBinder();

	@Override
	default Binder globalBinder() {
		return forwardedBinder().globalBinder();
	}

	@Override
	default ProtectedBinder withSource(Object source) {
		return forwardedBinder().withSource(source);
	}

	@Override
	default ProtectedBinder skipSources(Class<?>... classesToSkip) {
		return forwardedBinder().skipSources(classesToSkip);
	}
}
