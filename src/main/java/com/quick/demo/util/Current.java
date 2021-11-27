package com.quick.demo.util;

import java.time.Instant;
import java.util.Random;

public class Current {

	private static final Random RANDOM = new Random(1000);

	public static long toMills() {
		return Instant.now().toEpochMilli();
	}

	public static String uniqueId() {
		return String.valueOf(toMills()) + RANDOM.nextInt();
	}

}
