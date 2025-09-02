package pcshop.cart;

import java.util.EnumMap;
import java.util.Optional;

import pcshop.domain.Build;
import pcshop.domain.ComponentType;
import pcshop.domain.Part;

public final class CartItem {
	private final EnumMap<ComponentType, Integer> partQty = new EnumMap<>(ComponentType.class);
	public final String id;
	public final Build build;
	public int quantity;

	public CartItem(String id, Build build, int quantity) {
		this.id = id;
		this.build = build;
		this.quantity = Math.max(1, quantity);

		for (ComponentType t : build.asMap().keySet()) {
			partQty.put(t, 1);
		}
	}
	public Build getBuild() {
		return build;
	}
	public int getPartQty(ComponentType type) {
		return partQty.getOrDefault(type, 1);
	}
	public void setPartQty(ComponentType type, int qty) {
		if(qty<1)qty=1;
		partQty.put(type, qty);
	}
	public int partsSubtotal() {
		int sum=0;
		for(ComponentType t : build.asMap().keySet()) {
			Optional<Part> op = build.get(t);
			if(op.isPresent()) {
				Part p = op.get();
				sum +=p.price*getPartQty(t);	//부품 단가 * 수량
			}
		}
		return sum;
	}

	public int subtotal() {
		return quantity*partsSubtotal();

	}
}