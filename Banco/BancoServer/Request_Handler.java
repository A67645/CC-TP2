public class Request_Handler
{
	String cmd;
	BancoImpl b;
	public Request_Handler(String cmd, BancoImpl b)
	{
		this.cmd = cmd;
		this.b = b;
	}

	public String handle_request()
    {
    	String[] t = this.cmd.split(";");
    	String op = t[0];
    	int nib = Integer.parseInt(t[1]);
    	float amount = Float.parseFloat(t[2]);
    	if(op == "createAccount")
    	{
    		try
    		{
    			String result = Integer.toString(this.b.createAccount(amount));
    			return result;
    		}
    		catch(InvalidAccountException e1){}

    	}
    	else if(op == "closeAccount")
    	{
    		try
    		{
    			String result = Float.toString(this.b.closeAccount(nib));
    			return result;
    		}
    		catch(InvalidAccountException e2){}
    	}
    	else if(op == "debit")
    	{
    		try
    		{
    			String result = Float.toString(this.b.debit(nib, amount)); 
    			return result;
    		}
    		catch(NotEnoughCoinException e3){}
    	}
    	else if(op == "deposit")
    	{
    		String result = Float.toString(this.b.deposit(nib, amount));
    		return result;
    	}
    	else if(op == "balance")
    	{
    		String result = Float.toString(this.b.balance(nib));
    		return result;
    	}
    	else
    	{
    		String result = "Operation Not Valid!";
    		return result;

    	}
    	return "";
    }
}