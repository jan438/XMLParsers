package main.java;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	
	private String name;
	private List<Lesson> lessons;
	
    public Chapter() {
    	this.name = null;
    	lessons = new ArrayList<Lesson>();
    }

    public void setName(String iname) {
		name = iname;
	}
    
    public String getName() {
		return name;
	}
    
	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> ilessons) {
		lessons = ilessons;
	}

	
	public void addLesson (Lesson lesson) {
		lessons.add(lesson);
	}
	
	@Override
    public String toString() {
            return "<" + name + ">";
    }

}
