package net.ruready.web.common.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Generates code to be inserted in StripEntities, InsertEntities, htmlcheat.html. Builds
 * serialised HashMap of associations to convert entities to numbers. Needs be run only
 * once to create entitytochar.ser Entities.class itself does not need to be included in
 * jars. Generates Java case: source code for converting numbers to entities, and loading
 * entity table efficiently. Only has to be run once ever. This class is not needed to
 * execute StripEntities or InsertEntities. The *.ser file is no longer used.
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
 * Please contact these numbers immediately if you receive this file without permission
 * from the authors. Thank you.<br>
 * -------------------------------------------------------------------------
 * 
 * @author Roedy Green version information in {@link StripEntities}.
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 13, 2007
 */
class Entities
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Entities.class);

	// ------------------------------ FIELDS ------------------------------

	/**
	 * entities as case statements to convert number to entity.
	 */
	static PrintWriter entityCase;

	/**
	 * entities for the HTML Cheat sheet.
	 */
	static PrintWriter entityHTML;

	/**
	 * entity names without &;
	 */
	static PrintWriter entityKeys;

	/**
	 * allows lookup by entity name, to get the corresponding char.
	 */
	// private static HashMap<String /* entity */, Character /* unicode */>
	// entityToChar = new HashMap<String, Character>(511);
	private static HashMap<String, Character> entityToChar = new HashMap<String, Character>(
			511);

	/**
	 * character codes
	 */
	static PrintWriter entityValues;

	/**
	 * For Vslick editor vslick\builtins\html.tagdoc
	 */
	static PrintWriter entityVslick;

	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Used to build the entityToChar conversion table. Builds one entry
	 * 
	 * @param theChar
	 *            Character equivalent
	 * @param entity
	 *            The entity equivalent with lead & and ; stripped.
	 */
	@SuppressWarnings("unchecked")
	private static final void associate(int theChar, String entity)
	{
		entityToChar.put(entity, new Character((char) theChar));
		// display Java source code for the association of number to entity
		entityCase.print("case ");
		// quick and dirty way to get numbers to right align
		if (theChar < 100)
		{
			entityCase.print(" ");
		}
		if (theChar < 1000)
		{
			entityCase.print(" ");
		}
		entityCase.println(theChar + ": return \"&" + entity + ";\";");

		entityKeys.println("\"" + entity + "\" /* " + theChar + " */,");

		entityValues.println(theChar + " /* " + "&" + entity + "; */,");

		entityHTML.println("<td>&" + entity + ";</td>\n" + "<td>&amp;" + entity
				+ ";</td>\n" + "<td>description here...</td>\n" + "</tr>");

		entityVslick.println("const " + entity + ";");
	}

	private static void makeAssociations()
	{
		// build hash lookup table of associations char codes/numeric entities
		// to entities.
		// Note that lead & and trailing ; have been stripped.
		// List generated from http://www.w3.org/TR/html4/sgml/entities.html
		// on 2005-06-20
		// if you add to this table, add to the table in InsertEntities
		// and add to the SlickEdit tables. jprep.HTMLTokenizer uses these methods
		// so does not need adjusting.
		// If you add to this table, also check out the tables in
		// com.mindprod.quoter.HTMLReservedChars
		// and com.mindprod.quoter.JavaReserveedChars
		// these should be converted to use Entities.

		// table has 252 entries
		associate(34, "quot");
		associate(38, "amp");
		associate(60, "lt");
		associate(62, "gt");
		associate(160, "nbsp");
		associate(161, "iexcl");
		associate(162, "cent");
		associate(163, "pound");
		associate(164, "curren");
		associate(165, "yen");
		associate(166, "brvbar");
		associate(167, "sect");
		associate(168, "uml");
		associate(169, "copy");
		associate(170, "ordf");
		associate(171, "laquo");
		associate(172, "not");
		associate(173, "shy");
		associate(174, "reg");
		associate(175, "macr");
		associate(176, "deg");
		associate(177, "plusmn");
		associate(178, "sup2");
		associate(179, "sup3");
		associate(180, "acute");
		associate(181, "micro");
		associate(182, "para");
		associate(183, "middot");
		associate(184, "cedil");
		associate(185, "sup1");
		associate(186, "ordm");
		associate(187, "raquo");
		associate(188, "frac14");
		associate(189, "frac12");
		associate(190, "frac34");
		associate(191, "iquest");
		associate(192, "Agrave");
		associate(193, "Aacute");
		associate(194, "Acirc");
		associate(195, "Atilde");
		associate(196, "Auml");
		associate(197, "Aring");
		associate(198, "AElig");
		associate(199, "Ccedil");
		associate(200, "Egrave");
		associate(201, "Eacute");
		associate(202, "Ecirc");
		associate(203, "Euml");
		associate(204, "Igrave");
		associate(205, "Iacute");
		associate(206, "Icirc");
		associate(207, "Iuml");
		associate(208, "ETH");
		associate(209, "Ntilde");
		associate(210, "Ograve");
		associate(211, "Oacute");
		associate(212, "Ocirc");
		associate(213, "Otilde");
		associate(214, "Ouml");
		associate(215, "times");
		associate(216, "Oslash");
		associate(217, "Ugrave");
		associate(218, "Uacute");
		associate(219, "Ucirc");
		associate(220, "Uuml");
		associate(221, "Yacute");
		associate(222, "THORN");
		associate(223, "szlig");
		associate(224, "agrave");
		associate(225, "aacute");
		associate(226, "acirc");
		associate(227, "atilde");
		associate(228, "auml");
		associate(229, "aring");
		associate(230, "aelig");
		associate(231, "ccedil");
		associate(232, "egrave");
		associate(233, "eacute");
		associate(234, "ecirc");
		associate(235, "euml");
		associate(236, "igrave");
		associate(237, "iacute");
		associate(238, "icirc");
		associate(239, "iuml");
		associate(240, "eth");
		associate(241, "ntilde");
		associate(242, "ograve");
		associate(243, "oacute");
		associate(244, "ocirc");
		associate(245, "otilde");
		associate(246, "ouml");
		associate(247, "divide");
		associate(248, "oslash");
		associate(249, "ugrave");
		associate(250, "uacute");
		associate(251, "ucirc");
		associate(252, "uuml");
		associate(253, "yacute");
		associate(254, "thorn");
		associate(255, "yuml");
		associate(338, "OElig");
		associate(339, "oelig");
		associate(352, "Scaron");
		associate(353, "scaron");
		associate(376, "Yuml");
		associate(402, "fnof");
		associate(710, "circ");
		associate(732, "tilde");
		associate(913, "Alpha");
		associate(914, "Beta");
		associate(915, "Gamma");
		associate(916, "Delta");
		associate(917, "Epsilon");
		associate(918, "Zeta");
		associate(919, "Eta");
		associate(920, "Theta");
		associate(921, "Iota");
		associate(922, "Kappa");
		associate(923, "Lambda");
		associate(924, "Mu");
		associate(925, "Nu");
		associate(926, "Xi");
		associate(927, "Omicron");
		associate(928, "Pi");
		associate(929, "Rho");
		associate(931, "Sigma");
		associate(932, "Tau");
		associate(933, "Upsilon");
		associate(934, "Phi");
		associate(935, "Chi");
		associate(936, "Psi");
		associate(937, "Omega");
		associate(945, "alpha");
		associate(946, "beta");
		associate(947, "gamma");
		associate(948, "delta");
		associate(949, "epsilon");
		associate(950, "zeta");
		associate(951, "eta");
		associate(952, "theta");
		associate(953, "iota");
		associate(954, "kappa");
		associate(955, "lambda");
		associate(956, "mu");
		associate(957, "nu");
		associate(958, "xi");
		associate(959, "omicron");
		associate(960, "pi");
		associate(961, "rho");
		associate(962, "sigmaf");
		associate(963, "sigma");
		associate(964, "tau");
		associate(965, "upsilon");
		associate(966, "phi");
		associate(967, "chi");
		associate(968, "psi");
		associate(969, "omega");
		associate(977, "thetasym");
		associate(978, "upsih");
		associate(982, "piv");
		associate(8194, "ensp");
		associate(8195, "emsp");
		associate(8201, "thinsp");
		associate(8204, "zwnj");
		associate(8205, "zwj");
		associate(8206, "lrm");
		associate(8207, "rlm");
		associate(8211, "ndash");
		associate(8212, "mdash");
		associate(8216, "lsquo");
		associate(8217, "rsquo");
		associate(8218, "sbquo");
		associate(8220, "ldquo");
		associate(8221, "rdquo");
		associate(8222, "bdquo");
		associate(8224, "dagger");
		associate(8225, "Dagger");
		associate(8226, "bull");
		associate(8230, "hellip");
		associate(8240, "permil");
		associate(8242, "prime");
		associate(8243, "Prime");
		associate(8249, "lsaquo");
		associate(8250, "rsaquo");
		associate(8254, "oline");
		associate(8260, "frasl");
		associate(8364, "euro");
		associate(8465, "image");
		associate(8472, "weierp");
		associate(8476, "real");
		associate(8482, "trade");
		associate(8501, "alefsym");
		associate(8592, "larr");
		associate(8593, "uarr");
		associate(8594, "rarr");
		associate(8595, "darr");
		associate(8596, "harr");
		associate(8629, "crarr");
		associate(8656, "lArr");
		associate(8657, "uArr");
		associate(8658, "rArr");
		associate(8659, "dArr");
		associate(8660, "hArr");
		associate(8704, "forall");
		associate(8706, "part");
		associate(8707, "exist");
		associate(8709, "empty");
		associate(8711, "nabla");
		associate(8712, "isin");
		associate(8713, "notin");
		associate(8715, "ni");
		associate(8719, "prod");
		associate(8721, "sum");
		associate(8722, "minus");
		associate(8727, "lowast");
		associate(8730, "radic");
		associate(8733, "prop");
		associate(8734, "infin");
		associate(8736, "ang");
		associate(8743, "and");
		associate(8744, "or");
		associate(8745, "cap");
		associate(8746, "cup");
		associate(8747, "int");
		associate(8756, "there4");
		associate(8764, "sim");
		associate(8773, "cong");
		associate(8776, "asymp");
		associate(8800, "ne");
		associate(8801, "equiv");
		associate(8804, "le");
		associate(8805, "ge");
		associate(8834, "sub");
		associate(8835, "sup");
		associate(8836, "nsub");
		associate(8838, "sube");
		associate(8839, "supe");
		associate(8853, "oplus");
		associate(8855, "otimes");
		associate(8869, "perp");
		associate(8901, "sdot");
		associate(8968, "lceil");
		associate(8969, "rceil");
		associate(8970, "lfloor");
		associate(8971, "rfloor");
		associate(9001, "lang");
		associate(9002, "rang");
		associate(9674, "loz");
		associate(9824, "spades");
		associate(9827, "clubs");
		associate(9829, "hearts");
		associate(9830, "diams");
	}

	// --------------------------- main() method ---------------------------

	/**
	 * Run once to generate the serialised HashMap entitytochar.ser for StripEntitities.
	 * Also generate Java source case: code for InsertEntities.
	 * 
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args)
	{
		try
		{
			// O P E N
			FileWriter entityCaseFw = new FileWriter("entitycase.javafrag");
			BufferedWriter entityCaseBw = new BufferedWriter(entityCaseFw, 4096 /* buffsize */);
			entityCase = new PrintWriter(entityCaseBw, false);

			FileWriter entityKeysFw = new FileWriter("entitykeys.javafrag");
			BufferedWriter entityKeysBw = new BufferedWriter(entityKeysFw, 4096 /* buffsize */);
			entityKeys = new PrintWriter(entityKeysBw, false);

			FileWriter entityValuesFw = new FileWriter("entityValues.javafrag");
			BufferedWriter entityValuesBw = new BufferedWriter(entityValuesFw, 4096 /* buffsize */);
			entityValues = new PrintWriter(entityValuesBw, false);

			FileWriter entityHTMLFw = new FileWriter("entityHTML.htmlfrag");
			BufferedWriter entityHTMLBw = new BufferedWriter(entityHTMLFw, 4096 /* buffsize */);
			entityHTML = new PrintWriter(entityHTMLBw, false);

			FileWriter entityVslickFw = new FileWriter("entityVslick.tagdoc");
			BufferedWriter entityVslickBw = new BufferedWriter(entityVslickFw, 4096 /* buffsize */);
			entityVslick = new PrintWriter(entityVslickBw, false);

			FileOutputStream fos = new FileOutputStream("entitytochar.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			makeAssociations();

			// W R I T E
			// entityToChar is already built by makeAssociations
			oos.writeObject(entityToChar);

			// C L O S E
			entityCase.close();
			entityKeys.close();
			entityValues.close();
			entityHTML.close();
			entityVslick.close();
			oos.close();
		}
		catch (IOException e)
		{
			logger.error(e.getMessage());
		}
	} // end main
} // end Entities
