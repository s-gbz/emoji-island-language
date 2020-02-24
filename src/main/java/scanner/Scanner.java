package scanner;

import java.util.*;
import java.io.*;
import com.vdurmont.emoji.*;

abstract class Scanner implements TokenList{

	//-------------------------------------------------------------------------		
	// Datenstruktur zum Ablegen eines Eingabezeichens mit Angabe der
	// Zeilennummer aus der Eingabedatei
	//-------------------------------------------------------------------------		

	class InputCharacter{
		// Attribute
		
		// Eingabezeichen
		char character;
		// Zeilennummer
		int line;

		// Konstruktor
		InputCharacter(char c, int l){
			this.character=c;
			this.line=l;
		}	
	}//InputCharacter
	
	//-------------------------------------------------------------------------		
	// Datenstruktur für den Deterministischen Endlichen Automatens 
	// (DEA).
	// transitions gibt die Übergangstabelle von einem Zustand zum 
	// nächsten an. Der Übergang von Zustand i zu Zustand j ist dann
	// möglich, wenn auf der Eingabe ein Zeichen aus transitions[i][j]
	// gelesen wird.
	// Ein Übergang von Zustand 2 nach Zustand 3 soll z.B. beim Lesen eines
	// ';' oder eines ',' möglich sein, dann ist transitions [2][3]={';',','}
	//
	// token gibt den Token der Grammatik an, der einem Endzustand des DEA
	// entsprechen soll. Im Beispiel oben wäre z.B. token[3]=TRENNZEICHEN bzw.
	// DELIMITER.
	//
	//-------------------------------------------------------------------------		

	class DEA{
		// Attribute
		char transitions [][][];
		byte states [];
		// Konstruktor
		DEA(char transitions[][][],byte states[]){
			this.transitions=transitions;
			this.states=states;
		}	
	}
	
	//-------------------------------------------------------------------------		
	// Datenstruktur zum Ablegen der aus der Eingabedatei gewonnenen Token
	// zusammen mit einem Hinweis auf die Eingabezeile für die bessere
	// Lokalisierung der Syntaxfehler durch den Parser
	//-------------------------------------------------------------------------		

	public class Token{
		public byte token;
		public String lexem;
		public int line;
		
		// Konstruktor
		Token(byte token, int line, String lexem){
			this.token=token;
			this.lexem=lexem;
			this.line=line;
		}
	}
	
	//-------------------------------------------------------------------------		
	// Konstanten
	//-------------------------------------------------------------------------		

	// Konstante für Ende der Eingabe
	public final char EOF=(char)255;
	
	//-------------------------------------------------------------------------		
	// Attribute
	//-------------------------------------------------------------------------		

	// Eingabezeichen aus Datei 
	private LinkedList <InputCharacter> inputStream;

	// Pointer auf aktuelles Zeichen aus inputStream
	private int pointer;
	
	// Lexem für des aktuellen Tokens
	private String lexem;

	// Liste der durch den Scanner erkannten Token aus der Eingabe
	protected LinkedList <Token> tokenStream;
	
	// Instanz des deterministischen endlichen Automaten (DEA) 
	DEA dea;  
			
	//-------------------------------------------------------------------------		
	// Hilfsmethoden
	//-------------------------------------------------------------------------		

	
	//-------------------------------------------------------------------------		
	// Methode, die testet, ob das aktuele Eingabezeichen unter den Zeichen
	// ist, die als Parameter (matchSet) übergeben wurden.
	// Ist das der Fall, so gibt match() true zurück und setzt den Eingabe-
	// zeiger auf das nächste Zeichen, sonst wird false zurückgegeben.
	//-------------------------------------------------------------------------	
	boolean match(char [] matchSet){
		for (int i=0;i<matchSet.length;i++)
			if (inputStream.get(pointer).character==matchSet[i]){
				System.out.println("match:"+inputStream.get(pointer).character);
				lexem=lexem+inputStream.get(pointer).character;
				pointer++;	//Eingabepointer auf das nächste Zeichen setzen 
				return true;		
			}
		return false;
	}//match
	
	//-------------------------------------------------------------------------
	// Methode zum Ausgeben eines lexikalischen Fehlers mit Angabe des 
	// vermuteten Zeichens, bei dem der Fehler gefunden wurde 
	//-------------------------------------------------------------------------
	void lexicalError(String s){
		char z;
		System.out.println("lexikalischer Fehler in Zeile "+
				inputStream.get(pointer).line+". Zeichen: "+
				inputStream.get(pointer).character);
		System.out.println((byte)inputStream.get(pointer).character);	
	}//lexicalError

