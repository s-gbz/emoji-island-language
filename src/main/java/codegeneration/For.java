package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class For extends Semantic{
	//-------------------------------------------------------------------------
	// for -> emojiFor openpar forStatement closepar sequence
	// for.f=sequence.f(forStatement.f)
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS FOR >> n: " + n);
		SyntaxTree forStatement = t.getChild(2), sequence = t.getChild(4);
		bufferedWriter.write("for(");
		int valueToWrite = sequence.semanticFunction.f(sequence, forStatement.semanticFunction.f(forStatement, UNDEFINED, bufferedWriter), bufferedWriter);

		bufferedWriter.write(valueToWrite + ")");
		return valueToWrite;
	} 
}
