/**
 * Jeu de Marienbad
 * @author Tristan & Mélanie
 */

class Partie2{
	void principal(){
		
		test(); 
		
		// Saisie du nombre de lignes
		int n = SimpleInput.getInt("Nombre de lignes (entre 2 et 15): ");
		while(n<2 || n>15){
			System.out.println("Nombre de lignes invalide");
			n = SimpleInput.getInt("Nombre de lignes (entre 2 et 15): ");
		} // n doit etre 2 et 15
		int[] sticks = generateSticks(n);
		
		// Saisie du nom du joueur qui joue en 1er
		String joueur1 = nomJoueur(1, "L'ordinateur"); 
		
		char jouer;
		do{
			jouer = SimpleInput.getChar("Voulez vous commencez ? (O/N) :  ");
		}while( jouer != 'o' && jouer != 'n' && jouer != 'N' && jouer != 'O');
		
		int turn;
		if ( jouer == 'o' || jouer == 'O'){
			turn = 1;
		}else{
			turn = 0;
		}
		
		// Variables utilisées dans la boucle du jeu
		int nbLigne ; // Stock le numéro de la ligne choisie par le joueur
		int nbAll ; // Stock le nombre d'allumettes à enlever
		int[][] tabBin ;
		int[] sum ;
		int[] tabMarque;
		
		boolean run = true; // Condition de continuation de la boucle du jeu
		
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
				
			}else{
				// Tour de l'ordinateur
				tabBin = sticksInBinary(sticks);
				sum = sumColumns(tabBin);
				tabMarque = marqueurImpairs(sum);
				nbLigne = ligneImpair(tabBin, tabMarque);
				nbAll = getSticksToRemove(tabBin, tabMarque, nbLigne);
				System.out.println("L'ordinateur joue a la ligne " +nbLigne+ " et retire " +nbAll+ " allumettes ");
			}
			
