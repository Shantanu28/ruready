/*****************************************************************************************
 * Source File: LineReaderAssemblerFactory.java
 ****************************************************************************************/
package net.ruready.common.parser.core.pretty.assembler;

import net.ruready.common.exception.SystemException;
import net.ruready.common.parser.core.assembler.AbstractAssemblerFactory;
import net.ruready.common.parser.core.assembler.Assembler;
import net.ruready.common.parser.core.assembler.AssemblerIdentifier;

/**
 * A factory to instantiate assembler types for the parametric evaluation parser. This is
 * the only public class in the assembler package, and serves as the package's proxy.
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9359<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 8, 2007
 */
public class PrettyAssemblerFactory implements AbstractAssemblerFactory
{
	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize an arithmetic parser assembler factory.
	 */
	public PrettyAssemblerFactory()
	{

	}

	// ========================= IMPLEMENTATION: AbstractColorFactory ==

	/**
	 * @see net.ruready.common.parser.arithmetic.entity.AbstractAssemblerFactory#createAssembler(net.ruready.common.parser.arithmetic.assembler.LineID,
	 *      java.lang.Object[])
	 */
	public Assembler createAssembler(AssemblerIdentifier identifier, Object... args)
	{
		switch ((PrettyAssemblerID) identifier)
		{
			/**
			 * Process an Alternation parser.
			 */
			case ALTERNATION:
			{
				return new PrettyAlternationAssembler((String) args[0]);
			}

				/**
				 * Process an Empty parser.
				 */
			case EMPTY:
			{
				return new PrettyEmptyAssembler();
			}

				/**
				 * Process a Repetition parser.
				 */
			case REPETITION:
			{
				return new PrettyRepetitionAssembler((String) args[0], args[1]);
			}

				/**
				 * Process a Sequence parser.
				 */
			case SEQUENCE:
			{
				return new PrettySequenceAssembler((String) args[0], (Integer) args[1]);
			}

				/**
				 * Process a Terminal-type parser.
				 */
			case TERMINAL:
			{
				return new PrettyTerminalAssembler();
			}

				/**
				 * Process fence token.
				 */
			case FENCE:
			{
				return new FenceAssembler(args[0]);
			}

			default:
			{
				throw new SystemException(
						"Unsupported assembler type " + identifier);
			}
		}
	}
	// ========================= FIELDS ====================================

}
