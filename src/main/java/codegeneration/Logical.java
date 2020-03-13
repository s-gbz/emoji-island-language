package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class Logical extends Semantic{
	//-------------------------------------------------------------------------
	// logical -> logicalOperator statement
	// logical.f=statement.f(logicalOperator.f())
	//
	// logical -> epsilon
	// logical.f=n
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS LOGICAL >> n: " + t.getChildNumber());
		if(t.getChildNumber()==2) {
			SyntaxTree logicalOperator = t.getChild(0), statement = t.getChild(1);
			int valueToWrite = statement.semanticFunction.f(statement, logicalOperator.semanticFunction.f(logicalOperator, UNDEFINED, bufferedWriter, stack), bufferedWriter, stack);
			//bufferedWriter.write(")");
			//bufferedWriter.write(valueToWrite);
			return valueToWrite;
		}else {
			//bufferedWriter.write(")");
			//bufferedWriter.write(n);
			return -1; //Epsilon-Fall
		}

	} 
}
