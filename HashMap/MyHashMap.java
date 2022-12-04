//my implementation of HashMap
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MyHashMap<K, V> implements DefaultMap<K, V> {
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	private double loadFactor;
	private int capacity;
	private int size;

	// Use this instance variable for Separate Chaining conflict resolution
	private List<HashMapEntry<K, V>>[] buckets;  
	
	// Use this instance variable for Linear Probing
	private HashMapEntry<K, V>[] entries; 	

	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * 
	 * @param initialCapacity the initial capacity of this MyHashMap
	 * @param loadFactor the load factor for rehashing this MyHashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
	 * positive
	 */
	
	@SuppressWarnings("unchecked")
	public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
		//throwing error for negative initial capacity
		if(initialCapacity<0) {
			throw new IllegalArgumentException();
		}
		this.capacity= initialCapacity;
		
		//throwing error for non-positive load factor
		if(loadFactor<=0) {
			throw new IllegalArgumentException();
		}
		this.loadFactor= loadFactor;
		
		// array of lists used for separate chaining
		buckets = (List<HashMapEntry<K, V>>[]) new List<?>[capacity];
		
		//Initialized size
		int size =0;
		
		//Created list of entries at every index of buckets array
		for(int i=0; i< this.buckets.length; i++) {
			List<HashMapEntry<K, V>> list = new ArrayList<>();
			buckets[i]=list;
		}
	}
	//helper method used for testing
	public void printArray(HashMapEntry<K, V>[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
	
	//helper method used to expand capacity incase the HashMap exceeds load factor
	public void expandCapacity() {
		List<HashMapEntry<K, V>>[] newBuckets= (List<HashMapEntry<K, V>>[]) new List<?>[this.buckets.length*2];
		for(int i=0; i< buckets.length; i++) {
			for(int j=0; j< buckets[i].size(); j++) {
				HashMapEntry<K, V> element= buckets[i].get(j);
				int keyHash = Objects.hashCode(element.getKey());
				int index= keyHash % newBuckets.length;
				if(index<0) {
					index+=newBuckets.length;
				}
				newBuckets[index].add(element);
				size++;
			}
		}
		this.buckets= newBuckets;
	}
	
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		int keyHash = Objects.hashCode(key);
		
		HashMapEntry<K, V> element = new HashMapEntry<>(key, value);
		
		//throwing error for null key
		if(key==null) {
			throw new IllegalArgumentException();
		}
		
		//calling expand capacity if load factor is exceeded 
		if((size/buckets.length) >= this.loadFactor) {
			expandCapacity();
		}
		
		int index= keyHash % this.buckets.length;
		if(index<0) {   //removing possibilities for negative index
			index+=buckets.length;
		}
		
		List<HashMapEntry<K, V>> bucket =  buckets[index];
		for(int j=0; j<bucket.size(); j++) {
			//checking if bucket already contains key
			if(bucket.get(j).getKey().equals(key)) {
				return false;
			}
		}
		bucket.add(element);
		size++;
		return true;
		
	}
	
	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		
		//throwing error for null key
		if(key==null) {
			throw new IllegalArgumentException();
		}
		
		int keyHash = Objects.hashCode(key);
		
		int index= keyHash % this.buckets.length;
		if(index<0) { //removing possibilities for negative index
			index+=buckets.length;
		}
		
		for(int i=0; i< buckets[index].size(); i++) {
			//checking if bucket already contains key
			if((buckets[index].get(i).getKey().equals(key))==true) {
					buckets[index].get(i).setValue(newValue);
					return true;
			}	
		}		
		//calling expand capacity if load factor is exceeded 
		if((size/buckets.length) >= this.loadFactor) {
			expandCapacity();
		}
		return false;
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		
		//throwing error for null key
		if(key==null) {
			throw new IllegalArgumentException();
		}
		
		int keyHash = Objects.hashCode(key);	
		
		int index= keyHash % this.buckets.length;
		if(index<0) { //removing possibilities for negative index
			index+=buckets.length;
		}
		
		for(int i=0; i< buckets[index].size(); i++) {
			if((buckets[index].get(i).getKey().equals(key))==true) {
					buckets[index].remove(i);
					size-=1;
					return true;
			}	
		}
		//calling expand capacity if load factor is exceeded
		if((size/buckets.length) >= this.loadFactor) {
			expandCapacity();
		}
		
		return false;
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		
		int keyHash = Objects.hashCode(key);
		
		HashMapEntry<K, V> element = new HashMapEntry<>(key, value);
		
		//throwing error for null key
		if(key==null) {
			throw new IllegalArgumentException();
		}
		
		//calling expand capacity if load factor is exceeded 
		if((size/buckets.length) >= this.loadFactor) {
			expandCapacity();
		}
		
		int index= keyHash % this.buckets.length;
		if(index<0) { //removing possibilities for negative index
			index+=buckets.length;
		}
		
		int check=0;
		List<HashMapEntry<K, V>> bucket =  buckets[index];
		for(int j=0; j<bucket.size(); j++) {
			if(bucket.get(j).getKey().equals(key)) {
				bucket.get(j).setValue(value);
				check=1; //to indicate that the entry has been replaced
			}
		}
		
		if(check==0) {
			bucket.add(element);
			size++;
		}
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		
		//throwing error for null key
		if(key==null) {
			throw new IllegalArgumentException();
		}
		
		int keyHash = Objects.hashCode(key);	
		
		int index= keyHash % this.buckets.length;
		if(index<0) { //removing possibilities for negative index
			index+=buckets.length;
		}
		
		for(int i=0; i< buckets[index].size(); i++) {
			if((buckets[index].get(i).getKey().equals(key))==true) {
				return buckets[index].get(i).getValue();
			}	
		}
		
		//calling expand capacity if load factor is exceeded
		if((size/buckets.length) >= this.loadFactor) {
			expandCapacity();
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if(size==0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		
		//throwing error for null key
		if(key==null) {
			throw new IllegalArgumentException();
		}
		
		int keyHash = Objects.hashCode(key);
		
		int index= keyHash % this.buckets.length;
		if(index<0) { //removing possibilities for negative index
			index+=buckets.length;
		}
		
		for(int i=0; i< buckets[index].size(); i++) {
			if((buckets[index].get(i).getKey().equals(key))==true) {
				return true;
			}	
		}
		return false;
	}

	@Override
	public List<K> keys() {
		List<K> arrayOfKeys= new ArrayList<K>();
		
		for(int i=0; i< buckets.length; i++) {
			for(int j=0; j< buckets[i].size(); j++) {
				arrayOfKeys.add(buckets[i].get(j).getKey());
			}
		}
		return arrayOfKeys;
	}
	
	private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {
		
		K key;
		V value;
		
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
		
		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}
}
