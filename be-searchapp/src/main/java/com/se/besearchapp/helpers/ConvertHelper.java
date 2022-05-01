package com.se.besearchapp.helpers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import com.se.besearchapp.request.FilterParam;
import com.se.besearchapp.request.FilterReq;

public class ConvertHelper {
	public static ArrayList<?> convertStringToList(Object obj) {
		ArrayList<?> list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			list = (ArrayList<?>) Arrays.asList((String[]) obj);
		} else if (obj instanceof Collection) {
			list = new ArrayList<>((Collection<?>) obj);
		}
		return list;
	}

	public static String getValueReq(FilterReq objFilterReq, String strKey) throws Exception {
		String strResult = null;
		if (objFilterReq.getFilterParams() != null && objFilterReq.getFilterParams().size() > 0) {
			FilterParam pram = objFilterReq.getFilterParams().stream().filter(pr -> pr.getKey().equals(strKey))
					.findAny().orElse(null);
			if (pram != null)
				strResult = pram.getValue();
		}
		return strResult;
	}

	public static Boolean compareEqual(Object value1, Object value2) {
		if (value1 == null && value2 == null)
			return true;
		if (value1 == null && value2 != null)
			return false;
		if (value1 != null && value2 == null)
			return false;
		if (value1 instanceof BigDecimal) {
			try {
				int intResult = ((BigDecimal) value1).compareTo((BigDecimal) value2);
				if (intResult == 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else
			return Objects.equals(value1, value2);
	}
}
