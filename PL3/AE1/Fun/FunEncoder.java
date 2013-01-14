// $ANTLR 3.3 Nov 30, 2010 12:50:56 FunEncoder.g 2012-11-30 12:54:15

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class FunEncoder extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PROG", "VAR", "FORMAL", "NOFORMAL", "IFELSE", "SEQ", "PROCCALL", "FUNCCALL", "NOACTUAL", "PROC", "ID", "LPAR", "RPAR", "COLON", "DOT", "FUNC", "RETURN", "ASSN", "BOOL", "INT", "IF", "ELSE", "WHILE", "FOR", "TO", "EQ", "LT", "GT", "PLUS", "MINUS", "TIMES", "DIV", "FALSE", "TRUE", "NUM", "NOT", "DIGIT", "LETTER", "SPACE", "EOL", "COMMENT"
    };
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


        public FunEncoder(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public FunEncoder(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return FunEncoder.tokenNames; }
    public String getGrammarFileName() { return "FunEncoder.g"; }



    	private SVM obj = new SVM();

    	private int globalvaraddr = 0;
    	private int localvaraddr = 0;
    	private int currentLocale = Address.GLOBAL;

    	private SymbolTable<Address> addrTable =
    	   new SymbolTable<Address>();

    	private void predefine () {
    	// Add predefined procedures to the address table.
    		addrTable.put("read",
    		   new Address(SVM.READOFFSET, Address.CODE));
    		addrTable.put("write",
    		   new Address(SVM.WRITEOFFSET, Address.CODE));
    	}




    // $ANTLR start "program"
    // FunEncoder.g:48:1: program returns [SVM objectprogram] : ^( PROG ( var_decl )* ( proc_decl )+ ) ;
    public final SVM program() throws RecognitionException {
        SVM objectprogram = null;

        try {
            // FunEncoder.g:49:2: ( ^( PROG ( var_decl )* ( proc_decl )+ ) )
            // FunEncoder.g:49:4: ^( PROG ( var_decl )* ( proc_decl )+ )
            {
            match(input,PROG,FOLLOW_PROG_in_program66); 

             predefine();
            				

            match(input, Token.DOWN, null); 
            // FunEncoder.g:52:5: ( var_decl )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==VAR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // FunEncoder.g:52:5: var_decl
            	    {
            	    pushFollow(FOLLOW_var_decl_in_program78);
            	    var_decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             int calladdr =
            				    obj.currentOffset();
            				  obj.emit12(SVM.CALL, 0);
            				  obj.emit1(SVM.HALT);
            				
            // FunEncoder.g:58:5: ( proc_decl )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==PROC||LA2_0==FUNC) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // FunEncoder.g:58:5: proc_decl
            	    {
            	    pushFollow(FOLLOW_proc_decl_in_program91);
            	    proc_decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            match(input, Token.UP, null); 
             int mainaddr =
            				    addrTable.get("main").offset;
            				  obj.patch12(calladdr, mainaddr);
            				  objectprogram = obj;
            				

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return objectprogram;
    }
    // $ANTLR end "program"


    // $ANTLR start "proc_decl"
    // FunEncoder.g:69:1: proc_decl : ( ^( PROC ID formal ( var_decl )* com ) | ^( FUNC type ID formal ( var_decl )* com expr ) );
    public final void proc_decl() throws RecognitionException {
        CommonTree ID1=null;
        CommonTree ID2=null;

        try {
            // FunEncoder.g:70:2: ( ^( PROC ID formal ( var_decl )* com ) | ^( FUNC type ID formal ( var_decl )* com expr ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==PROC) ) {
                alt5=1;
            }
            else if ( (LA5_0==FUNC) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // FunEncoder.g:70:4: ^( PROC ID formal ( var_decl )* com )
                    {
                    match(input,PROC,FOLLOW_PROC_in_proc_decl114); 

                    match(input, Token.DOWN, null); 
                    ID1=(CommonTree)match(input,ID,FOLLOW_ID_in_proc_decl120); 
                     String id = (ID1!=null?ID1.getText():null);
                    				  Address procaddr = new Address(
                    				    obj.currentOffset(), Address.CODE);
                    				  addrTable.put(id, procaddr);
                    				  addrTable.enterLocalScope();
                    				  currentLocale = Address.LOCAL;
                    				  localvaraddr = 2;
                    				  // ... allows 2 words for link data
                    				
                    pushFollow(FOLLOW_formal_in_proc_decl132);
                    formal();

                    state._fsp--;

                    // FunEncoder.g:82:5: ( var_decl )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==VAR) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // FunEncoder.g:82:5: var_decl
                    	    {
                    	    pushFollow(FOLLOW_var_decl_in_proc_decl138);
                    	    var_decl();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    pushFollow(FOLLOW_com_in_proc_decl145);
                    com();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit11(SVM.RETURN, 0);
                    				  addrTable.exitLocalScope();
                    				  currentLocale = Address.GLOBAL;
                    				

                    }
                    break;
                case 2 :
                    // FunEncoder.g:88:4: ^( FUNC type ID formal ( var_decl )* com expr )
                    {
                    match(input,FUNC,FOLLOW_FUNC_in_proc_decl158); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_type_in_proc_decl164);
                    type();

                    state._fsp--;

                    ID2=(CommonTree)match(input,ID,FOLLOW_ID_in_proc_decl170); 
                     String id = (ID2!=null?ID2.getText():null);
                    				  Address procaddr = new Address(
                    				    obj.currentOffset(), Address.CODE);
                    				  addrTable.put(id, procaddr);
                    				  addrTable.enterLocalScope();
                    				  currentLocale = Address.LOCAL;
                    				  localvaraddr = 2;
                    				  // ... allows 2 words for link data
                    				
                    pushFollow(FOLLOW_formal_in_proc_decl182);
                    formal();

                    state._fsp--;

                    // FunEncoder.g:101:5: ( var_decl )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==VAR) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // FunEncoder.g:101:5: var_decl
                    	    {
                    	    pushFollow(FOLLOW_var_decl_in_proc_decl188);
                    	    var_decl();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    pushFollow(FOLLOW_com_in_proc_decl195);
                    com();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_proc_decl201);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit11(SVM.RETURN, 1);
                    				  addrTable.exitLocalScope();
                    				  currentLocale = Address.GLOBAL;
                    				

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
    // $ANTLR end "proc_decl"


    // $ANTLR start "formal"
    // FunEncoder.g:110:1: formal : ( ^( FORMAL type ID ) | NOFORMAL );
    public final void formal() throws RecognitionException {
        CommonTree ID3=null;

        try {
            // FunEncoder.g:111:2: ( ^( FORMAL type ID ) | NOFORMAL )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==FORMAL) ) {
                alt6=1;
            }
            else if ( (LA6_0==NOFORMAL) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // FunEncoder.g:111:4: ^( FORMAL type ID )
                    {
                    match(input,FORMAL,FOLLOW_FORMAL_in_formal220); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_type_in_formal226);
                    type();

                    state._fsp--;

                    ID3=(CommonTree)match(input,ID,FOLLOW_ID_in_formal232); 

                    match(input, Token.UP, null); 
                     String id = (ID3!=null?ID3.getText():null);
                    				  addrTable.put(id, new Address(
                    				    localvaraddr++, Address.LOCAL));
                    				  obj.emit11(SVM.COPYARG, 1); 
                    				

                    }
                    break;
                case 2 :
                    // FunEncoder.g:119:4: NOFORMAL
                    {
                    match(input,NOFORMAL,FOLLOW_NOFORMAL_in_formal244); 

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
    // $ANTLR end "formal"


    // $ANTLR start "var_decl"
    // FunEncoder.g:122:1: var_decl : ^( VAR type ID expr ) ;
    public final void var_decl() throws RecognitionException {
        CommonTree ID4=null;

        try {
            // FunEncoder.g:123:2: ( ^( VAR type ID expr ) )
            // FunEncoder.g:123:4: ^( VAR type ID expr )
            {
            match(input,VAR,FOLLOW_VAR_in_var_decl256); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_var_decl262);
            type();

            state._fsp--;

            ID4=(CommonTree)match(input,ID,FOLLOW_ID_in_var_decl268); 
            pushFollow(FOLLOW_expr_in_var_decl274);
            expr();

            state._fsp--;


            match(input, Token.UP, null); 
             String id = (ID4!=null?ID4.getText():null);
            				  switch (currentLocale) {
            				    case Address.LOCAL:
            				      addrTable.put(id, new Address(
            				        localvaraddr++, Address.LOCAL));
            				      break;
            				    case Address.GLOBAL:
            				      addrTable.put(id, new Address(
            				        globalvaraddr++, Address.GLOBAL));
            				  }
            				

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
    // $ANTLR end "var_decl"


    // $ANTLR start "type"
    // FunEncoder.g:140:1: type : ( BOOL | INT );
    public final void type() throws RecognitionException {
        try {
            // FunEncoder.g:141:2: ( BOOL | INT )
            // FunEncoder.g:
            {
            if ( (input.LA(1)>=BOOL && input.LA(1)<=INT) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


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
    // $ANTLR end "type"


    // $ANTLR start "com"
    // FunEncoder.g:148:1: com : ( ^( ASSN ID expr ) | ^( PROCCALL ID expr ) | ^( IF expr com ) | ^( IFELSE expr com com ) | ^( WHILE expr com ) | ^( FOR ID expr expr com ) | ^( SEQ ( com )* ) );
    public final void com() throws RecognitionException {
        CommonTree ID5=null;
        CommonTree ID6=null;
        CommonTree ID7=null;

        try {
            // FunEncoder.g:149:2: ( ^( ASSN ID expr ) | ^( PROCCALL ID expr ) | ^( IF expr com ) | ^( IFELSE expr com com ) | ^( WHILE expr com ) | ^( FOR ID expr expr com ) | ^( SEQ ( com )* ) )
            int alt8=7;
            switch ( input.LA(1) ) {
            case ASSN:
                {
                alt8=1;
                }
                break;
            case PROCCALL:
                {
                alt8=2;
                }
                break;
            case IF:
                {
                alt8=3;
                }
                break;
            case IFELSE:
                {
                alt8=4;
                }
                break;
            case WHILE:
                {
                alt8=5;
                }
                break;
            case FOR:
                {
                alt8=6;
                }
                break;
            case SEQ:
                {
                alt8=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // FunEncoder.g:149:4: ^( ASSN ID expr )
                    {
                    match(input,ASSN,FOLLOW_ASSN_in_com312); 

                    match(input, Token.DOWN, null); 
                    ID5=(CommonTree)match(input,ID,FOLLOW_ID_in_com314); 
                    pushFollow(FOLLOW_expr_in_com316);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     String id = (ID5!=null?ID5.getText():null);
                    				  Address varaddr = addrTable.get(id);
                    				  switch (varaddr.locale) {
                    				    case Address.GLOBAL:
                    				      obj.emit12(SVM.STOREG,
                    				        varaddr.offset);
                    				      break;
                    				    case Address.LOCAL:
                    				      obj.emit12(SVM.STOREL,
                    				        varaddr.offset);
                    				  }
                    				

                    }
                    break;
                case 2 :
                    // FunEncoder.g:162:4: ^( PROCCALL ID expr )
                    {
                    match(input,PROCCALL,FOLLOW_PROCCALL_in_com329); 

                    match(input, Token.DOWN, null); 
                    ID6=(CommonTree)match(input,ID,FOLLOW_ID_in_com331); 
                    pushFollow(FOLLOW_expr_in_com333);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     String id = (ID6!=null?ID6.getText():null);
                    				  Address procaddr = addrTable.get(id);
                    				  // Assume procaddr.locale == CODE.
                    				  obj.emit12(SVM.CALL,
                    				    procaddr.offset);
                    				

                    }
                    break;
                case 3 :
                    // FunEncoder.g:169:4: ^( IF expr com )
                    {
                    match(input,IF,FOLLOW_IF_in_com346); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_com352);
                    expr();

                    state._fsp--;

                     int condaddr = obj.currentOffset();
                    				  obj.emit12(SVM.JUMPF, 0);
                    				
                    pushFollow(FOLLOW_com_in_com364);
                    com();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     int exitaddr = obj.currentOffset();
                    				  obj.patch12(condaddr, exitaddr);
                    				

                    }
                    break;
                case 4 :
                    // FunEncoder.g:178:4: ^( IFELSE expr com com )
                    {
                    match(input,IFELSE,FOLLOW_IFELSE_in_com377); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_com383);
                    expr();

                    state._fsp--;

                     int condaddr = obj.currentOffset();
                    				  obj.emit12(SVM.JUMPF, 0);
                    				
                    pushFollow(FOLLOW_com_in_com395);
                    com();

                    state._fsp--;

                     int jumpaddr = obj.currentOffset();
                    				  obj.emit12(SVM.JUMP, 0);
                    				  int elseaddr = obj.currentOffset();
                    				  obj.patch12(condaddr, elseaddr);
                    				
                    pushFollow(FOLLOW_com_in_com407);
                    com();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     int exitaddr = obj.currentOffset();
                    				  obj.patch12(jumpaddr, exitaddr);
                    				

                    }
                    break;
                case 5 :
                    // FunEncoder.g:193:4: ^( WHILE expr com )
                    {
                    match(input,WHILE,FOLLOW_WHILE_in_com420); 

                     int startaddr = obj.currentOffset();
                    				

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_com433);
                    expr();

                    state._fsp--;

                     int condaddr = obj.currentOffset();
                    				  obj.emit12(SVM.JUMPF, 0);
                    				
                    pushFollow(FOLLOW_com_in_com446);
                    com();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit12(SVM.JUMP, startaddr);
                    				  int exitaddr = obj.currentOffset();
                    				  obj.patch12(condaddr, exitaddr);
                    				

                    }
                    break;
                case 6 :
                    // FunEncoder.g:205:9: ^( FOR ID expr expr com )
                    {
                    match(input,FOR,FOLLOW_FOR_in_com464); 

                    match(input, Token.DOWN, null); 
                    ID7=(CommonTree)match(input,ID,FOLLOW_ID_in_com466); 
                    pushFollow(FOLLOW_expr_in_com468);
                    expr();

                    state._fsp--;

                     String id = (ID7!=null?ID7.getText():null);                 //EXTENSION
                                      Address varaddr = addrTable.get(id);  //EXTENSION
                                      obj.emit12(SVM.STOREL,varaddr.offset);//EXTENSION
                                      int beginLoop = obj.currentOffset();  //EXTENSION
                                      obj.emit12(SVM.LOADL,varaddr.offset); //EXTENSION
                                    
                    pushFollow(FOLLOW_expr_in_com574);
                    expr();

                    state._fsp--;

                                                           //EXTENSION
                                      obj.emit1(SVM.CMPGT);                 //EXTENSION
                                      int exitJump = obj.currentOffset();   //EXTENSION
                                      obj.emit12(SVM.JUMPT,0);              //EXTENSION
                                    
                    pushFollow(FOLLOW_com_in_com685);
                    com();

                    state._fsp--;


                    match(input, Token.UP, null); 
                                                           //EXTENSION
                                      obj.emit12(SVM.LOADL,varaddr.offset); //EXTENSION
                                      obj.emit12(SVM.LOADLIT,1);            //EXTENSION
                                      obj.emit1(SVM.ADD);                   //EXTENSION
                                      obj.emit12(SVM.STOREL,varaddr.offset);
                                      obj.emit12(SVM.JUMP,beginLoop);       //EXTENSION
                                      int exitaddr = obj.currentOffset();   //EXTENSION
                                      obj.patch12(exitJump,exitaddr);       //EXTENSION
                                    

                    }
                    break;
                case 7 :
                    // FunEncoder.g:229:4: ^( SEQ ( com )* )
                    {
                    match(input,SEQ,FOLLOW_SEQ_in_com802); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // FunEncoder.g:229:10: ( com )*
                        loop7:
                        do {
                            int alt7=2;
                            int LA7_0 = input.LA(1);

                            if ( ((LA7_0>=IFELSE && LA7_0<=PROCCALL)||LA7_0==ASSN||LA7_0==IF||(LA7_0>=WHILE && LA7_0<=FOR)) ) {
                                alt7=1;
                            }


                            switch (alt7) {
                        	case 1 :
                        	    // FunEncoder.g:229:10: com
                        	    {
                        	    pushFollow(FOLLOW_com_in_com804);
                        	    com();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop7;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }

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
    // FunEncoder.g:235:1: expr : ( FALSE | TRUE | NUM | ID | ^( FUNCCALL ID expr ) | ^( EQ expr expr ) | ^( LT expr expr ) | ^( GT expr expr ) | ^( PLUS expr expr ) | ^( MINUS expr expr ) | ^( TIMES expr expr ) | ^( DIV expr expr ) | ^( NOT expr ) | NOACTUAL );
    public final void expr() throws RecognitionException {
        CommonTree NUM8=null;
        CommonTree ID9=null;
        CommonTree ID10=null;

        try {
            // FunEncoder.g:236:2: ( FALSE | TRUE | NUM | ID | ^( FUNCCALL ID expr ) | ^( EQ expr expr ) | ^( LT expr expr ) | ^( GT expr expr ) | ^( PLUS expr expr ) | ^( MINUS expr expr ) | ^( TIMES expr expr ) | ^( DIV expr expr ) | ^( NOT expr ) | NOACTUAL )
            int alt9=14;
            switch ( input.LA(1) ) {
            case FALSE:
                {
                alt9=1;
                }
                break;
            case TRUE:
                {
                alt9=2;
                }
                break;
            case NUM:
                {
                alt9=3;
                }
                break;
            case ID:
                {
                alt9=4;
                }
                break;
            case FUNCCALL:
                {
                alt9=5;
                }
                break;
            case EQ:
                {
                alt9=6;
                }
                break;
            case LT:
                {
                alt9=7;
                }
                break;
            case GT:
                {
                alt9=8;
                }
                break;
            case PLUS:
                {
                alt9=9;
                }
                break;
            case MINUS:
                {
                alt9=10;
                }
                break;
            case TIMES:
                {
                alt9=11;
                }
                break;
            case DIV:
                {
                alt9=12;
                }
                break;
            case NOT:
                {
                alt9=13;
                }
                break;
            case NOACTUAL:
                {
                alt9=14;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // FunEncoder.g:236:4: FALSE
                    {
                    match(input,FALSE,FOLLOW_FALSE_in_expr820); 
                     obj.emit12(SVM.LOADLIT, 0); 

                    }
                    break;
                case 2 :
                    // FunEncoder.g:238:4: TRUE
                    {
                    match(input,TRUE,FOLLOW_TRUE_in_expr831); 
                     obj.emit12(SVM.LOADLIT, 1); 

                    }
                    break;
                case 3 :
                    // FunEncoder.g:240:4: NUM
                    {
                    NUM8=(CommonTree)match(input,NUM,FOLLOW_NUM_in_expr842); 
                     int value =
                    				    Integer.parseInt((NUM8!=null?NUM8.getText():null));
                    				  obj.emit12(SVM.LOADLIT, value);
                    				

                    }
                    break;
                case 4 :
                    // FunEncoder.g:245:4: ID
                    {
                    ID9=(CommonTree)match(input,ID,FOLLOW_ID_in_expr853); 
                     String id = (ID9!=null?ID9.getText():null);
                    				  Address varaddr = addrTable.get(id);
                    				  switch (varaddr.locale) {
                    				    case Address.GLOBAL:
                    				      obj.emit12(SVM.LOADG,
                    				        varaddr.offset);
                    				      break;
                    				    case Address.LOCAL:
                    				      obj.emit12(SVM.LOADL,
                    				        varaddr.offset);
                    				  }
                    				

                    }
                    break;
                case 5 :
                    // FunEncoder.g:258:4: ^( FUNCCALL ID expr )
                    {
                    match(input,FUNCCALL,FOLLOW_FUNCCALL_in_expr865); 

                    match(input, Token.DOWN, null); 
                    ID10=(CommonTree)match(input,ID,FOLLOW_ID_in_expr867); 
                    pushFollow(FOLLOW_expr_in_expr869);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     String id = (ID10!=null?ID10.getText():null);
                    				  Address funcaddr = addrTable.get(id);
                    				  // Assume funcaddr.locale == CODE ...
                    				  obj.emit12(SVM.CALL,
                    				    funcaddr.offset);
                    				

                    }
                    break;
                case 6 :
                    // FunEncoder.g:265:4: ^( EQ expr expr )
                    {
                    match(input,EQ,FOLLOW_EQ_in_expr882); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr884);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr886);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit1(SVM.CMPEQ); 

                    }
                    break;
                case 7 :
                    // FunEncoder.g:267:4: ^( LT expr expr )
                    {
                    match(input,LT,FOLLOW_LT_in_expr899); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr901);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr903);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit1(SVM.CMPLT); 

                    }
                    break;
                case 8 :
                    // FunEncoder.g:269:4: ^( GT expr expr )
                    {
                    match(input,GT,FOLLOW_GT_in_expr916); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr918);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr920);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit1(SVM.CMPGT); 

                    }
                    break;
                case 9 :
                    // FunEncoder.g:271:4: ^( PLUS expr expr )
                    {
                    match(input,PLUS,FOLLOW_PLUS_in_expr933); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr935);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr937);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit1(SVM.ADD); 

                    }
                    break;
                case 10 :
                    // FunEncoder.g:273:4: ^( MINUS expr expr )
                    {
                    match(input,MINUS,FOLLOW_MINUS_in_expr950); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr952);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr954);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit1(SVM.SUB); 

                    }
                    break;
                case 11 :
                    // FunEncoder.g:275:4: ^( TIMES expr expr )
                    {
                    match(input,TIMES,FOLLOW_TIMES_in_expr967); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr969);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr971);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit1(SVM.MUL); 

                    }
                    break;
                case 12 :
                    // FunEncoder.g:277:4: ^( DIV expr expr )
                    {
                    match(input,DIV,FOLLOW_DIV_in_expr984); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr986);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr988);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit1(SVM.DIV); 

                    }
                    break;
                case 13 :
                    // FunEncoder.g:279:4: ^( NOT expr )
                    {
                    match(input,NOT,FOLLOW_NOT_in_expr1001); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr1003);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     obj.emit1(SVM.INV); 

                    }
                    break;
                case 14 :
                    // FunEncoder.g:281:4: NOACTUAL
                    {
                    match(input,NOACTUAL,FOLLOW_NOACTUAL_in_expr1015); 
                     

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
    // $ANTLR end "expr"

    // Delegated rules


 

    public static final BitSet FOLLOW_PROG_in_program66 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_var_decl_in_program78 = new BitSet(new long[]{0x0000000000082020L});
    public static final BitSet FOLLOW_proc_decl_in_program91 = new BitSet(new long[]{0x0000000000082008L});
    public static final BitSet FOLLOW_PROC_in_proc_decl114 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_proc_decl120 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_formal_in_proc_decl132 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_var_decl_in_proc_decl138 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_com_in_proc_decl145 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNC_in_proc_decl158 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_proc_decl164 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_proc_decl170 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_formal_in_proc_decl182 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_var_decl_in_proc_decl188 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_com_in_proc_decl195 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_proc_decl201 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FORMAL_in_formal220 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_formal226 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_formal232 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOFORMAL_in_formal244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_var_decl256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_var_decl262 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_var_decl268 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_var_decl274 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_type0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASSN_in_com312 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_com314 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_com316 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROCCALL_in_com329 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_com331 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_com333 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IF_in_com346 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_com352 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_com_in_com364 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IFELSE_in_com377 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_com383 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_com_in_com395 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_com_in_com407 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHILE_in_com420 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_com433 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_com_in_com446 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FOR_in_com464 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_com466 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_com468 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_com574 = new BitSet(new long[]{0x000000000D282720L});
    public static final BitSet FOLLOW_com_in_com685 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SEQ_in_com802 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_com_in_com804 = new BitSet(new long[]{0x000000000D282728L});
    public static final BitSet FOLLOW_FALSE_in_expr820 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_expr831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_expr842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expr853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNCCALL_in_expr865 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_expr867 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_expr869 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EQ_in_expr882 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr884 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_expr886 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_expr899 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr901 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_expr903 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_expr916 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr918 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_expr920 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PLUS_in_expr933 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr935 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_expr937 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_expr950 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr952 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_expr954 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMES_in_expr967 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr969 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_expr971 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_expr984 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr986 = new BitSet(new long[]{0x000000FFE0005800L});
    public static final BitSet FOLLOW_expr_in_expr988 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_in_expr1001 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr1003 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOACTUAL_in_expr1015 = new BitSet(new long[]{0x0000000000000002L});

}