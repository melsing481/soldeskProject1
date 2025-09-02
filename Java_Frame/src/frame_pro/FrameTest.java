package frame_pro;

import java.awt.Color;
import java.awt.Frame;

public class FrameTest {

	public static void main(String[] args) {

		Frame frame = new Frame("첫 프레임"); //프레임 제목
		frame.setBounds(800, 100, 400, 300); //x좌표, y좌표, 너비, 높이
		
		System.out.println(frame.getBounds().getWidth());
		System.out.println(frame.getBounds().height);
		
		frame.setBackground(Color.blue); //배경색 지정
		frame.setVisible(true); 
		
	}

}
