package com.backinfile.dSync.parser;

import java.util.ArrayList;
import java.util.List;

public class DSyncStruct {
	public final DSyncStructType type;
	public List<DSyncVariable> children = new ArrayList<>();

	public DSyncStruct(DSyncStructType type) {
		this.type = type;
	}

}
