package frame_pro;

import javax.swing.JFrame;

public class FrameTest2 {

	public static void main(String[] args) {
		
		JFrame fr = new JFrame();
		fr.setSize(400, 400); //사이즈
		fr.setLocation(800, 100); //좌표
		
		//창닫기
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
		
		//하나의 프레임만 종료할 때
		//fr.dispose();
		
	}
	
}
