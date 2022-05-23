package TP1;
import java.util.Scanner;
public class Gauss_jordan {
	private static final double EPSILON = 1e-8;
	 
    private int N = 0;
    private double[][] a;     // N-by-N+1 augmented matrix
 
    // Gauss-Jordan  avec pivot partiel
    public void Gauss_Jordan_Elimination(double[][] A, double[] b) 
    {
        N = b.length;
 
        // cree matrice augmentée
        a = new double[N][N+N+1];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = A[i][j];
 
        //seulement besoin si vous voulez  calculer l'inverse
        for (int i = 0; i < N; i++)
            a[i][N+i] = 1.0;
 
        for (int i = 0; i < N; i++) 
            a[i][N+N] = b[i];
 
        solve();
 
        assert check(A, b);
    }
 
    private void solve() 
    {
        // Gauss-Jordan 
        for (int p = 0; p < N; p++) 
        {
            int max = p;
            for (int i = p+1; i < N; i++) 
            {
                if (Math.abs(a[i][p]) > Math.abs(a[max][p])) 
                {
                    max = i;
                }
            }
 
            // échanger la ligne p avec la ligne max
            swap(p, max);
 
            // singular or nearly singular
            if (Math.abs(a[p][p]) <= EPSILON) 
            {
                continue;
                
            }
 
            // pivot
            pivot(p, p);
        }
        // show();
    }
 
    // permuté row1 et row2
    private void swap(int row1, int row2) 
    {
        double[] temp = a[row1];
        a[row1] = a[row2];
        a[row2] = temp;
    }
 
 
    // pivot dans lentré (p, q) utilisons Gauss-Jordan 
    private void pivot(int p, int q) 
    {   // tout sauf ligne p et colonne q
        for (int i = 0; i < N; i++) {
            double alpha = a[i][q] / a[p][q];
            for (int j = 0; j <= N+N; j++) 
            {
                if (i != p && j != q) a[i][j] -= alpha * a[p][j];
            }
        }
 
        // mettre à zéro la colonne q
        for (int i = 0; i < N; i++)
            if (i != p) a[i][q] = 0.0;
 
        // mettre à l'échelle la ligne p (ok pour aller de q+1 à N, mais faites-le pour la cohérence avec le pivot simplex)
        for (int j = 0; j <= N+N; j++)
            if (j != q) a[p][j] /= a[p][q];
        a[p][q] = 1.0;
    }
 
    // extraire la solution de Ax = b
    public double[] primal() 
    {
        double[] x = new double[N];
        for (int i = 0; i < N; i++) 
        {
            if (Math.abs(a[i][i]) > EPSILON)
                x[i] = a[i][N+N] / a[i][i];
            else if (Math.abs(a[i][N+N]) > EPSILON)
                return null;
        }
        return x;
    }
 
    // extraire la solution de yA = 0, yb != 0
    public double[] dual() 
    {
        double[] y = new double[N];
        for (int i = 0; i < N; i++) 
        {
            if ( (Math.abs(a[i][i]) <= EPSILON) && (Math.abs(a[i][N+N]) > EPSILON) ) 
            {
                for (int j = 0; j < N; j++)
                    y[j] = a[i][N+j];
                return y;
            }
        }
        return null;
    }
 
    
    public boolean isFeasible() 
    {
        return primal() != null;
    }
 
    // remplir le tableau
    private void show() 
    {
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < N; j++) 
            {
                System.out.print(" "+a[i][j]);
            }
            System.out.print("| ");
            for (int j = N; j < N+N; j++) 
            {
            	System.out.print(" "+a[i][j]);
            }
            System.out.print("| \n"+a[i][N+N]);
        }
        System.out.println();
    }
 
 
    // vérifier que Ax = b or yA = 0, yb != 0
    private boolean check(double[][] A, double[] b) 
    {
 
        // vérifier que Ax = b
        if (isFeasible()) 
        {
            double[] x = primal();
            for (int i = 0; i < N; i++) 
            {
                double sum = 0.0;
                for (int j = 0; j < N; j++) 
                {
                     sum += A[i][j] * x[j];
                }
                if (Math.abs(sum - b[i]) > EPSILON) 
                {
                	System.out.println("not feasible");
                	System.out.println(i+" = "+b[i]+", sum = "+sum+"\n");
                   return false;
                }
            }
            return true;
        }
 
        // ou que yA = 0, yb != 0
        else 
        {
            double[] y = dual();
            for (int j = 0; j < N; j++) 
            {
                double sum = 0.0;
                for (int i = 0; i < N; i++) 
                {
                     sum += A[i][j] * y[i];
                }
                if (Math.abs(sum) > EPSILON) 
                {
                    System.out.println("invalid certificate of infeasibility");
                    System.out.println("sum = "+sum+"\n");
                    return false;
                }
            }
            double sum = 0.0;
            for (int i = 0; i < N; i++) 
            {
                sum += y[i] * b[i];
            }
            if (Math.abs(sum) < EPSILON) 
            {
            	System.out.println("invalid certificate of infeasibility");
            	System.out.println("yb  = "+sum+"\n");
 
                return false;
            }
            return true;
        }
    }
 
 
    public static void test(double[][] A, double[] b) 
    {
        Gauss_jordan gaussian = new Gauss_jordan();
        if (gaussian.isFeasible()) 
        {
        	System.out.println("Solution to Ax = b");
            double[] x = gaussian.primal();
            for (int i = 0; i < x.length; i++) 
            {
            	System.out.println(" "+x[i]+"\n");
            }
        }
        else 
        {
        	System.out.println("Certificate of infeasibility");
            double[] y = gaussian.dual();
            for (int j = 0; j < y.length; j++) 
            {
            	System.out.println(" "+y[j]+"\n");
            }
        }
        System.out.println();
    }
 
    public static void main(String[] args) 
    {
 
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter the number of variables in the equations: ");
    	int n = input.nextInt();
        System.out.println("Enter the coefficients of each variable for each equations");
        System.out.println("ax + by + cz + ... = d");
        double [][]mat = new double[n][n];
        double []constants = new double[n];
        //input
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                mat[i][j] = input.nextDouble();
            }
            constants[i] = input.nextDouble();
        }
        test(mat, constants);          
    }
   


}
