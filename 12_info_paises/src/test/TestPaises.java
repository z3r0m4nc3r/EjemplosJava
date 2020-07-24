package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import service.ServicePaises;

public class TestPaises {
	ServicePaises service;
	@Before
	public void setUp() throws Exception {
		service =new ServicePaises();
	}

	@Test
	public void testContinenteConMasPaises() {
		assertEquals(service.continenteConMasPaises(), "Europe");
		
	}

}
