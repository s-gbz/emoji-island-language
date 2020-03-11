package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import scanner.TokenList;

public class TestArithmetikParserClass implements TokenList{

	public static void main(String args[]) throws IOException {
		
		// Anlegen des Wurzelknotens f�r den Syntaxbaum. Dem Konstruktor
		// wid als Token das Startsymbol der Grammatik �bergeben
		SyntaxTree parseTree = new SyntaxTree(PROGRAM);
		
		// Anlegen des Parsers als Instanz der Klasse ArithmetikParserClass
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree);
		
		String filePath = new File("src\\test\\resources").getAbsolutePath();
		System.out.println("path" + filePath);
		
		//parser.readInput(filePath + "\\testdatei_arithmetik.txt")
		// Einlesen der Datei 		
		if (parser.readInputEmoji(filePath + "\\testdatei_arithmetik_emoji.txt"))
			// lexikalische Analyse durchf�hren
			if (parser.lexicalAnalysis())
				//Aufruf des Parsers und Test, ob gesamte Eingabe gelesen
				if (parser.checkGrammarRuleProgram(parseTree)&& parser.inputEmpty()){
					//Ausgabe des Syntaxbaumes und des sematischen Wertes
					parseTree.printSyntaxTree(0);
					//parser.printTokenStream();
					BufferedWriter bufferedWriter = createBufferedWriter("ParsedProgram.java");
					Stack<String> stack = new Stack<String>();

					System.out.println("Korrekter Ausdruck mit Wert:" +parseTree.semanticFunction.f(parseTree,PROGRAM, bufferedWriter, stack));
					bufferedWriter.close();
				}else
					//Fehlermeldung, falls Ausdruck nicht zu parsen war
					System.out.println("Fehler im Ausdruck");
			else	
				//Fehlermeldung, falls lexikalische Analyse fehlgeschlagen
				System.out.println("Fehler in lexikalischer Analyse");
	}//main

	public static BufferedWriter createBufferedWriter(String fileName) throws IOException {
		return new BufferedWriter(new FileWriter(fileName));
	}
}
