import java.sql.Statement;

public class PartieDecale extends Partie {
	
	public PartieDecale (Joueur joueur1, Joueur joueur2, int decalage, String idPartie, Boolean historique) {
		super(joueur1, joueur2, decalage, idPartie, historique);
		this.historique = false;
		this.board = new Carte[5];
		this.nBCarteOnBoard = 0;
	}
	
	public void DistribuerCarte(Joueur J, Carte c1) throws Exception {
		
		if (J.getCarteMain1() == null) {
			J.setCarteMain1(c1);
		} else {
			J.setCarteMain2(c1);
		}
		
	}
	
	
	public void AddOnBoard(Carte carte) throws Exception {
		
		this.board[this.nBCarteOnBoard] = carte;
		this.nBCarteOnBoard++;

		if (historique) {
			//Ajout a historique
			try{
				Statement stat = CmdSql.GererBD();
				stat.executeUpdate("insert into Coups(ID_Action, ID_Partie, ID_Carte) values('4','"+this.idPartie+"','"+carte.getID()+"')");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

}
