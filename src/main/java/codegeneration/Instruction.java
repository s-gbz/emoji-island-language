package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class Instruction extends Semantic{
	//-------------------------------------------------------------------------
	// instruction ->   assignment instruction 
	// instruction.f=instruction.f(assignment.f)
	//
	// instruction ->   while instruction 
	// instruction.f=instruction.f(while.f)
	//
	// instruction ->   if instruction  
	// instruction.f=instruction.f(if.f)
	//
	// instruction ->   for instruction 
	// instruction.f=instruction.f(for.f)
	//
	// instruction ->   epsilon
	// instruction.f=n
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS INSTRUCTION >> n: " + n);

		if(t.getChildNumber()==2) {
			//instruction.value sagt um welcher instruction = {assigment, while, of, for} es sich handelt
			SyntaxTree assigmentORwhileORifORfor=t.getChild(0), instruction=t.getChild(1);
			return instruction.semanticFunction.f(instruction,assigmentORwhileORifORfor.semanticFunction.f(assigmentORwhileORifORfor, UNDEFINED, bufferedWriter), bufferedWriter);
		}else {
			return n;
		}
	} 
}
