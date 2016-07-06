package ÍøÂç°æserver;

import java.io.Serializable;

public class Card  implements  Serializable {

	public String name;//Í¼Æ¬urlÃû×Ö
	
	public Card(String name){
		
		this.name=name;
	   
	}

	@Override
	public String toString() {
		return "Card [name=" + name + "]";
	}
	
	

}
