/*
 * Title: MakeFolders
 * Author: Matthew Boyette
 * Date: 11/12/2012
 * 
 * This is a simple program to take plain-text files with folder names on each line and then automatically generate the folders.
 */

import java.io.File;
import java.util.Scanner;

import api.util.Support;

public class MakeFolders
{
	public final static void main(final String[] args)
	{
		new MakeFolders(args);
	}
	
	private boolean	isDebugging	= false;
	
	public MakeFolders(final String[] args)
	{
		this.setDebugging(Support.promptDebugMode(null));
		
		if (args.length > 0)
		{
			for (String arg: args)
			{
				this.readFile(arg);
			}
		}
		else
		{
			this.readFile("");
		}
	}
	
	public final boolean isDebugging()
	{
		return this.isDebugging;
	}
	
	protected final void makeFolder(final String folderName)
	{
		File folder = new File(folderName);
		folder.mkdir();
	}
	
	protected final void readFile(final String filePath)
	{
		String newFilePath = new String(filePath.toCharArray());
		
		if ((newFilePath == null) || newFilePath.isEmpty())
		{
			newFilePath = Support.getFilePath(null, true, this.isDebugging());
		}
		
		if ((newFilePath == null) || newFilePath.isEmpty())
		{
			// Either the user has canceled the file operation or something has gone wrong; abort!
			return;
		}
		
		Scanner inputStream = null; // Stream object for file input.
		
		try
		{
			// Initialize file stream. If the given path is invalid, an exception is thrown.
			inputStream = new Scanner(new File(newFilePath));
			
			while (inputStream.hasNextLine())
			{
				// Get the next line in the original file.
				String line = inputStream.nextLine().trim();
				this.makeFolder(line);
			}
		}
		catch (final Exception exception)
		{
			// Handle the exception by alerting the user of the error and then terminating the program.
			// Exit code is non-zero indicating abnormal termination.
			Support.displayException(null, exception, true);
		}
		finally
		{
			if (inputStream != null)
			{
				// Close the input stream.
				inputStream.close();
				inputStream = null;
			}
		}
	}
	
	public final void setDebugging(final boolean isDebugging)
	{
		this.isDebugging = isDebugging;
	}
}