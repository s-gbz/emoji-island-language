package codegeneration;

import parser.SyntaxTree;

public class Assignment extends Semantic{
	//-------------------------------------------------------------------------
	// assignment ->  ident '<-' expression ';'
	// assigment.f=expression.f
	// assignment ->  ident '<-' expression 'is' 'EMOJI_INT' ';'
	// assigment.f=expression.f
	// assignment ->  ident '<-' END_SINGLEQOUTE 'is' 'EMOJI_CHAR' ';'
	// assigment.f=n
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS ASSIGNMENT >> n: " + n);
		if(t.getChildNumber()==4) {
			SyntaxTree expression=t.getChild(2);
			return expression.value.f(expression,UNDEFINED);
		}else {
			System.out.println("CLASS ASSIGNMENT >> LEXEM: " + t.getChild(4).getLexem());
			switch(t.getChild(4).getLexem()) {
				case ":bar_chart:" : 
					SyntaxTree expression=t.getChild(2);
					return expression.value.f(expression,UNDEFINED);
				case":memo:" :
					return n;
				default: return n;
			}

		}
	} 
}
