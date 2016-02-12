package net.ruready.common.chemistry;

import net.ruready.common.io.In;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A chemical element. Includes a demo. Compilation: javac Element.java Execution: java
 * Element < ../datafiles/elements.txt Dependencies: In.java Data type for elements in
 * periodic table. Store name, atomic number, symbol, and atomic.
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
 * @version Aug 14, 2007
 */
public class Element
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Element.class);

	// ========================= FIELDS ====================================

	private String name; // name of element

	private int number; // number in periodic table

	private String symbol; // atomic symbol

	private double weight; // atomic weight

	public Element(String name, int number, String symbol, double weight)
	{
		this.name = name;
		this.number = number;
		this.symbol = symbol;
		this.weight = weight;
	}

	@Override
	public String toString()
	{
		String s = "";
		s = s + name + " (" + symbol + ")\n";
		s = s + "Atomic number: " + number + "\n";
		s = s + "Atomic weight: " + weight + "\n";
		return s;
	}

	public static void main(String[] args)
	{
		int ELEMENTS = 103;
		Element[] elements = new Element[ELEMENTS];
		In in = new In();

		// ignore first line
		String s = in.readLine();

		// read data
		for (int i = 0; i < ELEMENTS; i++)
		{
			s = in.readLine();
			s = s.replaceAll("\t\t", "\t"); // data file has some double tabs
			String[] fields = s.split("\t");
			String name = fields[0];
			int number = Integer.parseInt(fields[1]);
			String symbol = fields[2];
			double weight = Double.parseDouble(fields[3]);
			elements[i] = new Element(name, number, symbol, weight);
			logger.debug(elements[i]);
		}
	}
}
