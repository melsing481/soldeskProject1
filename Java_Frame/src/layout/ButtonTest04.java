package layout;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ButtonTest04 {

	public static void main(String[] args) {

		Frame f = new Frame("질문");
		f.setSize(500, 250);
		f.setLocation(400, 100);
		f.setLayout(null);
		
		Choice day = new Choice();
		
		day.add("SUN");
		day.add("MON");
		day.add("TUE");
		day.add("WEN");
		day.add("TRU");
		day.add("FRI");
		day.add("SAT");
		
		day.setSize(150, 0);
		day.setLocation(50, 100);
		
		//선택할 때 호출되는 이벤트 리스너
		day.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				System.out.println("요일 :" + day.getSelectedItem());	
				
			}
		});
		
		f.add(day);
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
