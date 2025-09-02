package frame_pro;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuTest {

	public static void main(String[] args) {

		Frame f = new Frame("메뉴");
		f.setSize(300, 200);
		
		MenuBar mb = new MenuBar();
		
		Menu mFile = new Menu("파일");
		MenuItem miNew = new MenuItem("New");
		MenuItem miOpen = new MenuItem("Open");
		MenuItem miSave = new MenuItem("Save");
		
		Menu print = new Menu("Print");
		MenuItem printSetUp = new MenuItem("프린트 셋업");
		MenuItem printPre = new MenuItem("미리보기");
		
		print.add(printSetUp);
		print.add(printPre);
		
		MenuItem miClose = new MenuItem("Close");
				
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.add(print);
		mFile.add(miClose);
		
		//메뉴바에 메뉴 추가
		mb.add(mFile); //추가
		
		//메뉴 설정
		f.setMenuBar(mb);
		f.setVisible(true);
		
		//이벤트 등록
		printSetUp.addActionListener(new MyMenu());
		miClose.addActionListener(new MyMenu());
		
		//종료 버튼 
		f.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
	}

}
