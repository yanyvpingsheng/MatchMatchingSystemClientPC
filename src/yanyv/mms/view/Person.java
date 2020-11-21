package yanyv.mms.view;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import org.json.JSONObject;

import yanyv.mms.Window.MainWindow;
import yanyv.mms.Window.MatchWindow;
import yanyv.mms.vo.Account;
import yanyv.mms.vo.Matching;

public class Person extends JButton {
	
	private Font font = new Font("¿¬Ìå",Font.BOLD,15);
	
	Person p = this;
	Matching m;
	Account acc;
	
	private boolean win = false;
	
	public Person(Account acc) {
		this.acc = acc;
		this.setText(acc.getName());
		this.setSize(200, 30);
		this.setFont(font);
		this.setMargin(new Insets(0, 0, 0, 0));
		if(MatchWindow.isWeb) {
			this.setToolTipText(acc.getUid()+"");
		} else {
			this.setToolTipText(acc.getName());
		}
		
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				setWin(true);
				if(m != null) m.win(p);
			}
			
		});
	}
	
	public void setMatch(Matching m) {
		this.m = m;
	}
	
	public JSONObject save() {
		JSONObject json = new JSONObject();
		json.put("name", this.getText());
		json.put("won", this.isWin());
		
		return json;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
	
}
