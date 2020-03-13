package codegeneration;

import parser.SyntaxTree;

import java.io.*;
import java.util.Stack;
import java.util.stream.Collectors;

public class ForAssignmentFirst extends Semantic{
	//-------------------------------------------------------------------------
	// forAssignmentFirst ->  ident '<-' expression 'is' 'EMOJI_INT' 
	// forAssigment.f=expression.f
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS FORASSIGNMENT >> n: " + n);
		int valueToWrite;
				
		switch(t.getChild(4).getLexem()) {
			case ":bar_chart:" :
				SyntaxTree expression=t.getChild(2);
				valueToWrite = expression.semanticFunction.f(expression,UNDEFINED, bufferedWriter, stack);
				bufferedWriter.write("int " + t.getChild(0).getLexem() + " = ");
				for (String entry : stack) {
					bufferedWriter.write(entry);
				}
				stack.clear();
				bufferedWriter.write("; ");
				//bufferedWriter.write(valueToWrite);
				return valueToWrite;
			default: return n;
		
		}
	}
}
