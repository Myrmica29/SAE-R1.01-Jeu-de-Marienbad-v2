/**
 * Jeu de Marienbad
 * @author Tristan & Melanie
 */

class Partie1{
	void principal(){
		
		test(); 
		
		// Saisie du nom du joueur qui joue en 1er
		String joueur1 = SimpleInput.getString("Nom du joueur 1 : ");
		
		// Saisie du nom du joueur qui joue en 2ème
		String joueur2 = SimpleInput.getString("Nom du joueur 2 : ");
		while ( egalString(joueur1, joueur2)){
			System.out.println("Le nom du joueur 2 doit etre different de celui du joueur 1");
			joueur2 = SimpleInput.getString("Nom du joueur 2 : ");
		}
		
		// Saisie du nombre de lignes
		int n = SimpleInput.getInt("Nombre de lignes (entre 2 et 15): ");
		while(n<2 || n>15){
			System.out.println("Nombre de lignes invalide");
			n = SimpleInput.getInt("Nombre de lignes (entre 2 et 15): ");
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
	
	
	void test(){
		System.out.println("**** TEST ****");
		
		testContinueGame();
		testEgalString();
		testGenerateSticks();
		
		System.out.println();
		System.out.println("**************");
		System.out.println();
	}
		
	/**
	 * test si deux  chaine de caractere sont identique 
	 * @param s1 premiere chaine de caractere
	 * @param s2 deuxieme chaine de caractere
	 * @return true si les deux chaine sont identique
	 */
	boolean egalString( String s1, String s2){
		boolean equal = true;
		if (s1.length() != s2.length()){
			equal = false;
		}else {
			int i = 0;
			while (i<s1.length() ){
				if (s1.charAt(i) != s2.charAt(i) ){
					equal = false;
				}
				i ++;
			}
		}
		return equal;
	}
	/**
	 * Test la méthode egalString()
	 */
	void testEgalString(){
		System.out.println();
		System.out.println("*** testEgalString() ***");
		testCasEgalString("test","test", true);
		testCasEgalString("test","Test", false);
		testCasEgalString("test","test ", false);
		testCasEgalString("test","abc", false);
	}
	
	/**
	 * Teste un appel de egalDiff()
	 * @param s1 chaine de caractere
	 * @param s2 chaine de caractere
	 * @param result résultat attendu
	 */
	void testCasEgalString(String s1, String s2, boolean result){
		//Affichage
		System.out.print("egalString(" + s1+ ", " +s2+ ")\t= " + result + "\t : ");
		//Appel
		boolean resExec = egalString(s1, s2);
		//Vérification
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
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
	 * Test la méthode egalString()
	 */
	void testGenerateSticks(){
		System.out.println();
		System.out.println("*** testGenerateSticks() ***");
		
		testCasGenerateSticks(2, new int [] {1,3});
		testCasGenerateSticks(6, new int [] {1,3,5,7,9,11});
		testCasGenerateSticks(15, new int [] {1,3,5,7,9,11,13,15,17,19,21,23,25,27,29} );
	}
	
	/**
	 * Teste un appel de generateSticks()
	 * @param n entier
	 * @param result résultat attendu
	 */
	void testCasGenerateSticks(int n, int [] res){
		//Affichage
		System.out.print("egalString(" + n+ ")\t= " + displayTab(res) + "\t : ");
		//Appel
		int [] resExec = generateSticks(n);
		//Vérification
		if (tabIden(res, resExec)){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	boolean tabIden( int [] t1, int [] t2){
		boolean res = true ;
		if (t1.length != t2.length ){
			res = false ;
		}else{
			for (int i = 0; i < t1.length; i++ ){
				if (t1[i] != t2[i]){
					res = false ;
				}
			}
		}
		return res; 
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
	 * Test la méthode updateSticks()
	 */
	void testUpdateSticks(){
		System.out.println();
		System.out.println("*** testUpdateSticks() ***");
		
		testCasUpdateSticks(new int [] {0,2,4,2}, 2, 1, new int [] {0,2,3,2});
		//testCasUpdateSticks();
		//testCasUpdateSticks();
		//testCasUpdateSticks();
	}
	
	/**
	 * Teste un appel de updateSticks()
	 * @param tab tableau d'entiers
	 * @param result résultat attendu
	 */
	void testCasUpdateSticks(int[] tab, int a, int b ,int [] result){
		//Affichage
		System.out.print("updateSticks(" + displayTab(tab) + ", " +a+ ", " +b+ ")\t= " + result + "\t : ");
		//Appel
		updateSticks(tab, a, b);
		//Vérification
		if (tab == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
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
		System.out.println("*** testContinueGame() ***");
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
