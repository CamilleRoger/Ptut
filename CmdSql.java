

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;




public class CmdSql {
	static String tmp[] = new String[20];//gere les doublons, 53 car il y a 54 cartes
	static int error;//recupere si il y a une erreur
	static Connection data = Connecter();

	public static Connection Connecter()  {
		try{
			Class.forName("org.sqlite.JDBC");
			Connection data;
			data = DriverManager.getConnection("jdbc:sqlite:database.db"); //se connecte a la base de donn�e database.db
			return data;
		}catch(Exception e){
			return null;
		}
	}


	public static Statement GererBD() throws Exception {
		return data.createStatement();
	}

	public static void CreerBD() throws Exception {

		Statement stat = GererBD();

		stat.executeUpdate("create table if not exists Paquet ( "
				+ "`ID_Paquet`INTEGER PRIMARY KEY  AUTOINCREMENT ,"
				+ "`Nom` VARCHAR(25));");

		stat.executeUpdate("create table if not exists Joueur ("
				+ "`ID_Joueur` INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "`Pseudo`VARCHAR(255));");

		stat.executeUpdate("create table if not exists Action ("
				+ "`ID_Action` INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "`Nom_Action`VARCHAR(50));");

		stat.executeUpdate("create table if not exists Carte ("
				+ "`ID_Carte` PRIMARY KEY NOT NULL,"
				+ "`Nom` VARCHAR(20),"
				+ "`ID_Paquet`,"
				+ "FOREIGN KEY(ID_Paquet) REFERENCES Paquet(ID_Paquet));");

		stat.executeUpdate("create table if not exists Coups ("
				+ "`ID_Coup` INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "`Date` DATETIME DEFAULT (datetime('now','localtime')),"
				+ "`Montant`,"
				+ "`ID_Partie`,"
				+ "`ID_Action`,"
				+ "`ID_Joueur`,"
				+ "`ID_Carte`,"
				+ "FOREIGN KEY(ID_Action) REFERENCES Action(ID_Action),"
				+ "FOREIGN KEY(ID_Joueur) REFERENCES Joueur(ID_Joueur),"
				+ "FOREIGN KEY(ID_Carte) REFERENCES Carte(ID_Carte),"
				+ "FOREIGN KEY(ID_Partie) REFERENCES Partie(ID_Partie));");

		stat.executeUpdate("create table if not exists Partie ("
				+ "`ID_Partie` INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "`Heure_Debut` DATETIME DEFAULT (datetime('now','localtime')),"
				+ "`Decalage` INTEGER,"
				+ "`ID_Joueur1`,"
				+ "`ID_Joueur2`,"
				+ "FOREIGN KEY(ID_Joueur2) REFERENCES Joueur(ID_Joueur),"
				+ "FOREIGN KEY(ID_Partie) REFERENCES Partie(ID_Partie),"
				+ "FOREIGN KEY(ID_Joueur1) REFERENCES Joueur(ID_Joueur));");


		stat.executeUpdate("Insert into Action(Nom_Action) values('Miser') ");
		stat.executeUpdate("Insert into Action(Nom_Action) values('Check') ");
		stat.executeUpdate("Insert into Action(Nom_Action) values('Se Coucher') ");
		stat.executeUpdate("Insert into Action(Nom_Action) values('Ajouter au Board') ");
		stat.executeUpdate("Insert into Action(Nom_Action) values('Distribuer') ");
		stat.executeUpdate("Insert into Action(Nom_Action) values('Aligner') ");
		stat.executeUpdate("Insert into Action(Nom_Action) values('Stack') ");
		stat.executeUpdate("Insert into Action(Nom_Action) values('Gagner') ");
		stat.executeUpdate("Insert into Action(Nom_Action) values('Fin') ");

	}


	public static PreparedStatement PreparerCmdBD() throws Exception {
		PreparedStatement prep = data.prepareStatement("insert into Carte values (?, ?, ?);");
		return prep;
	}


	public static void SupprimerBD() throws Exception {
		Statement stat = data.createStatement();
		stat.executeUpdate("drop table if exists Action;");
		stat.executeUpdate("drop table if exists Coups;");
		stat.executeUpdate("drop table if exists Paquet;");
		stat.executeUpdate("drop table if exists Carte;");
		stat.executeUpdate("drop table if exists Joueur;");
		stat.executeUpdate("drop table if exists Partie;");

	}

	public static void AfficherCard(String cmd) throws Exception{

		//on affiche la carte que l'on vient de creer
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		while (rs.next()) {


			System.out.println("CARD = " + rs.getString("Nom"));
			System.out.println("CODE = " + rs.getString("ID_Carte"));
			System.out.println();

		}
		rs.close();
	}

	public static String RecupererPartie(String cmd) throws Exception{
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		String s = new String();
		rs.next();
		s=rs.getString("ID_Partie");
		rs.close();
		return s;
	}
	
	public static int RecupererJoueur(String cmd) throws Exception{
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		String s = new String();
		rs.next();
		s=rs.getString("ID_Joueur");
		rs.close();
		return Integer.parseInt(s);
	}
	
	public static int RecupererDecalage(String cmd) throws Exception{
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		String s = new String();
		rs.next();
		s=rs.getString("Decalage");
		rs.close();
		return Integer.parseInt(s);
	}
	
	public static int RecupererIndice(String cmd) throws Exception{
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		String s = new String();
		rs.next();
		s=rs.getString("ID_Coup");
		rs.close();
		return Integer.parseInt(s);
	}
	
	public static LinkedList<Action> RecupererCoups(String cmd) throws Exception{

		LinkedList<Action> actions = new LinkedList<>();
		
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		while (rs.next()) {
			
			Action a = new Action(rs.getString("Date"),rs.getString("Montant"), rs.getString("ID_Action"), rs.getString("ID_Joueur") , rs.getString("ID_Carte"));
			actions.add(a);

		}
		rs.close();
		
		return actions;
	}
	
	
	public static Action RecupererCoupsParActions(String cmd) throws Exception{
		
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		Action a = null;
		while (rs.next()) {
			
			a = new Action(rs.getString("Date"),rs.getString("Montant"), rs.getString("ID_Action"), rs.getString("ID_Joueur") , rs.getString("ID_Carte"));

		}
		rs.close();
		
		return a;
	}
	
	public static Joueur RecupererJoueurs(String cmd, String num) throws Exception{
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		rs.next();
		Joueur j = new Joueur(rs.getString("Pseudo"), null, null, false, false);
		rs.close();
		return j;
	}
	
	public static String RecupererDebutPartie(String cmd) throws Exception{
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery(cmd);
		String s = new String();
		rs.next();
		s=rs.getString("Heure_Debut");
		rs.close();
		return s;
	}

	public static String getCardName(String RID) throws Exception{
		Statement stat = data.createStatement();
		ResultSet rs= stat.executeQuery("select * from card where card.code="+"'"+RID+"'"+";");
		return rs.getString("Nom");

	}

}
