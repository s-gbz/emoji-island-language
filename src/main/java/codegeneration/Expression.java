package codegeneration;

import parser.SyntaxTree;

public class Expression extends Semantic{
	// expression->term rightExpression
	// expression.f=rightExpression.f(term.f)
	 public int f(SyntaxTree t, int n){
		System.out.println("CLASS Expression >> n: " + n);
		SyntaxTree term=t.getChild(0), rightExpression=t.getChild(1);
		return rightExpression.value.f(rightExpression,term.value.f(term,UNDEFINED));
		} 	
}//Expression