	//-------------------------------------------------------------------------
	// Gibt den zum Zahlenwert passenden String des Tokentyps zurück
	// Wird in der entsprechenden Unterklasse, die den Scanner definiert
	// und somit die Token festlegt implementiert
	//-------------------------------------------------------------------------
	abstract String getTokenString(byte token);

	//-------------------------------------------------------------------------			
	// Methode zum  Ausgaben des Attributes tokenStream
	//-------------------------------------------------------------------------
	void printTokenStream(){
		for(int i=0;i<tokenStream.size();i++) {
			System.out.println(getTokenString(tokenStream.get(i).token)+": "+
			tokenStream.get(i).lexem);	
			System.out.println(tokenStream.get(i).token+": "+
				tokenStream.get(i).lexem);	
		}
	}


	//-------------------------------------------------------------------------			
	// Methode zum  Ausgaben des Attributes inputStream
	//-------------------------------------------------------------------------
	void printInputStream(){
		for(int i=0;i<inputStream.size();i++)
			System.out.print(inputStream.get(i).character);
		System.out.println();
			
	}
	
	//-------------------------------------------------------------------------			
	// Methode zum zeichenweise Einlesen der Eingabe aus
	// einer Eingabedatei mit dem übergebenen Namen.
	// Das Ende der Eingabe wird mit EOF markiert
	//-------------------------------------------------------------------------		
	public boolean readInputEmoji(String name){
		int c=0;
		int l=1;
		inputStream=new LinkedList <InputCharacter> ();
		tokenStream=new LinkedList <Token>();
		try{
			File file = new File(name);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			    if (line.isEmpty()) {
			        break;
			    }
			    
			    System.out.println("Line: " + line);
				String parseLine = EmojiParser.parseToAliases(line);
				System.out.println("ParseLine: " + parseLine);
			    
			    char parseLineArray[] = parseLine.toCharArray();
				
				for(int i = 0; i<parseLineArray.length; i++){
					c = parseLineArray[i];
					System.out.println("Lc: " + c);
					if(((char)c)==' '){
						// Leerzeichen überlesen
					}else if (((char)c)=='\n'){
						// carriage return überlesen und Zeilennummer hochzählen
						l++;
					}else if (((char)c)=='\r'){
						// linefeed überlesen
					}else if (((char)c)=='\t'){
						// tabulator überlesen
					}else{
						// Zeichen einlesen
						inputStream.addLast(new InputCharacter((char)c, l));
					}
					
				}
			    
			}
			inputStream.addLast(new InputCharacter(EOF, l));
			
			
			/*String emojiLine = "summe <- 0 is 📊;";
			System.out.println("EmojiLine: " + EmojiParser.parseToAliases(emojiLine));*/
			
			bufferedReader.close();
		}
		catch(Exception e){
			System.out.println("Fehler beim Dateizugriff: "+name);
			return false;
		}
		
