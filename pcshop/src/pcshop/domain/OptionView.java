package pcshop.domain;

import java.util.List;

public final class OptionView {
	public final Part part;
	public final boolean enabled;
	public final List<String> reasonsIfDisabled;

	public OptionView(Part p, boolean enabled, List<String> reasons) {
		this.part = p;
		this.enabled = enabled;
		this.reasonsIfDisabled = List.copyOf(reasons);
	}
}