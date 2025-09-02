package pcshop.rules;

import java.util.Optional;
import pcshop.domain.Build;
import pcshop.domain.ComponentType;

public final class GpuPsuWattageRule implements CompatibilityRule {
	@Override
	public Optional<String> validate(Build b) {
		var gpu = b.get(ComponentType.GPU);
		var psu = b.get(ComponentType.PSU);
		if (gpu.isEmpty() || psu.isEmpty())
			return Optional.empty();
		int gpuReq = parseInt(gpu.get().attr("psu_min_watt"), 0);
		int psuWat = parseInt(psu.get().attr("watt"), 0);
		return psuWat >= gpuReq ? Optional.empty()
				: Optional.of("PSU 용량 부족: 최소 " + gpuReq + "W 필요 (현재 " + psuWat + "W)");
	}

	private int parseInt(String s, int d) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return d;
		}
	}
}