			// Mise à jour du nombre d'allumettes
			updateSticks(sticks, nbLigne, nbAll);
			
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
	 * test si nom du joueur est acceptable (non vide, sans espace et different de celui de l'adversaire)
	 * @param n numero du joueur
	 * @param adversaire nom deja pris par l'adversaire
	 * @return le nom du joueur si est il acceptable
	 */
	String nomJoueur(int n, String adversaire){
		boolean valide;
		String joueur ;
		do{
			valide = false ;
			if ( n == 1 ){
				joueur = SimpleInput.getString("Nom du joueur 1 (non vide et sans espace) : ");
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
			
				
		}while (!valide || (egalString(adversaire, joueur)));
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
	 * trouve le numero de ligne a modifier
	 * @param matrice tableau a deux dimensions contenant les ligne en binaire
	 * @param tabMarque tableau ou 1 signifie que la colonne est impaire
	 * @return le numero de ligne a modifier, -1 si aucune ne corresponds
	 */
	int ligneImpair(int[][] matrice, int[] tabMarque){
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
	 * Test la méthode ligneImpair()
	 */
	void testLigneImpair(){
		System.out.println();
		System.out.println("*** testLigneImpair() ***");
		testCasLigneImpair(new int[][] {{0,1,0},{0,0,1},{1,0,1},{1,0,0}}, new int[] {1,0,0}, 2);
		testCasLigneImpair(new int[][] {{1,1,0},{0,0,1},{1,0,1},{1,0,0}}, new int[] {1,0,0}, 0);
		testCasLigneImpair(new int[][] {{0,1,0},{0,0,1},{0,0,1},{1,0,0}}, new int[] {1,0,0}, 3);
		testCasLigneImpair(new int[][] {{0,0,0},{0,0,1},{0,1,0},{1,0,0}}, new int[] {0,1,0}, 2);
		testCasLigneImpair(new int[][] {{0,0,0},{0,0,1},{0,1,0},{1,0,0}}, new int[] {0,0,1}, 1);
		testCasLigneImpair(new int[][] {{0,0,0},{0,0,1},{0,1,0},{1,0,0}}, new int[] {0,1,1}, 2);
		testCasLigneImpair(new int[][] {{0,0,0},{0,0,1},{0,1,0},{1,0,0}}, new int[] {1,1,1}, 3);
		testCasLigneImpair(new int[][] {{0,0},{0,1},{1,0}}, new int[] {0,1}, 1);
	}
	
	/**
	 * Teste un appel de ligneImpair()
	 * @param matrice un tableau de tableaux qui représentent des nombres en binaires (chaque case est une puissance de 2)
	 * @param tabMarque tableau qui marque par des 1 les indices qui correspondant aux indices des colonnes de la matrice dont la somme est impair
	 * @param result résultat attendu
	 */
	void testCasLigneImpair(int[][] matrice, int[] tabMarque, int result){
		//Affichage
		System.out.print("ligneImpair(" + displayTabDim2(matrice) + "," + displayTab(tabMarque) + ")\t= " + result + "\t : ");
		//Appel
		int resExec = ligneImpair(matrice, tabMarque);
		//Vérification
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	
	/**
	 * convertie un binaire (sous forme de tableau) en decimal
	 * @param nb un tableau en binaire et chaque bit dans une case
	 * @return nombre en decimal
	 */
	 int toDecimal(int[] tab){
		 int [] bin = {16,8,4,2,1};
		 int res = 0;
		 for (int i = 0; i < tab.length ; i++){
			 if ( tab[i] == 1 ){
				 res += bin[i];
			}
		}
		return res;
	}
	
	/**
	 * Test la méthode toDecimal()
	 */
	void testToDecimal(){
		System.out.println();
		System.out.println("*** testToDecimal() ***");
		testCasToDecimal(new int[] {0,0,1,0,0}, 4);
		testCasToDecimal(new int[] {0,1,1,0,0}, 12);
		testCasToDecimal(new int[] {0,0,0,0,0}, 0);
		testCasToDecimal(new int[] {1,1,1,1,1}, 31);
	}
	
	/**
	 * Teste un appel de toDecimal()
	 * @param un tableau en binaire où chaque bit est dans une case
	 * @param result résultat attendu
	 */
	void testCasToDecimal(int[] tab, int result){
		//Affichage
		System.out.print("toDecimal(" + displayTab(tab) + ")\t= " + result + "\t : ");
		//Appel
		int resExec = toDecimal(tab);
		//Vérification
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	
	/**
	 * Renvoie le nombre de bâtons à retirer sur une ligne pour que la somme des colonnes des lignes converties en binaire ne donne que des nombres pairs
	 * @param matrice matrice un tableau de tableaux qui représentent des nombres en binaires (chaque case est une puissance de 2)
	 * @param tabMarque tableau qui marque par des 1 les indices qui correspondant aux indices des colonnes de la matrice dont la somme est impair
	 * @param l la ligne sur laquelle on doit
	 * @return le nombre d'allumettes à retirer.
	 */
	int getSticksToRemove(int[][] matrice, int[] tabMarque, int l){
		int[] ligne = matrice[l];
		int sticksAvant = toDecimal(ligne);
		for (int i=0; i<tabMarque.length; i++){
			if (tabMarque[i] == 1){
				ligne[i] = (ligne[i]+1)%2;
			}
		}
		int sticksApres = toDecimal(ligne);
		return sticksAvant - sticksApres;
	}
	
	/**
	 * Test la méthode getSticksToRemove()
	 */
	void testGetSticksToRemove(){
		System.out.println();
		System.out.println("*** testGetSticksToRemove() ***");
		testCasGetSticksToRemove(new int[][] {{0,0,0,0,1},{0,0,0,1,0},{0,0,0,0,0}}, new int[] {0,0,0,1,1}, 1, 1);
		testCasGetSticksToRemove(new int[][] {{0,0,0,0,1},{0,0,0,1,1},{0,0,0,0,0}}, new int[] {0,0,0,1,0}, 1, 2);
	}
	
	/**
	 * Teste un appel de getSticksToRemove()
	 * @param matrice un tableau de tableaux qui représentent des nombres en binaires (chaque case est une puissance de 2)
	 * @param tabMarque tableau qui marque par des 1 les indices qui correspondant aux indices des colonnes de la matrice dont la somme est impair
	 * @param l la ligne sur laquelle on doit
	 * @param result résultat attendu
	 */
	void testCasGetSticksToRemove(int[][] matrice, int[] tabMarque, int l, int result){
		//Affichage
		System.out.print("getSticksToRemove(" + displayTabDim2(matrice) + "," + displayTab(tabMarque) + "," + l + ")\t= " + result + "\t : ");
		//Appel
		int resExec = getSticksToRemove(matrice, tabMarque, l);
		//Vérification
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR" + resExec);
		}
	}
	
	
	/**
	 * convertie un decimal en nombre binaire
	 * @param nb un nombre entier inférieur strict à 32
	 * @return nb en binaire et chaque bit dans une case d'un tableau de longueur 5
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
	 * Test la méthode toBinaryTab()
	 */
	void testToBinaryTab(){
		System.out.println();
		System.out.println("*** testToBinaryTab()");
		testCasToBinaryTab(5,new int[] {0,0,1,0,1});
		testCasToBinaryTab(0,new int[] {0,0,0,0,0});
		testCasToBinaryTab(31,new int[] {1,1,1,1,1});
	}
	
	/**
	 * Teste un appel de toBinaryTab()
	 * @param nb nombre entier que l'on veut convertir en binaire
	 * @param result résultat attendu
	 */
	void testCasToBinaryTab(int nb, int[] result){
		//Affichage
		System.out.print("toBinaryTab(" + nb + ")\t= " + displayTab(result) + "\t : ");
		//Appel
		int[] resExec = toBinaryTab(nb);
		//Vérification
		if (tabIden(resExec, result)){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	
	/**
	 * convertie le nombre d'allumettes de chaques lignes dans un tableau en binaire dans un tableau
	 * @param sticks tableau du nombre d'alluemtte par ligne
	 * @return un tableau a deux dimension avec chaque nombre de sticks en binaire dans un tableau
	 */
	int[][] sticksInBinary(int[] sticks){
		int[][] binSticks = new int[sticks.length][];
		for (int i=0;i<sticks.length;i++){
			binSticks[i] = toBinaryTab(sticks[i]);
		}
		return binSticks;
	}
	
	/**
	 * Test la méthode sticksInBinary()
	 */
	void testSticksInBinary(){
		System.out.println();
		System.out.println("*** testSticksInBinary()");
		testCasSticksInBinary(new int[] {1,3,5},new int[][] {{0,0,0,0,1},{0,0,0,1,1},{0,0,1,0,1}});
		testCasSticksInBinary(new int[] {0,0,0},new int[][] {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}});
		testCasSticksInBinary(new int[] {0,31,0},new int[][] {{0,0,0,0,0},{1,1,1,1,1},{0,0,0,0,0}});
	}
	
	/**
	 * Test un appel de sticksInBinary()
	 * @param nb nombre entier que l'on veut convertir en binaire
	 * @param result résultat attendu
	 */
	void testCasSticksInBinary(int[] tab, int[][] result){
		//Affichage
		System.out.print("sticksInBinary(" + displayTab(tab) + ")\t= " + displayTabDim2(result) + "\t : ");
		//Appel
		int[][] resExec = sticksInBinary(tab);
		//Vérification
		if (tabDim2Iden(resExec, result)){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	
	/**
	 * Fait le somme des colonnes d'une matrice représentée par un tableau de dimension 2.
	 * @param la matrice dont on veut faire la somme des colonnes, un tableau de tableaux non vides et de mêmes longueurs
	 * @return un tableau contenant la somme de chaque colonne, avec à l'indice i la somme de la colonne d'indice i.
	 */
	int[] sumColumns(int[][] matrice){
		int[] sum = new int[matrice[0].length];
		for (int i=0;i<matrice.length;i++){
			for (int j=0;j<matrice[i].length;j++){
				sum[j] += matrice[i][j];
			}
		}
		return sum;
	}
	
	/**
	 * Test la méthode sumColumns()
	 */
	void testSumColumns(){
		System.out.println();
		System.out.println("*** testSumColumns()");
		testCasSumColumns(new int[][] {{1,0,2},{1,0,3},{1,0,4}},new int[] {3,0,9});
		testCasSumColumns(new int[][] {{0,0,0},{0,0,0},{0,0,0}},new int[] {0,0,0});
		testCasSumColumns(new int[][] {{0,0},{0,0},{1,2}},new int[] {1,2});
		testCasSumColumns(new int[][] {{2},{3}},new int[] {5});
	}
	
	/**
	 * Test un appel de sumColumns()
	 * @param matrice tableau de dimension 2 d'entiers
	 * @param result résultat attendu
	 */
	void testCasSumColumns(int[][] matrice, int[] result){
		//Affichage
		System.out.print("sumColumns(" + displayTabDim2(matrice) + ")\t= " + displayTab(result) + "\t : ");
		//Appel
		int[] resExec = sumColumns(matrice);
		//Vérification
		if (tabIden(resExec, result)){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}
	
	
	/**
	 * Marque les nombres impairs d'un tableau en renvoyant un tableau dans 
	 * lequel il y a un 1 aux indices où il y a des nombres impairs, et des 0 sinon.
	 * @param tab tableau d'entiers
	 * @return un tableau de 0 et de 1, avec les 1 positionnés aux indices où il y a des nombres impairs dans le tableau initial
	 */
	int[] marqueurImpairs(int[] tab){
		int[] tabMarque = new int[tab.length];
		for (int i=0; i<tab.length;i++){
			if (tab[i]%2 == 1){
				tabMarque[i] = 1;
			}
		}
		return tabMarque;
	}
	
	/**
	 * Test la méthode marqueurImpairs()
	 */
	void testMarqueurImpairs(){
		System.out.println();
		System.out.println("*** testMarqueurImpairs()");
		testCasMarqueurImpairs(new int[] {3,2,9,4,0},new int[] {1,0,1,0,0});
		testCasMarqueurImpairs(new int[] {1,3,5,7,9},new int[] {1,1,1,1,1});
		testCasMarqueurImpairs(new int[] {2,4,6,8,10},new int[] {0,0,0,0,0});
		testCasMarqueurImpairs(new int[] {0,0,0,0,0},new int[] {0,0,0,0,0});
		testCasMarqueurImpairs(new int[] {2,3,1},new int[] {0,1,1});
		testCasMarqueurImpairs(new int[] {},new int[] {});
	}
	
	/**
	 * Test un appel de marqueurImpairs()
	 * @param tab tableau d'entiers
	 * @param result résultat attendu
	 */
	void testCasMarqueurImpairs(int[] tab, int[] result){
		//Affichage
		System.out.print("marqueurImpairs(" + displayTab(tab) + ")\t= " + displayTab(result) + "\t : ");
		//Appel
		int[] resExec = marqueurImpairs(tab);
		//Vérification
		if (tabIden(resExec, result)){
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
	 * Teste un appel de tabIden()
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
	 * verifie si deux tableaux de dimension 2 sont identiques
	 * @param t1 premier tableau d'entier de dimension 2
	 * @param t2 deuxieme tableau d'entier de dimension 2
	 * @return vrai ssi les deux tableaux sont identiques
	 */
	boolean tabDim2Iden( int[][] t1, int[][] t2){
		boolean res = true ;
		if (t1.length != t2.length ){
			res = false ;
		}else{
			for (int i = 0; i < t1.length; i++ ){
				if (t1[i].length != t2[i].length ){
					res = false ;
				}else{
					if (!tabIden(t1[i], t2[i])){
						res = false ;
					}
				}
			}
		}
		return res; 
	}
	
	/**
	 * Test la méthode tabDim2Iden()
	 */
	void testTabDim2Iden(){
		System.out.println();
		System.out.println("*** testTabDim2Iden()");
		testCasTabDim2Iden(new int[][] {{1,0,2},{1,0,3},{1,0,4}},new int[][] {{1,0,2},{1,0,3},{1,0,4}}, true);
		testCasTabDim2Iden(new int[][] {{1,0,1},{1,0,3},{1,0,4}},new int[][] {{1,0,2},{1,0,3},{1,0,4}}, false);
		testCasTabDim2Iden(new int[][] {{1,0,4},{1,0,3},{1,0,2}},new int[][] {{1,0,2},{1,0,3},{1,0,4}}, false);
		testCasTabDim2Iden(new int[][] {{},{},{}},new int[][] {{},{},{}}, true);
		testCasTabDim2Iden(new int[][] {},new int[][] {}, true);
		testCasTabDim2Iden(new int[][] {{1,2},{2,3}},new int[][] {{1,2},{2,3},{3,4}}, false);
	}
	
	/**
	 * Test un appel de tabDim2Iden()
	 * @param tab tableau d'entiers
	 * @param result résultat attendu
	 */
	void testCasTabDim2Iden(int[][] tab1, int[][] tab2, boolean result){
		//Affichage
		System.out.print("tabDim2Iden(" + displayTabDim2(tab1) + "," + displayTabDim2(tab2) + ")\t= " + result + "\t : ");
		//Appel
		boolean resExec = tabDim2Iden(tab1, tab2);
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
	
	/**
	 * Affiche un tableau de dimension 2
	 * @param t tableau de dimension 2
	 * @return une chaine de caractère qui représente le tableau t
	 */
	String displayTabDim2(int[][] t){
		String res = "{";
		if (t.length > 0){
			for (int i=0; i<t.length; i++){
				res += displayTab(t[i]);
				if (i!=t.length-1){
					res += ",";
				}
			}
		}
		res += "}";
		return res;
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
		testToBinaryTab();
		testSticksInBinary();
		testSumColumns();
		testMarqueurImpairs();
		testTabIden();
		testTabDim2Iden();
		testLigneImpair();
		testToDecimal();
		testGetSticksToRemove();
		
		System.out.println();
		System.out.println("**************");
		System.out.println();
	}

}
