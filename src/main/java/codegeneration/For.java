package codegeneration;

import parser.SyntaxTree;

public class For extends Semantic{
	//-------------------------------------------------------------------------
	// for -> emojiFor openpar forStatement closepar sequence
	// for.f=sequence.f(forStatement.f)
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS FOR >> n: " + n);
		SyntaxTree forStatement = t.getChild(2), sequence = t.getChild(4);
		return sequence.value.f(sequence, forStatement.value.f(forStatement, UNDEFINED));		
	} 
}
