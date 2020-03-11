package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class Expression extends Semantic{
	// expression->term rightExpression
	// expression.f=rightExpression.f(term.f)
	 public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS Expression >> n: " + n);
		SyntaxTree term=t.getChild(0), rightExpression=t.getChild(1);
		return rightExpression.semanticFunction.f(rightExpression,term.semanticFunction.f(term, UNDEFINED, bufferedWriter, stack), bufferedWriter, stack);
		} 	
}//Expression