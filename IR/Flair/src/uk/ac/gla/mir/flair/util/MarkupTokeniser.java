package uk.ac.gla.mir.flair.util;

/**
 * Title:        Description Readers and Writers<br>
 * Description:  Tool for putting whitespace between tokens<br>
 * Company:      Department of Computing Science, University of Glasgow<br>
 * @author Steven Morrison
 * @version 1.0
 */

import java.util.*;
import java.io.*;

import uk.ac.gla.mir.flair.util.*;

public class MarkupTokeniser {

	/**
	 * Opens the specified <code>File</code> and parses its contents, separating
	 * adjoining tokens with ' 's. Returns the modified file contents as a String.
	 * @param file File to parse contents of
	 * @return File contents with extra spaces where required
	 */
	public static String separateDescTokens(File file) {

		StringBuffer returnString = new StringBuffer();
		StringBuffer tempString = new StringBuffer();
		String tempStr, previousToken = "";

		try {
			String currentToken = "";
			BufferedReader in = Files.openFileReader(file);
			StreamTokenizer stk = new StreamTokenizer(in);
			boolean parseCurrentToken = false;

			stk.eolIsSignificant(false);
			stk.wordChars(33, 126);
			stk.whitespaceChars(0, 32);
			stk.parseNumbers();

			while ((tempStr = in.readLine()) != null)
				tempString.append(tempStr + " ");

			StringTokenizer strtk = new StringTokenizer(new String(tempString));

			while (strtk.hasMoreTokens()) {

				if (!parseCurrentToken)
					currentToken = strtk.nextToken();
				else
					parseCurrentToken = false;

				if (currentToken.equals("<")) {
					//Change "<" + "tag" into "<tag" and append to buffer
					currentToken = currentToken + strtk.nextToken();
					if (currentToken.indexOf('>') >= 0) {
						previousToken = currentToken.substring(0, currentToken.indexOf('<'));
						currentToken = currentToken.substring(currentToken.indexOf('<'), currentToken.length());
						parseCurrentToken = true;
					}
					else
						previousToken = currentToken;
				}
				if (currentToken.equals("</")) {
					//Change "</" + "tag" into "</tag" and append to buffer
					currentToken = currentToken + strtk.nextToken();
					if (currentToken.indexOf('>') >= 0) {
						previousToken = currentToken.substring(0, currentToken.indexOf('<'));
						currentToken = currentToken.substring(currentToken.indexOf('<'), currentToken.length());
						parseCurrentToken = true;
					}
					else {
						returnString.append(" " + currentToken);
						previousToken = currentToken;
					}
				}
				else if (currentToken.equals(">") && previousToken.charAt(0) == '<') {
					//Change ("<tag" or "</tag") + ">" into ("<tag>" or ("</tag") and append to buffer
					returnString.append(currentToken);
					previousToken = previousToken + currentToken;
				}
				else if (currentToken.indexOf('>') == 0 && currentToken.length() > 1 && previousToken.charAt(0) == '<') {
					//Change ("<tag" or "</tag") + ">ttt" into ("<tag>" or ("</tag")  + "ttt" and append <tag> to buffer
					returnString.append(">");
					previousToken = previousToken + ">";
					currentToken = currentToken.substring(1);
					parseCurrentToken = true;
				}
				else if (currentToken.length() > 1 && currentToken.endsWith(">") && currentToken.charAt(0) != '<') {
					if (currentToken.indexOf('<')>0) {
						//Change "ttt<tag>" into "ttt" + "<tag>" amd append ttt to buffer
						returnString.append(" " + currentToken.substring(0, currentToken.indexOf('<')));
						previousToken = currentToken.substring(0, currentToken.indexOf('<'));
						currentToken = currentToken.substring(currentToken.indexOf('<'));
						parseCurrentToken = true;
					}
					else {
						//Change "val>" into "val" + ">" and append to buffer
						returnString.append(" " + currentToken.substring(0, currentToken.indexOf('>')) + " >");
						previousToken = ">";
						currentToken = currentToken.substring(currentToken.indexOf('>')+1);
						parseCurrentToken = true;
					}
				}
				else if (currentToken.indexOf('>') >= 0) {
					if (currentToken.indexOf('>') == 0 && currentToken.indexOf('<') == 1) {
						//Change "><ttt" into ">" + "<ttt" and append ">" to buffer
						returnString.append(" >");
						previousToken = ">";
						currentToken = currentToken.substring(1);
						parseCurrentToken = true;
					}
					else if (currentToken.indexOf('>') == 0) {
						//Change ">ttt" into ">" + "ttt" and append ">" to buffer
						returnString.append(" >");
						previousToken = ">";
						currentToken = currentToken.substring(1, currentToken.length());
						parseCurrentToken = true;
					}
					else {
						//Change "<tag>ttt" into "<tag> + "ttt" and append "<tag>" to buffer
						returnString.append(" " + currentToken.substring(0, currentToken.indexOf('>') + 1));
						previousToken = currentToken.substring(0, currentToken.indexOf('>') + 1);

						if (!(currentToken.indexOf('>') == currentToken.length() + 1)) {
							currentToken = currentToken.substring(currentToken.indexOf('>') + 1, currentToken.length());
							parseCurrentToken = true;
						}
					}
				}
				else if (currentToken.indexOf('<') >= 0) {
					if (currentToken.indexOf('<') == 0) {
						//Append "<tag" or "</tag>" to buffer
						returnString.append(" " + currentToken);
						previousToken = currentToken;
					}
					else {
						//Change "ttt</tag>" into "ttt" + "</tag"  and append "ttt" to buffer
						returnString.append(" " + currentToken.substring(0, currentToken.indexOf('<') - 1));
						previousToken = currentToken.substring(0, currentToken.indexOf('<') - 1);
						currentToken = currentToken.substring(currentToken.indexOf('<'), currentToken.length() + 1);
						parseCurrentToken = true;
					}
				}
				else if (currentToken.equals("=")) {
					//Append "=" to buffer
					returnString.append(" =");
					previousToken = "=";
				}
				else if (currentToken.indexOf('=') >= 0) {
					if (currentToken.indexOf('=') == 0) {
						//Change "=val" into "=" + "val" and append to buffer
						returnString.append(" = " + currentToken.substring(1));
						previousToken = currentToken.substring(1);
					}
					else if (currentToken.indexOf('=') == currentToken.length() - 1) {
						//Change "attr=" into "attr" + "=" and append to buffer
						returnString.append(" " + currentToken.substring(0, currentToken.length() - 1) + " =");
						previousToken = "=";
					}
					else {
						//Change "attr=val" into "attr" + "=" + "val" and append to buffer
						String attrName = currentToken.substring(0, currentToken.indexOf('='));
						String attrVal = currentToken.substring(currentToken.indexOf('=') + 1, currentToken.length());
						returnString.append(" " + attrName + " = " + attrVal);
						previousToken = attrVal;
					}
				}
				else {
					returnString.append(" " + currentToken);
					previousToken = currentToken;
				}

			}

		}
		catch (IOException ioe) {
			Assert.fatal(false,
				"Error opening file " + file.getAbsoluteFile() + " Please ensure the specified path exists. (MarkupTokeniser)");
		}
		return new String(returnString);
	}

