package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class Assignment extends Semantic{
	//-------------------------------------------------------------------------
	// assignment ->  ident '<-' expression ';'
	// assigment.f=expression.f
	// assignment ->  ident '<-' expression 'is' 'EMOJI_INT' ';'
	// assigment.f=expression.f
	// assignment ->  ident '<-' END_SINGLEQOUTE 'is' 'EMOJI_CHAR' ';'
	// assigment.f=n
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS ASSIGNMENT >> n: " + n);
		if(t.getChildNumber()==4) {
			SyntaxTree expression=t.getChild(2);
			return expression.semanticFunction.f(expression, UNDEFINED, bufferedWriter);
		}else {

			switch(t.getChild(4).getLexem()) {
				case ":bar_chart:" : 
					SyntaxTree expression=t.getChild(2);
					return expression.semanticFunction.f(expression, UNDEFINED, bufferedWriter);
				case":memo:" :
					return n;
				default: return n;
			}

		}
	} 
}
