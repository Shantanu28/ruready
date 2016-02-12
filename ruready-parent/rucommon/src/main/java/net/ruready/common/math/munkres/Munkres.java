/*******************************************************************************
 * Source File: Munkres.java
 ******************************************************************************/
package net.ruready.common.math.munkres;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.ruready.common.math.basic.IntegerPair;
import net.ruready.common.math.real.RealUtil;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.measure.MatrixMeasure;
import org.jmatrices.dbl.transformer.MatrixEBETransformation;
import org.jmatrices.dbl.transformer.MatrixEBETransformer;
import org.jmatrices.dbl.transformer.MatrixTransformer;

/**
 * An implementation of the Munkres optimal assignment algorithm between two
 * sets of items.
 * 
 * @see http://216.249.163.93/bob.pilgrim/445/munkres.html
 * @see http://www.spatial.maine.edu/~kostas/dev/soft/munkres.htm
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112 (c) 
 *         2006-07 Continuing Education , University of Utah . All copyrights
 *         reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Jun 7, 2007
 */
public final class Munkres
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Munkres.class);

	// Munkres marking conventions
	private static final byte NOT_MARKED = 0;

	private static final byte STARRED_ZERO = 1;

	private static final byte PRIMED_ZERO = 2;

	// Mapping and cost matrix are indexBase-based (e.g. 0-based; normally
	// 1-based)
	public static final int INDEX_BASE = 1;

	// ========================= FIELDS ====================================

	// ==================================
	// Input fields
	// ==================================

	// Original cost matrix
	private final Matrix costMatrix;

	// ==================================
	// Convenient local variables
	// ==================================

	// Workspace cost matrix (transposed and/or negated if necessary)
	private Matrix workCostMatrix;

	// Workspace cost matrix as an array (transposed and/or negated if
	// necessary)
	private double[][] workCostArray;

	// Do we have to transpose the cost matrix or not
	private boolean transpose = false;

	// # rows in cost matrix
	int numCols;

	// # columns in cost matrix
	int numRows;

	// Mark-zeros map
	byte[][] mark;

	// Array that holds 0 if a row is not covered and
	// 1 if covered
	int[] rowIsCovered;

	// Array that holds 0 if a column is not covered and
	// 1 if covered
	int[] colIsCovered;

	// ==================================
	// Output fields (results of solve())
	// ==================================

	// Assignment of cost matrix rows to columns
	Map<Integer, Integer> assignment = new HashMap<Integer, Integer>();

	// Total cost for best assignment
	private double bestCost;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Set up a Munkres algorithm object for a cost matrix.
	 * 
	 * @param costMatrix
	 *            input cost matrix
	 */
	public Munkres(final Matrix costMatrix)
	{
		super();
		this.costMatrix = costMatrix;
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * Print the marked cost matrix (reduced rows, primes and stars near zeros,
	 * uncovered rows and columns marks).
	 * 
	 * @return marked cost matrix
	 */
	@Override
	public String toString()
	{
		StringBuffer output = new StringBuffer();
		for (int j = 0; j < numCols; j++) {
			output.append(CommonNames.MUNKRES.SEPARATOR).append(colIsCovered[j]);
		}
		output.append(CommonNames.MISC.NEW_LINE_CHAR);
		for (int i = 0; i < numRows; i++) {
			output.append(rowIsCovered[i]);
			for (int j = 0; j < numCols; j++) {
				output.append(CommonNames.MUNKRES.SEPARATOR).append(
						workCostArray[i][j]);
				if (mark[i][j] == STARRED_ZERO) {
					output.append(CommonNames.MUNKRES.STARRED_ZERO_STRING);
				}
				else if (mark[i][j] == PRIMED_ZERO) {
					output.append(CommonNames.MUNKRES.PRIMED_ZERO_STRING);
				}
			}
			output.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		return output.toString();
	} // toString()

	// ========================= PUBLIC METHODS ============================

	/**
	 * Run the Munkres algorithm and find the best-cost mapping and
	 * corresponding best cost. (Internally, if the number of rows is greater
	 * than the number of columns, the matrix is transposed. If the maximum cost
	 * is sought, the maximum entry is subtracted from all entries and then all
	 * entries are negated.)
	 * 
	 * @param assignmentType
	 *            type of cost optimization: minimizing or maximizing the totla
	 *            assignment cost
	 * @return a map: row -> column
	 */
	public void solve(final AssignmentType assignmentType)
	{
		prepareWorkArrays(assignmentType);

		// Carry out of the steps of the Munkres Hungarian algorithm
		reduceRows();
		starSomeZeros();
		while (true) {
			if (isDone())
				break;
			IntegerPair p = findAndPrimeUncoveredZero();
			new Step5().main(p);
		}

		// Set the assignment field
		this.prepareAssignment();

		// Set the total cost field
		bestCost = Munkres.totalCost(costMatrix, assignment);
	} // solve()

	/**
	 * Print the assignment results into a string
	 * 
	 * @return
	 */
	public String resultsToString()
	{
		StringBuffer s = TextUtil.emptyStringBuffer();
		s.append("cost matrix = ");
		s.append(CommonNames.MISC.NEW_LINE_CHAR);
		for (int i = 0; i < costMatrix.rows(); i++) {
			for (int j = 0; j < costMatrix.cols(); j++) {
				s.append(costMatrix.get(i, j)).append(CommonNames.MUNKRES.SEPARATOR);
			}
			s.append(CommonNames.MISC.NEW_LINE_CHAR);
		}
		s.append(CommonNames.MISC.NEW_LINE_CHAR);
		s.append("assignment = " + assignment);
		s.append("best cost  = " + bestCost);
		return s.toString();
	}

	// ========================= PRIVATE METHODS - UTILITIES ===============

	/**
	 * Prepare the workspace matrices and arrays.
	 * 
	 * @param assignmentType
	 *            type of cost optimization: minimizing or maximizing the totla
	 *            assignment cost
	 */
	private void prepareWorkArrays(final AssignmentType assignmentType)
	{
		// Copy the original cost matrix into a working cost matrix and array
		workCostMatrix = MatrixFactory.getMatrix(costMatrix.rows(), costMatrix
				.cols(), costMatrix, costMatrix.get());

		// The algorithm in this class assumes we are looking for the minimum-
		// cost assignment. If we seek the maximum cost instead, convert each
		// element in the cost matrix C as folows: C(i,j) <- max(C(i,j)) -
		// C(i,j) . This reduces the problem back to minimizing cost.
		if (assignmentType == AssignmentType.MAX) {
			double maxEntry = MatrixMeasure.getMax(workCostMatrix);
			// Perform the operation: C <- maxEntry - C
			workCostMatrix = Munkres
					.scalarMinusMatrix(maxEntry, workCostMatrix);
		}

		// If cost matrix is "thin", transpose it to "fat"
		if (workCostMatrix.rows() > workCostMatrix.cols()) {
			workCostMatrix = MatrixTransformer.transpose(workCostMatrix);
			transpose = true;
		}

		// Set the matrix dimension fields
		numRows = workCostMatrix.rows();
		numCols = workCostMatrix.cols();
		workCostArray = workCostMatrix.get();

		// Allocate marking arrays
		mark = new byte[numRows][];
		for (int i = 0; i < numRows; i++) {
			mark[i] = new byte[numCols];
		}
		rowIsCovered = new int[numRows];
		colIsCovered = new int[numCols];
	}

	/**
	 * Set the assignment field based on the results of
	 * {@link #solve(AssignmentType)}.
	 */
	private void prepareAssignment()
	{
		// Find out where the non-zeros in every row of mark are; convert them
		// to a map row -> col.
		// Switch back to a 1-based indexing because this is how the original
		// matrix is defined by JMatrices.
		assignment = new HashMap<Integer, Integer>();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (mark[i][j] != 0) {
					if (transpose) {
						// Transpose the mapping to
						// correspond to the original
						// cost matrix
						assignment.put(j + Munkres.INDEX_BASE, i
								+ Munkres.INDEX_BASE);
					}
					else {
						assignment.put(i + Munkres.INDEX_BASE, j
								+ Munkres.INDEX_BASE);
					}
					break;
				}
			}
		}

	}

	/**
	 * Return the total cost of an assignment.
	 * 
	 * @param cost
	 *            matrix
	 * @param assignment
	 *            row -> column. Has to be a legal assignment map (indices in
	 *            the cost matrix), else this method will throw an exception.
	 * @return total cost of this assignment.
	 */
	private static double totalCost(final Matrix costMatrix,
			final Map<Integer, Integer> assignment)
	{
		double totalCost = 0.0;
		Set<Map.Entry<Integer, Integer>> entries = assignment.entrySet();
		Iterator<Map.Entry<Integer, Integer>> it = entries.iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Integer> entry = it.next();
			int i = entry.getKey().intValue();
			int j = assignment.get(i).intValue();
			totalCost += costMatrix.get(i, j);
		}
		return totalCost;
	}

	/**
	 * Perform the operation: scalar - M (element-by-element).
	 * 
	 * @param scalar
	 *            scalar
	 * @param m
	 *            matrix
	 * @return scalar - M (element-by-element)
	 */
	private static Matrix scalarMinusMatrix(final double scalar, final Matrix m)
	{
		return MatrixEBETransformer.ebeTransform(m,
				new MatrixEBETransformation()
				{
					public double transform(double element)
					{
						return scalar - element;
					}
				});
	}

	/**
	 * Is this cost matrix entry zero or not, up to machine precision.
	 * 
	 * @param value
	 *            cost matrix
	 * @return Is this cost matrix entry zero or not, up to machine precision
	 */
	private static boolean isZeroEntry(final double value)
	{
		return RealUtil.doubleEquals(value, 0.0D);
	} // isZeroEntry()

	// ========================= PRIVATE METHODS OF THE ALGORITHM ==========

	/**
	 * Step 1 of the Munkres algorithm: reduce rows (subtract minimal element of
	 * each row from all elements of that row).
	 */
	private void reduceRows()
	{
		double minval;
		for (int i = 0; i < numRows; i++) {
			// Find minimal element
			minval = workCostArray[i][0];
			for (int j = 1; j < numCols; j++) {
				if (minval > workCostArray[i][j]) {
					minval = workCostArray[i][j];
				}// if;
			}// for

			// Subtract minimal element from row
			for (int j = 0; j < numCols; j++) {
				workCostArray[i][j] -= minval;
			}
		}
	} // reduceRows()

	/**
	 * Step 2 of the Munkres algorithm: make sure that there is only one star
	 * per row and one per column.
	 */
	private void starSomeZeros()
	{
		boolean[] colIsCoveredHere = new boolean[numCols];
		A: for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (!colIsCoveredHere[j]
						&& Munkres.isZeroEntry(workCostArray[i][j])) {
					mark[i][j] = STARRED_ZERO;
					colIsCoveredHere[j] = true;
					continue A; // DO NOT FINISH THIS ROW
				}
			}
		}
	} // starSomeZeros()

	/**
	 * Step 3 of the Munkres algorithm: check if we are done. This method
	 * returns true iff we are done.
	 * <p>
	 * Also, cover rows and columns if we are not done yet.
	 * 
	 * @return Is the algorithm done or not
	 */
	private boolean isDone()
	{
		int count = 0;
		for (int j = 0; j < numCols; j++) {
			for (int i = 0; i < numRows; i++) {
				if (mark[i][j] == STARRED_ZERO) {
					colIsCovered[j] = 1;
					count++;
					break;
				}
			}
		}
		return count >= numRows;
	} // isDone()

	/**
	 * Munkres algorithm step 4: Find an uncovered zero and prime it.
	 * 
	 * @return a pair (i,j) = location of the zero
	 */
	private IntegerPair findAndPrimeUncoveredZero()
	{
		while (true) {
			IntegerPair p = findUncoveredZero();
			if (p == null) {
				// Can't find a zero
				makeSomeZeros(); // Make a zero
				continue; // Try again
			}

			mark[p.getLeft().intValue()][p.getRight().intValue()] = PRIMED_ZERO;

			// find_star_in_row
			boolean tryAgain = false;
			for (int j = 0; j < numCols; j++) {
				if (mark[p.getLeft().intValue()][j] == STARRED_ZERO) {
					rowIsCovered[p.getLeft()] = 1;
					colIsCovered[j] = 0;
					tryAgain = true;
					break;
				}
				if (tryAgain) {
					break;
				}
			}
			if (tryAgain) {
				continue;
			}
			return p;
		} // while (true)
	} // findAndPrimeUncoveredZero()

	/**
	 * Return an uncovered zero location. If not found, returns null.
	 * 
	 * @return an uncovered zero index pair (i,j).
	 */
	private IntegerPair findUncoveredZero()
	{
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (Munkres.isZeroEntry(workCostArray[i][j])
						&& rowIsCovered[i] == 0 && colIsCovered[j] == 0) {
					return new IntegerPair(i, j);
				}
			}
		}
		return null;
	}

	/**
	 * Adjust (add/subtract minimal element to/from) rows and columns to make
	 * more zeros in the matrix (need to read more on the algorithm to
	 * understand what this method really does...).
	 */
	private void makeSomeZeros()
	{
		// Find smallest element in uncovered rows and
		// covered rows in the matrix.
		double minval = Float.MAX_VALUE;
		for (int i = 0; i < numRows; i++) {
			if (rowIsCovered[i] == 1)
				continue;
			for (int j = 0; j < numCols; j++) {
				if (colIsCovered[j] == 0) {
					if (minval > workCostArray[i][j]) {
						minval = workCostArray[i][j];
					}
				}
			}
		}

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (rowIsCovered[i] == 1) {
					workCostArray[i][j] += minval;
				}
				if (colIsCovered[j] == 0) {
					workCostArray[i][j] -= minval;
				}
			}
		}
	} // makeSomeZeros()

	/**
	 * Munkres algorithm step 5: Construct a series of alternating primed and
	 * starred zeros as follows.
	 */
	private class Step5
	{
		IntegerPair[] path = new IntegerPair[numRows * 2];

		public Step5()
		{
			super();
		}

		/**
		 * Find index of a starred entry in a column.
		 * 
		 * @param r
		 *            column index
		 * @return column of star in the column. Returns -1 if not found.
		 */
		private int findStarInCol(int c)
		{
			for (int i = 0; i < numRows; i++) {
				if (mark[i][c] == STARRED_ZERO) {
					return i;
				}
			}
			return -1;
		} // findStarInCol()

		/**
		 * Find index of a primed entry in a row.
		 * 
		 * @param r
		 *            row index
		 * @return index of prime in the row. Returns -1 if not found.
		 */
		private int findPrimeInRow(int r)
		{
			for (int j = 0; j < numCols; j++) {
				if (mark[r][j] == PRIMED_ZERO) {
					return j;
				}
			}
			return -1;
		} // findPrimeInRow()

		/**
		 * Remove all cover marks from rows and columns. WARNING: this function
		 * assumed that the matrix is square in the original implementation. It
		 * has been modified to accomodate general matrices. Hopefully it
		 * doesn't break anything.
		 */
		private void clear_covers()
		{
			for (int i = 0; i < numRows; i++) {
				rowIsCovered[i] = 0;
			}
			for (int j = 0; j < numCols; j++) {
				colIsCovered[j] = 0;
			}
		} // clear_covers()

		/**
		 * Clear all primed zero marks.
		 */
		private void clear_primes()
		{
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numCols; j++) {
					if (mark[i][j] == PRIMED_ZERO) {
						mark[i][j] = NOT_MARKED;
					}
				}
			}
		} // clear_primes()

		/**
		 * Main step 5 method.
		 * 
		 * @param p
		 */
		public void main(IntegerPair p)
		{
			int count = 0;
			path[count] = p;
			while (true) {
				int r = findStarInCol(path[count].getRight().intValue());
				if (r == -1)
					break;
				count++;
				path[count] = new IntegerPair(r, path[count - 1].getRight());
				int c = findPrimeInRow(path[count].getLeft().intValue());
				count++;
				path[count] = new IntegerPair(path[count - 1].getLeft()
						.intValue(), c);
			} // while (true)

			// This used to be called convert_path()
			// in the original PASCAL implementation.
			for (int i = 0; i <= count; i++) {
				int left = path[i].getLeft().intValue();
				int right = path[i].getRight().intValue();
				if (mark[left][right] == STARRED_ZERO) {
					mark[left][right] = NOT_MARKED;
				}
				else {
					mark[left][right] = STARRED_ZERO;
				}
			}

			clear_covers();
			clear_primes();
		} // main()

	} // class step5

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Returns the best assignment cost. Should be called after only
	 * {@link #solve(AssignmentType)} is inokved.
	 * 
	 * @return Returns the bestCost.
	 */
	public double getBestCost()
	{
		return bestCost;
	}

	/**
	 * Return the optiomal assignment of rows to columns with minimal total
	 * cost. Should be called after only {@link #solve(AssignmentType)} is
	 * inokved.
	 * 
	 * @return a map: row -> column
	 */
	public Map<Integer, Integer> getAssignment()
	{
		return assignment;
	}

}
