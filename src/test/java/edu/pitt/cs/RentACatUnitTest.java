package edu.pitt.cs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatUnitTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	ByteArrayOutputStream out; // Output stream for testing system output
	PrintStream stdout; // Print stream to hold the original stdout stream
	String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n") for use in assertEquals

	@Before
	public void setUp() throws Exception {
		
		r = RentACat.createInstance(InstanceType.IMPL);

		c1 = Cat.createInstance(InstanceType.MOCK, 1, "Jennyanydots");
		c2 = Cat.createInstance(InstanceType.MOCK, 2, "Old Deuteronomy");
		c3 = Cat.createInstance(InstanceType.MOCK, 3, "Mistoffelees");

		// Mockito.when(c1.getId()).thenReturn(1);
		// Mockito.when(c1.getName()).thenReturn("Jennyanydots");
		
		// Mockito.when(c2.getId()).thenReturn(2);
		// Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");

		// Mockito.when(c3.getId()).thenReturn(3);
		// Mockito.when(c3.getName()).thenReturn("Mistoffelees");


		// 5. Redirect system output from stdout to the "out" stream
		// First, make a back up of System.out (which is the stdout to the console)
		stdout = System.out;
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

	}

	@After
	public void tearDown() throws Exception {
		// Restore System.out to the original stdout
		System.setOut(stdout);

		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNullNumCats0() throws Exception {
		// TODO: Fill in
		Method getCat = r.getClass().getDeclaredMethod("getCat", int.class);
		getCat.setAccessible(true);
		Cat cat = (Cat) getCat.invoke(r,2);
		assertNull(cat);
		assertEquals("Invalid cat ID." + newline, out.toString());
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 * 
	 * Hint: You will need to use Java reflection to invoke the private getCat(int)
	 * method. efer to the Unit Testing Part 1 lecture and the textbook appendix 
	 * hapter on using reflection on how to do this.  Please use r.getClass() to get
	 * the class object of r instead of hardcoding it as RentACatImpl.
	 */
	@Test
	public void testGetCatNumCats3() throws Exception{
		// TODO: Fill in
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Method getCat = r.getClass().getDeclaredMethod("getCat",int.class);
		getCat.setAccessible(true);

		//Cat cat = getCat.invoke(r,2);

		Cat cat = (Cat) getCat.invoke(r, 2);
		assertNotNull(cat);
		assertEquals(2, cat.getId());
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats0() {
		// TODO: Fill in

		String list = r.listCats();
		assertEquals("", list);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */
	@Test
	public void testListCatsNumCats3() {
		// TODO: Fill in
		Mockito.when(c1.toString()).thenReturn("ID 1. Jennyanydots");
		Mockito.when(c2.toString()).thenReturn("ID 2. Old Deuteronomy");
		Mockito.when(c3.toString()).thenReturn("ID 3. Mistoffelees");

		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		String list = r.listCats();

		assertEquals( "ID 1. Jennyanydots" + newline + "ID 2. Old Deuteronomy" + newline + "ID 3. Mistoffelees", list);
		
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is false.
	 *                 c2 is not renamed to "Garfield".
	 *                 System output is "Invalid cat ID." + newline.
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRenameFailureNumCats0() {
		// TODO: Fill in
		boolean result = r.renameCat(2, "Garfield");
		assertFalse(result);
		assertEquals("Invalid cat ID." + newline, out.toString());
		Mockito.verify(c2, Mockito.never()).renameCat("Garfield");
	}

	/**
	 * Test case for boolean renameCat(int id, String name).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call renameCat(2, "Garfield").
	 * Postconditions: Return value is true.
	 *                 c2 is renamed to "Garfield".
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRenameNumCat3() {
		// TODO: Fill in
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		boolean result = r.renameCat(2, "Garfield");
		assertTrue(result);
		Mockito.verify(c2).renameCat("Garfield");
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is rented as a result of the execution steps.
	 *                 System output is "Old Deuteronomy has been rented." + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRentCatNumCats3() {
		// TODO: Fill in
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		Mockito.when(c2.getRented()).thenReturn(false);
		Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
		
		boolean result = r.rentCat(2);
		assertTrue(result);
		assertEquals("Old Deuteronomy has been rented." + newline, out.toString());
		Mockito.verify(c2).rentCat();
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 is not rented as a result of the execution steps.
	 *                 System output is "Sorry, Old Deuteronomy is not here!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testRentCatFailureNumCats3() {
		// TODO: Fill in
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		Mockito.when(c2.getRented()).thenReturn(true);
		Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
		
		boolean result = r.rentCat(2);
		assertFalse(result);
		assertEquals("Sorry, Old Deuteronomy is not here!" + newline, out.toString());
		Mockito.verify(c2, Mockito.never()).rentCat();
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2 is returned as a result of the execution steps.
	 *                 System output is "Welcome back, Old Deuteronomy!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testReturnCatNumCats3() {
		// TODO: Fill in
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		Mockito.when(c2.getRented()).thenReturn(true);
		Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
		
		boolean result = r.returnCat(2);
		assertTrue(result);
		assertEquals("Welcome back, Old Deuteronomy!" + newline, out.toString());
		Mockito.verify(c2).returnCat();
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 *                 c2 is not returned as a result of the execution steps.
	 *                 System output is "Old Deuteronomy is already here!" + newline
	 * </pre>
	 * 
	 * Hint: You may need to use behavior verification for this one. See
	 * sample_code/junit_example/LinkedListUnitTest.java in the course repository to
	 * see examples.
	 */
	@Test
	public void testReturnFailureCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		Mockito.when(c2.getRented()).thenReturn(false);
		Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
		
		boolean result = r.returnCat(2);
		assertFalse(result);
		assertEquals("Old Deuteronomy is already here!" + newline, out.toString());
		Mockito.verify(c2, Mockito.never()).returnCat();
	}

}