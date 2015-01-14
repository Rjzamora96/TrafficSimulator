package enums;
import java.util.Random;

public enum Paint {
	BLUE("images/topCarBlue.png"),
	PURPLE("images/topCarPurple.png"),
	RED("images/topCarRed.png"),
	YELLOW("images/topCarYellow.png");
	
	private String image;
	
	private Paint(String image) {
		this.image = image;
	}
	
	public String getImage() {
		return image;
	}
	
	public Paint randomize() {
		Random rand = new Random();
		Paint[] possible = Paint.values();
		int selected = rand.nextInt(Paint.values().length - 1);
		return possible[selected];
	}
}
