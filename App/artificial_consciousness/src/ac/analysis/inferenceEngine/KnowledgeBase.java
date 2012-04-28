package ac.analysis.inferenceEngine;

import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import ac.analysis.structure.*;
import ac.shared.FOLObjects.Option_FOL;
import ac.util.LinkedSet;

/**
 * This class represents a Knowledge Base. It consists of:
 * <p>
 * a fact base and
 * <p>
 * a set of rules.
 * <p>
 * It acts as an Inference Engine which :
 * <p>
 * 1)calculates all deducible facts by forward chaining
 * <p>
 * 2)answers queries of the form :
 * <p>
 * "find all x1...xn that satisfy P", where x1...xn are variables in P and P is
 * a conjunction of atoms
 * 
 * 
 * @author namratapatel
 * 
 */
public class KnowledgeBase extends Thread
{

  private static final Logger LOGGER = Logger.getLogger(KnowledgeBase.class);

  // **************************************************************************
  // ATTRIBUTES
  // **************************************************************************

  private FactBase BF;// base de faits
  private ArrayList<Rule> BR;// base de r�gles
  private Option_FOL option;
  private String sourceFilename;
  private boolean isSaturated = false;

  // **************************************************************************
  // CONSTRUCTORS
  // **************************************************************************

  /**
   * Constructeur vide
   */
  public KnowledgeBase()
  {
    super();
    BF = new FactBase();
    ;
    BR = new ArrayList<Rule>();
    sourceFilename = null;
  }

  /**
   * Copy constructor
   * 
   * @param k
   *          to copy
   */
  public KnowledgeBase(KnowledgeBase k)
  {
    super();
    BF = new FactBase(k.getFB());
    BR = new ArrayList<Rule>(k.getRB());
    sourceFilename = k.sourceFilename;
  }

  /**
   * Constructor
   * 
   * @param o
   *          an instance of {@link Option_FOL}
   * 
   * @param BF
   *          an instance of {@link FactBase}
   * @param BR
   *          a list of rules {@link Rule}
   */
  public KnowledgeBase(Option_FOL o, ArrayList<Rule> BR)
  {
    super();
    this.option = o;
    this.BF = o.getResult().getBoardStateFacts();
    this.BR = BR;
    sourceFilename = null;
  }

  /**
   * Constructor
   * 
   * @param filename
   *          file from which the KB will be created
   * @throws IOException
   */
  public KnowledgeBase(String filename) throws IOException
  {
    sourceFilename = filename;
    BufferedReader lectureFichier = new BufferedReader(new FileReader(filename));
    if (LOGGER.isDebugEnabled())
      LOGGER
          .debug("CHARGEMENT D'UNE BASE DE CONNAISSANCES à partir du fichier : "
              + filename);

    String t = lectureFichier.readLine();
    BF = new FactBase(t);
    t = lectureFichier.readLine();
    int n = Integer.parseInt(t);

    BR = new ArrayList<Rule>(n);
    for (int i = 0; i < n; i++)
      {
        t = lectureFichier.readLine();
        BR.add(new Rule(t, "Règle " + (i + 1)));
      }
    lectureFichier.close();

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("FIN DU CHARGEMENT DE LA BASE DE CONNAISSANCE");
  }

  // **************************************************************************
  // METHODS
  // **************************************************************************

  /**
   * Clears the fact base
   */
  public void clearFactBase()
  {
    BF = new FactBase();
    isSaturated = false;
  }

  /**
   * Adds a new fact (atom passed as parameter) to the fact base. Note: it
   * indicates that the fact base is not saturated anymore
   * 
   * @param fact
   *          the new fact
   */
  public void addNewFact(Atom fact)
  {
    BF.addNewFact(fact);
    isSaturated = false;
  }

  /**
   * Adds a new rule to the set of rules in the knowledge base
   * 
   * @param newRule
   */
  public void addNewRule(Rule newRule)
  {
    BR.add(newRule);
  }

  /**
   * Saturates the fact base by forward chaining using the rule dependency graph
   * 
   * @return the saturated fact base
   * @throws IOException
   */
  public LinkedList<Long> optimizedSaturation_FOL() throws IOException
  {
    if (isSaturated)
      return new LinkedList<Long>();

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("DEBUT SATURATION KB");

    RuleDependencyGraph ruleDependencyGraph = new RuleDependencyGraph(this);
    ruleDependencyGraph.computeRDG();
    if (LOGGER.isDebugEnabled())
      {
        LOGGER.debug(ruleDependencyGraph.toString());
        LOGGER.debug("--> Début de l'algorithme de chaînage avant");
      }

    LinkedSet<Rule> rules = new LinkedSet<Rule>();
    for (int i = 0; i < BF.getAtomList().size(); i++)
      {
        rules.addAll(ruleDependencyGraph.getGraphe().get(i));
      }
    LinkedList<Long> list_rpbs = computeNewFacts(rules, this.BF);

    this.isSaturated = true;

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("FIN SATURATION KB");

    return list_rpbs;
  }

