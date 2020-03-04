package codegeneration;

import parser.SyntaxTree;

public class While extends Semantic{

	//-------------------------------------------------------------------------
	// while -> emojiWhile openpar statement closepar sequence
	// while.f=sequence.f(statement.f)
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS WHILE >>  n: " + n);
		SyntaxTree statement = t.getChild(2), sequence = t.getChild(4);
		return sequence.value.f(sequence, statement.value.f(statement, UNDEFINED));
	} 
}
