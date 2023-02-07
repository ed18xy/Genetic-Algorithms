package ScamTest;

import java.io.File;
import java.io.FileWriter;

public class test {
    //To avoid creating all these files by hand, this class creates and prints the names of all files
    //Refer to descriptionOfOutput.txt in input folder for more information
    public static void main(String[] args) throws Exception{
        String n1 = "ScamTest/document1-shredded.txt";
        String n2 = "ScamTest/document2-shredded.txt";
        String n3 = "ScamTest/document3-shredded.txt";
        for (int o = 0; o < 2; o++){//crossover type
            for(int j = 0; j < 5; j++){//crossover+mutation
                for(int i = 0; i < 3; i++) {//input files
                    for (int seed = 0; seed < 5; seed++) {//random seeds
                        String name = "ScamTest/input/"+o+"_"+j+"_"+i+"_"+seed+".txt";
                        System.out.print(name+" ");
                        File file = new File(name); 
                        if(file.createNewFile()){
                            FileWriter writer = new FileWriter(file);
                            writer.write(200 + "\n");
                            writer.write(seed+"\n");
                            writer.write(30+ "\n");
                            if(i==0)writer.write(n1+ "\n");
                            if(i==1)writer.write(n2+ "\n");
                            if(i==2)writer.write(n3+ "\n");
                            if(j==0)writer.write(1+ "\n"+0+ "\n");//a
                            if(j==1)writer.write(1+ "\n"+0.1+ "\n");//b
                            if(j==2)writer.write(0.9+ "\n"+0+ "\n");//c
                            if(j==3)writer.write(0.9+ "\n"+0.1+ "\n");//d
                            if(j==4)writer.write(0.95+ "\n"+0.15+ "\n");//e
                            writer.write(5+ "\n");
                            if(o==0)writer.write("O"+ "\n");
                            if(o==1)writer.write("U"+ "\n");
                            writer.write(2+ "\n");
                            writer.close();
                        }
                    }
                }
            }
        }
        
            
    }
}
/*
 *  ScamTest/input/0_0_0_0.txt ScamTest/input/0_0_0_1.txt ScamTest/input/0_0_0_2.txt ScamTest/input/0_0_0_3.txt ScamTest/input/0_0_0_4.txt ScamTest/input/0_0_1_0.txt ScamTest/input/0_0_1_1.txt ScamTest/input/0_0_1_2.txt ScamTest/input/0_0_1_3.txt ScamTest/input/0_0_1_4.txt ScamTest/input/0_0_2_0.txt ScamTest/input/0_0_2_1.txt ScamTest/input/0_0_2_2.txt ScamTest/input/0_0_2_3.txt ScamTest/input/0_0_2_4.txt ScamTest/input/0_1_0_0.txt ScamTest/input/0_1_0_1.txt ScamTest/input/0_1_0_2.txt ScamTest/input/0_1_0_3.txt ScamTest/input/0_1_0_4.txt ScamTest/input/0_1_1_0.txt ScamTest/input/0_1_1_1.txt ScamTest/input/0_1_1_2.txt ScamTest/input/0_1_1_3.txt ScamTest/input/0_1_1_4.txt ScamTest/input/0_1_2_0.txt ScamTest/input/0_1_2_1.txt ScamTest/input/0_1_2_2.txt ScamTest/input/0_1_2_3.txt ScamTest/input/0_1_2_4.txt ScamTest/input/0_2_0_0.txt ScamTest/input/0_2_0_1.txt ScamTest/input/0_2_0_2.txt ScamTest/input/0_2_0_3.txt ScamTest/input/0_2_0_4.txt ScamTest/input/0_2_1_0.txt ScamTest/input/0_2_1_1.txt ScamTest/input/0_2_1_2.txt ScamTest/input/0_2_1_3.txt ScamTest/input/0_2_1_4.txt ScamTest/input/0_2_2_0.txt ScamTest/input/0_2_2_1.txt ScamTest/input/0_2_2_2.txt ScamTest/input/0_2_2_3.txt ScamTest/input/0_2_2_4.txt ScamTest/input/0_3_0_0.txt ScamTest/input/0_3_0_1.txt ScamTest/input/0_3_0_2.txt ScamTest/input/0_3_0_3.txt ScamTest/input/0_3_0_4.txt ScamTest/input/0_3_1_0.txt ScamTest/input/0_3_1_1.txt ScamTest/input/0_3_1_2.txt ScamTest/input/0_3_1_3.txt ScamTest/input/0_3_1_4.txt ScamTest/input/0_3_2_0.txt ScamTest/input/0_3_2_1.txt ScamTest/input/0_3_2_2.txt ScamTest/input/0_3_2_3.txt ScamTest/input/0_3_2_4.txt ScamTest/input/0_4_0_0.txt ScamTest/input/0_4_0_1.txt ScamTest/input/0_4_0_2.txt ScamTest/input/0_4_0_3.txt ScamTest/input/0_4_0_4.txt ScamTest/input/0_4_1_0.txt ScamTest/input/0_4_1_1.txt ScamTest/input/0_4_1_2.txt ScamTest/input/0_4_1_3.txt ScamTest/input/0_4_1_4.txt ScamTest/input/0_4_2_0.txt ScamTest/input/0_4_2_1.txt ScamTest/input/0_4_2_2.txt ScamTest/input/0_4_2_3.txt ScamTest/input/0_4_2_4.txt ScamTest/input/1_0_0_0.txt ScamTest/input/1_0_0_1.txt ScamTest/input/1_0_0_2.txt ScamTest/input/1_0_0_3.txt ScamTest/input/1_0_0_4.txt ScamTest/input/1_0_1_0.txt ScamTest/input/1_0_1_1.txt ScamTest/input/1_0_1_2.txt ScamTest/input/1_0_1_3.txt ScamTest/input/1_0_1_4.txt ScamTest/input/1_0_2_0.txt ScamTest/input/1_0_2_1.txt ScamTest/input/1_0_2_2.txt ScamTest/input/1_0_2_3.txt ScamTest/input/1_0_2_4.txt ScamTest/input/1_1_0_0.txt ScamTest/input/1_1_0_1.txt ScamTest/input/1_1_0_2.txt ScamTest/input/1_1_0_3.txt ScamTest/input/1_1_0_4.txt ScamTest/input/1_1_1_0.txt ScamTest/input/1_1_1_1.txt ScamTest/input/1_1_1_2.txt ScamTest/input/1_1_1_3.txt ScamTest/input/1_1_1_4.txt ScamTest/input/1_1_2_0.txt ScamTest/input/1_1_2_1.txt ScamTest/input/1_1_2_2.txt ScamTest/input/1_1_2_3.txt ScamTest/input/1_1_2_4.txt ScamTest/input/1_2_0_0.txt ScamTest/input/1_2_0_1.txt ScamTest/input/1_2_0_2.txt ScamTest/input/1_2_0_3.txt ScamTest/input/1_2_0_4.txt ScamTest/input/1_2_1_0.txt ScamTest/input/1_2_1_1.txt ScamTest/input/1_2_1_2.txt ScamTest/input/1_2_1_3.txt ScamTest/input/1_2_1_4.txt ScamTest/input/1_2_2_0.txt ScamTest/input/1_2_2_1.txt ScamTest/input/1_2_2_2.txt ScamTest/input/1_2_2_3.txt ScamTest/input/1_2_2_4.txt ScamTest/input/1_3_0_0.txt ScamTest/input/1_3_0_1.txt ScamTest/input/1_3_0_2.txt ScamTest/input/1_3_0_3.txt ScamTest/input/1_3_0_4.txt ScamTest/input/1_3_1_0.txt ScamTest/input/1_3_1_1.txt ScamTest/input/1_3_1_2.txt ScamTest/input/1_3_1_3.txt ScamTest/input/1_3_1_4.txt ScamTest/input/1_3_2_0.txt ScamTest/input/1_3_2_1.txt ScamTest/input/1_3_2_2.txt ScamTest/input/1_3_2_3.txt ScamTest/input/1_3_2_4.txt ScamTest/input/1_4_0_0.txt ScamTest/input/1_4_0_1.txt ScamTest/input/1_4_0_2.txt ScamTest/input/1_4_0_3.txt ScamTest/input/1_4_0_4.txt ScamTest/input/1_4_1_0.txt ScamTest/input/1_4_1_1.txt ScamTest/input/1_4_1_2.txt ScamTest/input/1_4_1_3.txt ScamTest/input/1_4_1_4.txt ScamTest/input/1_4_2_0.txt ScamTest/input/1_4_2_1.txt ScamTest/input/1_4_2_2.txt ScamTest/input/1_4_2_3.txt ScamTest/input/1_4_2_4.txt
 */
