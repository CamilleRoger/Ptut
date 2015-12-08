

import java.util.Scanner;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;


public class Display {
	static Scanner scan = new Scanner(System.in);
	static int choix ;

	public static void display(CardTerminal terminal)throws Exception {
		//System.out.println("Numero du paquet ?");

		do{
			System.out.println();
			System.out.println("Veuillez deposer votre carte");
			System.out.println();
			terminal.waitForCardPresent(1000000);

			if (terminal.isCardPresent()) {


				// Connecter la carte
				CardChannel channel = CmdCard.ConnecterCarte(terminal);

				// Recuperer RID

				String RID = CmdCard.GetRID(channel);
				String cmd = new String("select * from card where card.code="+"'"+RID+"'"+";");
				CmdSql.AfficherCard(cmd);

				//Saisie la carte dans la base de donn�e

				System.out.println();
				System.out.println("Voulez vous reesayer ?");
				terminal.waitForCardAbsent(1000000);

				//choix = scan.nextInt();
			}else{
				System.out.println("Pas de carte presente.");
			}
		}while(!terminal.isCardPresent());
	}


	/**
	 * RETURN: Le nom de la carte
	 */
	public static Carte getCarte(CardTerminal terminal) throws Exception {
		terminal.waitForCardAbsent(1000000);
		System.out.println();
		System.out.println("Veuillez deposer votre carte");
		System.out.println();
		terminal.waitForCardPresent(1000000);

		if (terminal.isCardPresent()) {


			// Connecter la carte
			CardChannel channel = CmdCard.ConnecterCarte(terminal);

			// Recuperer RID

			String RID = CmdCard.GetRID(channel);
			
			//Saisie la carte dans la base de donn�e
			
			return new Carte(RID, CmdSql.getCardName(RID));

			//choix = scan.nextInt();
		}else{
			System.out.println("Pas de carte presente.");
			return null;
		}

	}



}
