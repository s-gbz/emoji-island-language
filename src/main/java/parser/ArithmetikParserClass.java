package parser;


import java.io.*;
import scanner.*;


/*
	ArithmetikParserClass.java

	Diese Java Klasse implementiert einen
	einfachen Parser zum Erkennen arithmetischer
	Ausdrücke der folgenden Grammatik:
	
	program -> emojiStartCode sequence emojiEndCode
	sequence -> '{' instruction '}'
	instruction -> assigment 
	instruction -> while 
	instruction -> if 
	instruction -> for 
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
	Array of Char abgespeichert. Pointer zeigt beim Parsen auf den aktuellen
	Eingabewert.
	
	Ist der zu parsende Ausdruck syntaktisch nicht korrekt, so werden 
	über die Methode syntaxError() entsprechende Fehlermeldungen ausgegeben.
	
	Zusätzlich werden den Methoden der Klasse neben der Rekursionstiefe auch
	eine Referenz auf eine Instanz der Klasse SyntaxTree übergeben.
	
	Über die Instanzen der Klasse SyntaxTree wird beim rekursiven Abstieg
	eine konkreter Syntaxbaum des geparsten Ausdrucks aufgebaut.

*/

public class ArithmetikParserClass extends NumScanner implements TokenList{
	// Konstante für Ende der Eingabe
	public final char EOF=(char)255;
	// Zeiger auf das aktuelle Eingabezeichen
	private int pointer;
	// Zeiger auf das Ende der Eingabe
	private int maxPointer;
	// Eingabe zeichenweise abgelegt
	 private char input[];
	// Syntaxbaum
	private SyntaxTree parseTree;
	
	//-------------------------------------------------------------------------
	//------------Konstruktor der Klasse ArithmetikParserClass-----------------
	//-------------------------------------------------------------------------
	
	ArithmetikParserClass(SyntaxTree parseTree){
		this.parseTree=parseTree;
		//this.input = new char[256];
		this.pointer=0;
		this.maxPointer=0;
	}
	
	ArithmetikParserClass(SyntaxTree parseTree, char[] input) {
		this.parseTree = parseTree;
		this.input = input;
		this.pointer = 0;
		this.maxPointer = input.length - 1;
	}
	
	//-------------------------------------------------------------------------
	//-------------------Methoden der Grammatik--------------------------------
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------
	// program -> emojiStartCode sequence emojiEndCode
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean program(SyntaxTree sT){
		byte [] emojiStartCodeSet = {EMOJI_START_CODE};
		byte [] emojiEndCodeSet = {EMOJI_END_CODE};
		byte [] identSet = {IDENT};

		// Falls aktuelle Eingabe ':triangular_flag_on_post:'
		if (match(emojiStartCodeSet,sT)) {
    		if (sequence(sT.insertSubtree(SEQUENCE))){
    			if(match(emojiEndCodeSet,sT)) {
    				return true;
    			}else{//Syntaxfehler
					syntaxError("EMOJI_END_OF_CODE erwartet"); 			
 					return false;
    			}
    		}else{//Syntaxfehler
    			syntaxError("Fehler in geschachtelter Sequence"); 			
 				return false;
    		}
		}{//Syntaxfehler
			syntaxError("EMOJI_start_OF_CODE erwartet"); 			
				return false;
		}
	}//program
	
