package com.slimsimapps.ava.enums;

public enum ExceptionType {
	ENTITY_NOT_FOUND("not.found"),
	DUPLICATE_ENTITY("duplicate"),
	ENTITY_NULL("is.null"),
	ENTITY_EXCEPTION("exception");

	String value;

	ExceptionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
