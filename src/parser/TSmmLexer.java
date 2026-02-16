// Generated from C:/Users/Windows/Desktop/tercerAno/segundoCuatri/DLP/LABS/proyectos/Codigo_V1/src/parser/TSmm.g4 by ANTLR 4.13.2
package parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class TSmmLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT_CONSTANT=1, REAL_CONSTANT=2, CHAR_CONSTANT=3, ID=4, COMMENT_UNALINEA=5, 
		COMMENT_VARIASLINEAS=6, WHITESPACES=7;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"INT_CONSTANT", "REAL_CONSTANT", "EXPONENT", "CHAR_CONSTANT", "ID", "COMMENT_UNALINEA", 
			"COMMENT_VARIASLINEAS", "WHITESPACES"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INT_CONSTANT", "REAL_CONSTANT", "CHAR_CONSTANT", "ID", "COMMENT_UNALINEA", 
			"COMMENT_VARIASLINEAS", "WHITESPACES"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public TSmmLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "TSmm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0007w\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u0015\b\u0000"+
		"\n\u0000\f\u0000\u0018\t\u0000\u0003\u0000\u001a\b\u0000\u0001\u0001\u0004"+
		"\u0001\u001d\b\u0001\u000b\u0001\f\u0001\u001e\u0001\u0001\u0001\u0001"+
		"\u0005\u0001#\b\u0001\n\u0001\f\u0001&\t\u0001\u0001\u0001\u0001\u0001"+
		"\u0004\u0001*\b\u0001\u000b\u0001\f\u0001+\u0003\u0001.\b\u0001\u0001"+
		"\u0001\u0003\u00011\b\u0001\u0001\u0001\u0004\u00014\b\u0001\u000b\u0001"+
		"\f\u00015\u0001\u0001\u0003\u00019\b\u0001\u0001\u0002\u0001\u0002\u0003"+
		"\u0002=\b\u0002\u0001\u0002\u0004\u0002@\b\u0002\u000b\u0002\f\u0002A"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003M\b\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0005\u0004S\b\u0004\n\u0004\f\u0004"+
		"V\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"\\\b\u0005\n\u0005\f\u0005_\t\u0005\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006g\b\u0006\n\u0006\f\u0006"+
		"j\t\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0004\u0007r\b\u0007\u000b\u0007\f\u0007s\u0001\u0007\u0001"+
		"\u0007\u0001h\u0000\b\u0001\u0001\u0003\u0002\u0005\u0000\u0007\u0003"+
		"\t\u0004\u000b\u0005\r\u0006\u000f\u0007\u0001\u0000\b\u0001\u000019\u0001"+
		"\u000009\u0002\u0000EEee\u0002\u0000++--\u0003\u0000AZ__az\u0004\u0000"+
		"09AZ__az\u0002\u0000\n\n\r\r\u0003\u0000\t\n\r\r  \u0088\u0000\u0001\u0001"+
		"\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0001\u0019\u0001\u0000\u0000\u0000\u00038\u0001\u0000\u0000\u0000"+
		"\u0005:\u0001\u0000\u0000\u0000\u0007C\u0001\u0000\u0000\u0000\tP\u0001"+
		"\u0000\u0000\u0000\u000bW\u0001\u0000\u0000\u0000\rb\u0001\u0000\u0000"+
		"\u0000\u000fq\u0001\u0000\u0000\u0000\u0011\u001a\u00050\u0000\u0000\u0012"+
		"\u0016\u0007\u0000\u0000\u0000\u0013\u0015\u0007\u0001\u0000\u0000\u0014"+
		"\u0013\u0001\u0000\u0000\u0000\u0015\u0018\u0001\u0000\u0000\u0000\u0016"+
		"\u0014\u0001\u0000\u0000\u0000\u0016\u0017\u0001\u0000\u0000\u0000\u0017"+
		"\u001a\u0001\u0000\u0000\u0000\u0018\u0016\u0001\u0000\u0000\u0000\u0019"+
		"\u0011\u0001\u0000\u0000\u0000\u0019\u0012\u0001\u0000\u0000\u0000\u001a"+
		"\u0002\u0001\u0000\u0000\u0000\u001b\u001d\u0007\u0001\u0000\u0000\u001c"+
		"\u001b\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e"+
		"\u001c\u0001\u0000\u0000\u0000\u001e\u001f\u0001\u0000\u0000\u0000\u001f"+
		" \u0001\u0000\u0000\u0000 $\u0005.\u0000\u0000!#\u0007\u0001\u0000\u0000"+
		"\"!\u0001\u0000\u0000\u0000#&\u0001\u0000\u0000\u0000$\"\u0001\u0000\u0000"+
		"\u0000$%\u0001\u0000\u0000\u0000%.\u0001\u0000\u0000\u0000&$\u0001\u0000"+
		"\u0000\u0000\')\u0005.\u0000\u0000(*\u0007\u0001\u0000\u0000)(\u0001\u0000"+
		"\u0000\u0000*+\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000+,\u0001"+
		"\u0000\u0000\u0000,.\u0001\u0000\u0000\u0000-\u001c\u0001\u0000\u0000"+
		"\u0000-\'\u0001\u0000\u0000\u0000.0\u0001\u0000\u0000\u0000/1\u0003\u0005"+
		"\u0002\u00000/\u0001\u0000\u0000\u000001\u0001\u0000\u0000\u000019\u0001"+
		"\u0000\u0000\u000024\u0007\u0001\u0000\u000032\u0001\u0000\u0000\u0000"+
		"45\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000056\u0001\u0000\u0000"+
		"\u000067\u0001\u0000\u0000\u000079\u0003\u0005\u0002\u00008-\u0001\u0000"+
		"\u0000\u000083\u0001\u0000\u0000\u00009\u0004\u0001\u0000\u0000\u0000"+
		":<\u0007\u0002\u0000\u0000;=\u0007\u0003\u0000\u0000<;\u0001\u0000\u0000"+
		"\u0000<=\u0001\u0000\u0000\u0000=?\u0001\u0000\u0000\u0000>@\u0007\u0001"+
		"\u0000\u0000?>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000A?\u0001"+
		"\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000B\u0006\u0001\u0000\u0000"+
		"\u0000CL\u0005\'\u0000\u0000DM\u0001\u0000\u0000\u0000EF\u0005\\\u0000"+
		"\u0000FM\u0003\u0001\u0000\u0000GH\u0005\\\u0000\u0000HM\u0005t\u0000"+
		"\u0000IJ\u0005\\\u0000\u0000JM\u0005n\u0000\u0000KM\t\u0000\u0000\u0000"+
		"LD\u0001\u0000\u0000\u0000LE\u0001\u0000\u0000\u0000LG\u0001\u0000\u0000"+
		"\u0000LI\u0001\u0000\u0000\u0000LK\u0001\u0000\u0000\u0000MN\u0001\u0000"+
		"\u0000\u0000NO\u0005\'\u0000\u0000O\b\u0001\u0000\u0000\u0000PT\u0007"+
		"\u0004\u0000\u0000QS\u0007\u0005\u0000\u0000RQ\u0001\u0000\u0000\u0000"+
		"SV\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000"+
		"\u0000U\n\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000\u0000WX\u0005/\u0000"+
		"\u0000XY\u0005/\u0000\u0000Y]\u0001\u0000\u0000\u0000Z\\\b\u0006\u0000"+
		"\u0000[Z\u0001\u0000\u0000\u0000\\_\u0001\u0000\u0000\u0000][\u0001\u0000"+
		"\u0000\u0000]^\u0001\u0000\u0000\u0000^`\u0001\u0000\u0000\u0000_]\u0001"+
		"\u0000\u0000\u0000`a\u0006\u0005\u0000\u0000a\f\u0001\u0000\u0000\u0000"+
		"bc\u0005/\u0000\u0000cd\u0005*\u0000\u0000dh\u0001\u0000\u0000\u0000e"+
		"g\t\u0000\u0000\u0000fe\u0001\u0000\u0000\u0000gj\u0001\u0000\u0000\u0000"+
		"hi\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000ik\u0001\u0000\u0000"+
		"\u0000jh\u0001\u0000\u0000\u0000kl\u0005*\u0000\u0000lm\u0005/\u0000\u0000"+
		"mn\u0001\u0000\u0000\u0000no\u0006\u0006\u0000\u0000o\u000e\u0001\u0000"+
		"\u0000\u0000pr\u0007\u0007\u0000\u0000qp\u0001\u0000\u0000\u0000rs\u0001"+
		"\u0000\u0000\u0000sq\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000"+
		"tu\u0001\u0000\u0000\u0000uv\u0006\u0007\u0000\u0000v\u0010\u0001\u0000"+
		"\u0000\u0000\u0011\u0000\u0016\u0019\u001e$+-058<ALT]hs\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}