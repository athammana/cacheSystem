
public class IntStringProviderTest<Integer, String> implements DataProvider<Integer, String> {
	
	
	@Override
	public String get(Integer key) {
		return (String) key.toString();
	}

}