package TP1;
import java.io.*;

public class Lire {
	public static String S() // Lire un String
	{
		String tmp = "";
		char C = '\0';
		try
		{
			while ((C = (char) System.in.read()) != '\n')
			{
				if (C != '\r')
					tmp = tmp + C;

			}
		}
		catch (IOException e)
		{
			System.out.println("Erreur de frappe");
			System.exit(0);
		}
		return tmp;
	} // fin de S()

	public static byte b() // Lire un entier byte
	{
		byte x = 0;
		try
		{
			x = Byte.parseByte(S());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Format num�rique incorrect");
			System.exit(0);
		}
		return x;
	}

	public static short s() // Lire un entier short
	{
		short x = 0;
		try
		{
			x = Short.parseShort(S());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Format num�rique incorrect");
			System.exit(0);
		}
		return x;
	}
    // Lire un entier
	public static int i() 
	{
		int x = 0;
		try
		{
			x = Integer.parseInt(S());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Format num�rique incorrect");
			System.exit(0);
		}
		return x;
	}
    // Long
	public static long l() 
	{
		long x = 0;
		try
		{
			x = Integer.parseInt(S());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Format num�rique incorrect");
			System.exit(0);
		}
		return x;
	}

	public static double d() // Lire un double
	{
		double x = 0.0;
		try
		{
			x = Double.valueOf(S()).doubleValue();
		}
		catch (NumberFormatException e)
		{
			System.out.println("Format num�rique incorrect");
			System.exit(0);
		}
		return x;
	}
// float
	public static float f() 
	{
		float x = 0.0f;
		try
		{
			x = Double.valueOf(S()).floatValue();
		}
		catch (NumberFormatException e)
		{
			System.out.println("Format num�rique incorrect");
			System.exit(0);
		}
		return x;
	}
     // caractere
	public static char c() 
	{
		String tmp = S();
		if (tmp.length() == 0)
			return '\n';
		else
		{
			return tmp.charAt(0);
		}
	}

}
