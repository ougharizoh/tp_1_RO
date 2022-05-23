package TP1;
import java.util.ArrayList;

public class Dual_simplex {
	double[][] A2; //matrice des coef des contraintes
    ArrayList<Double> C2 = new ArrayList<Double>();//list des coef du fonction objectif
    ;
    ArrayList<Double> B2 = new ArrayList<Double>();//list de valuers du B (partie doit des contraintes)
    ;
    int s = 1;
    String c;

    public void Dual_Simplex(String maxMin, double[][] A, ArrayList<Double> C, ArrayList<Double> B, String[] comparaison, int contraintNbr, int nbrVariableHorsBase) {

        A2 = new double[nbrVariableHorsBase][contraintNbr];
        if (maxMin.equals("Max")) {
           
            maxMin = "Min";
            c = "<=";
           
            for (int i = 0; i < nbrVariableHorsBase; i++) {

                B2.add(C.get(i));
            }

            for (int i = 0; i < contraintNbr; i++) {
               
                if (comparaison[i].equals(c)) {
                    s = 1;
                } else {
                    comparaison[i] = c;
                    s = -1;

                }
                C2.add(B.get(i) * s);

                for (int j = 0; j < nbrVariableHorsBase; j++) {
                    A2[j][i] = A[i][j] * s;
                }
            }
            for(int i = 0; i < nbrVariableHorsBase; i++){
                comparaison[i] = c;
            }
            
            int t = contraintNbr;
            contraintNbr = nbrVariableHorsBase;
            nbrVariableHorsBase = t;

            new Simplex("Min", A2, C2, B2, comparaison, contraintNbr, nbrVariableHorsBase);
        } else {
            c = ">=";
           
        }

    }

}
