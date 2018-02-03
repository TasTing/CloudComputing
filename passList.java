
public class passList {
	int password;
	
	public passList(int password){
		super();
		this.password = password;
	}

    public boolean passExistConfirm(int passIn){		
		if(this.password == passIn){
			return true;
		}
		else {
			return false;
		}
	}	
}
