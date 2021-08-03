package my;

import javax.swing.tree.DefaultMutableTreeNode;

public class FileNode
{
	public String thisFileName;  //此节点名字
	public int thisLevel; //此节点所在层数
	public DefaultMutableTreeNode thisNode; //此节点
	public DefaultMutableTreeNode parentNode; //上一层节点
	
	public FileNode() 
	{
	}
	
	public FileNode(String fileName) //如果是根节点层数为0
	{
		// TODO Auto-generated constructor stub
		this(fileName,null,0);
	}
	
	public FileNode(String fileName,DefaultMutableTreeNode pnode,int l)
	{
		// TODO Auto-generated constructor stub
		thisFileName = fileName;
		thisNode = new DefaultMutableTreeNode(fileName);
		parentNode = pnode;
		thisLevel = l;
	}	
	
	//可能出现
	/*		"code//test//d.txt"
	 * 		"Tets//test//d.txt"
	 * 
	 */
}
