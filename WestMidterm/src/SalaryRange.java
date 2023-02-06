public class SalaryRange {
	private double lowerBound;
	private double upperBound;
	
	public SalaryRange(double lowerBound, double upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public double getLowerBound() {
		return lowerBound;
	}
	private void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	public double getUpperBound() {
		return upperBound;
	}
	
	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}
}
