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
	//界面
	private JPanel root; 
	private StatusBar statusBar;
	private JToolBar toolBar;
	private JSplitPane splitPane;
	private JPanel leftOccpuyPanel;
	private JPanel rightOccpuyPanel;
	private JTree fileTree;
	private JScrollPane treeScrollPane;
	
	//数据
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
	
	private void initFileTree() //初始化树控件
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
		text.setText("文件列表占位界面");
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
		text.setText("文件内容占位界面（未实装）");
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
		JButton newButton = createToolButton("new", "新建");
		JButton openButton = createToolButton("open", "打开");
		JButton saveButton = createToolButton("save", "保存");
		
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
		
		fileMenu.setText("文件");
		fileMenu.setMnemonic('F');
		
		//---- newMenuItem ----
		newMenuItem.setText("新建");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		newMenuItem.setMnemonic('N');
		newMenuItem.addActionListener(e -> newActionPerformed());
		fileMenu.add(newMenuItem);	
		
		//---- openMenuItem ----
		openMenuItem.setText("打开");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		openMenuItem.setMnemonic('O');
		openMenuItem.addActionListener(e -> openActionPerformed());
		fileMenu.add(openMenuItem);
		
		//---- saveAsMenuItem ----
		saveAsMenuItem.setText("保存");
		saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		saveAsMenuItem.setMnemonic('S');
		saveAsMenuItem.addActionListener(e -> saveActionPerformed());
		fileMenu.add(saveAsMenuItem);
		
		//分割线
		fileMenu.addSeparator();
		
		//---- closeMenuItem ----
		closeMenuItem.setText("关闭");
		closeMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
		closeMenuItem.setMnemonic('W');
		closeMenuItem.addActionListener(e -> closeActionPerformed());
		fileMenu.add(closeMenuItem);
		
		//分割线
		fileMenu.addSeparator();
		
		//---- exitMenuItem ----
		exitMenuItem.setText("退出");
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

		viewMenu.setText("视图");
		viewMenu.setMnemonic('V');
		
		
		
		//---- showToolBarMenuItem ----
		showToolBarMenuItem.setText("显示工具栏");
		showToolBarMenuItem.setSelected(true);
		showToolBarMenuItem.setMnemonic('T');
		showToolBarMenuItem.addActionListener(e -> showToolBarActionPerformed());
		viewMenu.add(showToolBarMenuItem);
		
		
		//---- showStatusBarMenuItem ----
		showStatusBarMenuItem.setText("显示状态栏");
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
		
		helpMenu.setText("帮助");
		helpMenu.setMnemonic('H');
		
		//---- showStatusBarMenuItem ----
		aboutMenuItem.setText("关于BIG编辑器");
		aboutMenuItem.setMnemonic('A');
		aboutMenuItem.addActionListener(e -> showAboutActionPerformed());
		helpMenu.add(aboutMenuItem);
		
		return helpMenu;
	}
	
	private void newActionPerformed()
	{
		System.out.println("新建");
	}
	
	private void openActionPerformed()
	{
		JFileChooser chooser = new JFileChooser();
		File file = null;
		FileNameExtensionFilter filter = new FileNameExtensionFilter("big文件", "big");
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
		System.out.println("保存");
	}
	
	private void closeActionPerformed()
	{
		System.out.println("关闭");
	}
	
	private void exitActionPerformed()
	{
		System.out.println("退出");
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
		String test = "<html>这是一款 <b>FIG文件编辑器</b><br>作者：<i>神奇的人鱼</i>";
		JOptionPane.showMessageDialog(null, test, "关于",JOptionPane.INFORMATION_MESSAGE);
	}
	
}
