import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class GimpWrapper {

	private static final String GIMP_PATH = "\"C:\\Program Files\\GIMP 2\\bin\\gimp-console-2.8.exe\"";
	private static final String BATCH_FLAG = "-b";
	private static final String COMMAND_NAME = "create-collage";
	private static final String QUIT_COMMAND = "\"(gimp-quit 0)\"";

	public static boolean ExecuteCommand(String inDirectory,
			String outDirectory, String picFormat, int numRows, int numCols,
			int numCollages) {

		String[] command = FormatCommand(inDirectory, outDirectory, picFormat,
				numRows, numCols, numCollages);

		//String result = ExecuteShellCommand(command);
		String result = ExecuteShellCommand(command);
		System.out.println("RESULT - " + result);
		return !result.isEmpty();

	}

	private static String[] FormatCommand(String inDirectory,
			String outDirectory, String picFormat, int numRows, int numCols,
			int numCollages) {
		String[] command = new String[5];
		String inPics = inDirectory + "\\*." + picFormat;
		inPics = inPics.replace("\\", "\\\\");
		outDirectory = outDirectory.replace("\\", "\\\\");
		inPics = "\\\"" + inPics + "\\\"";
		outDirectory = "\\\"" + outDirectory + "\\\"";
		command[0] = GIMP_PATH;
		command[1] = BATCH_FLAG; 
		command[2] = "\"(" + COMMAND_NAME + " " + inPics + " " + outDirectory + " " + numRows + " "
				+ numCols + " " + numCollages +  ")\"";
		command[3] =  BATCH_FLAG;
		command[4] = QUIT_COMMAND;
		return command;

	}

	// based off code from:
	// http://www.xyzws.com/javafaq/how-to-run-external-programs-by-using-java-processbuilder-class/189
	private static String ExecuteShellCommand(String[] command) {
 
		ProcessBuilder probuilder = new ProcessBuilder( command );
        //You can set up your work directory
        //probuilder.directory(new File("c:\\xyzwsdemo"));
        
        Process process;
		try {
			process = probuilder.start();
			 //Read out dir output
	        InputStream is = process.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);
	        BufferedReader br = new BufferedReader(isr);
	        String line;
	        System.out.printf("Output of running %s is:\n",
	                Arrays.toString(command));
	        while ((line = br.readLine()) != null) {
	            System.out.println(line);
	        }
	        
	        int exitValue = process.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
            
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
            e.printStackTrace();
        }
 
		return "";
	}

}
