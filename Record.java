import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;

public class Record {
	static Scanner scan = new Scanner(System.in);
	public static void record(CardTerminal terminal) throws Exception{

		for (int i=0; i<20;i++){ // nb de cartes
			System.out.println();
			System.out.println("Veuillez deposer : "+ NameCard.values()[i].toString());
			System.out.println("");
			terminal.waitForCardPresent(1000000);

			if (terminal.isCardPresent()) {

				// Connecter la carte
				CardChannel channel = CmdCard.ConnecterCarte(terminal);

				// Recuperer RID
				String RID = CmdCard.GetRID(channel);
				//System.out.println(Arrays.toString(RID));
				if (RID.equals("erreur")){
					System.out.println("Erreur de communication");
					i--;

				}else{
					//Saisie la carte dans la base de donnée
					try{
						CmdSql.CreerBD();

						PreparedStatement prep = CmdSql.PreparerCmdBD();
								

						//on ajoute la carte dans la base de donnée
						
						prep.setString(1, (NameCard.values()[i]).toString());
						prep.setString(2, RID);
						prep.addBatch();

						//on l'enregistre
						prep.executeBatch();
						
						String cmd = new String("select * from card;");
						CmdSql.AfficherCard(cmd);



					}catch(SQLException sqle){
						System.out.println("Erreur : Veuillez recommencer.");
						System.out.println(sqle);
						i--;
					}
				}

				System.out.println();
				System.out.println("Veuillez retirer la carte.");
				terminal.waitForCardAbsent(1000000);
			}else{
				System.out.println("Pas de carte presente.");
			}


		}
	}

}
