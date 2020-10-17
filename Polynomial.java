/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        this();
	Complex[] filter=coeff;
	Complex zero=new Complex();
	int i=coeff.length-1;
	while(filter[i]==zero && i>=0){
	     i--;
	}
	if(i<0){
	  
	}else if(i>=0){
	  this.coeff = new Complex[i+1];
	  for(int j=0;j<=i;j++){
	      this.coeff[j]=filter[j];
	  }
	}
    }
    
    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
	this.coeff = new Complex[1];
        coeff[0]=new Complex();
	
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Create a string representation of the polynomial.
     *
     * For example: (1.0+1.0i)+(1.0+2.0i)X+(1.0+3.0i)X^2
     */
    public String toString() {
	String result="";
	for(int i=0;i<coeff.length;i++){
	  if((coeff[i].getReal() !=0.0)||(coeff[i].getImag()!=0.0)){
	   if(i==0 && coeff.length!=1){
		result+="("+coeff[i].toString()+") + ";
	   }else if(i==0 && coeff.length==1){
		result+="("+coeff[i].toString()+")";
	   }else if(i==1 && coeff.length!=2){
		result+="("+coeff[i].toString()+")X + ";
	   }else if(i==1 && coeff.length==2){
                result+="("+coeff[i].toString()+")X";
           }else if(i>0 && i<coeff.length-1){
	   	result+="("+coeff[i].toString()+")X^"+i+" + ";
	   }else if(i>1 && i==coeff.length-1){
		result+="("+coeff[i].toString()+")X^"+i;
	   }
	 }
	}
	return result;
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        return coeff.length-1;
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        Complex P = new Complex();
	for(int i=coeff.length-1;i>=0;i--){
	   P = z.multiply(P.add(coeff[i]));   
	}
	return P;
    }
    
    /**
     * Calculate and returns the derivative of this polynomial.
     *
     * @return The derivative of this polynomial.
     */
    public Polynomial derivative() {
        Complex[] der = new Complex[coeff.length-1];
	for(int i=1;i<coeff.length;i++){
	    der[i-1]=coeff[i].multiply(i);
	}
	return new Polynomial(der);
    }
    
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        Complex A = new Complex(1.0,3.9);
	Complex B = new Complex(2.2,4.7);
	Complex C = new Complex(6.2,7.5);
        Complex D = new Complex(0.0,0.0);       
	Complex E = new Complex(0.0,3.9);
	Complex[] coeff={A,B,C,D,E};
	Polynomial P = new Polynomial(coeff);

	Complex Z = new Complex(6.6,7.2);
	System.out.println("The polynomial P is: "+P.toString());
	System.out.println("The degree of polynomial P is: "+P.degree());

	System.out.println("After substituting Z into the polynomial P, we get: "+P.evaluate(Z));
	System.out.println("After substituting A into the polynomial P, we get: "+P.evaluate(A));
	System.out.println("After substituting B into the polynomial P, we get: "+P.evaluate(B));
        System.out.println("After substituting C into the polynomial P, we get: "+P.evaluate(C));
	System.out.println("After substituting D into the polynomial P, we get: "+P.evaluate(D));
        System.out.println("After substituting E into the polynomial P, we get: "+P.evaluate(E));

	Polynomial P1 = new Polynomial();
	P1 = P.derivative();
	System.out.println("The derivative of polynomial P is: "+P1.toString());
	System.out.println("The degree of P1 is: "+P1.degree());
	
	System.out.println("After substituting Z into P1: "+P1.evaluate(Z));
        System.out.println("After substituting A into P1: "+P1.evaluate(A));
        System.out.println("After substituting B into P1: "+P1.evaluate(B));
        System.out.println("After substituting C into P1: "+P1.evaluate(C));
        System.out.println("After substituting D into P1: "+P1.evaluate(D));
        System.out.println("After substituting E into P1: "+P1.evaluate(E));

	
    }
}
