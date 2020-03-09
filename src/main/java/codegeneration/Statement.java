package codegeneration;

import parser.SyntaxTree;

public class Statement extends Semantic{
	//-------------------------------------------------------------------------
	// statement -> expression compareOperator expression logical
	// statement.f=logical.f(expressionSecound.f(compareOperator.f(expressionFirst())))
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS STATEMENT >> n: " + n);
		SyntaxTree expressionFirst = t.getChild(0), compareOperator = t.getChild(1), 
				expressionSecound = t.getChild(2), logical = t.getChild(3);
		return logical.semanticFunction.f(logical, expressionSecound.semanticFunction.f(expressionSecound, 
				compareOperator.semanticFunction.f(compareOperator, expressionFirst.semanticFunction.f(expressionFirst, UNDEFINED))));
	} 
}
