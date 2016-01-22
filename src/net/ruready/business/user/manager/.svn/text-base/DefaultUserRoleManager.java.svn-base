package net.ruready.business.user.manager;

import static net.ruready.business.user.manager.Messages.findByIdMessage;
import static net.ruready.business.user.manager.Messages.nullMessage;
import static org.apache.commons.lang.Validate.notNull;
import net.ruready.business.user.entity.TeacherRole;
import net.ruready.business.user.entity.UserRole;
import net.ruready.common.eis.manager.AbstractEISManager;
import net.ruready.common.rl.ApplicationContext;
import net.ruready.common.rl.ResourceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultUserRoleManager implements AbstractUserRoleManager
{
	private static final Log logger = LogFactory.getLog(DefaultUserRoleManager.class);
	
	private static final String ENTITY_NAME = "User Role";
	private static final String ID_NOT_NULL = nullMessage("id");

	/**
	 * DAO factory, obtained from the resource locator.
	 */
	protected final AbstractEISManager eisManager;

	/**
	 * Produces entity association manager objects.
	 */
	protected final ApplicationContext context;
	
	/**
	 * Create a user group service that will use a resource locator and a DAO factory to
	 * read/write users to/from the database.
	 * 
	 * @param resourceLocator
	 *            a resource locator pointing to a DAO factory
	 * @param context
	 *            application context
	 */
	public DefaultUserRoleManager(final ResourceLocator resourceLocator, final ApplicationContext context)
	{
		this.eisManager = resourceLocator.getDAOFactory();
		this.context = context;
	}
	
	public UserRole findUserRoleById(final Long id)
	{
		notNull(id, ID_NOT_NULL);

		logger.debug(findByIdMessage(ENTITY_NAME, id));
		return eisManager.getDAO(UserRole.class, context).read(id);
	}
	
	public TeacherRole findTeacherRoleById(final Long id)
	{
		notNull(id, ID_NOT_NULL);

		logger.debug(findByIdMessage(ENTITY_NAME, id));
		return eisManager.getDAO(TeacherRole.class, context).read(id);
	}
}
