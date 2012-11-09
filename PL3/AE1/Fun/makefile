SymbolTable.class: SymbolTable.java
	javac SymbolTable.java
Type.class: Type.java
	javac Type.java
Address.class: Address.java
	javac Address.java
SVM.class: SVM.java
	javac SVM.java
FunParser.class: Fun.g
	java org.antlr.Tool Fun.g
	javac FunLexer.java FunParser.java
FunChecker.class: FunChecker.g
	java org.antlr.Tool FunChecker.g
	javac FunChecker.java
FunEncoder.class: FunEncoder.g
	java org.antlr.Tool FunEncoder.g
	javac FunEncoder.java
FunRun.class: FunRun.java
	javac FunRun.java
FunRun: SymbolTable.class Type.class Address.class SVM.class \
		FunParser.class FunChecker.class FunEncoder.class \
		FunRun.class