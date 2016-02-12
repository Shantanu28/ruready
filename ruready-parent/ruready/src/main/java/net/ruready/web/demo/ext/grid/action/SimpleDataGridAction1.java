/*****************************************************************************************
 * Source File: SimpleDataGridAction1.java
 ****************************************************************************************/
package net.ruready.web.demo.ext.grid.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.common.text.TextUtil;
import net.ruready.port.json.List2JsonWrapper;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.util.HttpRequestUtil;
import net.ruready.web.common.util.ParameterMap;
import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import test.ruready.port.json.SimpleRecord;

/**
 * Handle AJAX events of an ExtJS grid. The data source is a hard-coded list of
 * strings ,loaded at web application start-up and modified by grid events.
 * Version 1 (corresponds to <code>edit_grid.{html,js}</code>).
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
 * @version Nov 7, 2007
 */
public class SimpleDataGridAction1 extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SimpleDataGridAction1.class);

	/**
	 * The space of all possible data.
	 */
	private static final List<SimpleRecord> dataSpace = new ArrayList<SimpleRecord>();

	/**
	 * Current list of records in the grid. A subset of dataSpace.
	 */
	private static final List<SimpleRecord> data = new ArrayList<SimpleRecord>();

	public SimpleDataGridAction1()
	{
		// =============================================
		// Initialize data space
		// =============================================
		logger.debug("Initializing data space");
		dataSpace.clear();
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Alabama"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Alaska"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Arizona"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Arkansas"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "California"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Colorado"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Connecticut"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Delaware"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Florida"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Georgia"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Hawaii"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Idaho"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Illinois"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Indiana"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Iowa"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Kansas"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Kentucky"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Louisiana"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Maine"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Maryland"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Massachusetts"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Michigan"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Minnesota"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Mississippi"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Missouri"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Montana"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Nebraska"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Nevada"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "New Hampshire"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "New Jersey"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "New Mexico"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "New York"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "North Carolina"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "North Dakota"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Ohio"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Oklahoma"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Oregon"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Pennsylvania"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Rhode Island"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "South Carolina"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "South Dakota"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Tennessee"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Texas"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Utah"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Vermont"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Virginia"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Washington"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "West Virginia"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Wisconsin"));
		dataSpace.add(new SimpleRecord(getNextId(dataSpace), "Wyoming"));
		logger.debug("dataSpace " + dataSpace);

		// =============================================
		// Initialize data set: the entire data space
		// =============================================
		data.clear();
		logger.debug("Initializing data");
		for (SimpleRecord record : dataSpace)
		{
			data.add(record.clone());
		}
	}

	// ========================= IMPLEMENTATION: Action ====================

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================

		// ======================================================
		// Business logic
		// ======================================================
		logger.debug("data " + data);

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in JSON format
		String encoded = List2JsonWrapper.toJSONString(SimpleRecord.class, data);
		logger.debug("encoded " + encoded);
		response.getWriter().write(encoded.toString());
		return null;
	}

	/**
	 * Prepare data for an auto-complete feature.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward action_complete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		String query = request.getParameter("query");

		// ======================================================
		// Business logic
		// ======================================================
		// Restrict dataSpace based on auto-complete parameter
		List<SimpleRecord> results = findByContext(dataSpace, query);

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in JSON format
		String encoded = List2JsonWrapper.toJSONString(SimpleRecord.class, results);
		logger.debug("encoded " + encoded);
		response.getWriter().write(encoded.toString());
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward action_update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		// A list of modified and new records in JSON format
		String jsonData = request.getParameter("data");

		// ======================================================
		// Business logic
		// ======================================================
		boolean success = true;
		// Convert JSON array data to POJOs
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
		List<DynaBean> updatedRecords = JSONArray.toList(jsonArray);

		// Process records
		for (DynaBean record : updatedRecords)
		{
			// Read parameters from dynamic bean
			Long id = null;
			try
			{
				id = TextUtil.getStringAsLong("" + record.get("id"));
			}
			catch (Exception e)
			{

			}

			Long newContextId = null;
			try
			{
				newContextId = TextUtil.getStringAsLong("" + record.get("context"));
			}
			catch (Exception e)
			{

			}
			int newContextIndex = indexOfId(data, newContextId);
			if (newContextIndex < 0)
			{
				logger.debug("New context index " + newContextIndex
						+ " not found, skipping");
				continue;
			}
			String newContext = data.get(newContextIndex).getContext();

			if (id != null)
			{
				// Existing record
				// Replace old record with incoming record
				int index = indexOfId(data, id);
				data.get(index).setContext(newContext);
				logger.debug("Updated record " + data.get(index));
			}
			else
			{
				// New record, add
				SimpleRecord newRecord = new SimpleRecord(getNextId(data), newContext);
				data.add(newRecord);
				logger.debug("Added record " + newRecord);
			}
		}

		ParameterMap map = new ParameterMap();
		map.put("success", "" + success);

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in JSON format. Use our own toJSONString()
		// method that does not quote the property names (map keys in this
		// case). This is due to problems of parsing quoted property names on
		// the client's side.
		String encoded = map.toJSONString();
		logger.debug("encoded " + encoded);
		response.setStatus(success ? HttpServletResponse.SC_ACCEPTED
				: HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().write(encoded);
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @see net.ruready.web.common.action.LoggedActionWithDispatcher#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward action_delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================
		Long id = HttpRequestUtil.getParameterAsLong(request, "id");

		// ======================================================
		// Business logic
		// ======================================================
		boolean success = true;
		int index = indexOfId(data, id);
		if (id < 0)
		{
			logger.debug("Record not found, skipping");
		}
		else
		{
			logger.debug("Removing record " + data.get(index));
			data.remove(index);
		}

		ParameterMap map = new ParameterMap();
		map.put("success", "" + success);

		// ======================================================
		// Prepare response
		// ======================================================
		// Write the record list in JSON format. Use our own toJSONString()
		// method that does not quote the property names (map keys in this
		// case). This is due to problems of parsing quoted property names on
		// the client's side.
		String encoded = map.toJSONString();
		logger.debug("encoded " + encoded);
		response.setStatus(success ? HttpServletResponse.SC_ACCEPTED
				: HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().write(encoded);
		return null;
	}

	// ========================= HOOKS =====================================

	// ========================= PRIVATE METHODS ===========================

	private static int indexOfId(final List<SimpleRecord> list, final Long id)
	{
		if (id == null)
		{
			return -1;
		}
		int i = 0;
		for (SimpleRecord record : list)
		{
			if (id.equals(record.getId()))
			{
				return i;
			}
			i++;
		}
		return -1;
	}

	private static Long getNextId(final List<SimpleRecord> list)
	{
		Long maxId = 0L;
		for (SimpleRecord record : list)
		{
			Long id = record.getId();
			if (maxId < id)
			{
				maxId = id;
			}
		}
		return maxId + 1;
	}

	private static List<SimpleRecord> findByContext(final List<SimpleRecord> list,
			final String query)
	{
		List<SimpleRecord> results = new ArrayList<SimpleRecord>();
		if (query == null)
		{
			return results;
		}
		for (SimpleRecord record : list)
		{
			if (record.getContext().startsWith(query))
			{
				results.add(record.clone());
			}
		}
		return results;
	}
}
