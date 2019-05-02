package org.draxent.funwap.gui;

import java.io.File;
import java.util.List;

import org.draxent.funwap.ast.statement.BlockNode;
import org.draxent.funwap.lexicalanalysis.Token;

public class Cache {
	private static Cache cache = new Cache();
	
	private File openedFile = null;
	private List<Token> tokens = null;
	private BlockNode programBlock = null;
	private String programName = null;
	
	public static Cache getCache() {
		return cache;
	}

	public File getOpenedFile() {
		return openedFile;
	}

	public void setOpenedFile(File openedFile) {
		this.openedFile = openedFile;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public BlockNode getProgramBlock() {
		return programBlock;
	}

	public void setProgramBlock(BlockNode programBlock) {
		this.programBlock = programBlock;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}
}
