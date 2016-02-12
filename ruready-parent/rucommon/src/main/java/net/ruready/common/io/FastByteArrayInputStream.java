package net.ruready.common.io;

import java.io.InputStream;

/**
 * A fast {@link InputStream} implementation that does not synchronize methods.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Sep 26, 2007
 */
public class FastByteArrayInputStream extends InputStream
{
	// ========================= CONSTANTS =================================

	// ========================= FIELDS ====================================

	/**
	 * Our byte buffer
	 */
	protected byte[] buf = null;

	/**
	 * Number of bytes that we can read from the buffer
	 */
	protected int count = 0;

	/**
	 * Number of bytes that have been read from the buffer
	 */
	protected int pos = 0;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * @param buf
	 * @param count
	 */
	public FastByteArrayInputStream(byte[] buf, int count)
	{
		this.buf = buf;
		this.count = count;
	}

	// ========================= IMPLEMENTATION: InputStream ===============

	/**
	 * @return
	 * @see java.io.InputStream#available()
	 */
	@Override
	public final int available()
	{
		return count - pos;
	}

	/**
	 * @return
	 * @see java.io.InputStream#read()
	 */
	@Override
	public final int read()
	{
		return (pos < count) ? (buf[pos++] & 0xff) : -1;
	}

	/**
	 * @param b
	 * @param off
	 * @param len
	 * @return
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	@Override
	public final int read(final byte[] b, final int off, final int len0)
	{
		int len = len0;
		if (pos >= count)
			return -1;

		if ((pos + len) > count)
			len = (count - pos);

		System.arraycopy(buf, pos, b, off, len);
		pos += len;
		return len;
	}

	/**
	 * @param n
	 * @return
	 * @see java.io.InputStream#skip(long)
	 */
	@Override
	public final long skip(final long n0)
	{
		long n = n0;
		if ((pos + n) > count)
			n = count - pos;
		if (n < 0)
			return 0;
		pos += n;
		return n;
	}

}
