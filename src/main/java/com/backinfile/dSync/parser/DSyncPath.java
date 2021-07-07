package com.backinfile.dSync.parser;

import java.util.ArrayList;
import java.util.List;

public class DSyncPath {
	public final String name;
	public List<DSyncPath> paths = new ArrayList<>();
	public List<DSyncVariable> variables = new ArrayList<>();

	public DSyncPath(String name) {
		this.name = name;
	}
}
