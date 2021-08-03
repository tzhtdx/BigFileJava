package BigFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BinaryReader
{
	private RandomAccessFile file;
	
	public BinaryReader(File f,String mode)
	{
		try
		{
			file = new RandomAccessFile(f, mode);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//从文件中读取一个字节
	public char ReadByte()
	{
		byte[] buff = new byte[1];
		try 
		{
			file.read(buff,0,1);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (char)buff[0];	
	}
	
	//从文件中读取无符号整型（大端读取）
	public int ReadBigEndianUInt32()
	{
		byte[] bs = new byte[4];
		int temp = 0;
		try {
			file.read(bs,0,4);
			temp = HexUtil.byte4int(HexUtil.Reverse(bs));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	//从文件中读取无符号整型（小端读取）
	public int ReadUInt32()
	{
		byte[] bs = new byte[4];
		int temp = 0;
		try {
			file.read(bs,0,4);
			temp = HexUtil.byte4int(bs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	//读取四个字节转化为String
	public String ReadFourCc(){
		byte[] bs = new byte[4];
		String string = "";
		try {
			file.read(bs,0,4);
			string = new String(bs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
	
	//读取一段字节码直到为0转成字符串
	public String ReadNullTerminatedString()
	{
		String string = "";
		char tmp = ReadByte();
		do
		{
			string += tmp;
			tmp = ReadByte();
		} while (tmp!=0);
		
		return string;
	}
	
	//读取任意长度
	public byte[] ReadByteLength(int length)
	{
		byte[] bs = new byte[length];
		byte[] rs = new byte[length];
		try {
			file.read(bs,0,length);
			System.arraycopy(bs, 0, rs, 0, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	//读取指定起始位置和长度的数据
	public byte[] ReadByteLength(int offset,int length)
	{
		byte[] bs = new byte[length];
		byte[] rs = new byte[length];
		try {
			file.seek(offset);
			file.read(bs,0,length);
			System.arraycopy(bs, 0, rs, 0, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public long getFileLength()
	{
		long length = 0;
		try
		{
			length = file.length();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}
	
	public void close()
	{
		try
		{
			file.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
