package ac.helloworldlog4j;

import org.apache.log4j.Logger;

/**
 * Classe d'Hello World
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class HelloWorldLog4j {
    private static final Logger logger = Logger
            .getLogger(HelloWorldLog4j.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        logger.debug("Hello World log4j");

        System.out.println("Hello World !");
        System.out
                .println("If the log4j.properties file is in your path, a file named \"helloworld.log\"had to be created in the log folder");
        
        logger.debug("Good job, your path is well configured !");
    }

}
