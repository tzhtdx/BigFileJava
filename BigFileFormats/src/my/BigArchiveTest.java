package my;

import java.util.Scanner;

public class BigArchiveTest
{

	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		//String filePath = "E:\\Game\\绝命时刻繁体中文硬盘版\\Command and Conquer Generals Zero Hour\\W3DChineseZH.big";
		//String filePath = "E:\\Code\\C#\\OpenSAGE-master\\src\\OpenSage.Game.Tests\\Data\\Big\\Assets\\test.big";
		//String filePath = "E:\\Code\\Test\\test.big";
		String filePath = "E:\\Code\\Test\\data1\\W3DChineseZH.big";
//		Scanner sc = new Scanner(System.in); 
//		System.out.println("请输入big文件的绝对路径");
//		System.out.println("PS：测试程序，建议把big单独放在一个文件夹下");
//		System.out.println("示例：E:\\\\Game\\\\绝命时刻繁体中文硬盘版\\\\Command and Conquer Generals Zero Hour\\\\W3DChineseZH.big\n>: ");
//		String filePath = sc.nextLine();
		BigArchive archive = new BigArchive(filePath, BigArchiveMode.Read);
		archive.Read();
	}

}
