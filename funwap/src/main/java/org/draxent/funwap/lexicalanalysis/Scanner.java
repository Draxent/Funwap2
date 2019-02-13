package org.draxent.funwap.lexicalanalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// It is the class performing the lexical analysis,
// that is the process of converting a sequence of characters into a sequence of tokens.
public class Scanner {
	private final Pattern LineComment = Pattern.compile("[^\n]+");
	private final Pattern MultiLineComment = Pattern.compile("/\\*+[^*]*\\*+(?:[^/*][^*]*\\*+)*/");
	
	private String source; // The string to convert.	
	private int index; // Index of the char that we are analysing.
	private int row; // The line in the source string
	private int column; // The column in the source string
	
	private TokenType[] tokenValues;
	
	public Scanner(String source) {
		this.source = source;
		initIndexes();
		
		tokenValues = TokenType.values();
		Arrays.sort(tokenValues, (TokenType a, TokenType b) -> a.getPriority() - b.getPriority());
	}
	
	public ArrayList<Token> tokenize() {
		ArrayList<Token> result = new ArrayList<>();
		initIndexes();
		
		while (!isEOF()) {
			skipWhiteSpacesAndComments();
			
			Token token = matchToken();
			index += token.getValue().length();
			column += token.getValue().length();
			result.add(token);
		}
		
		return result;
	}
	
	private void skipWhiteSpacesAndComments() {
		int oldIndex = index;
		skipWhiteSpaces();
		skipComment();
		boolean indexIsChanged = (this.index > oldIndex);
		if (indexIsChanged) {
			skipWhiteSpacesAndComments();
		}	
	}
	
	void skipWhiteSpaces()
	{
		while (!isEOF() && Character.isWhitespace(source.charAt(index)))
		{
			boolean isNewLine = (source.charAt(index) == '\n');
			if (isNewLine)
			{
				this.row++;
				this.column = 1;
			}
			else {
				this.column++;
			}
			this.index++;
		}
	}
	
	boolean checkCharLookingAhead(char c, int ahead)
	{
		return (!isEOF(ahead) && source.charAt(index + ahead) == c);
	}
	
	void skipComment()
	{
		if (source.charAt(index) == '/' && checkCharLookingAhead('/', 1))
		{
			skipLineComment();
		}
		else if (source.charAt(index) == '/' && checkCharLookingAhead('*', 1))
		{
			skipMultiLineComment();
		}
	}
	
	private void skipLineComment() {
		Matcher matcher = LineComment.matcher(getRemainingSource());
		if (matcher.find()) {
			index += matcher.group().length();
			column += matcher.group().length();
		}	
	}
	
	private void skipMultiLineComment() {
		Matcher matcher = MultiLineComment.matcher(getRemainingSource());
		if (matcher.find()) {
			index += matcher.group().length();
			String[] srows = matcher.group().split("\n");
			column = srows[srows.length - 1].length() + 1;
			row += srows.length - 1;			
		}
	}
	
	Token matchToken() {
		for (TokenType tokenType : tokenValues) {
			if (tokenType.isRegEx()) {
				Matcher matcher = tokenType.getPattern().matcher(getRemainingSource());
				
				if (matcher.find()) {
					return new Token(tokenType, matcher.group(), index, row, column);
				}				
			} else {
				String matchString = tokenType.getMatchString();
				String remainSource = getNCharsFromRemainingSource(matchString.length()).toString();
				if (remainSource.equals(matchString)) {
					return new Token(tokenType, matchString, index, row, column);				
				}
			}
		}
		
		return null;
	}
	
	int getIndex() {
		return index;
	}

	int getRow() {
		return row;
	}

	int getColumn() {
		return column;
	}
	
	CharSequence getSourceSubSequence(int start, int end) {
		int minStart = Math.min(start, source.length());
		int minEnd = Math.min(end, source.length());
		return source.subSequence(minStart, minEnd);
	}
	
	private CharSequence getRemainingSource() {
		return getSourceSubSequence(index, source.length());
	}
	
	private CharSequence getNCharsFromRemainingSource(int n) {
		return getSourceSubSequence(index, index + n);
	}
	
	private boolean isEOF() {
		return index >= source.length();
	}
	
	private boolean isEOF(int ahead) {
		return index + ahead >= source.length();
	}
	
	private void initIndexes() {
		index = 0;
		row = 1;
		column = 1;	
	}
}
