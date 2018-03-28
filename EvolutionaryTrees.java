/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolutionarytrees;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ajay
 */
public class EvolutionaryTrees {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        File file = new File("genetics.txt");
        String traversalPath = "";
        //ArrayList<Integer> path = new ArrayList<Integer>();
        ArrayList<String> speciesList = new ArrayList<String>();
        try {
            Scanner fileReader = new Scanner(file);
            traversalPath = fileReader.nextLine();
            //graph = new Graph(destinationVertex);
            do {
                speciesList.add(fileReader.nextLine());
            } while (fileReader.hasNext());
        } catch (FileNotFoundException fnfe) {
            System.err.println("File not Found");
        }
        
        int numberOfSite = speciesList.get(0).length();
        
        String tempRoot = "";
        for (int i = 1; i <= numberOfSite; i++) {
            tempRoot = tempRoot + "-";
        }
        BinaryTree tree = new BinaryTree(tempRoot);
        tree.populate(traversalPath, speciesList);
        System.out.println(tree.count());
        //tree.display();
        tree.createEvolutionTree(numberOfSite);
        ArrayList<String> solution = tree.getSolution();
        
        /*for (int i = 0; i < solution.size(); i++) {
            System.out.println(solution.get(i));
        }*/
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("tree.txt"));
            for (int i = 0; i < solution.size(); i++) {
                writer.write(solution.get(i)+"\n");
            }
            
            writer.close();
        } catch (IOException ioe) {
            System.err.println("File not found");
        }
        //tree.display();
        //tree.postorder();
        //tree.visitNode();
    }
    
}