	/**
	 * Opens the specified <code>File</code> and parses its contents, separating
	 * adjoining tokens with ' 's. Returns the modified file contents as a String.
	 * @param file File to parse contents of
	 * @param tokenStart Characters denoting the start of a token
	 * @param tokenEnd Characters denoting the end of a token
	 * @return File contents with extra spaces where required
	 */
	public static String separateTokens(File file, Vector tokenStart, Vector tokenEnd) {

		StringBuffer returnString = new StringBuffer();
		StringBuffer tempString = new StringBuffer();
		String tempStr;

		try {
			String currentToken;
			FileInputStream fileIn = new FileInputStream(file);
			InputStreamReader inrd = new InputStreamReader(fileIn);
			BufferedReader in = new BufferedReader(inrd);
			StreamTokenizer stk = new StreamTokenizer(in);

			stk.eolIsSignificant(false);
			stk.wordChars(33, 126);
			stk.whitespaceChars(0, 32);
			stk.parseNumbers();

			while ((tempStr = in.readLine()) != null)
				tempString.append(tempStr + " ");

			StringTokenizer strtk = new StringTokenizer(new String(tempString));

			while (strtk.hasMoreTokens()) {

				currentToken = strtk.nextToken();

				for (int i = 0; i < currentToken.length(); i++) {
					if (i > 0 && tokenStart.contains(currentToken.charAt(i) + "")) {
						//Start of a new token found. Split token in two
						returnString.append(" " + currentToken.substring(0, i));

						if (i < currentToken.length() + 1 && currentToken.charAt(i + 1) == ' ') {
							//Start of token is separate from the rest. Join them
							currentToken = currentToken.charAt(i) + currentToken.substring(currentToken.lastIndexOf(' '));
						}
						else
							currentToken = currentToken.substring(i);
						i = 0;
					}

					if (i < currentToken.length() && tokenEnd.contains(currentToken.charAt(i) + "")) {
						//End of current token found. Split token in two

						if (i == 0)
							//End of token is separate from the rest. Join them
							returnString.append(currentToken.substring(0, i + 1));
						else
							returnString.append(" " + currentToken.substring(0, i + 1));
						currentToken = currentToken.substring(i + 1);
						i = 0;
					}
				}
				returnString.append(" " + currentToken);
			}

		}
		catch (IOException ioe) {
			Assert.fatal(false,
				"Error opening file " + file.getAbsoluteFile() + ". Please ensure the specified path exists. (MarkupTokeniser)");
		}
		return new String(returnString);
	}

	/**
	 * Test program
	 * Opens a file and adds whitespace as appropriate, then displays the
	 * modified file's contents.
	 * @param args args[0] = File to tokenise. If no arguments
	 * are given, a default file is used.
	 */
	public static void main(String args[]) {

		Vector tokenStart = new Vector();
		tokenStart.add("<");
		tokenStart.add("=");
		Vector tokenEnd = new Vector();
		tokenEnd.add(">");
		tokenEnd.add("=");

		if (args.length>0)
			System.out.println(separateTokens(new File(args[0]), tokenStart, tokenEnd));
		else
			System.out.println(separateTokens(new File("descFile"), tokenStart, tokenEnd));
	}
}
