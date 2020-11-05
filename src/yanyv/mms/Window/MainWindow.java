package yanyv.mms.Window;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.json.JSONObject;

import yanyv.mms.manager.SaveManager;
import yanyv.mms.vo.Account;
import yanyv.mms.web.LoginWeb;
import yanyv.mms.web.QueryWeb;

public class MainWindow extends JFrame {
	
	public static String name = "竞赛匹配系统MMS ";
	public static String ver = "Ver 0.0.1";
	public static String mode = " (离线模式)";
	private static int windowWidth = 1080;
	private static int windowHeight = 720;
	public static Account acc;
	public static boolean logined = false;
	
	MainWindow main = this;
	JPanel mainPane;
	
	JButton newMatch;
	JButton conMatch;
	JButton exit;

	JLabel title1;
	JLabel title2;
	JLabel title3;
	
	JButton login;
	
	LoginWindow loginWin;
	
	public MainWindow() {
		this.setTitle(name + ver + mode);
		this.setSize(windowWidth, windowHeight);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		mainPane = new JPanel();
		mainPane.setLayout(null);

		newMatch = new JButton("新建");
		conMatch = new JButton("继续");
		exit = new JButton("退出");
		
		Font font = new Font("楷体",Font.BOLD,72);

		newMatch.setFont(font);
		conMatch.setFont(font);
		exit.setFont(font);

		newMatch.setSize(300, 100);
		conMatch.setSize(300, 100);
		exit.setSize(300, 100);

		newMatch.setLocation(100, 100);
		conMatch.setLocation(100, 280);
		exit.setLocation(100, 460);

		title1 = new JLabel("竞赛");
		title2 = new JLabel("匹配系统");
		title3 = new JLabel("Match Matching System");

		title1.setForeground(Color.red);
		
		title1.setSize(1000, 300);
		title2.setSize(500, 100);
		title3.setSize(500, 100);
		
		title1.setLocation(450, 100);
		title2.setLocation(600, 350);
		title3.setLocation(610, 400);

		Font tfont1 = new Font("楷体",Font.BOLD,256);
		Font tfont2 = new Font("楷体",Font.BOLD,80);
		Font tfont3 = new Font("楷体",Font.BOLD,27);

		title1.setFont(tfont1);
		title2.setFont(tfont2);
		title3.setFont(tfont3);
		
		login = new JButton("登录");
		login.setLocation(900, 600);
		login.setSize(100, 50);
		login.setFont(tfont3);
		
		mainPane.add(newMatch);
		mainPane.add(conMatch);
		mainPane.add(exit);
		mainPane.add(title1);
		mainPane.add(title2);
		mainPane.add(title3);
		mainPane.add(login);
		
		addListener();
		init();
		
		this.setContentPane(mainPane);

	}

	private void init() {
		
		loginWin = new LoginWindow(main);
		
	}

	private void addListener() {
		
		newMatch.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("new");
				SettingWindow set = new SettingWindow(main);
				set.setVisible(true);
				main.setVisible(false);
			}
			
		});
		
		conMatch.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("con");
				ConWindow con = new ConWindow(main);
				main.setVisible(false);
				con.setVisible(true);
			}
			
		});
		
		exit.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			
		});
		
		login.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("login");
				
				if(!logined) loginWin.setVisible(true);
				else logout();
				
			}
			
		});
		
	}

	public void loginSuccess(int uid) {
		logined = true;
		// 捏一个acc
		try {
			acc = QueryWeb.queryUserByUid(uid);
			mode = " (云模式) -";
			this.setTitle(name + ver + mode + acc.getName() + "(uid:" + uid + ")");
			login.setText("注销");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logout() {
		logined = false;
		acc = null;
		mode = " (离线模式)";
		this.setTitle(name + ver + mode);
		login.setText("登录");
	}
	
}
