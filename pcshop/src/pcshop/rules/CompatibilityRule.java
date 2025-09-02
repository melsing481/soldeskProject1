package pcshop.rules;

import java.util.Optional;
import pcshop.domain.Build;

public interface CompatibilityRule {
	/** 규칙 통과면 빈 Optional, 실패면 실패 사유 */
	Optional<String> validate(Build build);
}