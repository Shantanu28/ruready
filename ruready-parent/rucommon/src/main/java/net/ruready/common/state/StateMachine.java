/*****************************************************************************************
 * Source File: StateMachine.java
 ****************************************************************************************/
package net.ruready.common.state;

import net.ruready.common.observer.Observer;
import net.ruready.common.observer.Subject;

/**
 * A (Turing) finite state machine in a state pattern. Has a state but only exposes its
 * its unique identifier. Supports state transitional operations. Is an observer of its
 * own state (the state is usually managed by a state handler class), as well as a subject
 * for other observers to take action upon state change.
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
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Jul 31, 2007
 */
public interface StateMachine<ID extends StateID, A extends StateMachineAction<ID, A, M>, M extends StateMachine<ID, A, M>>
		extends Subject, Observer
{
	// ========================= CONSTANTS =================================

	// ========================= ABSTRACT METHODS ==========================

	/**
	 * Update a question's state. The operation argument uses a call-back to invoke the
	 * appropriate method of this object.
	 * 
	 * @param action
	 *            state transition action
	 */
	void updateState(A action);

	// ========================= ABSTRACT METHODS: GETTERS & SETTERS =======

	/**
	 * Return the unique identifier of the internal state of this object.
	 * 
	 * @return the state identifier
	 */
	ID getStateID();

}
