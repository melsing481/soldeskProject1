package layout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ButtonTest02 {

	public static void main(String[] args) {

		Frame f = new Frame();
		f.setBounds(800, 100, 1000, 500);
		f.setLayout(new FlowLayout());

		Button btn1 = new Button("1");
		Button btn2 = new Button("2");
		Button btn3 = new Button("3");
		Button btn4 = new Button("4");

		// 버튼 크기 변경
		btn1.setPreferredSize(new Dimension(200, 100));
		btn2.setPreferredSize(new Dimension(200, 100));
		btn3.setPreferredSize(new Dimension(200, 100));
		btn4.setPreferredSize(new Dimension(200, 100));

		f.add(btn1);
		f.add(btn2);
		f.add(btn3);
		f.add(btn4);
		f.setVisible(true);

		// 이벤트 감지자 등록
		btn1.addActionListener(al);
		btn2.addActionListener(al);
		btn3.addActionListener(al);
		btn4.addActionListener(al);

		// 종료 버튼
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}//m

	
	static ActionListener al = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			/*
			System.out.println(e);
			System.out.println(e.getActionCommand()); //버튼에 부착된 이름
			System.out.println(e.getID());
			System.out.println(e.getSource());
			*/
			if(e.getActionCommand().equals("1")) {
				System.out.println("1번 누름");
			}else if(e.getActionCommand().equals("2")) {
				System.out.println("2번 누름");
			}else if(e.getActionCommand().equals("3")) {
				System.out.println("3번 누름");
			}else if(e.getActionCommand().equals("4")) {
				System.out.println("4번 누름");
			}
			System.out.println("---------------");
		}
	};
}