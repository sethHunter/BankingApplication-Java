package backend;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BankLogger
{
    private static final BankLogger bankLogger = new BankLogger();
    private Logger logger = null;


    private BankLogger()
    {
        logger = Logger.getLogger("MyLog");
        logger.setUseParentHandlers(false);
        FileHandler fh;

        try {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd hh mm");
            String strDate = dateFormat.format(date);

            String filename = "./src/" + strDate + ".log";
            fh = new FileHandler(filename);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static BankLogger getBankLogger() {
        return bankLogger;
    }

    public void log(String text)
    {
        logger.info(text);
    }
}
