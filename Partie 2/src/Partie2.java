class Partie2{
	void principal(){
		
	}
	
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
}
