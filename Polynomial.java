import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class Polynomial{
	double[] coefficients;
	int[] exponents;

	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
		exponents = new int[1];
		coefficients[0] = 0;
	}
	int getNonZeroCoeff(double[] coefficients){
		int count = 0;
		for(int i = 0; i< coefficients.length; i++){
			if(coefficients[i] != 0){
				count++;
			}
		}
		return count;
	}

	public void sortByExpo(){
		int i = 0;
		int j = 0;
		int swap1;
		double swap2;
		while(i < exponents.length){
			while(j < exponents.length){
				if(exponents[i] > exponents[j]){
					swap1 = exponents[i];
					exponents[i] = exponents[j];
					exponents[j] = swap1;
					swap2 = coefficients[i];
					coefficients[i] = coefficients[j];
					coefficients[j] = swap2;
				}
				j++;
			}
			i++;
			j = i;
		}
	}

	public Polynomial(double[] coefficients, int[] exponents) {
		if(coefficients != null){
			int sizeOfPoly = getNonZeroCoeff(coefficients);
			this.coefficients = new double[sizeOfPoly];
			this.exponents = new int[sizeOfPoly];
			for(int i = 0; i< sizeOfPoly; i++){
				if(coefficients[i] != 0){
					this.coefficients[i] = coefficients[i];
					this.exponents[i] = exponents[i];
				}
			}
			sortByExpo();
		}
	} 
	
	public Polynomial add(Polynomial toadd) {
		if(toadd == null){return null;}
		int i = 0;
		int j = 0;
		int s = 0;
		//we will have to shorten the size when we copy it over anyways as we initalize to the maximum possible size
		double[] sumCoeff = new double[coefficients.length + toadd.coefficients.length];
		int[] sumExp = new int[coefficients.length + toadd.coefficients.length];
		//set up the refference to make it cleaner (always have c1 be the smaller array)
		Polynomial c1;
		Polynomial c2;
		if(coefficients.length < toadd.coefficients.length){
			c1 = this;
			c2 = toadd;
		}
		else{
			c2 = this;
			c1 = toadd;
		}
		while(i < c1.coefficients.length){
			if(c1.exponents[i] == c2.exponents[j]){
				sumExp[s] = c1.exponents[i];
				sumCoeff[s] = c1.coefficients[i] + c2.coefficients[j];
				i++; j++; s++;
			}
			else if(c1.exponents[i] < c2.exponents[j]){
				sumExp[s] = c1.exponents[i];
				sumCoeff[s] = c1.coefficients[i];
				i++; s++;
			}
			else if(c1.exponents[i] > c2.exponents[j]){
				sumExp[s] = c2.exponents[j];
				sumCoeff[s] = c2.coefficients[j];
				j++; s++;
			}
		}
		while(j < c2.coefficients.length){
			sumExp[s] = c2.exponents[j];
			sumCoeff[s] = c2.coefficients[j];
			j++; s++;
		}
		//copy back to shortened aray
		int termsOfSum = getNonZeroCoeff(sumCoeff);
		Polynomial ret = new Polynomial();
		ret.exponents = new int[termsOfSum];
		ret.coefficients = new double[termsOfSum];
		for(i = 0; i < termsOfSum;){
			if(sumCoeff[i] != 0){
				ret.coefficients[i] = sumCoeff[i];
				ret.exponents[i] = sumExp[i];
				i++;
			}
		}
		return ret;
	}
	
	public double evaluate(double x) {
		double result  = 0;
		for(int i = 0; i<coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, exponents[i]);
		}
		return result;
	}

	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}

	public Polynomial multiply(Polynomial toMult){
		Polynomial tempMult;
		Polynomial product = new Polynomial();
		for(int i = 0; i < toMult.coefficients.length; i++){
			tempMult = new Polynomial(toMult.coefficients, toMult.exponents);
			for(int j = 0;  j < coefficients.length; j++){
				tempMult.coefficients[j] *= coefficients[i];
				tempMult.exponents[j] += exponents[i];
			}
			product.add(tempMult);
		}
		return product;
	}

	public Polynomial(File readFile){
		Scanner input;
		try{
			input = new Scanner(readFile);
			String inputString  = input.nextLine();
			System.out.println(inputString);
		}
		catch(FileNotFoundException error){
			System.out.println("File not found");
			error.printStackTrace();

		}
	}

	public String formatEqn(){
		String output = "";
		int i = 0;
		output += String.valueOf(coefficients[i]);
		output += "x";
		output += String.valueOf(exponents[i]);
		for(; i< coefficients.length; i++){
			if(coefficients[i] > 0){
				output += "+";
			}
			output += String.valueOf(coefficients[i]);
			output += "x";
			output += String.valueOf(exponents[i]);
		}
		return output;
	}

	public void saveToFile(String fileName){
		try {
      		FileWriter output = new FileWriter(fileName);
      		output.write(formatEqn());
      		output.close();
    	} catch (IOException error) {
      		System.out.println("File not found");
      		error.printStackTrace();
    }
	}

}