package xplatform.util.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WindowsProcess
{
    private static String processName;
    public static void kill(String processName) throws Exception
    {
    	WindowsProcess.processName = processName;
        if (isRunning())
        {
            getRuntime().exec("taskkill /F /IM " + processName);
            Thread.sleep(1000);
        }
    }

    private static boolean isRunning() throws Exception
    {
        Process listTasksProcess = getRuntime().exec("tasklist");
        BufferedReader tasksListReader = new BufferedReader(
                new InputStreamReader(listTasksProcess.getInputStream()));

        String tasksLine;

        while ((tasksLine = tasksListReader.readLine()) != null)
        {
            if (tasksLine.contains(processName))
            {
                return true;
            }
        }

        return false;
    }

    private static Runtime getRuntime()
    {
        return Runtime.getRuntime();
    }
}
