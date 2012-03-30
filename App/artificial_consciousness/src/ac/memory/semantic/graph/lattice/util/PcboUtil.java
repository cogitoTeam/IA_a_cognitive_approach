/**
 * 
 */
package ac.memory.semantic.graph.lattice.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import org.apache.log4j.Logger;

import ac.memory.semantic.graph.lattice.LatticeContext;
import ac.shared.structure.CompleteBoardState;
import ac.shared.structure.RelevantPartialBoardState;

/**
 * Tool to use Pcbo
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 29 mars 2012
 * @version 0.1
 */
public class PcboUtil {
    private static final Logger logger = Logger.getLogger(PcboUtil.class);

    /**
     * Architecture type
     * 
     * @author Thibaut Marmin <marminthibaut@gmail.com>
     * @date 29 mars 2012
     * @version 0.1
     */
    @SuppressWarnings("javadoc")
    public enum Arch {
        linux_x86_64, linux_i686, linux_sparc64, windows_i686;
    }

    /**
     * Generate a string sequence in FIMI format
     * 
     * @param context
     *            the context
     * @return the FIMI String sequence
     */
    public static String toFimi(LatticeContext context) {
        String ret = "";

        logger.debug("Génération du FIMI");
        boolean hasattribute;
        for (CompleteBoardState object : context.getObjects().values()) {
            hasattribute = false;
            for (Iterator<RelevantPartialBoardState> iterator = context
                    .getAttributesByObject(object).values().iterator(); iterator
                    .hasNext();) {
                ret += ((RelevantPartialBoardState) iterator.next()).getId()
                        + " ";
                hasattribute = true;

            }
            if (hasattribute)
                ret += "\n";
        }
        logger.debug("Génération du FIMI terminée");
        return ret;
    }

    /**
     * Execute the native binary FCBO
     * 
     * @param fimi
     * @param arch
     * @return \\TODO COMMENT
     * @throws IOException
     */
    public static String executeBinary(String fimi, Arch arch)
            throws IOException {
        logger.debug("Generation d'une commande d'execution FCBO");

        logger.debug("Determination du shell");
        String shell = "/bin/sh";
        String arg = "-c";
        if (arch.equals(Arch.windows_i686)) {
            shell = "cmd.exe";
            arg = "/C";
        }

        File file = new java.io.File("fimi.tmp.dat");

        PrintWriter out = new PrintWriter(file);
        out.write(fimi);
        out.close();

        String cmd = System.getProperty("user.dir") + "/native/fcbo-static-"
                + arch.toString().toLowerCase() + " -V1 "
                + System.getProperty("user.dir") + "/fimi.tmp.dat "
                + System.getProperty("user.dir") + "/concepts.tmp.dat";
        logger.debug("Shell : " + shell);
        logger.debug("arg : " + arg);
        logger.debug("Commande : " + cmd);
        String[] args = { shell, arg, cmd };

        logger.debug("Execution de la commande");
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(args);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        /*
         * java.io.InputStream in = process.getInputStream(); int i = 0; while
         * ((i = in.read()) != -1) ;
         */

        String retour = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    retour += line + "\n";
                }
            } finally {
                reader.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        logger.debug("Execution de la commande terminée");

        return retour;
    }
}
