package layout;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyBoarderLayout {

	Frame f = new Frame();
	
	Button jb1 = new Button("북");
	Button jb2 = new Button("서");
	Button jb3 = new Button("중앙");
	Button jb4 = new Button("동");
	Button jb5 = new Button("남");
	Button jb6 = new Button("버튼6");
	
	public MyBoarderLayout() {

		f.setLayout(new BorderLayout());
		
		f.add(jb1, BorderLayout.NORTH);
		f.add(jb2, BorderLayout.WEST);
		f.add(jb3, BorderLayout.CENTER);
		f.add(jb4, BorderLayout.EAST);
		f.add(jb5, BorderLayout.SOUTH);
		f.add(jb6, "Center");
		
		f.setSize(400, 300);
		f.setVisible(true);
		
		//종료 버튼 
		f.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
	}
	
	public static void main(String[] args) {
		
		new MyBoarderLayout();
		
	}
	
}
