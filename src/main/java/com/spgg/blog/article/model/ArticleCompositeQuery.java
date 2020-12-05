package com.article.model;

import java.util.*;

public class ArticleCompositeQuery {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;
		
		
		if ("article_type".equals(columnName) || "article_status".equals(columnName)) 
			aCondition = columnName + "=" + value;
		else if ("article_title".equals(columnName) || "mem_no".equals(columnName) || "article_no".equals(columnName)) {
			aCondition = columnName + " like '%" + value + "%'";
		}
//		else if ("hiredate".equals(columnName))                          
//			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());
				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

//				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("article_title", new String[] { "試試" });
		
		String finalSQL = "select * from article "
				          + ArticleCompositeQuery.get_WhereCondition(map)
				          + "order by article_date";
		System.out.println("finalSQL = " + finalSQL);
		
		ArticleService articleSvc = new ArticleService();
		List<ArticleVO> articleList = articleSvc.getAll(map);
		
		System.out.println(articleList);
	}
}
