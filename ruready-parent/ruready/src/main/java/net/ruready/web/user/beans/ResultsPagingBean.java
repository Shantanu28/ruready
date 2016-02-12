package net.ruready.web.user.beans;

import java.io.Serializable;

public class ResultsPagingBean implements Serializable
{
	private static final long serialVersionUID = -4146982794290391662L;

	public static final int DEFAULT_RESULTS_PER_PAGE = 10;
	
	private int totalResults;
	private int resultsPerPage;
	private int currentPage;
	
	public ResultsPagingBean(final int totalResults)
	{
		this(totalResults, DEFAULT_RESULTS_PER_PAGE, 1);
	}
	
	public ResultsPagingBean(final int totalResults, final int resultsPerPage)
	{
		this(totalResults, resultsPerPage, 1);
	}
	
	public ResultsPagingBean(final int totalResults, final int resultsPerPage, final int currentPage)
	{
		this.totalResults = totalResults;
		setResultsPerPage(resultsPerPage);
		setCurrentPage(currentPage);
	}
	
	/**
	 * Returns whether the current page is the first page.
	 * @return true if the current page is the first page, otherwise false
	 */
	public Boolean isFirstPage()
	{
		return getCurrentPage() == 1;
	}
	
	/**
	 * Returns whether the current page is the first page.
	 * Overloaded method for compatibility with JSP EL
	 * @return true if the current page is the first page, otherwise false
	 * @see #isFirstPage()
	 */
	public Boolean getIsFirstPage()
	{
		return isFirstPage();
	}
	
	public void gotoFirstPage()
	{
		setCurrentPage(1);
	}
	
	/**
	 * Returns whether the current page is the last page.
	 * @return true if the current page is the last page, otherwise false
	 */
	public Boolean isLastPage()
	{
		return getCurrentPage() == getTotalPages();
	}
	
	/**
	 * Returns whether the current page is the last page.
	 * Overloaded method for compatibility with JSP EL
	 * @return true if the current page is the last page, otherwise false
	 * @see #isLastPage()
	 */
	public Boolean getIsLastPage()
	{
		return isLastPage();
	}
	
	public void gotoLastPage()
	{
		setCurrentPage(getTotalPages());
	}
	
	/**
	 * Returns whether this paging bean has a previous page or not.
	 * @return true if there is a previous page available, otherwise false
	 */	
	public Boolean hasPreviousPage()
	{
		return getCurrentPage() > 1;
	}
	
	/**
	 * Returns whether this paging bean has a previous page or not.
	 * Overloaded method for compatibility with JSP EL
	 * @return true if there is a previous page available, otherwise false
	 * @see #hasPreviousPage()
	 */
	public Boolean getHasPreviousPage()
	{
		return hasPreviousPage();
	}
	
	/**
	 * Returns whether this paging bean has a next page or not.
	 * @return true if there is a next page available, otherwise false
	 */
	public Boolean hasNextPage()
	{
		return getCurrentPage() < getTotalPages();
	}
	
	/**
	 * Returns whether this paging bean has a next page or not.
	 * Overloaded method for compatibility with JSP EL
	 * @return true if there is a next page available, otherwise false
	 * @see #hasNextPage()
	 */
	public Boolean getHasNextPage()
	{
		return hasNextPage();
	}
	
	public void gotoPreviousPage()
	{
		if (hasPreviousPage())
		{
			setCurrentPage(getCurrentPage() - 1);
		}
	}
	
	public void gotoNextPage()
	{
		if (hasNextPage())
		{
			setCurrentPage(getCurrentPage() + 1);
		}
	}

	public int getResultsPerPage()
	{
		return resultsPerPage;
	}

	public void setResultsPerPage(final int resultsPerPage)
	{
		if (resultsPerPage < 1)
		{
			throw new IllegalArgumentException("resultsPerPage must be at least 1.");
		}
		this.resultsPerPage = resultsPerPage;
	}
	
	public Boolean hasPage(final int page)
	{
		if ((page >= 1) && (page <= getTotalPages()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(final int currentPage)
	{
		if (!hasPage(currentPage))
		{
			throw new IllegalArgumentException("currentPage must be a valid page within the search results.");
		}
		this.currentPage = currentPage;
	}

	public int getTotalResults()
	{
		return totalResults;
	}
	
	public void setTotalResults(final int totalResults)
	{
		this.totalResults = totalResults;
	}
	
	public int getTotalPages()
	{
		if (getTotalResults() == 0)
		{
			return 1;
		}
		else if (getTotalResults() % getResultsPerPage() > 0)
		{
			return (getTotalResults() / getResultsPerPage()) + 1;
		}
		else 
		{
			return getTotalResults() / getResultsPerPage();
		}
	}
	
	public int getCurrentPageFirstResult()
	{
		if (getTotalResults() == 0)
		{
			return 0;
		}
		return (getCurrentPage() - 1) * getResultsPerPage() + 1;
	}
	
	public int getCurrentPageLastResult()
	{
		if (getCurrentPage() == getTotalPages())
		{
			return getTotalResults();
		}
		else
		{
			return (getCurrentPage()) * getResultsPerPage();
		}
	}
	
	public int getCurrentPageFirstResultZeroBased()
	{
		if (getCurrentPageFirstResult() > 0)
		{
			return getCurrentPageFirstResult() - 1;
		}
		else 
		{
			return 0;
		}
	}
}
