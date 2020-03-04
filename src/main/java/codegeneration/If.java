package codegeneration;

import parser.SyntaxTree;

public class If extends Semantic{
	//-------------------------------------------------------------------------
	// if -> emojiIf openpar statement closepar sequence 
	// if.f=sequence.f(statement.f)
	// if -> emojiIf openpar statement closepar sequence else
	// if.f=else.f(sequence.f(statement.f))
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS IF >> n: " + n);
		if(t.getChildNumber()==5) {
			SyntaxTree statement = t.getChild(2), sequence = t.getChild(4);
			return sequence.value.f(sequence, statement.value.f(statement, UNDEFINED));
		}else {
			SyntaxTree statement = t.getChild(2), sequence = t.getChild(4), elseTree = t.getChild(5);
			return elseTree.value.f(elseTree, sequence.value.f(sequence, statement.value.f(statement, UNDEFINED)));
		}
		
	} 
}
