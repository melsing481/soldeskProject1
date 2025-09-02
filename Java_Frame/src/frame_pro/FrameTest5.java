package frame_pro;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FrameTest5 {

	public static void main(String[] args) {

		MyFrame fr = new MyFrame();
		fr.setBackground(Color.yellow);
		fr.setTitle("테스트 프레임");
		
		//fr.addWindowListener(new MyEventListener());
		//fr.addWindowListener(new WinClosingListener());
		
		fr.addWindowListener(new WindowAdapter() {
		
			@Override
			public void windowClosing(WindowEvent e) {
			
				System.out.println("어댑터 생성하여 감지함");
				System.exit(0);
				
			}
		
		});
		
		
	}

}
