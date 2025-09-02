package pcshop.rules;

import java.util.Optional;
import pcshop.domain.Build;

import pcshop.domain.ComponentType;

public final class GpuLengthCaseRule implements CompatibilityRule
{

	@Override
	public Optional<String> validate(Build b) {
		var gpu = b.get(ComponentType.GPU);
		var cs = b.get(ComponentType.CASE);
		if (gpu.isEmpty() || cs.isEmpty())
			return Optional.empty();
		int gl = parseInt(gpu.get().attr("length_mm"), 0);
		int ml = parseInt(cs.get().attr("gpu_max_len_mm"), Integer.MAX_VALUE);
		return gl <= ml ? Optional.empty() : Optional.of("케이스 GPU 길이 제한 초과: " + gl + "mm > " + ml + "mm");
	}

	private int parseInt(String s, int d) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return d;
		}
	}
}