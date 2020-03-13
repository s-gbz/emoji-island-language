package scanner;

/*
	interface TokenList

	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2

	Die Schnittstelle TokenList stellt die Konstanten f�r die
	Knotentypen eines Syntaxbaumes (Token) f�r Ziffernfolgen
	zur Verf�gung.
*/

public interface TokenList {
	// Konstanten zur Bezeichnung der Knoten des Syntaxbaumes
	
	final byte	NO_TYPE=0,
				NUM=1,
				DIGIT=2,
				ASSIGNMENT=3,
				EPSILON=4,
				START=5,
				NOT_FINAL=6,
				SEMICOLON=7,
				IDENT=8,
				OPEN_PARENTHESES =9,
				CLOSE_PARENTHESES =10,
				OPEN_CURLY_BRACKET=11,
				CLOSE_CURLY_BRACKET=12,
				HASHTAG=13,
				DOUBLE_QUOTES=14,
				EXPRESSION=15,
				RIGHT_EXPRESSION=16,
				TERM=17,
				RIGHT_TERM=18,
				OPERATOR=20,
				PROGRAM=21,
				FUNCTION=22,
				UNDERSCORE=23,
				LESS_THAN=24,
				MINUS=25,
				FIRST_COLON=26,
				EMOJI=27,
				EMOJI_CHARACTER=28,
				PLUS=29,
				MULT=30,
				DIV=31,
				VARIABLE_ASSIGNMENT_I=32,
				VARIABLE_ASSIGNMENT=33,
				EMOJI_INT=34,
				EMOJI_START_CODE=35,
				EMOJI_END_CODE=36,
				SEQUENCE=37,
				INSTRUCTION=38,
				VARIABLEDEFINITION=39,
				VARIABLEDEFINITIONWITHASSIGNMENT=40,
				VARIABLEDEFINITIONWITHOUTASSIGNMENT=41,
				EMOJI_DATA_TYPE=42,
				EMOJI_WHILE=43,
				STATEMENT=44,
				EMOJI_IF=45,
				EMOJI_FOR=46,
				FOR_STATEMENT=47,
				EMOJI_ELSE=48,
				EMOJI_UNEQUAL=49,
				EMOJI_EQUAL=50,
				EMOJI_LESS_THAN=51,
				EMOJI_GREATER_THAN=52,
				EMOJI_LESS_THAN_EQUALS=53,
				EMOJI_GREATER_THAN_EQUALS=54,
				COMPARE_OPERATOR=55,
				LOGICAL_OPERATOR=56,
				LOGICAL=57,
				EMOJI_LOGICAL_AND=58,
				EMOJI_LOGICAL_OR=59,
				EMOJI_PLUS=60,
				EMOJI_MINUS=61,
				EMOJI_MULT=62,
				EMOJI_DIV=63,
				FOR_ASSIGNMENTFIRST=64,
				ASSIGNMENT_SIGN=65,
				WHILE=66,
				START_SINGLEQOUTE=67,
				END_SINGLEQOUTE=68,
				CHAR_SIGN=69,
				EXPRESSION_CHAR=70,
				IF=71,
				ELSE=72,
				FOR=73,
				CONDITION=74,
				FOR_ASSIGNMENTSECOND=75,
				EMOJI_PRINTLN=76;
				
				
	// Konstante, die angibt, dass die Semantische Funktion eines Knotens 
	// undefiniert ist
	final int	UNDEFINED=0x10000001;	

				
}