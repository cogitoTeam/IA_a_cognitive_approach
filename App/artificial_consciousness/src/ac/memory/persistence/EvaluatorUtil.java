/**
 * 
 */
package ac.memory.persistence;

import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

/**
 * Provides some Evaluator
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 31 mars 2012
 * @version 0.1
 */
public class EvaluatorUtil
{
  /**
   * @return INCLUDE_AND_CONTINUE if lenght = 1, EXCLUDE_AND_CONTINUE otherwise
   */
  public static Evaluator lengthOfOne()
  {
    return new Evaluator()
    {

      @Override
      public Evaluation evaluate(Path arg0)
      {
        if (arg0.length() == 1)
          return Evaluation.INCLUDE_AND_CONTINUE;
        else
          return Evaluation.EXCLUDE_AND_CONTINUE;
      }
    };
  }
}
