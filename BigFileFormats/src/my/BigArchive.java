package my;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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
		
		int ArchiveSize = reader.ReadBigEndianUInt32(); // Archive Size
		int NumEntries = reader.ReadUInt32();
		int FirstOffset = reader.ReadUInt32(); // First File Offset
		
		BigFileHeader = 16;
		System.out.println("ArchiveSize " + ArchiveSize);
		System.out.println("NumEntries " + NumEntries);
		System.out.println("FirstOffset " + FirstOffset);
		 for (int i = 0; i < NumEntries; i++)
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
		 //WriteALLEntrys();
	}
	
	
	public byte[] Read(int offset,int size) throws Exception
	{
		BinaryReader reader = new BinaryReader(BigFile, "r");
		System.out.println(reader.getFileLength());
		byte[] res = new byte[size];
		res = reader.ReadByteLength(offset, size);
		reader.close();
		return res;
	}
	
	public void Write()
	{
		BinaryWriter writer = new BinaryWriter(BigFile, "rw");
		int tableSize = CalculateTableSize();
		int contentSize = CalculateContentSize();
		int headerSize = 16;
		int archiveSize = headerSize + tableSize + contentSize;
		int dataStart = headerSize + tableSize;
		try
		{
			WriteHeader(writer, archiveSize, dataStart);
			WriteFileTable(writer, dataStart);
			WriteFileContent(writer);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UpdateOffsets();
		writer.close();	
	}
	
	private void WriteTmp(File TmpBigFile)
	{
		BinaryWriter writer = new BinaryWriter(TmpBigFile, "rw");
		int tableSize = CalculateTableSize();
		int contentSize = CalculateContentSize();
		int headerSize = 16;
		int archiveSize = headerSize + tableSize + contentSize;
		int dataStart = headerSize + tableSize;
		try
		{
			WriteHeader(writer, archiveSize, dataStart);
			WriteFileTable(writer, dataStart);
			WriteFileContent(writer);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UpdateOffsets();
		writer.close();	
	}
	
	public void UpdateBigFile()
	{
		String TmpFilePath = FilePath.split(".big")[0] + "_tmp.big";
		File TmpBigFile = new File(TmpFilePath);
		WriteTmp(TmpBigFile);
		BigFile.delete(); //ɾ��Դ�ļ�
		TmpBigFile.renameTo(BigFile);
		BigFile = TmpBigFile;
	}
	
	private void WriteHeader(BinaryWriter writer,int archiveSize,int dataStart) throws Exception
	{
		String fourCc = "BIGF";
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
		
		writer.WriteFourCc(fourCc);
		writer.WriteBigEndianUInt32(archiveSize);
		writer.WriteUInt32(Entries.size());	
		writer.WriteUInt32(dataStart);
	}
	
	
	private void WriteFileTable(BinaryWriter writer, int dataStart)
	{
		int entryOffset = dataStart;
		for (BigArchiveEntry Entry : Entries)
		{
			writer.WriteUInt32(entryOffset);
			writer.WriteUInt32(Entry.GetLength());
			writer.WriteString(Entry.GetFullName());
			Entry.OutstandingOffset = entryOffset;
			entryOffset += Entry.GetLength();
		}
	}
	
	private void WriteFileContent(BinaryWriter writer)
	{
		byte[] res = null;  
		for (BigArchiveEntry Entry : Entries)
		{
			res = Entry.Read();
			writer.WriteByte(res);
		}
	}
	
	public void DeleteEntry(BigArchiveEntry Entry)
	{
		Entries.remove(Entry);
		UpdateBigFile();//
	}
	
	
	
	
	private int CalculateTableSize()
	{
		int tableSize = 0;
		for (BigArchiveEntry Entry : Entries)
		{
			tableSize += 8;
			tableSize += Entry.GetFullName().length() + 1;
		}
        return tableSize;
	}
	
	
	private void UpdateOffsets()
    {
        for(BigArchiveEntry Entry : Entries)
        {
        	Entry.SetOffset(Entry.OutstandingOffset);
        	Entry.OutstandingOffset = 0;
        }
    }
	
	private int CalculateContentSize()
	{
		int contentSize = 0;
        for (BigArchiveEntry Entry : Entries)
		{
        	contentSize += Entry.GetLength();
		}
        return contentSize;
	}
	
	public void WriteALLEntrys()
	{
		for (BigArchiveEntry bigArchiveEntry : Entries)
		{
			bigArchiveEntry.Write();
		}
	}
	
	public void SetEntry(String FullName)
	{
		BigArchiveEntry Entry = new BigArchiveEntry(this, FullName);
		Entries.add(Entry);
	}
	
	
	public BigArchiveEntry GetEntry(String FullName)
	{
		for (BigArchiveEntry bigArchiveEntry : Entries)
		{
			if(FullName.equals(bigArchiveEntry.GetFullName()))
			{
				return bigArchiveEntry;
			}
		}
		return null;
	}
	
	
	
	
	
	public enum BigArchiveVersion
	{
	    BigF,
	    Big4
	}
	
}
