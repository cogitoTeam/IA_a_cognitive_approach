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
public class TestLatticeConcext
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

    Neo4jLatticeContext context = new Neo4jLatticeContext();

    RelevantPartialBoardState tmp = new RelevantPartialBoardState();
    tmp.setId(0);
    RelevantPartialBoardState rpbs0 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(1);
    RelevantPartialBoardState rpbs1 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(2);
    RelevantPartialBoardState rpbs2 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(3);
    RelevantPartialBoardState rpbs3 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(4);
    RelevantPartialBoardState rpbs4 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(5);
    RelevantPartialBoardState rpbs5 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(6);
    RelevantPartialBoardState rpbs6 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(7);
    RelevantPartialBoardState rpbs7 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(8);
    RelevantPartialBoardState rpbs8 = tmp;
    tmp = new RelevantPartialBoardState();
    tmp.setId(9);
    RelevantPartialBoardState rpbs9 = tmp;

    context.addAttribute(rpbs0);
    context.addAttribute(rpbs1);
    context.addAttribute(rpbs2);
    context.addAttribute(rpbs3);
    context.addAttribute(rpbs4);
    context.addAttribute(rpbs5);
    context.addAttribute(rpbs6);
    context.addAttribute(rpbs7);
    context.addAttribute(rpbs8);
    context.addAttribute(rpbs9);

    CompleteBoardState tmp2 = new CompleteBoardState();
    tmp2.setId(1);
    CompleteBoardState cbs1 = tmp2;
    tmp2 = new CompleteBoardState();
    tmp2.setId(2);
    CompleteBoardState cbs2 = tmp2;
    tmp2 = new CompleteBoardState();
    tmp2.setId(3);
    CompleteBoardState cbs3 = tmp2;
    tmp2 = new CompleteBoardState();
    tmp2.setId(4);
    CompleteBoardState cbs4 = tmp2;

    context.addObject(cbs1);
    context.addObject(cbs2);
    context.addObject(cbs3);
    context.addObject(cbs4);

    context.setStatus(cbs1, rpbs8, true);
    context.setStatus(cbs1, rpbs7, true);
    context.setStatus(cbs1, rpbs6, true);
    context.setStatus(cbs1, rpbs5, true);

    context.setStatus(cbs2, rpbs0, true);

    context.setStatus(cbs3, rpbs6, true);
    context.setStatus(cbs3, rpbs3, true);
    context.setStatus(cbs3, rpbs5, true);
    context.setStatus(cbs3, rpbs2, true);
    context.setStatus(cbs3, rpbs0, true);
    context.setStatus(cbs3, rpbs9, true);

    context.setStatus(cbs4, rpbs1, true);
    context.setStatus(cbs4, rpbs3, true);

    System.out.println("Impression du context : ");
    System.out.println(context);

    System.out.println("/////////////////////////");

    System.out.println("Impression du Fimi : ");
    System.out.println(PcboUtil.toFimi(context));

    System.out.println("/////////////////////////");

    System.out.println("Impression de concepts générés en log");
    if (logger.isDebugEnabled())
      logger.debug(PcboUtil.executeBinary(PcboUtil.toFimi(context),
          PcboUtil.Arch.linux_x86_64));
  }
}
