package pcshop.rules;

import java.util.ArrayList;
import java.util.List;
import pcshop.domain.Build;

public final class RulesEngine {
	private final List<CompatibilityRule> rules = new ArrayList<>();

	public RulesEngine add(CompatibilityRule r) {
		rules.add(r);
		return this;
	}

	public ValidationResult checkAll(Build build) {
		List<String> errors = new ArrayList<>();
		for (CompatibilityRule r : rules)
			r.validate(build).ifPresent(errors::add);
		return errors.isEmpty() ? ValidationResult.ok() : ValidationResult.fail(errors);
	}
}