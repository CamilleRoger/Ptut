import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.smartcardio.CardTerminal;

public class Partie {

	protected Joueur joueur1;
	protected Joueur joueur2;
	protected int decalage;
	protected Carte[] board;
	protected int nBCarteOnBoard;
	protected String idPartie;
	protected Boolean historique;

	// LE DECALAGE EST EN SECONDE.
	public Partie(Joueur joueur1, Joueur joueur2, int decalage, String idPartie, Boolean historique) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
		this.decalage = decalage;
		this.board = new Carte[5];
		this.nBCarteOnBoard = 0;
		this.historique = historique;

		 if (historique) {
			//Ajout de la partie de la base de donn�e
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Partie(ID_Joueur1, ID_Joueur2, Decalage) values('"+joueur1.getId()+"','"+joueur2.getId()+"','"+decalage+"')");
				String cmd = new String("SELECT MAX(ID_Partie) as ID_Partie FROM Partie;");
				idPartie = CmdSql.RecupererPartie(cmd);
			}catch (Exception e){
				e.printStackTrace();
			}
		 }
		
		this.idPartie = idPartie;
		joueur1.setDealer(true);
		joueur2.setDealer(false);

	}

	public void setDecalage(int d) {
		this.decalage = d;
	}
	
	public void changerDealer() {
		if(this.joueur1.isDealer()) {
			this.joueur1.setDealer(false);
			this.joueur2.setDealer(true);
		} else if (this.joueur2.isDealer()) {
			this.joueur1.setDealer(true);
			this.joueur2.setDealer(false);
		}
	}

	@Override
	public String toString() {
		return "Partie [joueur1=" + joueur1 + ", joueur2=" + joueur2 + ", decalage=" + decalage + ", board="
				+ Arrays.toString(board) + ", nBCarteOnBoard=" + nBCarteOnBoard + ", idPartie=" + idPartie + "]";
	}

	public Carte[] getBoard() {
		return this.board;
	}
	

	public void InitManche(Joueur J, Boolean gagnant) {
		
		System.out.println(this);
		
		this.changerDealer();
		
		//On vide le board
		for (int i=0; i<4; i++) {
			this.board[i] = null;
		}
		this.nBCarteOnBoard = 0;
		
		//On enlève les mises
		if ((J == this.joueur2 && !gagnant) || (J == this.joueur1 && gagnant)) {
			System.out.println("Le joueur gagnant est le joueur " + this.joueur1.getPseudo());
			
			//Enlever les mises
			this.joueur1.setStack(this.joueur1.getStack() + this.joueur2.getMise() + this.joueur1.getMise(), this.idPartie);
			this.joueur1.deleteMise();
			this.joueur2.deleteMise();
		} else {
			System.out.println("Le joueur gagnant est le joueur " + this.joueur2.getPseudo());
			
			//Enlever les mises
			this.joueur2.setStack(this.joueur2.getStack() + this.joueur1.getMise() + this.joueur2.getMise(), this.idPartie);
			this.joueur2.deleteMise();
			this.joueur1.deleteMise();
		}
		
		//On eleve les mains
		this.joueur1.setCarteMain1(null);
		this.joueur1.setCarteMain2(null);
		this.joueur2.setCarteMain1(null);
		this.joueur2.setCarteMain2(null);
		
		//Si c'est la fin de la partie, on ajoute à l'historique la fin
		if (historique && (this.joueur1.getStack() == 0 || this.joueur2.getStack() == 0)) {
			//Ajout a historique
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Coups(ID_Action, ID_Partie) values('9','"+this.idPartie+"')");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public String getIdPartie() {
		return this.idPartie;
	}

}
