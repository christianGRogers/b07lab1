public class Driver {
	public static void printPolynomial(Polynomial toPrint){
		int i = 0;
		for(; i< toPrint.coefficients.length-1; i++){
			System.out.print("(");
			System.out.print(toPrint.coefficients[i]);
			System.out.print("x^");
			System.out.print(toPrint.exponents[i]);
			System.out.print(")");
			System.out.print("+");
		}
		System.out.print("(");
		System.out.print(toPrint.coefficients[i]);
		System.out.print("x^");
		System.out.print(toPrint.exponents[i]);
		System.out.print(")");
		System.out.print("\n");
	}
	public static void main(String [] args) {
		double[] c1 = {1,2,3,4,5};
		int[] e1 = {0,3,1,2,4};
		double[] c2 = {1,2,3,4,5,6,7};
		int[] e2 = {1,2,4,0,3,6,3};
		Polynomial p1 = new Polynomial(c1, e1);
		printPolynomial(p1);
		Polynomial p2 = new Polynomial(c2, e2);
		printPolynomial(p2);
		Polynomial added = p1.add(p2);
		printPolynomial(added);
	}
}