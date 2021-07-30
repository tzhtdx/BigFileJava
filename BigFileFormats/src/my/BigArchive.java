package my;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import my.BigArchive.BigArchiveVersion;


public class BigArchive
{
	public String FilePath; //big�ļ�·��
	public List<BigArchiveEntry> Entries; //������е�entry
	public File BigFile;
	
	private int BigFileHeader; //�ļ�ͷ�ֽ���
	private int BigFileTable; //big�ļ������е��ļ������ļ�ƫ�Ƶ�ַ���ļ���С���ļ���
	public int index; //��ǰ�ļ��������ֽ�λ��
	
	
	public BigArchiveMode Mode;
	public BigArchiveVersion Version;
	
	
	public BigArchive(String filePath, BigArchiveMode mode)
	{
		// TODO Auto-generated constructor stub
		FilePath = filePath;
		Mode = mode;
		Entries = new ArrayList<BigArchiveEntry>();
		BigFile = new File(filePath);	
	}
	
	public void Read() throws Exception
	{
		BinaryReader reader = new BinaryReader(BigFile, "r");
		if(reader.getFileLength() < 4)
		{
			char a = reader.ReadByte();
			char b = reader.ReadByte();
			
			 if (a == '?' && b == '?')
             {
                 return;
             }
             else
             {
            	 throw new Exception("Big archive is too small");
             }
		}
		
		String fourCc = reader.ReadFourCc();
		System.out.println("fourCc: " + fourCc);
		switch (fourCc) 
		{
			case "BIGF":
				Version = BigArchiveVersion.BigF;
				break;
			case "BIG4":
				Version = BigArchiveVersion.Big4;
				break;
			default:
				throw new Exception("Not a supported BIG format:" + fourCc);
		}
		
		int Size = reader.ReadBigEndianUInt32(); // Archive Size
		int numEntries = reader.ReadUInt32();
		int FirstOffset = reader.ReadUInt32(); // First File Offset
		
		BigFileHeader = 16;
		
		System.out.println("numEntries " + numEntries);
		
		 for (int i = 0; i < numEntries; i++)
         {
             int entryOffset = reader.ReadUInt32();
             int entrySize = reader.ReadUInt32();
             String entryName = reader.ReadNullTerminatedString();
             
             System.out.println("entryOffset " + entryOffset);
             System.out.println("entrySize " + entrySize);
             System.out.println("entryName " + entryName);
             
             BigFileTable = BigFileTable + entryName.getBytes().length + 9; //�ļ�������һ���ֽ�
             
             
             Entries.add(new BigArchiveEntry(this, entryName, entryOffset, entrySize));
         }	
		 
//		 System.out.println("�ļ�ͷ+�ļ��б�: "+ (BigFileTable + BigFileHeader));
//		 System.out.println("ƫ��ֵ " + FirstOffset);
		 
		 if(FirstOffset!=(BigFileTable + BigFileHeader))
		 {
			 reader.ReadByteLength(FirstOffset-(BigFileTable + BigFileHeader));
		 }
		 index = FirstOffset;
		 reader.close();
		 Write(); 
	}
	
	
	public byte[] Read(int offset,int size) throws Exception
	{
		BinaryReader reader = new BinaryReader(BigFile, "r");
		byte[] res = new byte[size];
		res = reader.ReadByteLength(offset, size);
		return res;
	}
	
	public void Write()
	{
		for (BigArchiveEntry bigArchiveEntry : Entries)
		{
			bigArchiveEntry.Write();
		}
	}
	
	
	
	
	
	public enum BigArchiveVersion
	{
	    BigF,
	    Big4
	}
	
}
