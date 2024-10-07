import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphL4A {

	private int n;
	private int type; //0 if undirected, 1 if directed
	private int weighted; // 0 if unweighted, 1 otherwise
	private WeightedNode4A[] adjlistW; //one of adjlistW and adjlist is null, depending on the type of the graph
	private Node4A[] adjlist;

	GraphL4A(int n , int type, int weighted){
		this.n = n;
		this.type = type;
		this.weighted = weighted;
		if (this.weighted==0) {
			this.adjlist = new Node4A[this.n];
			for (int i=0; i<this.n; i++)
				adjlist[i]=null;
			adjlistW=null;
		}
		else {
			this.adjlistW=new WeightedNode4A[this.n];
			for (int i=0;i<this.n;i++)
				adjlistW[i]=null;
			adjlist=null;
		}
	}
	public GraphL4A(Scanner sc){
	    String[] firstline = sc.nextLine().split(" ");
	    this.n = Integer.parseInt(firstline[0]);
	    System.out.println(this.n);
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
	    if (this.weighted==0) {
	    	this.adjlist=new Node4A[this.n];
	    	for (int i=0;i<this.n;i++)
	    		adjlist[i]=null;
	        adjlistW=null;
	    }
	    else {
	    	this.adjlistW=new WeightedNode4A[this.n];
	    	for (int i=0;i<this.n;i++)
	    		adjlistW[i]=null;
	        adjlist=null;
	    }
	    	
	    
	  	for(int k=0;k<this.n;k++){
	  		String[] line = sc.nextLine().split(" : ");
	  		int i = Integer.parseInt(line[0]); //the vertex "source"
	  		if (weighted==0) {
				if ((line.length>1) && (line[1].charAt(0)!=' ')) { // un espace entre les apostrophes
					String[] successors= line[1].split(", ");
					System.out.println(i+ " "+ successors.length);
					for (int h=0;h<successors.length;h++) {
						Node4A node=new Node4A(Integer.parseInt(successors[h])-1,null);
						node.setNext(adjlist[i-1]);
						adjlist[i-1]=node;
					}
				}
	  		}
	  		else {
	  			line = line[1].split(" // "); 
	  			if ((line.length==2)&&(line[1].charAt(0)!=' ')){// if there really are some successors, then we must have something different from " " after "// "
	  				  	String[] successors= line[0].split(", ");
	  			  	    String[] theirweights= line[1].split(", ");
	  				  	for (int h=0;h<successors.length;h++) {
	  				  	  	WeightedNode4A nodeW = new WeightedNode4A(Integer.parseInt(successors[h])-1,null,Float.parseFloat(theirweights[h]));
	  				  	  	nodeW.setNext(adjlistW[i-1]);
	  				  	  	adjlistW[i-1]=nodeW;
	  				  	}
	  			
	  			}
	  		}
	  	}
	  	
	  }
	    
	    
	  	
	
	//method to be applied only when type=0 and weighted=0
	public int[] degree(){
		int[] tmp=new int[this.n];
	    for(int i=0;i<this.n;i++) 
	    	tmp[i]=0;
	    	for(int i=0;i<this.n;i++) {
	    		Node4A p=adjlist[i];
	    		while (p!=null) {
	    			tmp[i]=tmp[i]+1;
	    			p=p.getNext();
	    		}
	    	}
	    	return(tmp);
	  }
	
	//method to be applied only when type=0 and weighted=1
		public int[] degreeW(){
			int[] tmp=new int[this.n];
		    for(int i=0;i<this.n;i++) 
		    	tmp[i]=0;
		    	for(int i=0;i<this.n;i++) {
		    		WeightedNode4A p=adjlistW[i];
		    		while (p!=null) {
		    			tmp[i]=tmp[i]+1;
		    			p=p.getNext();
		    		}
		    	}
		    	return(tmp);
		  }
	

	//method to be applied only when type=1 and weighted=0
	  public TwoArrays4A degrees(){
		  int[] tmp1=new int[this.n]; //indegrees
		  int[] tmp2=new int[this.n]; //outdegrees
		  for(int i=0;i<this.n;i++) { 
		    tmp1[i]=0;
		    tmp2[i]=0;
		  }
    	for(int i=0;i<this.n;i++) {
    		Node4A p=adjlist[i];
    		while (p!=null) {
    			tmp2[i]=tmp2[i]+1;
    			tmp1[p.getVal()]=tmp1[p.getVal()]+1;
    			p=p.getNext();
    		}
    	}	
	    return(new TwoArrays4A(tmp1,tmp2));        
	  }
		
	//method to be applied only when type=1 and weighted=1
	  public TwoArrays4A degreesW(){
		  int[] tmp1=new int[this.n]; //indegrees
		  int[] tmp2=new int[this.n]; //outdegrees
		  for(int i=0;i<this.n;i++) { 
		    tmp1[i]=0;
		    tmp2[i]=0;
		  }
    	for(int i=0;i<this.n;i++) {
    		WeightedNode4A p=adjlistW[i];
    		while (p!=null) {
    			tmp2[i]=tmp2[i]+1;
    			tmp1[p.getVal()]=tmp1[p.getVal()]+1;
    			p=p.getNext();
    		}
    	}	
	    return(new TwoArrays4A(tmp1,tmp2));        
	  }

		
	  //transpose Graph : the input graph G(List), and  the output graph Gt(List)
	  public GraphL4A transposeGL(){
		GraphL4A transposed = new GraphL4A(this.n, this.type, this.weighted);
		if(this.weighted == 0){
			for(int i=0; i<this.n; i++){
				Node4A current = this.adjlist[i];
				while(current != null){
					Node4A node = new Node4A(i, null);
					transposed.addNode(node, current.getVal());
					current = current.getNext();
				}
			}
		}else{
			for(int i=0; i<this.n; i++){
				WeightedNode4A current = this.adjlistW[i];
				while(current != null){
					WeightedNode4A nodeW = new WeightedNode4A(i, null, current.getWeight());
					transposed.addWeightedNode(nodeW, current.getVal(), current.getWeight());
					current = current.getNext();
				}
			}
		}
		return transposed;
	  }

	private void addWeightedNode(WeightedNode4A nodeW, int vertex, Float weight) {
		nodeW.setNext(this.adjlistW[vertex]);
		this.adjlistW[vertex] = nodeW;
	}

	private void addNode(Node4A node, int vertex) {
		node.setNext(this.adjlist[vertex]);
		this.adjlist[vertex] = node;
	}

	//Transposed graph : the input graph G(List), and  the output graph G(Matrix)
	public GraphM4A transposeGM(){
		GraphM4A transposed = new GraphM4A(this.n, this.type, this.weighted);
		if(weighted == 0){
			for(int i=0; i<n; i++){
				Node4A current = this.adjlist[i];
				while(current != null){
					transposed.addEdge(current.getVal(), i, 1); //ajout de l'arc
					current = current.getNext();
				}
			}
		}else{
			for(int i=0; i<n; i++){
				WeightedNode4A current = this.adjlistW[i];
				while(current != null){
					transposed.addEdge(current.getVal(), i, current.getWeight()); //ajout de l'arc
					current = current.getNext();
				}
			}
		}
		return transposed;
	}

	  public Node4A[] getAdjlist() { return this.adjlist; }
	  public WeightedNode4A[] getAdjlistW() { return this.adjlistW; }
	  public int getType() {
			return this.type;
		}
	  public int getWeighted() {
			return this.weighted;
		}

		/**
		 * Exo2 : verifier s'il le chemin donné en parametre est un chemin dans le graphe
		 * */
	public boolean thisIsAPath(int[] vertexes){
		if(vertexes.length ==0)
			return false;
		int k=0;
		while (k < vertexes.length-1 && verifyEdge(vertexes[k]-1, vertexes[k + 1]-1)) {
			k++;
		}
		return (k==vertexes.length-1);
	}

	public boolean verifyEdge(int i, int j) {
		if(weighted==0){
			if(this.adjlist[i] != null){
				Node4A current = this.adjlist[i];
				while(current != null && current.getVal()!= j){
					current = current.getNext();
				}
				return !(current == null);

			}else
				return false;
		}else{
			if(this.adjlistW[i] != null) {
				WeightedNode4A current = this.adjlistW[i];
				while(current != null && current.getVal() != j) {
					current = current.getNext();
				}
				return !(current == null);
			}else
				return false;
		}
	}

	public void DFSNum(int s){
		int[] debut = new int[this.n];
		int[] fin = new int[this.n];
		int[] nb = new int[1];
		String[][] edgeTypes = new String[this.n][this.n]; //stoquer le type des arcs dans un tableau
		nb[0] = 0;
		for(int i=0; i<n; i++){
			debut[i] = 0;
			fin[i] = 0;
		}

		DFSNum(s-1, debut, fin, nb, edgeTypes);

		System.out.println();
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(edgeTypes[i][j] != null) {
					System.out.println("Arc (" + (i+1) + ", " + (j+1) + "): " + edgeTypes[i][j]);
				}
			}
		}
	}

	/**
	 * Parcours DFSNum (en profendeurs)
	 * Compléxité = O(n+m)
	 **/
	public void DFSNum(int s, int[] debut, int[] fin, int[] nb, String[][] edgeTypes){
		nb[0]++;
		debut[s] = nb[0];
		if(weighted == 0){
			Node4A current = this.adjlist[s];
			while(current != null){
				int t = current.getVal();
				if(debut[t] == 0){
					edgeTypes[s][t] = "tree Edge";
					System.out.print(" "+(t+1)+" ");
					DFSNum(t, debut, fin, nb, edgeTypes);
				} else if(fin[t] == 0){
					edgeTypes[s][t] = "back Edge";
				} else if(debut[s] < debut[t]){
					edgeTypes[s][t] = "forward Edge";
				} else if(debut[s] > debut[t]){
					edgeTypes[s][t] = "cross Edge";
				}
				current = current.getNext();
			}
		} else { // graphe pondéré
			WeightedNode4A current = this.adjlistW[s];
			while(current != null){
				int t = current.getVal();
				if(debut[t] == 0){
					edgeTypes[s][t] = "tree Edge";
					System.out.print(" "+(t+1)+" ");
					DFSNum(t, debut, fin, nb, edgeTypes);
				} else if(fin[t] == 0){
					edgeTypes[s][t] = "back Edge";
				} else if(debut[s] < debut[t]){
					edgeTypes[s][t] = "forward Edge";
				} else if(debut[s] > debut[t]){
					edgeTypes[s][t] = "cross Edge";
				}
				current = current.getNext();
			}
		}
		nb[0]++;
		fin[s] = nb[0];
	}


	/**EXO2 TP2
	 * Methode qui verifie s'il a un cycle ou pas de le graphe
	 * Compléxité = O(n+m)
	 **/
	public boolean containCycle() {
		int[] debut = new int[this.n];
		int[] fin = new int[this.n];
		int[] nb = new int[1];
		nb[0] = 0;
		for(int i=0; i<n; i++){
			debut[i] = 0;
			fin[i] = 0;
		}

		for(int i=0; i<this.n; i++){ //pour chaque sommet regarder s'il y a pas de cycle
			if(containCycleVertex(i,debut, fin, nb))
				return true;
		}
		return false;
	}

	private boolean containCycleVertex(int s, int[] debut, int[] fin, int[] nb) {
		nb[0]++;
		debut[s] = nb[0];
		if(weighted==0){
			Node4A current = this.adjlist[s];
			while(current != null){
				int t = current.getVal();
				if(debut[t] == 0){
					if(containCycleVertex(t, debut, fin, nb))
						return true;
				} else if(fin[t] == 0){ //back arc
					return true;
				}
				current = current.getNext();
			}
		}else{
			WeightedNode4A current = this.adjlistW[s];
			while(current != null){
				int t = current.getVal();
				if(debut[t] == 0){
					if(containCycleVertex(t, debut, fin, nb))
						return true;
				} else if(fin[t] == 0){	//back arc
					return true;
				}
				current = current.getNext();
			}
		}
		nb[0]++;
		fin[s] = nb[0];
		return false;
	}

	/**
	 * Exo3 TP2
	 * Methode qui affiche les sommets du cycle s'il y a un
	 * Complexité O(n+m)
	 * */
	public void printCycleVertex(){
		int[] debut = new int[this.n];
		int[] fin = new int[this.n];
		int[] nb = new int[1];
		nb[0] = 0;
		List<Integer> cycle = new ArrayList<>();
		for(int i=0; i<n; i++){
			debut[i] = 0;
			fin[i] = 0;
		}

		for(int i=0; i<this.n; i++){ //pour chaque sommet regarder s'il y a pas de cycle
			cycle = new ArrayList<>();
			if(cycleVertex(i, debut, fin ,nb, cycle)){
				System.out.print("The verteces of the cycle :");
				if(!cycle.isEmpty()){
                    for (Integer integer : cycle) {
                        System.out.print(" " + (integer + 1));
                    }
					System.out.println();
				}
			return;
			}
		}
	}

	private boolean cycleVertex(int s, int[] debut, int[] fin, int[] nb, List<Integer> cycle) {
		nb[0]++;
		debut[s] = nb[0];
		if(weighted==0){
			Node4A current = this.adjlist[s];
			while(current != null){
				int t = current.getVal();
				if(debut[t] == 0){//pas encore visité
					cycle.add(s);
					if(cycleVertex(t, debut, fin, nb, cycle))
						return true;
				} else if(fin[t] == 0){ //back arc
					cycle.add(s);
					cycle.add(t);
					return  true;
				}
				current = current.getNext();
			}
		}else{
			WeightedNode4A current = this.adjlistW[s];
			while(current != null){
				int t = current.getVal();
				if(debut[t] == 0){
					cycle.add(s);
					if(cycleVertex(t, debut, fin, nb, cycle))
						return true;
				} else if(fin[t] == 0){	//back arc
					cycle.add(s);
					cycle.add(t);
					return true;
				}
				current = current.getNext();
			}
		}
		nb[0]++;
		fin[s] = nb[0];
		return false;
	}

}