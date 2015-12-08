
public class Action {
	
	private String Date;
	private String Montant;
	private String ID_Action;
	private String ID_Joueur;
	private String ID_Carte;
	
	public Action(String date, String montant, String iD_Action, String iD_Joueur, String iD_Carte) {
		this.Date = date;
		this.Montant = montant;
		this.ID_Action = iD_Action;
		this.ID_Joueur = iD_Joueur;
		this.ID_Carte = iD_Carte;
	}

	public String getDate() {
		return Date;
	}

	public String getMontant() {
		return Montant;
	}

	public String getID_Action() {
		return ID_Action;
	}

	public String getID_Joueur() {
		return ID_Joueur;
	}

	public String getID_Carte() {
		return ID_Carte;
	}
	

}
