/**
 * ***************************************************************************************
 * Source File: ErrorUtil.java
 ***************************************************************************************
 */
package net.ruready.web.common.util;

import net.ruready.common.exception.InternationalizableErrorMessage;
import net.ruready.common.misc.Utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.springframework.context.MessageSource;

/**
 * Utilities related to error messages in actions in other parts of the code.
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
 * @version Aug 14, 2007
 */
public class ErrorUtil implements Utility {
    // ========================= CONSTANTS =================================

    /**
     * A logger that helps identify this class' printouts.
     */
    @SuppressWarnings("unused")
    private static final Log logger = LogFactory.getLog(ErrorUtil.class);

	// ========================= CONSTRUCTORS ==============================
    /**
     * <p>
     * Hide constructor in utility class.
     * </p>
     */
    private ErrorUtil() {

    }

	// ========================= METHODS ===================================
    /**
     * Convert a dynamic error message to a Struts action message. if the parser
     * error contains more than four arguments, the first four are taken for the
     * Struts message (up to four arguments are supported by Struts messages).
     * The parser error message is
     *
     * @param error parser error
     * @return struts action message
     */
    public static ActionMessage dynamicToActionMessage(
            InternationalizableErrorMessage error) {
        final String key = error.getKey();
        if (error.getNumArgs() == 0) {
            return new ActionMessage(key);
        } else if (error.getNumArgs() == 1) {
            return new ActionMessage(key, error.getArg(0));
        } else if (error.getNumArgs() == 2) {
            return new ActionMessage(key, error.getArg(0), error.getArg(1));
        } else if (error.getNumArgs() == 3) {
            return new ActionMessage(key, error.getArg(0), error.getArg(1), error
                    .getArg(2));
        } else if (error.getNumArgs() == 4) {
            return new ActionMessage(key, error.getArg(0), error.getArg(1), error
                    .getArg(2), error.getArg(3));
        }
        // Fallback: numArgs >= 4, take the first four arguments
        return new ActionMessage(key, error.getArg(0), error.getArg(1), error.getArg(2),
                error.getArg(3));
    }

    /**
     * Convert a parser error to a message in a Struts resource bundle. if the
     * parser error contains more than four arguments, the first four are taken
     * for the Struts message (up to four arguments are supported by Struts
     * messages).
     *
     * @param error parser error
     * @param messageResources resource bundle
     * @return message string from Struts resource bundle
     */
    public static String errorToActionMessage(InternationalizableErrorMessage error,
            MessageSource messageResources) {
        final String key = error.getKey();
        if (error.getNumArgs() == 0) {
            return messageResources.getMessage(key, null, "", null);
        } else if (error.getNumArgs() == 1) {
            return messageResources.getMessage(key, new Object[] {error.getArg(0)}, "", null);
//            return messageResources.getMessage(key, error.getArg(0));
        } else if (error.getNumArgs() == 2) {
            return messageResources.getMessage(key, new Object[] {error.getArg(0), error.getArg(1)}, "", null);
//            return messageResources.getMessage(key, error.getArg(0), error.getArg(1));
        } else if (error.getNumArgs() == 3) {
            return messageResources.getMessage(key, new Object[] {error.getArg(0), error.getArg(1), error.getArg(2)}, "", null);
//            return messageResources.getMessage(key, error.getArg(0), error.getArg(1),
//                    error.getArg(2));
        } else if (error.getNumArgs() == 4) {
            return messageResources.getMessage(key, new Object[] {error.getArg(0), error.getArg(1), error.getArg(2), error.getArg(3)}, "", null);
//            return messageResources.getMessage(key, error.getArg(0), error.getArg(1),
//                    error.getArg(2), error.getArg(3));
        }
        // Fallback: numArgs >= 4, take the first four arguments
        return messageResources.getMessage(key, new Object[] {error.getArg(0), error.getArg(1), error.getArg(2), error.getArg(3), }, "", null);
//        return messageResources.getMessage(key, error.getArg(0), error.getArg(1), error
//                .getArg(2), error.getArg(3));
    }

}
