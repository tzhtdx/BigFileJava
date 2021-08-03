package BigFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BigArchiveEntry
{
	private int Offset; //ƫ����
	private String FullName; //ȫ��ַ
	private String Name; //�ļ���
	private int Length; //�ļ�����
	private BigArchive Archive;
	private RandomAccessFile EntryFile; //ά��һ��˽�е�Entry�ļ�
	public int OutstandingOffset; //��ʱƫ����
	
	
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
	
	
	//д����big��ȡ���ļ�д�뱾��
	public void Write()
	{
		//out = new FileOutputStream(FullName);
		byte[] res = new byte[Length];
		try 
		{
			res = Archive.Read(Offset,Length); //��ȡ�ļ�����
			Archive.index = Offset;
			
			String bigname = Archive.FilePath.split("\\\\")[Archive.FilePath.split("\\\\").length-1];
			String path = Archive.FilePath.substring(0, Archive.FilePath.length() - bigname.length());
			
			
			//�����ļ��ṹ
			if(!Name.equals(FullName))
			{
				path = path + FullName.substring(0, FullName.length() - Name.length());
				File file=new File(path);
				if(!file.exists())
				{
					file.mkdirs();
				}
			}	
			EntryFile = new RandomAccessFile(path + Name,"rw"); //�����ļ�
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
			} //��ȡ		
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
			} //��ȡ�ļ�����
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
