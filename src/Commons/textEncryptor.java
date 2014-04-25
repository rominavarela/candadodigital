package Commons;

public class textEncryptor {
	
	private class BallNode
	{
		char original;
		char equivalent;
		BallNode next;
		BallNode back;
		
		public BallNode(char original)
		{
			this.original=original;
			this.equivalent=original;
			this.next=this;
			this.back=this;
		}
		
		public void add(BallNode node)
		{
			node.next=this.next;
			this.next.back=node;
			this.next=node;
			node.back=this;
		}
		
		public void restore()
		{
			this.equivalent=this.original;
		}
	}
	
	BallNode root;
	int[] char_values;
	char[] char_means;
	
	public textEncryptor()
	{
		char_values= new int['z'+1];
		char_means= new char['z'+1];
		for(int i=0;i<'Z';i++)
			char_values[i]=-1;
		
		root= new BallNode(' ');
		char_values[' ']=0;
		char_means[0]=' ';
		
		int num=1;
		for(int i='a'; i<='z';i++)
		{
			root.add(new BallNode((char)i));
			char_values[i]=num;
			char_means[num]=(char)i;
			num++;
		}
		for(int i='0'; i<='1';i++)
		{
			root.add(new BallNode((char)i));
			char_values[i]=num;
			char_means[num]=(char)i;
			num++;
		}
		for(int i='A'; i<='Z';i++)
		{
			root.add(new BallNode((char)i));
			char_values[i]=num;
			char_means[num]=(char)i;
			num++;
		}
		
	}
	
	public void setKey(String key)
	{
		BallNode node=root;
		
		/*
		 * restore
		 */
		while(true)
		{
			node.restore();
			node=node.next;
			if(node==root)
				break;
		}
		
		/*
		 * mix
		 */
		BallNode node_l;
		BallNode node_r=root.next;
		char temp;
		
		int value;
		for(int i=0;i<key.length();i++)
		{
			node_l=node_r;
			
			value=value(key.charAt(i));
			for(int ii=0;ii<value;ii++)
			{
				temp=node_l.equivalent;
				node_l.equivalent=node_r.equivalent;
				node_r.equivalent=temp;
				
				node_l=node_l.back;
				node_r=node_r.next;
			}
		}
	}
	
	public String close(String s)
	{
		String ans="";
		char c;
		int value;
		
		BallNode root_node=root;
		BallNode position_node=root;
		
		for(int i=0;i<s.length();i++)
		{
			c=s.charAt(i);
			value=value(c);
			
			if(value==-1)
				ans+=c;
			else
			{
				for(int ii=0;ii<value;ii++)
					position_node=position_node.next;
				
				value=value(position_node.equivalent);
				
				root_node=root;
				
				for(int ii=0;ii<value;ii++)
					root_node=root_node.next;
				
				ans+=root_node.original;
			}
		}
				
		return ans;
	}
	
	public String open(String s)
	{
		String ans="";
		char c;
		int value;
		int hoops;

		BallNode position_node=root;
		BallNode root_node=root;
		
		for(int i=0;i<s.length();i++)
		{
			c=s.charAt(i);
			value=value(c);
			
			if(value==-1)
				ans+=c;
			else
			{
				root_node=root;
				hoops=0;
				while(true)
				{
					if(root_node.original==c)
						break;
					root_node=root_node.next;
					hoops++;
				}
				
				value=0;
				while(true)
				{
					if(value(position_node.equivalent)==hoops)
						break;
					position_node=position_node.next;
					value++;
				}
				
				c=mean(value);
				ans+=c;
			}
		}
		
		return ans;
	}
	
	private int value(char c)
	{
		if(c>char_values.length)
			return -1;
		else
			return char_values[c];
	}
	
	private char mean(int index)
	{
		return char_means[index];	
	}
}
