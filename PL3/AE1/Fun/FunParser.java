// $ANTLR 3.3 Nov 30, 2010 12:50:56 Fun.g 2012-11-16 18:57:08

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class FunParser extends Parser {
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


        public FunParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public FunParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return FunParser.tokenNames; }
    public String getGrammarFileName() { return "Fun.g"; }


    public static class program_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // Fun.g:38:1: program : ( var_decl )* ( proc_decl )+ EOF -> ^( PROG ( var_decl )* ( proc_decl )+ ) ;
    public final FunParser.program_return program() throws RecognitionException {
        FunParser.program_return retval = new FunParser.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token EOF3=null;
        FunParser.var_decl_return var_decl1 = null;

        FunParser.proc_decl_return proc_decl2 = null;


        CommonTree EOF3_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_proc_decl=new RewriteRuleSubtreeStream(adaptor,"rule proc_decl");
        RewriteRuleSubtreeStream stream_var_decl=new RewriteRuleSubtreeStream(adaptor,"rule var_decl");
        try {
            // Fun.g:39:2: ( ( var_decl )* ( proc_decl )+ EOF -> ^( PROG ( var_decl )* ( proc_decl )+ ) )
            // Fun.g:39:4: ( var_decl )* ( proc_decl )+ EOF
            {
            // Fun.g:39:4: ( var_decl )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=BOOL && LA1_0<=INT)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Fun.g:39:4: var_decl
            	    {
            	    pushFollow(FOLLOW_var_decl_in_program100);
            	    var_decl1=var_decl();

            	    state._fsp--;

            	    stream_var_decl.add(var_decl1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // Fun.g:39:14: ( proc_decl )+
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
            	    // Fun.g:39:14: proc_decl
            	    {
            	    pushFollow(FOLLOW_proc_decl_in_program103);
            	    proc_decl2=proc_decl();

            	    state._fsp--;

            	    stream_proc_decl.add(proc_decl2.getTree());

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

            EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_program106);  
            stream_EOF.add(EOF3);



            // AST REWRITE
            // elements: var_decl, proc_decl
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 39:31: -> ^( PROG ( var_decl )* ( proc_decl )+ )
            {
                // Fun.g:39:34: ^( PROG ( var_decl )* ( proc_decl )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROG, "PROG"), root_1);

                // Fun.g:40:35: ( var_decl )*
                while ( stream_var_decl.hasNext() ) {
                    adaptor.addChild(root_1, stream_var_decl.nextTree());

                }
                stream_var_decl.reset();
                if ( !(stream_proc_decl.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_proc_decl.hasNext() ) {
                    adaptor.addChild(root_1, stream_proc_decl.nextTree());

                }
                stream_proc_decl.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "program"

    public static class proc_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "proc_decl"
    // Fun.g:47:1: proc_decl : ( PROC ID LPAR formal RPAR COLON ( var_decl )* seq_com DOT -> ^( PROC ID formal ( var_decl )* seq_com ) | FUNC type ID LPAR formal RPAR COLON ( var_decl )* seq_com RETURN expr DOT -> ^( FUNC type ID formal ( var_decl )* seq_com expr ) );
    public final FunParser.proc_decl_return proc_decl() throws RecognitionException {
        FunParser.proc_decl_return retval = new FunParser.proc_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token PROC4=null;
        Token ID5=null;
        Token LPAR6=null;
        Token RPAR8=null;
        Token COLON9=null;
        Token DOT12=null;
        Token FUNC13=null;
        Token ID15=null;
        Token LPAR16=null;
        Token RPAR18=null;
        Token COLON19=null;
        Token RETURN22=null;
        Token DOT24=null;
        FunParser.formal_return formal7 = null;

        FunParser.var_decl_return var_decl10 = null;

        FunParser.seq_com_return seq_com11 = null;

        FunParser.type_return type14 = null;

        FunParser.formal_return formal17 = null;

        FunParser.var_decl_return var_decl20 = null;

        FunParser.seq_com_return seq_com21 = null;

        FunParser.expr_return expr23 = null;


        CommonTree PROC4_tree=null;
        CommonTree ID5_tree=null;
        CommonTree LPAR6_tree=null;
        CommonTree RPAR8_tree=null;
        CommonTree COLON9_tree=null;
        CommonTree DOT12_tree=null;
        CommonTree FUNC13_tree=null;
        CommonTree ID15_tree=null;
        CommonTree LPAR16_tree=null;
        CommonTree RPAR18_tree=null;
        CommonTree COLON19_tree=null;
        CommonTree RETURN22_tree=null;
        CommonTree DOT24_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_FUNC=new RewriteRuleTokenStream(adaptor,"token FUNC");
        RewriteRuleTokenStream stream_RETURN=new RewriteRuleTokenStream(adaptor,"token RETURN");
        RewriteRuleTokenStream stream_PROC=new RewriteRuleTokenStream(adaptor,"token PROC");
        RewriteRuleSubtreeStream stream_formal=new RewriteRuleSubtreeStream(adaptor,"rule formal");
        RewriteRuleSubtreeStream stream_seq_com=new RewriteRuleSubtreeStream(adaptor,"rule seq_com");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        RewriteRuleSubtreeStream stream_var_decl=new RewriteRuleSubtreeStream(adaptor,"rule var_decl");
        try {
            // Fun.g:48:2: ( PROC ID LPAR formal RPAR COLON ( var_decl )* seq_com DOT -> ^( PROC ID formal ( var_decl )* seq_com ) | FUNC type ID LPAR formal RPAR COLON ( var_decl )* seq_com RETURN expr DOT -> ^( FUNC type ID formal ( var_decl )* seq_com expr ) )
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
                    // Fun.g:48:4: PROC ID LPAR formal RPAR COLON ( var_decl )* seq_com DOT
                    {
                    PROC4=(Token)match(input,PROC,FOLLOW_PROC_in_proc_decl203);  
                    stream_PROC.add(PROC4);

                    ID5=(Token)match(input,ID,FOLLOW_ID_in_proc_decl205);  
                    stream_ID.add(ID5);

                    LPAR6=(Token)match(input,LPAR,FOLLOW_LPAR_in_proc_decl211);  
                    stream_LPAR.add(LPAR6);

                    pushFollow(FOLLOW_formal_in_proc_decl213);
                    formal7=formal();

                    state._fsp--;

                    stream_formal.add(formal7.getTree());
                    RPAR8=(Token)match(input,RPAR,FOLLOW_RPAR_in_proc_decl215);  
                    stream_RPAR.add(RPAR8);

                    COLON9=(Token)match(input,COLON,FOLLOW_COLON_in_proc_decl217);  
                    stream_COLON.add(COLON9);

                    // Fun.g:50:5: ( var_decl )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>=BOOL && LA3_0<=INT)) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // Fun.g:50:5: var_decl
                    	    {
                    	    pushFollow(FOLLOW_var_decl_in_proc_decl223);
                    	    var_decl10=var_decl();

                    	    state._fsp--;

                    	    stream_var_decl.add(var_decl10.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    pushFollow(FOLLOW_seq_com_in_proc_decl226);
                    seq_com11=seq_com();

                    state._fsp--;

                    stream_seq_com.add(seq_com11.getTree());
                    DOT12=(Token)match(input,DOT,FOLLOW_DOT_in_proc_decl228);  
                    stream_DOT.add(DOT12);



                    // AST REWRITE
                    // elements: var_decl, ID, seq_com, PROC, formal
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 50:30: -> ^( PROC ID formal ( var_decl )* seq_com )
                    {
                        // Fun.g:50:33: ^( PROC ID formal ( var_decl )* seq_com )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_PROC.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_formal.nextTree());
                        // Fun.g:52:35: ( var_decl )*
                        while ( stream_var_decl.hasNext() ) {
                            adaptor.addChild(root_1, stream_var_decl.nextTree());

                        }
                        stream_var_decl.reset();
                        adaptor.addChild(root_1, stream_seq_com.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // Fun.g:53:4: FUNC type ID LPAR formal RPAR COLON ( var_decl )* seq_com RETURN expr DOT
                    {
                    FUNC13=(Token)match(input,FUNC,FOLLOW_FUNC_in_proc_decl320);  
                    stream_FUNC.add(FUNC13);

                    pushFollow(FOLLOW_type_in_proc_decl322);
                    type14=type();

                    state._fsp--;

                    stream_type.add(type14.getTree());
                    ID15=(Token)match(input,ID,FOLLOW_ID_in_proc_decl324);  
                    stream_ID.add(ID15);

                    LPAR16=(Token)match(input,LPAR,FOLLOW_LPAR_in_proc_decl330);  
                    stream_LPAR.add(LPAR16);

                    pushFollow(FOLLOW_formal_in_proc_decl332);
                    formal17=formal();

                    state._fsp--;

                    stream_formal.add(formal17.getTree());
                    RPAR18=(Token)match(input,RPAR,FOLLOW_RPAR_in_proc_decl334);  
                    stream_RPAR.add(RPAR18);

                    COLON19=(Token)match(input,COLON,FOLLOW_COLON_in_proc_decl336);  
                    stream_COLON.add(COLON19);

                    // Fun.g:55:5: ( var_decl )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>=BOOL && LA4_0<=INT)) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // Fun.g:55:5: var_decl
                    	    {
                    	    pushFollow(FOLLOW_var_decl_in_proc_decl342);
                    	    var_decl20=var_decl();

                    	    state._fsp--;

                    	    stream_var_decl.add(var_decl20.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    pushFollow(FOLLOW_seq_com_in_proc_decl345);
                    seq_com21=seq_com();

                    state._fsp--;

                    stream_seq_com.add(seq_com21.getTree());
                    RETURN22=(Token)match(input,RETURN,FOLLOW_RETURN_in_proc_decl351);  
                    stream_RETURN.add(RETURN22);

                    pushFollow(FOLLOW_expr_in_proc_decl353);
                    expr23=expr();

                    state._fsp--;

                    stream_expr.add(expr23.getTree());
                    DOT24=(Token)match(input,DOT,FOLLOW_DOT_in_proc_decl355);  
                    stream_DOT.add(DOT24);



                    // AST REWRITE
                    // elements: seq_com, var_decl, type, expr, formal, ID, FUNC
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 56:30: -> ^( FUNC type ID formal ( var_decl )* seq_com expr )
                    {
                        // Fun.g:56:33: ^( FUNC type ID formal ( var_decl )* seq_com expr )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_FUNC.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_type.nextTree());
                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_formal.nextTree());
                        // Fun.g:58:35: ( var_decl )*
                        while ( stream_var_decl.hasNext() ) {
                            adaptor.addChild(root_1, stream_var_decl.nextTree());

                        }
                        stream_var_decl.reset();
                        adaptor.addChild(root_1, stream_seq_com.nextTree());
                        adaptor.addChild(root_1, stream_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "proc_decl"

    public static class formal_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "formal"
    // Fun.g:62:1: formal : ( type ID -> ^( FORMAL type ID ) | -> NOFORMAL );
    public final FunParser.formal_return formal() throws RecognitionException {
        FunParser.formal_return retval = new FunParser.formal_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token ID26=null;
        FunParser.type_return type25 = null;


        CommonTree ID26_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // Fun.g:63:2: ( type ID -> ^( FORMAL type ID ) | -> NOFORMAL )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0>=BOOL && LA6_0<=INT)) ) {
                alt6=1;
            }
            else if ( (LA6_0==RPAR) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // Fun.g:63:4: type ID
                    {
                    pushFollow(FOLLOW_type_in_formal497);
                    type25=type();

                    state._fsp--;

                    stream_type.add(type25.getTree());
                    ID26=(Token)match(input,ID,FOLLOW_ID_in_formal499);  
                    stream_ID.add(ID26);



                    // AST REWRITE
                    // elements: type, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 63:31: -> ^( FORMAL type ID )
                    {
                        // Fun.g:63:34: ^( FORMAL type ID )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FORMAL, "FORMAL"), root_1);

                        adaptor.addChild(root_1, stream_type.nextTree());
                        adaptor.addChild(root_1, stream_ID.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // Fun.g:64:31: 
                    {

                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 64:31: -> NOFORMAL
                    {
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(NOFORMAL, "NOFORMAL"));

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "formal"

    public static class var_decl_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "var_decl"
    // Fun.g:67:1: var_decl : type ID ASSN expr -> ^( VAR type ID expr ) ;
    public final FunParser.var_decl_return var_decl() throws RecognitionException {
        FunParser.var_decl_return retval = new FunParser.var_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token ID28=null;
        Token ASSN29=null;
        FunParser.type_return type27 = null;

        FunParser.expr_return expr30 = null;


        CommonTree ID28_tree=null;
        CommonTree ASSN29_tree=null;
        RewriteRuleTokenStream stream_ASSN=new RewriteRuleTokenStream(adaptor,"token ASSN");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // Fun.g:68:2: ( type ID ASSN expr -> ^( VAR type ID expr ) )
            // Fun.g:68:4: type ID ASSN expr
            {
            pushFollow(FOLLOW_type_in_var_decl573);
            type27=type();

            state._fsp--;

            stream_type.add(type27.getTree());
            ID28=(Token)match(input,ID,FOLLOW_ID_in_var_decl575);  
            stream_ID.add(ID28);

            ASSN29=(Token)match(input,ASSN,FOLLOW_ASSN_in_var_decl577);  
            stream_ASSN.add(ASSN29);

            pushFollow(FOLLOW_expr_in_var_decl579);
            expr30=expr();

            state._fsp--;

            stream_expr.add(expr30.getTree());


            // AST REWRITE
            // elements: ID, expr, type
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 68:31: -> ^( VAR type ID expr )
            {
                // Fun.g:68:34: ^( VAR type ID expr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(VAR, "VAR"), root_1);

                adaptor.addChild(root_1, stream_type.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "var_decl"

    public static class type_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // Fun.g:71:1: type : ( BOOL -> BOOL | INT -> INT );
    public final FunParser.type_return type() throws RecognitionException {
        FunParser.type_return retval = new FunParser.type_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token BOOL31=null;
        Token INT32=null;

        CommonTree BOOL31_tree=null;
        CommonTree INT32_tree=null;
        RewriteRuleTokenStream stream_INT=new RewriteRuleTokenStream(adaptor,"token INT");
        RewriteRuleTokenStream stream_BOOL=new RewriteRuleTokenStream(adaptor,"token BOOL");

        try {
            // Fun.g:72:2: ( BOOL -> BOOL | INT -> INT )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==BOOL) ) {
                alt7=1;
            }
            else if ( (LA7_0==INT) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // Fun.g:72:4: BOOL
                    {
                    BOOL31=(Token)match(input,BOOL,FOLLOW_BOOL_in_type611);  
                    stream_BOOL.add(BOOL31);



                    // AST REWRITE
                    // elements: BOOL
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 72:31: -> BOOL
                    {
                        adaptor.addChild(root_0, stream_BOOL.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // Fun.g:73:4: INT
                    {
                    INT32=(Token)match(input,INT,FOLLOW_INT_in_type642);  
                    stream_INT.add(INT32);



                    // AST REWRITE
                    // elements: INT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 73:31: -> INT
                    {
                        adaptor.addChild(root_0, stream_INT.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "type"

    public static class com_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "com"
    // Fun.g:79:1: com : ( ID ASSN expr -> ^( ASSN ID expr ) | ID LPAR actual RPAR -> ^( PROCCALL ID actual ) | IF expr COLON c1= seq_com ( DOT -> ^( IF expr $c1) | ELSE COLON c2= seq_com DOT -> ^( IFELSE expr $c1 $c2) ) | WHILE expr COLON seq_com DOT -> ^( WHILE expr seq_com ) | FOR ID ASSN e1= expr TO e2= expr COLON seq_com DOT -> ^( FOR ID $e1 $e2 seq_com ) );
    public final FunParser.com_return com() throws RecognitionException {
        FunParser.com_return retval = new FunParser.com_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token ID33=null;
        Token ASSN34=null;
        Token ID36=null;
        Token LPAR37=null;
        Token RPAR39=null;
        Token IF40=null;
        Token COLON42=null;
        Token DOT43=null;
        Token ELSE44=null;
        Token COLON45=null;
        Token DOT46=null;
        Token WHILE47=null;
        Token COLON49=null;
        Token DOT51=null;
        Token FOR52=null;
        Token ID53=null;
        Token ASSN54=null;
        Token TO55=null;
        Token COLON56=null;
        Token DOT58=null;
        FunParser.seq_com_return c1 = null;

        FunParser.seq_com_return c2 = null;

        FunParser.expr_return e1 = null;

        FunParser.expr_return e2 = null;

        FunParser.expr_return expr35 = null;

        FunParser.actual_return actual38 = null;

        FunParser.expr_return expr41 = null;

        FunParser.expr_return expr48 = null;

        FunParser.seq_com_return seq_com50 = null;

        FunParser.seq_com_return seq_com57 = null;


        CommonTree ID33_tree=null;
        CommonTree ASSN34_tree=null;
        CommonTree ID36_tree=null;
        CommonTree LPAR37_tree=null;
        CommonTree RPAR39_tree=null;
        CommonTree IF40_tree=null;
        CommonTree COLON42_tree=null;
        CommonTree DOT43_tree=null;
        CommonTree ELSE44_tree=null;
        CommonTree COLON45_tree=null;
        CommonTree DOT46_tree=null;
        CommonTree WHILE47_tree=null;
        CommonTree COLON49_tree=null;
        CommonTree DOT51_tree=null;
        CommonTree FOR52_tree=null;
        CommonTree ID53_tree=null;
        CommonTree ASSN54_tree=null;
        CommonTree TO55_tree=null;
        CommonTree COLON56_tree=null;
        CommonTree DOT58_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleTokenStream stream_ASSN=new RewriteRuleTokenStream(adaptor,"token ASSN");
        RewriteRuleTokenStream stream_FOR=new RewriteRuleTokenStream(adaptor,"token FOR");
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleTokenStream stream_WHILE=new RewriteRuleTokenStream(adaptor,"token WHILE");
        RewriteRuleTokenStream stream_TO=new RewriteRuleTokenStream(adaptor,"token TO");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleTokenStream stream_ELSE=new RewriteRuleTokenStream(adaptor,"token ELSE");
        RewriteRuleSubtreeStream stream_seq_com=new RewriteRuleSubtreeStream(adaptor,"rule seq_com");
        RewriteRuleSubtreeStream stream_actual=new RewriteRuleSubtreeStream(adaptor,"rule actual");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Fun.g:80:2: ( ID ASSN expr -> ^( ASSN ID expr ) | ID LPAR actual RPAR -> ^( PROCCALL ID actual ) | IF expr COLON c1= seq_com ( DOT -> ^( IF expr $c1) | ELSE COLON c2= seq_com DOT -> ^( IFELSE expr $c1 $c2) ) | WHILE expr COLON seq_com DOT -> ^( WHILE expr seq_com ) | FOR ID ASSN e1= expr TO e2= expr COLON seq_com DOT -> ^( FOR ID $e1 $e2 seq_com ) )
            int alt9=5;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==ASSN) ) {
                    alt9=1;
                }
                else if ( (LA9_1==LPAR) ) {
                    alt9=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
                }
                break;
            case IF:
                {
                alt9=3;
                }
                break;
            case WHILE:
                {
                alt9=4;
                }
                break;
            case FOR:
                {
                alt9=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // Fun.g:80:4: ID ASSN expr
                    {
                    ID33=(Token)match(input,ID,FOLLOW_ID_in_com683);  
                    stream_ID.add(ID33);

                    ASSN34=(Token)match(input,ASSN,FOLLOW_ASSN_in_com685);  
                    stream_ASSN.add(ASSN34);

                    pushFollow(FOLLOW_expr_in_com687);
                    expr35=expr();

                    state._fsp--;

                    stream_expr.add(expr35.getTree());


                    // AST REWRITE
                    // elements: expr, ID, ASSN
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 80:31: -> ^( ASSN ID expr )
                    {
                        // Fun.g:80:34: ^( ASSN ID expr )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_ASSN.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // Fun.g:81:4: ID LPAR actual RPAR
                    {
                    ID36=(Token)match(input,ID,FOLLOW_ID_in_com716);  
                    stream_ID.add(ID36);

                    LPAR37=(Token)match(input,LPAR,FOLLOW_LPAR_in_com718);  
                    stream_LPAR.add(LPAR37);

                    pushFollow(FOLLOW_actual_in_com720);
                    actual38=actual();

                    state._fsp--;

                    stream_actual.add(actual38.getTree());
                    RPAR39=(Token)match(input,RPAR,FOLLOW_RPAR_in_com722);  
                    stream_RPAR.add(RPAR39);



                    // AST REWRITE
                    // elements: ID, actual
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 81:31: -> ^( PROCCALL ID actual )
                    {
                        // Fun.g:81:34: ^( PROCCALL ID actual )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROCCALL, "PROCCALL"), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_actual.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // Fun.g:83:4: IF expr COLON c1= seq_com ( DOT -> ^( IF expr $c1) | ELSE COLON c2= seq_com DOT -> ^( IFELSE expr $c1 $c2) )
                    {
                    IF40=(Token)match(input,IF,FOLLOW_IF_in_com756);  
                    stream_IF.add(IF40);

                    pushFollow(FOLLOW_expr_in_com758);
                    expr41=expr();

                    state._fsp--;

                    stream_expr.add(expr41.getTree());
                    COLON42=(Token)match(input,COLON,FOLLOW_COLON_in_com760);  
                    stream_COLON.add(COLON42);

                    pushFollow(FOLLOW_seq_com_in_com764);
                    c1=seq_com();

                    state._fsp--;

                    stream_seq_com.add(c1.getTree());
                    // Fun.g:84:5: ( DOT -> ^( IF expr $c1) | ELSE COLON c2= seq_com DOT -> ^( IFELSE expr $c1 $c2) )
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==DOT) ) {
                        alt8=1;
                    }
                    else if ( (LA8_0==ELSE) ) {
                        alt8=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 0, input);

                        throw nvae;
                    }
                    switch (alt8) {
                        case 1 :
                            // Fun.g:84:7: DOT
                            {
                            DOT43=(Token)match(input,DOT,FOLLOW_DOT_in_com772);  
                            stream_DOT.add(DOT43);



                            // AST REWRITE
                            // elements: expr, c1, IF
                            // token labels: 
                            // rule labels: retval, c1
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_c1=new RewriteRuleSubtreeStream(adaptor,"rule c1",c1!=null?c1.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 84:30: -> ^( IF expr $c1)
                            {
                                // Fun.g:84:33: ^( IF expr $c1)
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot(stream_IF.nextNode(), root_1);

                                adaptor.addChild(root_1, stream_expr.nextTree());
                                adaptor.addChild(root_1, stream_c1.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;
                        case 2 :
                            // Fun.g:85:7: ELSE COLON c2= seq_com DOT
                            {
                            ELSE44=(Token)match(input,ELSE,FOLLOW_ELSE_in_com810);  
                            stream_ELSE.add(ELSE44);

                            COLON45=(Token)match(input,COLON,FOLLOW_COLON_in_com812);  
                            stream_COLON.add(COLON45);

                            pushFollow(FOLLOW_seq_com_in_com823);
                            c2=seq_com();

                            state._fsp--;

                            stream_seq_com.add(c2.getTree());
                            DOT46=(Token)match(input,DOT,FOLLOW_DOT_in_com825);  
                            stream_DOT.add(DOT46);



                            // AST REWRITE
                            // elements: c2, expr, c1
                            // token labels: 
                            // rule labels: retval, c1, c2
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_c1=new RewriteRuleSubtreeStream(adaptor,"rule c1",c1!=null?c1.tree:null);
                            RewriteRuleSubtreeStream stream_c2=new RewriteRuleSubtreeStream(adaptor,"rule c2",c2!=null?c2.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 87:30: -> ^( IFELSE expr $c1 $c2)
                            {
                                // Fun.g:87:33: ^( IFELSE expr $c1 $c2)
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(IFELSE, "IFELSE"), root_1);

                                adaptor.addChild(root_1, stream_expr.nextTree());
                                adaptor.addChild(root_1, stream_c1.nextTree());
                                adaptor.addChild(root_1, stream_c2.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;
                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // Fun.g:89:4: WHILE expr COLON seq_com DOT
                    {
                    WHILE47=(Token)match(input,WHILE,FOLLOW_WHILE_in_com891);  
                    stream_WHILE.add(WHILE47);

                    pushFollow(FOLLOW_expr_in_com893);
                    expr48=expr();

                    state._fsp--;

                    stream_expr.add(expr48.getTree());
                    COLON49=(Token)match(input,COLON,FOLLOW_COLON_in_com895);  
                    stream_COLON.add(COLON49);

                    pushFollow(FOLLOW_seq_com_in_com901);
                    seq_com50=seq_com();

                    state._fsp--;

                    stream_seq_com.add(seq_com50.getTree());
                    DOT51=(Token)match(input,DOT,FOLLOW_DOT_in_com903);  
                    stream_DOT.add(DOT51);



                    // AST REWRITE
                    // elements: seq_com, expr, WHILE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 90:30: -> ^( WHILE expr seq_com )
                    {
                        // Fun.g:90:33: ^( WHILE expr seq_com )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_WHILE.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_expr.nextTree());
                        adaptor.addChild(root_1, stream_seq_com.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // Fun.g:91:9: FOR ID ASSN e1= expr TO e2= expr COLON seq_com DOT
                    {
                    FOR52=(Token)match(input,FOR,FOLLOW_FOR_in_com936);  
                    stream_FOR.add(FOR52);

                    ID53=(Token)match(input,ID,FOLLOW_ID_in_com938);  
                    stream_ID.add(ID53);

                    ASSN54=(Token)match(input,ASSN,FOLLOW_ASSN_in_com940);  
                    stream_ASSN.add(ASSN54);

                    pushFollow(FOLLOW_expr_in_com944);
                    e1=expr();

                    state._fsp--;

                    stream_expr.add(e1.getTree());
                    TO55=(Token)match(input,TO,FOLLOW_TO_in_com946);  
                    stream_TO.add(TO55);

                    pushFollow(FOLLOW_expr_in_com950);
                    e2=expr();

                    state._fsp--;

                    stream_expr.add(e2.getTree());
                    COLON56=(Token)match(input,COLON,FOLLOW_COLON_in_com952);  
                    stream_COLON.add(COLON56);

                    pushFollow(FOLLOW_seq_com_in_com982);
                    seq_com57=seq_com();

                    state._fsp--;

                    stream_seq_com.add(seq_com57.getTree());
                    DOT58=(Token)match(input,DOT,FOLLOW_DOT_in_com984);  
                    stream_DOT.add(DOT58);



                    // AST REWRITE
                    // elements: ID, FOR, seq_com, e1, e2
                    // token labels: 
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 92:36: -> ^( FOR ID $e1 $e2 seq_com )
                    {
                        // Fun.g:92:39: ^( FOR ID $e1 $e2 seq_com )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_FOR.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_e1.nextTree());
                        adaptor.addChild(root_1, stream_e2.nextTree());
                        adaptor.addChild(root_1, stream_seq_com.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "com"

    public static class seq_com_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "seq_com"
    // Fun.g:95:1: seq_com : ( com )* -> ^( SEQ ( com )* ) ;
    public final FunParser.seq_com_return seq_com() throws RecognitionException {
        FunParser.seq_com_return retval = new FunParser.seq_com_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        FunParser.com_return com59 = null;


        RewriteRuleSubtreeStream stream_com=new RewriteRuleSubtreeStream(adaptor,"rule com");
        try {
            // Fun.g:96:2: ( ( com )* -> ^( SEQ ( com )* ) )
            // Fun.g:96:4: ( com )*
            {
            // Fun.g:96:4: ( com )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==ID||LA10_0==IF||(LA10_0>=WHILE && LA10_0<=FOR)) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // Fun.g:96:4: com
            	    {
            	    pushFollow(FOLLOW_com_in_seq_com1027);
            	    com59=com();

            	    state._fsp--;

            	    stream_com.add(com59.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);



            // AST REWRITE
            // elements: com
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 96:31: -> ^( SEQ ( com )* )
            {
                // Fun.g:96:34: ^( SEQ ( com )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SEQ, "SEQ"), root_1);

                // Fun.g:96:40: ( com )*
                while ( stream_com.hasNext() ) {
                    adaptor.addChild(root_1, stream_com.nextTree());

                }
                stream_com.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "seq_com"

    public static class expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // Fun.g:102:1: expr : sec_expr ( ( EQ | LT | GT ) sec_expr )? ;
    public final FunParser.expr_return expr() throws RecognitionException {
        FunParser.expr_return retval = new FunParser.expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token EQ61=null;
        Token LT62=null;
        Token GT63=null;
        FunParser.sec_expr_return sec_expr60 = null;

        FunParser.sec_expr_return sec_expr64 = null;


        CommonTree EQ61_tree=null;
        CommonTree LT62_tree=null;
        CommonTree GT63_tree=null;

        try {
            // Fun.g:103:2: ( sec_expr ( ( EQ | LT | GT ) sec_expr )? )
            // Fun.g:103:4: sec_expr ( ( EQ | LT | GT ) sec_expr )?
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_sec_expr_in_expr1073);
            sec_expr60=sec_expr();

            state._fsp--;

            adaptor.addChild(root_0, sec_expr60.getTree());
            // Fun.g:104:5: ( ( EQ | LT | GT ) sec_expr )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( ((LA12_0>=EQ && LA12_0<=GT)) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // Fun.g:104:7: ( EQ | LT | GT ) sec_expr
                    {
                    // Fun.g:104:7: ( EQ | LT | GT )
                    int alt11=3;
                    switch ( input.LA(1) ) {
                    case EQ:
                        {
                        alt11=1;
                        }
                        break;
                    case LT:
                        {
                        alt11=2;
                        }
                        break;
                    case GT:
                        {
                        alt11=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 0, input);

                        throw nvae;
                    }

                    switch (alt11) {
                        case 1 :
                            // Fun.g:104:8: EQ
                            {
                            EQ61=(Token)match(input,EQ,FOLLOW_EQ_in_expr1082); 
                            EQ61_tree = (CommonTree)adaptor.create(EQ61);
                            root_0 = (CommonTree)adaptor.becomeRoot(EQ61_tree, root_0);


                            }
                            break;
                        case 2 :
                            // Fun.g:104:14: LT
                            {
                            LT62=(Token)match(input,LT,FOLLOW_LT_in_expr1087); 
                            LT62_tree = (CommonTree)adaptor.create(LT62);
                            root_0 = (CommonTree)adaptor.becomeRoot(LT62_tree, root_0);


                            }
                            break;
                        case 3 :
                            // Fun.g:104:20: GT
                            {
                            GT63=(Token)match(input,GT,FOLLOW_GT_in_expr1092); 
                            GT63_tree = (CommonTree)adaptor.create(GT63);
                            root_0 = (CommonTree)adaptor.becomeRoot(GT63_tree, root_0);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_sec_expr_in_expr1096);
                    sec_expr64=sec_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, sec_expr64.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class sec_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sec_expr"
    // Fun.g:107:1: sec_expr : pri_expr ( ( PLUS | MINUS | TIMES | DIV ) pri_expr )* ;
    public final FunParser.sec_expr_return sec_expr() throws RecognitionException {
        FunParser.sec_expr_return retval = new FunParser.sec_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token PLUS66=null;
        Token MINUS67=null;
        Token TIMES68=null;
        Token DIV69=null;
        FunParser.pri_expr_return pri_expr65 = null;

        FunParser.pri_expr_return pri_expr70 = null;


        CommonTree PLUS66_tree=null;
        CommonTree MINUS67_tree=null;
        CommonTree TIMES68_tree=null;
        CommonTree DIV69_tree=null;

        try {
            // Fun.g:108:2: ( pri_expr ( ( PLUS | MINUS | TIMES | DIV ) pri_expr )* )
            // Fun.g:108:4: pri_expr ( ( PLUS | MINUS | TIMES | DIV ) pri_expr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_pri_expr_in_sec_expr1110);
            pri_expr65=pri_expr();

            state._fsp--;

            adaptor.addChild(root_0, pri_expr65.getTree());
            // Fun.g:109:5: ( ( PLUS | MINUS | TIMES | DIV ) pri_expr )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>=PLUS && LA14_0<=DIV)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // Fun.g:109:7: ( PLUS | MINUS | TIMES | DIV ) pri_expr
            	    {
            	    // Fun.g:109:7: ( PLUS | MINUS | TIMES | DIV )
            	    int alt13=4;
            	    switch ( input.LA(1) ) {
            	    case PLUS:
            	        {
            	        alt13=1;
            	        }
            	        break;
            	    case MINUS:
            	        {
            	        alt13=2;
            	        }
            	        break;
            	    case TIMES:
            	        {
            	        alt13=3;
            	        }
            	        break;
            	    case DIV:
            	        {
            	        alt13=4;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 13, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt13) {
            	        case 1 :
            	            // Fun.g:109:8: PLUS
            	            {
            	            PLUS66=(Token)match(input,PLUS,FOLLOW_PLUS_in_sec_expr1119); 
            	            PLUS66_tree = (CommonTree)adaptor.create(PLUS66);
            	            root_0 = (CommonTree)adaptor.becomeRoot(PLUS66_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // Fun.g:109:16: MINUS
            	            {
            	            MINUS67=(Token)match(input,MINUS,FOLLOW_MINUS_in_sec_expr1124); 
            	            MINUS67_tree = (CommonTree)adaptor.create(MINUS67);
            	            root_0 = (CommonTree)adaptor.becomeRoot(MINUS67_tree, root_0);


            	            }
            	            break;
            	        case 3 :
            	            // Fun.g:109:25: TIMES
            	            {
            	            TIMES68=(Token)match(input,TIMES,FOLLOW_TIMES_in_sec_expr1129); 
            	            TIMES68_tree = (CommonTree)adaptor.create(TIMES68);
            	            root_0 = (CommonTree)adaptor.becomeRoot(TIMES68_tree, root_0);


            	            }
            	            break;
            	        case 4 :
            	            // Fun.g:109:34: DIV
            	            {
            	            DIV69=(Token)match(input,DIV,FOLLOW_DIV_in_sec_expr1134); 
            	            DIV69_tree = (CommonTree)adaptor.create(DIV69);
            	            root_0 = (CommonTree)adaptor.becomeRoot(DIV69_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_pri_expr_in_sec_expr1138);
            	    pri_expr70=pri_expr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, pri_expr70.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sec_expr"

    public static class pri_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pri_expr"
    // Fun.g:112:1: pri_expr : ( FALSE -> FALSE | TRUE -> TRUE | NUM -> NUM | ID -> ID | ID LPAR actual RPAR -> ^( FUNCCALL ID actual ) | NOT pri_expr -> ^( NOT pri_expr ) | LPAR expr RPAR -> expr );
    public final FunParser.pri_expr_return pri_expr() throws RecognitionException {
        FunParser.pri_expr_return retval = new FunParser.pri_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token FALSE71=null;
        Token TRUE72=null;
        Token NUM73=null;
        Token ID74=null;
        Token ID75=null;
        Token LPAR76=null;
        Token RPAR78=null;
        Token NOT79=null;
        Token LPAR81=null;
        Token RPAR83=null;
        FunParser.actual_return actual77 = null;

        FunParser.pri_expr_return pri_expr80 = null;

        FunParser.expr_return expr82 = null;


        CommonTree FALSE71_tree=null;
        CommonTree TRUE72_tree=null;
        CommonTree NUM73_tree=null;
        CommonTree ID74_tree=null;
        CommonTree ID75_tree=null;
        CommonTree LPAR76_tree=null;
        CommonTree RPAR78_tree=null;
        CommonTree NOT79_tree=null;
        CommonTree LPAR81_tree=null;
        CommonTree RPAR83_tree=null;
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_FALSE=new RewriteRuleTokenStream(adaptor,"token FALSE");
        RewriteRuleTokenStream stream_TRUE=new RewriteRuleTokenStream(adaptor,"token TRUE");
        RewriteRuleTokenStream stream_NUM=new RewriteRuleTokenStream(adaptor,"token NUM");
        RewriteRuleSubtreeStream stream_pri_expr=new RewriteRuleSubtreeStream(adaptor,"rule pri_expr");
        RewriteRuleSubtreeStream stream_actual=new RewriteRuleSubtreeStream(adaptor,"rule actual");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Fun.g:113:2: ( FALSE -> FALSE | TRUE -> TRUE | NUM -> NUM | ID -> ID | ID LPAR actual RPAR -> ^( FUNCCALL ID actual ) | NOT pri_expr -> ^( NOT pri_expr ) | LPAR expr RPAR -> expr )
            int alt15=7;
            switch ( input.LA(1) ) {
            case FALSE:
                {
                alt15=1;
                }
                break;
            case TRUE:
                {
                alt15=2;
                }
                break;
            case NUM:
                {
                alt15=3;
                }
                break;
            case ID:
                {
                int LA15_4 = input.LA(2);

                if ( (LA15_4==LPAR) ) {
                    alt15=5;
                }
                else if ( ((LA15_4>=PROC && LA15_4<=ID)||(LA15_4>=RPAR && LA15_4<=RETURN)||(LA15_4>=BOOL && LA15_4<=DIV)) ) {
                    alt15=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 4, input);

                    throw nvae;
                }
                }
                break;
            case NOT:
                {
                alt15=6;
                }
                break;
            case LPAR:
                {
                alt15=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // Fun.g:113:4: FALSE
                    {
                    FALSE71=(Token)match(input,FALSE,FOLLOW_FALSE_in_pri_expr1152);  
                    stream_FALSE.add(FALSE71);



                    // AST REWRITE
                    // elements: FALSE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 113:31: -> FALSE
                    {
                        adaptor.addChild(root_0, stream_FALSE.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // Fun.g:114:4: TRUE
                    {
                    TRUE72=(Token)match(input,TRUE,FOLLOW_TRUE_in_pri_expr1182);  
                    stream_TRUE.add(TRUE72);



                    // AST REWRITE
                    // elements: TRUE
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 114:31: -> TRUE
                    {
                        adaptor.addChild(root_0, stream_TRUE.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // Fun.g:115:4: NUM
                    {
                    NUM73=(Token)match(input,NUM,FOLLOW_NUM_in_pri_expr1213);  
                    stream_NUM.add(NUM73);



                    // AST REWRITE
                    // elements: NUM
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 115:31: -> NUM
                    {
                        adaptor.addChild(root_0, stream_NUM.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // Fun.g:116:4: ID
                    {
                    ID74=(Token)match(input,ID,FOLLOW_ID_in_pri_expr1245);  
                    stream_ID.add(ID74);



                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 116:31: -> ID
                    {
                        adaptor.addChild(root_0, stream_ID.nextNode());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // Fun.g:117:4: ID LPAR actual RPAR
                    {
                    ID75=(Token)match(input,ID,FOLLOW_ID_in_pri_expr1278);  
                    stream_ID.add(ID75);

                    LPAR76=(Token)match(input,LPAR,FOLLOW_LPAR_in_pri_expr1280);  
                    stream_LPAR.add(LPAR76);

                    pushFollow(FOLLOW_actual_in_pri_expr1282);
                    actual77=actual();

                    state._fsp--;

                    stream_actual.add(actual77.getTree());
                    RPAR78=(Token)match(input,RPAR,FOLLOW_RPAR_in_pri_expr1284);  
                    stream_RPAR.add(RPAR78);



                    // AST REWRITE
                    // elements: actual, ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 117:31: -> ^( FUNCCALL ID actual )
                    {
                        // Fun.g:117:34: ^( FUNCCALL ID actual )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FUNCCALL, "FUNCCALL"), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_actual.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 6 :
                    // Fun.g:119:4: NOT pri_expr
                    {
                    NOT79=(Token)match(input,NOT,FOLLOW_NOT_in_pri_expr1340);  
                    stream_NOT.add(NOT79);

                    pushFollow(FOLLOW_pri_expr_in_pri_expr1342);
                    pri_expr80=pri_expr();

                    state._fsp--;

                    stream_pri_expr.add(pri_expr80.getTree());


                    // AST REWRITE
                    // elements: NOT, pri_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 119:31: -> ^( NOT pri_expr )
                    {
                        // Fun.g:119:34: ^( NOT pri_expr )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_NOT.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_pri_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 7 :
                    // Fun.g:120:4: LPAR expr RPAR
                    {
                    LPAR81=(Token)match(input,LPAR,FOLLOW_LPAR_in_pri_expr1369);  
                    stream_LPAR.add(LPAR81);

                    pushFollow(FOLLOW_expr_in_pri_expr1371);
                    expr82=expr();

                    state._fsp--;

                    stream_expr.add(expr82.getTree());
                    RPAR83=(Token)match(input,RPAR,FOLLOW_RPAR_in_pri_expr1373);  
                    stream_RPAR.add(RPAR83);



                    // AST REWRITE
                    // elements: expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 120:31: -> expr
                    {
                        adaptor.addChild(root_0, stream_expr.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pri_expr"

    public static class actual_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actual"
    // Fun.g:123:1: actual : ( expr -> expr | -> NOACTUAL );
    public final FunParser.actual_return actual() throws RecognitionException {
        FunParser.actual_return retval = new FunParser.actual_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        FunParser.expr_return expr84 = null;


        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // Fun.g:124:2: ( expr -> expr | -> NOACTUAL )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>=ID && LA16_0<=LPAR)||(LA16_0>=FALSE && LA16_0<=NOT)) ) {
                alt16=1;
            }
            else if ( (LA16_0==RPAR) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // Fun.g:124:4: expr
                    {
                    pushFollow(FOLLOW_expr_in_actual1400);
                    expr84=expr();

                    state._fsp--;

                    stream_expr.add(expr84.getTree());


                    // AST REWRITE
                    // elements: expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 124:31: -> expr
                    {
                        adaptor.addChild(root_0, stream_expr.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // Fun.g:125:31: 
                    {

                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 125:31: -> NOACTUAL
                    {
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(NOACTUAL, "NOACTUAL"));

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "actual"

    // Delegated rules


 

    public static final BitSet FOLLOW_var_decl_in_program100 = new BitSet(new long[]{0x0000000000C82000L});
    public static final BitSet FOLLOW_proc_decl_in_program103 = new BitSet(new long[]{0x0000000000082000L});
    public static final BitSet FOLLOW_EOF_in_program106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROC_in_proc_decl203 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_proc_decl205 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_LPAR_in_proc_decl211 = new BitSet(new long[]{0x0000000000C92000L});
    public static final BitSet FOLLOW_formal_in_proc_decl213 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RPAR_in_proc_decl215 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_in_proc_decl217 = new BitSet(new long[]{0x000000000DCC6000L});
    public static final BitSet FOLLOW_var_decl_in_proc_decl223 = new BitSet(new long[]{0x000000000DCC6000L});
    public static final BitSet FOLLOW_seq_com_in_proc_decl226 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_proc_decl228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNC_in_proc_decl320 = new BitSet(new long[]{0x0000000000C82000L});
    public static final BitSet FOLLOW_type_in_proc_decl322 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_proc_decl324 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_LPAR_in_proc_decl330 = new BitSet(new long[]{0x0000000000C92000L});
    public static final BitSet FOLLOW_formal_in_proc_decl332 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RPAR_in_proc_decl334 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_in_proc_decl336 = new BitSet(new long[]{0x000000000DD86000L});
    public static final BitSet FOLLOW_var_decl_in_proc_decl342 = new BitSet(new long[]{0x000000000DD86000L});
    public static final BitSet FOLLOW_seq_com_in_proc_decl345 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RETURN_in_proc_decl351 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_expr_in_proc_decl353 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_proc_decl355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_formal497 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_formal499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_var_decl573 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_var_decl575 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_ASSN_in_var_decl577 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_expr_in_var_decl579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_type611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_type642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_com683 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_ASSN_in_com685 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_expr_in_com687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_com716 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_LPAR_in_com718 = new BitSet(new long[]{0x000000F00001C000L});
    public static final BitSet FOLLOW_actual_in_com720 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RPAR_in_com722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_com756 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_expr_in_com758 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_in_com760 = new BitSet(new long[]{0x000000000F044000L});
    public static final BitSet FOLLOW_seq_com_in_com764 = new BitSet(new long[]{0x0000000002040000L});
    public static final BitSet FOLLOW_DOT_in_com772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELSE_in_com810 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_in_com812 = new BitSet(new long[]{0x000000000D044000L});
    public static final BitSet FOLLOW_seq_com_in_com823 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_com825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_com891 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_expr_in_com893 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_in_com895 = new BitSet(new long[]{0x000000000D044000L});
    public static final BitSet FOLLOW_seq_com_in_com901 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_com903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_com936 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_ID_in_com938 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_ASSN_in_com940 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_expr_in_com944 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_TO_in_com946 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_expr_in_com950 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_in_com952 = new BitSet(new long[]{0x000000000D044000L});
    public static final BitSet FOLLOW_seq_com_in_com982 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_com984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_com_in_seq_com1027 = new BitSet(new long[]{0x000000000D004002L});
    public static final BitSet FOLLOW_sec_expr_in_expr1073 = new BitSet(new long[]{0x00000000E0000002L});
    public static final BitSet FOLLOW_EQ_in_expr1082 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_LT_in_expr1087 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_GT_in_expr1092 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_sec_expr_in_expr1096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pri_expr_in_sec_expr1110 = new BitSet(new long[]{0x0000000F00000002L});
    public static final BitSet FOLLOW_PLUS_in_sec_expr1119 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_MINUS_in_sec_expr1124 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_TIMES_in_sec_expr1129 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_DIV_in_sec_expr1134 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_pri_expr_in_sec_expr1138 = new BitSet(new long[]{0x0000000F00000002L});
    public static final BitSet FOLLOW_FALSE_in_pri_expr1152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_pri_expr1182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_pri_expr1213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pri_expr1245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_pri_expr1278 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_LPAR_in_pri_expr1280 = new BitSet(new long[]{0x000000F00001C000L});
    public static final BitSet FOLLOW_actual_in_pri_expr1282 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RPAR_in_pri_expr1284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_pri_expr1340 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_pri_expr_in_pri_expr1342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAR_in_pri_expr1369 = new BitSet(new long[]{0x000000F00000C000L});
    public static final BitSet FOLLOW_expr_in_pri_expr1371 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RPAR_in_pri_expr1373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_actual1400 = new BitSet(new long[]{0x0000000000000002L});

}