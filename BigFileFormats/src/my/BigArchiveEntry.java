package my;

import java.io.File;
import java.io.RandomAccessFile;

public class BigArchiveEntry
{
	private int Offset; //ƫ����
	private String FullName; //ȫ��ַ
	private String Name; //�ļ���
	private int Length; //�ļ�����
	private BigArchive Archive;
	private RandomAccessFile EntryFile; //ά��һ��˽�е�Entry�ļ�
	
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
	
	
}
