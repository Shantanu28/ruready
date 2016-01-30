/*****************************************************************************************
 * Source File: Question.java
 ****************************************************************************************/
package net.ruready.business.content.question.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import net.ruready.business.content.item.entity.Item;
import net.ruready.business.content.item.entity.ItemType;
import net.ruready.business.content.item.exports.ItemVisitor;
import net.ruready.business.content.question.entity.property.QuestionAction;
import net.ruready.business.content.question.entity.property.QuestionType;
import net.ruready.business.content.question.entity.state.QuestionState;
import net.ruready.business.content.question.entity.state.QuestionStateID;
import net.ruready.common.observer.Message;
import net.ruready.common.observer.Observer;
import net.ruready.common.pointer.ShallowCloneable;
import net.ruready.common.rl.CommonNames;
import net.ruready.common.state.StateMachine;
import net.ruready.common.text.TextUtil;
import net.ruready.common.tree.TreeVisitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.IndexColumn;

/**
 * A question entity. Contains non-standard (non-{@link Item}) children called
 * &quot;bastards&quot; (choices, hints, keywords, etc.). This steps out of the
 * normal {@link Item} tree hierarchy.
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
 * @version Jul 31, 2007
 */
@Entity
public class Question extends Item implements
		StateMachine<QuestionStateID, QuestionAction, Question>
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(Question.class);

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	/**
	 * List of multiple choices.
	 */
	@OneToMany(cascade = CascadeType.ALL /* , fetch = FetchType.EAGER */)
	@IndexColumn(name = "index_choice")
	private List<Choice> choices;

	/**
	 * List of solutions.
	 */
	@OneToMany(cascade = CascadeType.ALL/* , fetch = FetchType.EAGER */)
	@IndexColumn(name = "index_answer")
	private List<Answer> answers;

	/**
	 * List of hints.
	 */
	@OneToMany(cascade = CascadeType.ALL/* , fetch = FetchType.EAGER */)
	@IndexColumn(name = "index_hint")
	private List<Hint> hints;

	/**
	 * Determines whether question is academic or creative
	 */
	@Enumerated(value = EnumType.STRING)
	@Column
	private QuestionType questionType = null;

	/**
	 * Difficulty level.
	 */
	@Column
	private int level;

	/**
	 * The question (formulation).
	 */
	@Column(length = 1000)
	private String formulation = CommonNames.MISC.EMPTY_STRING;

	/**
	 * Delimited list of symbolic variables (e.g. "x y z").
	 */
	@Column(length = 150)
	private String variables;

	/**
	 * Parameter list (in parametric questions).
	 */
	@Column(length = 150)
	private String parameters;

	/**
	 * A parser flag that controls the precision to which expressions are
	 * compared.
	 */
	@Column
	private int questionPrecision;

	/**
	 * A parser flag that controls the treatment of equality relations as
	 * equations or assignments.
	 */
	// @Column
	// private boolean commutativeEquality;
	//
	/**
	 * Number of multiple choices.
	 */
	@Column
	private int numberOfChoices;

	/**
	 * Holds the current state of the question. Cannot be set outside this
	 * object.
	 */
	// @Embedded
	@OneToOne(cascade =
	{ CascadeType.ALL })
	private QuestionState state = new QuestionState();

	/**
	 * List of observers. Needed for the observer pattern.
	 */
	@Transient
	private List<Observer> observers = new ArrayList<Observer>();

	/**
	 * Make sure we add ourselves as an observer of the associated state object
	 * only once.
	 */
	@Transient
	private boolean isObserveState = false;

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Construct an empty unnamed question.
	 */
	public Question()
	{
		this(CommonNames.MISC.EMPTY_STRING, CommonNames.MISC.EMPTY_STRING);
	}

	/**
	 * Construct an empty named question.
	 * 
	 * @param name
	 *            question name
	 * @param comment
	 *            comment string
	 */
	public Question(String name, String comment)
	{
		super(name, comment);

		this.answers = new ArrayList<Answer>();
		this.choices = new ArrayList<Choice>();
		this.hints = new ArrayList<Hint>();
	}

	// ========================= IMPLEMENTATION: PubliclyCloneable =========

	/**
	 * Creates a shallow-copy clone of this object. Only copies fields and
	 * bastards, not children of this object (this object is anyway the leaf of
	 * the item hierarchy). Does not add the clone to the children of this
	 * object's parent.
	 * 
	 * @see net.ruready.business.common.tree.entity.Node#clone()
	 */
	@Override
	public Question shallowClone()
	{
		Question copy = (Question) super.shallowClone();

		// Custom field setting
		// Do not copy state; use the default state upon constructing a question
		copy.state = new QuestionState();

		copy.answers = new ArrayList<Answer>();
		for (Answer a : answers)
		{
			copy.addAnswer(a.clone());
		}

		copy.choices = new ArrayList<Choice>();
		for (Choice a : this.getChoices())
		{
			copy.addChoice(a.clone());
		}

		copy.hints = new ArrayList<Hint>();
		for (Hint a : this.getHints())
		{
			copy.addHint(a.clone());
		}

		return copy;
	}

	/**
	 * Does not copy all fields over - only data fields of this node. Children
	 * are not copied. Identity is not copied.
	 * 
	 * @param destination
	 * @see net.ruready.common.pointer.ShallowCloneable#mergeInto(net.ruready.common.pointer.ShallowCloneable)
	 */
	@Override
	public void mergeInto(final ShallowCloneable destination)
	{
		super.mergeInto(destination);

		Question dest = (Question) destination;

		// Merge answers
		for (Answer answer : answers)
		{
			int index = dest.getAnswers().indexOf(answer);
			if (index < 0)
			{
				// Source child not found, add a copy of its entire tree under
				// the
				// destination item.
				dest.addAnswer(answer.clone());
			}
			else
			{
				// Source child found under the destination item, merge it
				answer.mergeInto(dest.getAnswer(index));
			}
		}

		// Merge choices
		for (Choice choice : choices)
		{
			int index = dest.getChoices().indexOf(choice);
			if (index < 0)
			{
				// Source child not found, add a copy of its entire tree under
				// the
				// destination item.
				dest.addChoice(choice.clone());
			}
			else
			{
				// Source child found under the destination item, merge it
				choice.mergeInto(dest.getChoice(index));
			}
		}

		// Merge hints
		for (Hint hint : hints)
		{
			int index = dest.getHints().indexOf(hint);
			if (index < 0)
			{
				// Source child not found, add a copy of its entire tree under
				// the
				// destination item.
				dest.addHint(hint.clone());
			}
			else
			{
				// Source child found under the destination item, merge it
				hint.mergeInto(dest.getHint(index));
			}
		}
	}

	// ========================= IMPLEMENTATION: Identifiable ==============

	/**
	 * @return the type of this item
	 */
	@Override
	public ItemType getIdentifier()
	{
		return ItemType.QUESTION;
	}

	// ========================= IMPLEMENTATION: Node =========================

	/**
	 * Print the data and other properties of this node.
	 * 
	 * @return data and properties of this node, represented as a string
	 */
	@Override
	public String printData()
	{
		return "[" + getClass().getSimpleName() + "]" + " '" + getName() + "' ID " + id
				+ " " + state + " V" + getVersion() + " L" + level + " " + questionType;
	}

	// ========================= IMPLEMENTATION: Visitable<ItemVisitor> ====

	/**
	 * Let a visitor process this item. Part of the {@link TreeVisitor} pattern.
	 * This calls back the visitor's <code>visit()</code> method with this
	 * item type. Must be overridden by every item sub-class.
	 * 
	 * @param visitor
	 *            item visitor whose <code>visit()</code> method is invoked
	 */
	@Override
	public void accept(ItemVisitor visitor)
	{
		visitor.visit(this);
	}

	// ========================= IMPLEMENTATION: Item ======================

	/**
	 * Is this item editable through a standard edit page, or can we edit it
	 * only using some custom pages (e.g. by searching for items of this type
	 * and clicking on rows from the result set table). Returns
	 * <code>false</code>.
	 * 
	 * @return is this item editable through a standard edit page
	 */
	@Override
	public boolean isEditAccessible()
	{
		return false;
	}

	// ========================= METHODS ===================================

	/**
	 * Update a question's state. The operation argument uses a call-back to
	 * invoke the appropriate method of this object.
	 * 
	 * @param operation
	 *            question state transition operation
	 */
	public void updateState(QuestionAction operation)
	{
		operation.updateState(this);
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isActive()
	 */
	public boolean isActive()
	{
		return state.isActive();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isDeleted()
	 */
	public boolean isDeleted()
	{
		return state.isDeleted();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isDisabled()
	 */
	public boolean isDisabled()
	{
		return state.isDisabled();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isExpired()
	 */
	public boolean isExpired()
	{
		return state.isExpired();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isNew()
	 */
	public boolean isNew()
	{
		return state.isNew();
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#isUpdated()
	 */
	public boolean isUpdated()
	{
		return state.isUpdated();
	}

	/**
	 * Return a sorted set of permissible actions based upon a question's state.
	 * If the question is deleted, undelete. If the question is disabled,
	 * enable. If question is not deleted or disabled, clone, disable, delete,
	 * edit.
	 * 
	 * @return sorted set of actions
	 */
	public Set<QuestionAction> getActions()
	{
		return state.getActions();
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#delete()
	 */
	public void delete()
	{
		state.delete();
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#disable()
	 */
	public void disable()
	{
		state.disable();
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#enable()
	 */
	public void enable()
	{
		state.enable();
	}

	/**
	 * @param cutoffTime
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#timeout(long)
	 */
	public void timeout(final long cutoffTime)
	{
		state.timeout(cutoffTime);
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#undelete()
	 */
	public void undelete()
	{
		state.undelete();
	}

	/**
	 * @see net.ruready.business.content.question.entity.state.AbstractQuestionHandler#update()
	 */
	public void update()
	{
		state.update();
	}

	/**
	 * if question has parameters, returns true, otherwise returns false.
	 * 
	 * @return is question parametric
	 */
	public boolean isParametric()
	{
		return (!TextUtil.isEmptyTrimmedString(parameters));
	}

	// ========================= IMPLEMENTATION: Subject ===================

	/**
	 * @see net.ruready.common.observer.Subject#addObserver(net.ruready.common.observer.Observer)
	 */
	public void addObserver(Observer o)
	{
		observeState();
		observers.add(o);
	}

	/**
	 * Unsubscribe an observer with this subject.
	 * 
	 * @param o
	 *            observer to remove
	 * @see net.ruready.common.observer.Subject#removeObserver(net.ruready.common.observer.Observer)
	 */
	public void removeObserver(Observer o)
	{
		observers.remove(o);
	}

	/**
	 * Notify all observers of a change in the subject's internal state. The
	 * <code>Observer.update()</code> method will be called in all observers
	 * from this method, with an optional message.
	 * 
	 * @see net.ruready.common.observer.Subject#notifyObservers()
	 */
	public void notifyObservers()
	{
		Message message = prepareMessage();
		for (Observer observer : observers)
		{
			observer.update(message);
		}
	}

	/**
	 * Prepare a concrete message. This should use the subject's getters
	 * methods.
	 * 
	 * @return the message
	 * @see net.ruready.common.observer.SimpleSubject#prepareMessage()
	 */
	protected Message prepareMessage()
	{
		Message message = new QuestionStateChangeMessage(this);
		return message;
	}

	// ========================= IMPLEMENTATION: Observer ==================

	/**
	 * @param message
	 * @see net.ruready.common.observer.Observer#update(net.ruready.common.observer.Message)
	 */
	public void update(Message message)
	{
		notifyObservers();
	}

	// ========================= IMPLEMENTATION: StateMachine ==============

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.QuestionState#getIdentifier()
	 */
	public QuestionStateID getStateID()
	{
		return state.getIdentifier();
	}

	// ========================= GETTERS & SETTERS =========================

	/**
	 * We need it. It's not healthy though because Hibernate is managing the
	 * entity's ID.
	 * 
	 * @todo understand if we can get rid of it
	 * @param id
	 */
	public void setId(Long id)
	{
		if (id != null && id == 0)
			return;

		this.id = id;
	}

	// /**
	// * @return
	// */
	// public String getChapter()
	// {
	// return chapter;
	// }
	//
	// /**
	// * @param chapter
	// */
	// public void setChapter(String chapter)
	// {
	// this.chapter = chapter;
	// }

	// /**
	// * @return
	// */
	// public boolean isCommutativeEquality()
	// {
	// return commutativeEquality;
	// }
	//
	// /**
	// * @param commutativeEquality
	// */
	// public void setCommutativeEquality(boolean commutativeEquality)
	// {
	// this.commutativeEquality = commutativeEquality;
	// }

	/**
	 * @return
	 */
	public String getFormulation()
	{
		return formulation;
	}

	/**
	 * @param formulation
	 */
	public void setFormulation(String formulation)
	{
		this.formulation = formulation;
	}

	/**
	 * @return
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * @param level
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}

	// /**
	// * @return
	// */
	// public String getNumber()
	// {
	// return number;
	// }
	//
	// /**
	// * @param number
	// */
	// public void setNumber(String number)
	// {
	// this.number = number;
	// }
	//
	/**
	 * @return
	 */
	public int getNumberOfChoices()
	{
		return numberOfChoices;
	}

	/**
	 * @param numberOfChoices
	 */
	public void setNumberOfChoices(int numberOfChoices)
	{
		this.numberOfChoices = numberOfChoices;
	}

	/**
	 * @return
	 */
	public String getParameters()
	{
		return parameters;
	}

	/**
	 * @param parameters
	 */
	public void setParameters(String parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @return
	 */
	public int getQuestionPrecision()
	{
		return questionPrecision;
	}

	/**
	 * @param questionPrecision
	 */
	public void setQuestionPrecision(int questionPrecision)
	{
		this.questionPrecision = questionPrecision;
	}

	// /**
	// * @return
	// */
	// public String getTextbook()
	// {
	// return textbook;
	// }
	//
	// /**
	// * @param textbook
	// */
	// public void setTextbook(String textbook)
	// {
	// this.textbook = textbook;
	// }

	/**
	 * @return
	 */
	public QuestionType getQuestionType()
	{
		return questionType;
	}

	/**
	 * @param type
	 */
	public void setQuestionType(QuestionType type)
	{
		this.questionType = type;
	}

	/**
	 * @return
	 */
	public String getQuestionTypeAsString()
	{

		String qt;

		if (questionType != null)
			qt = this.questionType.toString();
		else
			qt = null;

		return qt;
	}

	/**
	 * @return
	 */
	public String getVariables()
	{
		return variables;
	}

	/**
	 * @param variables
	 */
	public void setVariables(String variables)
	{
		this.variables = variables;
	}

	// /**
	// * @return
	// */
	// public String getQuestionPage()
	// {
	// return questionPage;
	// }
	//
	// /**
	// * @param questionPage
	// */
	// public void setQuestionPage(String questionPage)
	// {
	// this.questionPage = questionPage;
	// }

	/**
	 * @return
	 */
	public List<Choice> getChoices()
	{
		return choices;
	}

	/**
	 * @param choices
	 */
	public void setChoices(List<Choice> choices)
	{
		this.choices = choices;
	}

	/**
	 * @return
	 */
	public List<Answer> getAnswers()
	{
		return answers;
	}

	/**
	 * @param answers
	 */
	public void setAnswers(List<Answer> answers)
	{
		this.answers = answers;
	}

	/**
	 * @return
	 */
	public List<Hint> getHints()
	{
		return hints;
	}

	/**
	 * @param hints
	 */
	public void setHints(List<Hint> hints)
	{
		this.hints = hints;
	}

	//
	// /**
	// * @return the state
	// */
	// public QuestionState getState()
	// {
	// return state;
	// }

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Establish a connection with the associated state object. In principle,
	 * this should be part of all constructors, but Hibernate bypasses them when
	 * it loads an object from the database, so we call it upon adding an
	 * observer (i.e. when it becomes relevant).
	 */
	private void observeState()
	{
		if (!isObserveState)
		{
			// Establish a connection with the state object
			state.addObserver(this);
			isObserveState = true;
		}
	}

	// ========================= DELEGATE METHODS ==========================

	/**
	 * @return
	 */
	public int numAnswers()
	{
		return answers.size();
	}

	/**
	 * @param i
	 * @return
	 */
	public Answer getAnswer(int i)
	{
		return answers.get(i);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addAnswer(final Answer e)
	{
		return answers.add(e);
	}

	/**
	 * @return
	 */
	public int numChoices()
	{
		return choices.size();
	}

	/**
	 * @param i
	 * @return
	 */
	public Choice getChoice(int i)
	{
		return choices.get(i);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addChoice(final Choice e)
	{
		return choices.add(e);
	}

	/**
	 * @return
	 */
	public int numHints()
	{
		return hints.size();
	}

	/**
	 * @param i
	 * @return
	 */
	public Hint getHint(int i)
	{
		return hints.get(i);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addHint(final Hint e)
	{
		return hints.add(e);
	}

	/**
	 * @return
	 * @see net.ruready.business.content.question.entity.state.QuestionState#getLastModified()
	 */
	public Date getLastModified()
	{
		return state.getLastModified();
	}

}
