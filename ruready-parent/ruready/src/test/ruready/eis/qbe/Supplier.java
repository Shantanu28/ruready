/*****************************************************************************************
 * Source File: Supplier.java
 ****************************************************************************************/
package test.ruready.eis.qbe;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import net.ruready.common.eis.entity.PersistentEntity;

/**
 * A supplier of <code>Product</code>s (parent class example).
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
 * @version Aug 12, 2007
 */
@Entity
public class Supplier implements PersistentEntity<Long>
{
	// ========================= CONSTANTS =================================

	/**
	 * @serial Serializable version identifier.
	 */
	private static final long serialVersionUID = 1L;

	// ========================= FIELDS ====================================

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * Supplier's name.
	 */
	private String name;

	/**
	 * List of products that this supplier provides.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
	private List<Product> products = new ArrayList<Product>();

	// ========================= CONSTRUCTORS ==============================

	/**
	 * Create an empty supplier object with no products.
	 */
	public Supplier()
	{
		super();
	}

	// ========================= METHODS ====================================

	public void addProduct(Product product)
	{
		products.add(product);
	}

	public void removeProduct(Product product)
	{
		products.remove(product);
	}
	
	// ========================= GETTERS & SETTERS ==========================

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	// /**
	// * Create and return an association manager for this entity type.
	// *
	// * @return an association manager for this entity type.
	// * @see net.ruready.common.eis.entity.PersistentEntity#createAssociationManager()
	// */
	// public AssociationManager createAssociationManager()
	// {
	// return null;
	//	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Product> getProducts()
	{
		return products;
	}

	public void setProducts(List<Product> products)
	{
		this.products = products;
	}
}
