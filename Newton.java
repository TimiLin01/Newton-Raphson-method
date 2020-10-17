/*
 * PROJECT II: Newton.java
 *
 * This file contains a template for the class Newton. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * In this class, you will create a basic Java object responsible for
 * performing the Newton-Raphson root finding method on a given polynomial
 * f(z) with complex co-efficients. The formulation outlines the method, as
 * well as the basic class structure, details of the instance variables and
 * how the class should operate.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

class Newton {
    /**
     * The maximum number of iterations that should be used when applying
     * Newton-Raphson. Ensure this is *small* (e.g. at most 50) otherwise your
     * program may appear to freeze!
     */
    public static final int MAXITER = 20;

    /**
     * The tolerance that should be used throughout this project. Note that
     * you should reference this variable and _not_ explicity write out
     * 1.0e-10 in your solution code. Other classes can access this tolerance
     * by using Newton.TOL.
     */
    public static final double TOL = 1.0e-10;

    /**
     * The polynomial we wish to apply the Newton-Raphson method to.
     */
    private Polynomial f;
    
    /**
     * The derivative of the the polynomial f.
     */
    private Polynomial fp;

    /**
     * A root of the polynomial f corresponding to the root found by the
     * iterate() function below.
     */
    private Complex root;
    
    /**
     * The number of iterations required to reach within TOL of the root.
     */
    private int numIterations;

    /**
     * An integer that signifies errors that may occur in the root finding
     * process.
     *
     * Possible values are:
     *   =  0: Nothing went wrong.
     *   = -1: Derivative went to zero during the algorithm.
     *   = -2: Reached MAXITER iterations.
     */
    private int err;
    
    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * Basic constructor. You should calculate and set fp in this method.
     *
     * @param p  The polynomial used for Newton-Raphson.
     */
    public Newton(Polynomial p) {
        f=p;
	fp = p.derivative();
    }

    // ========================================================
    // Accessor methods.
    // ========================================================
    
    /**
     * Returns the current value of the err instance variable.
     */
    public int getError() {
        return err;
    }

    /**
     * Returns the current value of the numIterations instance variable.
     */
    public int getNumIterations() { 
        return numIterations;
    }
    
    /**
     * Returns the current value of the root instance variable.
     */
    public Complex getRoot() {
        return root;
    }

    /**
     * Returns the polynomial associated with this object.
     */
    public Polynomial getF() {
        return f;
    }

    /**
     * Returns the derivative of the polynomial associated with this object.
     */
    public Polynomial getFp() {
        return fp;
    }

    // ========================================================
    // Newton-Rapshon method
    // ========================================================
    
    /**
     * Given a complex number z0, apply Newton-Raphson to the polynomial f in
     * order to find a root within tolerance TOL.
     *
     * One of three things may occur:
     *
     *   - The root is found, in which case, set root to the end result of the
     *     algorithm, numIterations to the number of iterations required to
     *     reach it and err to 0.
     *   - At some point the derivative of f becomes zero. In this case, set err 
     *     to -1 and return.
     *   - After MAXITER iterations the algorithm has not converged. In this 
     *     case set err to -2 and return.
     *
     * @param z0  The initial starting point for the algorithm.
     */
    public void iterate(Complex z0) {
        int it=0;
	Complex[] z = new Complex[MAXITER+1];
	z[0]=z0;
	while(it<MAXITER && ((f.evaluate(z[it])).abs())>TOL && ((fp.evaluate(z[it])).abs())>TOL){
	    z[it+1]=z[it].add(((f.evaluate(z[it])).divide((fp.evaluate(z[it])))).minus());	
	    it++;
	}
	if(it<MAXITER && ((fp.evaluate(z[it])).abs())>TOL){  
	  root=z[it];
	  numIterations=it;
	  err=0;
	}else if(it<MAXITER && ((f.evaluate(z[it])).abs())>TOL){
	  err=-1;
	}else if(it>=MAXITER){
	  err=-2;
	}
    }
    
    // ========================================================
    // Tester function.
    // ========================================================
    
    public static void main(String[] args) {
        // Basic tester: find a root of f(z) = z^3-1 from the starting point
        // z_0 = 1+i.
        Complex[] coeff = new Complex[] { new Complex(-1.0,0.0), new Complex(), 
                                          new Complex(), new Complex(1.0,0.0) };
        Polynomial p    = new Polynomial(coeff);
        Newton     n    = new Newton(p);
        System.out.println("--For polynomial p--");
        n.iterate(new Complex(1.0, 1.0));
	System.out.println("The polynomial p is:"+(n.getF()).toString());
	System.out.println("The derivative of p is:"+(n.getFp()).toString());
        System.out.println("The root for polynomial p is: "+n.root);
	System.out.println("The number of iterations is: "+n.getNumIterations());
	System.out.println("Err is: "+n.getError());	

	System.out.println("");	

	Complex A = new Complex(1.0,3.9);
	Complex B = new Complex(2.2,4.7);
	Complex C = new Complex(6.2,7.5);
        Complex D = new Complex(0.0,0.0);       
	Complex E = new Complex(0.0,3.9);
	Complex[] coeff1={A,B,C,D,E};
	Polynomial P1 = new Polynomial(coeff1);
	System.out.println("--For polynomial P1---");
	Newton n1= new Newton(P1);
	n1.iterate(new Complex(8.3, 7.2));
	System.out.println("The polynomial P1 is:"+(n1.getF()).toString());
        System.out.println("The derivative of P1 is:"+(n1.getFp()).toString());
        System.out.println("The root for polynomial P1 is: "+n1.getRoot());
	System.out.println("The number of iterations is: "+n1.getNumIterations());
	System.out.println("Err is: "+n1.getError());

    }
}
