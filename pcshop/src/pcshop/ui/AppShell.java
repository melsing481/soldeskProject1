package pcshop.ui;

import javax.swing.*;
import java.awt.*;

public class AppShell extends JFrame {

    public AppShell() {
        super("우리 팀 프로젝트 - Checkout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 네가 만든 JPanel 화면을 프레임에 꽂기
        CheckoutPanel checkout = new CheckoutPanel();
        add(checkout, BorderLayout.CENTER);

        // 프레임 크기/위치
        pack();                              // CheckoutPanel의 preferredSize(960x540) 적용
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);         // 화면 가운데
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // OS 기본 룩앤필 적용(선택)
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}

            new AppShell().setVisible(true);
        });
    }
}
