package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class LogicalOperator extends Semantic{
	//-------------------------------------------------------------------------
	// logicalOperator -> emojiLogicalAnd 
	// logicalOperator.f=emojiLogicalAnd
	//
	// logicalOperator -> emojiLogicalOr
	// logicalOperator.f=emojiLogicalOr
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS LOGICALOPERATOR >> n: " + n);
		//return t.getToken();
		return UNDEFINED;
	} 
}
