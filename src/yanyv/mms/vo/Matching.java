package yanyv.mms.vo;

import java.awt.Font;
import java.awt.Panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import yanyv.mms.view.Person;
import yanyv.mms.view.Round;

public class Matching {

	private Person p1;
	private Person p2;
	JLabel vs;
	int x, y;

	private boolean finish = false;

	Round round;

	private Font font = new Font("¿¬Ìå", Font.BOLD, 25);

	public Matching(Person p1, Person p2) {
		this.setP1(p1);
		this.setP2(p2);
		p1.setMatch(this);
		p2.setMatch(this);
		vs = new JLabel("VS", JLabel.CENTER);
		vs.setSize(200, 40);
		vs.setFont(font);
	}

	public Matching(Person p1) {
		this.setP1(p1);
		p1.setMatch(this);
		vs = new JLabel("ÂÖ¿Õ", JLabel.CENTER);
		vs.setSize(200, 40);
		vs.setFont(font);

	}

	public Matching(JSONArray info) {
		System.out.println(info);
		setP1(new Person(info.getJSONObject(0).getString("name")));
		getP1().setMatch(this);
		vs = new JLabel("ÂÖ¿Õ", JLabel.CENTER);
		vs.setSize(200, 40);
		vs.setFont(font);

		if (info.length() != 1) {
			setP2(new Person(info.getJSONObject(1).getString("name")));
			getP2().setMatch(this);
			vs.setText("VS");
		}

	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		getP1().setLocation(x, y);
		vs.setLocation(x, y + 25);
		if (getP2() != null)
			getP2().setLocation(x, y + 60);
	}

	public void addin(Round round) {
		this.round = round;
		round.add(getP1());
		round.add(vs);
		if (getP2() != null)
			round.add(getP2());

		if (getP2() == null) {

			getP1().setWin(true);
			setFinish(true);
			JLabel l = new JLabel("<html><body>½ú¼¶<br>--¡ú<body></html>", JLabel.CENTER);
			l.setSize(150, 80);
			l.setLocation(vs.getLocation().x + 160, getP1().getLocation().y);
			l.setFont(font);

			Person win = new Person(getP1().getText());

			win.setLocation(vs.getLocation().x + 270, getP1().getLocation().y);

			round.add(l);
			round.add(win);
			round.repaint();
		}
	}

	public void win(Person p) {
		if (getP2() == null) {

		} else if (!isFinish()) {

			setFinish(true);

			if (getP1().getText().equals(p.getText())) {
				getP2().setEnabled(false);
			} else {
				getP1().setEnabled(false);
			}

			JLabel l = new JLabel("<html><body>½ú¼¶<br>--¡ú<body></html>", JLabel.CENTER);
			l.setSize(150, 80);
			l.setLocation(vs.getLocation().x + 160, getP1().getLocation().y);
			l.setFont(font);

			Person win = new Person(p.getText());

			win.setLocation(vs.getLocation().x + 270, vs.getLocation().y);

			round.add(l);
			round.add(win);
			round.repaint();

			round.next();
		}
	}

	public String getWinner() {

		if (getP1().isWin()) {
			return getP1().getText();
		} else if(getP2().isWin()) {
			return getP2().getText();
		} else return null;
	}

	public String getLoser() {

		if (getP2() == null)
			return null;
		if (!getP1().isWin() && !getP2().isWin()) return null;
		if (!getP1().isWin()) {
			return getP1().getText();
		} else if(!getP2().isWin()){
			return getP2().getText();
		}
		
		return null;
	}

	public JSONArray save() {
		JSONArray json = new JSONArray();
		json.put(getP1().save());
		if (getP2() != null)
			json.put(getP2().save());

		return json;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public Person getP1() {
		return p1;
	}

	public void setP1(Person p1) {
		this.p1 = p1;
	}

	public Person getP2() {
		return p2;
	}

	public void setP2(Person p2) {
		this.p2 = p2;
	}
}
