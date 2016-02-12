package test.ruready.web.bean;

import net.ruready.web.user.beans.ResultsPagingBean;

import org.junit.Assert;
import org.junit.Test;

import test.ruready.common.rl.TestBase;

public class TestResultsPagingBean extends TestBase
{
	@Test
	public void testDefaultPagingSetup() throws Exception
	{
		final ResultsPagingBean bean = new ResultsPagingBean(10);
		Assert.assertEquals(10, bean.getTotalResults());
		Assert.assertEquals(10, bean.getResultsPerPage());
		Assert.assertEquals(1, bean.getCurrentPage());
		Assert.assertEquals(1, bean.getTotalPages());
		Assert.assertEquals(1, bean.getCurrentPageFirstResult());
		Assert.assertEquals(10, bean.getCurrentPageLastResult());
		Assert.assertEquals(0, bean.getCurrentPageFirstResultZeroBased());
	}

	@Test
	public void testDefaultPagingSetup2() throws Exception
	{
		final ResultsPagingBean bean = new ResultsPagingBean(3);
		Assert.assertEquals(3, bean.getTotalResults());
		Assert.assertEquals(10, bean.getResultsPerPage());
		Assert.assertEquals(1, bean.getCurrentPage());
		Assert.assertEquals(1, bean.getTotalPages());
		Assert.assertEquals(1, bean.getCurrentPageFirstResult());
		Assert.assertEquals(3, bean.getCurrentPageLastResult());
		Assert.assertEquals(0, bean.getCurrentPageFirstResultZeroBased());
	}

	@Test
	public void testDefaultPagingSetup3() throws Exception
	{
		final ResultsPagingBean bean = new ResultsPagingBean(13);
		Assert.assertEquals(13, bean.getTotalResults());
		Assert.assertEquals(10, bean.getResultsPerPage());
		Assert.assertEquals(1, bean.getCurrentPage());
		Assert.assertEquals(2, bean.getTotalPages());
		Assert.assertEquals(1, bean.getCurrentPageFirstResult());
		Assert.assertEquals(10, bean.getCurrentPageLastResult());
		Assert.assertEquals(0, bean.getCurrentPageFirstResultZeroBased());
	}

	@Test
	public void testPagingSetupDifferentResultsPerPage() throws Exception
	{
		final ResultsPagingBean bean = new ResultsPagingBean(13, 7);
		Assert.assertEquals(13, bean.getTotalResults());
		Assert.assertEquals(7, bean.getResultsPerPage());
		Assert.assertEquals(1, bean.getCurrentPage());
		Assert.assertEquals(2, bean.getTotalPages());
		Assert.assertEquals(1, bean.getCurrentPageFirstResult());
		Assert.assertEquals(7, bean.getCurrentPageLastResult());
		Assert.assertEquals(0, bean.getCurrentPageFirstResultZeroBased());
	}

	@Test
	public void testZeroResults() throws Exception
	{
		final ResultsPagingBean bean = new ResultsPagingBean(0);
		Assert.assertEquals(0, bean.getTotalResults());
		Assert.assertEquals(10, bean.getResultsPerPage());
		Assert.assertEquals(1, bean.getCurrentPage());
		Assert.assertEquals(1, bean.getTotalPages());
		Assert.assertEquals(0, bean.getCurrentPageFirstResult());
		Assert.assertEquals(0, bean.getCurrentPageLastResult());
		Assert.assertEquals(0, bean.getCurrentPageFirstResultZeroBased());
	}

	@Test
	public void testPaging1() throws Exception
	{
		final ResultsPagingBean bean = new ResultsPagingBean(23);
		Assert.assertEquals(1, bean.getCurrentPage());
		Assert.assertEquals(1, bean.getCurrentPageFirstResult());
		Assert.assertEquals(10, bean.getCurrentPageLastResult());
		Assert.assertEquals(0, bean.getCurrentPageFirstResultZeroBased());
		bean.gotoNextPage();
		Assert.assertEquals(2, bean.getCurrentPage());
		Assert.assertEquals(11, bean.getCurrentPageFirstResult());
		Assert.assertEquals(20, bean.getCurrentPageLastResult());
		Assert.assertEquals(10, bean.getCurrentPageFirstResultZeroBased());
		bean.gotoNextPage();
		Assert.assertEquals(3, bean.getCurrentPage());
		Assert.assertEquals(21, bean.getCurrentPageFirstResult());
		Assert.assertEquals(23, bean.getCurrentPageLastResult());
		Assert.assertEquals(20, bean.getCurrentPageFirstResultZeroBased());
		bean.gotoPreviousPage();
		Assert.assertEquals(2, bean.getCurrentPage());
		Assert.assertEquals(11, bean.getCurrentPageFirstResult());
		Assert.assertEquals(20, bean.getCurrentPageLastResult());
		Assert.assertEquals(10, bean.getCurrentPageFirstResultZeroBased());
	}

	@Test
	public void testPaging2() throws Exception
	{
		final ResultsPagingBean bean = new ResultsPagingBean(23);
		Assert.assertEquals(1, bean.getCurrentPage());
		bean.gotoPreviousPage();
		Assert.assertEquals(1, bean.getCurrentPage());
		bean.gotoLastPage();
		Assert.assertEquals(3, bean.getCurrentPage());
		bean.gotoNextPage();
		Assert.assertEquals(3, bean.getCurrentPage());
	}
}
