package pcshop.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Part {
	public final String id;
	public final String name;
	public final ComponentType type;
	public final int price; // KRW
	public final Map<String, String> attrs; // e.g., socket, watt, length

	public Part(String id, String name, ComponentType type, int price, Map<String, String> attrs) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.attrs = Collections.unmodifiableMap(new HashMap<>(attrs));
	}

	public String attr(String key) {
		return attrs.get(key);
	}

	@Override
	public String toString() {
		return name + " (" + type + ", " + price + "원)";
	}
}