public interface Banco
{
	public int createAccount(float amount) throws InvalidAccountException;
	public float closeAccount(int nib) throws InvalidAccountException;
	public int debit(int nib, float amount) throws NotEnoughCoinException;
	public int deposit(int nib, float amount);
	public float balance(int nib);
}