package �����server;

import java.io.Serializable;

public class Card  implements  Serializable {

	public String name;//ͼƬurl����
	
	public Card(String name){
		
		this.name=name;
	   
	}

	@Override
	public String toString() {
		return "Card [name=" + name + "]";
	}
	
	

}
