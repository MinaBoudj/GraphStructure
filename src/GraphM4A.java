
import java.util.Scanner;

public class GraphM4A {

  private int n;
  private int type; //0 if undirected, 1 if directed
  private int weighted; // 0 if unweighted, 1 otherwise
  private float[][] adjmat;

  public GraphM4A(int n, int type, int weighted){
      this.n = n;
      this.type = type;
      this.weighted = weighted;
      this.adjmat = new float[this.n][this.n];
  }

  public GraphM4A(Scanner sc){
    String[] firstline = sc.nextLine().split(" ");
    this.n = Integer.parseInt(firstline[0]);
    System.out.println("Number of vertices "+this.n);
    if (firstline[1].equals("undirected"))
    	this.type = 0;
    else
    	this.type=1;
    System.out.println("Type= "+this.type);
    if (firstline[2].equals("unweighted"))
    	this.weighted = 0;
    else
    	this.weighted=1;
    System.out.println("Weighted= "+this.weighted);
    
    this.adjmat = new float[this.n][this.n];
    for (int i=0;i<this.n; i++)
    	for(int j=0; j<this.n; j++)
    		adjmat[i][j]=0; // replace 0 with something else if the weights can be 0

  	for(int k=0;k<this.n;k++){
  		String[] line = sc.nextLine().split(" : ");
  		int i = Integer.parseInt(line[0]); //the vertex "source"
  		if (weighted==0) {
            if ((line.length>1) && (line[1].charAt(0)!=' ')) {
                String[] successors= line[1].split(", ");
                System.out.println(i+ " "+ successors.length);
                for (int h=0;h<successors.length;h++){
                    System.out.println(Integer.parseInt(successors[h]));
                    this.adjmat[i-1][Integer.parseInt(successors[h])-1]=1;
                }
            }
  			/*String[] successors= line[1].split(", ");
  			System.out.println(i+ " "+ successors.length);
  			for (int h=0;h<successors.length;h++)
  					this.adjmat[i-1][Integer.parseInt(successors[h])-1]=1;*/
  		}
  		else {
  			line = line[1].split(" // "); 
  			if ((line.length==2)&&(line[1].charAt(0)!=' ')){// if there really are some successors, then we must have something different from " " after "// "
  				  	String[] successors= line[0].split(", ");
  			  	    String[] theirweights= line[1].split(", ");
  				  	for (int h=0;h<successors.length;h++)
  				  	  		this.adjmat[i-1][Integer.parseInt(successors[h])-1]=Float.parseFloat(theirweights[h]);
  			}
  		}
  	}
  }

//method to be applied only when type=0
  public int[] degree(){
	int[] tmp=new int[this.n];
    for(int i=0;i<this.n;i++) 
    	tmp[i]=0;
    for(int i=0;i<this.n;i++)
      for(int j=0;j<this.n;j++)
        if(this.adjmat[i][j] != 0)
          tmp[i] = tmp[i] + 1 ;
    return tmp;   

  }

//method to be applied only when type=1
  public TwoArrays4A degrees(){
	  int[] tmp1=new int[this.n]; //indegrees
	  int[] tmp2=new int[this.n]; //outdegrees
	  for(int i=0;i<this.n;i++) { 
	    tmp1[i]=0;
	    tmp2[i]=0;
	  }
    for(int i=0;i<this.n;i++)
      for(int j=0;j<this.n;j++)
        if(this.adjmat[i][j]!=0){
          tmp2[i]= tmp2[i] +1;
          tmp1[j]=tmp1[j]+1;
        }
    return(new TwoArrays4A(tmp1,tmp2));
        
  }

public int getType() {
	return this.type;
}
public void setAdjmat(float[][] adjmat){ this.adjmat = adjmat; }
//Exercice 1
    //the input graph G(Matrix), and of the output graph Gt(Matrix)
    public GraphM4A transposeGM(){ //complexite polynomiale
      GraphM4A transposed = new GraphM4A(this.n, this.type, this.weighted);
      for(int i=0; i<this.n; i++){
          for(int j=0; j<this.n; j++){
              transposed.adjmat[i][j] = this.adjmat[j][i];
          }
      }
      return transposed;
    }

    //the input graph G(Matrix), and of the output graph Gt(List)
    public GraphL4A transposeGML(){
      GraphL4A transposed = new GraphL4A(this.n, this.type, this.weighted);
      if(this.weighted == 0){ //non-pondéré
          for(int i=0; i<this.n; i++){
              for(int j=0; j<this.n; j++){
                  if(this.adjmat[i][j] == 1){
                      Node4A[] adjlist = transposed.getAdjlist();
                      Node4A node = new Node4A(i, adjlist[j]); //je mets au debut
                      adjlist[j] = node;
                  }
              }
          }
      }else{ //pondéré
          for(int i=0; i<this.n; i++){
              for(int j=0; j<this.n; j++){
                  WeightedNode4A[] adjlistW = transposed.getAdjlistW();
                  WeightedNode4A nodeW = new WeightedNode4A(i, adjlistW[j], this.adjmat[i][j]); //on mets à la fin
                  adjlistW[j] = nodeW;
              }
          }
      }
      return transposed;
    }

    public void addEdge(int i, int j, float val) {
        this.adjmat[i][j] = val;
    }

    /**
     * Étant donné un graphique non dirigé G et une séquence de sommets v1 , v2 , . . . , vk ,
     * testez s'il existe un chemin dans G avec des bords v1v2, v2v3, . . . , vivi+1, . . . , vk-1vk.
     * Un paramètre doit permettre de choisir la représentation (liste d'adjacence ou matrice d'adjacence)
     * du graphique d'entrée G.
     */
    public boolean thisIsAPath(int[] vertexes){
        if(vertexes.length == 0) // A rajouter !!
            return false;
        int k=0;
        while(k<vertexes.length-1 && this.adjmat[vertexes[k]-1][vertexes[k+1]-1] != 0){
            k++;
        }
        return k==vertexes.length-1;
    }

}
