package org.fxapps.temis.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.fxapps.temis.model.Alderman;
import org.junit.BeforeClass;
import org.junit.Test;

public class TemisClientServiceTest {
	
	private final static String BASE_URL = "http://temis-server.herokuapp.com/";
	
	private static TemisClientService temisClientService;
	
	@BeforeClass
	public static void before() {
		temisClientService = TemisClientService.get(BASE_URL);
	}

	@Test
	public void testAlderman() {
		List<Alderman> aldermen = temisClientService.aldermen();
		String aldermenNames = aldermen.stream().map(Alderman::getName).collect(Collectors.joining(", "));
		System.out.println("Alderman names: " + aldermenNames);
		assertEquals(29, aldermen.size());
		String firstAlderman = "Carlinhos Tiaca";
		String lastAlderman = "Tonhão Dutra";
		Optional<Alderman> foundFirst = aldermen.stream().filter(a ->  a.getName().equals(firstAlderman)).findFirst();
		assertTrue(foundFirst.isPresent());
		Optional<Alderman> foundLast = aldermen.stream().filter(a ->  a.getName().equals(lastAlderman)).findFirst();
		assertTrue(foundLast.isPresent());
	}

}
