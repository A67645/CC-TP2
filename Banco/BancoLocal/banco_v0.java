public class NotEnoughCoinExeption extends Exeption{
	
	public NotEnoughCoinExeption(String s){
		super(s);
	}
}

public class Account{
	private int balance;

	public Account(){
		this.balance = 0;
	}

	public synchronized int debit(int n){
		if(this.balance < n){
			return -1;
		}
		else{
			this.balance -= n;
			return 0;
		}
	}

	public synchronized int deposit(int n){
		this.balance += n;
		return 0;
	}

	public synchronized int balance(){
		return this.balance;
	}
}

public class Bank{
	private int Account[] bank;
	public Bank(int n){
		bank = new Account[n];
		for(int i = 0; i < n; i++){
			bank[i] = new Account();
		}
	}

	public int nAccounts(){
		return a.lenght;
	}

	public int debit(int nib, int value){
		if(bank[nib].debit(value) == 0){
			return 0;
		}
		else{
			return -1;
		}
	}

	public int deposit(int nib, int value){
		return bank[nib].deposit(value);
	}

	public int debit(int nib, int value){
		if(bank[nib].debit(value) == 0){
			return 0;
		}
		else{
			return -1;
		}
	}

	public int balance(int nib){
		return bank[nib].balance();
	}

	public void transfer(int nib_o, int nib_d, int value) throws NotEnoughCoinExeption{
		Account c_o = bank[nib_o];
		Account c_d = bank[nib_d];
		Account c1 = nib_o < nib_d ? c_o : c_d;
		Account c2 =  nib_o > nib_d ? c_d : c_o;
		synchronized(c1){
			synchronized(c2){
				if( c_o.debit(value) == 0){
						c_d.deposit(value);
					}
				else{
					throw NotEnoughCoinExeption("you are broke");
				}
			}
		}
	}

	public int total2(int nib1, int nib2){
		int r1, int r2;
		synchronized(bank[nib1]){
			synchronized(bank[nib2]){
				r1 =  bank[nib1].balance();
				r2 = bank[nib2].balance();
			}
		}
		return r1 + r2;
	}
}

public class TotalV2 extends Thread{
	privat Bank c;

	public TotalV2(Bank b){
		this.c = b;
	}

	public void run(){
		long c = 0;
		while(true){
			c += 1;
			int t = b.total2(0, 1);
			if(t != 0) System.out.println(t);
			if(t != 0 || c % 10000000 == 0) System.out.println("c = " + c);
		}
	}
}

public class TransfererV2 extends Thread{
	private Bank c;

	public TransfererV2(Bank b){
		this.c = b;
	}

	public void run(){
		Random r = new Random();
		while(true){
			int i = r.nextBoolean() ? 0 : 1;
			int j = 1 - i;
			b.transfer(i, j, 10);
		}
	}
}


public class Main{
	public static void main(String [] args){
		int n = parseInt(args[0]);
		int nt = parseInt(args[1]);

		Bank b = new Bank(n);

		BancoV0 c = new BancoV0(b);

		Thread[] a = new Thread(nt);


}
		

