package my;

import java.util.Scanner;

public class BigArchiveTest
{

	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		//String filePath = "E:\\Game\\����ʱ�̷�������Ӳ�̰�\\Command and Conquer Generals Zero Hour\\W3DChineseZH.big";
		//String filePath = "E:\\Code\\C#\\OpenSAGE-master\\src\\OpenSage.Game.Tests\\Data\\Big\\Assets\\test.big";
		//String filePath = "E:\\Code\\Test\\test.big";
		String filePath = "E:\\Code\\Test\\data1\\W3DChineseZH.big";
//		Scanner sc = new Scanner(System.in); 
//		System.out.println("������big�ļ��ľ���·��");
//		System.out.println("PS�����Գ��򣬽����big��������һ���ļ�����");
//		System.out.println("ʾ����E:\\\\Game\\\\����ʱ�̷�������Ӳ�̰�\\\\Command and Conquer Generals Zero Hour\\\\W3DChineseZH.big\n>: ");
//		String filePath = sc.nextLine();
		BigArchive archive = new BigArchive(filePath, BigArchiveMode.Read);
		archive.Read();
	}

}
