package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class Sequence extends Semantic{	
	// sequence -> '{' instruction '}'
	// sequence.f=instruction.f
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS SEQUENCE >> >> n: " + n);
		SyntaxTree instruction=t.getChild(1);
		bufferedWriter.write("{\n");
		int valueToWrite = instruction.semanticFunction.f(instruction, UNDEFINED, bufferedWriter, stack);

		bufferedWriter.write("\n \t }\n");

		return valueToWrite;
	} 
}
