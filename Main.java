import java.util.Scanner;

import javax.smartcardio.*;

public class Main {

	private static int choix ;
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		CardTerminal terminal = CmdCard.GetTerminal();// Get ACR 122U

		System.out.println("Bienvenue");
		System.out.println();
		do {
			System.out.println("1) Enregistrer paquet");
			System.out.println("2) Afficher carte");
			System.out.println("3) Supprimer base de donnée"); // a enlever par la suite
			System.out.println("4) Creer base de donnée"); // a enlever par la suite

			choix = scan.nextInt();
			switch (choix){
			case 1://Enregistrer paquet
				Record.record(terminal);
				break;
			case 2://Afficher
				Display.display(terminal);
				break;
			case 3://Gestion BD
				CmdSql.Connecter();
				CmdSql.SupprimerBD();
				System.out.println("La base de donnée a bien été supprimer.");
				System.out.println();
				break;
			case 4://Gestion BD
				CmdSql.Connecter();
				CmdSql.CreerBD();
				System.out.println("La base de donnée a bien été creer.");
				System.out.println();
				break;

			}
		}while(choix == 3);
	}


}
