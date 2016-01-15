package uk.ac.ucl.ucabter.initial;

public class ComplexNumberDriver {
	
	public static void main(String args[]){
		ComplexNumber c = new ComplexNumber(1.0, 1.0);
		
		System.out.println(c.real() + " + " + c.imaginary() + "i");
		
		ComplexNumber d = c.add(new ComplexNumber(2.0, 3.3));
		
		System.out.println(d.real() + " + " + d.imaginary() + "i");
		
		c = c.multiply(d);
		
		System.out.println(c.real() + " + " + c.imaginary() + "i");
	}
}
