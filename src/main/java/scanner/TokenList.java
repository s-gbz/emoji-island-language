package scanner;

/*
	interface TokenList

	Praktikum Algorithmen und Datenstrukturen
	Beispiel zum Versuch 2

	Die Schnittstelle TokenList stellt die Konstanten f�r die
	Knotentypen eines Syntaxbaumes (Token) f�r Ziffernfolgen
	zur Verf�gung.
*/

interface TokenList {
	// Konstanten zur Bezeichnung der Knoten des Syntaxbaumes
	
	final byte	NO_TYPE=0,
				NUM=1,
				DIGIT=2,
				ASSIGMENT=3,
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
				SECOND_COLON=27,
				EMOJI_CHARAKTER=28;
				
	// Konstante, die angibt, dass die Semantische Funktion eines Knotens 
	// undefiniert ist
	final int	UNDEFINED=0x10000001;	

				
}