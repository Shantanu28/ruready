/*****************************************************************************************
 * Source File: InactiveTestHibernateQBE.java
 ****************************************************************************************/
package test.ruready.eis.qbe;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.rl.StandAloneEnvTestBase;

/**
 * Testing Hibernate Query By Example (QBE) with multiple criteria of associated
 * entities.
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
 * -------------------------------------------------------------------------
 * 
 * @author Oren E. Livne <code>&lt;olivne@aoce.utah.edu&gt;</code>
 * @version Aug 25, 2007
 */
public class TestHibernateQBE extends StandAloneEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestHibernateQBE.class);

	// ========================= FIELDS ====================================

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#initialize()
	 */
	@Override
	protected void initialize()
	{
		Session session = environment.getSession();

		Supplier supplier1 = new Supplier();
		supplier1.setName("Supplier Name 1");
		session.save(supplier1);

		Supplier supplier2 = new Supplier();
		supplier2.setName("Supplier Name 2");
		session.save(supplier2);

		Product product1 = new Product("Product 1", "Name for Product 1", 2.0);
		product1.setSupplier(supplier1);
		supplier1.getProducts().add(product1);
		session.save(product1);

		Product product12 = new Product("Product 2", "Name for Product 2", 22.0);
		product12.setSupplier(supplier1);
		supplier1.getProducts().add(product12);
		session.save(product12);

		Product product2 = new Product("Product 3", "Name for Product 3", 30.0);
		product2.setSupplier(supplier2);
		supplier2.getProducts().add(product2);
		session.save(product2);
	}

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		Session session = environment.getSession();
		session.createQuery("delete Product p").executeUpdate();
		session.createQuery("delete Supplier s").executeUpdate();
	}

	// ========================= PUBLIC TESTING METHODS ====================

	@Test
	public void testQBE()
	{
		List<?> results = findProducts();
		displayProductsList(results);
		logger.debug("results " + results);
		Assert.assertEquals(2, results.size());
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Create a QBE with multiple criteria that use the association between
	 * {@link Product} and {@link Supplier}. In this case, we search for all
	 * products starting with "P" regardless of their price, that are supplied
	 * by a supplier with the name "Supplier Name 1".
	 * 
	 * @return list of products found
	 */
	private List<?> findProducts()
	{
		Session session = environment.getSession();
		session.beginTransaction();

		Criteria prdCrit = session.createCriteria(Product.class);
		Product product = new Product();
		product.setName("P%");
		Example prdExample = Example.create(product);
		prdExample.excludeProperty("price");
		prdExample.enableLike();
		Criteria suppCrit = prdCrit.createCriteria("supplier");
		Supplier supplier = new Supplier();
		supplier.setName("Supplier Name 1");
		suppCrit.add(Example.create(supplier));
		prdCrit.add(prdExample);
		List<?> results = prdCrit.list();

		session.getTransaction().commit();

		return results;
	}

	/**
	 * Display a result list of products
	 * 
	 * @param list
	 *            list of products obtained from a Hibernate QBE query
	 */
	public void displayProductsList(List<?> list)
	{
		Iterator<?> iter = list.iterator();
		if (!iter.hasNext())
		{
			logger.info("No products to display.");
			return;
		}
		while (iter.hasNext())
		{
			Product product = (Product) iter.next();
			String msg = product.getSupplier().getName() + "\t";
			msg += product.getName() + "\t";
			msg += product.getPrice() + "\t";
			msg += product.getDescription();
			logger.info(msg);
		}
	}
}
