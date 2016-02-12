/*****************************************************************************************
 * Source File: Dangle.java
 ****************************************************************************************/
package test.ruready.common.parser.pretty;

import net.ruready.common.parser.core.manager.Alternation;
import net.ruready.common.parser.core.manager.Parser;
import net.ruready.common.parser.core.manager.Sequence;
import net.ruready.common.parser.core.tokens.Literal;
import net.ruready.common.parser.core.tokens.Num;
import net.ruready.common.parser.core.tokens.Symbol;
import net.ruready.common.parser.core.tokens.Word;

/**
 * This class provides an ambiguous parser in its <code>
 * statement</code> method, which
 * serves to show that the test classes can find ambiguity.
 * <p>
 * The grammar this class supports is:
 * 
 * <pre>
 *       statement     = iff | ifelse | callCustomer | sendBill;
 *       iff           = &quot;if&quot; comparison statement;
 *       ifelse        = &quot;if&quot; comparison statement 
 *                       &quot;else&quot; statement;
 *       comparison    = '(' expression operator expression ')';
 *       expression    = Word | Num;
 *       operator      = '&lt;' | '&gt;' | '=' | &quot;&lt;=&quot; | &quot;&gt;=&quot; | &quot;!=&quot;;
 *       optionalElse  = &quot;else&quot; statement | Empty;
 *       callCustomer  = &quot;callCustomer&quot; '('')' ';';
 *       sendBill      = &quot;sendBill&quot; '('')' ';';
 * </pre>
 * 
 * @author Steven J. Metsker
 * @version 1.0
 */
public class Dangle
{
	protected static Alternation statement;

	/*
	 * Return a parser that recognizes the grammar: callCustomer = "callCustomer" '(' ')'
	 * ';';
	 */
	public static Parser callCustomer()
	{
		Sequence s = new Sequence("<callCustomer>");
		s.add(new Literal("callCustomer"));
		s.add(new Symbol('('));
		s.add(new Symbol(')'));
		s.add(new Symbol(';'));
		return s;
	}

	/*
	 * Return a parser that recognizes the grammar: comparison = '(' expression operator
	 * expression ')';
	 */
	public static Parser comparison()
	{
		Sequence s = new Sequence("<comparison>");
		s.add(new Symbol('('));
		s.add(expression());
		s.add(operator());
		s.add(expression());
		s.add(new Symbol(')'));
		return s;
	}

	/*
	 * Return a parser that recognizes the grammar: expression = Word | Num;
	 */
	public static Parser expression()
	{
		Alternation a = new Alternation("<expression>");
		a.add(new Word());
		a.add(new Num());
		return a;
	}

	/*
	 * Return a parser that recognizes the grammar: ifelse = "if" comparison statement
	 * "else" statement;
	 */
	public static Parser ifelse()
	{
		Sequence s = new Sequence("<ifelse>");
		s.add(new Literal("if"));
		s.add(comparison());
		s.add(statement());
		s.add(new Literal("else"));
		s.add(statement());
		return s;
	}

	/*
	 * Return a parser that recognizes the grammar: iff = "if" comparison statement;
	 */
	public static Parser iff()
	{
		Sequence s = new Sequence("<iff>");
		s.add(new Literal("if"));
		s.add(comparison());
		s.add(statement());
		return s;
	}

	/*
	 * Return a parser that recognizes the grammar: operator = '<' | '>' | '=' | "<=" |
	 * ">=" | "!=";
	 */
	public static Parser operator()
	{
		Alternation a = new Alternation("<operator>");
		a.add(new Symbol('<'));
		a.add(new Symbol('>'));
		a.add(new Symbol('='));
		a.add(new Symbol("<="));
		a.add(new Symbol(">="));
		a.add(new Symbol("!="));
		return a;
	}

	/*
	 * Return a parser that recognizes the grammar: sendBill = "sendBill" '('')' ';';
	 */
	public static Parser sendBill()
	{
		Sequence s = new Sequence("<sendBill>");
		s.add(new Literal("sendBill"));
		s.add(new Symbol('('));
		s.add(new Symbol(')'));
		s.add(new Symbol(';'));
		return s;
	}

	/**
	 * Return a parser that recognizes the grammar: <blockquote>
	 * 
	 * <pre>
	 *       statement    = &quot;if&quot; comparison statement optionalElse |
	 *                       callCustomer | sendBill;
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @return a parser that recognizes a statement
	 */
	public static Parser statement()
	{
		if (statement == null)
		{
			statement = new Alternation("<statement>");
			statement.add(iff());
			statement.add(ifelse());
			statement.add(callCustomer());
			statement.add(sendBill());
		}
		return statement;
	}
}
