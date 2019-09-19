import java.util.HashMap;

public class Provider<Integer, String> implements DataProvider<Integer, String> {
	
	
	@Override
	public String get(Integer key) {
		return (String) key.toString();
	}

}
