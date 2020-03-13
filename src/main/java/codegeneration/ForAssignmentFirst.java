package codegeneration;

import parser.SyntaxTree;

import java.io.*;
import java.util.Stack;
import java.util.stream.Collectors;

public class ForAssignment extends Semantic{
	//-------------------------------------------------------------------------
	// forAssignment ->  ident '<-' expression 
	// forAssigment.f=expression.f
	// forAssignment ->  ident '<-' expression 'is' 'EMOJI_INT' 
	// forAssigment.f=expression.f
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS FORASSIGNMENT >> n: " + n);
		int valueToWrite;
		if(t.getChildNumber()==3) {
			SyntaxTree expression=t.getChild(2);

			bufferedWriter.write("; " + t.getChild(0).getLexem() + " = " + t.getChild(0).getLexem() + "");

			valueToWrite = expression.semanticFunction.f(expression,UNDEFINED, bufferedWriter, stack);
			//bufferedWriter.write(valueToWrite);
			//bufferedWriter.write(n);
			return valueToWrite;
		}else {
			System.out.println("CLASS ASSIGNMENT >> LEXEM: " + t.getChild(4).getLexem());
			switch(t.getChild(4).getLexem()) {
				case ":bar_chart:" :
					SyntaxTree expression=t.getChild(2);
					//bufferedWriter.write("int " + t.getChild(0).getLexem() + " = " + expression.getLexem());
					valueToWrite = expression.semanticFunction.f(expression,UNDEFINED, bufferedWriter, stack);
					//bufferedWriter.write(valueToWrite);
					return valueToWrite;
				default: return n;
			}

		}
	}
}
