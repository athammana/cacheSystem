import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;
/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	
	public class IntStringProviderTest<Integer, String> implements DataProvider<Integer, String> {
		@Override
		public String get(Integer key) {
			return (String) key.toString();
		}

	}

	
	DataProvider<Integer,String> provider = new Provider<Integer, String>(); // Need to instantiate an actual DataProvider
	Cache<Integer,String> cache = new LRUCache<Integer,String>(provider, 3);
	
	@Before
	public void firstTestInit() {

	}
	
	@Test
	public void leastRecentlyUsedIsCorrect () {
		assertEquals(cache.get(18), "18");
		assertEquals(cache.get(41), "41");
		assertEquals(cache.get(59), "59");
		assertEquals(cache.getNumMisses(), 3);
		assertEquals(cache.get(48), "48"); 
		assertEquals(cache.getNumMisses(), 4);
	}
	
	@Test
	public void checkIfNumMissWorks () {
		assertEquals(cache.get(48), "48");
		assertEquals(cache.getNumMisses(), 1);
		assertEquals(cache.get(41), "41");
		assertEquals(cache.getNumMisses(), 2);
		assertEquals(cache.get(59), "59");
		assertEquals(cache.getNumMisses(), 3);
	}

	
}
