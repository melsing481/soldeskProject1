package pcshop.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pcshop.cart.Cart;
import pcshop.cart.CartItem;
import pcshop.catalog.Catalog;
import pcshop.catalog.SampleData;
import pcshop.domain.Build;
import pcshop.domain.ComponentType;
import pcshop.domain.OptionView;
import pcshop.domain.Part;
import pcshop.rules.CpuMotherboardSocketRule;
import pcshop.rules.GpuLengthCaseRule;
import pcshop.rules.GpuPsuWattageRule;
import pcshop.rules.RulesEngine;
import pcshop.rules.ValidationResult;

public class CheckoutPanel extends JPanel {
	// 모델들
	private final Catalog catalog = new Catalog(SampleData.catalog());
	private final RulesEngine rules = new RulesEngine().add(new CpuMotherboardSocketRule()).add(new GpuPsuWattageRule())
			.add(new GpuLengthCaseRule());

	private final Build build = new Build();
	private final Cart cart = new Cart();

	// UI 컴포넌트
	private JComboBox<OptionView> cpuBox;
	private JComboBox<OptionView> gpuBox;
	private JComboBox<OptionView> mbBox;
	private JLabel cpuPrice;
	private JLabel gpuPrice;
	private JLabel mbPrice;
	private JLabel totalLabel;
	private Integer budgetTarget=null;//[확인] 으로 확정한 예산
	private JButton payButton;	//견적 화면 결제 버튼
	private JButton cartPayButton;	//장바구니 화면 결제 버튼

	private JTextField budgetField;
	private JLabel remainLabel;
	private CardLayout cards;
	private JPanel cardHost;
	private static final String vem = "estimate";
	private static final String vc = "cart";
	private JPanel cartPanel;
	private JPanel cartListPanel;
	private JLabel cartTotalLabel;

	// 숫자 포맷 (원화)
	private final NumberFormat money = NumberFormat.getInstance(Locale.KOREA);

	public CheckoutPanel() {
		setPreferredSize(new Dimension(900,600)); //부모 프레임에서 pack을 호출하게
		setLayout(new BorderLayout());

		cards = new CardLayout();
		cardHost = new JPanel(cards);
		add(cardHost, BorderLayout.CENTER);
		
		JPanel estimateview=buildEstimateView();
		cardHost.add(estimateview, vem);
		
		cartPanel=buildCartView();
		cardHost.add(cartPanel, vc);
		
		cards.show(cardHost, vem);

		// 초기 셋업
		refreshCombosAll();
		// 기본 선택: 각 콤보의 첫 번째 가능 옵션을 적용
		applyInitialSelection();
		updatePricesAndTotal();
	}

	private JPanel rowSelector(String labelText, ComponentType type) {
		JPanel row = new JPanel(new BorderLayout(8, 8));
		row.setOpaque(false);
		row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

		JLabel left = label(labelText, 14, Color.black);
		left.setPreferredSize(new Dimension(100, 40));
		row.add(left, BorderLayout.WEST);

		JComboBox<OptionView> combo = new JComboBox<>();
		combo.setRenderer(new OptionRenderer());
		combo.setPreferredSize(new Dimension(420, 40));
		row.add(combo, BorderLayout.CENTER);

		JLabel price = priceLabel();
		price.setPreferredSize(new Dimension(140, 40));
		row.add(price, BorderLayout.EAST);

		switch (type) {
		case CPU -> {
			cpuBox = combo;
			cpuPrice = price;
			attachComboHandler(cpuBox, ComponentType.CPU);
		}
		case GPU -> {
			gpuBox = combo;
			gpuPrice = price;
			attachComboHandler(gpuBox, ComponentType.GPU);
		}
		case MOTHERBOARD -> {
			mbBox = combo;
			mbPrice = price;
			attachComboHandler(mbBox, ComponentType.MOTHERBOARD);
		}
		default -> {
		}
		}
		return row;
	}

