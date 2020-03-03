package parser;


import java.io.*;
import scanner.*;

/*
	ArithmetikParserClass.java
	
	Diese Java Klasse implementiert einen Parser zum Erkennen von
	Ausdrücken gemäß folgenden Grammatikregeln:
	
	program -> emojiStartCode sequence emojiEndCode
	sequence -> '{' instruction '}'
	instruction -> assigment instruction
	instruction -> while instruction
	instruction -> if instruction
	instruction -> for instruction
	instruction -> epsilon
	assigment -> ident '<-' expression ';'
	assigment -> ident '<-' expression 'is' EmojiDatatype ';' 
	assigment -> ident '<-' END_SINGLEQOUTE 'is' 'EMOJI_CHAR' ';'
	forAssigment -> ident '<-' expression
	forAssigment -> ident '<-' expression 'is' EmojiDatatype
	while -> emojiWhile openpar statement closepar sequence
	if -> emojiIf openpar statement closepar sequence else
	else -> emojiElse sequence 
	else -> epsilon
	for -> emojiFor openpar forStatement closepar sequence
	statement -> expression compareOperator expression logical
	forStatement -> forAssigment ';' statement ';' forAssigment
	logical -> logicalOperator statement 
	logical -> epsilon
	compareOperator -> emojiUnequal 
	compareOperator -> emojiEqual 
	compareOperator -> emojiLessthan 
	compareOperator -> emojiGreaterthan 
	compareOperator -> emojiLessthanEquals 
	compareOperator -> emojiGreaterthenEquals 
	logicalOperator -> emojiLogicalAnd 
	logicalOperator -> emojiLogicalOr
	arithmeticOperator -> emojiPlus 
	arithmeticOperator -> emojiMinus 
	arithmeticOperator -> emojiMult 
	arithmeticOperator -> emojiDiv 
	emojiDatatype -> emojiInt  
	emojiDatatype -> emojiChar
		
	expression -> term rightExpression
	rightExpression -> plus term rightExpression 
	rightExpression -> minus term rightExpression 
	rightExpression -> Epsilon
	term -> operator rightTerm
	rightTerm -> mult operator rightTerm 
	rightTerm -> div operator rightTerm 
	rightTerm -> Epsilon
	operator -> openPar expression closePar | num | ident
	
	
	Epsilon steht hier für das "leere Wort"
	
	Der Parser verarbeitet den Token Strom, der durch den Scanner bei der
	lexikalischen Analyse der Eingabe erzeugt wurde.
	
	Der Parser ist nach dem Prinzip des rekursiven Abstiegs programmiert,
	d.h. jedes nicht terminale Symbol der Grammatik wird durch eine 
	Methode in Java repräsentiert, die die jeweils anderen nicht terminalen
	Symbole auf der rechten Seite der Grammatik Regeln ggf. auch rekursiv
	aufruft.
	
	Der zu parsende Ausdruck wird aus einer Datei gelesen und in einem
	Array of Char abgespeichert. InputPointer zeigt beim Parsen auf den aktuellen
	Eingabewert.
	
	Ist der zu parsende Ausdruck syntaktisch nicht korrekt, so werden 
	über die Methode syntaxError() entsprechende Fehlermeldungen ausgegeben.
	
	Zusätzlich werden den Methoden der Klasse neben der Rekursionstiefe auch
	eine Referenz auf eine Instanz der Klasse SyntaxTree übergeben.
	
	Über die Instanzen der Klasse SyntaxTree wird beim rekursiven Abstieg
	eine konkreter Syntaxbaum des geparsten Ausdrucks aufgebaut.

*/

public class ArithmetikParserClass extends NumScanner implements TokenList{

	public final char EOF=(char)255;
	private int inputPointer;
	private int maxInputPointer;
	private char input[];
	private SyntaxTree parseTree;
	
	//-------------------------------------------------------------------------
	//------------Konstruktor der Klasse ArithmetikParserClass-----------------
	//-------------------------------------------------------------------------
	
	ArithmetikParserClass(SyntaxTree parseTree){
		this.parseTree=parseTree;
		//this.input = new char[256];
		this.inputPointer=0;
		this.maxInputPointer=0;
	}
	
