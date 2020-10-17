import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

class NewtonFractal {
    /* A reference to the Newton-Raphson iterator object.*/
    private Newton iterator;
    
    /* The top-left corner of the square in the complex plane to examine.*/
    private Complex origin;
    
    /* The width of the square in the complex plane to examine.*/
    private double width;
    
    /* A list of roots of the polynomial.*/
    private ArrayList<Complex> roots;
    
    /* A two dimensional array holding the colours of the plot.*/
    private Color[][] colors;

    /* A flag indicating the type of plot to generate. If true, we choose 
    *darker colors if a particular root takes longer to converge.*/
    private boolean colorIterations;

    /* A standard Java object which allows us to store a simple image in memory. 
    *This will be set up by setupFractal -- you do not need to worry about it!*/
    private BufferedImage fractal;
    
    /* This object is another standard Java object which allows us to perform
     * basic graphical operations (drawing lines, rectangles, pixels, etc) on
     * the BufferedImage. This will be set up by setupFractal -- you do not
     * need to worry about it!*/
    private Graphics2D g2;

    /* Defines the width (in pixels) of the BufferedImage and hence the
     * resulting image.*/
    public static final int NUMPIXELS = 400;
    
 // Constructor function.
    
    /* Constructor function which initialises the instance variables above.
     * @param p       The polynomial to generate the fractal of.
     * @param origin  The top-left corner of the square to image.
     * @param width   The width of the square to image.*/
    public NewtonFractal(Polynomial p, Complex origin, double width) {
        iterator = new Newton(p);
	this.origin = origin;
	this.width = width;
	this.roots = new ArrayList<Complex>();
	setupFractal();
    }
    
 // Basic operations.

    /*Print out all of the roots found so far, which are contained in the
     * roots ArrayList.*/
    public void printRoots() {
        for(int i=0;i<roots.size();i++){
	   System.out.println(roots.get(i));
	}
    }
    
    /* Check to see if root is in the roots ArrayList (up to tolerance). If
     * the root is not found, then return -1. Otherwise return the index
     * inside this.roots where you found it.
     * @param root  Root to find in this.roots.*/
    public int findRoot(Complex root) {
        int i=0;
	while(i<roots.size() && roots.get(i).add(root.minus()).abs()>Newton.TOL){
           i++;
        }
	if(i<roots.size()){
	  return i;
	}else if(i>=roots.size()){
	  return -1;
	}
	return -1;
    }
    
    /* Convert from pixel indices (i,j) to the complex number 
     *(origin.real +i*dz, origin.imag - j*dz).
     * @param i  x-axis co-ordinate of the pixel located at (i,j)
     * @param j  y-axis co-ordinate of the pixel located at (i,j)*/
    public Complex pixelToComplex(int i, int j) {
        double dz = (width)/NUMPIXELS;
	Complex pix = new Complex(origin.getReal()+i*dz, origin.getImag()-j*dz);
	return pix;
    }
    
 // Fractal generating function.

    /*Generate the fractal image. See the colorIterations instance variable
     *for a better description of its purpose.*/
    public void createFractal(boolean colorIterations) {
	this.colorIterations = colorIterations;
	int rcolor = 0;
        for(int j=0;j<NUMPIXELS;j++){
	   for(int k=0;k<NUMPIXELS;k++){
		Complex pix = pixelToComplex(j,k);
		iterator.iterate(pix);
		if(iterator.getError()==0){
		Complex r = iterator.getRoot();
		if(findRoot(r)==-1){
		  roots.add(r);
		}
		rcolor = findRoot(r);
		colorPixel(j,k,rcolor,iterator.getNumIterations());
		}
	   }	
	}
    }
   
// Tester function.
    
    public static void main(String[] args) {
     //Example 1
        Complex[] coeff = new Complex[] { new Complex(-1.0,0.0), new Complex(), 
                                          new Complex(), new Complex(1.0,0.0) };
        Polynomial p    = new Polynomial(coeff);
        NewtonFractal f = new NewtonFractal(p, new Complex(-1.0,1.0), 2.0);
        
        f.createFractal(false);
        f.saveFractal("fractal-light.png");
        f.createFractal(true);
        f.saveFractal("fractal-dark.png");
	
     //Example 2
	Complex A = new Complex(2.5,6.9);
	Complex B = new Complex(3.2,4.7);
	Complex C = new Complex(3.2,7.5);
        Complex D = new Complex(9.3,5.0);       
	Complex E = new Complex(7.5,3.9);
	Complex[] coeff1={A,B,C,D,E};
	Polynomial P = new Polynomial(coeff1);
	NewtonFractal fractal = new NewtonFractal(P, new Complex(-3.4,4.3), 3.5);

	fractal.createFractal(false);
        fractal.saveFractal("Fractal_light.png");
        fractal.createFractal(true);
        fractal.saveFractal("Fractal_dark.png");

    }
        
    /* Sets up all the fractal image. Make sure that your constructor calls
     * this function!*/
    private void setupFractal()
    {
        int i, j;

        if (iterator.getF().degree() < 3 || iterator.getF().degree() > 5)
            throw new RuntimeException("Degree of polynomial must be between 3 and 5 inclusive!");

        this.colors       = new Color[5][Newton.MAXITER];
        this.colors[0][0] = Color.RED;
        this.colors[1][0] = Color.GREEN;
        this.colors[2][0] = Color.BLUE;
        this.colors[3][0] = Color.CYAN;
        this.colors[4][0] = Color.MAGENTA;
        
        for (i = 0; i < 5; i++) {
            float[] components = colors[i][0].getRGBComponents(null);
            float[] delta      = new float[3];
            
            for (j = 0; j < 3; j++)
                delta[j] = 0.8f*components[j]/Newton.MAXITER;
            
            for (j = 1; j < Newton.MAXITER; j++) {
                float[] tmp  = colors[i][j-1].getRGBComponents(null);
                colors[i][j] = new Color(tmp[0]-delta[0], tmp[1]-delta[1], 
                                         tmp[2]-delta[2]);
            }
        }
        
        fractal = new BufferedImage(NUMPIXELS, NUMPIXELS, BufferedImage.TYPE_INT_RGB);
        g2      = fractal.createGraphics();
    }
    
    /* Colors a pixel in the image.
     * @param i          x-axis co-ordinate of the pixel located at (i,j)
     * @param j          y-axis co-ordinate of the pixel located at (i,j)
     * @param rootColor  An integer between 0 and 4 inclusive indicating the
     *                   root number.
     * @param numIter    Number of iterations at this root.*/
    private void colorPixel(int i, int j, int rootColor, int numIter) 
    {
        if (colorIterations)
            g2.setColor(colors[rootColor][numIter-1]);
        else
            g2.setColor(colors[rootColor][0]);
        g2.fillRect(i,j,1,1);
    }

    /* Saves the fractal image to a file.
     * @param fileName  The filename to save the image as. Should end in .png.*/
    public void saveFractal(String fileName) {
        try {
            File outputfile = new File(fileName);
            ImageIO.write(fractal, "png", outputfile);
        } catch (IOException e) {
            System.out.println("I got an error trying to save! Maybe you're out of space?");
        }
    }
}
