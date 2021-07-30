package my;

public class HexUtil
{
	/**
	 * 4byte 转 int
	 * */
	public static int byte4int(byte[] res) 
	{
		int firstByte 				= 0;
		int secondByte				= 0;
		int thirdByte				= 0;
		int fourthByte				= 0;
		int value					= 0;
		
		firstByte					= (0x000000FF & ((int) res[0]));
		secondByte					= (0x000000FF & ((int) res[1]));
		thirdByte					= (0x000000FF & ((int) res[2]));
		fourthByte					= (0x000000FF & ((int) res[3]));
		 
		value  = (firstByte<<24|secondByte<<16|thirdByte<<8|fourthByte) & 0x00000000FFFFFFFF;
		return value;
	}
	

	public static char byte4Char(byte[] res) 
	{
		int firstByte 				= 0;
		int secondByte				= 0;
		char value					= 0;
		
		firstByte 					= (0xFF & res[0]);
		secondByte					= (0xFF & res[1]);
		
		value = (char) (firstByte << 8 | secondByte);
		return value;
	}
 	
	
	//反转数组
	public static byte[] Reverse(byte[] res) 
	{
	    for (int i = 0; i < res.length / 2; i++) 
	    {
	    	byte temp = res[i];
	    	res[i] = res[res.length - 1 - i];
	    	res[res.length - 1 - i] = temp;
	    }
	    return res;
	}

}
