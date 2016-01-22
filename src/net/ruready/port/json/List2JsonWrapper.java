package net.ruready.port.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ruready.common.misc.Immutable;
import net.ruready.eis.factory.imports.HibernateDAO;
import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.bean.BeanMorpher;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A simple record in a result set for JSON experiments.
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
 * Please contact these numbers immediately if you receive this file without
 * permission from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Nov 22, 2007
 */
public class List2JsonWrapper<T> implements Immutable
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(HibernateDAO.class);

	// ========================= FIELDS ====================================

	/**
	 * The collection's entry class.
	 */
	private Class<T> dataClass;

	/**
	 * Data collection.
	 */
	private List<? extends T> data;

	/**
	 * Convenient size property. Inferred from data.
	 */
	private int size;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * This constructor should not be used. It is here only so that this class
	 * can be treated as a bean by the EZMorph library.
	 */
	public List2JsonWrapper()
	{
		this.dataClass = null;
		this.data = null;
		this.size = 0;
	}

	/**
	 * Construct a list JSON wrapper.
	 * 
	 * @param dataClass
	 *            The collection's entry class
	 * @param data
	 *            Data collection to wrap
	 */
	private List2JsonWrapper(final Class<T> dataClass, final List<? extends T> data)
	{
		super();
		this.dataClass = dataClass;
		this.data = data;
		this.size = data.size();
	}

	// ========================= IMPLEMENTATION: Object ====================

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "data:" + data;
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((dataClass == null) ? 0 : dataClass.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final List2JsonWrapper<T> other = (List2JsonWrapper<T>) obj;
		if (data == null)
		{
			if (other.data != null)
				return false;
		}
		else if (!data.equals(other.data))
			return false;
		if (dataClass == null)
		{
			if (other.dataClass != null)
				return false;
		}
		else if (!dataClass.equals(other.dataClass))
			return false;
		return true;
	}

	// ========================= METHODS ===================================

	/**
	 * Convert a list to a JSON string.
	 * 
	 * @param dataClass
	 *            The collection's entry class
	 * @param data
	 *            Data collection to wrap
	 * @return wrapper list string representation in JSON string
	 */
	public static <T> String toJSONString(final Class<T> dataClass, final List<? extends T> data)
	{
		return toJsonObject(dataClass, data).toString();
	}

	/**
	 * Convert a list to a JSON object.
	 * 
	 * @param dataClass
	 *            The collection's entry class
	 * @param data
	 *            Data collection to wrap
	 * @return wrapper list in JSON format
	 */
	public static <T> JSONObject toJsonObject(final Class<T> dataClass, final List<? extends T> data)
	{
		return JSONObject.fromObject(new List2JsonWrapper<T>(dataClass, data));
	}

	/**
	 * Convert a JSON string to a list.
	 * 
	 * @param dataClass
	 *            The collection's entry class
	 * @param jsonString
	 *            input string in JSON format
	 * @return list of objects
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<? extends T> toList(final Class<T> dataClass, final String jsonString)
	{
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		List2JsonWrapper<T> bean = (List2JsonWrapper<T>) JSONObject.toBean(jsonObject,
				List2JsonWrapper.class, classMap);
		MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
		Morpher dynaMorpher = new BeanMorpher(dataClass, morpherRegistry);
		morpherRegistry.registerMorpher(dynaMorpher);
		List<T> output = new ArrayList<T>();
		for (Object raw : bean.getData())
		{
			MorphDynaBean dynaBean = (MorphDynaBean) raw;
			T myBean = (T) morpherRegistry.morph(dataClass, dynaBean);
			output.add(myBean);
		}
		return output;
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * Return the dataClass property.
	 * 
	 * @return the dataClass
	 */
	public Class<T> getDataClass()
	{
		return dataClass;
	}

	/**
	 * Return the data property.
	 * 
	 * @return the data
	 */
	public List<? extends T> getData()
	{
		return data;
	}

	/**
	 * Return the size property.
	 * 
	 * @return the size
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * Set a new value for the dataClass property.
	 * 
	 * @param dataClass
	 *            the dataClass to set
	 */
	public void setDataClass(Class<T> dataClass)
	{
		this.dataClass = dataClass;
	}

	/**
	 * Set a new value for the data property.
	 * 
	 * @param data
	 *            the data to set
	 */
	public void setData(List<? extends T> data)
	{
		this.data = data;
		this.size = (data == null) ? 0 : data.size();
	}
}
