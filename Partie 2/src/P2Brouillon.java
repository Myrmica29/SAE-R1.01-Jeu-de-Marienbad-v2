/**
 * Jeu de Marienbad en joueur contre IA
 * @author Tristan & Mélanie
 */
 
class P2Brouillon{
	void principal(){
		int[] tab = generateSticks(5);
		int[][] tabBin = sticksInBinary(tab);
		System.out.println("Tableau du jeu:");
		System.out.println(displayTab(tab));
		
		System.out.println("Matrice:");
		for (int i=0; i<tabBin.length;i++){
			System.out.println(displayTab(tabBin[i]));
		}
		System.out.println("Somme des colonnes:");
		int[] sum = sumColumns(tabBin);
		System.out.println(displayTab(sum));
		
		System.out.println("Marquage des nombres impairs:");
		int[] tabMarque = marqueurImpairs(sum);
		System.out.println(displayTab(tabMarque));
		
		System.out.println("Ligne a modifier : ");
		int ligne = ligneImpair(tabBin, tabMarque);
		System.out.println(ligne);
		
		System.out.println("Test Convertie en decimal : ");
		int nb = toDecimal(new int[] {1,0,0,1,0});
		System.out.println(nb);
		
		System.out.println("Modifie tableau : ");
		modifMatrice(tabBin, tabMarque, tab);
		System.out.println(displayTab(tab));
		
		tabBin = sticksInBinary(tab);
		System.out.println("Matrice:");
		for (int i=0; i<tabBin.length;i++){
			System.out.println(displayTab(tabBin[i]));
		}
		
		System.out.println("/");
		testSumColumns();
		testMarqueurImpairs();
		testToBinaryTab();
		testTabDim2Iden();
		testSticksInBinary();
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
	 * @param la matrice dont on veut faire la somme des colonnes
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
	 * 
	 * 
	 */
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
	 * convertie un binaire (sous forme de tableau) en decimal
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
	
	// Importée pour les tests
	void updateSticks(int[] sticks, int a, int b){
		sticks[a] = sticks[a] - b;
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
				res += "{";
				if (t[i].length > 0){
					for (int j=0; j<t.length-1; j++){
						res += t[i][j] + ",";
					}
					res += t[i][t.length-1];
				}
				res += "}";
				if (i!=t.length-1){
					res += ",";
				}
			}
		}
		res += "}";
		return res;
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
}
