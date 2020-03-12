package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class Assignment extends Semantic{
	//-------------------------------------------------------------------------
	// assignment ->  ident '<-' expression ';'
	// assigment.f=expression.f
	// assignment ->  ident '<-' expression 'is' 'EMOJI_INT' ';'
	// assigment.f=expression.f
	// assignment ->  ident '<-' END_SINGLEQOUTE 'is' 'EMOJI_CHAR' ';'
	// assigment.f=n
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS ASSIGNMENT >> n: " + n);
		if(t.getChildNumber()==4) {
			SyntaxTree expression=t.getChild(2);
			bufferedWriter.write(t.getChild(0).getLexem() + " = " + t.getChild(0).getLexem());

			int valueToWrite = expression.semanticFunction.f(expression, UNDEFINED, bufferedWriter, stack);

			bufferedWriter.write(";\n");

			return valueToWrite;
		}else {

			switch(t.getChild(4).getLexem()) {
				case ":bar_chart:" : 
					SyntaxTree expression=t.getChild(2);

					int valueToWrite = expression.semanticFunction.f(expression, UNDEFINED, bufferedWriter, stack);
					bufferedWriter.write("int " + t.getChild(0).getLexem() + " = " + valueToWrite + ";");
					return n;
				case":memo:" :

					bufferedWriter.write("char " + t.getChild(0).getLexem() + " = " + t.getChild(2).getLexem() + ";\n");
					return n;
				default:
					//bufferedWriter.write(n);
					return n;
			}

		}
	} 
}
