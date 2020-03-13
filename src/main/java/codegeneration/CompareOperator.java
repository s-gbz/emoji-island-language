package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class CompareOperator extends Semantic{
	//-------------------------------------------------------------------------
	// compareOperator -> 	emojiUnequal  
	// compareOperator.f=emojiUnequal
	//
	// compareOperator -> 	emojiEqual 
	// compareOperator.f=emojiEqual
	//
	// compareOperator -> 	emojiLessthan 
	// compareOperator.f=emojiLessthan
	//
	// compareOperator -> 	emojiGreaterthan 
	// compareOperator.f=emojiGreaterthan
	//
	// compareOperator -> 	emojiLessthanEquals 
	// compareOperator.f=emojiLessthanEquals
	//
	// compareOperator -> 	emojiGreaterthenEquals 
	// compareOperator.f=emojiGreaterthenEquals

	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS COMPAREOPERATOR >> n: " + n);
		SyntaxTree compareOperator=t.getChild(0);

		String leafValue = compareOperator.getTokenString();

		switch (leafValue) {
			case "EMOJI_EQUAL":
				//bufferedWriter.write("==");
				stack.push("==");
				break;
			case "EMOJI_UNEQUAL":
				//bufferedWriter.write("!=");
				stack.push("!=");
				break;
			case "EMOJI_GREATER_THAN":
				//bufferedWriter.write(">");
				stack.push(">");
				break;
			case "EMOJI_GREATER_THAN_EQUALS":
				//bufferedWriter.write(">=");
				stack.push(">=");
				break;
			case "EMOJI_LESS_THAN":
				//bufferedWriter.write("<");
				stack.push("<");
				break;
			case "EMOJI_LESS_THAN_EQUALS":
				//bufferedWriter.write("<=");
				stack.push("<=");
				break;
		}
		return UNDEFINED;

	}
}
