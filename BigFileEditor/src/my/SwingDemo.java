package my;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatLightLaf;

public class SwingDemo
{

	private static void createGUI()
	{
		// JFrameָһ�����ڣ����췽���Ĳ���Ϊ���ڱ���
		// �﷨����ΪMyFrame��JFrame�����࣬���Կ�����ôд
		JFrame frame = new DemoFrame("FIG�ļ��༭��");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ڵ������������細�ڴ�С
		frame.setSize(1000, 800);
		// ��ʾ����
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
