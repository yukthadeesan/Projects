//a few tests that test my implementation
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

public class MyHashMapTest<K> {
	
	private DefaultMap<String, String> testMap; // use this for basic tests
	private DefaultMap<String, String> mapWithCap; // use for testing proper rehashing
	public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
	
	@Before
	public void setUp() {
		testMap = new MyHashMap<>();
		mapWithCap = new MyHashMap<>(4, MyHashMap.DEFAULT_LOAD_FACTOR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPut_nullKey() {
		testMap.put(null, TEST_VAL);
	}

	@Test
	public void testKeys_nonEmptyMap() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		assertEquals(expectedKeys, resultKeys);
	}
	@Test
	//testing put method
	public void testput() { 
		testMap.put(TEST_KEY,TEST_VAL);
		boolean expected = true;
		boolean actual=testMap.containsKey(TEST_KEY);
		assertEquals(actual, expected);
	}
	
	@Test
	//testing empty method 
	public void testEmpty() { 
		assertEquals(testMap.isEmpty(), true);
		testMap.put(TEST_KEY, TEST_VAL);
		assertEquals(testMap.isEmpty(), false);
	}
	@Test
	//testing get and put method
	public void testPutandGet() { 
		testMap.put(TEST_KEY,TEST_VAL);
		assertEquals(testMap.get(TEST_KEY), TEST_VAL);
	}
	@Test
	//testing set method
	public void testSet() { 
		testMap.put(TEST_KEY,TEST_VAL);
		testMap.set(TEST_KEY, "hello");
		assertEquals(testMap.get(TEST_KEY), "hello");
	}
	@Test
	//testing replace method 
	public void testReplace() {
		testMap.put(TEST_KEY,TEST_VAL);
		testMap.replace(TEST_KEY, "hello");
		assertEquals(testMap.get(TEST_KEY), "hello");
		assertEquals(testMap.replace("world", "hello"), false);
	}
	@Test
	//testing size of Hashmap implementation
	public void testSize() {
		testMap.put(TEST_KEY,TEST_VAL);
		testMap.replace(TEST_KEY, "hello");
		assertEquals(testMap.size(), 1);
	}
	//testing if hashmap implementation contains a particular key 
	@Test
	public void testContains() {
		testMap.put(TEST_KEY,TEST_VAL);
		assertEquals(testMap.containsKey(TEST_KEY), true);
		assertEquals(testMap.containsKey("hello"), false);
	}
	@Test
	//testing output for keys method.
	public void testkeysarray() {
		testMap.put(TEST_KEY,TEST_VAL);
		testMap.put("hello","world");
		List<K> actual = new ArrayList<>();
		actual.add((K) TEST_KEY);
		actual.add((K) "hello");
		assertEquals(testMap.keys(), actual);
	}
}
