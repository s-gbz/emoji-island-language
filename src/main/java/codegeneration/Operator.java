package codegeneration;

import parser.SyntaxTree;

public class Operator extends Semantic{
	//-------------------------------------------------------------------------
	// operator -> (expression) 
	// operator.f = expression.f
	//
	// operator -> num 
	// operator.f = num.f
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS Operator >>  n: " + n);
		if (t.getChildNumber()==3){
			SyntaxTree expression=t.getChild(1);
			return expression.semanticFunction.f(expression,UNDEFINED);
		}else{
			SyntaxTree num=t.getChild(0);
			
			char leafValue = num.getCharacter();
			if(	leafValue == '0' || leafValue == '1' || leafValue == '2' || leafValue == '3' || leafValue == '4' || 
				leafValue == '5' || leafValue == '6' || leafValue == '7' || leafValue == '8' || leafValue == '9' ) 
			{
				return Integer.parseInt(num.getLexem());
			}
			return UNDEFINED;
		}		
	}//f 	
}//Operator