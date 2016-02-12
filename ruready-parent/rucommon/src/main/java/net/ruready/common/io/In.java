package net.ruready.common.io;

/*************************************************************************
 * Compilation:  javac In.java
 *  Execution:    java In
 *  
 *  Reads in data of various types from: stdin, file, URL.
 *
 *  % java In
 *
 *************************************************************************/

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class In
{
	// ========================= CONSTANTS =================================

	/**
	 * A logger that helps identify this class' printouts.
	 */
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(In.class);

	// ========================= FIELDS ====================================

	private Scanner scanner;

	// for stdin
	public In()
	{
		scanner = new Scanner(System.in);
	}

	// for socket
	public In(Socket socket)
	{
		try
		{
			InputStream is = socket.getInputStream();
			scanner = new Scanner(is);
		}
		catch (IOException ioe)
		{
			System.err.println("Could not open " + socket);
		}
	}

	// for URLs
	public In(URL url)
	{
		try
		{
			URLConnection site = url.openConnection();
			InputStream is = site.getInputStream();
			scanner = new Scanner(is);
		}
		catch (IOException ioe)
		{
			System.err.println("Could not open " + url);
		}
	}

	// for files and web pages
	public In(String s)
	{

		try
		{
			// first try to read file from local file system
			File file = new File(s);
			if (file.exists())
			{
				scanner = new Scanner(file);
				return;
			}

			// next try for files included in jar
			URL url = getClass().getResource(s);

			// or URL from web
			if (url == null)
				url = new URL(s);

			URLConnection site = url.openConnection();
			InputStream is = site.getInputStream();
			scanner = new Scanner(is);
		}
		catch (IOException ioe)
		{
			System.err.println("Could not open " + s);
		}
	}

	// does input stream exists
	public boolean exists()
	{
		return scanner != null;
	}

	// return true if only whitespace left
	public boolean isEmpty()
	{
		return !scanner.hasNext();
	}

	// next line
	public boolean hasNextLine()
	{
		return scanner.hasNextLine();
	}

	public String readLine()
	{
		return scanner.nextLine();
	}

	// next character
	public char readChar()
	{
		// (?s) for DOTALL mode so . matches any character, including a line
		// termination character
		// 1 says look only one character ahead
		// consider precompiling the pattern
		String s = scanner.findWithinHorizon("(?s).", 1);
		return s.charAt(0);
	}

	// return rest of input as string
	public String readAll()
	{
		if (!scanner.hasNextLine())
			return null;

		// reference:
		// http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
		return scanner.useDelimiter("\\A").next();
	}

	// next string, int, double, long, byte, boolean
	public String readString()
	{
		return scanner.next();
	}

	public int readInt()
	{
		return scanner.nextInt();
	}

	public double readDouble()
	{
		return scanner.nextDouble();
	}

	public double readFloat()
	{
		return scanner.nextFloat();
	}

	public long readLong()
	{
		return scanner.nextLong();
	}

	public byte readByte()
	{
		return scanner.nextByte();
	}

	public boolean readBoolean()
	{
		return scanner.nextBoolean();
	}

	// close the scanner
	public void close()
	{
		scanner.close();
	}

	// for testing
	public static void main(String args[])
	{
		In in;
		String s;

		// read from a URL
		logger.debug("readAll() from a URL");
		logger.debug("----------------------------------");
		in = new In("http://www.cs.princeton.edu/IntroCS/24inout/InTest.txt");
		logger.debug(in.readAll());

		// read one line at a time from URL
		logger.debug("readLine() from a URL");
		logger.debug("----------------------------------");
		in = new In("http://www.cs.princeton.edu/IntroCS/24inout/InTest.txt");
		while (!in.isEmpty())
		{
			s = in.readLine();
			logger.debug(s);
		}
		logger.debug("");

		// read one string at a time from URL
		logger.debug("readString() from a URL");
		logger.debug("----------------------------------");
		in = new In("http://www.cs.princeton.edu/IntroCS/24inout/InTest.txt");
		while (!in.isEmpty())
		{
			s = in.readString();
			logger.debug(s);
		}
		logger.debug("");

		// read one line at a time from file in current directory
		logger.debug("readLine() from current directory");
		logger.debug("----------------------------------");
		in = new In("./InTest.txt");
		while (!in.isEmpty())
		{
			s = in.readLine();
			logger.debug(s);
		}
		logger.debug("");

		// read one line at a time from absolute path
		logger.debug("readLine() from absolute path");
		logger.debug("----------------------------------");
		in = new In("/n/fs/csweb/introcs/24inout/InTest.txt");
		// in = new In("G:\\www\\introcs\\24inout\\InTest.txt"); // Windows
		while (!in.isEmpty())
		{
			s = in.readLine();
			logger.debug(s);
		}
		logger.debug("");

		// read one line at a time from file using relative path
		logger.debug("readLine() from relative path");
		logger.debug("----------------------------------");
		in = new In("../24inout/InTest.txt");
		while (!in.isEmpty())
		{
			s = in.readLine();
			logger.debug(s);
		}
		logger.debug("");

		// read one char at a time
		logger.debug("readChar() from file");
		logger.debug("----------------------------------");
		in = new In("InTest.txt");
		while (!in.isEmpty())
		{
			char c = in.readChar();
			logger.debug(c);
		}
		logger.debug("");

	}

}
