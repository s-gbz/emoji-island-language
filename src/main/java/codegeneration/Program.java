package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class Program extends Semantic{
	// program -> emojiStartCode sequence emojiEndCode
	// program.f=sequence.f
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		SyntaxTree sequence=t.getChild(1);
		bufferedWriter.write("public class ParsedProgram {\n" +
				"    public static void main(String[] args)");
		int valueToWrite = sequence.semanticFunction.f(sequence,UNDEFINED, bufferedWriter, stack);

		bufferedWriter.write("\t\n}");
		return valueToWrite;
	} 
}

