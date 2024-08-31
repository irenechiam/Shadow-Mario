import java.io.*;
import java.util.*;

/**
 * Class that contains methods to read a CSV file and a properties file.
 * You may edit this as you wish.
 */
public class IOUtils {
    private static final String DELIMITER = ",";

    /***
     * Method that reads a CSV file and return a 2D String array
     * @param csvFile: the path to the CSV file
     * @return String ArrayList
     */
    public static List<String[]> readCsv(String csvFile) {
        List<String[]> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(DELIMITER);
                items.add(itemData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    /***
     * Method that reads a properties file and return a Properties object
     * @param configFile: the path to the properties file
     * @return Properties object
     */
    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }
}
