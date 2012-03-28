/**
 * 
 */
package ac.memory.graph;

import grph.Grph;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class TestGraph {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Grph myG = new Grph(10, 15);

        myG.addVertex(1);
        System.out.println("Ajout d'un sommet 1");
        myG.addVertex(2);
        System.out.println("Ajout d'un sommet 2");
        myG.addVertex(3);
        System.out.println("Ajout d'un sommet 3");
        myG.addVertex(4);
        System.out.println("Ajout d'un sommet 4");
        myG.addVertex(5);
        System.out.println("Ajout d'un sommet 5");
        myG.addVertex(6);
        System.out.println("Ajout d'un sommet 6");

        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(1, 2));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(6, 1));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(3, 2));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(4, 2));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(5, 2));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(6, 2));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(1, 3));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(3, 4));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(4, 5));
        System.out.println("Ajout d'un arc " + myG.addDirectedSimpleEdge(5, 6));

        System.out.println("////////////////////");
        System.out.println(myG.toDot());

        Grph myG2 = new Grph(2, 1);
        myG2.addVertex(1);
        myG2.addVertex(2);
        myG2.addDirectedSimpleEdge(2, 1);

        System.out.println(myG.contains(myG2));
    }

}
