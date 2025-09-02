package frame_pro;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JOptionPane;

public class TextFieldTest {

	public static void main(String[] args) {
		
		//프레임 설정
		Frame frame = new Frame("문장 입력");
		frame.setBounds(800, 100, 250, 400);
		frame.setBackground(Color.CYAN);
		
		//폰트 설정
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN , 18);
		frame.setFont(font);

		//북쪽 
		Panel pNorth = new Panel();
		pNorth.setBackground(Color.GREEN);
		
		TextField tf = new TextField(10); //입력 상자의 길이
		Button btn = new Button("입력");
		
		btn.setEnabled(false);
		
		pNorth.add(tf);
		pNorth.add(btn);
		pNorth.setFont(font);
		
		//중앙
		TextArea ta = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		ta.setBackground(Color.LIGHT_GRAY);
		ta.setFont(font);
		ta.setEnabled(false);
		
		//남쪽
		Panel pSouth = new Panel();
		pSouth.setFont(font);
		pSouth.setBackground(Color.MAGENTA);
		
		Button btnSave = new Button("저장");
		Button btnClose = new Button("닫기");
		pSouth.add(btnSave);
		pSouth.add(btnClose);
		
		//컴포넌트 예시
		frame.add(pNorth, BorderLayout.NORTH);
		frame.add(ta, BorderLayout.CENTER);
		frame.add(pSouth, BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.setResizable(false);
		
		//------------------------이벤트
		
		tf.addTextListener(new TextListener() {
			
			@Override
			public void textValueChanged(TextEvent e) {
		
				if(tf.getText().equals("")) {
					btn.setEnabled(false);
				}else {
					btn.setEnabled(true);
				}
				
			}
		});
		
		//엔터를 누르면 텍스트필드 내용이 비워지고, 아래에 추가됨
		tf.addKeyListener(new KeyAdapter() {
		
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					ta.append(tf.getText() + "\n");
					tf.setText("");
					tf.requestFocus();
				}
			}
			
		});
		
		//입력 버튼 클릭시 아래의 내용 추가
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ta.append(tf.getText() + "\n");
				tf.setText("");
				tf.requestFocus();
				
			}
			
		});
		
		//닫기버튼
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		
				System.exit(0);
				
			}
		});
		
		//저장버튼
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		
				String message = ta.getText();
				
				try {
					//FileDialog 저장이나 로드할 떄 사용하는 대화상자. 경로 구해옴
					FileDialog fd = new FileDialog(frame, "저장", FileDialog.SAVE);
					fd.setVisible(true);
					
					String path = fd.getDirectory() + fd.getFile();
					if(!message.equals("")) {
						
						//스트림으로 path 경로에 텍스트 저장
						FileWriter fw = new FileWriter(path);
						BufferedWriter bw = new BufferedWriter(fw);
						
						bw.write(message);		
						
						if(fd.getFile() != null) {
							JOptionPane.showConfirmDialog(frame, path + "\n에 저장했습니다");
						}
						bw.close();
						
						//기록할 내용이 없는 경우
					}else {
						JOptionPane.showConfirmDialog(frame, "저장할 내용이 없습니다");
					}
					
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
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
