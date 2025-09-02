package pcshop.rules;

import java.util.Optional;
import pcshop.domain.Build;

import pcshop.domain.ComponentType;

public final class CpuMotherboardSocketRule implements CompatibilityRule {
	@Override
	public Optional<String> validate(Build b) {
		var cpu = b.get(ComponentType.CPU);
		var mb = b.get(ComponentType.MOTHERBOARD);
		if (cpu.isEmpty() || mb.isEmpty())
			return Optional.empty();
		String cpuSock = cpu.get().attr("socket");
		String mbSock = mb.get().attr("socket");
		if (cpuSock == null || mbSock == null)
			return Optional.of("소켓 정보가 부족합니다.");
		return cpuSock.equalsIgnoreCase(mbSock) ? Optional.empty()
				: Optional.of("CPU/메인보드 소켓 불일치: " + cpuSock + " vs " + mbSock);
	}
}