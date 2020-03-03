package parser;

import java.io.File;

import scanner.TokenList;

public class TestArithmetikParserClass implements TokenList{

	public static void main(String args[]){
		
		// Anlegen des Wurzelknotens für den Syntaxbaum. Dem Konstruktor
		// wid als Token das Startsymbol der Grammatik übergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);
		
		// Anlegen des Parsers als Instanz der Klasse ArithmetikParserClass
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree);
		
		String filePath = new File("src\\test\\resources").getAbsolutePath();
		System.out.println("path" + filePath);
		
		//parser.readInput(filePath + "\\testdatei_arithmetik.txt")
		// Einlesen der Datei 		
		if (parser.readInputEmoji(filePath + "\\testdatei_arithmetik_emoji.txt"))
			// lexikalische Analyse durchführen
			if (parser.lexicalAnalysis())
				//Aufruf des Parsers und Test, ob gesamte Eingabe gelesen
				if (parser.checkGrammarRuleProgram(parseTree)&& parser.inputEmpty()){
					//Ausgabe des Syntaxbaumes und des sematischen Wertes
					parseTree.printSyntaxTree(0);
					/*
					System.out.println("Korrekter Ausdruck mit Wert:"
					+parseTree.value.f(parseTree,UNDEFINED));
	*/
				}else
					//Fehlermeldung, falls Ausdruck nicht zu parsen war
					System.out.println("Fehler im Ausdruck");
			else	
				//Fehlermeldung, falls lexikalische Analyse fehlgeschlagen
				System.out.println("Fehler in lexikalischer Analyse");
	}//main
}
