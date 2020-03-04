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
			return expression.value.f(expression,UNDEFINED);
		}else{
			SyntaxTree num=t.getChild(0);
			
			//Hier getLexem() und dann convert string to int
			switch(num.getCharacter()){
				case '0' : return 0;
				case '1' : return 1;
				case '2' : return 2;
				case '3' : return 3;
				case '4' : return 4;
				case '5' : return 5;
				case '6' : return 6;
				case '7' : return 7;
				case '8' : return 8;
				case '9' : return 9;
			default: return UNDEFINED;} //Fehler Fall
		}		
	}//f 	
}//Operator