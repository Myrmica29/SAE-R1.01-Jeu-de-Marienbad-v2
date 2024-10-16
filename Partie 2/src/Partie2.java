/**
 * Jeu de Marienbad
 * @author Tristan & Mélanie
 */

class Partie2{
	void principal(){
		
		test(); 
		
		String joueur1;
		String joueur2 ;
		int n;
		int [] sticks ;
		int turn ;
		boolean run;
		int nbLigne ; // Variable utilisée dans la boucle pour stocker le numéro de la ligne choisie par le joueur
		int nbAll ; // Variable utilisée dans la boucle pour stocker le nombre d'allumettes à enlever
		
		int[][] tabBin ;
		int[] sum ;
		int[] tabMarque;
		int ligne;
		
		
		// Saisie du nombre de lignes
		n = SimpleInput.getInt("Nombre de lignes (entre 2 et 15): ");
		while(n<2 || n>15){
			System.out.println("Nombre de lignes invalide");
			n = SimpleInput.getInt("Nombre de lignes (entre 2 et 15): ");
		} // n doit etre 2 et 15
		sticks = generateSticks(n);
		
		
		// Saisie du nom du joueur qui joue en 1er
		joueur1 = nomJoueur(1, ""); 
		
		char jouer ;
		do{
			jouer = SimpleInput.getChar("Voulez vous commencez ? (O/N) :  ");
		}while( jouer != 'o' && jouer != 'n' && jouer != 'N' && jouer != 'O');
		
		if ( jouer == 'o' || jouer == 'O'){
			turn = 1;
		}else{
			turn = 0;
		}
		
		run = true;
		
		
		while (run){
			
			//changement de joueur
			turn = (turn+1)%2;
			
			// Affichage du jeu
			displaySticks(sticks);
			
			// Affiche le nom du joueur qui joue
			if (turn == 0){
				System.out.println("C'est au tour de " + joueur1 + " de jouer !");
				
				// Saisie de la ligne et du nombre d'allumettes à enlever
				do{
					nbLigne = SimpleInput.getInt("Saisie de la ligne sur laquelle vous voulez retirer des allumettes (0=ligne 0, ...):");
				}while(nbLigne<0 || nbLigne >=n || sticks[nbLigne] == 0); // 0 <= nbLIgne < n
				do{
					nbAll = SimpleInput.getInt("Nombre d'allumettes que vous voulez enlever:");
				}while(nbAll <=0 || nbAll>sticks[nbLigne]); // 0 < nbAll <= sticks[nbLigne]
				
				updateSticks(sticks, nbLigne, nbAll);
				
				
			}else{
				tabBin = sticksInBinary(sticks);
				sum = sumColumns(tabBin);
				tabMarque = marqueurImpairs(sum);
				ligne = ligneImpair(tabBin, tabMarque);
				modifMatrice(tabBin, tabMarque, sticks);
			}
			
			
			
			
			
			// Mise à jour de la condition de continuation de la boucle
			run = continueGame(sticks);
		}
		
		System.out.println();
		displaySticks(sticks);
		if (turn == 0){
			System.out.println(joueur1 + " a gagne !!!");
		}else{
			System.out.println("L'ordinateur a gagne !!!");
		}
	}
	
	
	
	
	/**
	 * test si nom du joueur est acceptable (non vide, sans espace et different de celui de ladversaire)
	 * @param n numero du joueur
	 * @param adversaire nom deja pris par l'adversaire
	 * @return le nom du joueur si est il acceptable
	 */
	String nomJoueur(int n, String adversaire){
		boolean valide = false;
		String joueur = adversaire;
		while (!valide || (egalString(adversaire, joueur))){
			valide = false ;
			if ( n == 1 ){
				joueur = SimpleInput.getString("Nom du joueur 1 (non vide et sans espace) : ");
				System.out.println("test");
			}else {
				joueur = SimpleInput.getString("Nom du joueur 2 (non vide, sans espace et different du joueur 1) : ");
			}
				
			if (joueur.length() != 0){
				int i = 0;
				while (i < joueur.length() && !valide ){
					if (joueur.charAt(i) == ' '){
						valide = false;
					}else if ( i == (joueur.length()-1) ){
						valide = true;
					}
					i ++;
				}
			}else {
				valide = false;
			}
				
		}
		
		return joueur;
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
	 * cree un tableau contenant le nombre initial d'allumette en debut de partie
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
		System.out.println();
		for (int i=0; i<sticks.length;i++){
			System.out.print(i+" : ");
			for (int j=0; j<sticks[i];j++){
				System.out.print("|");
			}
			System.out.println(" (" +sticks[i]+ ")");
		}
		System.out.println();
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
	
	
	/**
	 * convertie un nombre en binaire
	 * @param nb un entier en decimal
	 * @return un tableau avec un bit par element
	 */
	int [] binaire(int nb){
		int [] bin = {16,8,4,2,1};
		int []res = new int [5];
		
		for (int i = 0; i < 5 ; i ++){
			if (nb >= bin[i] ){
				nb -= bin[i];
				res[i] = 1;
			}
		}
		
		return res;
	}
	

	
	/**
	 * trouve le numero de ligne a modifier
	 * @param matrice tableau a deux dimensions contenant les ligne en binaire
	 * @param tabMarque tableau ou 1 signifie que la colonne est impaire
	 * @return le numero de ligne a modifier, -1 si aucune ne corresponds
	 */
	int ligneImpair(int[][] matrice, int[] tabMarque ){
		int ligne = -1;
		int i = 0; // index parcours de somme
		while (i < tabMarque.length && ligne == -1){
			if (tabMarque[i] == 1 ){
				int j = 0; // index de parcours de stick
				while ( j < matrice.length && ligne == -1){
					if ( matrice[j][i] == 1 ){
						ligne = j;
					}
					j ++;
				}
			}
			i ++;
		}
		return ligne;
	}
	
	
	/**
	 * convertie un binaire en decimal
	 * @param nb un tableau en binaire et chaque bit dans une case
	 * @return nombre en decimal
	 */
	 int toDecimal(int[] nb){
		 int [] bin = {16,8,4,2,1};
		 int res = 0;
		 for (int i = 0; i < nb.length ; i++){
			 if ( nb[i] == 1 ){
				 res += bin[i];
			}
		}
		return res;
	}
	
	
	void modifMatrice( int [][] matrice, int []tabMarque, int[] tab){
		int ligne;
		int stick;
		ligne = ligneImpair(matrice, tabMarque);
		if ( ligne == -1 ){
			do{
				ligne = (int) Math.random()*(tab.length);
			}while (tab[ligne] == 0);
			
			do{
				stick = (int) Math.random()*(tab[ligne]);
			}while( stick == 0);
		}else{
			for (int i = 0; i < tabMarque.length; i++){
				if ( tabMarque[i] == 1 ) {
					if ( matrice[ligne][i] == 1){
						 matrice[ligne][i] = 0;
					}else{
						matrice[ligne][i] = 1;
					}
				}
			}
			stick = toDecimal(matrice[ligne]);
			stick = tab[ligne] - stick;
		}
		
		System.out.println("L'ordinateur joue a la ligne " +ligne+ " et retire " +stick+ " allumettes ");
		updateSticks(tab, ligne, stick);
	}
	
	/**
	 * convertie un decimal en binaire
	 * @param nb un nombre entier
	 * @return nb en binaire et chaque bit dans une case 
	 */
	int [] toBinaryTab(int nb){
		int [] bin = {16,8,4,2,1};
		int []res = new int [5];
		
		for (int i = 0; i < 5 ; i ++){
			if (nb >= bin[i] ){
				nb -= bin[i];
				res[i] = 1;
			}
		}
		
		return res;
	}
	
	
	/**
	 * met le nombre d'allumette de en binaire
	 * @param sticks tableau du nombre d'alluemtte par ligne
	 * @return un tableau a deux dimension avec chaque nombre de sticks dans un tableau en binaire
	 */
	int[][] sticksInBinary(int[] sticks){
		int[][] binSticks = new int[sticks.length][];
		for (int i=0;i<sticks.length;i++){
			binSticks[i] = toBinaryTab(sticks[i]);
		}
		return binSticks;
	}
	
	int[] sumColumns(int[][] matrice){
		int[] sum = new int[matrice[0].length];
		for (int i=0;i<matrice.length;i++){
			for (int j=0;j<matrice[i].length;j++){
				sum[j] += matrice[i][j];
			}
		}
		return sum;
	}
	
	int[] marqueurImpairs(int[] tab){
		int[] tabMarque = new int[tab.length];
		for (int i=0; i<tab.length;i++){
			if (tab[i]%2 == 1){
				tabMarque[i] = 1;
			}
		}
		return tabMarque;
	}
	///////////////fonction test
	/**
	 * Affiche tous les tests de fonction
	 */
	void test(){
		System.out.println("**** TEST ****");
		
		testEgalString();
		testGenerateSticks();
		testUpdateSticks();
		testContinueGame();
		
		System.out.println();
		System.out.println("**************");
		System.out.println();
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
	 * Teste un appel de egalString()
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
	 * Test la méthode generateSticks()
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
		System.out.print("generateSticks(" + n+ ")\t= " + displayTab(res) + "\t : ");
		//Appel
		int [] resExec = generateSticks(n);
		//Vérification
		if (tabIden(res, resExec)){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	/**
	 * Test la méthode updateSticks()
	 */
	void testUpdateSticks(){
		System.out.println();
		System.out.println("*** testUpdateSticks() ***");
		
		testCasUpdateSticks(new int [] {0,2,4,2}, 2, 1, new int [] {0,2,3,2});
		testCasUpdateSticks(new int [] {1,3,5,7,9}, 0, 1, new int [] {0,3,5,7,9});
		testCasUpdateSticks(new int [] {1,3,5,7,9}, 4,9, new int [] {1,3,5,7,0});
	}
	
	/**
	 * Teste un appel de updateSticks()
	 * @param tab tableau d'entiers
	 * @param result résultat attendu
	 */
	void testCasUpdateSticks(int[] tab, int a, int b ,int [] result){
		//Affichage
		System.out.print("updateSticks(" + displayTab(tab) + ", " +a+ ", " +b+ ")\t= " + displayTab(result) + "\t : ");
		//Appel
		updateSticks(tab, a, b);
		//Vérification
		if (tabIden(result, tab)){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	/**
	 * verifie si deux tableaux sont identiques
	 * @param t1 premier tableau d'entier
	 * @param t2 deuxieme tableau d'entier
	 * @return vrai ssi les deux tableaux sont identiques
	 */
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
	 * Test la méthode tabIden()
	 */
	void testTabIden(){
		System.out.println();
		System.out.println("*** testUpdateSticks() ***");
		
		testCasTabIden(new int [] {0,2,4,2}, new int [] {0,2,4,2}, true);
		testCasTabIden(new int [] {1},new int [] {1}, true);
		testCasTabIden(new int [] {1,3,5,7,9}, new int [] {1,3,5,7,0}, false);
	}
	
	/**
	 * Teste un appel de updateSticks()
	 * @param tab1 premier tableau d'entiers
	 * @param tab2 deuxieme tableau d'entiers
	 * @param result résultat attendu
	 */
	void testCasTabIden(int[] tab1, int [] tab2,boolean result){
		//Affichage
		System.out.print("tabIden(" + displayTab(tab1) + ", " +displayTab(tab2)+ ")\t= " + result + "\t : ");
		//Appel
		boolean res = tabIden(tab1, tab2) ;
		//Vérification
		if (res == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
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
}
