package pcshop.rules;

import java.util.List;

public final class ValidationResult {
	public final boolean ok;
	public final List<String> errors;

	private ValidationResult(boolean ok, List<String> errors) {
		this.ok = ok;
		this.errors = errors;
	}

	public static ValidationResult ok() {
		return new ValidationResult(true, List.of());
	}

	public static ValidationResult fail(String msg) {
		return new ValidationResult(false, List.of(msg));
	}

	public static ValidationResult fail(List<String> msgs) {
		return new ValidationResult(false, List.copyOf(msgs));
	}
}