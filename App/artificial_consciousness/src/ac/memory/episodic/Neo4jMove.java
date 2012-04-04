/**
 * 
 */
package ac.memory.episodic;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ac.memory.persistence.AttributeNode;
import ac.memory.persistence.MoveNode;
import ac.memory.persistence.NodeException;
import ac.memory.semantic.graph.lattice.LatticeContextException;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 3 avr. 2012
 * @version 0.1
 */
public class Neo4jMove implements Move
{
  private final MoveNode move;

  /**
   * @param move
   *          the underlying move node
   */
  public Neo4jMove(MoveNode move)
  {
    this.move = move;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Move#getDate() */
  @Override
  public Date getDate()
  {
    return move.getDate();
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Move#getCompleteBoardState() */
  @Override
  public CompleteBoardState getCompleteBoardState()
      throws EpisodicMemoryException
  {
    try
      {
        return move.getObject().getObject();
      }
    catch (LatticeContextException | NodeException e)
      {
        throw new EpisodicMemoryException(
            "Error occured when getting object for a node", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Move#getGame() */
  @Override
  public Game getGame() throws EpisodicMemoryException
  {
    try
      {
        return new Neo4jGame(move.getGame());
      }
    catch (NodeException e)
      {
        throw new EpisodicMemoryException(
            "Error occured when getting a related game", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Move#getPreviousMove() */
  @Override
  public Move getPreviousMove()
  {
    MoveNode prev_move = move.getPrevious();
    if (prev_move != null)
      return new Neo4jMove(prev_move);
    else
      return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Move#getNextMove() */
  @Override
  public Move getNextMove()
  {
    MoveNode next_move = move.getNext();
    if (next_move != null)
      return new Neo4jMove(next_move);
    else
      return null;
  }

  @Override
  public String toString()
  {
    String ret = "Move[" + getDate() + "]";
    return ret;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Move#getRelevantPartialBoardStates() */
  @Override
  public List<RelevantPartialBoardState> getRelevantPartialBoardStates()
  {
    LinkedList<RelevantPartialBoardState> list = new LinkedList<RelevantPartialBoardState>();

    try
      {
        for (Iterator<AttributeNode> iterator = move.getRelatedObject()
            .getRelatedObjects().iterator(); iterator.hasNext();)
          {
            AttributeNode att_node = (AttributeNode) iterator.next();
            try
              {
                list.add(att_node.getObject());
              }
            catch (LatticeContextException e)
              {
                // TODO WARNING
              }

          }
      }
    catch (Exception e)
      {
        // TODO: WARNING
      }
    return list;
  }
}
