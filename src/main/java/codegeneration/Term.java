package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class Term extends Semantic{
	// term->operator rightTerm
	// term.f=rightTerm.f(operator.f)
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS Term >> n: " + n);
		SyntaxTree operator=t.getChild(0), 
				   rightTerm=t.getChild(1);
		
		return rightTerm.semanticFunction.f(rightTerm,operator.semanticFunction.f(operator,UNDEFINED, bufferedWriter), bufferedWriter);
	} 	
}//Term