package layout;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ButtonTest03 {

	public static void main(String[] args) {

		Frame f = new Frame();
		
		f.setBounds(500, 100, 800, 250);
		f.setLayout(new FlowLayout());
		
		Label q1 = new Label("1. 관심 분야는 무엇인가요?");
		Checkbox news = new Checkbox("new", true);		
		Checkbox sports = new Checkbox("sports");
		Checkbox movie = new Checkbox("movie");
		Checkbox music = new Checkbox("music");
		
		//체크 박스 선택여부 판단
		news.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
			
				String str = e.getStateChange() == 1 ? "뉴스 선택" : "뉴스 선택 해제";
				System.out.println(str);			
					
			}
			
		});
		
		//폰트설정
		Font font = new Font("맑은 고딕", Font.BOLD, 30);
		q1.setFont(font);
		f.add(q1);
		f.add(news);
		f.add(sports);
		f.add(movie);		
		f.add(music);
			
		Label q2 = new Label("2. 한 달에 영화를 얼마나 자주 보나요?");
		CheckboxGroup group = new CheckboxGroup();
		Checkbox one = new Checkbox("한 번", group, true);
		Checkbox two = new Checkbox("두 번", group, true);
		Checkbox three = new Checkbox("세 번", group, true);
			
		//라디오 버튼 선택 여부 판단
		one.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println("한 번 봅니다.");
				
			}
		});
		
		two.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println("두 번 봅니다.");
				
			}
		});
		
		q2.setFont(font);
		f.add(q2);
		f.add(one);
		f.add(two);
		f.add(three);
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
