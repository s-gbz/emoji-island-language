package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class Program extends Semantic{
	// program -> emojiStartCode sequence emojiEndCode
	// program.f=sequence.f
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		SyntaxTree sequence=t.getChild(1);
		bufferedWriter.write("public class ParsedProgram {\n" +
				"    public static void main(String[] args)");
		bufferedWriter.flush();
		return sequence.semanticFunction.f(sequence,UNDEFINED, bufferedWriter);
	} 
}

