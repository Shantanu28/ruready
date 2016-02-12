/*****************************************************************************************
 * Source File: Names.java
 ****************************************************************************************/
package net.ruready.common.rl;

import java.math.BigInteger;

/**
 * This interface centralizes constants, labels and names used throughout the
 * application. All strings referring to attributes should of course be
 * different from each other.
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
 * @version Aug 6, 2007
 */
public interface CommonNames
{
	// ========================= CONSTANTS =================================

	// If no trailing slash is attached, file names are relative to the
	// "classes" directory of the application.

	// -----------------------------------------------------------------------
	// Resource locators & Properties
	// -----------------------------------------------------------------------
	public interface RESOURCE_LOCATOR
	{
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Specific RLs of this project
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

		/**
		 * For common component; only logger available.
		 */
		static final String MINIMAL_ALONE_CONFIG_FILE = "ResourceLocator_Minimal.properties";

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Configuration property conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface PROPERTY
		{
			static final String LOG4J_CONFIG_FILE = "log4j.config_file_name";

			/**
			 * DAO Factory & related properties.
			 */
			static final String DAO_FACTORY = "dao_factory";

			static final String DAO_FACTORY_CONFIG_FILE_NAME = "dao_factory.config_file_name";

			static final String DAO_FACTORY_HIBERNATE = "hibernate";

			static final String HIBERNATE_CONFIG = "dao_factory.hibernate.config";

			static final String HIBERNATE_NAMING_STRATEGY = "dao_factory.hibernate.naming_strategy";

			/**
			 * E-Mail session & related properties.
			 */
			static final String MAIL = "javamail";

			/**
			 * Encryption engine & related properties.
			 */
			static final String ENCRYPTOR = "encryptor";

			/**
			 * Read Mail session properties from a JNDI resource.
			 */
			static final String MAIL_JNDI = "jndi";

			/**
			 * If this value is specified for mail, all properties that start
			 * with MAIL will be set for the Mail session.
			 */
			static final String MAIL_STAND_ALONE = "stand_alone";
		}
	}

