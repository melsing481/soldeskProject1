package layout;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

//이벤트 분리
public class ChoiceHandler implements ItemListener {

	@Override
	public void itemStateChanged(ItemEvent e) {
	
		String str = (String)e.getItem();
		System.out.println("지역 : " + str);
		
		
	}
	
}
