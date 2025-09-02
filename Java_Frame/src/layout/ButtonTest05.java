package layout;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ButtonTest05 {

	public static void main(String[] args) {
		
		Frame f = new Frame("지역 선택");
		f.setBounds(400, 100, 500, 250);
		f.setLayout(null);
		
		Choice region = new Choice();
		region.add("지역선택");
		region.add("서울");
		region.add("경기");
		region.add("인천");
		region.add("광주");
		region.add("대구");
		region.add("대전");
		region.add("부산");
		
		region.setSize(100, 0);
		region.setLocation(50, 100);;
		
		region.addItemListener(new ChoiceHandler());
		
		f.add(region);
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
