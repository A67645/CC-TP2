import java.util.concurrent.locks.*;
import java.lang.Object;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class NotEnoughCoinException extends Exception{

	public NotEnoughCoinException(String s){
		super(s);
	}
}

public class InvalidAccountException extends Exception{

	public InvalidAccountException(String s){
		super(s);
	}
}

public class Bank{
	private static class Account{
		private float balance;
		Lock l = new ReentrantLock();

		public Account(float n){
		l.lock();
		this.balance = n;
		l.unlock();
		}

		public int debit(float n) throws NotEnoughCoinException{
			l.lock();
			try{
				if(this.balance < n){
					throw new NotEnoughCoinException("Your are broke");
					return -1;
				}
				else{
					this.balance -= n;
					return 0;
				}
			}
			finally{
				l.unlock();
			}
		}

		public int deposit(float n){
			l.lock();
			this.balance += n;
			l.unlock();
			return 0;
		}

		public float balance(){
			l.lock();
			return this.balance;
			l.unlock();
		}
	}
	private HashMap<Integer, Account> accounts =  new HashMap<>();
	private int nextID = 0;
	private Lock l = new ReentrantLock();

	public Bank(int n){
		accounts = new HashMap<>(n);
		nextID = 0;
	}

	public int nAccounts(){
		l.lock();
		try{
			return accounts.size();
		}
		finally{
			l.unlock();
		}
	}

	public int createAccount(float initialBalance) throws InvalidAccountException{
		Account c = new Account(initialBalance);
		l.lock();
		try{
			if(c == null) throw new InvalidAccountException("Null Account Exception");
			accounts.put(nextID, c);
			nextID++;
			return 0;
		}
		finally{
			l.unlock();
		}
	}

	public float closeAccount(int nib) throws InvalidAccountException{
		l.lock();
		Account c;
		try{
			c = accounts.get(nib);
			if (c == null) throw new InvalidAccountException("Account does not exist");
			accounts.remove(nib);
			nextID--;
			c.l.lock();
			try{
				float value = c.balance();
				return value;
			}
			finally{
				c.l.unlock();
			}
		}
		finally{
			l.unlock();
		}
	}

	public int debit(int nib, float value) throws NotEnoughCoinException{
		l.lock();
		try{
			try{
				accounts.get(nib).debit(value);
				return 0;
			}
			catch (NotEnoughCoinException e){
				return -1;
			}
		}
		finally{
			l.unlock();
		}
	}

	public int deposit(int nib, float value){
		l.lock();
		try{
			return accounts.get(nib).deposit(value);
		}
		finally{
			l.unlock();
		}
	}

	public float balance(int nib){
		l.lock();
		try{
			return accounts.get(nib).balance();
		}
		finally{
			l.unlock();
		}
	}

	public void transfer(int nib_o, int nib_d, float value) throws NotEnoughCoinException{
		/*inserir reentrant locks*/
		l.lock();
		try{
			Account c_o = accounts.get(nib_o);
			c_o.l.lock();
			try{
			Account c_d = accounts.get(nib_d);
			c_d.l.lock();
				try{

					try{
						c_o.debit(value);
						c_d.deposit(value);
					}
					catch (NotEnoughCoinException e){
						System.err.println("NotEnoughCoinException on account " + nib_o);
					}
				}
				finally{
					c_d.l.unlock();
				}
			}
			finally{
				c_o.l.unlock();
			}

		}
		finally{
			l.unlock();
		}
	}

	public float totalBalance(int[] nibs) throws InvalidAccountException{
		float total_balance = 0;
		nibs = nibs.clone();
		Arrays.sort(nibs);
		Account[] a = new Account[nibs.length];
		l.lock();
		try{
			int n = nibs.length;
			for(int i = 0; i < n; i++){
				a[i] = accounts.get(nibs[i]);
				if(a[i] == null) throw new InvalidAccountException("Account " + nibs[i] + " does not exist");
			}
			for(Account c : a) c.l.lock();
			for(Account c : a){
				total_balance += c.balance();
				c.l.unlock();
			}
			return total_balance;

		}
		finally{
			l.unlock();
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
}
