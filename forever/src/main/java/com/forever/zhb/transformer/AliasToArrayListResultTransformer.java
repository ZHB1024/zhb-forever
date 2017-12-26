package com.forever.zhb.transformer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

public class AliasToArrayListResultTransformer implements ResultTransformer {

	public List transformList(List collection) {
		return collection;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		List<Object[]> result = new ArrayList<Object[]>(tuple.length);
        for ( int i=0; i<tuple.length; i++ ) {
            String alias = aliases[i];
            if ( alias!=null ) {
                Object[] ele = new Object[]{alias,tuple[i]};
                result.add(ele);
            }
        }
        return result;
	}

}
