import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class MainRejouer {

	public static void main(String[] args) {
		String idPartie = "3";
		int decalage = 0;
		try{
			Statement stat = CmdSql.GererBD();
			String cmd = new String("SELECT Decalage FROM Partie WHERE Partie.ID_Partie = '"+idPartie+"';");
			decalage = CmdSql.RecupererDecalage(cmd);

		}catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("La partie commence dans " + decalage + " secondes.");
		
		try {
			Thread.sleep(decalage*1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		rejouer(idPartie);
	}
	
	public static void rejouer(String idPartie) {
		
		System.out.println("Go !");
		
		String debutpartie = null;
		int indice = 0;
		
		//Création des objets Joueurs
		Joueur J1 = null;
		Joueur J2 = null;
		try{
			Statement stat = CmdSql.GererBD();
			String cmd = new String("SELECT Pseudo FROM Joueur, Partie WHERE Joueur.ID_Joueur = Partie.ID_Joueur1 AND Partie.ID_Partie = '"+idPartie+"';");
			J1 = CmdSql.RecupererJoueurs(cmd, "1");

		}catch (Exception e){
			e.printStackTrace();
		}
		
		try{
			Statement stat = CmdSql.GererBD();
			String cmd = new String("SELECT Pseudo FROM Joueur, Partie WHERE Joueur.ID_Joueur = Partie.ID_Joueur2 AND Partie.ID_Partie = '"+idPartie+"';");
			J2 = CmdSql.RecupererJoueurs(cmd, "2");

		}catch (Exception e){
			e.printStackTrace();
		}
		
		//Création de l'objet Partie
		PartieDecale p = new PartieDecale(J1, J2, 0, idPartie, false);
		
		//Liste des actions
		LinkedList<Action> actions = new LinkedList<>();
		
		//Récupération de l'indice de la premiere action
		try{
			Statement stat = CmdSql.GererBD();
			String cmd = new String("SELECT ID_Coup FROM Coups WHERE ID_Partie = '" + idPartie + "';");
			indice = CmdSql.RecupererIndice(cmd);

		}catch (Exception e){
			e.printStackTrace();
		}
		
		//Récupération du timestamp du début de la partie
		try{
			Statement stat = CmdSql.GererBD();
			String cmd = new String("SELECT Heure_Debut FROM Partie WHERE ID_Partie = '" + idPartie + "';");
			debutpartie = CmdSql.RecupererDebutPartie(cmd);

		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		String lasttimestamp = debutpartie;
		String idAction = "0";
		Action a = null;
		
		//Récupération de la première action
		try{
			Statement stat = CmdSql.GererBD();
			String cmd = new String("SELECT * FROM Coups WHERE ID_Coup = '" + indice + "';");
			a = CmdSql.RecupererCoupsParActions(cmd);

		}catch (Exception e){
			e.printStackTrace();
		}
		
		//for(Action a: actions) {
		while (!idAction.equals("9")) {
			Joueur j = null;
			Joueur adverse = null;
			
			try {
				Thread.sleep(compareTimestamp(lasttimestamp, a.getDate()));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(compareTimestamp(lasttimestamp, a.getDate()));
			
			lasttimestamp = a.getDate();
			
			System.out.println(a.getID_Action());
			System.out.println(p);
			
			switch(a.getID_Action()) {
				case "1":
					if (J1.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J1;
					} else if (J2.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J2;
					} else {
						System.out.println("ERROR: Cannot find ID_Joueur");
					}
					
					j.mise(Integer.parseInt(a.getMontant()), idPartie);
					break;
				case "2":
					if (J1.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J1;
					} else if (J2.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J2;
					} else {
						System.out.println("ERROR: Cannot find ID_Joueur");
					}
					
					j.check(idPartie);
					break;
				case "3":
					if (J1.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J1;
					} else if (J2.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J2;
					} else {
						System.out.println("ERROR: Cannot find ID_Joueur");
					}
					
					j.seCouche(p);
					break;
				case "4":
					try {
						p.AddOnBoard(new Carte(a.getID_Carte(), a.getID_Carte()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "5":
					if (J1.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J1;
					} else if (J2.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J2;
					} else {
						System.out.println("ERROR: Cannot find ID_Joueur");
					}
					
					try {
						p.DistribuerCarte(j, new Carte(a.getID_Carte(), a.getID_Carte()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "6":
					if (J1.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J1;
						adverse = J2;
					} else if (J2.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J2;
						adverse = J1;
					} else {
						System.out.println("ERROR: Cannot find ID_Joueur");
					}
					
					j.aligner(adverse, idPartie);
					break;
				case "7":
					if (J1.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J1;
					} else if (J2.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J2;
					} else {
						System.out.println("ERROR: Cannot find ID_Joueur");
					}
					
					j.setStack(Integer.parseInt(a.getMontant()), idPartie);
					break;
				case "8":
					if (J1.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J1;
					} else if (J2.getId() == Integer.parseInt(a.getID_Joueur())) {
						j = J2;
					} else {
						System.out.println("ERROR: Cannot find ID_Joueur");
					}
					
					j.gagne(p);
					break;
				default:
					System.out.println("ERROR: Cannot parse action");
			}
			
			
			//Récupération de l'action suivante
			try{
				Statement stat = CmdSql.GererBD();
				String cmd = new String("SELECT * FROM Coups WHERE ID_Coup = '" + indice + "';");
				a = CmdSql.RecupererCoupsParActions(cmd);
				
				indice++;

			}catch (Exception e){
				e.printStackTrace();
			}
			
			//Sécurité
			if (a == null) {
				break;
			}
			
			idAction = a.getID_Action();
			
		}
				
		System.out.println(p);
		System.out.println(J1);
		System.out.println(J2);
		
	}
	
	public static int compareTimestamp(String t1, String t2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    Date parsedDate = null;
	    Date parsedDate2 = null;
		try {
			parsedDate = dateFormat.parse(t1);
			parsedDate2 = dateFormat.parse(t2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int) (parsedDate2.getTime() - parsedDate.getTime());
	}

}
