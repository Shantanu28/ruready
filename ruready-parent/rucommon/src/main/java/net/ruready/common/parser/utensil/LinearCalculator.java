/*******************************************************************************
 * Source File: LinearCalculator.java
 ******************************************************************************/
package net.ruready.common.parser.utensil;

import net.ruready.common.rl.CommonNames;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Copyright (c) 1999 Steven J. Metsker. All Rights Reserved. Steve Metsker
 * makes no representations or warranties about the fitness of this software for
 * any particular purpose, including the implied warranty of merchantability.
 */

/**
 * A LinearCalculator models two variables that vary linearly with each other.
 * For example, Fahrenheit and Celsius temperate scales vary linearly.
 * Fahrenheit temperature varies from 32 to 212 as Celsius varies 0 to 100. A
 * LinearCalculator can model the whole scale, if it is created as: <blockquote>
 * 
 * <pre>
 * 
 * LinearCalculator lc = new LinearCalculator(32, 212, 0, 100);
 * logger.debug(lc.calculateYforGivenX(68));
 * 
 * </pre>
 * 
 * </blockquote>
 * 
 * @author Steven J. Metsker Protected by U.S. Provisional Patent U-4003,
 *         February 2006
 * @version 1.0
 */
public class LinearCalculator
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(LinearCalculator.class);

	// ========================= FIELDS ====================================

	double xFrom;

	double xTo;

	double yFrom;

	double yTo;

	/**
	 * Create a LinearCalculator from known points on two scales.
	 * 
	 * @param double
	 *            xFrom
	 * @param double
	 *            xTo
	 * @param double
	 *            yFrom
	 * @param double
	 *            yTo
	 */
	public LinearCalculator(double xFrom, double xTo, double yFrom, double yTo)
	{
		this.xFrom = xFrom;
		this.xTo = xTo;
		this.yFrom = yFrom;
		this.yTo = yTo;
	}

	/**
	 * Return the value on the first scale, corresponding to the given value on
	 * the second scale.
	 * 
	 * @return the value on the first scale, corresponding to the given value on
	 *         the second scale
	 */
	public double calculateXforGivenY(double y)
	{
		if (yTo == yFrom)
		{
			return (xFrom + xTo) / 2;
		}
		return (y - yFrom) / (yTo - yFrom) * (xTo - xFrom) + xFrom;
	}

	/**
	 * Return the value on the second scale, corresponding to the given value on
	 * the first scale.
	 * 
	 * @return the value on the second scale, corresponding to the given value
	 *         on the first scale
	 */
	public double calculateYforGivenX(double x)
	{
		if (xTo == xFrom)
		{
			return (yFrom + yTo) / 2;
		}
		return (x - xFrom) / (xTo - xFrom) * (yTo - yFrom) + yFrom;
	}

	/**
	 * Show the example in the class comment.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(String args[])
	{
		LinearCalculator lc = new LinearCalculator(32, 212, 0, 100);
		logger.debug(lc.calculateYforGivenX(68));
	}

	/**
	 * Return a textual description of this object.
	 * 
	 * @return a textual description of this object
	 */
	@Override
	public String toString()
	{
		return CommonNames.MISC.EMPTY_STRING + xFrom
				+ CommonNames.PARSER.CORE.LINEAR_CALCULATOR.SEPARATOR + xTo
				+ CommonNames.PARSER.CORE.LINEAR_CALCULATOR.RANGE_SEPARATOR + yFrom
				+ CommonNames.PARSER.CORE.LINEAR_CALCULATOR.SEPARATOR + yTo;
	}
}
