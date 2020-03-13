package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class Else extends Semantic{
	//-------------------------------------------------------------------------
	// else -> emojiElse sequence
	// else.f=sequence.f()
	// else -> epsilon
	// else.f=n
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS ELSE >> n: " + n);
		if(t.getChildNumber()==2) {
			SyntaxTree sequence = t.getChild(1);
			bufferedWriter.write(" \t else");
			int valueToWrite = sequence.semanticFunction.f(sequence, UNDEFINED, bufferedWriter, stack);

			return valueToWrite;
		}else {
			return n;
		}
		
	} 
}
