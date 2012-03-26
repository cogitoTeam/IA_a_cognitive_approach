/**
 * 
 */
package ac.shared.advanced_concept;

/**
 * Advanced Concept class
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class AdvancedConcept {

    private String id;
    private AdvancedConceptCore advanced_concept_core;

    /**
     * Default constructor of an advanced concept
     * 
     * @param id
     *            the id's advanced concept
     */
    public AdvancedConcept(String id) {
        this(id, new AdvancedConceptCore());
    }

    /**
     * Constructor with id and advanced concept core in parameter
     * 
     * @param id
     * @param advanced_concept_core
     */
    public AdvancedConcept(String id, AdvancedConceptCore advanced_concept_core) {
        this.setId(id);
        this.setAdvanced_concept_core(advanced_concept_core);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the advanced_concept_core
     */
    public AdvancedConceptCore getAdvanced_concept_core() {
        return advanced_concept_core;
    }

    /**
     * @param advanced_concept_core
     *            the advanced_concept_core to set
     */
    public void setAdvanced_concept_core(
            AdvancedConceptCore advanced_concept_core) {
        this.advanced_concept_core = advanced_concept_core;
    }

}
