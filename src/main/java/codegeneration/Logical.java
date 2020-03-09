package codegeneration;

import parser.SyntaxTree;

public class Logical extends Semantic{
	//-------------------------------------------------------------------------
	// logical -> logicalOperator statement
	// logical.f=statement.f(logicalOperator.f())
	//
	// logical -> epsilon
	// logical.f=n
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS LOGICAL >> n: " + n);
		if(t.getChildNumber()==2) {
			SyntaxTree logicalOperator = t.getChild(0), statement = t.getChild(1);
			return statement.semanticFunction.f(statement, logicalOperator.semanticFunction.f(logicalOperator, UNDEFINED));
		}else {
			return n;
		}

	} 
}
