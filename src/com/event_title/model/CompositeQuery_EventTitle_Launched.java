package com.event_title.model;

import java.util.*;

public class CompositeQuery_EventTitle_Launched {
	
	
	
	
	
	public static String get_WhereCondition(Map<String, String[]> map) {
		
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
				
		for (String key : keys) {
			String[] values = map.get(key);
			
			
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {

				if ("evetit_name".equals(key)) {
					whereCondition.append("AND (" + key + " LIKE upper('%" + values[0] + "%') ");
					whereCondition.append("or " + key + " LIKE lower('%" + values[0] + "%')) ");
				}					
				
				else if ("eveclass_no".equals(key)) {
					whereCondition.append("AND " + key + " IN (");
					for(int i = 0; i < values.length; i++) {				
						whereCondition.append("'").append(values[i]).append("'");
						if( i != (values.length-1) ) {
							whereCondition.append(", ");
						}
					}
					whereCondition.append(") ");
				}

				else if ("evetit_startdate".equals(key)) {
					whereCondition.append("AND to_char(" + key + ",'yyyy-mm-dd') > '").append(values[0]).append("' ");
				}
				
				else if ("evetit_enddate".equals(key)) {
					whereCondition.append("AND to_char(" + key + ",'yyyy-mm-dd') < '").append(values[0]).append("' ");
				}
			}			
		}
		return whereCondition.toString();
	}
	

	
	
	
	public static void main(String argv[]) {

		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("evetit_name", new String[] { "5" });
		map.put("eveclass_no", new String[] { "A", "B" });
		map.put("evetit_startdate", new String[] { "2018-12-31" });
		map.put("evetit_enddate", new String[] { "2019-12-31" });

		String finalSQL = "SELECT * FROM EVENT_TITLE WHERE (evetit_status = 'confirmed') AND (CURRENT_DATE BETWEEN launchdate AND offdate) "
				          + CompositeQuery_EventTitle_Launched.get_WhereCondition(map)
				          + "ORDER BY promotionranking DESC, launchdate";
		System.out.println(finalSQL);
	}
	
}
