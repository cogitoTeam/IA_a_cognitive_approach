/**
 * 
 */
package ac.memory.semantic.tests;

import java.io.IOException;

import org.apache.log4j.Logger;

import ac.memory.semantic.lattice.LatticeContextException;
import ac.memory.semantic.lattice.Neo4jLatticeContext;
import ac.memory.semantic.util.PcboUtil;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
@SuppressWarnings("unused")
public class TestPerformance
{
  private static final Logger logger = Logger
      .getLogger(TestLatticeConcext.class);

  /**
   * @param args
   * @throws IOException
   * @throws LatticeContextException
   */
  public static void main(String[] args) throws IOException,
      LatticeContextException
  {

    int n_attributes = 50;
    int n_objets = 500;
    double proba = 15.0;

    Neo4jLatticeContext context = new Neo4jLatticeContext();
    if (false)
      {
        if (logger.isDebugEnabled())
          logger.debug("Génération de " + n_attributes + " attributs");
        for (int i = 0; i < n_attributes; ++i)
          {
            context.addAttribute(new RelevantPartialBoardState(i));
          }

        if (logger.isDebugEnabled())
          logger.debug("Génération de " + n_objets + " objets");
        for (int i = 0; i < n_objets; ++i)
          {
            context.addObject(new CompleteBoardState(i));
          }

        logger
            .debug("Génération de remplissage aléatoire avec une proba d'appartenance de "
                + proba);
        proba = proba / 100.0;
        int percent = -1;
        for (int i = 0; i < n_objets; ++i)
          {
            for (int j = 0; j < n_attributes; ++j)
              {
                if (Math.random() < proba)
                  context.setStatus(i, j, true);
              }
            if (percent != (int) (((float) i / (float) n_objets) * 100))
              {
                percent = (int) (((float) i / (float) n_objets) * 100);
                if (logger.isDebugEnabled())
                  logger.debug("pourcentage : " + percent);
              }
          }

        if (logger.isDebugEnabled())
          logger.debug("Génération du context terminée");
      }
    if (logger.isDebugEnabled())
      logger.debug("Génération du code Fimi");
    String fimi = PcboUtil.toFimi(context);

    if (logger.isDebugEnabled())
      logger.debug("Génération des concepts");
    String concepts = PcboUtil.executeBinary(fimi, PcboUtil.Arch.linux_x86_64);
    if (logger.isDebugEnabled())
      logger.debug("Génération des concepts terminée !");
    if (logger.isDebugEnabled())
      logger.debug(concepts);

  }
}
