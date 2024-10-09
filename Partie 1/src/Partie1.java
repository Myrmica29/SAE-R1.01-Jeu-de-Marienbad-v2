/**
 * 
 * 
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
				a = SimpleInput.getInt("Saisie de la ligne sur laquelle vous voulez retirer des allumettes (1=ligne 1, ...):") - 1;
			}while(a<0 || a>=n || sticks[a] == 0); // 0 <= a < n
			do{
				b = SimpleInput.getInt("Nombre d'allumettes que vous voulez enlever:");
			}while(b<=0 || b>sticks[a]); // 0 < b <= sticks[a]
			
			// Mise à jour du tableau de batons
			updateSticks(sticks, a, b);
			
			// Mise à jour de la condition de continuation de la boucle
			run = continueGame(sticks);
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
	 * 
	 * @param sticks
	 */
	void displaySticks(int[] sticks){
		
	}
	
	/**
	 * 
	 * @param sticks
	 * @param a
	 * @param b
	 */
	int[] updateSticks(int[] sticks, int a, int b){
		int [] t = {};
		return t;
	}
	
	/**
	 * Check if it is the end of the game by checking if there is no stick left on any lines
	 * @param sticks the tables containing the number of sticks per lines
	 * @return true if it's not the end of the game
	 */
	boolean continueGame(int[] sticks){
		return false;
	}
}
