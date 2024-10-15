/**
 * Jeu de Marienbad en joueur contre IA
 * @author Tristan & Mélanie
 */
 
class P2Brouillon{
	void principal(){
		int[] tab = generateSticks(5);
		int[][] tabBin = sticksInBinary(tab);
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
		
		System.out.println("Ligne a modifie : ");
		int ligne = ligneImpair(tabBin, tabMarque);
		System.out.println(ligne);
		
		System.out.println("Convertie en decimal : ");
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
	
	
	
	void updateSticks(int[] sticks, int a, int b){
		sticks[a] = sticks[a] - b;
	}
	
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
}
