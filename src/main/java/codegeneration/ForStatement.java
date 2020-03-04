package codegeneration;

import parser.SyntaxTree;

public class ForStatement extends Semantic{
	//-------------------------------------------------------------------------
	// forStatement -> forassignment ';' statement ';' forassignment
	// forStatement.f=forassignment.f(statement.f(forassignment.f()))
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n){
		System.out.println("CLASS STATEMENT >> n: " + n);
		SyntaxTree forAssignmentFirst = t.getChild(0), statement = t.getChild(2), forAssignmentSecound = t.getChild(4);
		return forAssignmentSecound.value.f(forAssignmentSecound, statement.value.f(statement, forAssignmentFirst.value.f(forAssignmentFirst, UNDEFINED)));
	} 
}
