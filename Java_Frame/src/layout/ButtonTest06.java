package layout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ButtonTest06 {

	public static void main(String[] args) {

		Frame frame = new Frame("제목");
		
		//버튼 배열로 준비
		Button [] arbtn = new Button[5];
		
		String [] btnTitles = {"닫기", "열기", "취소", "확인", "안녕"};
		
		for(int i = 0; i < arbtn.length; i++) {
			arbtn[i] = new Button(btnTitles[i]);
		}
		
		//Frame도 위치선정 가능
		frame.add(arbtn[0], BorderLayout.EAST);
		frame.add(arbtn[1], BorderLayout.WEST);
		frame.add(arbtn[2], BorderLayout.SOUTH);
		frame.add(arbtn[3], BorderLayout.NORTH);
		frame.add(arbtn[4], BorderLayout.CENTER);
		
		//프레임에 내용이 있을 때 해당 내용만큼만 영역확보
		frame.pack();
		frame.setVisible(true);
		
		//동쪽에 위치한 닫기 버튼에 닫기 기능 추가
		arbtn[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		//종료 버튼 
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
		
	}

}
