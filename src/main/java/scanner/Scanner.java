package scanner;

import java.util.*;
import java.io.*;

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
	// Datenstruktur f�r den Deterministischen Endlichen Automatens 
	// (DEA).
	// transitions gibt die �bergangstabelle von einem Zustand zum 
	// n�chsten an. Der �bergang von Zustand i zu Zustand j ist dann
	// m�glich, wenn auf der Eingabe ein Zeichen aus transitions[i][j]
	// gelesen wird.
	// Ein �bergang von Zustand 2 nach Zustand 3 soll z.B. beim Lesen eines
	// ';' oder eines ',' m�glich sein, dann ist transitions [2][3]={';',','}
	//
	// token gibt den Token der Grammatik an, der einem Endzustand des DEA
	// entsprechen soll. Im Beispiel oben w�re z.B. token[3]=TRENNZEICHEN bzw.
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
	// zusammen mit einem Hinweis auf die Eingabezeile f�r die bessere
	// Lokalisierung der Syntaxfehler durch den Parser
	//-------------------------------------------------------------------------		

	class Token{
		byte token;
		String lexem;
		int line;
		
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

	// Konstante f�r Ende der Eingabe
	public final char EOF=(char)255;
	
	//-------------------------------------------------------------------------		
	// Attribute
	//-------------------------------------------------------------------------		

	// Eingabezeichen aus Datei 
	private LinkedList <InputCharacter> inputStream;

	// Pointer auf aktuelles Zeichen aus inputStream
	private int pointer;
	
	// Lexem f�r des aktuellen Tokens
	private String lexem;

	// Liste der durch den Scanner erkannten Token aus der Eingabe
	LinkedList <Token> tokenStream;
	
	// Instanz des deterministischen endlichen Automaten (DEA) 
	DEA dea;  
			
	//-------------------------------------------------------------------------		
	// Hilfsmethoden
	//-------------------------------------------------------------------------		

	
	//-------------------------------------------------------------------------		
	// Methode, die testet, ob das aktuele Eingabezeichen unter den Zeichen
	// ist, die als Parameter (matchSet) �bergeben wurden.
	// Ist das der Fall, so gibt match() true zur�ck und setzt den Eingabe-
	// zeiger auf das n�chste Zeichen, sonst wird false zur�ckgegeben.
	//-------------------------------------------------------------------------	
	boolean match(char [] matchSet){
		for (int i=0;i<matchSet.length;i++)
			if (inputStream.get(pointer).character==matchSet[i]){
				System.out.println("match:"+inputStream.get(pointer).character);
				lexem=lexem+inputStream.get(pointer).character;
				pointer++;	//Eingabepointer auf das n�chste Zeichen setzen 
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
	// Gibt den zum Zahlenwert passenden String des Tokentyps zur�ck
	// Wird in der entsprechenden Unterklasse, die den Scanner definiert
	// und somit die Token festlegt implementiert
	//-------------------------------------------------------------------------
	abstract String getTokenString(byte token);

	//-------------------------------------------------------------------------			
	// Methode zum  Ausgaben des Attributes tokenStream
	//-------------------------------------------------------------------------
	void printTokenStream(){
		for(int i=0;i<tokenStream.size();i++)
			System.out.println(getTokenString(tokenStream.get(i).token)+": "+
			tokenStream.get(i).lexem);			
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
	// einer Eingabedatei mit dem �bergebenen Namen.
	// Das Ende der Eingabe wird mit EOF markiert
	//-------------------------------------------------------------------------		
	boolean readInput(String name){
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
					// Leerzeichen �berlesen
				}else if (((char)c)=='\n'){
					// carriage return �berlesen und Zeilennummer hochz�hlen
					l++;
				}else if (c==13){
					// linefeed �berlesen
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
		System.out.println(inputStream.size());
		return true;	
	}//readInput
	
	//-------------------------------------------------------------------------
	// Methoden des DEA
	//-------------------------------------------------------------------------		

	//-------------------------------------------------------------------------
	// F�hrt die lexikalische Analyse f�r den n�chsten Token durch und gibt
	// diesen zur�ck
	//-------------------------------------------------------------------------
	boolean lexicalAnalysis(){
		char [] EOFSet={EOF};
		byte token=NO_TYPE;
		// Eingabe Token f�r Token pr�fen und gefundene Token in tokenStream
		// eintragen
		while(!match(EOFSet)){
			token = getNextToken();
			System.out.println(getTokenString(token));
			// falls kein g�ltiges Token gefunden wurde, lexikalische Analyse
			// abbrechen
			if (token==NO_TYPE)
				return false;
			// sonst Token in tokenStream eintragen
			else
				tokenStream.
				addLast(new Token(token,inputStream.get(pointer-1).line,lexem));
		}//while
		// Bei erfolgreichem Scannen, Token Strom mit EOF abschlie�en
		tokenStream.addLast(new Token((byte)EOF,inputStream.get(pointer-1).line,"EOF"));
		return true;
	}//lexicalAnalysis
	
	//-------------------------------------------------------------------------
	// F�hrt die lexikalische Analyse f�r den n�chsten Token durch und gibt
	// diesen zur�ck
	//-------------------------------------------------------------------------
	byte getNextToken(){
			// Variable, die angibt, ob ein Zustands�bregang des Automaten
			// erfolgt ist
			boolean transitionFound=false;
			int actualState=0;
			// aktuelles Lexem mit Leerstring initialisieren
			lexem="";
			// Schleife durchl�uft die Zust�nde des DEA solange das aufgrund
			// der Eingabe m�glich ist
			do{
				// transitionFound vor jedem neuen Schleifendurchlauf
				// zur�cksetzen
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
			// kann ein Token zur�ckgegeben werden
			if ((dea.states[actualState]!=NOT_FINAL)&&(dea.states[actualState]!=START))
				return dea.states[actualState];
			else{
				lexicalError("");
				System.out.println(pointer);
				return NO_TYPE;
			}
		}//getNextToken

}