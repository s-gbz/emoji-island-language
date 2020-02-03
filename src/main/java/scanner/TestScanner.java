package scanner;

import java.io.File;

class TestScanner{
	static public void main(String args[]){
		NumScanner scanner;
		scanner = new NumScanner();
		String filePath = new File("").getAbsolutePath();
		System.out.println("path" + filePath);
		if (scanner.readInputEmoji(filePath + "\\testdatei_arithmetik_emoji.txt")){
			scanner.printInputStream();
			if(scanner.lexicalAnalysis())
				scanner.printTokenStream();
		}
		else
			System.out.println("Scanner beendet");
	}
}