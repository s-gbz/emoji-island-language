package codegeneration;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import parser.SyntaxTree;

public class Condition extends Semantic{
	//-------------------------------------------------------------------------
	// condition -> expression compareOperator expression 
	// condition.f=expressionSecound.f(compareOperator.f(expressionFirst()))
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS CONDITION >> n: " + n);
		SyntaxTree expressionFirst = t.getChild(0), compareOperator = t.getChild(1), 
				expressionSecound = t.getChild(2);

		//bufferedWriter.write(expressionFirst.getChild(0).getChild(0).getChild(0).getLexem());

		int valueToWrite = expressionSecound.semanticFunction.f(expressionSecound,
				compareOperator.semanticFunction.f(compareOperator, expressionFirst.semanticFunction.f(expressionFirst, UNDEFINED, bufferedWriter, stack), bufferedWriter, stack), bufferedWriter, stack);
		for (String entry : stack) {
			bufferedWriter.write(entry);
		}
		stack.clear();

		//bufferedWriter.write(expressionSecound.getChild(0).getChild(0).getChild(0).getLexem());
		//bufferedWriter.write(")");

		return valueToWrite;
	}
}
