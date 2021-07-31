package my;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BinaryWriter
{
	private RandomAccessFile file;
	public BinaryWriter(File f,String mode)
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
	
	//从文件中写入一个字节
	public void WriteByte(byte[] buff)
	{
		try 
		{
			file.write(buff);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//写入大端无符号整型
	public void WriteBigEndianUInt32(int res)
	{
		byte[] buff = HexUtil.Reverse(HexUtil.int4byte(res));
		try 
		{
			file.write(buff);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//写入小端无符号整型
	public void WriteUInt32(int res)
	{
		byte[] buff = HexUtil.int4byte(res);
		try 
		{
			file.write(buff);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//写入文件头类型
	public void WriteFourCc(String res)
	{
		byte[] bs = res.getBytes();
		try {
			file.write(bs,0,4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void WriteString(String res)
	{
		try {
			file.writeBytes(res + "\0"); //对文件名进行补零操作
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
