package engine.util.collection;

import java.util.List;
import java.util.ArrayList;

public class Lists {
	private Lists() {}
	
	public static <T> List<List<T>> chunk(List<T> list, int sublength) {
		List<List<T>> partitions = new ArrayList<>();
		for (int i = 0; i < list.size(); i += sublength)
			partitions.add(list.subList(i, Math.min(i + sublength, list.size() - 1)));
		return partitions;
	}
}