	ArithmetikParserClass(SyntaxTree parseTree, char[] input) {
		this.parseTree = parseTree;
		this.input = input;
		this.inputPointer = 0;
		this.maxInputPointer = input.length - 1;
	}
	
	
	//-------------------------------------------------------------------------
	//-------------------Methoden der Grammatik--------------------------------
	//-------------------------------------------------------------------------
	// Folgende Methoden überprüfen, ob der eingegeben Code vom Syntax her 
	// korrekt ist. Dazu überprüft jede Methode einzelnt die über ihr 
	// beschriebene Regel der Grammatik.
	// Ist der jeweilige Grammatikregel syntaktisch korrekt, so gibt die 
	// Methode True zurück, ansonsten False. Erst wenn die Methode 
	// "checkGrammarRuleProgramm" true zurück gibt, ist der gesamte Code vom
	// Syntax her korrekt.
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------
	// program -> emojiStartCode sequence emojiEndCode
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleProgram(SyntaxTree sT){
		byte [] emojiStartCodeSet = {EMOJI_START_CODE};
		byte [] emojiEndCodeSet = {EMOJI_END_CODE};

		
		if (match(emojiStartCodeSet,sT)) {
    		if (checkGrammarRuleSequence(sT.insertSubtree(SEQUENCE))){
    			if(match(emojiEndCodeSet,sT)) {
    				return true;
    			}else{
					syntaxError("EMOJI_END_OF_CODE erwartet"); 			
 					return false;
    			}
    		}else{
    			syntaxError("Fehler in geschachtelter Sequence"); 			
 				return false;
    		}
		}else{
			syntaxError("EMOJI_start_OF_CODE erwartet"); 			
				return false;
		}
	}
	
	//-------------------------------------------------------------------------
	// sequence -> '{' instruction '}'
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleSequence(SyntaxTree sT){
		byte [] openCurlySet= {OPEN_CURLY_BRACKET};
		byte [] closeCurlySet= {CLOSE_CURLY_BRACKET};
		byte [] numSet={NUM};
		byte [] identSet={IDENT};

		if (match(openCurlySet,sT)) {
    		if (checkGrammarRuleInstruction(sT.insertSubtree(INSTRUCTION))){
    			if(match(closeCurlySet,sT)) {
    				return true;
    			}else{
					syntaxError("Geschlossene geschweifte Klammer erwartet"); 			
 					return false;
    			}
    		}else{
    			syntaxError("Fehler in geschachtelter Expression"); 			
 				return false;
    		}
		}
		return false;
	}
	
	
	//-------------------------------------------------------------------------
	// instruction ->   assigment instruction | 
	//					while instruction | 
	//					if instruction | 
	//					for instruction | 
	//					epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleInstruction(SyntaxTree sT){
		byte [] identSet = {IDENT};
		byte [] emojiWhileSet = {EMOJI_WHILE};
		byte [] emojiIfSet = {EMOJI_IF};
		byte [] emojiForSet = {EMOJI_FOR};
		
