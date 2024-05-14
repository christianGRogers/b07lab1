public class Polynomial{
	double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
	}
	
	public Polynomial(double[] coefficients) {
		this.coefficients = new double[coefficients.length];
		for(int i = 0; i< coefficients.length; i++) {
			this.coefficients[i] = coefficients[i]; 
		}
	}
	
	public Polynomial add(Polynomial toadd) {
		int maxPolyDeg = Math.max(toadd.coefficients.length, this.coefficients.length);
		int minPolyDeg = Math.min(toadd.coefficients.length, this.coefficients.length);
		if(toadd.coefficients.length == maxPolyDeg) {
			for(int i = 0; i<minPolyDeg; i++) {
				toadd.coefficients[i] += this.coefficients[i];
			}
			return toadd;
		}
		for(int i = 0; i<minPolyDeg; i++) {
			this.coefficients[i] += toadd.coefficients[i];
		}
		return this;
	}
	
	public double evaluate(double x) {
		double result  = 0;
		for(int i = 1; i<coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, i);
		}
		result += coefficients[0];
		return result;
	}
	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}
}