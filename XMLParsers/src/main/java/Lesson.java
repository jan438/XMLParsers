package main.java;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
	
	private String name;
	private List<Triplet> triplets;    
    
	public Lesson() {
		this.name = null;
		this.triplets = new ArrayList<Triplet>();
	}

    public void setName(String iname) {
		name = iname;
	}
    
    public String getName() {
		return name;
	}
    
	public List<Triplet> getTriplets() {
		return triplets;
	}

	public void setTriplets(List<Triplet> itriplets) {
		triplets = itriplets;
	}

	public void addTriplet (Triplet triplet) {
		triplets.add(triplet);
	}
	
	@Override
    public String toString() {
            return "<" + name + ">";
    }

}
