package yanyv.mms.Window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

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
import yanyv.mms.vo.Account;
import yanyv.mms.vo.Match;
import yanyv.mms.web.IPConfig;
import yanyv.mms.web.MatchWeb;
import yanyv.mms.web.QueryWeb;
import yanyv.mms.web.SignupWeb;

public class InputWindow extends JFrame {

	private int count = 2;
	private int incount = 0;
	private String name = "";
	private boolean fuhuo = false;

	private Thread webRefresh;
	private boolean web = false;
	private Match match;
	private String url;

	private static int windowWidth = 500;
	private static int windowHeight = 300;

	boolean applyed = false;

	JFrame main = this;
	JPanel mainPane;

	JLabel progress;

	JScrollPane listPane;

	DefaultListModel<Account> listModel;
	JList<Account> list;

	JButton del;

	JButton test_;

	JTextField nameInput;
	JButton sure;
	JButton apply;

	// web view
	PicShower qrCode;

	MatchWindow matchWin;

	public InputWindow(JFrame lastWindow) {

		this.setTitle(name + "参赛人员登记");
		this.setSize(windowWidth, windowHeight);
		this.setLocationRelativeTo(lastWindow);

		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				if (!applyed) {
					if (lastWindow.getClass().getName().equals("yanyv.mms.Window.SettingWindow")) {
						((SettingWindow) lastWindow).applyed = false;
						if (web) {
							lastWindow.setVisible(true);
							lastWindow.setVisible(false);
						} else {
							lastWindow.setVisible(true);
						}

					} else if (lastWindow.getClass().getName().equals("yanyv.mms.Window.ConWindow")) {

						((ConWindow) lastWindow).applyed = false;
						lastWindow.setVisible(true);
					}
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

		listModel = new DefaultListModel<Account>();
		list = new JList<Account>(listModel);
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

		matchWin = new MatchWindow();
	}

	private void addListener() {
		// TODO Auto-generated method stub
		nameInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					addToList();
				}
			}

		});
		sure.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				addToList();
			}

		});

		apply.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				if (web && match.getState() == 4) {
					JOptionPane.showMessageDialog(null, "该比赛已取消", "警告", JOptionPane.WARNING_MESSAGE);
				} else if (web && match.getStartDate().after(now)) {
					JOptionPane.showMessageDialog(null, "未到开赛日期，预定的开赛日为：" + format.format(match.getStartDate()), "警告",
							JOptionPane.WARNING_MESSAGE);
				} else {

					boolean startSuccess = true;
					if (web) {

						if (match.getState() == 1) {
							try {
								startSuccess = MatchWeb.startMatch(match.getMid());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}

					ArrayList<Account> all = new ArrayList<Account>();
					for (int i = 0; i < listModel.getSize(); i++) {
						all.add(listModel.getElementAt(i));
					}

					applyed = true;
					matchWin.setWeb(web);
					matchWin.setArray(all);
					main.setVisible(false);
					matchWin.setVisible(true);

					matchWin.setName(name);
					matchWin.setFuhuo(fuhuo);

					if (web && startSuccess) {

						switch (match.getModeId()) {
						case 1:
							matchWin.setFuhuo(false);
							break;
						case 2:
							matchWin.setFuhuo(true);
							break;
						}
						matchWin.setMatch(match);
					}
				}

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
				if (web) {
					JOptionPane.showMessageDialog(null, "该测试功能于网络模式不可用", "警告", JOptionPane.WARNING_MESSAGE);
				} else
					for (int i = incount; i < count; i++) {
						listModel.addElement(new Account((int) (Math.random() * 500) + ""));
						incount++;
						progress.setText(incount + "/" + count);
						nameInput.setText("");
					}
			}

		});
		
		qrCode.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setSysClipboardText("报名地址: " + url + "\n 赛事编号: " + match.getMid());
				JOptionPane.showMessageDialog(null, "赛事链接已复制到剪贴板", "提示", JOptionPane.INFORMATION_MESSAGE);
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
		if (web) {
			webRefresh = new Thread() {

				@Override
				public void run() {
					try {
						while (true) {
							List<Account> accs = QueryWeb.queryAllByMid(match.getMid());
							listModel.clear();
							for (Account acc : accs) {
								listModel.addElement(acc);
							}
							Thread.sleep(1000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			};
			webRefresh.start();
		}
		if (!this.web && web) {
			listPane.setSize(listPane.getSize().width / 2, listPane.getSize().height);
			listPane.setLocation(listPane.getLocation().x + listPane.getSize().width, listPane.getLocation().y);

			url = IPConfig.IP + "/signup?mid=" + match.getMid();

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
				strs[0] = match.getMid().substring(0, 5);
				strs[1] = match.getMid().substring(5, 9);
				strs[2] = match.getMid().substring(9, 13);

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
		return match.getMid();
	}

	public void setMatch(Match match) {
		this.match = match;
		this.setTitle(match.getName() + "(" + match.getMid() + ")" + this.getTitle());
	}

	private void addToList() {
		String name = nameInput.getText();
		if (web) {
			addToListWeb(name);
		} else {
			addToListLocal(name);
		}
	}

	private void addToListWeb(String name) {
		String reg = "^[0-9]+(.[0-9]+)?$";

		if (name.matches(reg)) {
			try {
				Account acc = QueryWeb.queryUserByUid(Integer.parseInt(name));
				if (acc != null) {
					SignupWeb.Signup(acc.getUid(), match.getMid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void addToListLocal(String name) {
		if (name.equals("")) {
			JOptionPane.showMessageDialog(null, "输入为空", "警告", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (incount < count) {
			Account acc = new Account();
			acc.setName(name);
			listModel.addElement(acc);
			incount++;
			progress.setText(incount + "/" + count);
			nameInput.setText("");
		} else {
			JOptionPane.showMessageDialog(null, "参赛人员已满，" + name + " 未能加入到参赛人员列表中！", "警告", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void setSysClipboardText(String str) {  
		// 写入剪贴板
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
        Transferable tText = new StringSelection(str);  
        clip.setContents(tText, null);  
    }
}
