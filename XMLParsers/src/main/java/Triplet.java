package main.java;

public class Triplet {
	
    private String description;
    private String image;
    private String voice;
    
    public Triplet() {
    }

	public String getImage() {
		return image;
	}

	public void setImage(String iimage) {
		image = iimage;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String ivoice) {
		voice = ivoice;
	}
		
	@Override
    public String toString() {
            return "<" + description + ", " + image + ", " + voice + ">";
    }
}
