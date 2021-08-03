package my;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatLightLaf;

public class SwingDemo
{

	private static void createGUI()
	{
		// JFrame指一个窗口，构造方法的参数为窗口标题
		// 语法：因为MyFrame是JFrame的子类，所以可以这么写
		JFrame frame = new DemoFrame("FIG文件编辑器");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口的其他参数，如窗口大小
		frame.setSize(1000, 800);
		// 显示窗口
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		FlatLightLaf.setup();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				createGUI();
			}
		});

	}

}
