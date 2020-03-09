package codegeneration;

import parser.SyntaxTree;

public class Sequence extends Semantic{	
	// sequence -> '{' instruction '}'
	// sequence.f=instruction.f
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS SEQUENCE >> >> n: " + n);
		SyntaxTree instruction=t.getChild(1);
		return instruction.semanticFunction.f(instruction,UNDEFINED);
	} 
}
