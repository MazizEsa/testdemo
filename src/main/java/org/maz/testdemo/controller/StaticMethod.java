package org.maz.testdemo.controller;

public class StaticMethod {
	private StaticMethod() {
	}

	public static String returnStaticValue() {
		return "REAL_STATIC_VALUE";
	}

	public static String buildValue(final String param) {
		return "REAL_"+param;
	}
}
