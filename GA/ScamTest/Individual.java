package ScamTest;

import java.util.ArrayList;

//This class simmulates individual in the population. It's chromosome is an array of integer of length 15
//The chromosome represents the solution to the problem
//In our case it isthe sequence to restore shredded text to the original
public class Individual {
    private double fitness; 
    private ArrayList<Integer> chromosome = new ArrayList<>();

    /*
     * ArrayList<Integer> chromosome: DNA of the individual represents the solution
     */
    public Individual(ArrayList<Integer> chromosome) {
        this.chromosome = chromosome;
    }

    //returns ArrayList<Integer> representing chromosome
    public ArrayList<Integer> getChromosome() {
        return chromosome;
    }
    
    //Convert chromosome ArrayList to primitive array
    public int[] getChromosomeArray() {
        int[] chromosomeArr = new int[chromosome.size()];
        int i = 0;
        for (Integer integer : chromosome) {
            chromosomeArr[i] = integer;
            i++;
        }
        return chromosomeArr;
    }

    //return fitness value
    public double getFitness() {
        return fitness;
    }

    //change fitness value
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

}
