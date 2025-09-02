package pcshop.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pcshop.domain.Build;
import pcshop.domain.ComponentType;
import pcshop.domain.OptionView;
import pcshop.domain.Part;
import pcshop.rules.RulesEngine;

public final class Catalog {
	private final List<Part> parts;

	public Catalog(List<Part> parts) {
		this.parts = List.copyOf(parts);
	}

	public List<Part> listByType(ComponentType t) {
		return parts.stream().filter(p -> p.type == t).collect(Collectors.toList());
	}

	public List<OptionView> optionsFor(Build current, ComponentType type, RulesEngine rules) {
		var candidates = listByType(type);
		List<OptionView> result = new ArrayList<>();
		for (Part c : candidates) {
			var vr = current.tryReplace(type, c, rules);
			result.add(new OptionView(c, vr.ok, vr.errors));
		}
		return result;

	}
}