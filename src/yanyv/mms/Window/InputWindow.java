package yanyv.mms.Window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import yanyv.mms.view.PicShower;

public class InputWindow extends JFrame {

	private int count = 2;
	private int incount = 0;
	private String name = "";
	private boolean fuhuo = false;
	
	private boolean web = false;
	private String mid = "";

	private static int windowWidth = 500;
	private static int windowHeight = 300;

	boolean applyed = false;

	JFrame main = this;
	JPanel mainPane;

	JLabel progress;

	JScrollPane listPane;

	DefaultListModel<String> listModel;
	JList<String> list;

	JButton del;

	JButton test_;

	JTextField nameInput;
	JButton sure;
	JButton apply;
	
	// web view
	PicShower qrCode;

	MatchWindow match;

	public InputWindow(SettingWindow settingWindow) {

		this.setTitle(name + "参赛人员登记");
		this.setSize(windowWidth, windowHeight);
		this.setLocationRelativeTo(settingWindow);

		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				if (!applyed) {
					settingWindow.setVisible(true);
					settingWindow.applyed = false;
				}
			}

		});

		Font font = new Font("楷体", Font.BOLD, 15);

		mainPane = new JPanel();
		mainPane.setLayout(null);

		progress = new JLabel("*/*");
		progress.setSize(100, 20);
		progress.setLocation(45, 5);
		progress.setFont(font);

		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setSize(300, 2000);
		list.setFont(font);

		listPane = new JScrollPane(list);
		listPane.setSize(400, 180);
		listPane.setLocation(45, 25);
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		del = new JButton("删除");
		del.setSize(40, 18);
		del.setLocation(400, 5);
		del.setFont(font);
		del.setMargin(new Insets(0, 0, 0, 0));

		test_ = new JButton("生成测试数据");
		test_.setSize(120, 18);
		test_.setLocation(280, 5);
		test_.setFont(font);
		test_.setMargin(new Insets(0, 0, 0, 0));

		nameInput = new JTextField();
		nameInput.setSize(180, 30);
		nameInput.setLocation(45, 215);

		sure = new JButton("提交");
		sure.setSize(100, 30);
		sure.setLocation(235, 215);
		sure.setFont(font);

		apply = new JButton("完成");
		apply.setSize(100, 30);
		apply.setLocation(345, 215);
		apply.setFont(font);
		
		// web view
		qrCode = new PicShower();

		addListener();

		mainPane.add(progress);
		mainPane.add(listPane);
		mainPane.add(test_);
		mainPane.add(del);
		mainPane.add(nameInput);
		mainPane.add(sure);
		mainPane.add(apply);
		mainPane.add(qrCode);

		this.setContentPane(mainPane);

		match = new MatchWindow();
	}

	private void addListener() {
		// TODO Auto-generated method stub
		nameInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					String name = nameInput.getText();
					if (name.equals("")) {
						JOptionPane.showMessageDialog(null, "输入为空", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (incount < count) {
						listModel.addElement(name);
						incount++;
						progress.setText(incount + "/" + count);
						nameInput.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "参赛人员已满，" + name + " 未能加入到参赛人员列表中！", "警告",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}

		});
		sure.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String name = nameInput.getText();
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "输入为空", "警告", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (incount < count) {
					listModel.addElement(name);
					incount++;
					progress.setText(incount + "/" + count);
					nameInput.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "参赛人员已满，" + name + " 未能加入到参赛人员列表中！", "警告",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		});

		apply.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				ArrayList<String> all = new ArrayList<String>();
				for (int i = 0; i < listModel.getSize(); i++) {
					all.add(listModel.getElementAt(i));
				}

				applyed = true;
				match.setArray(all);
				main.setVisible(false);
				match.setVisible(true);

				match.setName(name);
				match.setFuhuo(fuhuo);

			}

		});

		del.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selects[] = list.getSelectedIndices();
				for (int i = 0; i < selects.length; i++) {
					listModel.removeElementAt(selects[selects.length - 1 - i]);
					incount--;
					progress.setText(incount + "/" + count);
				}

			}

		});

		test_.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (int i = incount; i < count; i++) {
					listModel.addElement(Math.random() * 500 + "");
					incount++;
					progress.setText(incount + "/" + count);
					nameInput.setText("");
				}
			}

		});

	}

	public void setCount(int count) {
		this.count = count;
		progress.setText(incount + "/" + this.count);
	}

	public void setName(String name) {
		this.name = name;
		this.setTitle(name + " 参赛人员登记");
	}

	public void setFuhuo(boolean fuhuo) {
		this.fuhuo = fuhuo;
	}

	public boolean isWeb() {
		return web;
	}

	public void setWeb(boolean web) {
		if (!this.web && web) {
			listPane.setSize(listPane.getSize().width / 2, listPane.getSize().height);
			listPane.setLocation(listPane.getLocation().x + listPane.getSize().width, listPane.getLocation().y);
			
			String url = "localhost:2020/enroll?mid=" + mid;
			
			int size = 450;
			Hashtable<EncodeHintType, String> hints = new Hashtable<>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 字符转码格式设置
			hints.put(EncodeHintType.ERROR_CORRECTION, "H"); // 容错级别设置 默认为L
			hints.put(EncodeHintType.MARGIN, "4"); // 空白边距设置
			BitMatrix bitMatrix;
			try {
				bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, size, size, hints);
				BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
				for (int y = 0; y < size; y++) {
					for (int x = 0; x < size; x++) {
						if (bitMatrix.get(x, y)) { // 黑色色块像素设置
							img.setRGB(x, y, Color.BLACK.getRGB());
						} else { // 白色色块像素设置
							img.setRGB(x, y, Color.WHITE.getRGB());
						}
					}
				}
				
				String[] strs = new String[3];
				strs[0] = mid.substring(0, 5);
				strs[1] = mid.substring(5, 9);
				strs[2] = mid.substring(9, 13);
				
				// 下方文字
				Font font = new Font("楷体", Font.BOLD, 45);
				Graphics2D painter = (Graphics2D) img.getGraphics();
				painter.setColor(Color.BLACK);
				painter.setFont(font);
				painter.drawString(strs[0] + " " + strs[1] + " " + strs[2], 40, size - 7);

				qrCode.setImg(img);

				qrCode.setSize(size, size);
				qrCode.setLocation(listPane.getLocation().x - listPane.getWidth(), listPane.getLocation().y);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (this.web && !web) {
			listPane.setSize(listPane.getSize().width * 2, listPane.getSize().height);
			listPane.setLocation(listPane.getLocation().x - listPane.getSize().width / 2, listPane.getLocation().y);
		}
		this.web = web;
	}
	

	public String getMid() {
		return mid;
	}
	

	public void setMid(String mid) {
		this.mid = mid;
	}
	
}
