package net.ruready.eis.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class UtcDateTimeType implements UserType
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SQL type.
	 */
	private static final int[] SQL_TYPES =
	{
		Types.BIGINT
	};

	/**
	 * Make a copy of the date.
	 * 
	 * @see UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object obj) throws HibernateException
	{
		return (obj == null) ? null : new Date(((Date) obj).getTime());
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
	 * Dates are mutable.
	 * 
	 * @see net.sf.hibernate.UserType#isMutable()
	 */
	public boolean isMutable()
	{
		return true;
	}

	/**
	 * Return an instance of the date or null if no value is specified.
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
		Date date;
		if (rs.wasNull())
		{
			date = null;
		}
		else
		{
			date = new Date(value);
		}
		return date;

	}

	/**
	 * Set an instance of the date into the database field.
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
			Date date = (Date) value;
			statement.setLong(index, date.getTime());
		}
	}

	/**
	 * Return the {@link Date} class.
	 * 
	 * @see net.sf.hibernate.UserType#returnedClass()
	 */
	public Class<?> returnedClass()
	{
		return Date.class;
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
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable,
	 *      java.lang.Object)
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
}
