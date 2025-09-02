package layout;

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ButtonTest01 {

	public static void main(String[] args) {
		
		Frame f = new Frame("버튼 테스트");
		
		f.setBounds(200, 200, 400, 400);
		f.setLayout(null);
		
		
		Button btnOk = new Button("확인");
		btnOk.setBounds(70, 90, 100, 50);
		
		Button btnClose = new Button("닫기");
		btnClose.setBounds(btnOk.getBounds());
		
		btnClose.setLocation(btnOk.getWidth() + btnOk.getX() + 60
				, btnOk.getY());
		
		f.add(btnOk);
		f.add(btnClose);
		
		f.setVisible(true);
		
		//종료 버튼 
		f.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
	}
	
}
