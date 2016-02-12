/*******************************************************
 * Source File: Univariate.java
 *******************************************************/
package net.ruready.common.math.real;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Nava L. Livne <i>&lt;nlivne@aoce.utah.edu&gt;</i> Academic Outreach and
 *         Continuing Education (AOCE) 1901 East South Campus Dr., Room 2197-E
 *         University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i>&lt;olivne@aoce.utah.edu&gt;</i> AOCE, Room 2197-E,
 *         University of Utah University of Utah, Salt Lake City, UT 84112
 *         (c) 2006-07 Continuing Education , University of Utah .  All copyrights reserved. U.S. Patent Pending DOCKET NO. 00846 25702.PROV
 * @version Feb 23, 2006 A univariate random variable. Source: Sundar Dorai-Raj
 *          <i><sdoraira@vt.edu><i> http://www.stat.vt.edu/~sundar/java/
 */
public class Univariate
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Univariate.class);

	// ========================= FIELDS ====================================

	private double[] x, sortx;

	private double[] summary = new double[6];

	private boolean isSorted = false;

	public double[] five = new double[5];

	private int n;

	private double mean, variance, stdev;

	// ========================= METHODS ===================================

	public Univariate(double[] data)
	{
		x = data.clone();
		n = x.length;
		createSummaryStats();
	}

	private void createSummaryStats()
	{
		int i;
		mean = 0;
		for (i = 0; i < n; i++)
			mean += x[i];
		mean /= n;
		variance = variance();
		stdev = stdev();

		double sumxx = 0;
		variance = 0;
		for (i = 0; i < n; i++)
			sumxx += x[i] * x[i];
		if (n > 1)
			variance = (sumxx - n * mean * mean) / (n - 1);
		stdev = Math.sqrt(variance);
	}

	public double[] summary()
	{
		summary[0] = n;
		summary[1] = mean;
		summary[2] = variance;
		summary[3] = stdev;
		summary[4] = Math.sqrt(variance / n);
		summary[5] = mean / summary[4];
		return (summary);
	}

	public double mean()
	{
		return (mean);
	}

	public double variance()
	{
		return (variance);
	}

	public double stdev()
	{
		return (stdev);
	}

	public double SE()
	{
		return (Math.sqrt(variance / n));
	}

	public double max()
	{
		if (!isSorted)
			sortx = sort();
		return (sortx[n - 1]);
	}

	public double min()
	{
		if (!isSorted)
			sortx = sort();
		return (sortx[0]);
	}

	public double median()
	{
		return (quant(0.50));
	}

	public double quant(double q)
	{
		if (!isSorted)
			sortx = sort();
		if (q > 1 || q < 0)
			return (0);
		else {
			double index = (n + 1) * q;
			if (index - (int) index == 0)
				return sortx[(int) index - 1];
			else
				return q * sortx[(int) Math.floor(index) - 1] + (1 - q)
						* sortx[(int) Math.ceil(index) - 1];
		}
	}

	public double[] sort()
	{
		sortx = x.clone();
		int incr = (int) (n * .5);
		while (incr >= 1) {
			for (int i = incr; i < n; i++) {
				double temp = sortx[i];
				int j = i;
				while (j >= incr && temp < sortx[j - incr]) {
					sortx[j] = sortx[j - incr];
					j -= incr;
				}
				sortx[j] = temp;
			}
			incr /= 2;
		}
		isSorted = true;
		return (sortx);
	}

	public double[] getData()
	{
		return (x);
	}

	public int size()
	{
		return (n);
	}

	public double elementAt(int index)
	{
		double element = 0;
		try {
			element = x[index];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			logger.info("Index " + index + " does not exist in data.");
		}
		return (element);
	}

	public double[] subset(int[] indices)
	{
		int k = indices.length, i = 0;
		double elements[] = new double[k];
		try {
			for (i = 0; i < k; i++)
				elements[i] = x[k];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			logger.info("Index " + i + " does not exist in data.");
		}
		return (elements);
	}

	public int compare(double t)
	{
		int index = n - 1;
		int i;
		boolean found = false;
		for (i = 0; i < n && !found; i++)
			if (sortx[i] > t) {
				index = i;
				found = true;
			}
		return (index);
	}

	public int[] between(double t1, double t2)
	{
		int[] indices = new int[2];
		indices[0] = compare(t1);
		indices[1] = compare(t2);
		return (indices);
	}

	public int indexOf(double element)
	{
		int index = -1;
		for (int i = 0; i < n; i++)
			if (Math.abs(x[i] - element) < 1e-6)
				index = i;
		return (index);
	}
}
