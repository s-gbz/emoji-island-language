package codegeneration;

import parser.SyntaxTree;

public class RightExpression extends Semantic{
	//-------------------------------------------------------------------------
	// rightExpression -> '+' term rightExpression 
	// rightExpression.f(n)=n+rightExpression.f(term.f)
	//
	// rightExpression -> '-' term rightExpression 
	// rightExpression.f(n)=n-rightExpression.f(term.f)
	//
	// rightExpression -> Epsilon
	// rightExpression.f(n)=n
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS RightExpression >> n: " + n);
		if (t.getChildNumber()==3){
			SyntaxTree symbol=t.getChild(0), term=t.getChild(1), rightExpression=t.getChild(2);
			
			switch(symbol.getLexem()){
				case ":heavy_plus_sign:" : 	return n+rightExpression.value.f(rightExpression,term.value.f(term,UNDEFINED));
				case ":heavy_minus_sign:" :	return n-rightExpression.value.f(rightExpression,term.value.f(term,UNDEFINED));
			default: return UNDEFINED;
			}
		}else {
			return n;		
		}
	}//f 	
}//RightExpression