/**
 * 
 */
package ac.memory.persistance;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.*;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;

import ac.shared.structure.CompleteBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class NodeObject {
    static final String ID = "id";
    static final String OBJECT = "object";

    // START SNIPPET: the-node
    private final Node underlyingNode;

    NodeObject(Node objectNode) {
        this.underlyingNode = objectNode;
    }

    protected Node getUnderlyingNode() {
        return underlyingNode;
    }

    /**
     * @return the Id of the object
     */
    public Long getId() {
        return (Long) underlyingNode.getProperty(ID);
    }

    /**
     * @return the CompleteBoardState
     */
    public CompleteBoardState getObject() {
        return (CompleteBoardState) underlyingNode.getProperty(OBJECT);
    }

    @Override
    public int hashCode() {
        return underlyingNode.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NodeObject
                && underlyingNode.equals(((NodeObject) o).getUnderlyingNode());
    }

    @Override
    public String toString() {
        return "Person[" + getId() + "]";
    }
}
