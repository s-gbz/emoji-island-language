package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

public class ForStatement extends Semantic{
	//-------------------------------------------------------------------------
	// forStatement -> forassignment ';' statement ';' forassignment
	// forStatement.f=forassignment.f(statement.f(forassignment.f()))
	//-------------------------------------------------------------------------
	public int  f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS STATEMENT >> n: " + n);

		SyntaxTree forAssignmentFirst = t.getChild(0), statement = t.getChild(2), forAssignmentSecound = t.getChild(4);
		bufferedWriter.write("int " + forAssignmentFirst.getChild(0).getLexem() + " = " + forAssignmentFirst.getChild(2).getChild(0).getChild(0).getChild(0).getLexem() + "; ");
		int valueToWrite = forAssignmentSecound.semanticFunction.f(forAssignmentSecound, statement.semanticFunction.f(statement, forAssignmentFirst.semanticFunction.f(forAssignmentFirst, UNDEFINED, bufferedWriter, stack), bufferedWriter, stack), bufferedWriter, stack);

		//bufferedWriter.write(forAssignmentSecound.getChild(0).getLexem() + " = " + forAssignmentSecound.getChild(2).getChild(0).getChild(0).getChild(0).getLexem());

		bufferedWriter.write(")");
		return valueToWrite;
	}
}
