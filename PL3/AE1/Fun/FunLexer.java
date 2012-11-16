// $ANTLR 3.3 Nov 30, 2010 12:50:56 Fun.g 2012-11-16 18:57:09

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class FunLexer extends Lexer {
    public static final int EOF=-1;
    public static final int PROG=4;
    public static final int VAR=5;
    public static final int FORMAL=6;
    public static final int NOFORMAL=7;
    public static final int IFELSE=8;
    public static final int SEQ=9;
    public static final int PROCCALL=10;
    public static final int FUNCCALL=11;
    public static final int NOACTUAL=12;
    public static final int PROC=13;
    public static final int ID=14;
    public static final int LPAR=15;
    public static final int RPAR=16;
    public static final int COLON=17;
    public static final int DOT=18;
    public static final int FUNC=19;
    public static final int RETURN=20;
    public static final int ASSN=21;
    public static final int BOOL=22;
    public static final int INT=23;
    public static final int IF=24;
    public static final int ELSE=25;
    public static final int WHILE=26;
    public static final int FOR=27;
    public static final int TO=28;
    public static final int EQ=29;
    public static final int LT=30;
    public static final int GT=31;
    public static final int PLUS=32;
    public static final int MINUS=33;
    public static final int TIMES=34;
    public static final int DIV=35;
    public static final int FALSE=36;
    public static final int TRUE=37;
    public static final int NUM=38;
    public static final int NOT=39;
    public static final int DIGIT=40;
    public static final int LETTER=41;
    public static final int SPACE=42;
    public static final int EOL=43;
    public static final int COMMENT=44;

    // delegates
    // delegators

    public FunLexer() {;} 
    public FunLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public FunLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Fun.g"; }

    // $ANTLR start "BOOL"
    public final void mBOOL() throws RecognitionException {
        try {
            int _type = BOOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:131:6: ( 'bool' )
            // Fun.g:131:8: 'bool'
            {
            match("bool"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOL"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:132:6: ( 'else' )
            // Fun.g:132:8: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:133:7: ( 'false' )
            // Fun.g:133:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "FUNC"
    public final void mFUNC() throws RecognitionException {
        try {
            int _type = FUNC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:134:6: ( 'func' )
            // Fun.g:134:8: 'func'
            {
            match("func"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FUNC"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:135:4: ( 'if' )
            // Fun.g:135:6: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:136:5: ( 'int' )
            // Fun.g:136:7: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "PROC"
    public final void mPROC() throws RecognitionException {
        try {
            int _type = PROC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:137:6: ( 'proc' )
            // Fun.g:137:8: 'proc'
            {
            match("proc"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROC"

    // $ANTLR start "RETURN"
    public final void mRETURN() throws RecognitionException {
        try {
            int _type = RETURN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:138:8: ( 'return' )
            // Fun.g:138:10: 'return'
            {
            match("return"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RETURN"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:139:6: ( 'true' )
            // Fun.g:139:8: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:140:7: ( 'while' )
            // Fun.g:140:9: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:141:9: ( 'for' )
            // Fun.g:141:13: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "TO"
    public final void mTO() throws RecognitionException {
        try {
            int _type = TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:142:5: ( 'to' )
            // Fun.g:142:9: 'to'
            {
            match("to"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TO"

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:144:4: ( '==' )
            // Fun.g:144:6: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQ"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:145:4: ( '<' )
            // Fun.g:145:6: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:146:4: ( '>' )
            // Fun.g:146:6: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GT"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:147:6: ( '+' )
            // Fun.g:147:8: '+'
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
            // Fun.g:148:7: ( '-' )
            // Fun.g:148:9: '-'
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
            // Fun.g:149:7: ( '*' )
            // Fun.g:149:9: '*'
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
            // Fun.g:150:5: ( '/' )
            // Fun.g:150:7: '/'
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

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:151:5: ( 'not' )
            // Fun.g:151:7: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "ASSN"
    public final void mASSN() throws RecognitionException {
        try {
            int _type = ASSN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:153:6: ( '=' )
            // Fun.g:153:8: '='
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

    // $ANTLR start "LPAR"
    public final void mLPAR() throws RecognitionException {
        try {
            int _type = LPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:155:6: ( '(' )
            // Fun.g:155:8: '('
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
            // Fun.g:156:6: ( ')' )
            // Fun.g:156:8: ')'
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

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:157:7: ( ':' )
            // Fun.g:157:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:158:5: ( '.' )
            // Fun.g:158:7: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:160:5: ( ( DIGIT )+ )
            // Fun.g:160:7: ( DIGIT )+
            {
            // Fun.g:160:7: ( DIGIT )+
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
            	    // Fun.g:160:7: DIGIT
            	    {
            	    mDIGIT(); 

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

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:162:4: ( LETTER ( LETTER | DIGIT )* )
            // Fun.g:162:6: LETTER ( LETTER | DIGIT )*
            {
            mLETTER(); 
            // Fun.g:162:13: ( LETTER | DIGIT )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Fun.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "SPACE"
    public final void mSPACE() throws RecognitionException {
        try {
            int _type = SPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:164:7: ( ( ' ' | '\\t' )+ )
            // Fun.g:164:9: ( ' ' | '\\t' )+
            {
            // Fun.g:164:9: ( ' ' | '\\t' )+
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
            	    // Fun.g:
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

    // $ANTLR start "EOL"
    public final void mEOL() throws RecognitionException {
        try {
            int _type = EOL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:165:5: ( ( '\\r' )? '\\n' )
            // Fun.g:165:7: ( '\\r' )? '\\n'
            {
            // Fun.g:165:7: ( '\\r' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Fun.g:165:7: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EOL"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Fun.g:166:9: ( '#' (~ ( '\\r' | '\\n' ) )* ( '\\r' )? '\\n' )
            // Fun.g:166:11: '#' (~ ( '\\r' | '\\n' ) )* ( '\\r' )? '\\n'
            {
            match('#'); 
            // Fun.g:166:15: (~ ( '\\r' | '\\n' ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='\u0000' && LA5_0<='\t')||(LA5_0>='\u000B' && LA5_0<='\f')||(LA5_0>='\u000E' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Fun.g:166:15: ~ ( '\\r' | '\\n' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // Fun.g:167:5: ( '\\r' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\r') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // Fun.g:167:5: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // Fun.g:169:17: ( 'a' .. 'z' | 'A' .. 'Z' )
            // Fun.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // Fun.g:170:17: ( '0' .. '9' )
            // Fun.g:170:19: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    public void mTokens() throws RecognitionException {
        // Fun.g:1:8: ( BOOL | ELSE | FALSE | FUNC | IF | INT | PROC | RETURN | TRUE | WHILE | FOR | TO | EQ | LT | GT | PLUS | MINUS | TIMES | DIV | NOT | ASSN | LPAR | RPAR | COLON | DOT | NUM | ID | SPACE | EOL | COMMENT )
        int alt7=30;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // Fun.g:1:10: BOOL
                {
                mBOOL(); 

                }
                break;
            case 2 :
                // Fun.g:1:15: ELSE
                {
                mELSE(); 

                }
                break;
            case 3 :
                // Fun.g:1:20: FALSE
                {
                mFALSE(); 

                }
                break;
            case 4 :
                // Fun.g:1:26: FUNC
                {
                mFUNC(); 

                }
                break;
            case 5 :
                // Fun.g:1:31: IF
                {
                mIF(); 

                }
                break;
            case 6 :
                // Fun.g:1:34: INT
                {
                mINT(); 

                }
                break;
            case 7 :
                // Fun.g:1:38: PROC
                {
                mPROC(); 

                }
                break;
            case 8 :
                // Fun.g:1:43: RETURN
                {
                mRETURN(); 

                }
                break;
            case 9 :
                // Fun.g:1:50: TRUE
                {
                mTRUE(); 

                }
                break;
            case 10 :
                // Fun.g:1:55: WHILE
                {
                mWHILE(); 

                }
                break;
            case 11 :
                // Fun.g:1:61: FOR
                {
                mFOR(); 

                }
                break;
            case 12 :
                // Fun.g:1:65: TO
                {
                mTO(); 

                }
                break;
            case 13 :
                // Fun.g:1:68: EQ
                {
                mEQ(); 

                }
                break;
            case 14 :
                // Fun.g:1:71: LT
                {
                mLT(); 

                }
                break;
            case 15 :
                // Fun.g:1:74: GT
                {
                mGT(); 

                }
                break;
            case 16 :
                // Fun.g:1:77: PLUS
                {
                mPLUS(); 

                }
                break;
            case 17 :
                // Fun.g:1:82: MINUS
                {
                mMINUS(); 

                }
                break;
            case 18 :
                // Fun.g:1:88: TIMES
                {
                mTIMES(); 

                }
                break;
            case 19 :
                // Fun.g:1:94: DIV
                {
                mDIV(); 

                }
                break;
            case 20 :
                // Fun.g:1:98: NOT
                {
                mNOT(); 

                }
                break;
            case 21 :
                // Fun.g:1:102: ASSN
                {
                mASSN(); 

                }
                break;
            case 22 :
                // Fun.g:1:107: LPAR
                {
                mLPAR(); 

                }
                break;
            case 23 :
                // Fun.g:1:112: RPAR
                {
                mRPAR(); 

                }
                break;
            case 24 :
                // Fun.g:1:117: COLON
                {
                mCOLON(); 

                }
                break;
            case 25 :
                // Fun.g:1:123: DOT
                {
                mDOT(); 

                }
                break;
            case 26 :
                // Fun.g:1:127: NUM
                {
                mNUM(); 

                }
                break;
            case 27 :
                // Fun.g:1:131: ID
                {
                mID(); 

                }
                break;
            case 28 :
                // Fun.g:1:134: SPACE
                {
                mSPACE(); 

                }
                break;
            case 29 :
                // Fun.g:1:140: EOL
                {
                mEOL(); 

                }
                break;
            case 30 :
                // Fun.g:1:144: COMMENT
                {
                mCOMMENT(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\1\uffff\10\26\1\47\6\uffff\1\26\11\uffff\5\26\1\56\4\26\1\63\1"+
        "\26\2\uffff\5\26\1\72\1\uffff\1\73\3\26\1\uffff\1\26\1\100\1\101"+
        "\1\102\1\26\1\104\2\uffff\1\105\1\26\1\107\1\26\3\uffff\1\111\2"+
        "\uffff\1\26\1\uffff\1\113\1\uffff\1\114\2\uffff";
    static final String DFA7_eofS =
        "\115\uffff";
    static final String DFA7_minS =
        "\1\11\1\157\1\154\1\141\1\146\1\162\1\145\1\157\1\150\1\75\6\uffff"+
        "\1\157\11\uffff\1\157\1\163\1\154\1\156\1\162\1\60\1\164\1\157\1"+
        "\164\1\165\1\60\1\151\2\uffff\1\164\1\154\1\145\1\163\1\143\1\60"+
        "\1\uffff\1\60\1\143\1\165\1\145\1\uffff\1\154\3\60\1\145\1\60\2"+
        "\uffff\1\60\1\162\1\60\1\145\3\uffff\1\60\2\uffff\1\156\1\uffff"+
        "\1\60\1\uffff\1\60\2\uffff";
    static final String DFA7_maxS =
        "\1\172\1\157\1\154\1\165\1\156\1\162\1\145\1\162\1\150\1\75\6\uffff"+
        "\1\157\11\uffff\1\157\1\163\1\154\1\156\1\162\1\172\1\164\1\157"+
        "\1\164\1\165\1\172\1\151\2\uffff\1\164\1\154\1\145\1\163\1\143\1"+
        "\172\1\uffff\1\172\1\143\1\165\1\145\1\uffff\1\154\3\172\1\145\1"+
        "\172\2\uffff\1\172\1\162\1\172\1\145\3\uffff\1\172\2\uffff\1\156"+
        "\1\uffff\1\172\1\uffff\1\172\2\uffff";
    static final String DFA7_acceptS =
        "\12\uffff\1\16\1\17\1\20\1\21\1\22\1\23\1\uffff\1\26\1\27\1\30\1"+
        "\31\1\32\1\33\1\34\1\35\1\36\14\uffff\1\15\1\25\6\uffff\1\5\4\uffff"+
        "\1\14\6\uffff\1\13\1\6\4\uffff\1\24\1\1\1\2\1\uffff\1\4\1\7\1\uffff"+
        "\1\11\1\uffff\1\3\1\uffff\1\12\1\10";
    static final String DFA7_specialS =
        "\115\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\27\1\30\2\uffff\1\30\22\uffff\1\27\2\uffff\1\31\4\uffff\1"+
            "\21\1\22\1\16\1\14\1\uffff\1\15\1\24\1\17\12\25\1\23\1\uffff"+
            "\1\12\1\11\1\13\2\uffff\32\26\6\uffff\1\26\1\1\2\26\1\2\1\3"+
            "\2\26\1\4\4\26\1\20\1\26\1\5\1\26\1\6\1\26\1\7\2\26\1\10\3\26",
            "\1\32",
            "\1\33",
            "\1\34\15\uffff\1\36\5\uffff\1\35",
            "\1\37\7\uffff\1\40",
            "\1\41",
            "\1\42",
            "\1\44\2\uffff\1\43",
            "\1\45",
            "\1\46",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\50",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "\1\64",
            "",
            "",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "\1\74",
            "\1\75",
            "\1\76",
            "",
            "\1\77",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "\1\103",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "",
            "",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "\1\106",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "\1\110",
            "",
            "",
            "",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "",
            "",
            "\1\112",
            "",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "",
            "\12\26\7\uffff\32\26\6\uffff\32\26",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( BOOL | ELSE | FALSE | FUNC | IF | INT | PROC | RETURN | TRUE | WHILE | FOR | TO | EQ | LT | GT | PLUS | MINUS | TIMES | DIV | NOT | ASSN | LPAR | RPAR | COLON | DOT | NUM | ID | SPACE | EOL | COMMENT );";
        }
    }
 

}