/*******************************************************************************
 * Source File: CharacterTester.java
 ******************************************************************************/
package net.ruready.common.parser.core.chars;

import net.ruready.common.parser.core.entity.Assembly;
import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.ParserTester;
import net.ruready.common.rl.CommonNames;

/**
 * A parser tester with a character assembly.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 *         <br>(c) 2006-07 Continuing Education , University of Utah . All
 *         copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 29, 2007
 */
public class CharacterTester extends ParserTester
{
	/**
	 * 
	 */
	public CharacterTester(Parser p)
	{
		super(p);
	}

	/**
	 * assembly method comment.
	 */
	@Override
	protected Assembly assembly(String s)
	{
		return new CharacterAssembly(s);
	}

	/**
	 * @return java.lang.String
	 */
	@Override
	protected String separator()
	{
		return CommonNames.MISC.EMPTY_STRING;
	}
}
