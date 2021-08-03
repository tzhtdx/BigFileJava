package my;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import BigFile.BigArchive;
import BigFile.BigArchiveEntry;
import BigFile.BigArchiveMode;

public class DemoFrame extends JFrame
{
	//����
	private JPanel root; 
	private StatusBar statusBar;
	private JToolBar toolBar;
	private JSplitPane splitPane;
	private JPanel leftOccpuyPanel;
	private JPanel rightOccpuyPanel;
	private JTree fileTree;
	private JScrollPane treeScrollPane;
	
	//����
	private FileNodeList fileNodes;
	private DefaultTreeModel fileModel;
	private BigArchive archive;
	
	
	public DemoFrame(String title)
	{
		super(title);
		// Content Pane
		root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());

		initComponents();

	}
	
	private void initComponents()
	{
		setJMenuBar(initMenuBar());
		initFileTree();
		statusBar = new StatusBar();
		
		initLeftPanel();
		initRightPanel();
		
		root.add(initToolBar(), BorderLayout.NORTH);
		
		
		root.add(initSplitPane(),BorderLayout.CENTER);
		
		
		root.add(statusBar, BorderLayout.SOUTH);
	}
	
	private void initFileTree() //��ʼ�����ؼ�
	{
		fileTree = new JTree();
		fileNodes = new FileNodeList();
		treeScrollPane = new JScrollPane(fileTree);
		fileTree.setVisible(false);
	}
	
	private JMenuBar initMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(initFileMenuBar());
		menuBar.add(initViewMenuBar());
		menuBar.add(initHelpMenuBar());
		return menuBar;
		
	}
	
	private JSplitPane initSplitPane()
	{
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.3);
		splitPane.setLeftComponent(leftOccpuyPanel);
		splitPane.setRightComponent(rightOccpuyPanel);
		return splitPane;
	}
	
	private void initLeftPanel()
	{
		leftOccpuyPanel = new JPanel();
		leftOccpuyPanel.setBackground(new Color(217, 163, 67));
		leftOccpuyPanel.setLayout(new BorderLayout());

		JLabel text = new JLabel();
		text.setText("�ļ��б�ռλ����");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setForeground(Color.white);
		
		leftOccpuyPanel.add(text, BorderLayout.CENTER);
	}
	
	private void initRightPanel()
	{
		rightOccpuyPanel = new JPanel();
		rightOccpuyPanel.setBackground(new Color(98, 181, 67));
		rightOccpuyPanel.setLayout(new BorderLayout());

		JLabel text = new JLabel();
		text.setText("�ļ�����ռλ���棨δʵװ��");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setForeground(Color.white);
//		text.setOpaque(true);
//		text.setBackground(Color.blue);
		
		rightOccpuyPanel.add(text, BorderLayout.CENTER);
	}
	
	private JToolBar initToolBar()
	{
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		JButton newButton = createToolButton("new", "�½�");
		JButton openButton = createToolButton("open", "��");
		JButton saveButton = createToolButton("save", "����");
		
		newButton.addActionListener(e -> newActionPerformed());
		openButton.addActionListener(e -> openActionPerformed());
		saveButton.addActionListener(e -> saveActionPerformed());
		
		toolBar.add(newButton);
		toolBar.add(openButton);
		toolBar.add(saveButton);
		
		return toolBar;
	}
	
	private JButton createToolButton(String icon, String tooltip)
	{
		String iconPath = "icons/ic_" + icon + ".svg";
		JButton button = new JButton();
		Icon i = new FlatSVGIcon(iconPath);
		button.setIcon(i);
		button.setFocusPainted(false);
		button.setToolTipText(tooltip);
		return button;
	}
	
	private JMenu initFileMenuBar()
	{
		JMenu fileMenu = new JMenu();
		JMenuItem newMenuItem = new JMenuItem();
		JMenuItem openMenuItem = new JMenuItem();
		JMenuItem saveAsMenuItem = new JMenuItem();
		JMenuItem closeMenuItem = new JMenuItem();
		JMenuItem exitMenuItem = new JMenuItem();
		
		fileMenu.setText("�ļ�");
		fileMenu.setMnemonic('F');
		
		//---- newMenuItem ----
		newMenuItem.setText("�½�");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newMenuItem.setMnemonic('N');
		newMenuItem.addActionListener(e -> newActionPerformed());
		fileMenu.add(newMenuItem);	
		
		//---- openMenuItem ----
		openMenuItem.setText("��");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		openMenuItem.setMnemonic('O');
		openMenuItem.addActionListener(e -> openActionPerformed());
		fileMenu.add(openMenuItem);
		
		//---- saveAsMenuItem ----
		saveAsMenuItem.setText("����");
		saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		saveAsMenuItem.setMnemonic('S');
		saveAsMenuItem.addActionListener(e -> saveActionPerformed());
		fileMenu.add(saveAsMenuItem);
		
		//�ָ���
		fileMenu.addSeparator();
		
		//---- closeMenuItem ----
		closeMenuItem.setText("�ر�");
		closeMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
		closeMenuItem.setMnemonic('W');
		closeMenuItem.addActionListener(e -> closeActionPerformed());
		fileMenu.add(closeMenuItem);
		
		//�ָ���
		fileMenu.addSeparator();
		
		//---- exitMenuItem ----
		exitMenuItem.setText("�˳�");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		exitMenuItem.setMnemonic('Q');
		exitMenuItem.addActionListener(e -> exitActionPerformed());
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}
	
	private JMenu initViewMenuBar()
	{
		JMenu viewMenu = new JMenu();
		JCheckBoxMenuItem showToolBarMenuItem = new JCheckBoxMenuItem();
		JCheckBoxMenuItem showStatusBarMenuItem = new JCheckBoxMenuItem();

		viewMenu.setText("��ͼ");
		viewMenu.setMnemonic('V');
		
		
		
		//---- showToolBarMenuItem ----
		showToolBarMenuItem.setText("��ʾ������");
		showToolBarMenuItem.setSelected(true);
		showToolBarMenuItem.setMnemonic('T');
		showToolBarMenuItem.addActionListener(e -> showToolBarActionPerformed());
		viewMenu.add(showToolBarMenuItem);
		
		
		//---- showStatusBarMenuItem ----
		showStatusBarMenuItem.setText("��ʾ״̬��");
		showStatusBarMenuItem.setSelected(true);
		showStatusBarMenuItem.setMnemonic('S');
		showStatusBarMenuItem.addActionListener(e -> showStatusBarActionPerformed());
		viewMenu.add(showStatusBarMenuItem);
		
		
		
		return viewMenu;
	}
	
	private JMenu initHelpMenuBar()
	{
		JMenu helpMenu = new JMenu();
		JMenuItem aboutMenuItem = new JMenuItem();
		
		helpMenu.setText("����");
		helpMenu.setMnemonic('H');
		
		//---- showStatusBarMenuItem ----
		aboutMenuItem.setText("����BIG�༭��");
		aboutMenuItem.setMnemonic('A');
		aboutMenuItem.addActionListener(e -> showAboutActionPerformed());
		helpMenu.add(aboutMenuItem);
		
		return helpMenu;
	}
	
	private void newActionPerformed()
	{
		System.out.println("�½�");
	}
	
	private void openActionPerformed()
	{
		JFileChooser chooser = new JFileChooser();
		File file = null;
		FileNameExtensionFilter filter = new FileNameExtensionFilter("big�ļ�", "big");
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			file = chooser.getSelectedFile();
			archive = new BigArchive(file, BigArchiveMode.Read);
			
			String rootname = archive.FilePath.split("\\\\")[archive.FilePath.split("\\\\").length - 1];
			FileNode root = new FileNode(rootname);
			fileNodes.add(root);

			for (BigArchiveEntry Entry : archive.Entries)
			{
			
				String[] paths = Entry.GetFullName().split("\\\\");				
				FileNode tmp = new FileNode();
				tmp = root;
				
				for (int i = 0;i<paths.length;i++)
				{
					FileNode fnode = new FileNode(paths[i], tmp.thisNode, i+1);
					if(fileNodes.getNodeByName(paths[i], tmp.thisNode, i+1) == null)
					{
						fileNodes.add(fnode);
						tmp.thisNode.add(fnode.thisNode);
						tmp = fnode;
					}
					else
					{
						tmp = fileNodes.getNodeByName(paths[i], tmp.thisNode, i+1);
					}
					
				}
			}
			
			fileModel = new DefaultTreeModel(root.thisNode);
			fileTree.setModel(fileModel);
			fileTree.setVisible(true);	
		}
		if(file != null)
		{
			splitPane.remove(0);
			splitPane.setLeftComponent(treeScrollPane);
		}
	}
	
	private void saveActionPerformed()
	{
		System.out.println("����");
	}
	
	private void closeActionPerformed()
	{
		System.out.println("�ر�");
	}
	
	private void exitActionPerformed()
	{
		System.out.println("�˳�");
	}
	
	
	private void showToolBarActionPerformed()
	{
		if(toolBar.isVisible())
		{
			toolBar.setVisible(false);
		}
		else
		{
			toolBar.setVisible(true);
		}
	}
	
	private void showStatusBarActionPerformed()
	{
		if(statusBar.isVisible())
		{
			statusBar.setVisible(false);
		}
		else
		{
			statusBar.setVisible(true);
		}
	}
	
	private void showAboutActionPerformed()
	{		
		String test = "<html>����һ�� <b>FIG�ļ��༭��</b><br>���ߣ�<i>���������</i>";
		JOptionPane.showMessageDialog(null, test, "����",JOptionPane.INFORMATION_MESSAGE);
	}
	
}
