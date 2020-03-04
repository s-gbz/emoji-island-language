package codegeneration;

import parser.SyntaxTree;

public class LogicalOperator extends Semantic{
	//-------------------------------------------------------------------------
	// logicalOperator -> emojiLogicalAnd 
	// logicalOperator.f=emojiLogicalAnd
	//
	// logicalOperator -> emojiLogicalOr
	// logicalOperator.f=emojiLogicalOr
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS LOGICALOPERATOR >> n: " + n);
		return t.getToken();
	} 
}
