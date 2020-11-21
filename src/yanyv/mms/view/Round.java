package yanyv.mms.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import yanyv.mms.Window.MatchWindow;
import yanyv.mms.vo.Account;
import yanyv.mms.vo.Matching;

public class Round extends JPanel {

	private Round next;

	private int top = 70;
	private boolean fuhuo;
	private boolean opening = false;

	JScrollPane js;

	JLabel tit;
	JTextField titEdit;

	int round = 0;
	private ArrayList<Matching> m;

	ArrayList<Account> fuhuolist;
	ArrayList<Account> list;

	private Font font = new Font("楷体", Font.BOLD, 30);
	String title = "";
	
	public Round() {
		
	}

	public Round(int round, ArrayList<Account> list) {
		this.round = round;
		this.list = list;

		Collections.shuffle(list);

		setM(new ArrayList<Matching>());

		this.setLayout(null);
		this.setSize(480, 100000);
		this.setLocation(round * 480 + 20, 0);

		tit = new JLabel(title, JLabel.CENTER);
		tit.setSize(this.getSize().width, 50);
		tit.setLocation(0, 0);
		tit.setFont(font);
		tit.setOpaque(true);
		this.add(tit);

		titEdit = new JTextField();
		titEdit.setHorizontalAlignment(JTextField.CENTER);
		titEdit.setSize(this.getSize().width, 50);
		titEdit.setLocation(0, 0);
		titEdit.setFont(font);
		titEdit.setVisible(false);
		this.add(titEdit);

		Matching match;
		for (int i = 0; i < list.size(); i += 2) {
			if (i == list.size() - 1) {
				match = new Matching(new Person(list.get(i)));
			} else {
				match = new Matching(new Person(list.get(i)), new Person(list.get(i + 1)));
			}
			match.setLocation(0, top);
			match.addin(this);
			getM().add(match);
			top += 100;
		}

		addListener();

	}

	public Round(int round, JSONObject info) {
		opening = true;
		
		this.round = round;
		title = info.getString("title");

		setM(new ArrayList<Matching>());

		this.setLayout(null);
		this.setSize(480, 100000);
		this.setLocation(round * 480 + 20, 0);

		tit = new JLabel(title, JLabel.CENTER);
		tit.setSize(this.getSize().width, 50);
		tit.setLocation(0, 0);
		tit.setFont(font);
		tit.setOpaque(true);
		this.add(tit);

		titEdit = new JTextField();
		titEdit.setHorizontalAlignment(JTextField.CENTER);
		titEdit.setSize(this.getSize().width, 50);
		titEdit.setLocation(0, 0);
		titEdit.setFont(font);
		titEdit.setVisible(false);
		this.add(titEdit);

		Matching match;

		JSONArray matchs = info.getJSONArray("matchs");

		for (int i = 0; i < matchs.length(); i++) {
			match = new Matching(matchs.getJSONArray(i));
			match.setLocation(0, top);
			match.addin(this);
			getM().add(match);
			top += 100;
			
			if(matchs.getJSONArray(i).getJSONObject(0).getBoolean("won")) {
				match.getP1().setWin(true);
				match.win(new Person(new Account(matchs.getJSONArray(i).getJSONObject(0).getString("name"))));
			} else if(matchs.getJSONArray(i).getJSONObject(1).getBoolean("won")) {
				match.getP2().setWin(true);
				match.win(new Person(new Account(matchs.getJSONArray(i).getJSONObject(1).getString("name"))));
			}
			
		}
		opening = false;

		addListener();

	}

	public void setJs(JScrollPane js) {
		this.js = js;
	}

	public void next() {

		boolean allfinish = true;
		for (Matching m : this.getM()) {
			if (!m.isFinish())
				allfinish = false;
		}

		if (allfinish && !opening) {

			doNext();

		}

	}
	
	private void doNext() {
		if (fuhuo && round == 0)
			fuhuo();
		else if (fuhuo && round == 1) {
			fuhuoend();
		} else {
			ArrayList<Account> list = new ArrayList<Account>();
			for (Matching m : this.getM()) {
				if(m.getWinner() != null && !"".equals(m.getWinner())) list.add(new Account(m.getWinner()));
			}

			Round r = new Round(round + 1, list);
			r.setJs(js);
			if (list.size() > 1) {
				this.getParent().add(r);
				setNext(r);
				r.setFuhuo();
			}
		}
		refresh();
	}
	
	public void refresh() {

		getParent().setPreferredSize(new Dimension((round + 2) * 500, getParent().getPreferredSize().height));
		this.getParent().repaint();

		js.validate();
	}

	private void fuhuo() {
		ArrayList<Account> list = new ArrayList<Account>();
		fuhuolist = new ArrayList<Account>();
		for (Matching m : this.getM()) {
			if (m.getLoser() != null && !"".equals(m.getLoser())) list.add(new Account(m.getLoser()));
			if(m.getWinner() != null && !"".equals(m.getWinner())) fuhuolist.add(new Account(m.getWinner()));
		}

		Round r = new Round(round + 1, list);
		r.setJs(js);
		r.fuhuolist = this.fuhuolist;
		if (list.size() > 1) {
			this.getParent().add(r);
			setNext(r);
			r.setFuhuo();
		}
		getParent().setPreferredSize(new Dimension((round + 2) * 420, getParent().getPreferredSize().height));
		this.getParent().repaint();

		js.validate();
	}

	private void fuhuoend() {
		ArrayList<Account> list = new ArrayList<Account>();
		for (Matching m : this.getM()) {
			if(m.getWinner() != null && !"".equals(m.getWinner())) list.add(new Account(m.getWinner()));
		}

		list.addAll(fuhuolist);

		Round r = new Round(round + 1, list);
		r.setJs(js);
		if (list.size() > 1) {
			this.getParent().add(r);
			setNext(r);
			r.setFuhuo();
		}
		getParent().setPreferredSize(new Dimension((round + 2) * 420, getParent().getPreferredSize().height));
		this.getParent().repaint();

		js.validate();
	}

	private void addListener() {
		tit.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					titEdit.setText(tit.getText());
					tit.setVisible(false);
					titEdit.setVisible(true);
				}
				
				if(e.getButton() == 3) {
					doNext();
				}
			}

		});

		titEdit.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					title = titEdit.getText();
					tit.setText(title);
					titEdit.setVisible(false);
					tit.setVisible(true);
				}
			}

		});
	}

	public void setFuhuo() {
		this.fuhuo = ((MatchWindow) (this.getParent().getParent().getParent().getParent().getParent().getParent()))
				.getFuhuo();

		if (list != null) {
			if (round == 0) {
				title = "Round " + (round + 1);
			} else if (fuhuo && round == 1) {
				title = "复活赛";
			} else if (list.size() <= 2) {
				title = "决赛";
			} else if (list.size() <= 4) {
				title = "半决赛";
			} else if (list.size() <= 8) {
				title = "八强";
			} else if (list.size() <= 16) {
				title = "十六强";
			} else {
				title = "Round " + round;
			}

			tit.setText(title);
		}

	}

	public JSONObject save() {
		JSONObject json = new JSONObject();
		json.put("title", title);

		JSONArray matchs = new JSONArray();
		for (Matching i : getM()) {
			matchs.put(i.save());
		}

		json.put("matchs", matchs);

		return json;
	}

	public Round getNext() {
		return next;
	}

	public void setNext(Round next) {
		this.next = next;
	}

	public ArrayList<Matching> getM() {
		return m;
	}

	public void setM(ArrayList<Matching> m) {
		this.m = m;
	}

}
