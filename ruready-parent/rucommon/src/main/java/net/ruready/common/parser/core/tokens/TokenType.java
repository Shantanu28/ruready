/*******************************************************
 * Source File: TokenType.java
 *******************************************************/
package net.ruready.common.parser.core.tokens;

/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 * 
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */

/**
 * Objects of this class represent a type of token, such
 * as "number" or "word".
 * 
 * @author Steven J. Metsker
 *
 * (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * 
 * @version 1.0 
 */
 public class TokenType {
	 protected String name;
/**
 * Creates a token type of the given name.
 */
public TokenType(String name) {
	this.name = name;
}

@Override
public String toString() {
	return name;
}
}
