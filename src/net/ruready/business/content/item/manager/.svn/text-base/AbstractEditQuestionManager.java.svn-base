package net.ruready.business.content.item.manager;

import java.util.List;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.question.entity.Question;
import net.ruready.business.content.question.entity.QuestionCount;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.trash.entity.AbstractTrash;
import net.ruready.common.exception.ApplicationException;
import net.ruready.common.rl.BusinessManager;

/**
 * Abstract specific {@link Question} business delegation methods in addition
 * generic item BD methods applied to {@link Question}. This manager also
 * handles question trash clean-ups.
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
 * @author Greg Felice <code>&lt;greg.felice@gmail.com&gt;</code>
 * @version Aug 9, 2007
 */
public interface AbstractEditQuestionManager extends BusinessManager,
		AbstractEditItemManager<Question>, AbstractTrash
{
	/**
	 * Find all questions scheduled for deletion.
	 * 
	 * @return list of questions scheduled for deletion
	 * @throws ApplicationException
	 *             if a DAO problem occurred
	 */
	List<Question> findDeleted();

	/**
	 * Find questions of a certain type and level under a certain node.
	 * 
	 * @param parent
	 *            parent node to look under
	 * @param questionType
	 *            if non-<code>null</code>, searches for this question type
	 *            (academic/creative)
	 * @param level
	 *            if non-<code>null</code>, searches for this difficulty
	 *            level
	 * @param parametric
	 *            if non-<code>null</code>, restricts the set of questions
	 *            to parametric (if <code>true</code>) or non-parametric (if
	 *            <code>false</code>)
	 * @return list of questions of this type, level and under this parent
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findQuestionCount(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.question.entity.property.QuestionType,
	 *      int)
	 */
	List<Question> findQuestions(final Item parent, final QuestionType questionType,
			final Integer level, final Boolean parametric);

	/**
	 * Find the number of question of a certain type and level under a certain
	 * node.
	 * 
	 * @param parent
	 *            parent node to look under
	 * @param questionType
	 *            if non-<code>null</code>, searches for this question type
	 *            (academic/creative)
	 * @param level
	 *            if non-<code>null</code>, searches for this difficulty
	 *            level
	 * @param parametric
	 *            if non-<code>null</code>, restricts the set of questions
	 *            to parametric (if <code>true</code>) or non-parametric (if
	 *            <code>false</code>)
	 * @return number of questions of this type, level and under this parent
	 * @see net.ruready.business.content.item.manager.AbstractEditQuestionManager#findQuestionCount(net.ruready.business.content.item.entity.Item,
	 *      net.ruready.business.content.question.entity.property.QuestionType,
	 *      int)
	 */
	long findQuestionCount(final Item parent, final QuestionType questionType,
			final Integer level, final Boolean parametric);

	/**
	 * Generate an object holding the number of question of every type and level
	 * under a certain node.
	 * 
	 * @param parent
	 *            parent node to look under
	 * @return an object holding the number of question of every type and level
	 */
	QuestionCount generateQuestionCount(final Item parent);
}
