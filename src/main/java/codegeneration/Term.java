package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class Term extends Semantic{
	// term->operator rightTerm
	// term.f=rightTerm.f(operator.f)
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS Term >> n: " + n);
		SyntaxTree operator=t.getChild(0), 
				   rightTerm=t.getChild(1);
		
		return rightTerm.semanticFunction.f(rightTerm,operator.semanticFunction.f(operator,UNDEFINED, bufferedWriter, stack), bufferedWriter, stack);
	} 	
}//Term