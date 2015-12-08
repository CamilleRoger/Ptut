import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

public class CmdCard {

	public static CardTerminal GetTerminal() {
		try{
			TerminalFactory factory = TerminalFactory.getDefault();
			List<CardTerminal> terminals = factory.terminals().list();
			//System.out.println("Terminals: " + terminals);
			CardTerminal terminal = terminals.get(0);
			return terminal;
		} catch(Exception erreur) {
			System.out.println("Erreur: " + erreur.toString());
			return null;
		}

	}

	
	public static CardChannel ConnecterCarte(CardTerminal terminal) {

		try{
			Card card = terminal.connect("T=1");
			//System.out.println("card: " + card);
			CardChannel channel = card.getBasicChannel();
			return channel;
		} catch(Exception erreur) {
			System.out.println("Erreur: " + erreur.toString());
			return null;
		}
	}





	public static String GetRID(CardChannel channel) {

		try{
			String R = new String();
			ResponseAPDU answer = channel.transmit(new CommandAPDU(0xFF, 0xB0, 0x00, 0x00, 0x10));//CLA:FF,INS:B0,P1:00,P2:1(first page to read),Le(length)4 ou autre a voir(du à la puce qu'on utilise)
			//System.out.println(answer);
			byte RID[] = answer.getData();
			
			//A GERER
			int erreur = answer.getSW();// recuperer 6300 si erreur 
			//System.out.println(erreur);
			if (erreur == 3600){
				return "erreur";
				//code erreur
			}
			
			for (int i=0; i<RID.length; i++){ 
				R += RID[i];
			}
			//System.out.println(R);

			return R;
		} catch(Exception erreur) {
			System.out.println("Erreur: " + erreur.toString());
			return null;
		}
	}




}