		if(matchDoesNotMoveinputPointer(identSet,sT)) {
			return checkGrammarRuleAssigment(sT.insertSubtree(ASSIGMENT)) && checkGrammarRuleInstruction(sT.insertSubtree(INSTRUCTION));
		}else if(matchDoesNotMoveinputPointer(emojiWhileSet,sT)) {
			return checkGrammarRuleWhile(sT.insertSubtree(WHILE)) && checkGrammarRuleInstruction(sT.insertSubtree(INSTRUCTION));
		}else if(matchDoesNotMoveinputPointer(emojiIfSet,sT)) {
			return checkGrammarRuleIf(sT.insertSubtree(IF)) && checkGrammarRuleInstruction(sT.insertSubtree(INSTRUCTION));
		}else if(matchDoesNotMoveinputPointer(emojiForSet,sT)) {
			return forBody(sT.insertSubtree(FOR)) && checkGrammarRuleInstruction(sT.insertSubtree(INSTRUCTION));
		}else{
  			SyntaxTree epsilonTree = sT.insertSubtree(EPSILON);
  			return true;
  		}	
	}
	
	
	
	//-------------------------------------------------------------------------
	// assigment ->  ident '<-' expression ';' ||
	// assigment ->  ident '<-' expression 'is' EmojiDatatype ';' ||
	// assigment ->  ident '<-' END_SINGLEQOUTE 'is' 'EMOJI_CHAR' ';'
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleAssigment(SyntaxTree sT){
		byte [] assigmentSet = {ASSIGMENT_SIGN};
		byte [] variableAssigmentSet = {VARIABLE_ASSIGMENT};
		byte [] identSet = {IDENT};
		byte [] semicolonSet = {SEMICOLON};
		byte [] emojiCharacterSet = {EMOJI_CHARACTER};
		byte [] charSet = {END_SINGLEQOUTE}; //Example: 'a'
		
		if (match(identSet,sT)) {
			if (match(assigmentSet,sT)) {
				if(match(charSet,sT)) {
		 			if(match(variableAssigmentSet,sT)){
		 				if(match(emojiCharacterSet,sT)) {
							if(match(semicolonSet,sT)) {
								return true;
							}else {
				 				syntaxError("Semicolon erwartet"); 			
								return false;
				 			}
						}else {
			 				syntaxError("Emoji_data_type erwartet"); 			
							return false;
			 			}
		 			}else {
		 				syntaxError("'is' erwartet"); 			
						return false;
		 			}
				} else if(checkGrammarRuleExpression(sT.insertSubtree(EXPRESSION))) {
		 			if(match(semicolonSet,sT)) {
		 				return true;
		 			}else if(match(variableAssigmentSet,sT)){
		 				if(checkGrammarRuleEmojiDatatype(sT.insertSubtree(EMOJI_DATA_TYPE))) {
							if(match(semicolonSet,sT)) {
								return true;
							}else {
				 				syntaxError("Semicolon erwartet"); 			
								return false;
				 			}
						}else {
			 				syntaxError("Emoji_data_type erwartet"); 			
							return false;
			 			}
		 			} else {
		 				syntaxError("Semicolon oder 'is' erwartet"); 			
						return false;
		 			}
		 		} else {
	 				syntaxError("Expression fehlerhaft"); 			
					return false;
	 			}
			}else{
				syntaxError("Assigment erwartet"); 			
				return false;
			}
		}else{
			syntaxError("Ident erwartet"); 			
			return false;
		}
	}
	
		
	
	//-------------------------------------------------------------------------
	// while -> emojiWhile openpar statement closepar sequence
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleWhile(SyntaxTree sT){
		byte [] openParSet= {OPEN_PARENTHESES};
		byte [] closeParSet= {CLOSE_PARENTHESES};
		byte [] emojiWhileSet= {EMOJI_WHILE};

		if (match(emojiWhileSet,sT)) {
			if (match(openParSet,sT)) {
				if (checkGrammarRuleStatement(sT.insertSubtree(STATEMENT))){
					if(match(closeParSet,sT)) {
						if(checkGrammarRuleSequence(sT.insertSubtree(SEQUENCE))) {
							return true;
						}else{
							syntaxError("Fehler in Sequence"); 			
							return false;
							}	
					}else{
						syntaxError("Geschlossene Klammer erwartet"); 			
						return false;
						}
				}else{
					syntaxError("Fehler in statement"); 			
					return false;
				}
			}else{
				syntaxError("Offene Klammer erwartet"); 			
				return false;
			}
   		}else{ 
			syntaxError("EmojiWhile erwartet"); 			
 			return false;
  		}
	}
	
	//-------------------------------------------------------------------------
	// if -> emojiIf openpar statement closepar sequence |
	// if -> emojiIf openpar statement closepar sequence else
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleIf(SyntaxTree sT){
		byte [] openParSet= {OPEN_PARENTHESES};
		byte [] closeParSet= {CLOSE_PARENTHESES};
		byte [] emojiIfSet= {EMOJI_IF};
		byte [] emojiElseSet= {EMOJI_ELSE};

		if (match(emojiIfSet,sT)) {
			if (match(openParSet,sT)) {
				if (checkGrammarRuleStatement(sT.insertSubtree(STATEMENT))){
					if(match(closeParSet,sT)) {
						if(checkGrammarRuleSequence(sT.insertSubtree(SEQUENCE))) {
							if (matchDoesNotMoveinputPointer(emojiElseSet,sT)) {
								return checkGrammarRuleElse(sT.insertSubtree(ELSE));
							}
							return true;
						}else{
							syntaxError("Fehler in Sequence"); 			
							return false;
							}	
					}else{
						syntaxError("Geschlossene Klammer erwartet"); 			
						return false;
						}
				}else{
					syntaxError("Fehler in statement"); 			
					return false;
				}
			}else{
				syntaxError("Offene Klammer erwartet"); 			
				return false;
			}
   		}else{ 
			syntaxError("EmojiIf erwartet"); 			
 			return false;
  		}
	}


	//-------------------------------------------------------------------------
	// else -> emojiElse sequence | epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleElse(SyntaxTree sT){
		byte [] emojiElseSet= {EMOJI_ELSE};

		if (match(emojiElseSet,sT)) {
			if(checkGrammarRuleSequence(sT.insertSubtree(SEQUENCE))) {
				return true;
			}else{
				syntaxError("Fehler in Sequence"); 			
				return false;
			}			
   		}else{ 
			syntaxError("EmojiElse erwartet"); 			
 			return false;
  		}
	}
	
	
	//-------------------------------------------------------------------------
	// for -> emojiFor openpar forStatement closepar sequence
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean forBody(SyntaxTree sT){
		byte [] openParSet= {OPEN_PARENTHESES};
		byte [] closeParSet= {CLOSE_PARENTHESES};
		byte [] emojiForSet= {EMOJI_FOR};

		if (match(emojiForSet,sT)) {
			if (match(openParSet,sT)) {
				if (checkGrammarRuleForStatement(sT.insertSubtree(FOR_STATEMENT))){
					if(match(closeParSet,sT)) {
						if(checkGrammarRuleSequence(sT.insertSubtree(SEQUENCE))) {
							return true;
						}else{
							syntaxError("Fehler in Sequence"); 			
							return false;
							}	
					}else{
						syntaxError("Geschlossene Klammer erwartet"); 			
						return false;
						}
				}else{
					syntaxError("Fehler in For statement"); 			
					return false;
				}
			}else{
				syntaxError("Offene Klammer erwartet"); 			
				return false;
			}
   		}else{ 
			syntaxError("EmojiWhile erwartet"); 			
 			return false;
  		}
	}	
	
	//-------------------------------------------------------------------------
	// statement -> expression compareOperator expression logical
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleStatement(SyntaxTree sT){
		return 	checkGrammarRuleExpression(sT.insertSubtree(EXPRESSION)) &&
				checkGrammarRuleCompareOperator(sT.insertSubtree(COMPARE_OPERATOR)) && 
				checkGrammarRuleExpression(sT.insertSubtree(EXPRESSION)) &&
				checkGrammarRuleLogical(sT.insertSubtree(LOGICAL));
	}//statement
	
	//-------------------------------------------------------------------------
	// forStatement -> forAssigment ';' statement ';' forAssigment
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleForStatement(SyntaxTree sT){
		byte [] semicolonSet = {SEMICOLON};
		
		if(checkGrammarRuleForAssigment(sT.insertSubtree(FOR_ASSIGMENT))) {
			if(match(semicolonSet,sT)) {
				if(checkGrammarRuleStatement(sT.insertSubtree(STATEMENT))) {
					if(match(semicolonSet,sT)) {
						if(checkGrammarRuleForAssigment(sT.insertSubtree(FOR_ASSIGMENT))) {
							return true;
						}else {
							syntaxError("Fehler in ForAssigment"); 			
							return false;
						}
					}else {
						syntaxError("Semicolon erwartet"); 			
						return false;
					}
				}else {
					syntaxError("Fehler in statement"); 			
					return false;
				}
			}else  {
				syntaxError("Semicolon erwartet"); 			
				return false;
			}	
		}else {
			syntaxError("Fehler in ForAssigment"); 			
			return false;
		}
	}
	
	//-------------------------------------------------------------------------
	// forAssigment ->  ident '<-' expression ||
	// forAssigment ->  ident '<-' expression 'is' EmojiDatatype
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleForAssigment(SyntaxTree sT){
		byte [] assigmentSet = {ASSIGMENT_SIGN};
		byte [] variableAssigmentSet = {VARIABLE_ASSIGMENT};
		byte [] identSet = {IDENT};
		
   
		if (match(identSet,sT)) {
			if (match(assigmentSet,sT)) {
		 		if(checkGrammarRuleExpression(sT.insertSubtree(EXPRESSION))) {
		 			if(match(variableAssigmentSet,sT)){
		 				if(checkGrammarRuleEmojiDatatype(sT.insertSubtree(EMOJI_DATA_TYPE))) {
		 						return true;
						}else {
			 				syntaxError("Emoji_data_type erwartet"); 			
							return false;
			 			}
		 			}
		 			return true;
		 		}else{
					syntaxError("Expression Fehlerhaft"); 			
					return false;
				}
			}else{
				syntaxError("Assigment erwartet"); 			
				return false;
			}
		}else{
			syntaxError("Ident erwartet"); 			
			return false;
		}
	}
	
	//-------------------------------------------------------------------------
	// compareOperator -> 	emojiUnequal | 
	//						emojiEqual | 
	//						emojiLessthan | 
	//						emojiGreaterthan | 
	//						emojiLessthanEquals | 
	//						emojiGreaterthenEquals 
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleCompareOperator(SyntaxTree sT){
		byte [] emojiUnequalSet = {EMOJI_UNEQUAL};
		byte [] emojiequalSet = {EMOJI_EQUAL};
		byte [] emojiLessThanSet = {EMOJI_LESS_THAN};
		byte [] emojiGreaterThanSet = {EMOJI_GREATER_THAN};
		byte [] emojiLessThanEqualsSet = {EMOJI_LESS_THAN_EQUALS};
		byte [] emojiGreaterThanEqualsSet = {EMOJI_GREATER_THAN_EQUALS};
		
		if(match(emojiUnequalSet,sT)) {
			return true;
		}else if(match(emojiequalSet,sT)) {
			return true;
		}else if(match(emojiLessThanSet,sT)) {
			return true;
		}else if(match(emojiGreaterThanSet,sT)) {
			return true;
		}else if(match(emojiLessThanEqualsSet,sT)) {
			return true;
		}else if(match(emojiGreaterThanEqualsSet,sT)) {
			return true;
		}else {
			syntaxError("compareOperator erwartet"); 	
			return false;
		}
 
	}
	
	//-------------------------------------------------------------------------
	// logical -> logicalOperator statement | epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleLogical(SyntaxTree sT){
		SyntaxTree epsilonTree;
		if (checkGrammarRuleLogicalOperator(sT.insertSubtree(LOGICAL_OPERATOR))) {
			if (checkGrammarRuleStatement(sT.insertSubtree(STATEMENT))) {
				return true;
	   		}else{ 
	   						
	 			return false;
	  		}
   		}else{ 
   			epsilonTree = sT.insertSubtree(EPSILON);			
 			return true;
  		}
	}
	
	//-------------------------------------------------------------------------
	// logicalOperator -> emojiLogicalAnd | emojiLogicalOr	
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleLogicalOperator(SyntaxTree sT){
		byte [] emojiLogicalAndSet = {EMOJI_LOGICAL_AND};
		byte [] emojiLogicalOrSet = {EMOJI_LOGICAL_OR};
		
		if(match(emojiLogicalAndSet,sT)) {
			return true;
		}else if(match(emojiLogicalOrSet,sT)) {
			return true;
		}else {
			return false;
		}
 
	}
	
	//-------------------------------------------------------------------------
	// arithmeticOperator -> emojiPlus | emojiMinus | emojiMult | emojiDiv 
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleArithmeticOperator(SyntaxTree sT){
		byte [] emojiPlusSet = {EMOJI_PLUS};
		byte [] emojiMinusSet = {EMOJI_MINUS};
		byte [] emojiMultSet = {EMOJI_MULT};
		byte [] emojiDivSet = {EMOJI_DIV};	
		
		if(match(emojiPlusSet,sT)) {
			return true;
		}else if(match(emojiMinusSet,sT)) {
			return true;
		}else if(match(emojiMultSet,sT)) {
			return true;
		}else if(match(emojiDivSet,sT)) {
			return true;
		}else {
			syntaxError("arithmeticOperator erwartet"); 	
			return false;
		}
	}
	
	
	//-------------------------------------------------------------------------
	// emojiDatatype -> EMOJI_INT | EMOJI_CHAR
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleEmojiDatatype(SyntaxTree sT){
		byte [] emojiIntSet = {EMOJI_INT};
		byte [] emojiCharacterSet = {EMOJI_CHARACTER};
		SyntaxTree epsilonTree;

		if (match(emojiIntSet,sT)) {
			return true;
		}
		else if(match(emojiCharacterSet,sT)){
			return true;
		}
		else{

  			return false;
		}				
	}
	

	//-------------------------------------------------------------------------
	// expressionChar -> "'" Ident "'"
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleExpressionChar(SyntaxTree sT){
		byte [] startSingleQouteSet = {START_SINGLEQOUTE};
		byte [] endSingleQouteSet = {END_SINGLEQOUTE};
		byte [] identSet={IDENT};


		if (match(startSingleQouteSet,sT)) {
			if(match(identSet,sT)){
				if(match(endSingleQouteSet,sT)){
					return true;
				}else {
					syntaxError("Einfacher Anführungsstrich erwartet"); 
					return false;
				}
			}else {
				syntaxError("ident erwartet"); 
				return false;
			}
		}else {	
			syntaxError("Einfacher Anführungsstrich erwartet"); 
  			return false;
		}				
	}
	
	
	
	//-------------------------------------------------------------------------
	// expression -> term rightExpression
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleExpression(SyntaxTree sT){
		return (checkGrammarRuleTerm(sT.insertSubtree(TERM))&&
				checkGrammarRuleRightExpression(sT.insertSubtree(RIGHT_EXPRESSION)));
	}
	
	
	//-------------------------------------------------------------------------
	// rightExpression -> '+' term rightExpression | 
	//                    '-' term rightExpression | Epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleRightExpression(SyntaxTree sT){
		byte [] addSet = {PLUS};
		byte [] subSet = {MINUS};
	
		SyntaxTree epsilonTree;

		if (match(addSet,sT))
    		return checkGrammarRuleTerm(sT.insertSubtree(TERM))&& 
    		checkGrammarRuleRightExpression(sT.insertSubtree(RIGHT_EXPRESSION)); 
  		else if (match(subSet,sT))		
     		return checkGrammarRuleTerm(sT.insertSubtree(TERM))&& 
     		checkGrammarRuleRightExpression(sT.insertSubtree(RIGHT_EXPRESSION));
  		else{
  			epsilonTree = sT.insertSubtree(EPSILON);
  			return true;
  			}				
	}
	
	
	//-------------------------------------------------------------------------	
	// term -> operator rightTerm
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------	
	boolean checkGrammarRuleTerm(SyntaxTree sT){
		return (checkGrammarRuleOperator(sT.insertSubtree(OPERATOR))&&checkGrammarRuleRightTerm(sT.insertSubtree(RIGHT_TERM)));		
	}
	
	//-------------------------------------------------------------------------	
	// rightTerm -> '*' operator rightTerm | 
	//              '/' operator rightTerm | 
	//				Epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleRightTerm(SyntaxTree sT){
		byte [] multDivSet = {MULT,DIV};
		byte [] divSet = {DIV};

		SyntaxTree epsilonTree;

		if (match(multDivSet,sT))	
    		return checkGrammarRuleOperator(sT.insertSubtree(OPERATOR))&& checkGrammarRuleRightTerm(sT.insertSubtree(RIGHT_TERM));
  		else {
  			epsilonTree = sT.insertSubtree(EPSILON);
  			return true;
  			}				
	}


	//-------------------------------------------------------------------------	
	// operator -> '(' expression ')' | num	| ident
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean checkGrammarRuleOperator(SyntaxTree sT){
		byte [] openParSet= {OPEN_PARENTHESES};
		byte [] closeParSet= {CLOSE_PARENTHESES};
		byte [] numSet={NUM};
		byte [] identSet={IDENT};



		if (match(openParSet,sT)) {
    		if (checkGrammarRuleExpression(sT.insertSubtree(EXPRESSION))){
    			if(match(closeParSet,sT)) {
    				return true;
    			}else{
					syntaxError("Geschlossene Klammer erwartet"); 			
 					return false;
    				}
    		}else{
    			syntaxError("Fehler in geschachtelter Expression"); 			
 				return false;
    		}
		}else if (match(numSet,sT)) {	
     		return true;
   		}else if (match(identSet,sT)) {
     		return true;			
   		}else{ 
			syntaxError("Ziffer, Identifier oder Klammer auf erwartet"); 			
 			return false;
  		}
	}

	
	
	//-------------------------------------------------------------------------
	//------------------ Hilfsmethoden ----------------------------------------
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------		
	// Methode, die testet, ob das aktuele Token unter den Token
	// ist, die als Parameter (matchSet) übergeben wurden.
	// Ist das der Fall, so gibt match() true zurück und setzt den Eingabe-
	// zeiger auf das nächste Zeichen, sonst wird false zurückgegeben.
	//-------------------------------------------------------------------------
	boolean match(byte [] matchSet, SyntaxTree sT){
		SyntaxTree node;
		String inputText = "";
		for (int i=0;i<matchSet.length;i++) {
			if (tokenStream.get(inputPointer).token==matchSet[i]){
				// gefundenes Token in den Syntaxbaum eintragen
				node = sT.insertSubtree(tokenStream.get(inputPointer).token);
				node.setLexem(tokenStream.get(inputPointer).lexem);
				node.setCharacter(tokenStream.get(inputPointer).lexem.charAt(0));
				inputPointer++;	//EingabeinputPointer auf das nächste Zeichen setzen 
				System.out.println("Lexem: " + tokenStream.get(inputPointer).lexem);
				return true;
			}
		}
		return false;
	}//match
	
	//-------------------------------------------------------------------------		
	// Methode, die testet, ob das aktuele Token unter den Token
	// ist, die als Parameter (matchSet) übergeben wurden.
	// Ist das der Fall, so gibt match() true zurück, sonst wird false zurückgegeben.
	//-------------------------------------------------------------------------
	boolean matchDoesNotMoveinputPointer(byte [] matchSet, SyntaxTree sT){
		SyntaxTree node;
		for (int i=0;i<matchSet.length;i++) {
			if (tokenStream.get(inputPointer).token==matchSet[i]){
				// gefundenes Token in den Syntaxbaum eintragen
				//sT.insertSubtree(tokenStream.get(inputPointer).token);
				//inputPointer++;	//EingabeinputPointer auf das nächste Zeichen setzen 
				System.out.println("Lexem: " + tokenStream.get(inputPointer).lexem);
				return true;
			}
		}
		return false;
	}//match
	
	//-------------------------------------------------------------------------
	//Methode, die testet, ob das auf das aktuelle Token folgende Token
	//unter den Token ist, die als Parameter (aheadSet) übergeben wurden.
	//Der EingabeinputPointer wird nicht verändert!
	//-------------------------------------------------------------------------
	boolean lookAhead(byte [] aheadSet){
		for (int i=0;i<aheadSet.length;i++) {
			System.out.println("Lexem: " + tokenStream.get(inputPointer).lexem);
			if (tokenStream.get(inputPointer+1).token==aheadSet[i])
				return true;
		}
		return false;
	}//lookAhead
	



	//-------------------------------------------------------------------------	
	// Methode, die testet, ob das Ende der Eingabe erreicht ist
	// (inputPointer == maxInputPointer)
	//-------------------------------------------------------------------------
	boolean inputEmpty(){
		if (inputPointer==(tokenStream.size()-1)){
			output("Eingabe leer!",0);
			return true;
		}else{
			syntaxError("Eingabe bei Ende des Parserdurchlaufs nicht leer");
			return false;
		}
		
	}//inputEmpty


	//-------------------------------------------------------------------------	
	// Methode zum korrekt eingerückten Ausgeben des Syntaxbaumes auf der 
	// Konsole 
	//-------------------------------------------------------------------------
	void output(String s, int t){
		for(int i=0;i<t;i++)
		  System.out.print("  ");
		System.out.println(s);
	}//output

	//-------------------------------------------------------------------------
	// Methode zum Ausgeben eines Syntaxfehlers mit Angabe des vermuteten
	// Zeichens, bei dem der Fehler gefunden wurde 
	//-------------------------------------------------------------------------
	void syntaxError(String s){
		char z;
		if (tokenStream.get(inputPointer).token==EOF)
			System.out.println("Syntax Fehler in Zeile "+tokenStream.get(inputPointer).line+": "+"EOF");
		else
			System.out.println("Syntax Fehler in Zeile "+tokenStream.get(inputPointer).line+": "+tokenStream.get(inputPointer).token);
			System.out.println("Lexem: " + tokenStream.get(inputPointer).lexem);
		System.out.println(s);	
	}//syntaxError
	

}//ArithmetikParserClass