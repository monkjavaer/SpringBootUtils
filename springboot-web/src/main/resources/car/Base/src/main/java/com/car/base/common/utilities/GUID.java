package com.car.base.common.utilities;

import java.util.UUID;

public class GUID {
	public static final String generate() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase();
	}
}
