package ScamTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

//This class imitates the population (collection) of individuals
public class Population {
    private ArrayList<Individual> individuals;
    static Random random = GA.random;

    /*
     * int size 
     * size > 0: generate random initial poplation
     * size = 0: new population is created from selection. Individuals are added later 
     */
    public Population(int size) {
        individuals = new ArrayList<Individual>();
        int gene, geneCap = 0;
        ArrayList<Integer> capitals = new ArrayList<Integer>();
        capitals = findCaps();
        for (int i = 0; i < size; i++){
            ArrayList<Integer> chromosome = new ArrayList<Integer>();
            for(int j = 0; j < 15; j++){
                if(j==0 && !capitals.isEmpty()){
                    gene = capitals.get(geneCap);
                    geneCap++;
                    if(geneCap >= capitals.size())geneCap = 0;
                }else{
                    do{
                        gene = random.nextInt(15);
                    }while(chromosome.contains(gene));
                }
                chromosome.add(gene);
            }
            individuals.add(new Individual(chromosome));
        }
    }

    /*
     * Returns array of indedxes of the Capital letters in the ffirst line of the input file
     */
    public ArrayList<Integer> findCaps(){
        ArrayList<Integer> caps = new ArrayList<Integer>();
        try{
            File f = new File(GA.fileIn);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if(Character.isUpperCase(line.toCharArray()[0]))caps.add(i);
                i++;
            }
            fr.close();
            br.close();
        }catch(Exception e){
            System.out.println("Error getting caps: "+"\n");
            e.printStackTrace();
        }
        return caps;
    }

    //Add an individual to population (Selection or elitism)
    public void addIndividual(Individual i){
        individuals.add(i);
    }

    //Assign fitness value to each individual
    public void evaluate(){
        for (Individual individual : individuals) {
            individual.setFitness(FitnessCalculator.fitness(GA.shreddedDocument, individual.getChromosomeArray()));
        }
    }

    //return the number ofindividuals in population
    public int size(){
        return individuals.size();
    }

    //return the array of individuals
    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    //return specified individual
    public Individual getIndividual(int position) {
        return individuals.get(position);
    }
    
}
