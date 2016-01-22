/*****************************************************************************************
 * Source File: InactiveTestHibernateQBE.java
 ****************************************************************************************/
package test.ruready.eis;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.junit.Assert;
import org.junit.Test;

import test.ruready.eis.qbe.Product;
import test.ruready.eis.qbe.Supplier;
import test.ruready.rl.TestEnvTestBase;

/**
 * Test Hibernate one-to-many association. In this example, it is
 * {@link Supplier} - {@link Product}. The main question is whether specifying
 * <code>cascade=ALL</code> annotation on the product list automatically
 * deletes the foreign key entry from a product database table row when the
 * product is removed from the supplier's list, but not explicitly saved (only
 * the supplier is saved).
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
public class TestOneToMany extends TestEnvTestBase
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(TestOneToMany.class);

	// ========================= FIELDS ====================================

	// ========================= SETUP METHODS =============================

	/**
	 * @see test.ruready.rl.TestEnvTestBase#cleanUp()
	 */
	@Override
	protected void cleanUp()
	{
		Session session = environment.getSession();
		session.beginTransaction();

		session.createQuery("delete Product p").executeUpdate();
		session.createQuery("delete Supplier s").executeUpdate();

		session.getTransaction().commit();

		environment.tearDown();
	}

	// ========================= PUBLIC TESTING METHODS ====================

	/**
	 * 
	 */
	@Test
	public void testOneToMany()
	{

		Session session = environment.getSession();
		session.beginTransaction();

		// =====================================================
		// Add a supplier that provides three products
		// =====================================================
		Supplier supplier1 = new Supplier();
		supplier1.setName("A Supplier");
		session.save(supplier1);

		Product product1 = new Product("Product 1", "Name for Product 1", 2.0);
		product1.setSupplier(supplier1);
		session.save(product1);

		Product product2 = new Product("Product 2", "Name for Product 2", 22.0);
		product2.setSupplier(supplier1);
		session.save(product2);

		Product product3 = new Product("Product 3", "Name for Product 3", 30.0);
		product1.setSupplier(supplier1);
		session.save(product3);

		supplier1.addProduct(product1);
		supplier1.addProduct(product2);
		supplier1.addProduct(product3);
		session.save(supplier1);

		session.getTransaction().commit();

		// =====================================================
		// Remove a product and save only the supplier; that
		// should update Supplier.products by cascading.
		// =====================================================

		session = environment.getSession();
		session.beginTransaction();

		supplier1.addProduct(product2);

		session.save(supplier1);

		session.getTransaction().commit();

		// =====================================================
		// Check the updated list of products
		// =====================================================

		List<?> results = findProducts(supplier1);
		displayProductsList(results);
		Assert.assertEquals(2, results.size());
	}

	// ========================= PRIVATE METHODS ===========================

	/**
	 * Find all products supplied by a supplier.
	 * 
	 * @param supplier
	 *            supplier
	 * @return list of products of this supplier
	 */
	private List<?> findProducts(final Supplier supplier)
	{
		Session session = environment.getSession();
		session.beginTransaction();

		Criteria prdCrit = session.createCriteria(Product.class);
		Product product = new Product();
		Example prdExample = Example.create(product).excludeProperty("price");

		Criteria suppCrit = prdCrit.createCriteria("supplier");
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
	private void displayProductsList(List<?> list)
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
