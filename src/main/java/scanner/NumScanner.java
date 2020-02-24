package scanner;

/*
	NunScanner.java
	
	Diese Klasse implementiert die Zustnde und Transitionstabelle eines DEA f�r 
	Ziffernfolgen nach dem folgenden regul�ren Ausdruck:
	
													+
	NUM := {'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'}

*/
public class NumScanner extends Scanner{
	
	//-------------------------------------------------------------------------
	// Konstruktor (Legt die Zust�nde und Transitionstabelle des DEA an)
	//-------------------------------------------------------------------------
	
	protected NumScanner(){
		// Transitionstabelle zum regul�ren Ausdruck
		//	    											+
		// NUM := {'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'}
		
		char transitions[][][]={
		//				START				IDENT																							 _		 <		-		START:	END:	EMOJI																											NUM											;		#		"		(		)		{		}		START'	END'	CHAR
		//				----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		/*START*/		{{},	{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},	{},		{'<'},	{},		{':'},	{},		{},																												{'1','2','3','4','5','6','7','8','9','0'},	{';'},	{'#'},	{'"'},	{'('},	{')'},	{'{'},	{'}'},	{'\''},	{},		{}},		
		/*IDENT*/		{{}, 	{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},	{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}},	
		/*_*/			{{}, 	{},																											{},		{},		{},		{},		{},		{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},		{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}},
		/*<*/			{{}, 	{},																											{},		{},		{'-'},	{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}},
		/*-*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}},	//LEER
		/*START:*/		{{}, 	{},																											{},		{},		{},		{},		{},		{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},		{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}},	
		/*END:*/		{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}},	//LEER
		/*EMOJI*/		{{}, 	{},																											{'_'},	{},		{},		{},		{':'},	{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},		{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}},
		/*NUMBER*/		{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{'1','2','3','4','5','6','7','8','9','0'},	{},		{},		{},		{},		{},		{},		{},		{},		{},		{}},
		/*;*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}}, 	//LEER		
		/*#*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}}, 	//LEER		
		/*"*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}}, 	//LEER		
		/*(*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}}, 	//LEER		
		/*)*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}}, 	//LEER		
		/*{*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}}, 	//LEER		
		/*}*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}}, 	//LEER		
		/*START'*/		{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}},
		/*END'*/		{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{},		{}}, 	//LEER								
		/*CHAR*/		{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{},		{},		{'\''},		{}}};
		//				-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// Zust�nde zum DEA 
		byte states[]={START, IDENT, UNDERSCORE, LESS_THAN, ASSIGMENT_SIGN, FIRST_COLON, EMOJI, EMOJI_CHARACTER, NUM, SEMICOLON, HASHTAG, DOUBLE_QUOTES, OPEN_PARENTHESES, CLOSE_PARENTHESES, OPEN_CURLY_BRACKET, CLOSE_CURLY_BRACKET, START_SINGLEQOUTE, END_SINGLEQOUTE, CHAR_SIGN};
		// Instanz des DEA anlegen
		this.dea=new DEA(transitions, states);
	}
	
	// Gibt den zum Zahlenwert passenden String des Tokentyps zur�ck
	// Implementierung der abstrakten Methode aus der Klasse Scanner
	String getTokenString(byte token){
		switch(token){
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
			case 57: return "LOGICAL=57";
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

		default: return "NO_TOKEN";
		}
	}
	
	

}