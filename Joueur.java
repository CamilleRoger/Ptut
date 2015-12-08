import java.sql.Statement;

public class Joueur {
	
	private String pseudo;
	private Carte carteMain1;
	private Carte carteMain2;
	private boolean dealer;
	private int stack;
	private int mise;
	private int id;
	private Boolean historique;
	
	public Joueur(String pseudo, Carte carteMain1, Carte carteMain2, boolean dealer, boolean historique) {
		this.pseudo = pseudo;
		this.carteMain1 = carteMain1;
		this.carteMain2 = carteMain2;
		this.dealer = dealer;
		this.stack = stack;
		this.mise = mise;
		this.historique = historique;
		
		if (historique) {
			//Ajout du Joueur et récupèration en cas d'existance
			try{
				Statement stat = CmdSql.GererBD();
				String cmd = new String("SELECT COUNT(ID_Joueur) as ID_Joueur FROM Joueur WHERE Pseudo = '" + pseudo + "';");
				id = CmdSql.RecupererJoueur(cmd);
				
				if (id == 0) {
					stat.executeUpdate("insert into Joueur(Pseudo) values('" + pseudo + "')");
					cmd = new String("SELECT ID_Joueur FROM Joueur WHERE Pseudo = '" + pseudo + "';");
					id = CmdSql.RecupererJoueur(cmd);
				} else {
					cmd = new String("SELECT ID_Joueur FROM Joueur WHERE Pseudo = '" + pseudo + "';");
					id = CmdSql.RecupererJoueur(cmd);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		} else {
			String cmd = new String("SELECT ID_Joueur FROM Joueur WHERE Pseudo = '" + pseudo + "';");
			try {
				id = CmdSql.RecupererJoueur(cmd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public Carte getCarteMain1() {
		return carteMain1;
	}

	public void setCarteMain1(Carte carteMain1) {
		this.carteMain1 = carteMain1;
	}

	public Carte getCarteMain2() {
		return carteMain2;
	}

	public void setCarteMain2(Carte carteMain2) {
		this.carteMain2 = carteMain2;
	}

	public boolean isDealer() {
		return dealer;
	}

	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}
	
	public void setStack(int pot, String idPartie){
		this.stack = pot;
		
		if (historique) {
			//Ajout a historique
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, Montant) values('7','"+this.getId()+"','"+idPartie+"', '"+pot+"')");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public int getStack() {
		return this.stack;
	}
	
	public void mise(int m, String idPartie) {
		this.stack = this.stack - m;
		this.mise+=m;
		
		if (historique) {
			//Ajout a historique
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, Montant) values('1','"+this.getId()+"','"+idPartie+"', '"+m+"')");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public int getMise() {
		return this.mise;
	}
	
	public void deleteMise() {
		this.mise = 0;
	}
	
	public void check(String idPartie) {
		if (historique) {
			//Ajout a historique
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie) values('2','"+this.getId()+"','"+idPartie+"')");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void aligner(Joueur adversaire, String idPartie) {
		int difference = adversaire.getMise() - this.getMise();
		this.stack = this.stack - difference;
		this.mise += difference;
		
		if (historique) {
			//Ajout a historique
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie) values('6','"+this.getId()+"','"+idPartie+"')");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void seCouche(Partie p) {

		//Ajouter � Historique
		if (historique) {
			//Ajout a historique
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Coups(ID_Action, ID_Partie, ID_Joueur) values('8','"+p.getIdPartie()+"','"+this.id+"')");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		p.InitManche(this, false);
	}
	
	public void gagne(Partie p) {
		
		//Ajouter � Historique
		if (historique) {
			//Ajout a historique
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Coups(ID_Action, ID_Partie, ID_Joueur) values('8','"+p.getIdPartie()+"','"+this.getId()+"')");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		p.InitManche(this, true);
	}

	@Override
	public String toString() {
		return "Joueur [pseudo=" + pseudo + ", carteMain1=" + carteMain1 + ", carteMain2=" + carteMain2 + ", dealer="
				+ dealer + ", stack=" + stack + ", mise=" + mise + ", id=" + id + ", historique=" + historique + "]";
	}
}
