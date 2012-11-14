// $ANTLR 3.3 Nov 30, 2010 12:50:56 Calc.g 2012-10-30 11:52:03

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CalcParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PUT", "EOL", "SET", "ID", "ASSN", "SCOM", "FCOM", "PLUS", "MINUS", "TIMES", "DIV", "NUM", "LPAR", "RPAR", "SPACE"
    };
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


        public CalcParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public CalcParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return CalcParser.tokenNames; }
    public String getGrammarFileName() { return "Calc.g"; }


    	private int[] store = new int[26];
    	// ... storage for variables 'a', ..., 'z'



    // $ANTLR start "prog"
    // Calc.g:10:1: prog : ( com )* EOF ;
    public final void prog() throws RecognitionException {
        try {
            // Calc.g:11:2: ( ( com )* EOF )
            // Calc.g:11:4: ( com )* EOF
            {
            // Calc.g:11:4: ( com )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==PUT||LA1_0==SET||LA1_0==SCOM) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Calc.g:11:4: com
            	    {
            	    pushFollow(FOLLOW_com_in_prog19);
            	    com();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_prog22); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "prog"


    // $ANTLR start "com"
    // Calc.g:16:1: com : ( PUT v= expr EOL | SET ID ASSN v= expr EOL | SCOM ( com )* FCOM );
    public final void com() throws RecognitionException {
        Token ID1=null;
        int v = 0;


        try {
            // Calc.g:17:2: ( PUT v= expr EOL | SET ID ASSN v= expr EOL | SCOM ( com )* FCOM )
            int alt3=3;
            switch ( input.LA(1) ) {
            case PUT:
                {
                alt3=1;
                }
                break;
            case SET:
                {
                alt3=2;
                }
                break;
            case SCOM:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // Calc.g:17:4: PUT v= expr EOL
                    {
                    match(input,PUT,FOLLOW_PUT_in_com35); 
                    pushFollow(FOLLOW_expr_in_com39);
                    v=expr();

                    state._fsp--;

                    match(input,EOL,FOLLOW_EOL_in_com41); 
                     System.out.println(v); 

                    }
                    break;
                case 2 :
                    // Calc.g:18:4: SET ID ASSN v= expr EOL
                    {
                    match(input,SET,FOLLOW_SET_in_com54); 
                    ID1=(Token)match(input,ID,FOLLOW_ID_in_com56); 
                    match(input,ASSN,FOLLOW_ASSN_in_com58); 
                    pushFollow(FOLLOW_expr_in_com66);
                    v=expr();

                    state._fsp--;

                    match(input,EOL,FOLLOW_EOL_in_com68); 
                     int a =
                    		                         (ID1!=null?ID1.getText():null).charAt(0) - 'a'; 
                    		                       store[a] = v; 

                    }
                    break;
                case 3 :
                    // Calc.g:22:9: SCOM ( com )* FCOM
                    {
                    match(input,SCOM,FOLLOW_SCOM_in_com88); 
                    // Calc.g:22:14: ( com )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==PUT||LA2_0==SET||LA2_0==SCOM) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // Calc.g:22:14: com
                    	    {
                    	    pushFollow(FOLLOW_com_in_com90);
                    	    com();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    match(input,FCOM,FOLLOW_FCOM_in_com93); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "com"


    // $ANTLR start "expr"
    // Calc.g:27:1: expr returns [int value] : v1= term ( PLUS v2= term | MINUS v2= term | TIMES v2= term | DIV v2= term )* ;
    public final int expr() throws RecognitionException {
        int value = 0;

        int v1 = 0;

        int v2 = 0;


        try {
            // Calc.g:28:2: (v1= term ( PLUS v2= term | MINUS v2= term | TIMES v2= term | DIV v2= term )* )
            // Calc.g:28:4: v1= term ( PLUS v2= term | MINUS v2= term | TIMES v2= term | DIV v2= term )*
            {
            pushFollow(FOLLOW_term_in_expr134);
            v1=term();

            state._fsp--;

             value = v1; 
            // Calc.g:29:3: ( PLUS v2= term | MINUS v2= term | TIMES v2= term | DIV v2= term )*
            loop4:
            do {
                int alt4=5;
                switch ( input.LA(1) ) {
                case PLUS:
                    {
                    alt4=1;
                    }
                    break;
                case MINUS:
                    {
                    alt4=2;
                    }
                    break;
                case TIMES:
                    {
                    alt4=3;
                    }
                    break;
                case DIV:
                    {
                    alt4=4;
                    }
                    break;

                }

                switch (alt4) {
            	case 1 :
            	    // Calc.g:29:5: PLUS v2= term
            	    {
            	    match(input,PLUS,FOLLOW_PLUS_in_expr155); 
            	    pushFollow(FOLLOW_term_in_expr159);
            	    v2=term();

            	    state._fsp--;

            	     value += v2; 

            	    }
            	    break;
            	case 2 :
            	    // Calc.g:30:5: MINUS v2= term
            	    {
            	    match(input,MINUS,FOLLOW_MINUS_in_expr173); 
            	    pushFollow(FOLLOW_term_in_expr177);
            	    v2=term();

            	    state._fsp--;

            	     value -= v2; 

            	    }
            	    break;
            	case 3 :
            	    // Calc.g:31:5: TIMES v2= term
            	    {
            	    match(input,TIMES,FOLLOW_TIMES_in_expr190); 
            	    pushFollow(FOLLOW_term_in_expr194);
            	    v2=term();

            	    state._fsp--;

            	     value *= v2; 

            	    }
            	    break;
            	case 4 :
            	    // Calc.g:32:11: DIV v2= term
            	    {
            	    match(input,DIV,FOLLOW_DIV_in_expr213); 
            	    pushFollow(FOLLOW_term_in_expr217);
            	    v2=term();

            	    state._fsp--;

            	     value /= v2; 

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "expr"


    // $ANTLR start "term"
    // Calc.g:36:1: term returns [int value] : ( NUM | ID | LPAR v= expr RPAR );
    public final int term() throws RecognitionException {
        int value = 0;

        Token NUM2=null;
        Token ID3=null;
        int v = 0;


        try {
            // Calc.g:37:2: ( NUM | ID | LPAR v= expr RPAR )
            int alt5=3;
            switch ( input.LA(1) ) {
            case NUM:
                {
                alt5=1;
                }
                break;
            case ID:
                {
                alt5=2;
                }
                break;
            case LPAR:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // Calc.g:37:4: NUM
                    {
                    NUM2=(Token)match(input,NUM,FOLLOW_NUM_in_term268); 
                     value = Integer.parseInt(
                    		                         (NUM2!=null?NUM2.getText():null)); 

                    }
                    break;
                case 2 :
                    // Calc.g:39:4: ID
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_term292); 
                     int a =
                    		                         (ID3!=null?ID3.getText():null).charAt(0) - 'a'; 
                    		                       value = store[a]; 

                    }
                    break;
                case 3 :
                    // Calc.g:42:4: LPAR v= expr RPAR
                    {
                    match(input,LPAR,FOLLOW_LPAR_in_term317); 
                    pushFollow(FOLLOW_expr_in_term321);
                    v=expr();

                    state._fsp--;

                    match(input,RPAR,FOLLOW_RPAR_in_term323); 
                     value = v; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "term"

    // Delegated rules


 

    public static final BitSet FOLLOW_com_in_prog19 = new BitSet(new long[]{0x0000000000000250L});
    public static final BitSet FOLLOW_EOF_in_prog22 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PUT_in_com35 = new BitSet(new long[]{0x0000000000018080L});
    public static final BitSet FOLLOW_expr_in_com39 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EOL_in_com41 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SET_in_com54 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ID_in_com56 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ASSN_in_com58 = new BitSet(new long[]{0x0000000000018080L});
    public static final BitSet FOLLOW_expr_in_com66 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_EOL_in_com68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SCOM_in_com88 = new BitSet(new long[]{0x0000000000000650L});
    public static final BitSet FOLLOW_com_in_com90 = new BitSet(new long[]{0x0000000000000650L});
    public static final BitSet FOLLOW_FCOM_in_com93 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_expr134 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_PLUS_in_expr155 = new BitSet(new long[]{0x0000000000018080L});
    public static final BitSet FOLLOW_term_in_expr159 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_MINUS_in_expr173 = new BitSet(new long[]{0x0000000000018080L});
    public static final BitSet FOLLOW_term_in_expr177 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_TIMES_in_expr190 = new BitSet(new long[]{0x0000000000018080L});
    public static final BitSet FOLLOW_term_in_expr194 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_DIV_in_expr213 = new BitSet(new long[]{0x0000000000018080L});
    public static final BitSet FOLLOW_term_in_expr217 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_NUM_in_term268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_term292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAR_in_term317 = new BitSet(new long[]{0x0000000000018080L});
    public static final BitSet FOLLOW_expr_in_term321 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RPAR_in_term323 = new BitSet(new long[]{0x0000000000000002L});

}