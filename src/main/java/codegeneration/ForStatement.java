package codegeneration;

import parser.SyntaxTree;

import java.io.BufferedWriter;
import java.io.IOException;

public class ForStatement extends Semantic{
	//-------------------------------------------------------------------------
	// forStatement -> forassignment ';' statement ';' forassignment
	// forStatement.f=forassignment.f(statement.f(forassignment.f()))
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter) throws IOException {
		System.out.println("CLASS STATEMENT >> n: " + n);

		SyntaxTree forAssignmentFirst = t.getChild(0), statement = t.getChild(2), forAssignmentSecound = t.getChild(4);

		bufferedWriter.write(forAssignmentFirst.getLexem());
		return forAssignmentSecound.semanticFunction.f(forAssignmentSecound, statement.semanticFunction.f(statement, forAssignmentFirst.semanticFunction.f(forAssignmentFirst, UNDEFINED, bufferedWriter), bufferedWriter), bufferedWriter);
	} 
}
