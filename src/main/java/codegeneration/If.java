package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class If extends Semantic{
	//-------------------------------------------------------------------------
	// if -> emojiIf openpar statement closepar sequence 
	// if.f=sequence.f(statement.f)
	// if -> emojiIf openpar statement closepar sequence else
	// if.f=else.f(sequence.f(statement.f))
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS IF >> n: " + n);
		if(t.getChildNumber()==5) {
			SyntaxTree statement = t.getChild(2), sequence = t.getChild(4);
			return sequence.semanticFunction.f(sequence, statement.semanticFunction.f(statement, UNDEFINED, bufferedWriter, stack), bufferedWriter, stack);
		}else {
			SyntaxTree statement = t.getChild(2), sequence = t.getChild(4), elseTree = t.getChild(5);
			return elseTree.semanticFunction.f(elseTree, sequence.semanticFunction.f(sequence, statement.semanticFunction.f(statement, UNDEFINED, bufferedWriter, stack), bufferedWriter, stack), bufferedWriter, stack);
		}
		
	} 
}
