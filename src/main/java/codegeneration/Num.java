package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class Num extends Semantic{
	
	//-------------------------------------------------------------------------
	// Berechnet die n�chst gr��ere 10er Potenz von v
	// Hilfsmethode f�r num.f
	//-------------------------------------------------------------------------
	private int potenz(int v){
		int p=10;
		while(v/p!=0)
			p=p*10;
		return p;
	}//potenz
	
	
	//-------------------------------------------------------------------------
	// num -> digit num | digit
	// num.f = digit.f*potenz(num.f)+num.f, mit potenz(z)= 10 hoch n, falls
	//												z div (10 hoch n-1)=0
	// num -> digit
	// num.f=digit.f
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		if (t.getChildNumber()==2){
			SyntaxTree digit=t.getChild(0),
					             num=t.getChild(1);
			int v=num.semanticFunction.f(num, UNDEFINED, bufferedWriter);
			return digit.semanticFunction.f(digit, UNDEFINED, bufferedWriter)*potenz(v)+v;
		}else{
			SyntaxTree digit=t.getChild(0);
			return digit.semanticFunction.f(digit, UNDEFINED, bufferedWriter);
		}
	}//f 	
}//Num