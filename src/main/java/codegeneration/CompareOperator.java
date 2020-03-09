package codegeneration;

import parser.SyntaxTree;

public class CompareOperator extends Semantic{
	//-------------------------------------------------------------------------
	// compareOperator -> 	emojiUnequal  
	// compareOperator.f=emojiUnequal
	//
	// compareOperator -> 	emojiEqual 
	// compareOperator.f=emojiEqual
	//
	// compareOperator -> 	emojiLessthan 
	// compareOperator.f=emojiLessthan
	//
	// compareOperator -> 	emojiGreaterthan 
	// compareOperator.f=emojiGreaterthan
	//
	// compareOperator -> 	emojiLessthanEquals 
	// compareOperator.f=emojiLessthanEquals
	//
	// compareOperator -> 	emojiGreaterthenEquals 
	// compareOperator.f=emojiGreaterthenEquals

	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS COMPAREOPERATOR >> n: " + n);
		//return t.getToken();
		return UNDEFINED;
	} 
}
