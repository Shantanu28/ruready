package net.ruready.web.user.beans;

import static org.apache.commons.lang.StringUtils.remove;
import static org.apache.commons.lang.WordUtils.capitalizeFully;
import static org.apache.commons.lang.WordUtils.uncapitalize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.ruready.business.user.entity.property.UserField;
import net.ruready.business.user.entity.property.ViewableUserField;
import net.ruready.common.search.Logic;
import net.ruready.common.search.SearchType;
import net.ruready.web.common.rl.WebAppNames;
import net.ruready.web.common.util.StrutsUtil;
import net.ruready.web.select.exports.Option;
import net.ruready.web.select.exports.OptionList;
import net.ruready.web.select.exports.OptionListSourceFactory;

import org.apache.struts.util.MessageResources;

public class SearchCriteriaOptions implements Serializable
{
	private static final long serialVersionUID = 4400200773712122876L;

	private final OptionList logicOptions;
	private final OptionList searchableFieldNameOptions;
	private final OptionList viewableFieldNameOptions;
	private final OptionList textSearchTypeOptions;
	private final OptionList enumSearchTypeOptions;
	private final List<Option> viewableUserFieldList;

	public SearchCriteriaOptions(final MessageResources messageResources)
	{
		this.logicOptions = getOptionList(messageResources, Logic.class);
		this.searchableFieldNameOptions = getOptionList(messageResources, UserField.class);
		this.textSearchTypeOptions = getFilteredOptionList(messageResources,
				SearchType.class, UserField.getTextSearchTypes());
		this.enumSearchTypeOptions = getEnumSearchTypeOptionList(this.textSearchTypeOptions);
		this.viewableFieldNameOptions = getOptionList(messageResources,
				ViewableUserField.class);
		this.viewableUserFieldList = buildViewableFieldNamesList();
	}

	public OptionList getLogicOptions()
	{
		return logicOptions;
	}

	public OptionList getSearchableFieldNameOptions()
	{
		return searchableFieldNameOptions;
	}

	public OptionList getViewableFieldNameOptions()
	{
		return viewableFieldNameOptions;
	}

	public OptionList getTextSearchTypeOptions()
	{
		return textSearchTypeOptions;
	}

	public OptionList getEnumSearchTypeOptions()
	{
		return enumSearchTypeOptions;
	}

	public List<Option> getViewableUserFieldList()
	{
		return viewableUserFieldList;
	}

	private OptionList getEnumSearchTypeOptionList(final OptionList optionList)
	{
		final EnumSet<SearchType> enumSearchTypes = EnumSet.of(SearchType.EQ);
		final OptionList filteredList = new OptionList();
		for (Option bean : optionList)
		{
			if (enumSearchTypes.contains(SearchType.valueOf(bean.getValue())))
			{
				filteredList.add(bean);
			}
		}
		return filteredList;
	}

	private OptionList getOptionList(final MessageResources messageResources,
			final Class<?> type)
	{
		return StrutsUtil.i18NSelection(type, true,
				WebAppNames.KEY.MESSAGE.OLS_ENUM_OPTION_SUFFIX,
				WebAppNames.KEY.MESSAGE.SEPARATOR, messageResources);
	}

	private <E> OptionList getFilteredOptionList(final MessageResources messageResources,
			final Class<E> type, final List<E> allowedTypes)
	{
		// Generate filtered selection
		OptionList options = OptionListSourceFactory.getOptionListSource(
				WebAppNames.OLS.TYPE_ENUM_LIST, allowedTypes).getOptions();
		// i18 labels
		StrutsUtil.i18NEnumeratedSelection(options, type,
				WebAppNames.KEY.MESSAGE.OLS_ENUM_OPTION_SUFFIX,
				WebAppNames.KEY.MESSAGE.SEPARATOR, messageResources,
				WebAppNames.KEY.MESSAGE.OLS_NO_SELECTION_LABEL);
		return options;
	}

	private final List<Option> buildViewableFieldNamesList()
	{
		final List<Option> list = new ArrayList<Option>();
		for (ViewableUserField field : ViewableUserField.values())
		{
			final String methodKey = uncapitalize(remove(capitalizeFully(field.name(),
					new char[]
					{ '_' }), '_'));
			final Option bean = new Option(field.name(), methodKey);
			list.add(bean);
		}
		return list;
	}
}