	//-------------------------------------------------------------------------
	// sequence -> '{' instruction '}'
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean sequence(SyntaxTree sT){
		byte [] openCurlySet= {OPEN_CURLY_BRACKET};
		byte [] closeCurlySet= {CLOSE_CURLY_BRACKET};
		byte [] numSet={NUM};
		byte [] identSet={IDENT};

		if (match(openCurlySet,sT)) {
    		if (instruction(sT.insertSubtree(INSTRUCTION))){
    			if(match(closeCurlySet,sT)) {
    				return true;
    			}else{//Syntaxfehler
					syntaxError("Geschlossene geschweifte Klammer erwartet"); 			
 					return false;
    			}
    		}else{
    			syntaxError("Fehler in geschachtelter Expression"); 			
 				return false;
    		}
		}
		return false;
	}//sequence
	
	
	//-------------------------------------------------------------------------
	// instruction -> 	variableDefinition instruction |
	//					assigment instruction | 
	//					while instruction | 
	//					if instruction | 
	//					for instruction | 
	//					epsilon?
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean instruction(SyntaxTree sT){
		byte [] identSet = {IDENT};
		byte [] emojiWhileSet = {EMOJI_WHILE};
		byte [] emojiIfSet = {EMOJI_IF};
		byte [] emojiForSet = {EMOJI_FOR};
		
		if(matchDoesNotMovePointer(identSet,sT)) {
			return assigment(sT.insertSubtree(ASSIGMENT)) && instruction(sT.insertSubtree(INSTRUCTION));
		}else if(matchDoesNotMovePointer(emojiWhileSet,sT)) {
			return whileBody(sT.insertSubtree(WHILE)) && instruction(sT.insertSubtree(INSTRUCTION));
		}else if(matchDoesNotMovePointer(emojiIfSet,sT)) {
			return ifBody(sT.insertSubtree(IF)) && instruction(sT.insertSubtree(INSTRUCTION));
		}else if(matchDoesNotMovePointer(emojiForSet,sT)) {
			return forBody(sT.insertSubtree(FOR)) && instruction(sT.insertSubtree(INSTRUCTION));
		}else{
  			SyntaxTree epsilonTree = sT.insertSubtree(EPSILON);
  			return true;
  		}	
	}//instruction
	
	
	
	//-------------------------------------------------------------------------
	// assigment ->  ident '<-' expression ';' ||
	// assigment ->  ident '<-' expression 'is' EmojiDatatype ';' ||
	// assigment ->  ident '<-' END_SINGLEQOUTE 'is' 'EMOJI_CHAR' ';'
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean assigment(SyntaxTree sT){
		byte [] assigmentSet = {ASSIGMENT_SIGN};
		byte [] variableAssigmentSet = {VARIABLE_ASSIGMENT};
		byte [] identSet = {IDENT};
		byte [] semicolonSet = {SEMICOLON};
		byte [] emojiCharacterSet = {EMOJI_CHARACTER};
		byte [] charSet = {END_SINGLEQOUTE}; //Example: 'a'
		
		//assigment ->  ident '<-' expression   
		if (match(identSet,sT)) {
			if (match(assigmentSet,sT)) {
				if(match(charSet,sT)) {
		 			if(match(variableAssigmentSet,sT)){
		 				if(match(emojiCharacterSet,sT)) {
							if(match(semicolonSet,sT)) {
								return true;
							}else {//Syntaxfehler
				 				syntaxError("Semicolon erwartet"); 			
								return false;
				 			}
						}else {//Syntaxfehler
			 				syntaxError("Emoji_data_type erwartet"); 			
							return false;
			 			}
		 			}else {
		 				syntaxError("'is' erwartet"); 			
						return false;
		 			}
				} else if(expression(sT.insertSubtree(EXPRESSION))) {
		 			if(match(semicolonSet,sT)) {
		 				return true;
		 			}else if(match(variableAssigmentSet,sT)){
		 				if(emojiDataType(sT.insertSubtree(EMOJI_DATA_TYPE))) {
							if(match(semicolonSet,sT)) {
								return true;
							}else {//Syntaxfehler
				 				syntaxError("Semicolon erwartet"); 			
								return false;
				 			}
						}else {//Syntaxfehler
			 				syntaxError("Emoji_data_type erwartet"); 			
							return false;
			 			}
		 			} else {//Syntaxfehler
		 				syntaxError("Semicolon oder 'is' erwartet"); 			
						return false;
		 			}
		 		} else {//Syntaxfehler
	 				syntaxError("Expression fehlerhaft"); 			
					return false;
	 			}
			}else{//Syntaxfehler
				syntaxError("Assigment erwartet"); 			
				return false;
			}
		}else{//Syntaxfehler
			syntaxError("Ident erwartet"); 			
			return false;
		}
	}//assigment
	
		
	
