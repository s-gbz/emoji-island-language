package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class Else extends Semantic{
	//-------------------------------------------------------------------------
	// else -> emojiElse sequence
	// else.f=sequence.f()
	// else -> epsilon
	// else.f=n
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS ELSE >> n: " + n);
		if(t.getChildNumber()==2) {
			SyntaxTree sequence = t.getChild(1);
			return sequence.semanticFunction.f(sequence, UNDEFINED, bufferedWriter);
		}else {
			return n;
		}
		
	} 
}
