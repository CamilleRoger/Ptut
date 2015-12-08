
public class Carte {
	
	private String name;
	private String id;
	
	public Carte(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getID() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Carte [name=" + name + "]";
	}

}
