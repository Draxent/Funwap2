package org.draxent.funwap.compiler;

import java.util.List;
import java.util.function.Function;

public class CompilerHelper {
	public static final char CURLYBR_OPEN = '{';
	public static final char CURLYBR_CLOSE = '}';
	public static final char ROUNDBR_OPEN = '(';
	public static final char ROUNDBR_CLOSE = ')';
	public static final char SPACE = ' ';
	public static final char COMMA = ',';
	public static final char TAB = '\t';
	public static final char SEMICOLON = ';';
	public static final char ASSIGN = '=';
	public static final String NEW_LINE = "\r\n";
	public static final String PUBLIC_STATIC = "public static ";
	
	public static <T> void compileCommaSeparatedList(StringBuilder sb, List<T> list, 
			Function<T, String> compileObject) {
		boolean firstIteration = true;
		for (T obj : list) {
			if (!firstIteration) {
				sb.append(COMMA);
				sb.append(SPACE);
			}
			sb.append(compileObject.apply(obj));
			firstIteration = false;
		}	
	}
}
