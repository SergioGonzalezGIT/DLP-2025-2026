package parser;

public class LexerHelper {

	public static int lexemeToInt(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}

	public static double lexemeToReal(String str) {
		try {
			return Double.parseDouble(str);
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return -1;
	}

	public static char lexemeToChar(String str) {

		if(str.length()==3){
			return str.charAt(1);
		}

		if(str.equals("'\\t'")){
			return '\t';
		}

		if(str.equals("'\\n'")){
			return '\n';
		}

		try {
			String formated = str.substring(2,str.length()-1);
			int ascii = Integer.parseInt(formated);
			return (char) ascii;
		}
		catch(NumberFormatException e) {
			System.out.println(e);
		}
		return '\0';
	}

	// TODO: Implement the lexemeToChar and lexemeToReal methods

}
