import java.awt.Component;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

public class MakeFolders
{
	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			for (int i = 0; i < args.length; i++)
			{
				readFile(args[i]);
			}
		}
		else
		{
			readFile(null);
		}
	}
	
	private static String getDateTimeStamp()
	{
		/*
			This method was constructed after searching for simple custom date/time formatting.
			Its only downside is that the Date class is deprecated, and may become unavailable
			in the future. I am in the process of working on a better alternative.
		*/
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy hh:mm:ss a z");
		return dateFormat.format(new Date());
	}
	
	private static String getFilePath(boolean isOpen)
	{
		JFileChooser fileDialog = new JFileChooser();
		boolean      stopFlag   = false;
		String       filePath   = null;
		int          choice     = 0;

		do // Loop while stopFlag equals false, post-test.
		{
			if (isOpen)
			{
				choice = fileDialog.showOpenDialog(null);
			}
			else
			{
				choice = fileDialog.showSaveDialog(null);
			}

			switch (choice)
			{
			case JFileChooser.APPROVE_OPTION:

				try
				{
					filePath = fileDialog.getSelectedFile().getCanonicalPath();
					stopFlag = true;
				}
				catch (Exception  exception)
				{
					filePath = null;
					stopFlag = false;
				}
				break;

			case JFileChooser.CANCEL_OPTION:

				filePath = null;
				stopFlag = true;
				break;

			default:

				filePath = null;
				stopFlag = false;
				break;
			}
		}
		while (stopFlag == false);

		return filePath;
	}
	
	private static void handleException(Component parent, Exception exception)
	{
		/*
			Report error message, complete with some useful debug info.
			Source file is where the error chain ended, which could be null in the case of a function in the Java API.
			Cause file is where the error chain began, which is the bottom of the stack and where the bad method is likely to be.
		*/
		JOptionPane.showMessageDialog(parent,
			exception.toString() + 
			"\n\nSource file: " + exception.getStackTrace()[0].getFileName() +
			"\nLine number: " + exception.getStackTrace()[0].getLineNumber() +
			"\n\nCause file: " + exception.getStackTrace()[exception.getStackTrace().length-1].getFileName() +
			"\nLine number: " + exception.getStackTrace()[exception.getStackTrace().length-1].getLineNumber() +
			"\n\nWhen: " + getDateTimeStamp(),
			"Unhandled Exception",
			JOptionPane.ERROR_MESSAGE);
		exception.printStackTrace();
	}
	
	private static void makeFolder(String folderName)
	{
		File folder = new File(folderName);
		folder.mkdir();
	}
	
	private static void readFile(String filePath)
	{
		if ((filePath == null) || filePath.isEmpty())
		{
			filePath = getFilePath(true);
		}
		
		if ((filePath == null) || filePath.isEmpty())
		{
			// User has canceled the file operation; abort!
			return;
		}
		
		Scanner inputStream = null; // Stream object for file input.
		
		try
		{
			// Initialize file stream. If the given path is invalid, an exception is thrown.
			inputStream = new Scanner(new File(filePath));
			
			while (inputStream.hasNextLine())
			{
				// Get the next line in the original file.
				String line = inputStream.nextLine().trim();
				makeFolder(line);
			}
		}
		catch(Exception exception)
		{
			// Handle the exception by alerting the user of the error and then terminating the program. Exit code is non-zero indicating abnormal termination.
			handleException(null, exception);
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
}
