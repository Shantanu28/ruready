/*******************************************************
 * Source File: TokenTester.java
 *******************************************************/
package net.ruready.common.parser.core.tokens;

import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.ParserTester;

public class TokenTester extends ParserTester {
	/**
	 * 
	 */
	public TokenTester(Parser p) 
	{
		super(p);
	}
	
	/**
	 * assembly method comment.
	 */
	@Override
	protected Assembly assembly(String s) 
	{
		return new TokenAssembly(s);
	}
}
