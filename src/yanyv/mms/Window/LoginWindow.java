package yanyv.mms.Window;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.json.JSONObject;

import yanyv.mms.manager.ConfigManager;
import yanyv.mms.manager.SaveManager;
import yanyv.mms.web.LoginWeb;

public class LoginWindow extends JFrame {

	private static int windowWidth = 400;
	private static int windowHeight = 320;

	MainWindow mainWin;

	JFrame main = this;
	JPanel mainPane;
	
	JLabel mmsLogo;

	JLabel userLabel;
	JLabel passLabel;
	
	JTextField user;
	JPasswordField pass;

	JCheckBox remember;
	JCheckBox autoLogin;
	JLabel findBack;

	JButton submit;
	JButton register;

	public LoginWindow(MainWindow mainWin) {
		this.mainWin = mainWin;
		
		this.setTitle("登录");
		this.setSize(windowWidth, windowHeight);
		this.setLocationRelativeTo(mainWin);

		mainPane = new JPanel();
		mainPane.setLayout(null);
		
		mmsLogo = new JLabel("<html><body style=\"font-size:70px\"><a>MM</a><a style=\"color:red\">S</a></body></html>", JLabel.CENTER);
		mmsLogo.setLocation(50, 0);
		mmsLogo.setSize(290, 120);
		
		userLabel = new JLabel("账号");
		userLabel.setLocation(10, 130);
		userLabel.setSize(40, 30);

		passLabel = new JLabel("密码");
		passLabel.setLocation(10, 170);
		passLabel.setSize(40, 30);

		user = new JTextField();
		user.setLocation(50, 130);
		user.setSize(290, 30);

		pass = new JPasswordField();
		pass.setLocation(50, 170);
		pass.setSize(290, 30);
		
		remember = new JCheckBox("记住密码");
		remember.setLocation(50, 200);
		remember.setSize(80, 30);
		
		autoLogin = new JCheckBox("自动登录");
		autoLogin.setLocation(130, 200);
		autoLogin.setSize(80, 30);
		
		findBack = new JLabel("找回密码");
		findBack.setLocation(288, 200);
		findBack.setSize(80, 30);

		submit = new JButton("登录");
		submit.setLocation(50, 230);
		submit.setSize(140, 30);
		
		register = new JButton("注册");
		register.setLocation(200, 230);
		register.setSize(140, 30);

		mainPane.add(mmsLogo);
		mainPane.add(userLabel);
		mainPane.add(passLabel);
		mainPane.add(user);
		mainPane.add(pass);
		mainPane.add(remember);
		mainPane.add(autoLogin);
		mainPane.add(findBack);
		mainPane.add(submit);
		mainPane.add(register);

		addListener();

		this.setContentPane(mainPane);
		
		init();
	}

	private void init() {
		ConfigManager config = SaveManager.getConfig();
		if(config.getSavePass()) {
			remember.setSelected(true);
			user.setText(config.getUser());
			pass.setText(config.getPass());
		}
		
		if(config.getAutoLogin()) {
			autoLogin.setSelected(true);
			login(user.getText(), new String(pass.getPassword()));
		}
		
	}

	private void addListener() {
		// TODO Auto-generated method stub
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				login(user.getText(), new String(pass.getPassword()));
			}
		});
		
		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if (java.awt.Desktop.isDesktopSupported()) {
					try {
						// 创建一个URI实例
						URI uri = URI.create("http://localhost:2020/register");
						// 获取当前系统桌面扩展
						Desktop dp = Desktop.getDesktop();
						// 判断系统桌面是否支持要执行的功能
						if (dp.isSupported(Desktop.Action.BROWSE)) {
							// 获取系统默认浏览器打开链接
							dp.browse(uri);
						}
		 
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
	}
	
	public void login(String user, String pass) {
		int uid = -1;
		try {
			JSONObject result = LoginWeb.login(user, pass);
			
			switch(result.getInt("code")) {
			case yanyv.mms.code.Login.SUCCESS:
				System.out.println("登录成功");
				uid = result.getInt("uid");
				break;
			case yanyv.mms.code.Login.PASS_ERR:
			case yanyv.mms.code.Login.UID_NOTFOUND:
			case yanyv.mms.code.Login.UID_ERR:
			case yanyv.mms.code.Login.DATEBASE_ERR:
				System.out.println(result.getString("data"));
				JOptionPane.showMessageDialog(null, result.getString("data"), "错误",JOptionPane.WARNING_MESSAGE);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "网络异常", "错误",JOptionPane.WARNING_MESSAGE);
		}
		
		ConfigManager config = new ConfigManager();
		
		if(uid != -1) {
			config.setSavePass(remember.isSelected());
			config.setAutoLogin(autoLogin.isSelected());
			
			if(remember.isSelected()) {
				config.setUser(user);
				config.setPass(pass);
			} else {
				config.setUser("");
				config.setPass("");
			}
			
			main.setVisible(false);
			mainWin.loginSuccess(uid);
		} else {
			main.setVisible(true);
			this.pass.setText("");
			remember.setSelected(false);
			autoLogin.setSelected(false);
			config.setPass("");
			config.setSavePass(false);
			config.setAutoLogin(false);
		}
		
		SaveManager.saveConfig(config);
		
	}
	
	@Override
	public void setVisible(boolean vis) {
		// TODO Auto-generated method stub
		super.setVisible(vis);
		if(vis) {
			this.setLocationRelativeTo(mainWin);
		}
	}
	
}
