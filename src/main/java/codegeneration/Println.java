package codegeneration;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import parser.SyntaxTree;

public class Println extends Semantic{
	//-------------------------------------------------------------------------
	// println -> emojiPrintln openpar End_Singleqoute closepar ';'
	// println.f=f(n)
	//
	// println -> emojiPrintln openpar expression closepar ';'
	// println.f =expression.f(n)
	//-------------------------------------------------------------------------
	public int f(SyntaxTree t, int n, BufferedWriter bufferedWriter, Stack<String> stack) throws IOException {
		System.out.println("CLASS PRINTLN >>  n: " + t.getChild(2).getToken());
		if(t.getChildNumber()==5 && t.getChild(2).getToken() == 68) {
			SyntaxTree openpar = t.getChild(1), strSingleqoute = t.getChild(2), closepar = t.getChild(3), semicolon = t.getChild(4);
			
			String str ="\"";
			for(int i = 0; i<strSingleqoute.getLexem().length()-2;i++) {
				str = str + strSingleqoute.getLexem().charAt(i+1);
			}
			str = str + "\"";
			bufferedWriter.write("\n \t System.out.println(" + str + ");");
			return n;
		}else {
			SyntaxTree openpar = t.getChild(1), expression = t.getChild(2), closepar = t.getChild(3), semicolon = t.getChild(4);
			
			int valueToWrite = expression.semanticFunction.f(expression, UNDEFINED, bufferedWriter, stack);
			bufferedWriter.write("\n \t System.out.println(");
			for (String entry : stack) {
				bufferedWriter.write(entry);
			}
			stack.clear();
			bufferedWriter.write(");\n");

			return n;
		}
		
	}
}
