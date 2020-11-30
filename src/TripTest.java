import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

class TripTest {
	 static Trips trip;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		 trip = new Trips();
	}


	
	@Test
	void testGetDiff() throws ParseException {
		Assert.assertEquals(600, Trips.getDiff("27-11-2020 14:30:00", "27-11-2020 14:40:00"));
	}
	
	@Test
	void testGetChargeAmountCompleteTrip()
	{
		Assert.assertEquals(7.30, Trips.getChargeAmount("Stop1", "Stop3"));
	}
	
	
	
	@Test
	void testGetChargeAmountIncompleteTrip()
	{
		Assert.assertEquals(5.50, Trips.getChargeAmount("Stop2", "0"));
	}
	
	@Test
	void testGetChargeAmountCancelledrip()
	{
		Assert.assertEquals(0.0, Trips.getChargeAmount("Stop3", "Stop3"));
	}
	

}
