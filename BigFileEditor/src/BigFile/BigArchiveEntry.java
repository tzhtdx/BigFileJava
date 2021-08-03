package BigFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BigArchiveEntry
{
	private int Offset; //偏移量
	private String FullName; //全地址
	private String Name; //文件名
	private int Length; //文件长度
	private BigArchive Archive;
	private RandomAccessFile EntryFile; //维护一个私有的Entry文件
	public int OutstandingOffset; //临时偏移量
	
	
	public BigArchiveEntry(BigArchive archive,String name,int offset,int size)
	{
		Archive = archive;
		FullName = name;
		Offset = offset;
		Length = size;
		
		if(FullName.contains("\\"))
		{
			Name = FullName.split("\\\\")[FullName.split("\\\\").length -1];
		}
		else
		{
			Name = FullName;
		}	
	}
	
	public BigArchiveEntry(BigArchive archive,String name)
	{
		Archive = archive;
		FullName = name;
		try
		{
			EntryFile = new RandomAccessFile(FullName,"r");
			Length = (int)EntryFile.length();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Name = FullName.split("\\\\")[FullName.split("\\\\").length -1];

		
		String BigName = Archive.FilePath.split("\\\\")[Archive.FilePath.split("\\\\").length-1];
		String BigPath = Archive.FilePath.substring(0, Archive.FilePath.length() - BigName.length());	
		String EntryPath = FullName.substring(0,FullName.length() - Name.length());
		
		if(BigPath.equals(EntryPath))
		{
			FullName = Name;
		}
		else
		{
			FullName = EntryPath.split(":\\\\")[1] + Name;
		}
		
		System.out.println(FullName);
	}
	
	
	//写：从big读取完文件写入本地
	public void Write()
	{
		//out = new FileOutputStream(FullName);
		byte[] res = new byte[Length];
		try 
		{
			res = Archive.Read(Offset,Length); //获取文件内容
			Archive.index = Offset;
			
			String bigname = Archive.FilePath.split("\\\\")[Archive.FilePath.split("\\\\").length-1];
			String path = Archive.FilePath.substring(0, Archive.FilePath.length() - bigname.length());
			
			
			//存在文件结构
			if(!Name.equals(FullName))
			{
				path = path + FullName.substring(0, FullName.length() - Name.length());
				File file=new File(path);
				if(!file.exists())
				{
					file.mkdirs();
				}
			}	
			EntryFile = new RandomAccessFile(path + Name,"rw"); //创建文件
			EntryFile.write(res);
			EntryFile.close();
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public byte[] Read()
	{
		byte[] res = new byte[Length];
		if(Archive.Mode == BigArchiveMode.Create)
		{
			try
			{
				//EntryFile = new RandomAccessFile(FullName,"r");
				//Length = (int)EntryFile.length();
				EntryFile.readFully(res);
				EntryFile.close();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //读取		
		}
		if(Archive.Mode == BigArchiveMode.Update)
		{
			try
			{
				res = Archive.Read(Offset,Length);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //获取文件内容
		}
		
		return res;
	}
	
	public int GetLength()
	{	
		return Length;
	}
	
	public String GetFullName()
	{
		return FullName;
	}
	
	public void SetOffset(int offset)
	{
		Offset = offset;
	}
	
	public void Delete()
	{
		Archive.DeleteEntry(this);
	}
	
	
}
