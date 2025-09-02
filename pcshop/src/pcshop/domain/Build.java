package pcshop.domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import pcshop.rules.RulesEngine;
import pcshop.rules.ValidationResult;

public final class Build {
	private final EnumMap<ComponentType,Part>parts=new EnumMap<>(ComponentType.class);

	public Optional<Part> get(ComponentType t) {
		return Optional.ofNullable(parts.get(t));
	}

	public void put(Part p) {
		parts.put(p.type, p);
	}

	public Map<ComponentType, Part> asMap() {
		return Map.copyOf(parts);
	}

	public int totalPrice() {
		return parts.values().stream().mapToInt(p -> p.price).sum();
	}

	public ValidationResult tryReplace(ComponentType type, Part candidate, RulesEngine rules) {
		if (candidate.type != type) {
			return ValidationResult.fail("선택한 후보 부품 타입이 다릅니다: " + candidate.type);
		}

		Build tmp = this.copy();
		tmp.put(candidate);
		return rules.checkAll(tmp);
	}

	public void replace(ComponentType type, Part p) {
		put(p);
	}

	public Build copy() {
		Build b = new Build();
		parts.values().forEach(b::put);
		return b;
	}
}