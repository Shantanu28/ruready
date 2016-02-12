/*******************************************************
 * Source File: GrayCode.java
 *******************************************************/
package net.ruready.common.math.basic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This function returns a variable-base multiple-digit gray code. G =
 * grayCode(N,K) returns the gray code permutation of the integers from 0 to
 * prod(K)-1. N bust be a non-negative integer and K must be an N-vector of
 * non-negative integers of bases. K[0] is the base of the right-most digit
 * (LSB) in the N-digit string space, K[1] the base of the next right digit, and
 * so on. The generated gray code is not necssarily cyclic. G is an array of
 * size prod(K)xN, whose rows are the gray-code-ordered N-digit strings.
 * 
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version 03/12/2005
 */
public class GrayCode
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(GrayCode.class);

	protected static final int verbose = 0;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * <p>
	 * Hide constructor in utility class.
	 * </p>
	 */
	private GrayCode()
	{
	}

	// ========================= METHODS ===================================

	/**
	 * Generate a base-k, length-n gray code. WARNING: this code is very very
	 * slow for some reason!
	 * 
	 * @param n
	 *            #digits
	 * @param k
	 *            base for each digit
	 * @return gray code
	 * @deprecated
	 */
	@Deprecated
	public static int[][] generate(int n, int[] k)
	{
		logger.debug("n " + n + " k " + k);
		// Assumptions
		assert (n > 0);
		assert (k.length == n);

		int numRows = 1;
		int numCols = 0;
		int m = 0;
		numRows *= k[m];
		numCols++;
		int[][] G = new int[numRows][];
		for (int i = 0; i < numRows; i++) {
			G[i] = new int[numCols];
		}
		for (int i = 0; i < k[0]; i++) {
			G[i][m] = i;
		}
		{
			if (verbose >= 1) {
				logger.debug("G numRows=" + G.length + " numCols = "
						+ G[0].length);
				logger.debug("  G for " + m + " digits = " + G);
			}
		}

		// Generate G recursively
		for (m = 1; m < n; m++) {
			int b = k[m];
			numRows *= b;
			numCols++;
			int[][] Gnew = new int[numRows][];
			for (int i = 0; i < numRows; i++) {
				Gnew[i] = new int[numCols];
			}
			int startRow = 0;
			if (verbose >= 1) {
				logger.debug("m = " + m + " " + "b = " + b);
			}
			for (int d = 0; d < b; d++) {
				if ((d % 2) == 1) { // d odd
					if (verbose >= 1) {
						logger.debug("  (G*)^(" + d + ")");
					}
					for (int i = 0; i < G.length; i++) {
						for (int j = 0; j < G[i].length; j++) {
							// Gnew(startRow+G.numRows()-1-i,j) = G(i,j);
							Gnew[startRow + G.length - 1 - i][j] = G[i][j];
						}
						// Gnew(startRow+G.numRows()-1-i,m) = d;
						Gnew[startRow + G.length - 1 - i][m] = d;
					}
				}
				else { // d even
					if (verbose >= 1) {
						logger.debug("  G^(" + d + ")");
					}
					for (int i = 0; i < G.length; i++) {
						for (int j = 0; j < G[i].length; j++) {
							// Gnew(startRow+i,j) = G(i,j);
							Gnew[startRow + i][j] = G[i][j];
						}
						// Gnew(startRow+i,m) = d;
						Gnew[startRow + i][m] = d;
					}
				} // end d even
				startRow += G.length;
			} // end for d

			G = Gnew;
			/*
			 * G.setSize(Gnew.size()); for (int i = 0; i < Gnew.size(); i++) {
			 * G.set(i, new Array<Integer>());
			 * G.get(i).setSize(Gnew.get(i).size()); for (int j = 0; j <
			 * Gnew.get(i).size(); j++) { G.get(i).set(j, Gnew.get(i).get(j)); } }
			 */
			if (verbose >= 1) {
				logger.debug("G numRows=" + G.length + " numCols = "
						+ G[0].length);
				logger.debug("  G for " + m + " digits = " + G);
			}
		} // end for m

		// Check result
		boolean fail = false;
		for (int i = 0; i < G.length - 1; i++) {
			int diff = 0;
			for (int j = 0; j < G[i].length; j++) {
				diff += (Math.abs(G[i][j] - G[i + 1][j]));
			}
			if (diff != 1) {
				fail = true;
				logger.debug("failed in difference between rows " + i + " and "
						+ (i + 1));
				break;
			}
		} // end for i

		for (int i = 0; (i < G.length) && (!fail); i++) {
			for (int i2 = 0; i2 < G.length; i2++) {
				if (i != i2) {
					int diff = 0;
					for (int j = 0; j < G[i].length; j++) {
						diff += (Math.abs(G[i][j] - G[i2][j]));
					}
					if (diff == 0) {
						fail = true;
						logger.debug("failed in equality of rows " + i
								+ " and " + i2);
						break;
					}
				}
			} // end for i2
		} // end for i
		if (fail) {
			logger.debug("Gray code is incorrect!!!");
		}
		else {
			if (verbose >= 1) {
				logger.debug("Gray code is correct.");
			}
		}

		logger.debug("size(G) " + G.length);
		return G;
	} // end generate()

	/**
	 * Function Box::iterator::operator++() Increment the d-dimensional
	 * subscript sub. This is useful when looping over a volume or an area.
	 * active is a d- boolean array. Indices with active=false are kept fixed,
	 * and those with active=true are updated. lower,upper specify the extents
	 * of the hypercube we loop over. eof is returned as 1 if we're at the end
	 * of the cube (the value of sub is set to lower for active=1 indices; i.e.
	 * we do a periodic looping) and 0 otherwise. E.g., incrementing sub=(2,0,1)
	 * with active=(0,1,0), lower=(0,0,0) and upper=(2,2,2) results in
	 * sub=(0,0,2) and eof=1. If sub were (2,0,2) then sub=(0,0,0) and eof=1.
	 * 
	 * @return is iterator EOF reached
	 */
	public static boolean plusPlus(int[] sub, int[] lower, int upper[])
	{
		int numDims = sub.length;
		int d = 0;
		sub[d]++;
		if (sub[d] > upper[d]) {
			while (sub[d] > upper[d]) {
				sub[d] = lower[d];
				d++;
				if (d == numDims) { // end of box reached
					sub[0] -= 1; // So that iter is now Box::end() that is
					// outside the box
					return true;
				}
				sub[d]++;
			}
		}
		return false;
	}
}
