/**
 * Jeu de Marienbad
 * @author Tristan & Melanie
 */

class Partie1{
	void principal(){
		
		// Saisie du nom du joueur qui joue en 1er
		String joueur1 = SimpleInput.getString("Nom du joueur 1:");
		
		// Saisie du nom du joueur qui joue en 2ème
		String joueur2 = SimpleInput.getString("Nom du joueur 2:");
		
		// Saisie du nombre de lignes
		int n = SimpleInput.getInt("Nombre de lignes (entre 2 et 15):");
		while(n<2 || n>15){
			System.out.println("Nombre de lignes invalide");
			n = SimpleInput.getInt("Nombre de lignes (entre 2 et 15):");
		} // n doit etre 2 et 15
		int[] sticks = generateSticks(n);
		
		int turn = 1;
		boolean run = true;
		int a; // Variable utilisée dans la boucle pour stocker le numéro de la ligne choisie par le joueur
		int b; // Variable utilisée dans la boucle pour stocker le nombre d'allumettes à enlever
		
		while (run){
			
			//changement de joueur
			turn = (turn+1)%2;
			
			// Affichage du jeu
			displaySticks(sticks);
			
			// Affiche le nom du joueur qui joue
			if (turn == 0){
				System.out.println("C'est au tour de " + joueur1 + " de jouer !");
			}else{
				System.out.println("C'est au tour de " + joueur2 + " de jouer !");
			}
			
			// Saisie de la ligne et du nombre d'allumettes à enlever
			do{
				a = SimpleInput.getInt("Saisie de la ligne sur laquelle vous voulez retirer des allumettes (0=ligne 0, ...):");
			}while(a<0 || a>=n || sticks[a] == 0); // 0 <= a < n
			do{
				b = SimpleInput.getInt("Nombre d'allumettes que vous voulez enlever:");
			}while(b<=0 || b>sticks[a]); // 0 < b <= sticks[a]
			
			// Mise à jour du tableau de batons
			updateSticks(sticks, a, b);
			
			// Mise à jour de la condition de continuation de la boucle
			run = continueGame(sticks);
		}
		
		if (turn == 0){
			System.out.println(joueur1 + " a gagné !!!");
		}else{
			System.out.println(joueur2 + " a gagné !!!");
		}
	}
	
	/**
	 * creer un tableau contenant le nombre initial d'allumette en debut de partie
	 * @param n nombre de ligne
	 * @return stick tableau, des lignes d'allumette
	 */
	int[] generateSticks(int n){
		int[] stick = new int[n];
		for ( int i = 0; i <n ; i ++){
			stick[i] = 1 + i*2;
		}
		return stick;
	}
	
	/**
	 * Affiche les allumettes
	 * @param sticks tableau contenant le nombre d'allumettes par lignes
	 */
	void displaySticks(int[] sticks){
		for (int i=0; i<sticks.length;i++){
			System.out.print(i+" : ");
			for (int j=0; j<sticks[i];j++){
				System.out.print("|");
			}
			System.out.println(" (" +sticks[i]+ ")");
		}
	}
	
	/**
	 * actualise le tableau des allumetttes
	 * @param sticks tableau d'entiers contenant le nombre d'allumettes par ligne
	 * @param a ligne a modifié
	 * @param b nombre d'allumettes à retirer
	 */
	void updateSticks(int[] sticks, int a, int b){
		sticks[a] = sticks[a] - b;
	}
	
	/**
	 * Test si c'est la fin du jeu en regardant si il n'y a plus aucune allumettes sur aucune lignes
	 * @param sticks tableau d'entiers contenant le nombre d'allumettes par ligne
	 * @return true si ce n'est pas la fin du jeu
	 */
	boolean continueGame(int[] sticks){
		boolean res = false;
		for (int i=0; i<sticks.length; i++){
			if (sticks[i]>0){
				res = true;
			}
		}
		return res;
	}
	
	/**
	 * Test la méthode continueGame()
	 */
	void testContinueGame(){
		System.out.println();
		System.out.println("*** testContinueGame()");
		testCasContinueGame(new int[]{1,3,5}, true);
		testCasContinueGame(new int[]{0,1,2}, true);
		testCasContinueGame(new int[]{2,1,0}, true);
		testCasContinueGame(new int[]{0,0,0}, false);
		testCasContinueGame(new int[]{0}, false);
	}
	
	/**
	 * Teste un appel de continueGame()
	 * @param tab tableau d'entiers
	 * @param result résultat attendu
	 */
	void testCasContinueGame(int[] tab, boolean result){
		//Affichage
		System.out.print("continueGame(" + displayTab(tab) + ")\t= " + result + "\t : ");
		//Appel
		boolean resExec = continueGame(tab);
		//Vérification
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	/**
	 * Affiche un tableau d'entiers
	 * @param t tableau d'entiers
	 * @return une chaine de caractère qui représente le tableau t
	 */
	String displayTab(int[] t){
		String res = "{";
		if (t.length > 0){
			for (int i=0; i<t.length-1; i++){
				res += t[i] + ",";
			}
			res += t[t.length-1];
		}
		res += "}";
		return res;
	}
}
