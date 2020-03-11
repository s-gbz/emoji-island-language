package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class Sequence extends Semantic{	
	// sequence -> '{' instruction '}'
	// sequence.f=instruction.f
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS SEQUENCE >> >> n: " + n);
		SyntaxTree instruction=t.getChild(1);
		bufferedWriter.write("{\n");
		int blalbla = instruction.semanticFunction.f(instruction, UNDEFINED, bufferedWriter);
		bufferedWriter.write(blalbla);
		return instruction.semanticFunction.f(instruction, UNDEFINED, bufferedWriter);
	} 
}
