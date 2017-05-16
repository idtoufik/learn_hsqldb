package fr.learn.compilation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Compilation {

	public Compilation() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static BufferedReader getOutput(Process p) {
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	private static BufferedReader getError(Process p) {
		return new BufferedReader(new InputStreamReader(p.getErrorStream()));
	}

	public String compile(String cammand){
		String log="";
		try {
			Process p1 = Runtime.getRuntime().exec(cammand);

			BufferedReader error = getError(p1);
			String ligne = null;

			//log+="\n......\n";
			boolean erreur=false;
			while ((ligne = error.readLine()) != null) {
				log+=ligne;
				erreur=true;
				log+="\n";
			}
			if(erreur==false) log+="successful";

			p1.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println(" \n ------- Fin de la compilation  ------- ");
		return log;
	}
	
	public String run(String execution){	
		String resultat = "";
		try{
			Process p2 = Runtime.getRuntime().exec(execution);
			BufferedReader output = getOutput(p2);
			String ligne =null;
			while ((ligne = output.readLine()) != null) {
				System.out.println(ligne);
				resultat += ligne+"\n";
			}
			p2.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return resultat;
	}

}