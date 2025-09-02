package frame_pro;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageTest {

	public static void main(String[] args) {
		
		Frame f = new Frame();
		f.setLayout(null);
		f.setBounds(500, 100, 750, 430);
		
		//이미지
		ImageIcon img = new ImageIcon("sul1_1.jpg");
		
		JLabel jl = new JLabel(img);
		jl.setBounds(10, 10, 730, 400);
		f.add(jl);
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
