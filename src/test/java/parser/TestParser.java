package parser;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.vdurmont.emoji.EmojiParser;

import scanner.TokenList;






public class TestParser implements TokenList {

	/*
	@Test
	public void parserVergleich1() {

		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		// 1<2
		char input[] = { '1', ':', 'r', 'e', 'w', 'i', 'n', 'd', ':', '2' };
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde
		Assert.assertTrue(parser.vergleich(parseTree));
		Assert.assertTrue(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);

	}
	
	@Test
	public void parserVergleich2() {

		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		// 1<2
		char input[] = { '8', ':', 'r', 'e', 'w', 'i', 'n', 'd', ':', '2' };
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde
		Assert.assertTrue(parser.vergleich(parseTree));
		Assert.assertTrue(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);

	}
	*/
	/*
	@Test
	public void parserArethmetik1() {

		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		// 1<2
		char input[] = { '9', 'M', ':', 'd', 'i', 'v','2' };
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde
		Assert.assertTrue(parser.expression(parseTree));
		Assert.assertTrue(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);

		LinkedList<InputSign> inputSigns = new LinkedList<>();
		//parseTree.printCharacters(0,inputSigns);
		System.out.println(" ");
		Codegenerator codegenerator = new Codegenerator();
		//Instructions ins = codegenerator.generateCode(inputSigns);
		//ins.printOutAllInstructions();
		//Interpreter interpreter = new Interpreter(4,4);
		//interpreter.setInstructions(ins);
		//interpreter.run();

	}
	*/
	
	@Test
	public void parserArethmetik2() {

		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		// 2*(3+2)
		char input[] = { '2', ':', 'h', 'e', 'a', 'v', 'y', '_', 'm', 'u', 'l', 't', 'i', 'p', 'l', 'i', 'c', 'a', 't', 'i', 'o', 'n', '_', 'x', ':', '(', '3', ':', 'h', 'e', 'a', 'v', 'y', '_', 'p', 'l', 'u', 's', '_', 's', 'i', 'g', 'n', ':', '2', ')' };
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		
		if (parser.lexicalAnalysis())
			//Aufruf des Parsers und Test, ob gesamte Eingabe gelesen
			if (parser.expression(parseTree)&& parser.inputEmpty()){
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
		
		
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde
		Assert.assertTrue(parser.expression(parseTree));
		Assert.assertTrue(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);

	}

	
	@Test
	public void parserArethmetik3() {

		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		// 2*(3+2)
		char input[] = { '2', ':', 'h', 'e', 'a', 'v', 'y', '_', 'm', 'u', 'l', 't', 'i', 'p', 'l', 'i', 'c', 'a', 't', 'i', 'o', 'n', '_', 'x', ':', '(', '3', ':', 'h', 'e', 'a', 'v', 'y', '_', 'p', 'l', 'u', 's', '_', 's', 'i', 'g', 'n', ':', '2' };
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde

		Assert.assertTrue(parser.expression(parseTree));
		Assert.assertTrue(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);

	}

	/*

	@Test
	public void parserArethmetik4() {

		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		char input[] = { '2', 'M', ':', 'm', 'u', 'l', 't', ')', '3', 'M', ':', 'a', 'd', 'd', '2' };
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde
		Assert.assertFalse(parser.expression(parseTree));
		// Assert.assertFalse(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);

	}

	 */
	
	@Test
	public void parserArethmetik5() {

		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		// 1<2
		char input[] = { '1', '1', ':', 'h', 'e', 'a', 'v', 'y', '_', 'p', 'l', 'u', 's', '_', 's', 'i', 'g', 'n', ':', '2' };
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde
		Assert.assertTrue(parser.expression(parseTree));
		Assert.assertTrue(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);

	}
	/*
	@Test
	public void parserBedingung1() {
		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		String inputString = ":white_check_mark:(2:rewind:1){}";
		// 1<2
		char input[] = inputString.toCharArray();
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde
		Assert.assertTrue(parser.bedingungIf(parseTree));
		Assert.assertTrue(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);
	}
	
	@Test
	public void parserBedingung2() {
		// Anlegen des Wurzelknotens fuer den Syntaxbaum.
		// Dem Konstruktor wird als Token das Startsymbol der Grammatik uebergeben
		SyntaxTree parseTree = new SyntaxTree(EXPRESSION);

		String inputStringEmoji = "✅(1⏮2){}";
		String inputString = EmojiParser.parseToAliases(inputStringEmoji);
		// 1<2
		char input[] = inputString.toCharArray();
		ArithmetikParserClass parser = new ArithmetikParserClass(parseTree, input);
		// teste ob die Eingabe gueltig ist & ob gesamte die Eingabe gelesen wurde
		Assert.assertTrue(parser.bedingungIf(parseTree));
		Assert.assertTrue(parser.inputEmpty());
		// Ausgabe des Syntaxbaumes und des sematischen Wertes
		parseTree.printSyntaxTree(0);
	}
	*/
	
	
}
