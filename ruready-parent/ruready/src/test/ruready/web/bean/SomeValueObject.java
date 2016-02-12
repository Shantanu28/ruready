/*******************************************************
 * Source File: SomeValueObject.java
 *******************************************************/
package test.ruready.web.bean;

import net.ruready.web.common.util.StrutsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SomeValueObject
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SomeValueObject.class);

	// ========================= FIELDS ====================================

	private int level;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * 
	 */
	public SomeValueObject()
	{
		super();
	}

	// ========================= METHODS ===================================

	/**
	 * Print this form bean.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return StrutsUtil.formToString(this);
	}

	/**
	 * @return the level
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}
}
