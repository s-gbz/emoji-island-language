package codegeneration;

import parser.SyntaxTree;

public class ForAssignment extends Semantic{
	//-------------------------------------------------------------------------
	// forAssignment ->  ident '<-' expression 
	// forAssigment.f=expression.f
	// forAssignment ->  ident '<-' expression 'is' 'EMOJI_INT' 
	// forAssigment.f=expression.f
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS FORASSIGNMENT >> n: " + n);
		if(t.getChildNumber()==3) {
			SyntaxTree expression=t.getChild(2);
			return expression.semanticFunction.f(expression,UNDEFINED);
		}else {
			System.out.println("CLASS ASSIGNMENT >> LEXEM: " + t.getChild(4).getLexem());
			switch(t.getChild(4).getLexem()) {
				case ":bar_chart:" : 
					SyntaxTree expression=t.getChild(2);
					return expression.semanticFunction.f(expression,UNDEFINED);
				default: return n;
			}

		}
	} 
}
