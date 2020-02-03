package scanner;

/*
	NunScanner.java
	
	Diese Klasse implementiert die Zustnde und Transitionstabelle eines DEA f�r 
	Ziffernfolgen nach dem folgenden regul�ren Ausdruck:
	
													+
	NUM := {'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'}

*/
class NumScanner extends Scanner{
	
	//-------------------------------------------------------------------------
	// Konstruktor (Legt die Zust�nde und Transitionstabelle des DEA an)
	//-------------------------------------------------------------------------
	
	NumScanner(){
		// Transitionstabelle zum regul�ren Ausdruck
		//	    											+
		// NUM := {'1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9'|'0'}
		
		char transitions[][][]={
		//				START				IDENT																							 _		 <		-		Start:	END:	EMOJI																											NUM											;		#		"		(		)		{		}
		//				--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		/*START*/		{{},	{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},	{},		{'<'},	{},		{':'},	{},		{},																												{'1','2','3','4','5','6','7','8','9','0'},	{';'},	{'#'},	{'"'},	{'('},	{')'},	{'{'},	{'}'}},		
		/*IDENT*/		{{}, 	{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},	{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},	
		/*_*/			{{}, 	{},																											{},		{},		{},		{},		{},		{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},		{},											{},		{},		{},		{},		{},		{},		{}},
		/*<*/			{{}, 	{},																											{},		{},		{'-'},	{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*-*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*FIRST:*/		{{}, 	{},																											{},		{},		{},		{},		{},		{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},		{},											{},		{},		{},		{},		{},		{},		{}},
		/*END:*/		{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*EMOJI*/		{{}, 	{},																											{'_'},	{},		{},		{},		{':'},	{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'},		{},											{},		{},		{},		{},		{},		{},		{}},
		/*NUMBER*/		{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{'1','2','3','4','5','6','7','8','9','0'},	{},		{},		{},		{},		{},		{},		{}},
		/*;*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*#*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*"*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*(*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*)*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*{*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}},
		/*}*/			{{}, 	{},																											{},		{},		{},		{},		{},		{},																												{},											{},		{},		{},		{},		{},		{},		{}}};
		//				-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// Zust�nde zum DEA 
		byte states[]={START, IDENT, UNDERSCORE, LESS_THAN, MINUS, FIRST_COLON, SECOND_COLON, EMOJI_CHARAKTER, NUM, SEMICOLON, HASHTAG, DOUBLE_QUOTES, OPEN_PAR, CLOSE_PAR, OPEN_CURLY_BRACKET, CLOSE_CURLY_BRACKET};
		// Instanz des DEA anlegen
		this.dea=new DEA(transitions, states);
	}
	
	// Gibt den zum Zahlenwert passenden String des Tokentyps zur�ck
	// Implementierung der abstrakten Methode aus der Klasse Scanner
	String getTokenString(byte token){
		switch(token){
			case  1: return "NUMBER";
			case 25: return "ASSIGMENT";
			case  5: return "START";
			case  7: return "SEMICOLON";
			case  8: return "IDENTIFIER";
			case  9: return "OPEN_PAR";
			case 10: return "CLOSE_PAR";
			case 11: return "OPEN_CURLY_BRACKET";
			case 12: return "CLOSE_CURLY_BRACKET";		
			case 13: return "HASHTAG";
			case 14: return "DOUBLE_QUOTES";
			case 27: return "EMOJI";
		default: return "NO_TOKEN";
		}
	}
	
	

}