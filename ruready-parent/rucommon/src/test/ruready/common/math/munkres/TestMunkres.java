/*****************************************************************************************
 * Source File: TestMunkres.java
 ****************************************************************************************/
package test.ruready.common.math.munkres;

import java.util.HashMap;
import java.util.Map;

import net.ruready.common.math.munkres.AssignmentType;
import net.ruready.common.math.munkres.Munkres;
import net.ruready.common.math.real.RealUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

/**
 * Tests the Munkres optimal assignment algorithm on hard-coded cost matrices.
 * <p>
 * The assignment problem is one of the fundamental combinatorial optimization
 * problems in the branch of optimization or operations research in mathematics.
 * <p>
 * In its most general form, the problem is as follows: There are a number of
 * agents and a number of tasks. Any agent can be assigned to perform any task,
 * incurring some cost that may vary depending on the agent-task assignment. It
 * is required to perform all tasks by assigning exactly one agent to each task
 * in such a way that the total cost of the assignment is minimized. If the
 * numbers of agents and tasks are equal and the total cost of the assignment
 * for all tasks is equal to the sum of the costs for each agent (or the sum of
 * the costs for each task, which is the same thing in this case), then the
 * problem is called the Linear assignment problem. Commonly, when speaking of
 * the Assignment problem without any additional qualification, then the Linear
 * assignment problem is meant.
 * <p>
 * The Hungarian algorithm is one of many algorithms that have been devised that
 * solve the linear assignment problem within time bounded by a polynomial
 * expression of the number of agents. The Hungarian algorithm is a
 * combinatorial optimization algorithm which solves assignment problems in
 * polynomial time. The first version, known as the Hungarian method, was
 * invented and published by Harold Kuhn in 1955. This was revised by James
 * Munkres in 1957, and has been known since as the Hungarian algorithm, the
 * Munkres assignment algorithm, or the Kuhn-Munkres algorithm.
 * <p>
 * References: http://en.wikipedia.org/wiki/Munkres'_assignment_algorithm
 * http://www.arcavia.com/kyle/BLOG/arc20060101.htm#BlogID25
 * http://www.public.iastate.edu/~ddoty/HungarianAlgorithm.html
 * <p>
 * -------------------------------------------------------------------------<br>
 * (c) 2006-2007 Continuing Education, University of Utah<br>
 * All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * <p>
 * This file is part of the RUReady Program software.<br>
 * Contact: Nava L. Livne <code>&lt;nlivne@aoce.utah.edu&gt;</code><br>
 * Academic Outreach and Continuing Education (AOCE)<br>
 * 1901 East South Campus Dr., Room 2197-E<br>
 * University of Utah, Salt Lake City, UT 84112-9399<br>
 * U.S.A.<br>
 * Day Phone: 1-801-587-5835, Fax: 1-801-585-5414<br>
 * <br>
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 26, 2007
 */