  /**
   * Saturates the fact base by forward chaining without using the rule
   * dependency graph
   * 
   * @return the saturated fact base
   * @throws IOException
   */
  public LinkedList<Long> saturation_FOL_without_GDR() throws IOException
  {
    if (isSaturated)
      return new LinkedList<Long>(); // evite le calcul de saturation au cas où
                                     // la base est
    // déjà saturée

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("DEBUT SATURATION KB");

    LinkedList<Long> list_rpbs = computeNewFacts(new LinkedList<Rule>(this.BR),
        this.BF);

    this.isSaturated = true; // indique que la base est saturée

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("FIN SATURATION KB");

    return list_rpbs;
  }

  /**
   * Recursively calculates facts generated by the successors
   * (of facts or rules depending on the case) which are passed as parameters
   * 
   * @param successors
   *          The list of successors
   * @param facts
   *          the current fact base (to which the new facts shall be added)
   */
  private LinkedList<Long> computeNewFacts(LinkedList<Rule> successors,
      FactBase facts)
  {
    Rule r;
    Long l;
    int size = 0;
    ArrayList<Substitution> substitutions_list;
    LinkedList<Long> list_rpbs = new LinkedList<Long>();
    Homomorphisms s;

    while (!successors.isEmpty())
      {
        r = successors.removeFirst();

        if (LOGGER.isDebugEnabled())
          LOGGER.debug("\n\tRègle considérée : " + r);

        try
          {
            s = new Homomorphisms(r.getPremise(), facts);
            substitutions_list = s.getHomomorphisms();
            size = substitutions_list.size();
          }
        catch (Exception e)
          {
            LOGGER.error("Erreur lors d'un homomorphismes", e);
          }

        if (size > 0)
          {

            l = Long.parseLong(r.getConclusion().getLabel().substring(5));
            for (int i = 0; i < size; ++i)
              list_rpbs.add(l);

            if (LOGGER.isDebugEnabled())
              LOGGER.debug("\n\tNouveau fait ajouté : " + r.getConclusion());
          }
      }

    return list_rpbs;
  }

  /**
   * Computes the successors of a given rule (passed as parameter)
   * 
   * @param r
   *          The rule
   * @return The list of successors
   */
  @SuppressWarnings("unused")
  private LinkedSet<Rule> computeSuccessors(Rule r)
  {
    RuleDependencyGraph g = new RuleDependencyGraph(this);
    int n = Integer.parseInt(r.getName().substring(1))
        + BF.getAtomList().size() - 1;

    g.computeRDG();
    return g.getGraphe().get(n);
  }

  // **************************************************************************
  // OVERRIDE METHODS
  // **************************************************************************

  @Override
  /* This method is executed in new thread when you call it by
   * KnowledgeBase.start()
   * 
   * @see java.lang.Thread#run() */
  public void run()
  {
    LinkedList<Long> list_rpbs;
    try
      {
        list_rpbs = this.saturation_FOL_without_GDR();

        for (Long id_rpbs : list_rpbs)
          {
            if (LOGGER.isDebugEnabled())
              LOGGER.debug(id_rpbs);

            option.addPartialStates(id_rpbs);
          }
      }
    catch (IOException e)
      {
      }
  }

  @Override
  public String toString()
  {
    String BRs = "Nombre de règles : " + BR.size() + "\nListe des règles : \n";
    for (int i = 0; i < BR.size(); i++)
      {
        BRs += "\t" + BR.get(i) + "\n";
      }
    return "\n  Base de faits\n" + BF + "\n  Base de règles\n" + BRs;
  }

  // **************************************************************************
  // GETTERS / SETTERS
  // **************************************************************************

  /**
   * @return the fact base
   */
  public FactBase getFB()
  {
    return BF;
  }

  /**
   * @return the list of rules
   */
  public ArrayList<Rule> getRB()
  {
    return BR;
  }

  /**
   * @return the source filename
   */
  public String getSourceFilename()
  {
    return sourceFilename;
  }

  /**
   * @return True if the fact base is saturated, False otherwise
   */
  public boolean isSaturated()
  {
    return isSaturated;
  }

  /**
   * @param fb
   *          the fact base to set
   */
  public void setBF(FactBase fb)
  {
    this.BF = fb;
    this.isSaturated = false;
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException
  {
    KnowledgeBase k = new KnowledgeBase("RuleBase");
    System.out.println(k);
    // KnowledgeBase kSaturee = k.saturationOrdre0();
    // System.out.println(kSaturee);

    k.optimizedSaturation_FOL();
    System.out.println("\n\nBase de Faits satur�e par homomorphismes:\n\n"
        + k.BF);

  }

}
