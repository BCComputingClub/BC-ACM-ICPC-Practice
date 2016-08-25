
/**
 * 
 * @author Trevor Bostic
 * This class holds the necessary variables for the maximum sub array problem
 */
public class Tuple {

	public int maxSum;
	public int maxStartIndex;
	public int maxEndIndex;
	
	public Tuple(int maxSum, int maxStartIndex, int maxEndIndex)
	{
		this.maxSum = maxSum;
		this.maxStartIndex = maxStartIndex;
		this.maxEndIndex = maxEndIndex;
	}

	public int getMaxSum() {
		return maxSum;
	}

	public void setMaxSum(int maxSum) {
		this.maxSum = maxSum;
	}

	public int getMaxStartIndex() {
		return maxStartIndex;
	}

	public void setMaxStartIndex(int maxStartIndex) {
		this.maxStartIndex = maxStartIndex;
	}

	public int getMaxEndIndex() {
		return maxEndIndex;
	}

	public void setMaxEndIndex(int maxEndIndex) {
		this.maxEndIndex = maxEndIndex;
	}

	
}
