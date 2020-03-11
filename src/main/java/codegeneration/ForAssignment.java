package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class ForAssignment extends Semantic{
	//-------------------------------------------------------------------------
	// forAssignment ->  ident '<-' expression 
	// forAssigment.f=expression.f
	// forAssignment ->  ident '<-' expression 'is' 'EMOJI_INT' 
	// forAssigment.f=expression.f
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS FORASSIGNMENT >> n: " + n);
		if(t.getChildNumber()==3) {
			SyntaxTree expression=t.getChild(2);
			bufferedWriter.write("int " + t.getChild(0).getLexem() + " = " + expression.getLexem());
			return expression.semanticFunction.f(expression,UNDEFINED, bufferedWriter);
		}else {
			System.out.println("CLASS ASSIGNMENT >> LEXEM: " + t.getChild(4).getLexem());
			switch(t.getChild(4).getLexem()) {
				case ":bar_chart:" :
					SyntaxTree expression=t.getChild(2);
					bufferedWriter.write("int " + t.getChild(2).getLexem() + " = " + expression.getLexem());
					return expression.semanticFunction.f(expression,UNDEFINED, bufferedWriter);
				default: return n;
			}

		}
	} 
}
