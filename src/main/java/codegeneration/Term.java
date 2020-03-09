package codegeneration;

import parser.SyntaxTree;

public class Term extends Semantic{
	// term->operator rightTerm
	// term.f=rightTerm.f(operator.f)
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS Term >> n: " + n);
		SyntaxTree operator=t.getChild(0), 
				   rightTerm=t.getChild(1);
		
		return rightTerm.semanticFunction.f(rightTerm,operator.semanticFunction.f(operator,UNDEFINED));
	} 	
}//Term