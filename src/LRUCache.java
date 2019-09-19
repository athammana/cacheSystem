import java.util.HashMap;
// write a way to evict from the least recently used cache, so look at the head of the recents and evict that from the
// actual cache to create a cache that fits the capacity, should be done in the get function; also the recent
/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	
	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	
	private HashMap<T, LinkedHash.Node<T, U>> _cache;
	private LinkedHash<T, U> _recents;
	private int _capacity;
	private int _misses;
	private DataProvider <T, U> _provider;
	
	
	public LRUCache (DataProvider<T, U> provider, int capacity) {
		_capacity = capacity;
		_cache = new HashMap<T, LinkedHash.Node<T, U>>(_capacity);
		_recents = new LinkedHash<T, U>(null, null);
		_provider = provider;
		_misses = 0;
	}
 
	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
		if(_cache.containsKey(key)) {
			_recents.moveToEnd(key);
			return _cache.get(key).getValue();
		}else {
			_misses++;
			_recents.add(key, _provider.get(key));
			_cache.put(key, _recents._tail);
			if(_recents.numElements > _capacity) {
				_cache.remove(_recents._head.getKey());
				_recents.remove(_recents._head);
			}
			return _cache.get(key).getValue();
		}
	}
	
	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return _misses;
	}
	
	private static class LinkedHash<T, U> {
		private static class Node<T, U> {
			protected Node<T, U> _prev, _next;
			private T _key;
			private U _value;
			
			public Node(T key, U value, Node<T, U> prev, Node<T, U> next) {
				_prev = prev;
				_next = next;
				_key = key;
				_value = value;
			}
			/**
			 * Returns the key for this Node
			 * @return the key for this Node
			 */
			private T getKey() {
				return _key;
			}
			/**
			 * Returns the value for this Node
			 * @return the value for this Node
			 */
			private U getValue() {
				return _value;
			}
		}
		
		private Node<T, U> _head, _tail;
		private int numElements;
		
		public LinkedHash(Node<T, U> head, Node<T, U> tail) {
			_head = head;
			_tail = tail;
		}
		/**
		 * Returns void
		 * @param key the key and the given value with that key
		 * @return void
		 */
		private void add(T key, U value) {
			if(_head == null) {
				Node<T, U> cursor = new Node(key, value, null, null);
				_head = cursor;
				_tail = cursor;
				numElements++;
			} else {
				Node<T, U> cursor = new Node(key, value, _tail, null);
				_tail._next = cursor;
				cursor._prev = _tail;
				_tail = cursor;
				numElements++;
			}
		}
		/**
		 * Returns the associated Node with the given key
		 * @param key the key
		 * @return the associated Node with the given key
		 */
		private Node<T, U> get(T key) {
			Node<T,U> cursor = _head;
			for(int i = 0; i < numElements; i++) {
				if(cursor.getKey().equals(key)) {
					return cursor;
				}
				cursor = cursor._next;
			}
			return null;
		}
		/**
		 * Removes the given Node from the linkedList, used only for head
		 * @param Node the Node
		 * @return void
		 */
		private void remove(Node<T, U> cursor) {
			cursor._next._prev = null;
			cursor._next = null;
		}
		/**
		 * Moves the Node associated with the given key to the end so it is the most recently used one
		 * @param key the key
		 * @return void
		 */
		private void moveToEnd(T key) {
			Node<T, U> cursor = get(key);
			if(key.equals(_tail.getKey())) {
				return;
			}
			cursor._prev._next = cursor._next;
			cursor._next._prev = cursor._prev;
			_tail._next = cursor;
			cursor._prev = _tail;
			cursor._next = null;
			_tail = cursor;
		}
	}
	
	
}
