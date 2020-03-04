package codegeneration;

import parser.SyntaxTree;

public class Program extends Semantic{
	// program -> emojiStartCode sequence emojiEndCode
	// program.f=sequence.f
	public int f(SyntaxTree t, int n){
		SyntaxTree sequence=t.getChild(1);
		return sequence.value.f(sequence,UNDEFINED);
	} 
}

