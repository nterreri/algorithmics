package uk.ac.ucl.ucabter.initial;

public class ComplexNumber {
	private double real;
	private double imaginary;
	
	public ComplexNumber(double r, double i) {
		real = r;
		imaginary = i;
	}
	
	public double real() {
		return real;
	}
	
	public double imaginary() {
		return imaginary;
	}
	
	public ComplexNumber add(ComplexNumber c) {
		double newr = real + c.real;
		double newi = imaginary + c.imaginary;
		
		return new ComplexNumber(newr, newi);
	}
	
	public ComplexNumber multiply(ComplexNumber c) {
		double newr = real*c.real - imaginary*c.imaginary;
		double newi = real*c.imaginary + imaginary*c.real;
		
		return new ComplexNumber(newr, newi);
	}
} 