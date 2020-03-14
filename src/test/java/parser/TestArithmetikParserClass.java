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
import java.util.concurrent.TimeUnit;

import scanner.TokenList;

import javax.tools.*;

public class TestArithmetikParserClass implements TokenList{

	private final static String sourceFilePath = "src//test//resources//";
	private final static String sourceFileName = "ParsedProgram.java";
	private final static String sourceFileClassName = "ParsedProgram";
	private static Boolean compilationNotFinished = true;
	private final static Object compilationLock = new Object();

	public static synchronized void main(String args[]) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
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
					BufferedWriter bufferedWriter = createBufferedWriter(sourceFilePath + sourceFileName);
					Stack<String> stack = new Stack<String>();

					System.out.println("Korrekter Ausdruck mit Wert:" +parseTree.semanticFunction.f(parseTree,PROGRAM, bufferedWriter, stack));

					writeStackToFile(stack, bufferedWriter);
					bufferedWriter.close();

					createDummyClassFile();
					compileParsedProgram();
					Class compiledClass = loadClass();
					executeCompiledClass(compiledClass);
				}else
					//Fehlermeldung, falls Ausdruck nicht zu parsen war
					System.out.println("Fehler im Ausdruck");
			else	
				//Fehlermeldung, falls lexikalische Analyse fehlgeschlagen
				System.out.println("Fehler in lexikalischer Analyse");
	}//main

	private static BufferedWriter createBufferedWriter(String fileName) throws IOException {
		return new BufferedWriter(new FileWriter(fileName));
	}

	private static void writeStackToFile(Stack<String> stack, BufferedWriter bufferedWriter) throws IOException {
		for (String entry : stack) {
			bufferedWriter.write(entry);
		}
	}

	private static void createDummyClassFile() throws IOException {
		BufferedWriter bufferedWriter = createBufferedWriter(sourceFilePath + sourceFileClassName + ".class");
		bufferedWriter.close();
	}

	private static void compileParsedProgram() throws IOException {
		synchronized(compilationLock) {
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager =
					compiler.getStandardFileManager(diagnostics, null, null);

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

			compilationLock.notify();
			compilationNotFinished = false;
		}
	}

	private static Class loadClass() throws MalformedURLException, ClassNotFoundException, InterruptedException {
		Class loadedClass = null;
		synchronized (compilationLock) {
			while (compilationNotFinished) {
				compilationLock.wait();
			}

			URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
					new URL(
							"file:///" + sourceFilePath
					)
			});

			loadedClass = urlClassLoader.loadClass(sourceFileClassName);
		}
		return loadedClass;
	}


	private static void executeCompiledClass(Class compiledClass) {
		try {
			Class params[] = {};
			Object paramsObj[] = {};

			Object classInstance = compiledClass.newInstance();
			Method runMethod = compiledClass.getDeclaredMethod("run", params);

			runMethod.invoke(classInstance, paramsObj);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
