/*****************************************************************************************
 * Source File: MatrixDemo.java
 ****************************************************************************************/
package net.ruready.common.demo;

import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.measure.MatricesMeasure;
import org.jmatrices.dbl.operator.MatrixOperator;
import org.jmatrices.dbl.transformer.MatrixTransformer;

/**
 * MatrixDemo: demonstrates the usage of the JMatrices package. <br>
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 16, 2007
 */
public class MatrixDemo
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(MatrixDemo.class);

	// ========================= FIELDS ====================================

	public static void main(String[] args)
	{
		Matrix a = MatrixFactory.getRandomMatrix(3, 3, null);
		Matrix b = MatrixFactory.getRandomMatrix(3, 1, null);
		// invert a using LUDecomposition
		Matrix aInv = MatrixTransformer.inverse(a);
		// solve the system using LUDecomposition
		Matrix c = MatrixOperator.solve(a, b);
		// solve the system by multiplying aInv with b
		Matrix cThroughInv = MatrixOperator.multiply(aInv, b);
		// print things out
		logger.debug(a);
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		logger.debug(b);
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		logger.debug(c);
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		logger.debug(cThroughInv);
		logger.debug(CommonNames.MISC.EMPTY_STRING);
		// test for equality between the two ways of doing things
		logger.debug(MatricesMeasure.areEqual(c, cThroughInv));
	}
}