	// -----------------------------------------------------------------------
	// Application resource keys & messages used in managers and exception
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
		}
	}

	// -----------------------------------------------------------------------
	// Lookup names
	// -----------------------------------------------------------------------

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// JNDI Resources
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface JNDI
	{
		// Default JNDI context path. Paths below are relative to this.
		static final String INITIAL_CONTEXT = "java:comp/env";

		static final String EIS = "jdbc/MySQLDB";

		static final String MAIL_SESSION = "mail/Session";
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Date & Time constants
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface TIME
	{
		// Convert milliseconds (original Date units) to minutes (e.g. life time
		// context param units)
		static final long MINS_TO_MS = 1000 * 60;

	}

	// -----------------------------------------------------------------------
	// Discrete types
	// -----------------------------------------------------------------------
	// These may also be used as enumerated-type HTML option list sources

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Gender
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface GENDER
	{
		static final String NAME = "gender";

		// Identifier types (short description of each enumerated value)
		static final String MALE_TYPE = "M";

		static final String FEMALE_TYPE = "F";
	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	// Languages
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public interface LANGUAGE
	{
		static final String NAME = "language";
	}

	// -----------------------------------------------------------------------
	// Trees
	// -----------------------------------------------------------------------
	public interface TREE
	{
		// Default opening bracket (scope) in tree printouts
		static final String BRACKET_OPEN = "{";

		// Default closing bracket (scope) in tree printouts
		static final String BRACKET_CLOSE = "}";

		// Syntax symbol conventions for mapping and list printouts
		static final String PARENTHESIS_OPEN = "(";

		static final String PARENTHESIS_CLOSE = ")";

		// Default separator in variable and math token printouts
		// This symbol should typically not be part of any multi-character
		// symbol in the parser's syntax, to avoid conflicts and/or
		// ambigous syntax when parsing a syntax tree's string representation.
		static final String SEPARATOR = "$";

		// Default separator in variable and math token printouts
		static final String STATEMENT_SEPARATOR = ";";

		// Parenthesis token symbol
		static final String PARENTHESIS = "( )";
	}

	// -----------------------------------------------------------------------
	// Variables and variable maps
	// -----------------------------------------------------------------------
	public interface VARIABLE
	{
		// Default separator in a variable map string representation
		static final String SEPARATOR = ":";
	}

	// -----------------------------------------------------------------------
	// Munkres assignment algorithm
	// -----------------------------------------------------------------------
	public interface MUNKRES
	{
		// For Munkres assignment printouts

		static final String STARRED_ZERO_STRING = "*";

		static final String PRIMED_ZERO_STRING = "'";

		static final char SEPARATOR = CommonNames.MISC.TAB_CHAR;
	}

	// -----------------------------------------------------------------------
	// Miscellaneous
	// -----------------------------------------------------------------------
	public interface MISC
	{
		/**
		 * Non-null empty string.
		 */
		static final String EMPTY_STRING = "";

		/**
		 * A standard separator string.
		 */
		static final String SEPARATOR = "_";

		/**
		 * White space character.
		 */
		static final char SPACE_CHAR = ' ';

		/**
		 * New line char.
		 */
		static final char NEW_LINE_CHAR = '\n';

		/**
		 * Tab character.
		 */
		static final char TAB_CHAR = '\t';

		/**
		 * Printed in case of a null string.
		 */
		static final String NULL_TO_STRING = "<null>";

		/**
		 * Printed if property is not applicable.
		 */
		static final String NOT_APPLICABLE = "N/A";

		/**
		 * Printed if property is empty / not applicable.
		 */
		static final String NONE = "-";

		/**
		 * Printed if an object is yet to be added.
		 */
		static final String TO_BE_ADDED = "TBA";

		/**
		 * Invalid value of an integer field. May serve as a useful default
		 * value or when an index is not found in an array.
		 */
		static final int INVALID_VALUE_INTEGER = -1;

		/**
		 * Invalid value of a long field. May serve as a useful default value or
		 * when an index is not found in an array.
		 */
		static final long INVALID_VALUE_LONG = -1;

		/**
		 * Method calling recursion limit. Usually limited by JVM capabilities.
		 */
		static final int MAX_RECURSION = 256;
	}

	// -----------------------------------------------------------------------
	// JuUnit Test data file names and conventions
	// -----------------------------------------------------------------------
	public interface JUNIT
	{
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Test file reader parameter name conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface PARAMS
		{
			// Stop on first error found in the file or not; boolean
			static final String STOP_ON_FIRST_ERROR = "stopOnFirstError";
		}

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Environment variable name conventions
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		public interface ENVIRONMENT
		{
			// If non-null, taken to be the base directory to which test file
			// names
			// are relative
			static final String BASE_DIR = "basedir";
		}

	}

	// -----------------------------------------------------------------------
	// Parser core library naming conventions
	// -----------------------------------------------------------------------
	public interface PARSER
	{
		public interface CORE
		{
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			// Constants relating to the LinearCalculator Metsker code
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			public interface LINEAR_CALCULATOR
			{
				// Default separator in variable and math token printouts
				static final String SEPARATOR = ":";

				// Default separator of ranges, e.g. in the Metsker core
				// library's
				// LinearCalculator
				static final String RANGE_SEPARATOR = "::";
			}

			public interface TOKENS
			{
				// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
				// Tokenizer state names (appear in their toString())
				// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
				public interface TOKENIZER_STATE
				{
					// NumberState
					static final String NUMBER = "#";

					// QuoteState
					static final String QUOTE = "\"\"";

					// SlashSlashState
					static final String SLASH_SLASH = "//";

					// SlashStarState
					static final String SLASH_STAR = "/*";

					// SlashState
					static final String SLASH = "\"\"";

					// SymbolState
					static final String SYMBOL = "$";

					// WhiteSpaceState
					static final String WHITE_SPACE = "_";

					// WordState
					static final String WORD = "W";
				}
			}
		}

		public interface FENCE
		{
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			// Constants relating fencing in assemblies of parsers
			// (not necessarily in the core library)
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

			/**
			 * Prefix for fence token names.
			 */
			public static String PREFIX = "FENCE" + CommonNames.MISC.SEPARATOR;

		}
	}

	// -----------------------------------------------------------------------
	// Password and enryption constants
	// -----------------------------------------------------------------------
	public interface ENRCYPTOR
	{
		public interface BIG_RSA
		{

			// System-generated password length
			static final int LENGTH_GENERATED = 8;

			// Numeral password is generated in the range [0..MAX_PASSWORD-1]
			static final int MAX_NUMBER_PASSWORD = 10000;

			// Hard-wired primes for RSA private key generation . Match the
			// generated
			// password length constant above.
			final static BigInteger RSA_PRIME_P = new BigInteger("4143718961");

			final static BigInteger RSA_PRIME_Q = new BigInteger("3990883979");

			// Public key (fixed). Using a common value in practice: 2^16 + 1.
			// Normally used with RSA encryption. Must be large enough to match
			// LENGTH_GENERATED, so that it can correctly encrypt and decrypt
			// all password up to that length.
			final static BigInteger PUBLIC_KEY = new BigInteger("65537");
		}
	}
}
