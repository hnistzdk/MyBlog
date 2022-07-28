package com.zdk.blog.common.request.common;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:52
 */
public class SearchCondition extends Pager {
    private static final long serialVersionUID = 1L;
    private Map<String, Object> paramMap = new HashMap<>();
    private BaseSearchRequest searchBean;
    private String sort;
    private String order;

    public void addParam(String key, Object value) {
        this.paramMap.put(key, value);
    }

    public Map<String, Object> getParamMap() {
        return this.paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public BaseSearchRequest getSearchBean() {
        return this.searchBean;
    }

    public void setSearchBean(BaseSearchRequest searchBean) {
        this.setCurrPage(searchBean.getCurrPage());
        this.setPageSize(searchBean.getPageSize());
        if (StringUtils.isNotEmpty(searchBean.getSort()) && StringUtils.isNotEmpty(searchBean.getOrder())) {
            this.setSort(this.camel2Underline(searchBean.getSort()));
            this.setOrder("ascending".equals(searchBean.getOrder()) ? "asc" : "desc");
        }

        this.searchBean = searchBean;
    }

    private String camel2Underline(String line) {
        if (line != null && !"".equals(line)) {
            line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
            StringBuffer sb = new StringBuffer();
            String rex = "[A-Z]([a-z\\d]+)?";
            Pattern pattern = Pattern.compile(rex);
            Matcher matcher = pattern.matcher(line);

            while(matcher.find()) {
                String word = matcher.group();
                sb.append(word.toUpperCase());
                sb.append(matcher.end() == line.length() ? "" : "_");
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
