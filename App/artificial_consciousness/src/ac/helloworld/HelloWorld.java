package ac.helloworld;

import org.apache.log4j.Logger;

public class HelloWorld {
	private static final Logger logger = Logger.getLogger(HelloWorld.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.debug("Hello World log4j");
		
		System.out.println("Hello World ! (Si log4j est configuré, un fichier helloworld.log doit être créé dans le rep log");
	}

}