	private JPanel budgetRow() {
		JPanel row = new JPanel(new BorderLayout(8, 8));
		row.setOpaque(false);
		row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

		JLabel left = label("예산 입력 ▶", 14, Color.black);
		left.setPreferredSize(new Dimension(100,40)); //다른 행의 라벨 폭과 동일
		row.add(left, BorderLayout.WEST);

		budgetField = new JTextField();
		Dimension fieldSize=new Dimension(420,40);
		budgetField.setPreferredSize(fieldSize);
		budgetField.setMinimumSize(fieldSize);
		budgetField.setToolTipText("예: 2000000 (원)");
		row.add(budgetField, BorderLayout.CENTER);
		budgetField.addActionListener(e -> onConfirmBudget());

		
		JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		right.setOpaque(false);
		remainLabel = label("남은 예산: -", 13, Color.black);
		
		JButton confirm = new JButton("확인");
		confirm.addActionListener(e->onConfirmBudget());
		
		JButton reset = new JButton("초기화");
		reset.addActionListener(e->resetBudgetOnly());
		
		right.add(remainLabel);
		right.add(confirm);
		right.add(reset);
		row.add(right, BorderLayout.EAST);
		return row;
	}
	private void onConfirmBudget() {
		String s=budgetField.getText().trim().replaceAll(",", "");
		if(s.isEmpty()) {
			JOptionPane.showMessageDialog(this, "예산을 입력하세요.","입력 필요",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			int parsed=Integer.parseInt(s);
			if(parsed<0) throw new NumberFormatException();
			budgetTarget=parsed; //예산 확정
			renderRemain();
			reevaluatePayButtons(); //결제 버튼 활성/비활성 재평가		
		} catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "숫자만 입력하세요. 예 : 200000","입력 오류",JOptionPane.ERROR_MESSAGE);
		}
	}
	private void resetBudgetOnly() {
		budgetTarget=null;
		budgetField.setText("");
		remainLabel.setText("남은 예산 : -");
		reevaluatePayButtons();
	}
	private void renderRemain() {
		if(budgetTarget==null) {
			remainLabel.setText("남은 예산 : -");
			return;
		}
		int remain=budgetTarget-build.totalPrice();
		remainLabel.setText("남은 예산 : "+money(Math.max(remain, 0))+"원");
	}

	private JPanel totalPanel() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setOpaque(false);
		totalLabel = label("합계: 0원", 16, Color.black);
		p.add(totalLabel);
		return p;
	}

	private JPanel buttonPanel() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
		p.setOpaque(false);
		payButton = new JButton("결제하기");
		payButton.addActionListener(e->JOptionPane.showMessageDialog(this, "결제 되었습니다"));
		
		JButton cartBtn = new JButton("장바구니");
		cartBtn.addActionListener(e -> {
			cart.add(build, 1);
			refreshCartView();
			cards.show(cardHost, vc);
		});
		p.add(payButton);
		p.add(cartBtn);
		return p;
	}

	// 콤보 박스 선택 핸들러: 호환성 검사 후 적용
	private void attachComboHandler(JComboBox<OptionView> box, ComponentType type) {
		box.addActionListener(e -> {
			OptionView ov = (OptionView) box.getSelectedItem();
			if (ov == null)
				return;
			if (!ov.enabled) {
				// 비활성 옵션이면 선택 취소
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this, String.join("\n", ov.reasonsIfDisabled), "선택 불가",
						JOptionPane.WARNING_MESSAGE);
				// 이전 유효 선택으로 되돌리기
				refreshCombosAll();
				return;
			}
			// 실제 교체 시도
			ValidationResult vr = build.tryReplace(type, ov.part, rules);
			if (vr.ok) {
				build.replace(type, ov.part);
				updatePricesAndTotal();
				refreshCombosAll(); // 다른 카테고리도 영향 받으니 갱신
			} else {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this, String.join("\n", vr.errors), "호환성 오류", JOptionPane.ERROR_MESSAGE);
				refreshCombosAll();
			}
		});
	}

	private void refreshCombosAll() {
		refreshCombo(cpuBox, ComponentType.CPU);
		refreshCombo(gpuBox, ComponentType.GPU);
		refreshCombo(mbBox, ComponentType.MOTHERBOARD);
	}

	private void refreshCombo(JComboBox<OptionView> box, ComponentType type) {
		if (box == null)
			return;
		List<OptionView> list = catalog.optionsFor(build, type, rules);
		OptionView current = null;
		if (build.get(type).isPresent()) {
			Part p = build.get(type).get();
			for (OptionView ov : list)
				if (ov.part.id.equals(p.id)) {
					current = ov;
					break;
				}
		}
		DefaultComboBoxModel<OptionView> model = new DefaultComboBoxModel<>();
		for (OptionView ov : list)
			model.addElement(ov);
		box.setModel(model);
		if (current != null)
			box.setSelectedItem(current);
	}

	private void applyInitialSelection() {
		// 각 콤보에서 "enabled=true"인 첫 항목을 골라 빌드에 반영
		applyFirstEnabled(cpuBox, ComponentType.CPU);
		applyFirstEnabled(gpuBox, ComponentType.GPU);
		applyFirstEnabled(mbBox, ComponentType.MOTHERBOARD);
	}

	private void applyFirstEnabled(JComboBox<OptionView> box, ComponentType type) {
		if (box == null || box.getItemCount() == 0)
			return;
		for (int i = 0; i < box.getItemCount(); i++) {
			OptionView ov = box.getItemAt(i);
			if (ov.enabled) {
				build.replace(type, ov.part);
				box.setSelectedIndex(i);
				break;
			}
		}
	}

	private void updatePricesAndTotal() {
		cpuPrice.setText(priceText(build.get(ComponentType.CPU)));
		gpuPrice.setText(priceText(build.get(ComponentType.GPU)));
		mbPrice.setText(priceText(build.get(ComponentType.MOTHERBOARD)));
		totalLabel.setText("합계: " + money(build.totalPrice()) + "원");
		renderRemain();
		reevaluatePayButtons();
	}

	private String priceText(Optional<Part> p) {
		return p.map(part -> money(part.price) + "원").orElse("000원");
	}
	private JPanel cartItemCard(CartItem item) {
		JPanel card = new JPanel(new BorderLayout(8, 8));
		card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
				new EmptyBorder(8, 8, 8, 8)));

		// 썸네일 플레이스홀더
		JPanel thumb = new JPanel();
		thumb.setPreferredSize(new Dimension(64, 64));
		thumb.setBackground(new Color(230, 230, 230));
		thumb.add(new JLabel("IMG"));
		card.add(thumb, BorderLayout.WEST);

		// 설명
		String title = item.build.get(ComponentType.CPU).map(p -> p.name).orElse("CPU") + " / "
				+ item.build.get(ComponentType.GPU).map(p -> p.name).orElse("GPU") + " / "
				+ item.build.get(ComponentType.MOTHERBOARD).map(p -> p.name).orElse("MB");
		JLabel name = new JLabel(title);
		
		JPanel centerPanel=new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(new JLabel(title));
		
		JLabel itemSubtotalLabel=new JLabel(money(item.subtotal())+"원");
		
		JPanel partPanel=new JPanel();
		partPanel.setOpaque(false);
		partPanel.setLayout(new BoxLayout(partPanel, BoxLayout.Y_AXIS));
		
		for(ComponentType t: item.build.asMap().keySet()) {
			var
		}
		
		card.add(name, BorderLayout.CENTER);

		// 수량/삭제/소계
		JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		JSpinner qty = new JSpinner(new SpinnerNumberModel(item.quantity, 1, 99, 1));
		JLabel price=new JLabel(money(item.subtotal())+"원");
		qty.addChangeListener(e -> {
			cart.updateQuantity(item.id, (Integer) qty.getValue());
			price.setText(money(item.subtotal())+"원");		//소계 즉시 갱신
			cartTotalLabel.setText("최종 결제 금액 : "+money(cart.total())+"원");	//총액 즉시 갱신
			reevaluatePayButtons();
		});
		JButton remove = new JButton("삭제");
		remove.addActionListener(e -> {
			cart.updateQuantity(item.id, 0);
			refreshCartView();
		});
		right.add(new JLabel("수량"));
		right.add(qty);
		right.add(price);
		right.add(remove);
		card.add(right, BorderLayout.EAST);
		return card;
	}

	// ==== 유틸 ====
	private JLabel label(String text, int size, Color color) {
		JLabel l = new JLabel(text);
		l.setFont(l.getFont().deriveFont(Font.BOLD, size));
		l.setForeground(color);
		return l;
	}

	private JLabel priceLabel() {
		JLabel l = new JLabel("000원");
		l.setOpaque(true);
		l.setBackground(new Color(245, 245, 245));
		l.setBorder(new EmptyBorder(6, 12, 6, 12));
		return l;
	}

	private String money(int v) {
		return money.format(v);
	}

	// 옵션 렌더러: 비활성 옵션은 회색 처리 + (선택 불가: 사유) 표시
	private static class OptionRenderer extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof OptionView ov) {
				String name = ov.part.name + " - " + NumberFormat.getInstance(Locale.KOREA).format(ov.part.price) + "원";
				if (ov.enabled) {
					c.setText(name);
					c.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
				} else {
					String reason = ov.reasonsIfDisabled.isEmpty() ? "선택 불가" : String.join("; ", ov.reasonsIfDisabled);
					c.setText(name + "  (" + reason + ")");
					c.setForeground(Color.GRAY);
				}
			}
			return c;
		}
	}

	private JPanel buildEstimateView() {
		JPanel root = new JPanel(new BorderLayout());
		root.setBorder(new EmptyBorder(16, 16, 16, 16));
		root.setBackground(new Color(189, 233, 253));

		// 상단 타이틀
		JLabel title = label("나의 견적", 20, Color.black);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(new EmptyBorder(8, 8, 8, 8));
		root.add(title, BorderLayout.NORTH);

		// 중앙: 선택 영역
		JPanel center = new JPanel();
		center.setOpaque(false);
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.add(rowSelector("CPU", ComponentType.CPU));
		center.add(Box.createVerticalStrut(8));
		center.add(rowSelector("그래픽카드", ComponentType.GPU));
		center.add(Box.createVerticalStrut(8));
		center.add(rowSelector("메인보드", ComponentType.MOTHERBOARD));
		center.add(Box.createVerticalStrut(12));
		center.add(budgetRow());
		center.add(Box.createVerticalStrut(20));

		// 아이콘/마크 자리 (플레이스홀더)
		JPanel badge = new JPanel();
		badge.setPreferredSize(new Dimension(360, 120));
		badge.setMaximumSize(new Dimension(360, 120));
		badge.setBackground(new Color(53, 120, 230));
		badge.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 180)));
		JLabel badgeLabel = label("조 아이콘 혹은 마크 같은거", 14, Color.black);
		badge.add(badgeLabel);
		center.add(badge);
		root.add(center, BorderLayout.CENTER);

		// 하단: 합계 + 버튼들
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setOpaque(false);
		bottom.add(totalPanel(), BorderLayout.WEST);
		bottom.add(buttonPanel(), BorderLayout.EAST);
		root.add(bottom, BorderLayout.SOUTH);
		
		return root;

	}
	private JPanel buildCartView() {
		JPanel root = new JPanel(new BorderLayout(8,8));
		root.setBorder(new EmptyBorder(16, 16, 16, 16));
		root.setBackground(new Color(189, 233, 253));
		
		//상단 : 제목 + 돌아가기
		JPanel north=new JPanel(new BorderLayout());
		north.setOpaque(false);
		JLabel title=label("장바구니", 20, Color.black);
		JButton back=new JButton("견적 화면");
		back.addActionListener(e->cards.show(cardHost, vem));
		north.add(title, BorderLayout.WEST);
		north.add(back, BorderLayout.EAST);
		root.add(north, BorderLayout.NORTH);
		
		//중앙 : 리스트
		cartListPanel=new JPanel();
		cartListPanel.setOpaque(false);
		cartListPanel.setLayout(new BoxLayout(cartListPanel, BoxLayout.Y_AXIS));
		JScrollPane scroll=new JScrollPane(cartListPanel);
		root.add(scroll, BorderLayout.CENTER);
		
		//하단 : 총액 + 결제
		JPanel south=new JPanel(new BorderLayout());
		south.setOpaque(false);
		cartTotalLabel=label("최종 결제 금액 : 0원", 16, Color.black);
		cartPayButton=new JButton("결제하기");
		cartPayButton.addActionListener(e->JOptionPane.showMessageDialog(this, "결제 진행(모형)"));
		south.add(cartTotalLabel, BorderLayout.WEST);
		south.add(cartPayButton, BorderLayout.EAST);
		root.add(south, BorderLayout.SOUTH);
		
		return root;
			
	}
	private void reevaluatePayButtons() {
		boolean canPayEstimate=(budgetTarget !=null)&&(build.totalPrice()<=budgetTarget);
		if(payButton!=null)payButton.setEnabled(canPayEstimate);
		
		boolean canPayCart=(budgetTarget !=null)&&(cart.total()<=budgetTarget);
		if(cartPayButton!=null)cartPayButton.setEnabled(canPayCart);
	}
	private void refreshCartView() {
		cartListPanel.removeAll();
		for(CartItem it : cart.items()) {
			cartListPanel.add(cartItemCard(it));
			cartListPanel.add(Box.createVerticalStrut(8));
		}
		cartTotalLabel.setText("최종 결제 금액 : "+money(cart.total())+"원");
		cartListPanel.revalidate();
		cartListPanel.repaint();
		reevaluatePayButtons();

	}

}
