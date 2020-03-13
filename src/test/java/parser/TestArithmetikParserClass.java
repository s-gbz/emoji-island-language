package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Stack;

import scanner.TokenList;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

public class TestArithmetikParserClass implements TokenList{

	private final static String sourceFilePath = "src//test//resources//";
	private final static String sourceFileName = "ParsedProgram.java";
	private final static String sourceFileClassName = "ParsedProgram";

	public static void main(String args[]) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		int a=2;
		for(int i = 0; a+i<5+a && a<19; i++) {
			
		}
		
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
					BufferedWriter bufferedWriter = createBufferedWriter();
					Stack<String> stack = new Stack<String>();

					System.out.println("Korrekter Ausdruck mit Wert:" +parseTree.semanticFunction.f(parseTree,PROGRAM, bufferedWriter, stack));

					writeStackToFile(stack, bufferedWriter);
					bufferedWriter.close();

					compileParsedProgram();
					loadClass();
					//executeCompiledFile();
				}else
					//Fehlermeldung, falls Ausdruck nicht zu parsen war
					System.out.println("Fehler im Ausdruck");
			else	
				//Fehlermeldung, falls lexikalische Analyse fehlgeschlagen
				System.out.println("Fehler in lexikalischer Analyse");
	}//main

	private static BufferedWriter createBufferedWriter() throws IOException {
		return new BufferedWriter(new FileWriter(sourceFilePath + sourceFileName));
	}

	private static void writeStackToFile(Stack<String> stack, BufferedWriter bufferedWriter) throws IOException {
		for (String entry : stack) {
			bufferedWriter.write(entry);
		}
	}

	private static void compileParsedProgram() throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager =
				compiler.getStandardFileManager(null, null, null);

		fileManager.setLocation(StandardLocation.CLASS_PATH,
				Arrays.asList(new File("")));

		File sourceFile = new File(sourceFilePath + sourceFileName);
		compiler.getTask(null,
				fileManager,
				null,
				null,
				null,
				fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile)))
				.call();
		fileManager.close();
	}

	private static void executeCompiledFile() {
		try {
			Class params[] = {};
			Object paramsObj[] = {};
			Class thisClass = Class.forName(sourceFileClassName);
			Object iClass = thisClass.newInstance();
			Method thisMethod = thisClass.getDeclaredMethod("run", params);
			thisMethod.invoke(iClass, paramsObj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadClass() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

		URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {
				new URL(
						"file:///" + sourceFilePath
				)
		});

		Class params[] = {};
		Object paramsObj[] = {};
		Class thisClass = urlClassLoader.loadClass(sourceFileClassName);
		Object iClass = thisClass.newInstance();
		Method thisMethod = thisClass.getDeclaredMethod("run", params);
		thisMethod.invoke(iClass, paramsObj);
	}
}
