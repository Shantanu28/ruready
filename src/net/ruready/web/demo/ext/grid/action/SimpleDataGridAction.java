/*****************************************************************************************
 * Source File: SimpleDataGridAction.java
 ****************************************************************************************/
package net.ruready.web.demo.ext.grid.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ruready.common.rl.CommonNames;
import net.ruready.common.text.TextUtil;
import net.ruready.port.json.DynaBeanUtil;
import net.ruready.port.json.List2JsonWrapper;
import net.ruready.web.common.action.LoggedActionWithDispatcher;
import net.ruready.web.common.util.ParameterMap;
import net.ruready.web.demo.ext.grid.entity.RecordStatus;
import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import test.ruready.port.json.SimpleRecord;

/**
 * A demo of an action that handles AJAX events of an ExtJS grid. The data
 * source is a hard-coded list of strings ,loaded at web application start-up
 * and modified by grid events. Version 2 (corresponds to
 * <code>edit_grid2.{html,js}</code>).
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
public class SimpleDataGridAction extends LoggedActionWithDispatcher
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(SimpleDataGridAction.class);

	/**
	 * The space of all possible data.
	 */
	private static final List<SimpleRecord> dataSpace = new ArrayList<SimpleRecord>();

	/**
	 * Current list of records in the grid. A subset of dataSpace.
	 */
	private static final List<SimpleRecord> data = new ArrayList<SimpleRecord>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Initialize the data space.
	 */
	public SimpleDataGridAction()
	{
		// =============================================
		// Initialize data space
		// =============================================
		logger.debug("Initializing data space");
		String[] states =
		{ "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
				"Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
				"Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
				"Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota",
				"Mississippi", "Missouri", "Montana", "Nebraska", "Nevada",
				"New Hampshire", "New Jersey", "New Mexico", "New York",
				"North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
				"Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
				"Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
				"West Virginia", "Wisconsin", "Wyoming" };
		dataSpace.clear();
		for (String name : states)
		{
			addState(dataSpace, name);
		}
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
	public ActionForward action_reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) // throws
	// IOException
	{
		// ======================================================
		// Read context & parameters
		// ======================================================

		// ======================================================
		// Business logic
		// ======================================================
		// Re-initialize data set: the entire data space
		data.clear();
		logger.debug("Initializing data");
		for (SimpleRecord record : dataSpace)
		{
			data.add(record.clone());
		}
		logger.debug("data " + data);

		// ======================================================
		// Prepare response
		// ======================================================
		return mapping.findForward("view");
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
		String jsonData = request.getParameter("updategrid");
		if (TextUtil.isEmptyString(jsonData))
		{
			logger.debug("No grid data found, doing nothing");
			return mapping.findForward("view");
		}

		// ======================================================
		// Business logic
		// ======================================================
		boolean success = true;
		// Convert JSON array data to POJOs
		JSONArray jsonArray = JSONArray.fromObject(jsonData);
		List<DynaBean> updatedRecords = JSONArray.toList(jsonArray);

		// Process records
		data.clear();
		for (DynaBean record : updatedRecords)
		{
			// Read parameters from dynamic bean
			long id = DynaBeanUtil.getPropertyAsLong(record, "id",
					CommonNames.MISC.INVALID_VALUE_LONG);
			RecordStatus status = DynaBeanUtil.getPropertyAsEnum(RecordStatus.class,
					record, "status", RecordStatus.NOT_APPLICABLE);

			// Process the record based on its status
			switch (status)
			{
				case DELETED:
				{
					// We won't normally reach this code because deleted records
					// are not submitted with the form, but just in case
					logger.debug(status + " record ID " + id);
					continue;
				}

				case NEW:
				case UPDATED:
				{
					if (status == RecordStatus.NEW)
					{
						// Generate a new ID
						id = getNextId(data);
					}

					// Here record.context is a record's ID
					long contextId = DynaBeanUtil.getPropertyAsLong(record, "context",
							CommonNames.MISC.INVALID_VALUE_LONG);
					int contextIndex = indexOfId(dataSpace, contextId);
					if (contextIndex < 0)
					{
						logger.debug("New context index " + contextIndex
								+ " not found, skipping");
						continue;
					}
					String context = dataSpace.get(contextIndex).getContext();

					SimpleRecord r = new SimpleRecord(id, context);
					data.add(r);
					logger.debug(status + " record " + r);
					break;
				}

				case NOT_APPLICABLE:
				{
					// Here record.context is a literal string
					String context = DynaBeanUtil.getPropertyAsString(record, "context",
							null);

					SimpleRecord r = new SimpleRecord(id, context);
					data.add(r);
					logger.debug(status + " record " + r);
					break;
				}
			} // switch (status)
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
		return mapping.findForward("view");
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

	private static void addState(final List<SimpleRecord> list, final String stateName)
	{
		list.add(new SimpleRecord(getNextId(list), stateName));
	}
}
