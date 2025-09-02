package pcshop.catalog;

import java.util.List;
import java.util.Map;
import pcshop.domain.ComponentType;
import pcshop.domain.Part;

public final class SampleData {
	public static Part cpu(String id, String name, String socket, int price) {
		return new Part(id, name, ComponentType.CPU, price, Map.of("socket", socket));
	}

	public static Part mb(String id, String name, String socket, int price) {
		return new Part(id, name, ComponentType.MOTHERBOARD, price, Map.of("socket", socket));
	}

	public static Part gpu(String id, String name, int price, int psuMinWatt, int lengthMm) {
		return new Part(id, name, ComponentType.GPU, price,
				Map.of("psu_min_watt", String.valueOf(psuMinWatt), "length_mm", String.valueOf(lengthMm)));
	}

	public static Part psu(String id, String name, int watt, int price) {
		return new Part(id, name, ComponentType.PSU, price, Map.of("watt", String.valueOf(watt)));
	}

	public static Part kase(String id, String name, int gpuMaxLenMm, int price) {
		return new Part(id, name, ComponentType.CASE, price, Map.of("gpu_max_len_mm", String.valueOf(gpuMaxLenMm)));
	}

	public static List<Part> catalog() {
		return List.of(cpu("cpu1", "Ryzen 5 7600", "AM5", 230000), cpu("cpu2", "Ryzen 7 7800X3D", "AM5", 540000),
				mb("mb1", "MSI B650", "AM5", 200000), mb("mb2", "ASUS X670", "AM5", 380000),
				gpu("gpu1", "GeForce RTX 5070", 700000, 600, 270), gpu("gpu2", "GeForce RTX 5070 Ti", 850000, 700, 304),
				psu("psu1", "Seasonic 650W", 650, 120000), psu("psu2", "Corsair 750W", 750, 150000),
				kase("case1", "NR200P", 330, 120000), kase("case2", "ATX Air", 400, 140000));

	}
}