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
		System.out.println("CLASS LOGICALOPERATOR >> t: " + t.getChildNumber());
		SyntaxTree logicalOperator = t.getChild(0);
		String leafValue = logicalOperator.getTokenString();

		switch (leafValue) {
			case "EMOJI_LOGICAL_AND":
				//bufferedWriter.write("==");
				stack.push(" && ");
				break;
			case "EMOJI_LOGICAL_OR":
				//bufferedWriter.write("!=");
				stack.push(" || ");
				break;
		}
		return UNDEFINED;
	} 
}
