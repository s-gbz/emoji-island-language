package parser;

/*
class SyntaxTree

Praktikum Algorithmen und Datenstrukturen
Grundlage zum Versuch 2

SyntaxTree beschreibt die Knoten eines Syntaxbaumes
mit den Methoden zum Aufbau des Baums.
*/

import java.util.*;

import scanner.TokenList;

class SyntaxTree implements TokenList{
// Attribute 

// linker bzw. rechter Teilbaum (null bei Blättern), rightNode=null,
// wenn Operator nur einen Operanden hat
private LinkedList <SyntaxTree> childNodes; 

// Art des Knotens gemäß der Beschreibung in der Schnittstelle Arithmetic
private byte token;

// Zeichen des Knotens, falls es sich um einen Blätterknoten, der ein
// Eingabezeichen repräsentiert, handelt, d.h. einen Knoten mit dem Token  
// DIGIT oder MATH_SIGN.
private char character;

private String lexem;


// value enthält die semantsiche Funktion des Teilbaums
// mit Wurzelknoten this
public Semantic value;

	
//-------------------------------------------------------------------------
// Konstruktor des Syntaxbaumes 
//-------------------------------------------------------------------------

// Der Konstruktor bekommt den TokenTyp t des Knotens übergeben
SyntaxTree(byte t){
	this.childNodes= new LinkedList<SyntaxTree>();
	character=0;
	lexem = "";
	setToken(t);
	setSemantikFunction(t);
	}	
//-------------------------------------------------------------------------
// get und set Methoden des Syntaxbaumes
//-------------------------------------------------------------------------

// Setzt den Typ des Tokens auf den Übergabeparameter t
// Zu den möglichen TokenTypen siehe Interface TokenList.java
void setToken(byte t){
	this.token=t;
	}

// Gibt den aktuellen Konten des Syntaxbaumes zurück
byte getToken(){
	return this.token;
}

// Bei einem Knoten, der ein Eingabezeichen repräsentiert (INPUT_SIGN)
// wird mit dieser Methode das Zeichen im Knoten gespeichert
void setCharacter(char character){
	this.character=character;
}

// Gibt das zum Knoten gehörende Eingabezeichen zurück
char getCharacter(){
	return this.character;
}

//Bei einem Knoten, der ein Eingabewort repräsentiert (INPUT_SIGN)
//wird mit dieser Methode das Wort im Knoten gespeichert
void setLexem(String lexem){
	this.lexem=lexem;
}

//Gibt das zum Knoten gehörende Wort zurück
String getLexem(){
	return this.lexem;
}


// Gibt den Syntaxbaum mit entsprechenden Einrückungen auf der Konsole
// aus.
void printSyntaxTree(int t){
	for(int i=0;i<t;i++)
	  System.out.print("  ");
	System.out.print(this.getTokenString());  		
	if(this.lexem.length()!=0)
		System.out.println(":"+this.getLexem());
	else
		System.out.println("");	
	for(int i=0;i<this.childNodes.size();i++){
		this.childNodes.get(i).printSyntaxTree(t+1);
	}
}

// Gibt den zum Zahlenwert passenden String des Tokentyps zurück
String getTokenString(){
	switch(this.token){
		case  1: return "NUMBER";
		case  2: return "DIGIT";
		case  3: return "ASSIGMENT";
		case  4: return "EPSILON";
		case  5: return "START";
		case  6: return "NOT_FINALE";
		case  7: return "SEMICOLON";
		case  8: return "IDENTIFIER";
		case  9: return "OPEN_PARENTHESES";
		case 10: return "CLOSE_PARENTHESES";
		case 11: return "OPEN_CURLY_BRACKET";
		case 12: return "CLOSE_CURLY_BRACKET";		
		case 13: return "HASHTAG";
		case 14: return "DOUBLE_QUOTES";
		case 15: return "EXPRESSION";
		case 16: return "RIGHT_EXPRESSION";
		case 17: return "TERM";
		case 18: return "RIGHT_TERM";
		
		case 20: return "OPERATOR";
		case 21: return "PROGRAM";
		case 22: return "FUNCTION";
		case 23: return "UNDERSCORE";
		case 24: return "LESS_THAN";
		case 25: return "MINUS";
		case 26: return "FIRST_COLON";
		case 27: return "EMOJI";
		case 28: return "EMOJI_CHARACTER";
		case 29: return "PLUS";
		case 30: return "MULT";
		case 31: return "DIV";
		case 32: return "VARIABLE_ASSIGMENT_I";
		case 33: return "VARIABLE_ASSIGMENT";
		case 34: return "EMOJI_INT";
		case 35: return "EMOJI_START_CODE";
		case 36: return "EMOJI_END_CODE";
		case 37: return "SEQUENCE";
		case 38: return "INSTRUCTION";
		case 39: return "VARIABLEDEFINITION";
		case 40: return "VARIABLEDEFINITIONWITHASSIGMENT";
		case 41: return "VARIABLEDEFINITIONWITHOUTASSIGMENT";
		case 42: return "EMOJI_DATA_TYPE";
		case 43: return "EMOJI_WHILE";
		case 44: return "STATEMENT";
		case 45: return "EMOJI_IF";
		case 46: return "EMOJI_FOR";
		case 47: return "FOR_STATEMENT";
		case 48: return "EMOJI_ELSE";
		case 49: return "EMOJI_UNEQUAL";
		case 50: return "EMOJI_EQUAL";
		case 51: return "EMOJI_LESS_THAN";
		case 52: return "EMOJI_GREATER_THAN";
		case 53: return "EMOJI_LESS_THAN_EQUALS";
		case 54: return "EMOJI_GREATER_THAN_EQUALS";
		case 55: return "COMPARE_OPERATOR";
		case 56: return "LOGICAL_OPERATOR";
		case 57: return "LOGICAL";
		case 58: return "EMOJI_LOGICAL_AND";
		case 59: return "EMOJI_LOGICAL_OR";
		case 60: return "EMOJI_PLUS";
		case 61: return "EMOJI_MINUS";
		case 62: return "EMOJI_MULT";
		case 63: return "EMOJI_DIV";
		case 64: return "FOR_ASSIGMENT";
		case 65: return "ASSIGMENT_SIGN";
		case 66: return "WHILE";
		case 67: return "START_SINGLEQOUTE";
		case 68: return "END_SINGLEQOUTE";
		case 69: return "CHAR_SIGN";
		case 70: return "EXPRESSION_CHAR";
		case 71: return "IF";
		case 72: return "ELSE";
		case 73: return "FOR";
		
		default: return "";
	}
}



// Bestimmt und speichert die semantsiche Funktion des Kontens in
// Abhängigkeit vom Knotentyp
void setSemantikFunction(byte b){
	switch(b){
		case 1: value=new Expression();
				break;
		case 2: value=new RightExpression();
				break;
		case 3: value=new Term();
				break;
		case 4: value=new RightTerm();
				break;
		case 5: value=new Num();
				break;
		case 6: value=new Operator();
				break;/*
		case 7: value=new Digit();
				break; */
	default: value=new Semantic();
			 break;
	}
}



// Legt einen neuen Teilbaum als Kind des aktuellen Knotens an und gibt die
// Referenz auf seine Wurzel zurück
SyntaxTree insertSubtree(byte b){
	SyntaxTree node;
	node=new SyntaxTree(b); 
	this.childNodes.addLast(node);
	return node;
	}

// Gibt die Refernz der Wurzel des i-ten Kindes des aktuellen 
// Knotens zurück
SyntaxTree getChild(int i){
	if (i>this.childNodes.size())
		return null;
	else
		return this.childNodes.get(i);
	}
	
// Gibt die Referenz auf die Liste der Kinder des aktuellen Knotens zurück
LinkedList getChildNodes(){
	return this.childNodes;
	}	

// Gibt die Zahl der Kinder des aktuellen Konotens zurück
int getChildNumber(){
	return childNodes.size();
}


}