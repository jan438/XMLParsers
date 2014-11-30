package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserWords extends DefaultHandler {

	private static List<Chapter> chapters = new ArrayList<Chapter>();
	private static List<Lesson> lessons;
	private static List<Triplet> triplets;

	private static Chapter chapter = null;
	private static Lesson lesson = null;
	private static Triplet triplet = null;
	private static String text = null;

	private static HashMap<String, String> vertaald = new HashMap<String, String>();
	private static HashMap<String, String> wordsxml = new HashMap<String, String>();
	
	@Override
	// A start tag is encountered.
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		switch (qName) {
			// Create a new Chapter.
			case "Chapter": {
				chapter = new Chapter();
				lesson = null;
				triplet = null;
				break;
			}
			// Create a new Lesson.
			case "Lesson": {
				lesson = new Lesson();
				triplet = null;
				break;
			}
			// Create a new Triplet.
			case "Triplet": {
				triplet = new Triplet();
				break;
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
			case "Chapter": {
				// The end tag of an chapter was encountered, so add the chapter to the list.
				chapters.add(chapter);
				lesson = null;
				triplet = null;
				break;
			}
			case "Lesson": {
				// The end tag of an lesson was encountered, so add the lesson to the chapter.
				chapter.addLesson(lesson);
				triplet = null;
				break;
			}
			case "Triplet": {
				// The end tag of an triplet was encountered, so add the triplet to the lesson.
				lesson.addTriplet(triplet);
				break;
			}
			case "Description": {
				break;
			}
			case "name": {
				if ((chapter != null) && (chapter.getName() == null)) chapter.setName(text);
				else if ((lesson != null) && (lesson.getName() == null)) lesson.setName(text);
			}
			case "image": {
				if ((triplet != null) && (qName == "image")) {
					triplet.setImage(text);
					System.out.println("1: " + text);
				}
			}
			case "voice": {
				if ((triplet != null) && (qName == "voice")) {
						triplet.setVoice(text);
						System.out.println("2: " + text);
				}
			}
		} 
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		text = String.copyValueOf(ch, start, length).trim();
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		
		if (args.length != 1)
			throw new RuntimeException("The name of the XML file is required!");

		SAXParserFactory parserFactor = SAXParserFactory.newInstance();
		SAXParser parser = parserFactor.newSAXParser();
		SAXParserWords handler = new SAXParserWords();

		parser.parse(new File(args[0]), handler);
		
		String pngname, oggname, key, value;
		int pngstart, oggstart, pngend, oggend, keyend;
		
		BufferedReader br = new BufferedReader(new FileReader("/home/keanu/Documenten/gcompris_todo12.csv"));
	    try {
	        String line = br.readLine();
	        while (line != null) {
	        	keyend = line.indexOf(',');
	            key = line.substring(0,keyend);
	            value = line.substring(keyend + 1, line.length());
	            vertaald.put(key, value);
	            line = br.readLine();
	        }
	        
	    } finally {
	        br.close();
	    }	    
	    System.out.println(vertaald.size());    
		System.out.println("chapters"+ " " + chapters.size());
		int triple_count = 0;
	    
		// Print all chapters.
		for (Chapter chapter : chapters) {
			lessons = chapter.getLessons();
			System.out.println("Chapter: " + chapter.getName() + " Number of lessons: " + lessons.size());
		    for (Lesson lesson : lessons) {
		    	triplets = lesson.getTriplets();			
		    	System.out.println("Lesson: " + lesson.getName() + " Number of triplets: " + triplets.size());
		    	for (Triplet triplet : triplets) {
		    		triple_count++;
		    		pngstart = -1;
		    		oggstart = -1;
		    		pngend = -1;
		    		oggend = -1;
		    		pngname = triplet.getImage();
		    		oggname = triplet.getVoice();
		    		System.out.println("triplet " + triple_count + " " + pngname + " " + oggname);
		    		if ((pngname != null) && (pngname.length() > 0)) {
		    			pngstart = pngname.indexOf("lang/words/");
		    			pngend = pngname.indexOf(".png");
		    			if ((pngstart >= 0) && (pngend > pngstart)) {
		    				pngname = pngname.substring(pngstart + "lang/words/".length(), pngend);
		    				if ((oggname != null) && (oggname.length() > 0)) {
		    					oggstart = oggname.indexOf("voices/$LOCALE/words/");
		    					oggend = oggname.indexOf(".ogg");
		    					if ((oggstart >= 0) && (oggend > oggstart)) {
		    						oggname = oggname.substring(oggstart + "voices/$LOCALE/words/".length(), oggend);
		    						key = pngname;
		    						wordsxml.put(key, oggname);
		    					}
		    					else {
		    						System.out.println("=======" + oggname);
		    						wordsxml.put(pngname, oggname);
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		}	
		System.out.println(wordsxml.size());
	}
}
