/*****************************************************************************************
 * Source File: WebAppNames.java
 ****************************************************************************************/
package net.ruready.web.common.rl;

import net.ruready.common.rl.CommonNames;
import net.ruready.parser.rl.ParserNames;

/**
 * This interface centralizes constants, labels and names used throughout the
 * web tier of the application. All strings referring to attributes should of
 * course be different from each other.
 * <p>
 * Note: If no trailing slash is attached, file names are relative to the
 * <code>classes</code> directory of the application.
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
 * @version Jul 29, 2007
 */
public interface WebAppNames
{
	// ========================= CONSTANTS =================================

	/**
	 * Name of this web application project unless declared otherwise in the
	 * resource locator configuration file.
	 */
	static final String DEFAULT_PROJECT_NAME = "ru2";

	// -----------------------------------------------------------------------
	// Resource locators & property names
	// -----------------------------------------------------------------------
	public interface RESOURCE_LOCATOR
	{
		/**
		 * Stand-alone java applications RL identifier (no JNDI, production
		 * database).
		 */
		static final String STAND_ALONE_CONFIG_FILE = "ResourceLocator_StandAlone.properties";

		/**
		 * JUnit test case RL identifier (no JNDI, test database name).
		 */
		static final String TEST_CONFIG_FILE = "ResourceLocator_Test.properties";

