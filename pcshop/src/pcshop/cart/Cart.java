package pcshop.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import pcshop.domain.ComponentType;

public final class Cart {

	private final List<CartItem> items = new ArrayList<>();

	public CartItem add(pcshop.domain.Build build, int qty) {
		CartItem ci = new CartItem(UUID.randomUUID().toString(), build.copy(), qty);
		items.add(ci);
		return ci;
	}

	public Optional<CartItem> find(String id) {
		return items.stream().filter(i -> i.id.equals(id)).findFirst();
	}

	public void updateQuantity(String id, int qty) {
		find(id).ifPresent(i ->{
			if(qty<=0) {
				//0이하로 내려오면 아이템 자체를 장바구니에서 제거
				items.remove(i);
			}else {
				i.quantity=qty;
			}
		});
	}
	
	//부품별 수량 조정(RAM 2개, CPU 2개 등)
	public void updatePartQuantity(String id, ComponentType type, int qty) {
		find(id).ifPresent(i->i.setPartQty(type, Math.max(1,qty)));
	}
	
	//명시적 삭제
	public void remove(String id) {
		find(id).ifPresent(items::remove);
	}

	public int total() {
		return items.stream().mapToInt(CartItem::subtotal).sum();
	}

	public List<CartItem> items() {
		return List.copyOf(items);
	}
}