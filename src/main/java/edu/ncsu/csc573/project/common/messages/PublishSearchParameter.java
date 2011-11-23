package edu.ncsu.csc573.project.common.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PublishSearchParameter implements IParameter {
	private List<IParameter> complexParam = new ArrayList<IParameter>();
	private int count = 0;
	private IParameter current_param = null;

	public void add(EnumParamsType param, Object value) {

		if (count == 0 && current_param == null) {
			current_param = new Parameter();
		} else if (param == EnumParamsType.DELIMITER) {
			current_param.add(EnumParamsType.DELIMITER, count);
			complexParam.add(current_param);
			count++;
			current_param = new Parameter();
			return;
		}
		current_param.add(param, value);
	}

	public void add(PublishSearchParameter pubSearchParam) {
		if (this == pubSearchParam) {
			return;
		}
	    pubSearchParam.resetCounter();
		while (pubSearchParam.getParamCount() < pubSearchParam.getSize()) {
			this.add(EnumParamsType.FILENAME,
					pubSearchParam.getParamValue(EnumParamsType.FILENAME));
			this.add(EnumParamsType.FILEDIGEST,
					pubSearchParam.getParamValue(EnumParamsType.FILEDIGEST));
			this.add(EnumParamsType.FILESIZE,
					pubSearchParam.getParamValue(EnumParamsType.FILESIZE));
			this.add(EnumParamsType.ABSTRACT,
					pubSearchParam.getParamValue(EnumParamsType.ABSTRACT));
			this.add(EnumParamsType.IPADDRESS,
					pubSearchParam.getParamValue(EnumParamsType.IPADDRESS));
			this.add(EnumParamsType.DELIMITER, null);
			pubSearchParam.setNextParam();
		}
	}

	public void resetCounter() {
		count = 0;
	}

	public Object getParamValue(EnumParamsType paramType) {
		if (count >= complexParam.size()) {
			return null;
		}
		IParameter param = complexParam.get(count);
		return param.getParamValue(paramType);
	}

	public void setNextParam() {
		count++;
	}

	public Set<EnumParamsType> getAllParams() {
		return complexParam.get(0).getAllParams();
	}

	public int getParamCount() {
		return count;
	}

	public int getSize() {
		return complexParam.size();
	}
}
