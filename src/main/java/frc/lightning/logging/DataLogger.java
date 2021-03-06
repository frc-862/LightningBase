package frc.lightning.logging;

import java.io.File;
import java.util.ArrayList;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import frc.lightning.http.DataLoggingWebSocket;
import frc.lightning.util.Loop;

public class DataLogger implements Loop {
    private static DataLogger logger;
    private static String baseFName = "data";

    private LogWriter writer;
    private ArrayList<String> fieldNames = new ArrayList<>();
    private ArrayList<DoubleSupplier> fieldValues = new ArrayList<>();
    private boolean first_time = true;
    private boolean preventNewElements = false;

    public static DataLogger getLogger() {
        if (logger == null) {
            logger = new DataLogger();
            logger.addElement("Timestamp", () -> Timer.getFPGATimestamp());
        }
        return logger;
    }

    public LogWriter getLogWriter() {
        return writer;
    }

    public static void addDataElement(String name, DoubleSupplier val) {
        DataLogger.getLogger().addElement(name, val);
    }

    public static void addDelayedDataElement(String name, DoubleSupplier val) {
        DataLogger.getLogger().addElement(name, new DataLoggerOutOfBand(val));
    }

    public void addElement(String name, DoubleSupplier val) {
        if (preventNewElements) {
            System.err.println("Unexpected call to addDataElement: " + name);
        } else {
            fieldNames.add(name);
            fieldValues.add(val);
        }
    }

    public void onStart() {
        if (first_time) {
            writeHeader();
            first_time = false;
            writer.flush();
        }
    }

    public void onStop() {
        writer.flush();
    }

    @SuppressWarnings("unchecked")
    public String getJSONHeader() {
        JSONArray result = new JSONArray();
        fieldNames.stream().forEach((val) -> result.add(val));
        return result.toString();
    }

    public String getHeader() {
        return fieldNames.stream().collect(Collectors.joining(","));
    }

    private void writeHeader() {
        writer.logRawString(getHeader());
    }

    private void writeValues() {
        String valueList = fieldValues.parallelStream()
                           .map(fn -> Double.toString(fn.getAsDouble()))
                           .collect(Collectors.joining(","));

        // System.out.println(valueList);
        writer.logRawString(valueList);
        DataLoggingWebSocket.broadcast("[" + valueList + "]");
    }

    private DataLogger() {
        File file = logFileName();
        writer = new LogWriter(file.getAbsolutePath());
    }

    public void checkBaseFileName() {
        var ds = DriverStation.getInstance();
        String newName = String.format("%s-%s-%d.log",
                                       ds.getEventName(), ds.getMatchType().toString(), ds.getMatchNumber()
                                      );

        if (newName != baseFName) {
            setBaseFileName(newName);
        }
    }

    public static void setBaseFileName(String fname) {
        baseFName = fname;
        getLogger().reset_file();
    }

    private File cachedLogFileName = null;
    private File logFileName() {
        if (cachedLogFileName != null)
            return cachedLogFileName;

        File base = null;

        // find the mount point
        char mount = 'u';
        while (base == null && mount <= 'z') {
            File f = new File("/" + mount);
            if (f.isDirectory()) {
                base = f;
            }
            ++mount;
        }

        if (base == null) {
            base = new File("/home/lvuser");
        }

        base = new File(base, "log");
        System.out.println("Log to " + base);

        //noinspection ResultOfMethodCallIgnored
        base.mkdirs();

        String name_format = baseFName + "-%05d-dl.log";
        int counter = 1;
        File result = new File(base, String.format(name_format, counter));
        while (result.exists()) {
            result = new File(base, String.format(name_format, ++counter));
        }
        System.out.println("Logging to " + result);

        cachedLogFileName = result;
        return result;
    }

    public void reset_file() {
        cachedLogFileName = null;
        flush();
        writer.setFileName(logFileName().getAbsolutePath());
        writeHeader();
    }

    public static void flush() {
        getLogger().writer.flush();
    }

    @Override
    public void onLoop() {
        writeValues();
    }

    public static void logData() {
        getLogger().writeValues();
    }

    public static void preventNewDataElements() {
        getLogger().lockItUp();
    }

    private void lockItUp() {
        logger.preventNewElements = true;
        onStart();
    }
}
