package codegeneration;

import parser.SyntaxTree;

public class Else extends Semantic{
	//-------------------------------------------------------------------------
	// else -> emojiElse sequence
	// else.f=sequence.f()
	// else -> epsilon
	// else.f=n
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS ELSE >> n: " + n);
		if(t.getChildNumber()==2) {
			SyntaxTree sequence = t.getChild(1);
			return sequence.value.f(sequence, UNDEFINED);
		}else {
			return n;
		}
		
	} 
}
