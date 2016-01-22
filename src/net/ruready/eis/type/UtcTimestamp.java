package net.ruready.eis.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Custom Hibernate type for precise UTC millisecond persistance and retrieval using a
 * Timestamp. All values represent the given time from the Unix Epoch, 1 Jan 1970
 * 00:00:00.000 See http://en.wikipedia.org/wiki/Unix_epoch for more info. Credit: Rob @ "http://www.hibernate.org/100.html
 */
public class UtcTimestamp extends Timestamp implements UserType
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * These fields handle format of date in message printouts.
	 */
	public static final String DATE_FORMAT = "MMM dd, yyyy hh:mm:ss";

	/**
	 * Gets the value of SQL_TYPES
	 * 
	 * @return the value of SQL_TYPES
	 */
	public static int[] getSQL_TYPES()
	{
		return UtcTimestamp.SQL_TYPES;
	}

	/**
	 * SQL type.
	 */
	private static final int[] SQL_TYPES =
	{
		Types.BIGINT
	};

	/**
	 * Creates a new UTCDate instance at this moment.
	 */
	public UtcTimestamp()
	{
		super(System.currentTimeMillis());
	}

	/**
	 * With the given milliseconds.
	 * 
	 * @param utcMillis
	 *            a long value
	 */
	public UtcTimestamp(long utcMillis)
	{
		super(utcMillis);
	}

	/**
	 * Make a copy of the Timestamp.
	 * 
	 * @see UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object obj) throws HibernateException
	{
		return (obj == null) ? null : new Timestamp(((Timestamp) obj).getTime());
	}

	/**
	 * Compare via {@link Object#equals(java.lang.Object)}.
	 * 
	 * @see UserType#equals(java.lang.Object, java.lang.Object)
	 */
	public boolean equals(Object x, Object y)
	{
		return (x == null) ? (y == null) : x.equals(y);
	}

	/**
	 * Timestamps are mutable.
	 * 
	 * @see net.sf.hibernate.UserType#isMutable()
	 */
	public boolean isMutable()
	{
		return true;
	}

	/**
	 * Return an instance of the Timestamp or null if no value is specified.
	 * 
	 * @param rs
	 * @param columns
	 * @param owner
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet,
	 *      java.lang.String[], java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] columns, Object owner)
		throws HibernateException, SQLException
	{

		long value = rs.getLong(columns[0]);
		Timestamp timestamp;

		if (rs.wasNull())
		{
			timestamp = null;
		}
		else
		{
			timestamp = new UtcTimestamp(value);
		}
		return timestamp;

	}

	/**
	 * Set an instance of the Timestamp into the database field.
	 * 
	 * @see net.sf.hibernate.UserType#nullSafeSet(java.sql.PreparedStatement,
	 *      java.lang.Object, int)
	 */
	public void nullSafeSet(PreparedStatement statement, Object value, int index)
		throws HibernateException, SQLException
	{

		if (value == null)
		{
			statement.setNull(index, Types.BIGINT);
		}
		else
		{
			Timestamp timestamp = (Timestamp) value;
			statement.setLong(index, timestamp.getTime());
		}
	}

	/**
	 * Return the {@link Timestamp} class.
	 * 
	 * @see net.sf.hibernate.UserType#returnedClass()
	 */
	public Class<?> returnedClass()
	{
		return UtcTimestamp.class;
	}

	/**
	 * Return the supported SQL types.
	 * 
	 * @see net.sf.hibernate.UserType#sqlTypes()
	 */
	public int[] sqlTypes()
	{
		return SQL_TYPES;
	}

	/**
	 * All dates return their value in GMT time.
	 * 
	 * @return a String value
	 */
	@Override
	public String toString()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(UtcTimestamp.DATE_FORMAT);
		dateFormat.setTimeZone(DateUtils.UTC_TIME_ZONE);
		return dateFormat.format(this);
	}

	/**
	 * @param cached
	 * @param owner
	 * @return
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable,
	 *      java.lang.Object)
	 */
	public Object assemble(Serializable cached, Object owner)
	{
		return cached;
	}

	/**
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */

	public Serializable disassemble(Object value)
	{
		return (Serializable) value;
	}

	/**
	 * @param original
	 * @param target
	 * @param owner
	 * @return
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object,
	 *      java.lang.Object)
	 */
	public Object replace(Object original, Object target, Object owner)
	{
		return original;
	}

	/**
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */

	public int hashCode(Object x)
	{
		return x.hashCode();
	}

	/**
	 * Obtain a UTCDate from a Date.
	 * 
	 * @param date
	 *            a java.util.Date value
	 * @return an UTCDate value
	 */
	public static UtcTimestamp valueOf(java.util.Date date)
	{
		UtcTimestamp UTCDate = new UtcTimestamp(date.getTime());

		return UTCDate;

	}

}
