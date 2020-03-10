package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class While extends Semantic{

	//-------------------------------------------------------------------------
	// while -> emojiWhile openpar statement closepar sequence
	// while.f=sequence.f(statement.f)
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS WHILE >>  n: " + n);
		SyntaxTree statement = t.getChild(2), sequence = t.getChild(4);
		return sequence.semanticFunction.f(sequence, statement.semanticFunction.f(statement, UNDEFINED, bufferedWriter), bufferedWriter);
	} 
}
