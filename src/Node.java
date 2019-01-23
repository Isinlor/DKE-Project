class Node implements Comparable<Node>{
	
	private int label;
	private int degree;
	private int satDegree;
	private int color;
		
	public Node(int label){
		
		this.label = label;
		this.degree = 0;
		this.satDegree = 0;
		this.color = -1;
	}
		
	public int getLabel(){
		
		return this.label;
	}
		
	public void setLabel(int label){
		
		this.label = label;
	}	
	
	public int getDegree(){
		
		return this.degree;
	}
		
	public void setDegree(int degree){
		
		this.degree = degree;
	}
	
	public int getSatDegree(){
	
		return this.satDegree;
	}
	
	public void setSatDegree(int satDegree){
	
		this.satDegree = satDegree;
	}
	
	public int getColor(){
	
		return this.color;
	}
	
	public void setColor(int color){
	
		this.color = color;
	}
	
	public int compareTo(Node x){
	
		if(this.satDegree < x.satDegree)
			return -1;
		
		if(this.satDegree > x.satDegree)
			return 1;
	
		if(this.degree < x.degree)
			return -1;
		
		if(this.degree > x.degree)
			return 1; 
		
		return 0;
	}

}