package engine.physics2d;

public class Arithmetic {
	/**
	 * Gives the linear interpolation between two known points.
	 * 
	 * @param a the minimum value to evaluate
	 * @param b the maximum value to evaluate
	 * @param f the interpolation value between the two previous values
	 * @return the linear interpolation
	 */
	public static float lerp(float a, float b, float f) {
		return a + f*(b - a);
	}
	
	/**
	 * Returns a number whose value is limited to the given range.
	 *
	 * @param value th specified number to clamp
	 * @param min the lower boundary of the output range
	 * @param max the upper boundary of the output range
	 * @return a number in the range [min, max]
	 */
	public static float clamp(float value, float min, float max) {
		return Math.min(Math.max(value, min), max);
	}

	public static float quickExp(float a, int n) {
		if (n == 1)
			return a;
		if (n % 2 == 0) {
			float transition = quickExp(a, n/2);
			return transition*transition;
		}
		float transition = quickExp(a, (n - 1)/2);
		return transition*transition*a;
	}

	public static void quickSort(float[] array, int fst, int lst) {
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
					float tmp = array[i];
					array[i] = array[j];
					array[j] = tmp;
				}
			}
			
			float tmp = array[pivot];
			array[pivot] = array[j];
			array[j] = tmp;
			quickSort(array, fst, j - 1);
			quickSort(array, j + 1, lst);
		}
	}
}
