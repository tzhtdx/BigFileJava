package my;

import javax.swing.tree.DefaultMutableTreeNode;

public class FileNode
{
	public String thisFileName;  //�˽ڵ�����
	public int thisLevel; //�˽ڵ����ڲ���
	public DefaultMutableTreeNode thisNode; //�˽ڵ�
	public DefaultMutableTreeNode parentNode; //��һ��ڵ�
	
	public FileNode() 
	{
	}
	
	public FileNode(String fileName) //����Ǹ��ڵ����Ϊ0
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
	
	//���ܳ���
	/*		"code//test//d.txt"
	 * 		"Tets//test//d.txt"
	 * 
	 */
}
