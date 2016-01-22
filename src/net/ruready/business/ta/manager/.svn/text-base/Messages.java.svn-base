package net.ruready.business.ta.manager;

public class Messages
{
	private static final String MSG_NOT_NULL = "%s cannot be null.";

	private static final String MSG_NOT_EMPTY = "%s cannot be empty.";

	private static final String CREATE_MESSAGE = "Creating %s";

	private static final String READ_MESSAGE = "Reading %s: id=%d";

	private static final String UPDATE_MESSAGE = "Updating %s";

	private static final String DELETE_MESSAGE = "Deleting %s";

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
			final String... objectName)
	{
		return String.format(CREATE_MESSAGE, entityName, objectName);
	}

	protected static final String readMessage(final String entityName,
			final Long objectId, final String... objectName)
	{
		return String.format(READ_MESSAGE, entityName, objectId, objectName);
	}

	protected static final String updateMessage(final String entityName,
			final String... objectName)
	{
		return String.format(UPDATE_MESSAGE, entityName, objectName);
	}

	protected static final String deleteMessage(final String entityName,
			final String... objectName)
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