		System.out.println(inputStream.size());
		return true;	
	}//readInput

	//-------------------------------------------------------------------------			
	// Methode zum zeichenweise Einlesen der Eingabe aus
	// einer Eingabedatei mit dem übergebenen Namen.
	// Das Ende der Eingabe wird mit EOF markiert
	//-------------------------------------------------------------------------		
	public boolean readInput(String name){
		int c=0;
		int l=1;
		inputStream=new LinkedList <InputCharacter> ();
		tokenStream=new LinkedList <Token>();
		try{			
			FileReader f=new FileReader(name);
			
			while(true){
				c = f.read();
				if (c== -1){
					inputStream.addLast(new InputCharacter(EOF, l));
					break;
				}else if(((char)c)==' '){
					// Leerzeichen überlesen
				}else if (((char)c)=='\n'){
					// carriage return überlesen und Zeilennummer hochzählen
					l++;
				}else if (((char)c)=='\r'){
					// linefeed überlesen
				}else if (((char)c)=='\t'){
					// tabulator überlesen
				}else{
					// Zeichen einlesen
					inputStream.addLast(new InputCharacter((char)c, l));
				}
			}
			

		}
		catch(Exception e){
			System.out.println("Fehler beim Dateizugriff: "+name);
			return false;
		}
		//if(lexicalAnalysis())
			//printTokenStream();
		
		System.out.println(inputStream.size());
		return true;	
	}//readInput
	
	//-------------------------------------------------------------------------
	// Methoden des DEA
	//-------------------------------------------------------------------------		

	//-------------------------------------------------------------------------
	// Führt die lexikalische Analyse für den nächsten Token durch und gibt
	// diesen zurück
	//-------------------------------------------------------------------------
	public boolean lexicalAnalysis(){
		char [] EOFSet={EOF};
		byte token=NO_TYPE;
		// Eingabe Token für Token prüfen und gefundene Token in tokenStream
		// eintragen
		while(!match(EOFSet)){
			token = getNextToken();
			System.out.println(getTokenString(token));
			// falls kein gültiges Token gefunden wurde, lexikalische Analyse
			// abbrechen
			if (token==NO_TYPE) {
				return false;
			}
			// sonst Token in tokenStream eintragen
			else {
				System.out.println("LEXEM: " + lexem);
				System.out.println("token: " + getTokenString(token));
				if(token==IDENT) {
					System.out.println("token: " + getTokenString(token));
					if(lexem.equals(new String("is"))) {
						token=VARIABLE_ASSIGMENT;
						System.out.println("token: " + getTokenString(token));

					}
				}else if(token==EMOJI) { //SECOND_COLON
					token = parseEmoji(lexem);
					if(token==EMOJI_PLUS || token==EMOJI_MINUS || token == EMOJI_MULT || token == EMOJI_DIV) {
						token = parseArithmeticEmoji(token);
					}
				}
				System.out.println("token: " + getTokenString(token));
				System.out.println("LEXEM: " + lexem);
				tokenStream.addLast(new Token(token,inputStream.get(pointer-1).line,lexem));
			}
		}//while
		// Bei erfolgreichem Scannen, Token Strom mit EOF abschließen
		tokenStream.addLast(new Token((byte)EOF,inputStream.get(pointer-1).line,"EOF"));
		return true;
	}//lexicalAnalysis
	
	//-------------------------------------------------------------------------
	// Führt die lexikalische Analyse für den nächsten Token durch und gibt
	// diesen zurück
	//-------------------------------------------------------------------------
	byte getNextToken(){
			// Variable, die angibt, ob ein Zustandsübregang des Automaten
			// erfolgt ist
			boolean transitionFound=false;
			int actualState=0;
			// aktuelles Lexem mit Leerstring initialisieren
			lexem="";
			// Schleife durchläuft die Zustände des DEA solange das aufgrund
			// der Eingabe möglich ist
			do{
				// transitionFound vor jedem neuen Schleifendurchlauf
				// zurücksetzen
				transitionFound=false;
				// Folgezustand des DEA zu actualState ermitteln
				for(int j=0;j<dea.transitions[actualState].length;j++)
					if (match(dea.transitions[actualState][j])){
						// Eingabewert passt zu Wertemenge des Zustands j
						actualState=j;
						System.out.println(actualState+"->"+j);
						transitionFound=true;
						break;
					}
			}while(transitionFound);
			// Wenn der DEA sich jetzt in einem Endzustand befindet,
			// kann ein Token zurückgegeben werden
			if ((dea.states[actualState]!=NOT_FINAL)&&(dea.states[actualState]!=START))
				return dea.states[actualState];
			else{
				lexicalError("");
				System.out.println(pointer);
				return NO_TYPE;
			}
		}//getNextToken
	
	
	byte parseEmoji(String lexem) {
		switch(lexem) {
		case ":triangular_flag_on_post:":
			return EMOJI_START_CODE;
		case ":checkered_flag:":
			return EMOJI_END_CODE;
		case ":bar_chart:":
			return EMOJI_INT;
		case ":alarm_clock:":
			return EMOJI_WHILE;
		case ":white_check_mark:":
			return EMOJI_IF;
		case ":hourglass_flowing_sand:":
			return EMOJI_FOR;
		case ":x:":
			return EMOJI_ELSE;
		case ":crossed_swords:":
			return EMOJI_UNEQUAL;
		case ":rainbow_flag:":
			return EMOJI_EQUAL;
		case ":rewind:":
			return EMOJI_LESS_THAN;
		case ":fast_forward:":
			return EMOJI_GREATER_THAN;
		case ":black_left_pointing_double_triangle_with_vertical_bar:":
			return EMOJI_LESS_THAN_EQUALS;
		case ":black_right_pointing_double_triangle_with_vertical_bar:":
			return EMOJI_GREATER_THAN_EQUALS;
		case ":link:":
			return EMOJI_LOGICAL_AND;
		case ":grey_question:":
			return EMOJI_LOGICAL_OR;
		case ":heavy_plus_sign:":
			return EMOJI_PLUS;
		case ":heavy_minus_sign:":
			return EMOJI_MINUS;
		case ":heavy_multiplication_x:":
			return EMOJI_MULT;
		case ":heavy_division_sign:":
			return EMOJI_DIV;
		case ":memo:":
			return EMOJI_CHARACTER;
		default:
			return NO_TYPE;
		}
	}
	
	byte parseArithmeticEmoji(byte token) {
		switch(token) {
		case EMOJI_PLUS:
			return PLUS;
		case EMOJI_MINUS:
			return MINUS;
		case EMOJI_MULT:
			return MULT;
		case EMOJI_DIV:
			return DIV;
		default:
			return NO_TYPE;
		}
	}

}