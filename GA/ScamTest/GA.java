package ScamTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class GA {
    //Parameters
    int popSize; //population size
    int seed; //Random seed
    int maxGeneration; //Max number of iterations
    static String fileIn;
    double crossoverRate; //Percentage of selected population that will produce offsprings
    double mutationRate; //Percentage of selected population that will mutate
    int k; //for tournament selection
    String fileOut = "ScamTest/output/out_"+ LocalDateTime.now() +".txt";
    boolean crossoverType;//True: order crossover, False: uniform order crossover
    int eliteN; //elite indivisuals with best chromosomes are guaranteed to pass selection
    ArrayList<Double> avgFitnessTotal, bestFitnessTotal; // for statistical analysis
    
    //Declarations
    static Random random = new Random();
    static char[][] shreddedDocument;
    Population currentPop, initPop, selectPop, newPop;

    public GA(String paramFile){
        //Set parameters
        readParameters(paramFile);
        random.setSeed(seed);
        //Read input
        shreddedDocument = FitnessCalculator.getShreddedDocument(fileIn); 
        //Initialize population
        initPop = new Population(popSize);
        currentPop = initPop;
        avgFitnessTotal = new ArrayList<Double>();
        bestFitnessTotal = new ArrayList<Double>();
        //Create output file
        File file = new File(fileOut); 
        try{
            if(file.createNewFile()){
                FileWriter writer = new FileWriter(file);
                //Output per run
                writeParametersOutput(writer);
                //Generate populations
                for (int generation = 0; generation < maxGeneration+1; generation++){
                    //Evaluate population
                    currentPop.evaluate();
                    //Output per generation
                    writeGenerationalOutput(writer);
                    //Selection for new population
                    selectPop = new Population(0);
                    //Apply elitism 
                    elitism();
                    //Selection: Tournament
                    tournament();
                    //Crossover
                    ArrayList<Individual> children = new ArrayList<>();
                    if(crossoverType)children = orderCrossover();
                    else children = uniOrderCrossover();
                    newPop = new Population(0);
                    for (Individual child : children) {
                        newPop.addIndividual(child);
                    }
                    //Mutation
                    if(mutationRate>0)mutation();
                    //Move to the next generation
                    currentPop = newPop;
                }
                currentPop.evaluate();
                //Output per run
                writeFinalOutput(writer);
                writer.close();
            }else throw new IOException("Output file cannot be created");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    //Reads and sets the parameters for GA from the commanline input provided
    public void readParameters(String paramF) {
        try{
            File pf = new File(paramF);
            Scanner scan  = new Scanner(pf);
            popSize = scan.nextInt();
            scan.nextLine();
            seed = scan.nextInt();
            scan.nextLine();
            maxGeneration = scan.nextInt();
            scan.nextLine();
            fileIn = scan.nextLine();
            crossoverRate = scan.nextDouble();
            scan.nextLine();
            mutationRate = scan.nextDouble();
            scan.nextLine();
            k = scan.nextInt();
            scan.nextLine();
            if(scan.nextLine().contains("O"))crossoverType=true;
            else crossoverType=false;
            eliteN = scan.nextInt();
            scan.close();
        }catch(Exception e){
            System.out.println("Error getting params: "+"\n");
            e.printStackTrace();
        }
    }

    //Writes all the parameters used for the run of GA
    public void writeParametersOutput(FileWriter writer) throws IOException{
        writer.write("Random seed: " + seed + "\n");
        writer.write("Population size: " + popSize+"\n");
        writer.write("Maximum generation size: " + maxGeneration + "\n");
        writer.write("Crossover rate: " + crossoverRate + "\n");
        writer.write("Mutation rate: " + mutationRate + "\n");
        writer.write("Tournament: k = " + k + "\n");
        writer.write("Input file: " + fileIn + "\n");
        writer.write("Elitism members: n = " + eliteN + "\n");
        if(crossoverType)writer.write("Crossover type: Order Crossover \n\n");
        else writer.write("Crossover type: Uniform Order Crossover \n\n");
    }

    //Writes the ouput created per generation
    public void writeGenerationalOutput(FileWriter writer) throws IOException{
        double totalF = 0, avgF, bestF = Double.MAX_VALUE;
        for (Individual i : currentPop.getIndividuals()) {
            totalF+=i.getFitness();
            if(bestF>i.getFitness())bestF=i.getFitness();
        }
        avgF = totalF/currentPop.size();
        avgFitnessTotal.add(avgF);
        bestFitnessTotal.add(bestF);
        writer.write("Best fitness value: " +bestF+ "\n");
        writer.write("Average population fitness value: " +avgF+ "\n\n");
    }
    //Writes the final ouput of the best generated solution
    public void writeFinalOutput(FileWriter writer) throws IOException{
        Individual solution;
        solution = currentPop.getIndividual(0);
            for (int i = 1; i < currentPop.size()-1; i++){
                if (solution.getFitness() > currentPop.getIndividual(i).getFitness())
                    solution = currentPop.getIndividual(i);
            }
        writer.write("Best fitness value: " +solution.getFitness()+ "\n");
        writer.write("Solution chromosome: " +solution.getChromosome()+ "\n");
        //Copied from function prettyprint in FitnessCalculator
        //Adapted to write to the file instead of console
        char[][] unshredded = FitnessCalculator.unshred(shreddedDocument, solution.getChromosomeArray());
        for(int i = 0; i < unshredded[0].length; i++) {
            for(int j = 0; j < unshredded.length; j++) {
              writer.write(unshredded[j][i]);
            }
            writer.write("\n");
          }
          writer.write("\n");
        //print all the average fitness values and best fitness values for statistical analysis
        writer.write("--------average fitness values--------");
        for (double avgF : avgFitnessTotal) {
            writer.write(avgF+"\n");
        }
        writer.write("--------best fitness values--------");
        for (double bestF : bestFitnessTotal) {
            writer.write(bestF+"\n");
        }
    }

    //Selects sub-population according to tournament algorithm
    public void tournament(){
        Individual picked, top;
        while(selectPop.size()<popSize){
            Population selection = new Population(0);
            //Randomly select k individuals
            for(int i=0;i<k;i++){
                do{
                    picked = currentPop.getIndividual(random.nextInt(currentPop.size()));
                }while(selection.getIndividuals().contains(picked));
                selection.addIndividual(picked);
            }
            //Select best fitted one
            top = selection.getIndividual(0);
            for (int i = 1; i < k; i++){
                if (top.getFitness() > selection.getIndividual(i).getFitness())
                    top = selection.getIndividual(i);
            }
            //Add to new population
            selectPop.addIndividual(top);
        }
    }

    //Select the best n fitted individuals from current population to garantee a place in new generation 
    public void elitism(){
        Population p = new Population(0);
        for(int j = 0; j < popSize; j++){
            p.addIndividual(currentPop.getIndividual(j));
        }
        for(int j = 0; j < eliteN; j++) {
            Individual top;
            top = p.getIndividuals().get(0);
                for (int i = 1; i < p.getIndividuals().size()-1; i++){
                    if (top.getFitness() > p.getIndividuals().get(i).getFitness())
                        top = p.getIndividuals().get(i);
                }
            selectPop.addIndividual(top);
            p.getIndividuals().remove(top);
        }
    }
    //Returns an array of new individuals generated from the unifrom order crossover
    public ArrayList<Individual> uniOrderCrossover(){
        ArrayList<Individual> offsprings = new ArrayList<Individual>();
        ArrayList<Integer> child1Chromosome, child2Chromosome, mask;
        Individual parent1, parent2, child1, child2;
        for(int i = 0; i < selectPop.size(); i+=2){
            //apply crossover rate: chance of parents producing offsrings
            if(crossoverRate<1 && random.nextDouble()>crossoverRate){
                offsprings.add(selectPop.getIndividual(i));//if parents do not produce offsprings, they pass to new generation
                offsprings.add(selectPop.getIndividual(i+1));
                continue;
            }
            //Pick 2 parents 
            parent1 = selectPop.getIndividual(i);
            parent2 = selectPop.getIndividual(i+1);
            //Generate random mask:
            mask = new ArrayList<>();
            for(int n = 0; n<15; n++){
                mask.add(random.nextInt(2));
            }
            //Copy set of genes according to mask
            child1Chromosome = new ArrayList<>(Collections.nCopies(15, -1));
            child2Chromosome = new ArrayList<>(Collections.nCopies(15, -1));
            for (int j = 0; j < 15; j++){
                if(mask.get(j)==1){
                    child1Chromosome.set(j, parent1.getChromosome().get(j));
                    child2Chromosome.set(j, parent2.getChromosome().get(j));
                }
            }
            //Fill the rest of the genes in order from parent 2 (and parent 1 for child 1)
            int j1 = 0, j2 = 0;
            for(int j = 0; j < 15; j++){
                if(mask.get(j)==0){
                    while(child1Chromosome.contains(parent2.getChromosome().get(j2))){
                        j2++;
                    }
                    while(child2Chromosome.contains(parent1.getChromosome().get(j1))){
                        j1++;
                    }
                    child1Chromosome.set(j, parent2.getChromosome().get(j2));
                    child2Chromosome.set(j, parent1.getChromosome().get(j1));
                    j2++;
                    j1++;
                }
            }
            //Move children to next generation
            child1 = new Individual(child1Chromosome);
            child2 = new Individual(child2Chromosome);
            offsprings.add(child1);
            offsprings.add(child2);
        }
        return offsprings;
    }

    //Returns an array of new individuals generated from the order crossover
    public ArrayList<Individual> orderCrossover(){
        ArrayList<Individual> offsprings = new ArrayList<Individual>();
        ArrayList<Integer> child1Chromosome, child2Chromosome;
        Individual parent1, parent2, child1, child2;
        for(int i = 0; i < selectPop.size(); i+=2){
            //apply crossover rate: chance of parents producing offsrings
            if(crossoverRate<1 && random.nextDouble()>crossoverRate){
                offsprings.add(selectPop.getIndividual(i));//if parents do not produce offsprings, they pass to new generation
                offsprings.add(selectPop.getIndividual(i+1));
                continue;
            }
            //Pick 2 parents 
            parent1 = selectPop.getIndividual(i);
            parent2 = selectPop.getIndividual(i+1);
            //Pick random set of 8 genes from parent 1 for child 1 (and parent 2 for child 2)
            int startOrder = random.nextInt(6), endOrder = startOrder+8;
            child1Chromosome = new ArrayList<>(Collections.nCopies(15, -1));
            child2Chromosome = new ArrayList<>(Collections.nCopies(15, -1));
            for (int j = startOrder; j < endOrder; j++){
                child1Chromosome.set(j, parent1.getChromosome().get(j));
                child2Chromosome.set(j, parent2.getChromosome().get(j));
            }
            //Fill the rest of the 7 genes in order from parent 2 (and parent 1 for child 1)
            int jc, j1 = endOrder, j2 = endOrder;//counts for array (0-14)
            for(int j = endOrder; j < 15+startOrder; j++){
                jc = j;
                if(jc>14)jc-=15;//cycle through all genes
                if(j1>14)j1-=15;
                if(j2>14)j2-=15;
                while(child1Chromosome.contains(parent2.getChromosome().get(j2))){
                    j2++;
                    if(j2>14)j2-=15;
                }
                while(child2Chromosome.contains(parent1.getChromosome().get(j1))){
                    j1++;
                    if(j1>14)j1-=15;
                }
                child1Chromosome.set(jc, parent2.getChromosome().get(j2));
                child2Chromosome.set(jc, parent1.getChromosome().get(j1));
                j2++;
                j1++;
            }
            //Move children to next generation
            child1 = new Individual(child1Chromosome);
            child2 = new Individual(child2Chromosome);
            offsprings.add(child1);
            offsprings.add(child2);
        }
        return offsprings;
    }

    //Mutates DNA of few individuals accrording to the  mutation rate 
    public void mutation(){
        int swap1, swap2, gene1;
        for(int i = 0; i < newPop.size(); i++){
            //apply mutation rate
            if(random.nextDouble()>mutationRate){
                continue;
            }
            //find 2 different gene positions
            do{
                swap1 = random.nextInt(15);
                swap2 = random.nextInt(15);
            }while(swap1 == swap2);
            //swap genes
            gene1 = newPop.getIndividual(i).getChromosome().get(swap1);
            newPop.getIndividual(i).getChromosome().set(swap1, newPop.getIndividual(i).getChromosome().get(swap2));
            newPop.getIndividual(i).getChromosome().set(swap2, gene1);
        }
    }

    public static void main(String[] args){
        for(int i = 0; i< args.length; i++) {
            new GA(args[i]); 
        }
        // new GA("ScamTest/input/example.txt"); //This can be used for testing in IDE
    }
}