public class TestMunkres extends TestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestMunkres.class);

	// ========================= IMPLEMENTATION: TestBase ==================

	// ========================= TESTING METHODS ===========================

	/**
	 * Test some hard-coded matrices.
	 */
	@Test
	public void testHardcodedData1()
	{
		// C = cost matrix, C 3x3, C_{i,j} = (i-1)*(j-1)
		final double costMatrixArray[][] =
		{
		{ 1, 2, 3 },
		{ 2, 4, 6 },
		{ 3, 6, 9 } };
		final Matrix costMatrix = MatrixFactory.getMatrix(3, 3, null, costMatrixArray);
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		expectedAssignment.put(1, 3);
		expectedAssignment.put(2, 2);
		expectedAssignment.put(3, 1);
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment, 10.0);
	}

	/**
	 * Test some hard-coded matrices.
	 */
	@Test
	public void testHardcodedData2()
	{
		// C = cost matrix, C 4x4, C_{i,j} = (i-1)*(j-1)
		final double costMatrixArray[][] =
		{
		{ 1, 2, 3, 4 },
		{ 2, 4, 6, 8 },
		{ 3, 6, 9, 12 },
		{ 4, 8, 12, 16 } };
		final Matrix costMatrix = MatrixFactory.getMatrix(4, 4, null, costMatrixArray);
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		expectedAssignment.put(1, 4);
		expectedAssignment.put(2, 3);
		expectedAssignment.put(3, 2);
		expectedAssignment.put(4, 1);
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment, 20.0);
	}

	/**
	 * Test some hard-coded matrices.
	 */
	@Test
	public void testHardcodedData3()
	{
		// test0 from a previous LinearAssignment test case
		final double costMatrixArray[][] =
		{
		{ 2, 5, 3 },
		{ 3, 1, 4 },
		{ 6, 3, 10 } };
		final Matrix costMatrix = MatrixFactory.getMatrix(3, 3, null, costMatrixArray);
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		expectedAssignment.put(1, 3);
		expectedAssignment.put(2, 1);
		expectedAssignment.put(3, 2);
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment, 9.0);
	}

	/**
	 * Test some hard-coded matrices.
	 */
	@Test
	public void testHardcodedData4()
	{
		// test1 from a previous LinearAssignment test case
		// More columns than rows
		final double costMatrixArray[][] =
		{
		{ 1, 1, 3, 4, 5 },
		{ 2, 4, 2, 8, 0 },
		{ 3, 6, 9, 12, 7 },
		{ 4, 8, 12, 0, 9 } };
		final Matrix costMatrix = MatrixFactory.getMatrix(4, 5, null, costMatrixArray);
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		expectedAssignment.put(1, 2);
		expectedAssignment.put(2, 5);
		expectedAssignment.put(3, 1);
		expectedAssignment.put(4, 4);
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment, 4.0);
	}

	/**
	 * Test some hard-coded matrices.
	 */
	@Test
	public void testHardcodedData5()
	{
		// test2 from a previous LinearAssignment test case
		// This is the transpose of test1. Should yield the transpose of the
		// assignment for test1.
		final double costMatrixArray[][] =
		{
		{ 1, 2, 3, 4 },
		{ 1, 4, 6, 8 },
		{ 3, 1, 9, 12 },
		{ 4, 8, 12, 0 },
		{ 4, 0, 7, 9 } };
		final Matrix costMatrix = MatrixFactory.getMatrix(5, 4, null, costMatrixArray);
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		expectedAssignment.put(1, 3);
		expectedAssignment.put(2, 1);
		expectedAssignment.put(4, 4);
		expectedAssignment.put(5, 2);
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment, 4.0);
	}

	/**
	 * Test some hard-coded matrices.
	 */
	@Test
	public void testHardcodedData6()
	{
		// test3 from a previous LinearAssignment test case
		// A model of checking correctness
		// 0=equals 1=not equals
		final double costMatrixArray[][] =
		{
		{ 1, 0, 1, 1, 1 },
		{ 0, 1, 1, 1, 1 },
		{ 1, 1, 1, 1, 1 },
		{ 1, 1, 1, 1, 0 } };
		final Matrix costMatrix = MatrixFactory.getMatrix(4, 5, null, costMatrixArray);
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		expectedAssignment.put(1, 2);
		expectedAssignment.put(2, 1);
		expectedAssignment.put(3, 3);
		expectedAssignment.put(4, 5);
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment, 1.0);
	}

	/**
	 * Test some hard-coded matrices.
	 */
	@Test
	public void testHardcodedData7()
	{
		// test4 from a previous LinearAssignment test case
		// A model of checking correctness
		// 0=equals 1=not equals
		final double costMatrixArray[][] =
		{
		{ 1, 0, 1 },
		{ 0, 1, 1 },
		{ 1, 1, 1 },
		{ 1, 1, 0 } };
		final Matrix costMatrix = MatrixFactory.getMatrix(4, 3, null, costMatrixArray);
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		expectedAssignment.put(1, 2);
		expectedAssignment.put(2, 1);
		expectedAssignment.put(4, 3);
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment, 0.0);
	}

	/**
	 * Test some hard-coded matrices.
	 */
	@Test
	public void testHardcodedData8()
	{
		// Oren's hand-solved test case, 08-JUN-2007
		final double costMatrixArray[][] =
		{
		{ 8, 5, 2 },
		{ 3, 6, 7 },
		{ 4, 4, 4 } };
		final Matrix costMatrix = MatrixFactory.getMatrix(3, 3, null, costMatrixArray);
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		expectedAssignment.put(1, 3);
		expectedAssignment.put(2, 1);
		expectedAssignment.put(3, 2);
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment, 9.0);

	}

	/**
	 * Test some template matrices of various sizes and with various parameter
	 * values.
	 * 
	 * @TODO convert to a theory/parameter annotation
	 */
	@Test
	public void testParameterizedTemplates()
	{
		for (int n = 1; n <= 10; n++)
		{
			testITimesJMin(n);
			testITimesJMax(n);
		}
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Test a minimum-cost assignment for a cost matrix C of the form: C nxn,
	 * C_{i,j} = (i-1)*(j-1)
	 * 
	 * @param n
	 *            matrix size
	 */
	private void testITimesJMin(final int n)
	{
		logger.info("========= Testing Munkres for Cij = i*j, matrix size " + n
				+ " =========");

		// Prepare cost matrix using the JMatrices package
		Matrix costMatrix = MatrixFactory.getMatrix(n, n, null);
		for (int row = 1; row <= costMatrix.rows(); row++)
		{
			for (int col = 1; col <= costMatrix.cols(); col++)
			{
				costMatrix.set(row, col, row * col);
			}
		}

		// expected assignment is along the "reverse" diagonal
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		double expectedCost = 0.0;
		for (int row = 1; row <= costMatrix.rows(); row++)
		{
			int col = n - row + 1;
			expectedAssignment.put(row, col);
			expectedCost += costMatrix.get(row, col);
		}

		// Run the test
		this.testHardcodedData(costMatrix, AssignmentType.MIN, expectedAssignment,
				expectedCost);
	}

	/**
	 * Test a maximum-cost assignment for a cost matrix C of the form: C nxn,
	 * C_{i,j} = (i-1)*(j-1)
	 * 
	 * @param n
	 *            matrix size
	 */
	private void testITimesJMax(final int n)
	{
		logger.info("========= Testing Munkres for Cij = i*j, matrix size " + n
				+ " =========");

		// Prepare cost matrix using the JMatrices package
		Matrix costMatrix = MatrixFactory.getMatrix(n, n, null);
		for (int row = 1; row <= costMatrix.rows(); row++)
		{
			for (int col = 1; col <= costMatrix.cols(); col++)
			{
				costMatrix.set(row, col, row * col);
			}
		}

		// expected assignment is along the "reverse" diagonal
		final Map<Integer, Integer> expectedAssignment = new HashMap<Integer, Integer>();
		double expectedCost = 0.0;
		for (int row = 1; row <= costMatrix.rows(); row++)
		{
			int col = row;
			expectedAssignment.put(row, col);
			expectedCost += costMatrix.get(row, col);
		}

		// Run the test
		this.testHardcodedData(costMatrix, AssignmentType.MAX, expectedAssignment,
				expectedCost);
	}

	/**
	 * Test a hard-coded matrix.
	 */
	private void testHardcodedData(final Matrix costMatrix,
			final AssignmentType assignmentType,
			final Map<Integer, Integer> expectedAssignment, final double expectedCost)
	{
		logger.info("----------------------- Testing Munkres -----------------------");
		logger.info("cost matrix\n" + costMatrix);

		Munkres munkres = new Munkres(costMatrix);
		munkres.solve(assignmentType);
		Map<Integer, Integer> actualAssignment = munkres.getAssignment();
		double actualCost = munkres.getBestCost();

		logger.info("actual   assignment " + actualAssignment);
		logger.info("expected assignment " + expectedAssignment);
		logger.info("actual   cost       " + actualCost);
		logger.info("expected cost       " + expectedCost);

		// In principle the solution is not unique, so remove the mapping
		// equality check for such cases.
		Assert.assertEquals(expectedAssignment, actualAssignment);
		Assert.assertEquals(expectedCost, actualCost, RealUtil.MACHINE_DOUBLE_ERROR
				* Math.abs(expectedCost));
	}

	// ========================= PRIVATE METHODS ===========================
}
