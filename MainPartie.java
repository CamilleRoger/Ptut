import javax.smartcardio.CardTerminal;

public class MainPartie {
	
	static CardTerminal terminal = CmdCard.GetTerminal();// Get ACR 122U
	
	public static void main(String[] args) {
		
		// Attention: A chaque action ne pas oublier de l'enregister dans la BDD => table historique
		
		Joueur J1 = new Joueur("Joueur 1", null, null, false, true);
		Joueur J2 = new Joueur("Joueur 2", null, null, false, true);
		PartieDirecte p = new PartieDirecte(J1, J2, 10, null, true);
		
		// NE PAS LANCER DE PARTIE SI IL N'Y A PAS DE CARTE DANS LA BDD
		
		J1.setStack(1000, p.getIdPartie());
		J2.setStack(1000, p.getIdPartie());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Ajout des blindes
		J1.mise(5, p.getIdPartie());
		J2.mise(10, p.getIdPartie());
		
		J1.aligner(J2, p.getIdPartie());
		J2.check(p.getIdPartie());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			p.DistribuerCarte(J1, J2, terminal);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Partie crée, Pots initialisés et cartes distribués:");
		System.out.println(J1);
		System.out.println(J2);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			p.AddOnBoard(terminal);
			p.AddOnBoard(terminal);
			p.AddOnBoard(terminal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("");
		System.out.println("Cartes ajoutés sur le Board:");
		System.out.println(p);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		J1.mise(20, p.getIdPartie());
		J2.aligner(J1, p.getIdPartie());
		
		System.out.println("");
		System.out.println("Mise puis aligner:");
		System.out.println(J1);
		System.out.println(J2);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		J1.gagne(p);
		
		
		
		
		
		
		
		
		//Ajout des blindes
		J1.mise(5, p.getIdPartie());
		J2.mise(10, p.getIdPartie());
		
		J1.aligner(J2, p.getIdPartie());
		J2.check(p.getIdPartie());
		
		try {
			p.DistribuerCarte(J1, J2, terminal);

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Partie crée, Pots initialisés et cartes distribués:");
		System.out.println(J1);
		System.out.println(J2);
		
		try {
			p.AddOnBoard(terminal);
			p.AddOnBoard(terminal);
			p.AddOnBoard(terminal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("");
		System.out.println("Cartes ajoutés sur le Board:");
		System.out.println(p);
		
		J1.mise(960, p.getIdPartie());
		J2.aligner(J1, p.getIdPartie());
		
		System.out.println("");
		System.out.println("Mise puis aligner:");
		System.out.println(J1);
		System.out.println(J2);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		J1.gagne(p);
		
		System.out.println("");
		System.out.println("Fin de partie:");
		System.out.println(p);
		System.out.println(J1);
		System.out.println(J2);
		// Dealer bien changé ? Board vide ?
		
		
		try {
			//p.AddOnBoard(terminal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			//p.AddOnBoard(terminal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Attention au d�calage � l'affichage !!

	}

}
