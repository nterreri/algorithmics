package uk.ac.ucl.myuclaccountname.LUTM2F;

public class LinearLUTM2FTest {
    public static void main(String[] args) {
	try {
	    LinearLUTM2F myLUT = new LinearLUTM2F();
	    
	    myLUT.insert("Priscilla", new Integer(41));
	    myLUT.insert("Travis", new Integer(34));
	    myLUT.insert("Samuel", new Integer(28));
	    myLUT.insert("Helena", new Integer(39));
	    myLUT.insert("Andrew", new Integer(14));
	    myLUT.insert("Kay", new Integer(24));
	    myLUT.insert("John", new Integer(67));	    
	    System.out.println(myLUT);

	    System.out.println(myLUT.retrieve("Helena"));
	    System.out.println(myLUT);
	    
	    System.out.println(myLUT.retrieve("John"));
	    System.out.println(myLUT);
	    
	    System.out.println(myLUT.retrieve("Travis"));
	    System.out.println(myLUT);

	    myLUT.update("Samuel", new Integer(29));
	    System.out.println(myLUT);

	    myLUT.remove("Andrew");
	    System.out.println(myLUT);
	
	} catch (Exception e) {
	    System.out.println(e);
	}
    }
}
