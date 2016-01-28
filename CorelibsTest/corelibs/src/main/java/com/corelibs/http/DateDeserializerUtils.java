package com.corelibs.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class DateDeserializerUtils implements JsonDeserializer<java.util.Date> {

	@Override
	public java.util.Date deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
	}
}