package my;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;


public class FileNodeList
{
	List<FileNode> filenodes;
	
	public FileNodeList()
	{
		filenodes = new ArrayList<>();
	}
	
	public FileNode getNodeByName(
			String fileName,DefaultMutableTreeNode pnode,int l)
	{
		for (FileNode fnode : filenodes)
		{
			if(fnode.parentNode == null)
			{
				continue;
			}
			if(fnode.thisFileName.equals(fileName)		&&
			   fnode.parentNode.getUserObject().toString().equals(pnode.getUserObject().toString())			&&
			   fnode.thisLevel == l)
			{
				return fnode;
			}
		}
		return null;
	}
	
	public void add(FileNode fnode)
	{
		filenodes.add(fnode);
	}
}
