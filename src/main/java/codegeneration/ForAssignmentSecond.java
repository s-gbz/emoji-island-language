package codegeneration;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import parser.SyntaxTree;

public class ForAssignmentSecond extends Semantic {
	//-------------------------------------------------------------------------
	// forAssignmentSecond ->  ident '<-' expression 
	// forAssigment.f=expression.f

	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS FORASSIGNMENT >> n: " + n);
		int valueToWrite;

		SyntaxTree expression=t.getChild(2);
		valueToWrite = expression.semanticFunction.f(expression,UNDEFINED, bufferedWriter, stack);
		
		bufferedWriter.write("; " + t.getChild(0).getLexem() + " = ");
		for (String entry : stack) {
			bufferedWriter.write(entry);
		}
		stack.clear();
		//bufferedWriter.write(valueToWrite);
		//bufferedWriter.write(n);
		return valueToWrite;
			
	}
}
