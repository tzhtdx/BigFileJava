package my;

public class BigArchiveTest
{

	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		//String filePath = "E:\\Game\\绝命时刻繁体中文硬盘版\\Command and Conquer Generals Zero Hour\\W3DChineseZH.big";
		//String filePath = "E:\\Code\\C#\\OpenSAGE-master\\src\\OpenSage.Game.Tests\\Data\\Big\\Assets\\test.big";
		
		//String filePath = "E:\\Code\\Test\\data1\\W3DChineseZH.big";
//		Scanner sc = new Scanner(System.in); 
//		System.out.println("请输入big文件的绝对路径");
//		System.out.println("PS：测试程序，建议把big单独放在一个文件夹下");
//		System.out.println("示例：E:\\\\Game\\\\绝命时刻繁体中文硬盘版\\\\Command and Conquer Generals Zero Hour\\\\W3DChineseZH.big\n>: ");
//		String filePath = sc.nextLine();
		
		
		
		String filePath = "E:\\Code\\Test\\test3.big";
		BigArchive archive = new BigArchive(filePath, BigArchiveMode.Read);
		archive.Read();
		
		
//		String filePath = "E:\\Code\\test3.big";
//		String testFilePath1 = "E:\\Code\\Test\\a.txt";
//		String testFilePath2 = "E:\\Code\\Test\\c.txt";
//		BigArchive archive = new BigArchive(filePath, BigArchiveMode.Create);
//		archive.SetEntry(testFilePath1);
//		archive.SetEntry(testFilePath2);
//		archive.Write();
		
//		String testFilePath2 = "Code\\Test\\c.txt";
//		String filePath = "E:\\Code\\Test\\test3.big";
//		BigArchive archive = new BigArchive(filePath, BigArchiveMode.Update);
//		archive.Read();
//		archive.DeleteEntry(archive.GetEntry(testFilePath2));
		
		
		
		/*
		 * 
		 * fourCc: BIGF
			ArchiveSize 82
			NumEntries 2
			FirstOffset 51
			entryOffset 52
			entrySize 14
			entryName a.txt
			entryOffset 66
			entrySize 16
			entryName c.txt
		 * 
		 */
		
	}

}
