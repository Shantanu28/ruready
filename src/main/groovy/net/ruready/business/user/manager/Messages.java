package net.ruready.business.user.manager;

/**
 * ...
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
 * @author Jeremy Lund <code>&lt;jeremy.lund@stgutah.com&gt;</code>
 * @version Oct 15, 2007
 */
public class Messages
{
	private static final String MSG_NOT_NULL = "%s cannot be null.";

	private static final String MSG_NOT_EMPTY = "%s cannot be empty.";

	private static final String CREATE_MESSAGE = "Creating %s: email=\"%s\"";

	private static final String READ_MESSAGE = "Reading %s: id=%d, email=\"%s\"";

	private static final String UPDATE_MESSAGE = "Updating %s: email=\"%s\"";

	private static final String DELETE_MESSAGE = "Deleting %s: email=\"%s\"";

	private static final String FIND_ALL_MESSAGE = "Find all %ss";

	private static final String FIND_BY_ID_MESSAGE = "Find %s by id: %d";

	protected static final String nullMessage(final String property)
	{
		return String.format(MSG_NOT_NULL, property);
	}

	protected static final String emptyMessage(final String property)
	{
		return String.format(MSG_NOT_EMPTY, property);
	}

	protected static final String createMessage(final String entityName,
			final String objectName)
	{
		return String.format(CREATE_MESSAGE, entityName, objectName);
	}

	protected static final String readMessage(final String entityName,
			final Long objectId, final String objectName)
	{
		return String.format(READ_MESSAGE, entityName, objectId, objectName);
	}

	protected static final String updateMessage(final String entityName,
			final String objectName)
	{
		return String.format(UPDATE_MESSAGE, entityName, objectName);
	}

	protected static final String deleteMessage(final String entityName,
			final String objectName)
	{
		return String.format(DELETE_MESSAGE, entityName, objectName);
	}

	protected static final String findAllMessage(final String entityName)
	{
		return String.format(FIND_ALL_MESSAGE, entityName);
	}

	protected static final String findByIdMessage(final String entityName, final Long id)
	{
		return String.format(FIND_BY_ID_MESSAGE, entityName, id);
	}
}