		/**
		 * Web applications RL identifier (assumes access to a JDNI server,
		 * among other things).
		 */
		static final String WEB_APP_CONFIG_FILE = "ResourceLocator_WebApp.properties";

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Module names
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface MODULE
		{
			/**
			 * Separator for module URLs.
			 */
			static final String SEPARATOR = "/";

			/**
			 * Content management system (CMS).
			 */
			static final String CONTENT = "content";

			/**
			 * Sub-modules of the CMS.
			 */
			public interface CONTENT_SUBMODULES
			{
				/**
				 * Common prefix for all content sub-module URLs.
				 */
				static final String PREFIX = CONTENT + SEPARATOR;

				/**
				 * Trash can management sub-module.
				 */
				static final String TRASH = PREFIX + "trash";
			}

			/**
			 * User component.
			 */
			static final String USER = "user";
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Business Managers Lookup names
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface MANAGER
		{
			/**
			 * Generic item manipulation business manager.
			 */
			static final String CATALOG_EDIT_ITEM = MODULE.CONTENT + MODULE.SEPARATOR
					+ "EditItemManager";

			/**
			 * Item hierarchy's root node business manager.
			 */
			static final String CATALOG_ROOT = MODULE.CONTENT + MODULE.SEPARATOR
					+ "RootManager";

			/**
			 * Item hierarchy's trash can node business manager.
			 */
			static final String TRASH_TRASH = MODULE.CONTENT_SUBMODULES.TRASH
					+ MODULE.SEPARATOR + "TrashManager";
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Configuration property conventions
		// that refer to specific actions in the web application
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface PROPERTY
		{
			/**
			 * Project name configuration property.
			 */
			static final String PROJECT_NAME = "project.name";

			/**
			 * Deleted item life time before being expunged [ms].
			 */
			static final String TRASH_EXPUNGE_TIME = "trash.expunge_time";

			/**
			 * Time between trash cleanings [ms].
			 */
			static final String TRASH_CLEANING_INTERVAL = "trash.cleaning_interval";

			/**
			 * Web applications RL identifier (assumes access to a JDNI server,
			 * among other things).
			 */
			static final String DATA_DIR = "data.dir";
		}
	}

	// -----------------------------------------------------------------------
	// Application resource keys & messages used in actions
	// -----------------------------------------------------------------------
	public interface KEY
	{
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Exception handler keys
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface EXCEPTION
		{
			/**
			 * A common prefix for exception keys. Convention: global message
			 * uses the key "error.ExceptionClassSimpleName" the resource
			 * bundle.
			 */
			static final String PREFIX = "error.";

			/**
			 * Key to use for the full message of a system exception.
			 */
			static final String SYSTEM_MESSAGE = "error.SystemException.message";

			/**
			 * Key to use for stale question error messages in the catalog
			 * system.
			 */
			static final String STALE_QUESTION = "error.StaleRecordException.question";

			/**
			 * Key to use when a unique item is not found.
			 */
			static final String RECORD_NOT_FOUND_UNIQUE = "error.RecordExistsException.uniqueItem";

			/**
			 * Key to use for record exists error messages, during item editing.
			 */
			static final String RECORD_EXISTS_EDIT_ITEM = "error.RecordExistsException.editItem";

			/**
			 * Key to use for record exists error messages, during user
			 * editing/registration.
			 */
			static final String RECORD_EXISTS_EDIT_USER = "error.RecordExistsException.createUser";

			/**
			 * Failed login error message key.
			 */
			static final String AUTHENTICATION_LOGIN_FAILED = "error.AuthenticationException.LOGIN_FAILED";

			/**
			 * Failed login error message key (account is locked).
			 */
			static final String AUTHENTICATION_ACCOUNT_LOCKED = "error.AuthenticationException.ACCOUNT_LOCKED";

			// #################################################################
			// Math parser exceptions
			// for simplicity, they reference the parser naming conventions.
			// #################################################################
			public interface MATH_PARSER
			{
				static final String EXCEPTION = ParserNames.KEY.MATH_EXCEPTION.EXCEPTION;

				static final String UNARY_OP = ParserNames.KEY.MATH_EXCEPTION.UNARY_OP;

				static final String BINARY_FUNC = ParserNames.KEY.MATH_EXCEPTION.BINARY_FUNC;

				static final String COMPLEX_CONSTANT = ParserNames.KEY.MATH_EXCEPTION.COMPLEX_CONSTANT;

				static final String REAL_CONSTANT = ParserNames.KEY.MATH_EXCEPTION.REAL_CONSTANT;

				static final String CONSTANT = ParserNames.KEY.MATH_EXCEPTION.CONSTANT;

				static final String IMPLICIT_MULTIPLICATION = ParserNames.KEY.MATH_EXCEPTION.IMPLICIT_MULTIPLICATION;

				static final String SEQUENCE_TRACK = ParserNames.KEY.MATH_EXCEPTION.SEQUENCE_TRACK;

				static final String INVALID_VARIABLE = ParserNames.KEY.MATH_EXCEPTION.INVALID_VARIABLE;

				static final String VARIABLE_NOT_FOUND = ParserNames.KEY.MATH_EXCEPTION.VARIABLE_NOT_FOUND;

				/**
				 * Unsupported operation on this domain of numbers (e.g.
				 * division on Integers).
				 */
				static final String UNSUPPORTED_OPERATION = ParserNames.KEY.MATH_EXCEPTION.UNSUPPORTED_OPERATION;

				/**
				 * Cannot numerically evaluate an expression.
				 */
				static final String INEVALUABLE = ParserNames.KEY.MATH_EXCEPTION.INEVALUABLE;
			}
		}

		/**
		 * Keys for constructing a welcome e-mail message for a user: subject
		 * line.
		 */
		static final String MAIL_WELCOME_SUBJECT = "user.mailWelcome.subject";

		/**
		 * Keys for constructing a welcome e-mail message for a user: message
		 * content.
		 */
		static final String MAIL_WELCOME_CONTENT = "user.mailWelcome.content";

		/**
		 * Keys for constructing a user password reminder message: subject line.
		 */
		static final String MAIL_REMINDER_SUBJECT = "user.forgotPassword.subject";

		/**
		 * Keys for constructing a user password reminder message:message
		 * content.
		 */
		static final String MAIL_REMINDER_CONTENT = "user.forgotPassword.content";

		// key for constructing a user password reset message: subject line
		static final String MAIL_RESET_SUBJECT = "user.resetPassword.subject";

		// key for constructing a user password reset message: content line
		static final String MAIL_RESET_CONTENT = "user.resetPassword.content";

		/**
		 * Keys for constructing a details' update message for a user: subject
		 * line.
		 */
		static final String MAIL_UPDATE_SUBJECT = "user.mailUpdate.subject";

		/**
		 * Keys for constructing a details' update message for a user: message
		 * content.
		 */
		static final String MAIL_UPDATE_CONTENT = "user.mailUpdate.content";

		/**
		 * Message to be displayed/e-mailed when some items were not found.
		 */
		static final String CONTENT_NOT_FOUND = "error.content.somenotfound";

		/**
		 * Message to be displayed/e-mailed when a question is not found.
		 */
		static final String QUESTION_NOT_FOUND = "error.question.notfound";

		/**
		 * Message to be displayed/e-mailed when the user's email is not found
		 * in the database.
		 */
		static final String EMAIL_NOT_FOUND = "error.user.emailnotfound";

		/**
		 * Invalid number of choices.
		 */
		static final String EDIT_QUESTION_NUMBER_OF_CHOICES = "error.question.numberOfChoices";

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Struts dispatch action method name conventions,
		// labels and mappings
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		@Deprecated
		public interface METHOD
		{
			// #####################################
			// Method names common to all actions
			// #####################################
			public interface COMMON
			{
				/**
				 * I18n resource label for a reset action.
				 */
				static final String RESET = "app.action.reset";

				/**
				 * I18n resource label for a cancel action.
				 */
				static final String CANCEL = "app.action.cancel";
			}

			// #####################################
			// Parser demo action
			// #####################################
			public interface PARSER_DEMO
			{
				/**
				 * I18n resource label for an analysis method.
				 */
				static final String ANALYZE = "parser.demo.action.analyze";

				/**
				 * label for update format (radio button selection onchange()
				 * event) action.
				 */
				static final String UPDATE_FORMAT = "parser.demo.action.setupUpdateFormat";
			}
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Messages (in the application resource file / resource bundle)
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface MESSAGE
		{
			/**
			 * Separator within key strings.
			 */
			static final String SEPARATOR = ".";

			/**
			 * Error message in exception handlers when the action mapping is
			 * not found
			 */
			static final String EXCEPTION_HANDLER_MAPPING_NOT_FOUND = "<Null mapping so can't tell>";

			/**
			 * Default selection in drop-down menus.
			 */
			static final String OLS_NO_SELECTION_LABEL = "app.ols.noselection.label";

			/**
			 * Default selection in drop-down menus.
			 */
			static final String OLS_ANY_LABEL = "app.search.any";

			/**
			 * Message resource key suffix for enumerated-type option labels.
			 */
			static final String OLS_ENUM_OPTION_SUFFIX = "label";
		}
	}

	// -----------------------------------------------------------------------
	// HTML option list sources; for enumerated types, reference the
	// enumerated type name.
	// -----------------------------------------------------------------------
	public interface OLS
	{
		static final String FACTORY_CONFIG_FILE = "ols.properties";

		static final String PROPERTY_PREFIX = ".option.list.source";

		static final String LABEL_NO_SELECTION = "-- No Selection --";

		static final String GENDER = CommonNames.GENDER.NAME;

		static final String STATES = "states";

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Types of OL sources
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		static final String DISCRETE_TYPE = "olsType";

		/**
		 * toString() of each OL source type
		 */
		static final String TYPE_SIMPLE = "simple";

		static final String TYPE_PROPERTIES = "properties";

		static final String TYPE_ENUM = "enum";

		static final String TYPE_ENUM_MAP = "enummap";

		static final String TYPE_ENUM_LIST = "enumlist";

		static final String TYPE_ARRAYS = "arrays";

		static final String TYPE_LISTS = "lists";
	}

	// -----------------------------------------------------------------------
	// WebApplicationContext-scope variable name conventions
	// -----------------------------------------------------------------------
	public interface CONTEXT
	{
		/**
		 * The currently authenticated user [User].
		 */
		static final String USER = "user";

		/**
		 * Struts action error list [ActionErrors].
		 */
		static final String ACTION_ERRORS = "actionErrors";

		/**
		 * The currently used item business delegate [AbstractEditItemManager<Item>].
		 */
		static final String BD_ITEM = "bdItem";

		/**
		 * Is the current action handling the request an AJAX action [Boolean].
		 */
		static final String AJAX = "ajax";
	}

	// -----------------------------------------------------------------------
	// Request-scope variable name conventions
	// -----------------------------------------------------------------------
	public interface REQUEST
	{
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Request header name conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface HEADER
		{
			/**
			 * Flag that tells us whether to ignore this request in hit counter
			 * processing.
			 */
			public static final String PROCESS_HIT = "processHit";
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Request parameter name conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface PARAM
		{
			/**
			 * For all item view: references of the item of its type are
			 * required. Item's unique identifier.
			 */
			static final String ITEM_ID = "itemId";

			/**
			 * For tree item view actions: an item's unique identifier.
			 */
			static final String NODE = "node";

			/**
			 * Seed for item ID negations that refer to instantiating a unique
			 * item of a certain type.
			 */
			static final long BASE_TYPE = 1000;

			/**
			 * Seed for item ID negations that refer to instantiating a new
			 * (blank) item of a certain type.
			 */
			static final long BASE_NEW = 2000;

			/**
			 * For all item view: references of the item of its type are
			 * required. Item's type.
			 */
			static final String ITEM_TYPE = "itemType";

			/**
			 * For all item view: references of the item of its type are
			 * required. Item's version number (version control sub-system).
			 */
			static final String ITEM_VERSION = "itemVersion";

			/**
			 * For item transfer actions: need both source and destination item
			 * references.
			 */
			static final String ITEM_SOURCE_ID = "itemSourceId";

			/**
			 * For item transfer actions: need both source and destination item
			 * references.
			 */
			static final String ITEM_DESTINATION_ID = "itemDestinationId";

			/**
			 * For pages that populate parent drop-down menus for parent catalog
			 * items.
			 */
			static final String ITEM_PARENT_ID = "parentId";

			/**
			 * Refers to a child type of a certain item in the request.
			 */
			static final String CHILD_TYPE = "childType";

			/**
			 * Refers to a tag type of a certain item in the request.
			 */
			static final String TAG_TYPE = "tagType";

			/**
			 * User perspective (depends on access roles)
			 */
			static final String USER_PERSPECTIVE = "role";

			/**
			 * Name of object, for actions that use such an object to prepare
			 * some stand-alone output (e.g. PNG image of a tree object)
			 */
			static final String NAME = "name";

			/**
			 * An auto-complete query string.
			 */
			static final String QUERY = "query";

			/**
			 * Prefix for parameters used to identify Struts dispatch action
			 * methods. Do not declare parameters for any other purpose that
			 * start with this prefix, to void name collisions. Note: this is no
			 * longer a restriction of dispatch action parameter names, but we
			 * still use it for debugging printouts.
			 */
			static final String ACTION_PREFIX = "action";

			/**
			 * Display tag library parameter for search question result table
			 * state: page number.
			 */
			static final String DISPLAY_TAG_PAGE = "d-16544-p";

			/**
			 * Display tag library parameter for search question result table
			 * state: column whose sort status is active.
			 */
			static final String DISPLAY_TAG_SORT_COLUMN = "d-16544-s";

			/**
			 * Display tag library parameter for search question result table
			 * state: sort order (ascending/descending/...).
			 */
			static final String DISPLAY_TAG_SORT_ORDER = "d-16544-o";
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Request-scope attribute name conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface ATTRIBUTE
		{
			/**
			 * Web application context object, placed in the request scope at
			 * the beginning of the Struts processing chain.
			 */
			static final String WEB_APPLICATION_CONTEXT = "webApplicationContext";

			/**
			 * The authenticated user.
			 */
			static final String USER = "user";

			// Root item view: a list of the main items under the root node
			// static final String LIST_MAINITEMS = "mainItems";

			/**
			 * List of all catalogs in the database.
			 */
			static final String LIST_CATALOGS = "catalogs";

			/**
			 * List of all institutional hierarchies - worlds.
			 */
			static final String LIST_WORLDS = "worlds";

			/**
			 * List of all institutional hierarchies - unique trash can.
			 */
			static final String TRASH = "trash";

			/**
			 * For all item view pages and action: item object; contains its
			 * parent. hierarchy.
			 */
			static final String ITEM = "item";

			/**
			 * For item editing when a conflict arises: database version of the
			 * edited entity.
			 */
			static final String STORED_ITEM = "storedItem";

			/**
			 * For transfer actions: source item.
			 */
			static final String ITEM_SOURCE = "itemSource";

			/**
			 * For transfer actions: destination item.
			 */
			static final String ITEM_DESTINATION = "itemDestination";

			/**
			 * Data for drop-down menu to select child type.
			 */
			static final String ITEM_CHILD_TYPES = "childTypes";

			/**
			 * Subject drop-down menu data.
			 */
			static final String SUBJECT_OPTIONS = "subjectOptions";

			/**
			 * For search item pages: search results.
			 */
			static final String SEARCH_ITEM_RESULT = "searchItemResult";

			/**
			 * For search question pages: search results.
			 */
			static final String SEARCH_QUESTION_RESULT = "searchQuestionResult";

			/**
			 * For search user pages: search result set.
			 */
			static final String SEARCH_USER_RESULT = "searchUserResult";

			/**
			 * Revision history list of an item.
			 */
			static final String VIEW_REVISIONS_RESULT = "viewRevisionsResult";

			/**
			 * Invalid path (saved during chain processing).
			 */
			static final String INVALID_PATH = "invalidPath";

			/**
			 * Hit counter value.
			 */
			static final String HIT_COUNTER = "hitCounter";

			// %%%%%%%%%%%%%%%%%%%%%%%%%%
			// Validaion-related objects
			// %%%%%%%%%%%%%%%%%%%%%%%%%%

			/**
			 * Used for validating parametric strings in forms.
			 */
			static final String VALIDATOR_PARSER_OPTIONS = "validatorParserOptions";

			// %%%%%%%%%%%%%%%%%%%%%%%%%%
			// Parser demo results
			// %%%%%%%%%%%%%%%%%%%%%%%%%%

			/**
			 * Are reference and response equivalent.
			 */
			static final String PARSER_DEMO_EQUIVALENT = "parserDemoEquivalent";

			/**
			 * Highlighted reference string.
			 */
			static final String PARSER_DEMO_HTML_REFERENCE_STRING = "parserDemoHTMLReferenceString";

			/**
			 * Highlighted response string.
			 */
			static final String PARSER_DEMO_HTML_RESPONSE_STRING = "parserDemoHTMLResponseString";

			/**
			 * Parser analysis and element counts.
			 */
			static final String PARSER_DEMO_ANALYSIS = "parserDemoAnalysis";

			/**
			 * Element count map, string keys.
			 */
			static final String PARSER_DEMO_ELEMENT_MAP = "parserDemoElementMap";

			/**
			 * Reference syntax tree image.
			 */
			static final String PARSER_DEMO_TREE_IMAGE_REFERENCE = "parserDemoTreeImageReference";

			/**
			 * Response syntax tree image.
			 */
			static final String PARSER_DEMO_TREE_IMAGE_RESPONSE = "parserDemoTreeImageResponse";

			/**
			 * Reference syntax tree image size.
			 */
			static final String PARSER_DEMO_TREE_SIZE_REFERENCE = "parserDemoTreeImageSizeReference";

			/**
			 * Response syntax tree image size.
			 */
			static final String PARSER_DEMO_TREE_SIZE_RESPONSE = "parserDemoTreeImageSizeResponse";

			/**
			 * Parser format submitted with the request (if conversions fail, we
			 * go back to this format).
			 */
			static final String PARSER_OLD_FORMAT = "parserOldFormat";

			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			// Request-scope tokens
			// Note: they correspond to request parameters with identical names.
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			public interface TOKEN
			{
				/**
				 * Prefix for tokens that indicate that a certain item type's
				 * drop down menu populated.
				 */
				static final String FOUND_ITEM_PREFIX = "found";

				/**
				 * Indicates that parser demo result is available.
				 */
				static final String PARSER_DEMO_RESULT = "parserDemoResult";

				/**
				 * Indicates that the main object attached to the request (e.g.
				 * item) is an invalid object.
				 */
				static final String INVALID = "invalid";
			}
		}
	}

	// -----------------------------------------------------------------------
	// Session-scope variable name conventions
	// -----------------------------------------------------------------------
	public interface SESSION
	{
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Session-scope attribute name conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface ATTRIBUTE
		{
			/**
			 * The authenticated user's ID.
			 */
			static final String USER_ID = "userId";

			/**
			 * A flag that tells us whether to increment the hit counter or not.
			 */
			static final String PROCESS_HIT = "processHit";

			/**
			 * Currently active perspective.
			 */
			static final String USER_CURRENT_PERSPECTIVE = "currentPerspective";

			/**
			 * Error messages to be carried over redirection.
			 */
			static final String ERRORS = "errors";

			/**
			 * Currently active Subject reference to draw data from during
			 * question editing.
			 */
			static final String SUBJECT_ID = "subjectId";

			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			// Session-scope tokens
			// Note: they correspond to request parameters with identical names.
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			public interface TOKEN
			{
				/**
				 * Internal page URL / book-mark requested by a user. Held
				 * during gateway authentication of the user.
				 */
				static final String BOOKMARK = "bookmark";

				/**
				 * Identifier of a Struts forward action to forward the request
				 * to at the end of the current action.
				 */
				static final String CUSTOM_FORWARD = "customForward";
			}

		}
	}

	// -----------------------------------------------------------------------
	// Application-scope variable name conventions.
	// MUST BE READ-ONLY TO BE THREAD-SAFE.
	// -----------------------------------------------------------------------
	public interface APPLICATION
	{
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Session-scope attribute name conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface ATTRIBUTE
		{
			/**
			 * Struts web filter mapping, placed in the application scope during
			 * the Struts request processor initialization.
			 */
			static final String FILTER_PACKAGE = "filterPackage";
		}
	}

	// -----------------------------------------------------------------------
	// Default and fall-back view pages
	// -----------------------------------------------------------------------
	public interface JSP
	{
		/**
		 * Main (default module) JSP directory path under the application
		 * directory.
		 */
		static final String ROOT_DIR = "WEB-INF/common/jsp";

		/**
		 * Application front page.
		 */
		static final String HOME = "/";
	}

	// -----------------------------------------------------------------------
	// Cookie name conventions
	// -----------------------------------------------------------------------
	public interface COOKIE
	{
		/**
		 * Common prefix for all cookie names generated by our application.
		 */
		static final String PREFIX = "RUReady";

		/**
		 * Expiration date for all cookies [seconds]. Set to 365 days = 1 year.
		 */
		static final int MAX_AGE = 60 * 60 * 24 * 365;

		/**
		 * User's email address in the login form.
		 */
		static final String USER_EMAIL = PREFIX + "Email";

		/**
		 * User's password in the login form.
		 */
		static final String USER_PASSWORD = PREFIX + "Password";
	}

	// -----------------------------------------------------------------------
	// Struts Action parameter name conventions
	// -----------------------------------------------------------------------
	public interface ACTION
	{
		/**
		 * All dispatch actions should use this as their parameter name..
		 */
		@Deprecated
		static final String DISPATCH_PARAMETER_NAME = "action";

		/**
		 * All parameter names of dispatch methods MUST start with this prefix.
		 */
		static final String DISPATCH_PARAMETER_PREFIX = DISPATCH_PARAMETER_NAME
				+ CommonNames.MISC.SEPARATOR;

		/**
		 * For this specific subset of dispatch parameters, validation is
		 * skipped. This is normally setup actions.
		 */
		static final String DISPATCH_PARAMETER_PREFIX_NO_VALIDATION = DISPATCH_PARAMETER_PREFIX
				+ "setup" + CommonNames.MISC.SEPARATOR;

		/**
		 * If this parameter or attribute name is present in the request and
		 * equals to VALIDATE_PARAMETER_VALUE_FALSE below, validation is
		 * skipped.
		 */
		static final String VALIDATE_TOKEN = "validate";

		/**
		 * This token value signifies skipping validation.
		 */
		static final String VALUE_FALSE = "false";

		/**
		 * This token value forces validation.
		 */
		static final String VALUE_TRUE = "true";

		/**
		 * All actions that start with this prefix are regarded as setup
		 * actions. This means that the form bean validate() implementation is
		 * allowed not to validate the form. This couples actions and forms, but
		 * uses a defined rule that is tracked by this global naming class.
		 */
		@Deprecated
		static final String SETUP_PREFIX = "setup";

		/**
		 * Default action mapping path.
		 */
		static final String DEFAULT = "default";
	}

	// -----------------------------------------------------------------------
	// Global property entities
	// -----------------------------------------------------------------------
	public interface GLOBAL_PROPERTY
	{
		/**
		 * Web Hit counter property name.
		 */
		public static final String HIT_COUNTER = "Hit Counter";

		/**
		 * User counter property name.
		 */
		public static final String USER_COUNTER = "User Counter";
	}

	// -----------------------------------------------------------------------
	// XML tag names
	// -----------------------------------------------------------------------
	public interface XML
	{
		/**
		 * XML response root tag.
		 */
		static final String RESPONSE = "response";

		/**
		 * One record in a result set.
		 */
		static final String RECORD = "record";

		/**
		 * Record unique identifier.
		 */
		static final String ID = "id";

		/**
		 * Status code for a record/result set.
		 */
		static final String STATUS = "status";

		/**
		 * Select (drop-down menu) - main tag.
		 */
		static final String SELECT = "select";

		/**
		 * Select (drop-down menu) - single option tag.
		 */
		static final String OPTION = "option";

		/**
		 * Attribute tag.
		 */
		static final String ATTRIB = "attribute";

		/**
		 * Attribute name tag.
		 */
		static final String NAME = "name";

		/**
		 * Select (drop-down menu) - option label tag.
		 */
		static final String LABEL = "label";

		/**
		 * Attribute value tag or a select (drop-down menu) - option value tag.
		 */
		static final String VALUE = "value";

		/**
		 * An attribute that indicates whether an element is selected in a
		 * drop-down menu.
		 */
		static final String SELECTED_ATTRIBUTE = "selected";

		/**
		 * Indicates that an element is selected in a drop-down menu.
		 */
		static final String SELECTED_VALUE = "selected";

		/**
		 * Encompassing tag for AJAX XML response regarding drop-down menus.
		 */
		static final String MENU = "menu";

		/**
		 * Encompassing tag for AJAX XML response regarding question counts.
		 */
		static final String QUESTION_COUNT = "question-count";

		/**
		 * Encompassing tag for AJAX XML response regarding a single question
		 * count group.
		 */
		static final String COUNT = "count";

		/**
		 * Question count map - count type.
		 */
		static final String TYPE = "type";

		/**
		 * Question count map - difficulty level.
		 */
		static final String LEVEL = "level";

		/**
		 * Mean value tag.
		 */
		static final String MEAN = "mean";

		/**
		 * Standard deviation value tag.
		 */
		static final String STD = "std";

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Tag attributes
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface ATTRIBUTE
		{
			/**
			 * DOM division/span unique identifier.
			 */
			@SuppressWarnings("hiding")
			static final String ID = "id";
		}
	}

	// -----------------------------------------------------------------------
	// HTML tag names and symbols
	// -----------------------------------------------------------------------
	public interface HTML
	{
		/**
		 * A link tag name.
		 */
		static final String TAG_NAME_LINK = "a";

		/**
		 * A page division tag name.
		 */
		static final String TAG_NAME_DIVISION = "div";

		/**
		 * A commonly-used HTML/XML space character.
		 */
		static final String SPACE = "&nbsp;";

		/**
		 * Separator between path and query string in URLs (unencoded).
		 */
		static final String URL_SEPARATOR = "?";

		/**
		 * Separator between parameter snippets in URLs (unencoded).
		 */
		static final String PARAM_SEPARATOR = "&";

		/**
		 * Separator between parameter name and its value in URLs (unencoded).
		 */
		static final String PARAM_ASSIGNMENT = "=";
	}

	// -----------------------------------------------------------------------
	// Javascript code generation
	// -----------------------------------------------------------------------
	public interface JAVASCRIPT
	{
		/**
		 * Put before and after string function arguments.
		 */
		static final String ARGUMENT_STRING_DECORATOR = "'";

		/**
		 * Function argument separator.
		 */
		static final String ARGUMENT_SEPARATOR = ",";

	}
}
