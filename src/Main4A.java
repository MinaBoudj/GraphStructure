import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Compilation : javac -source 1.8 -target 1.8 -d bin *.java
 * Execution : java -cp bin Main4A filename.txt
 * */

public class Main4A {

  public static void main(String args[]){

    try{
      File file= new File(args[0]);
      
    //If we choose the representation by adjacency matrix
        System.out.println("\n***************************** Matrice *************************");
        Scanner sc = new Scanner(file);
      GraphM4A graphM = new GraphM4A(sc); 
      if (graphM.getType()==0) { //undirected
    	  int[] degree = graphM.degree();
      System.out.println("(Matrix) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");  
      Tools4A.printArray(degree);

      /*test Exo2 */
          int[] vertexes = new int[]{10,1,4,2,3};
          System.out.println("\n\n *teste Exo2\n");
          boolean verify = graphM.thisIsAPath(vertexes);
          if(verify)
              System.out.println("  [10,1,4,2,3] ce chemin est dans le graph !");
          else
              System.out.println("  [10,1,4,2,3] ce chemin n'est pas dans le graphe !!");

          vertexes = new int[]{10,3,2};
          verify = graphM.thisIsAPath(vertexes);
          if(verify)
              System.out.println("  [10,3,2] ce chemin est dans le graph !\n\n");
          else
              System.out.println("  [10,3,2] ce chemin n'est pas dans le graphe !!\n\n");
      }
      else { //directed
    	  TwoArrays4A pair=graphM.degrees();
    	  int[] indegree =pair.in(); //the result of graphM.degrees() is a pair of arrays, indegree and outdegree
          int[] outdegree =pair.out();
          System.out.println("(Matrix)Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(indegree);
          System.out.println("(Matrix)Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(outdegree);

          System.out.println("\n\n**teste Exo1 \n");
          System.out.println("*From GM to transpose GM\n");
          GraphM4A transposed = graphM.transposeGM();
          TwoArrays4A tpair=transposed.degrees();
          int[] tindegree =tpair.in(); //the result of graphM.degrees() is a pair of arrays, indegree and outdegree
          int[] toutdegree =tpair.out();
          System.out.println("(Matrix)Indegrees for vertices from 1 to " + tindegree.length + " for the given digraph");
          Tools4A.printArray(tindegree);
          System.out.println("(Matrix)Outdegrees for vertices from 1 to " + tindegree.length + " for the given digraph");
          Tools4A.printArray(toutdegree);

          System.out.println("\n*From GM to transpose GL\n");
          GraphL4A Ltransposed = graphM.transposeGML();
          TwoArrays4A Lpair=Ltransposed.degrees();
          int[] Lindegree = Lpair.in();
          int[] Loutdegree = Lpair.out();
          System.out.println("(List) Indegrees for vertices from 1 to " + Lindegree.length + " for the given digraph");
          Tools4A.printArray(Lindegree);
          System.out.println("(List) Outdegrees for vertices from 1 to " + Lindegree.length + " for the given digraph");
          Tools4A.printArray(Loutdegree);
          System.out.println();


      }
      
   // If we choose the representation by adjacency lists
        System.out.println("\n\n***************************** Liste *************************");
      sc = new Scanner(file);
      GraphL4A graphL = new GraphL4A(sc); 
      if (graphL.getType()==0&&graphL.getWeighted()==0) { //undirected and unweighted
    	  int[] degree = graphL.degree();
    	  System.out.println("(List) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");  
          Tools4A.printArray(degree);
      }
      if (graphL.getType()==0&&graphL.getWeighted()==1) { //undirected and weighted
    	  int[] degree = graphL.degreeW();
    	  System.out.println("(List) Degrees for vertices from 1 to " + degree.length + " for the given undirected graph");
          Tools4A.printArray(degree);

          /*test Exo2 */
          int[] vertexes = new int[]{10,1,4,2,3};
          System.out.println("\n***teste Exo2\n");
          boolean verify = graphL.thisIsAPath(vertexes);
          if(verify)
              System.out.println("  [10,1,4,2,3] ce chemin est dans le graph !");
          else
              System.out.println("  [10,1,4,2,3] ce chemin n'est pas dans le graphe !!");

          vertexes = new int[]{10,3,2};
          verify = graphL.thisIsAPath(vertexes);
          if(verify)
              System.out.println("  [10,3,2] ce chemin est dans le graph !\n\n");
          else
              System.out.println("  [10,3,2] ce chemin n'est pas dans le graphe !!\n\n");

          /*test Exo1 TP2 */
          System.out.println("\n***teste Exo1 TP2\n");
          graphL.DFSNum(10);

      }
      if (graphL.getType()==1&&graphL.getWeighted()==0){ //directed and unweighted
          System.out.println("Response ");
    	  TwoArrays4A pair = graphL.degrees();
    	  int[] indegree = pair.in(); 
          int[] outdegree = pair.out();
          System.out.println("(List) Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(indegree);
          System.out.println("(List) Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");  
          Tools4A.printArray(outdegree);

          System.out.println("\n\n**teste Exo1 \n");
          System.out.println("*From GL to transpose GM\n");
          TwoArrays4A pairM=graphL.transposeGL().degrees();
          int[] indegreeM = pairM.in();
          int[] outdegreeM = pairM.out();
          System.out.println("(List) Indegrees for vertices from 1 to " + indegreeM.length + " for the given digraph");
          Tools4A.printArray(indegreeM);
          System.out.println("(List) Outdegrees for vertices from 1 to " + indegreeM.length + " for the given digraph");
          Tools4A.printArray(outdegreeM);
      }
      if (graphL.getType()==1&&graphL.getWeighted()==1){ //directed and weighted
    	  TwoArrays4A pair=graphL.degreesW();
    	  int[] indegree = pair.in();
          int[] outdegree = pair.out();
          System.out.println("(List)Indegrees for vertices from 1 to " + indegree.length + " for the given digraph");
          Tools4A.printArray(indegree);
          System.out.println("(List)Outdegrees for vertices from 1 to " + indegree.length + " for the given digraph");
          Tools4A.printArray(outdegree);

      }
      sc.close();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
