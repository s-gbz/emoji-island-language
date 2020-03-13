package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class RightTerm extends Semantic{
	//-------------------------------------------------------------------------
	// rightTerm -> '*' operator rightTerm 
	// rightTerm.f(n)=n*rightTerm.f(operator.f)
	//
	// rightTerm -> '/' operator rightTerm 
	// rightTerm.f(n)=n/rightTerm.f(operator.f)
	//
	// rightTerm -> Epsilon
	// rightTerm.f(n)=n
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS RightTerm >> n: " + n);
		if (t.getChildNumber()==3){
			SyntaxTree symbol=t.getChild(0), operator=t.getChild(1), rightTerm=t.getChild(2);
			int valueToWrite;

			switch(symbol.getLexem()){
				case ":heavy_multiplication_x:" :
					stack.push("*");
					valueToWrite= n*rightTerm.semanticFunction.f(rightTerm,operator.semanticFunction.f(operator,UNDEFINED, bufferedWriter, stack), bufferedWriter, stack);
					//bufferedWriter.write(valueToWrite);
					return valueToWrite;
				case ":heavy_division_sign:" :
					stack.push("/");
					valueToWrite = n/rightTerm.semanticFunction.f(rightTerm,operator.semanticFunction.f(operator,UNDEFINED, bufferedWriter, stack), bufferedWriter, stack);
					//bufferedWriter.write(valueToWrite);
					return valueToWrite;
			default: return UNDEFINED; //Fehler Fall
			}
		}else {//Epsilon Fall 
			return n;	
		}
	}//f 	
}//RightTerm
