package com.example.r_web.myapplication;

public class execute 
{
	public int r = 0;
	public String[] str;
	public String s;
	public int t = 0;
	public double reslut = 0;
	public double[] res;
	public double eval(String st) throws Exception
	{
		s=st;
		int i = 0;
		t = 0;
		reslut = 0;
		for(int j=0;j < s.length();j++)
			if(s.charAt(j)=='+' || s.charAt(j) == '-')		
				r++;
		str = new String[r+1];
		r = 0;
		for(i=0;i<s.length();i++)
		{
			if(s.charAt(i)=='+'){
				str[r] = s.substring(t, i);
				t=i;
				r++;}
			else if(s.charAt(i)=='-'){
				str[r] = s.substring(t,i);
				t=i;
				r++;
			}
		}
		str[r] = s.substring(t, s.length());
		res = new double[r+1];
		jisuan();
		reslut();
		return reslut;
	}
	
	private void reslut() {
		for(int j=0; j<res.length;j++)
			reslut += res[j];
		
		
	}

	private double jisuan() {
		for(int i=0; i < str.length;i++)
		{
			if(str[i].indexOf("+")!= -1 || str[i].indexOf("-")!=-1 ||str[i].indexOf("*")!=-1 || str[i].indexOf("/")!=-1)
				res[i] = Double.parseDouble(fenbu(str[i]));
			else
				res[i] = Double.parseDouble(str[i]);
		}
		return 0;
	}
	/**
	 * @param string
	 * @param i
	 * @param m
	 * @return
	 */
	private String fenbu(String string)
	{
		double number=0;
		t = 0;
		int j;
		for(j=0;j<string.length();j++)
		{	
			if(string.charAt(j)=='*' || string.charAt(j)=='/')
			{
				if(t==0){
					number = Double.parseDouble(string.substring(0, j));
					t = j;}
				else if(t != 0 && j+1<string.length()){
					if(string.charAt(t)=='*')number = number *  Double.parseDouble(string.substring(t+1, j));
					else number = number / Double.parseDouble(string.substring(t+1, j));
					t = j;
				}
			}
		}
		if(t==0) number = Double.parseDouble(string);
		if(t!=0 && string.charAt(t)=='*')
			number = number * Double.parseDouble(string.substring(t+1, string.length()));
		else if(t!=0&& string.charAt(t)=='/')
			number = number / Double.parseDouble(string.substring(t+1, string.length()));
		return number+"";
	}
}
