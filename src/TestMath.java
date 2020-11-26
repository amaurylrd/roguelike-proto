public class TestMath { //arithmetic 2d class
	//diference final, private construct, abstract ?

	public static float lerp(float a, float b, float f) {
		return a + f*(b - a);
	}

	public static double quickExp(double a, int n) {
		if (n == 1)
			return a;
		if (n % 2 == 0) {
			double transition = quickExp(a, n/2);
			return transition*transition;
		}
		double transition = quickExp(a, (n - 1)/2);
		return transition*transition*a;
	}

	public static void quickSort(double[] array, int fst, int lst) {
		if (fst < lst) {
			int pivot = fst;
			int i = fst;
			int j = lst;

			while (i < j) {
				while (array[i] <= array[pivot] && i < lst)
					i++;
				while (array[j] > array[pivot])
					j--;

				if (i < j) {
					double tmp = array[i];
					array[i] = array[j];
					array[j] = tmp;
				}
			}
			
			double tmp = array[pivot];
			array[pivot] = array[j];
			array[j] = tmp;
			quickSort(array, fst, j - 1);
			quickSort(array, j + 1, lst);
		}
	}

	/**
	 * Returns a number whose value is limited to the given range.
	 *
	 * @param min the lower boundary of the output range
	 * @param max the upper boundary of the output range
	 * @return a number in the range [min, max]
	 */
	public static double clamp(double value, double min, double max) {
		return Math.min(Math.max(value, min), max);
	}
}