	//-------------------------------------------------------------------------
	// while -> emojiWhile openpar statement closepar sequence
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean whileBody(SyntaxTree sT){
		byte [] openParSet= {OPEN_PARENTHESES};
		byte [] closeParSet= {CLOSE_PARENTHESES};
		byte [] emojiWhileSet= {EMOJI_WHILE};

		if (match(emojiWhileSet,sT)) {
			if (match(openParSet,sT)) {
				if (statement(sT.insertSubtree(STATEMENT))){
					if(match(closeParSet,sT)) {
						if(sequence(sT.insertSubtree(SEQUENCE))) {
							return true;
						}else{//Syntaxfehler
							syntaxError("Fehler in Sequence"); 			
							return false;
							}	
					}else{//Syntaxfehler
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
   		}else{ //Syntaxfehler
			syntaxError("EmojiWhile erwartet"); 			
 			return false;
  		}
	}//whileBody
	
	//-------------------------------------------------------------------------
	// if -> emojiIf openpar statement closepar sequence |
	// if -> emojiIf openpar statement closepar sequence else
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean ifBody(SyntaxTree sT){
		byte [] openParSet= {OPEN_PARENTHESES};
		byte [] closeParSet= {CLOSE_PARENTHESES};
		byte [] emojiIfSet= {EMOJI_IF};
		byte [] emojiElseSet= {EMOJI_ELSE};

		if (match(emojiIfSet,sT)) {
			if (match(openParSet,sT)) {
				if (statement(sT.insertSubtree(STATEMENT))){
					if(match(closeParSet,sT)) {
						if(sequence(sT.insertSubtree(SEQUENCE))) {
							if (matchDoesNotMovePointer(emojiElseSet,sT)) {
								return elseBody(sT.insertSubtree(ELSE));
							}
							return true;
						}else{//Syntaxfehler
							syntaxError("Fehler in Sequence"); 			
							return false;
							}	
					}else{//Syntaxfehler
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
   		}else{ //Syntaxfehler
			syntaxError("EmojiIf erwartet"); 			
 			return false;
  		}
	}//ifBody


	//-------------------------------------------------------------------------
	// else -> emojiElse sequence | epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean elseBody(SyntaxTree sT){
		byte [] emojiElseSet= {EMOJI_ELSE};

		if (match(emojiElseSet,sT)) {
			if(sequence(sT.insertSubtree(SEQUENCE))) {
				return true;
			}else{//Syntaxfehler
				syntaxError("Fehler in Sequence"); 			
				return false;
			}			
   		}else{ //Syntaxfehler
			syntaxError("EmojiElse erwartet"); 			
 			return false;
  		}
	}//ifBody
	
	
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
				if (forStatement(sT.insertSubtree(FOR_STATEMENT))){
					if(match(closeParSet,sT)) {
						if(sequence(sT.insertSubtree(SEQUENCE))) {
							return true;
						}else{//Syntaxfehler
							syntaxError("Fehler in Sequence"); 			
							return false;
							}	
					}else{//Syntaxfehler
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
   		}else{ //Syntaxfehler
			syntaxError("EmojiWhile erwartet"); 			
 			return false;
  		}
	}//forBody	
	
	//-------------------------------------------------------------------------
	// statement -> expression compareOperator expression logical
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean statement(SyntaxTree sT){
		return 	expression(sT.insertSubtree(EXPRESSION)) &&
				compareOperator(sT.insertSubtree(COMPARE_OPERATOR)) && 
				expression(sT.insertSubtree(EXPRESSION)) &&
				logical(sT.insertSubtree(LOGICAL));
	}//statement
	
	//-------------------------------------------------------------------------
	// forStatement -> forAssigment ';' statement ';' forAssigment
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean forStatement(SyntaxTree sT){
		byte [] semicolonSet = {SEMICOLON};
		
		if(forAssigment(sT.insertSubtree(FOR_ASSIGMENT))) {
			if(match(semicolonSet,sT)) {
				if(statement(sT.insertSubtree(STATEMENT))) {
					if(match(semicolonSet,sT)) {
						if(forAssigment(sT.insertSubtree(FOR_ASSIGMENT))) {
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
	}//forstatement
	
	//-------------------------------------------------------------------------
	// forAssigment ->  ident '<-' expression ||
	// forAssigment ->  ident '<-' expression 'is' EmojiDatatype
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean forAssigment(SyntaxTree sT){
		byte [] assigmentSet = {ASSIGMENT_SIGN};
		byte [] variableAssigmentSet = {VARIABLE_ASSIGMENT};
		byte [] identSet = {IDENT};
		
		//assigment ->  ident '<-' expression   
		if (match(identSet,sT)) {
			if (match(assigmentSet,sT)) {
		 		if(expression(sT.insertSubtree(EXPRESSION))) {
		 			if(match(variableAssigmentSet,sT)){
		 				if(emojiDataType(sT.insertSubtree(EMOJI_DATA_TYPE))) {
		 						return true;
						}else {//Syntaxfehler
			 				syntaxError("Emoji_data_type erwartet"); 			
							return false;
			 			}
		 			}
		 			return true;
		 		}else{//Syntaxfehler
					syntaxError("Expression Fehlerhaft"); 			
					return false;
				}
			}else{//Syntaxfehler
				syntaxError("Assigment erwartet"); 			
				return false;
			}
		}else{//Syntaxfehler
			syntaxError("Ident erwartet"); 			
			return false;
		}
	}//forAssigment
	
	//-------------------------------------------------------------------------
	// compareOperator -> 	emojiUnequal | 
	//						emojiEqual | 
	//						emojiLessthan | 
	//						emojiGreaterthan | 
	//						emojiLessthanEquals | 
	//						emojiGreaterthenEquals 
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean compareOperator(SyntaxTree sT){
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
 
	}//compareOperator
	
	//-------------------------------------------------------------------------
	// logical -> logicalOperator statement | epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean logical(SyntaxTree sT){
		SyntaxTree epsilonTree;
		if (logicalOperator(sT.insertSubtree(LOGICAL_OPERATOR))) {
			if (statement(sT.insertSubtree(STATEMENT))) {
				return true;
	   		}else{ //Syntaxfehler
	   						
	 			return false;
	  		}
   		}else{ 
   			epsilonTree = sT.insertSubtree(EPSILON);			
 			return true;
  		}
	}//logical
	
	//-------------------------------------------------------------------------
	// logicalOperator -> emojiLogicalAnd | emojiLogicalOr	
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean logicalOperator(SyntaxTree sT){
		byte [] emojiLogicalAndSet = {EMOJI_LOGICAL_AND};
		byte [] emojiLogicalOrSet = {EMOJI_LOGICAL_OR};
		
		if(match(emojiLogicalAndSet,sT)) {
			return true;
		}else if(match(emojiLogicalOrSet,sT)) {
			return true;
		}else {
			return false;
		}
 
	}//logicalOperator
	
	//-------------------------------------------------------------------------
	// arithmeticOperator -> emojiPlus | emojiMinus | emojiMult | emojiDiv 
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean arithmeticOperator(SyntaxTree sT){
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
	}//arithmeticOperator
	
	
	//-------------------------------------------------------------------------
	// emojiDatatype -> EMOJI_INT | EMOJI_CHAR
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean emojiDataType(SyntaxTree sT){
		byte [] emojiIntSet = {EMOJI_INT};
		byte [] emojiCharacterSet = {EMOJI_CHARACTER};
		SyntaxTree epsilonTree;

		// Falls aktuelles Token  ist
		if (match(emojiIntSet,sT)) {
			return true;
		}
		else if(match(emojiCharacterSet,sT)){
			return true;
		}
		else{
  			
  			//epsilonTree = sT.insertSubtree(EPSILON);
  			return false;
		}				
	}//emojiDataType
	

	//-------------------------------------------------------------------------
	// expressionChar -> "'" Ident "'"
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean expressionChar(SyntaxTree sT){
		byte [] startSingleQouteSet = {START_SINGLEQOUTE};
		byte [] endSingleQouteSet = {END_SINGLEQOUTE};
		byte [] identSet={IDENT};

		// Falls aktuelles Token  ist
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
  			//epsilonTree = sT.insertSubtree(EPSILON);
  			return false;
		}				
	}//expressionChar	
	
	
	
	//-------------------------------------------------------------------------
	// expression -> term rightExpression
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean expression(SyntaxTree sT){
		return (term(sT.insertSubtree(TERM))&&
				rightExpression(sT.insertSubtree(RIGHT_EXPRESSION)));
	}//expression	
	
	
	//-------------------------------------------------------------------------
	// rightExpression -> '+' term rightExpression | 
	//                    '-' term rightExpression | Epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean rightExpression(SyntaxTree sT){
		byte [] addSet = {PLUS};
		byte [] subSet = {MINUS};

		
		SyntaxTree epsilonTree;
		// Falls aktuelles Token PLUS
		if (match(addSet,sT))
			//rightExpression -> '+' term rightExpression
    		return term(sT.insertSubtree(TERM))&& 
    		rightExpression(sT.insertSubtree(RIGHT_EXPRESSION)); 
   		// Falls aktuelles Token MINUS
  		else if (match(subSet,sT))
			//rightExpression -> '-' term rightExpression   		
     		return term(sT.insertSubtree(TERM))&& 
     		rightExpression(sT.insertSubtree(RIGHT_EXPRESSION));
     	// sonst				
  		else{
  			//rightExpression ->Epsilon
  			epsilonTree = sT.insertSubtree(EPSILON);
  			return true;
  			}				
	}//rightExpression
	
	
	//-------------------------------------------------------------------------	
	// term -> operator rightTerm
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	
	boolean term(SyntaxTree sT){

		//term -> operator rightTerm
		return (operator(sT.insertSubtree(OPERATOR))
		&&rightTerm(sT.insertSubtree(RIGHT_TERM)));		
	}//term
	
	//-------------------------------------------------------------------------	
	// rightTerm -> '*' operator rightTerm | 
	//              '/' operator rightTerm | 
	//				Epsilon
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------

	boolean rightTerm(SyntaxTree sT){
		byte [] multDivSet = {MULT,DIV};
		byte [] divSet = {DIV};

		SyntaxTree epsilonTree;

		// Falls aktuelles Token MULT oder DIV
		if (match(multDivSet,sT))
			//rightTerm -> '*' operator rightTerm bzw.
			//rightTerm -> '/' operator rightTerm   		
    		return operator(sT.insertSubtree(OPERATOR))&& 
    		rightTerm(sT.insertSubtree(RIGHT_TERM));
  		else {
  			//rightTerm ->Epsilon
  			epsilonTree = sT.insertSubtree(EPSILON);
  			return true;
  			}				
	}//rightTerm


	//-------------------------------------------------------------------------	
	// operator -> '(' expression ')' | num	| ident
	// Der Parameter sT ist die Wurzel des bis hier geparsten Syntaxbaumes
	//-------------------------------------------------------------------------
	boolean operator(SyntaxTree sT){
		byte [] openParSet= {OPEN_PARENTHESES};
		byte [] closeParSet= {CLOSE_PARENTHESES};
		byte [] numSet={NUM};
		byte [] identSet={IDENT};



		// Falls aktuelle Eingabe '('
		if (match(openParSet,sT)) {
			//operator -> '(' expression ')' 
    		if (expression(sT.insertSubtree(EXPRESSION))){
    			// Fallunterscheidung ermöglicht, den wichtigen Fehler einer
    			// fehlenden geschlossenen Klammer gesondert auszugeben
    			if(match(closeParSet,sT)) {
    				return true;
    			}else{//Syntaxfehler
					syntaxError("Geschlossene Klammer erwartet"); 			
 					return false;
    				}
    		}else{
    			syntaxError("Fehler in geschachtelter Expression"); 			
 				return false;
    		}
    	// sonst versuchen nach num abzuleiten 
		}else if (match(numSet,sT)) {
			//operator -> num   		
     		return true;
   		}else if (match(identSet,sT)) {
			//operator -> ident   		
     		return true;
     	// wenn das nicht möglich ...				
   		}else{ //Syntaxfehler
			syntaxError("Ziffer, Identifier oder Klammer auf erwartet"); 			
 			return false;
  		}
	}//operator
	
	// -------------------------------------------------------------------------
	// -------------------Methoden der Grammatik -------------------------------
	// -------------------------------------------------------------------------

	
	
	//-------------------------------------------------------------------------
	//-------------------Hilfsmethoden-----------------------------------------
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------		
	// Methode, die testet, ob das aktuele Token unter den Token
	// ist, die als Parameter (matchSet) übergeben wurden.
	// Ist das der Fall, so gibt match() true zurück und setzt den Eingabe-
	// zeiger auf das nächste Zeichen, sonst wird false zurückgegeben.
	//-------------------------------------------------------------------------
	boolean match(byte [] matchSet, SyntaxTree sT){
		SyntaxTree node;
		for (int i=0;i<matchSet.length;i++) {
			if (tokenStream.get(pointer).token==matchSet[i]){
				// gefundenes Token in den Syntaxbaum eintragen
				sT.insertSubtree(tokenStream.get(pointer).token);
				pointer++;	//Eingabepointer auf das nächste Zeichen setzen 
				System.out.println("Lexem: " + tokenStream.get(pointer).lexem);
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
	boolean matchDoesNotMovePointer(byte [] matchSet, SyntaxTree sT){
		SyntaxTree node;
		for (int i=0;i<matchSet.length;i++) {
			if (tokenStream.get(pointer).token==matchSet[i]){
				// gefundenes Token in den Syntaxbaum eintragen
				//sT.insertSubtree(tokenStream.get(pointer).token);
				//pointer++;	//Eingabepointer auf das nächste Zeichen setzen 
				System.out.println("Lexem: " + tokenStream.get(pointer).lexem);
				return true;
			}
		}
		return false;
	}//match
	
	//-------------------------------------------------------------------------
	//Methode, die testet, ob das auf das aktuelle Token folgende Token
	//unter den Token ist, die als Parameter (aheadSet) übergeben wurden.
	//Der Eingabepointer wird nicht verändert!
	//-------------------------------------------------------------------------
	boolean lookAhead(byte [] aheadSet){
		for (int i=0;i<aheadSet.length;i++) {
			System.out.println("Lexem: " + tokenStream.get(pointer).lexem);
			if (tokenStream.get(pointer+1).token==aheadSet[i])
				return true;
		}
		return false;
	}//lookAhead
	



	//-------------------------------------------------------------------------	
	// Methode, die testet, ob das Ende der Eingabe erreicht ist
	// (pointer == maxPointer)
	//-------------------------------------------------------------------------
	boolean inputEmpty(){
		if (pointer==(tokenStream.size()-1)){
			ausgabe("Eingabe leer!",0);
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
	void ausgabe(String s, int t){
		for(int i=0;i<t;i++)
		  System.out.print("  ");
		System.out.println(s);
	}//ausgabe

	//-------------------------------------------------------------------------
	// Methode zum Ausgeben eines Syntaxfehlers mit Angabe des vermuteten
	// Zeichens, bei dem der Fehler gefunden wurde 
	//-------------------------------------------------------------------------
	void syntaxError(String s){
		char z;
		if (tokenStream.get(pointer).token==EOF)
			System.out.println("Syntax Fehler in Zeile "+tokenStream.get(pointer).line+": "+"EOF");
		else
			System.out.println("Syntax Fehler in Zeile "+tokenStream.get(pointer).line+": "+tokenStream.get(pointer).token);
			System.out.println("Lexem: " + tokenStream.get(pointer).lexem);
		System.out.println(s);	
	}//syntaxError
	

}//ArithmetikParserClass