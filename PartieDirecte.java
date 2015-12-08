import java.sql.Statement;

import javax.smartcardio.CardTerminal;

public class PartieDirecte extends Partie{
	
	public PartieDirecte (Joueur joueur1, Joueur joueur2, int decalage, String idPartie, Boolean historique) {
		super(joueur1, joueur2, decalage, idPartie, historique);
		this.historique = true;
		this.board = new Carte[5];
		this.nBCarteOnBoard = 0;
	}
	
	public void DistribuerCarte(Joueur J1, Joueur J2, CardTerminal terminal) throws Exception {
		
		//Distribution avec Ajout a historique
		try{
			Statement stat = CmdSql.GererBD();
			Carte carte;
			
			if(J2.isDealer()) {
				carte = Display.getCarte(terminal);
				J1.setCarteMain1(carte);
				if (historique) {
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, ID_Carte) values('5','"+J1.getId()+"','"+this.idPartie+"','"+carte.getID()+"')");
				}
				carte = Display.getCarte(terminal);
				J2.setCarteMain1(carte);
				if (historique) {
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, ID_Carte) values('5','"+J2.getId()+"','"+this.idPartie+"','"+carte.getID()+"')");
				}
				carte = Display.getCarte(terminal);
				J1.setCarteMain2(carte);
				if (historique) {
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, ID_Carte) values('5','"+J1.getId()+"','"+this.idPartie+"','"+carte.getID()+"')");
				}
				carte = Display.getCarte(terminal);
				J2.setCarteMain2(carte);
				if (historique) {
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, ID_Carte) values('5','"+J2.getId()+"','"+this.idPartie+"','"+carte.getID()+"')");
				}
			}
			if(J1.isDealer()) {
				carte = Display.getCarte(terminal);
				J2.setCarteMain1(carte);
				if (historique) {
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, ID_Carte) values('5','"+J2.getId()+"','"+this.idPartie+"','"+carte.getID()+"')");
				}
				carte = Display.getCarte(terminal);
				J1.setCarteMain1(carte);
				if (historique) {
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, ID_Carte) values('5','"+J1.getId()+"','"+this.idPartie+"','"+carte.getID()+"')");
				}
				carte = Display.getCarte(terminal);
				J2.setCarteMain2(carte);
				if (historique) {
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, ID_Carte) values('5','"+J2.getId()+"','"+this.idPartie+"','"+carte.getID()+"')");
				}
				carte = Display.getCarte(terminal);
				J1.setCarteMain2(carte);
				if (historique) {
				stat.executeUpdate("insert into Coups(ID_Action, ID_Joueur, ID_Partie, ID_Carte) values('5','"+J1.getId()+"','"+this.idPartie+"','"+carte.getID()+"')");
				}
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void AddOnBoard(CardTerminal terminal) throws Exception {
		Carte carte = Display.getCarte(terminal);
		
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
