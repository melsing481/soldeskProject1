package frame_pro;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

public class FrameTest3 {

	public static void main(String[] args) {
		//화면 중앙에 프레임 위치 시키기
		Frame fr = new Frame();
		fr.setSize(400, 400);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension di = tk.getScreenSize();
		
		int monitorW = di.width;
		int monitorH = di.height;
		
		int x = monitorW / 2 - fr.getWidth();
		int y = monitorH / 2 - fr.getHeight();
		
		fr.setLocation(x, y);
		fr.setVisible(true);
		
		
		
	}

}
