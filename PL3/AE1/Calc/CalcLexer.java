// $ANTLR 3.3 Nov 30, 2010 12:50:56 Calc.g 2012-10-26 15:11:34

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CalcLexer extends Lexer {
    public static final int EOF=-1;
    public static final int PUT=4;
    public static final int EOL=5;
    public static final int SET=6;
    public static final int ID=7;
    public static final int ASSN=8;
    public static final int SCOM=9;
    public static final int FCOM=10;
    public static final int PLUS=11;
    public static final int MINUS=12;
    public static final int TIMES=13;
    public static final int DIV=14;
    public static final int NUM=15;
    public static final int LPAR=16;
    public static final int RPAR=17;
    public static final int SPACE=18;

    // delegates
    // delegators

    public CalcLexer() {;} 
    public CalcLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public CalcLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Calc.g"; }

    // $ANTLR start "PUT"
    public final void mPUT() throws RecognitionException {
        try {
            int _type = PUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:47:5: ( 'put' )
            // Calc.g:47:7: 'put'
            {
            match("put"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PUT"

    // $ANTLR start "SET"
    public final void mSET() throws RecognitionException {
        try {
            int _type = SET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:48:5: ( 'set' )
            // Calc.g:48:7: 'set'
            {
            match("set"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SET"

    // $ANTLR start "ASSN"
    public final void mASSN() throws RecognitionException {
        try {
            int _type = ASSN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:50:6: ( '=' )
            // Calc.g:50:8: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSN"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:51:6: ( '+' )
            // Calc.g:51:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:52:7: ( '-' )
            // Calc.g:52:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "TIMES"
    public final void mTIMES() throws RecognitionException {
        try {
            int _type = TIMES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:53:7: ( '*' )
            // Calc.g:53:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMES"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:54:9: ( '/' )
            // Calc.g:54:13: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "LPAR"
    public final void mLPAR() throws RecognitionException {
        try {
            int _type = LPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:55:6: ( '(' )
            // Calc.g:55:8: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAR"

    // $ANTLR start "RPAR"
    public final void mRPAR() throws RecognitionException {
        try {
            int _type = RPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:56:6: ( ')' )
            // Calc.g:56:8: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAR"

    // $ANTLR start "SCOM"
    public final void mSCOM() throws RecognitionException {
        try {
            int _type = SCOM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:57:9: ( '/*' )
            // Calc.g:57:13: '/*'
            {
            match("/*"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SCOM"

    // $ANTLR start "FCOM"
    public final void mFCOM() throws RecognitionException {
        try {
            int _type = FCOM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:58:9: ( '*/' )
            // Calc.g:58:13: '*/'
            {
            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FCOM"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:60:4: ( 'a' .. 'z' )
            // Calc.g:60:6: 'a' .. 'z'
            {
            matchRange('a','z'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:61:5: ( ( '0' .. '9' )+ )
            // Calc.g:61:7: ( '0' .. '9' )+
            {
            // Calc.g:61:7: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Calc.g:61:7: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM"

    // $ANTLR start "EOL"
    public final void mEOL() throws RecognitionException {
        try {
            int _type = EOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:63:5: ( ( '\\r' )? '\\n' )
            // Calc.g:63:7: ( '\\r' )? '\\n'
            {
            // Calc.g:63:7: ( '\\r' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\r') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Calc.g:63:7: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EOL"

    // $ANTLR start "SPACE"
    public final void mSPACE() throws RecognitionException {
        try {
            int _type = SPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Calc.g:65:7: ( ( ' ' | '\\t' )+ )
            // Calc.g:65:9: ( ' ' | '\\t' )+
            {
            // Calc.g:65:9: ( ' ' | '\\t' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\t'||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // Calc.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

             skip(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SPACE"

    public void mTokens() throws RecognitionException {
        // Calc.g:1:8: ( PUT | SET | ASSN | PLUS | MINUS | TIMES | DIV | LPAR | RPAR | SCOM | FCOM | ID | NUM | EOL | SPACE )
        int alt4=15;
        alt4 = dfa4.predict(input);
        switch (alt4) {
            case 1 :
                // Calc.g:1:10: PUT
                {
                mPUT(); 

                }
                break;
            case 2 :
                // Calc.g:1:14: SET
                {
                mSET(); 

                }
                break;
            case 3 :
                // Calc.g:1:18: ASSN
                {
                mASSN(); 

                }
                break;
            case 4 :
                // Calc.g:1:23: PLUS
                {
                mPLUS(); 

                }
                break;
            case 5 :
                // Calc.g:1:28: MINUS
                {
                mMINUS(); 

                }
                break;
            case 6 :
                // Calc.g:1:34: TIMES
                {
                mTIMES(); 

                }
                break;
            case 7 :
                // Calc.g:1:40: DIV
                {
                mDIV(); 

                }
                break;
            case 8 :
                // Calc.g:1:44: LPAR
                {
                mLPAR(); 

                }
                break;
            case 9 :
                // Calc.g:1:49: RPAR
                {
                mRPAR(); 

                }
                break;
            case 10 :
                // Calc.g:1:54: SCOM
                {
                mSCOM(); 

                }
                break;
            case 11 :
                // Calc.g:1:59: FCOM
                {
                mFCOM(); 

                }
                break;
            case 12 :
                // Calc.g:1:64: ID
                {
                mID(); 

                }
                break;
            case 13 :
                // Calc.g:1:67: NUM
                {
                mNUM(); 

                }
                break;
            case 14 :
                // Calc.g:1:71: EOL
                {
                mEOL(); 

                }
                break;
            case 15 :
                // Calc.g:1:75: SPACE
                {
                mSPACE(); 

                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\1\uffff\2\12\3\uffff\1\21\1\23\14\uffff";
    static final String DFA4_eofS =
        "\24\uffff";
    static final String DFA4_minS =
        "\1\11\1\165\1\145\3\uffff\1\57\1\52\14\uffff";
    static final String DFA4_maxS =
        "\1\172\1\165\1\145\3\uffff\1\57\1\52\14\uffff";
    static final String DFA4_acceptS =
        "\3\uffff\1\3\1\4\1\5\2\uffff\1\10\1\11\1\14\1\15\1\16\1\17\1\1\1"+
        "\2\1\13\1\6\1\12\1\7";
    static final String DFA4_specialS =
        "\24\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\15\1\14\2\uffff\1\14\22\uffff\1\15\7\uffff\1\10\1\11\1\6"+
            "\1\4\1\uffff\1\5\1\uffff\1\7\12\13\3\uffff\1\3\43\uffff\17\12"+
            "\1\1\2\12\1\2\7\12",
            "\1\16",
            "\1\17",
            "",
            "",
            "",
            "\1\20",
            "\1\22",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( PUT | SET | ASSN | PLUS | MINUS | TIMES | DIV | LPAR | RPAR | SCOM | FCOM | ID | NUM | EOL | SPACE );";
        }
    }
 

}