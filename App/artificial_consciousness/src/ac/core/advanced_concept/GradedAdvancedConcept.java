/**
 * 
 */
package ac.core.advanced_concept;

/**
 * This class represents a graded advanced concept
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class GradedAdvancedConcept extends AdvancedConcept {

    private Double grade;

    /**
     * Default constructeur with id parameter
     * 
     * @param id
     */
    public GradedAdvancedConcept(String id) {
        this(id, new AdvancedConceptCore());
    }

    /**
     * Constructor with id and advanced concept core in parameters
     * 
     * @param id
     * @param advanced_concept_core
     */
    public GradedAdvancedConcept(String id,
            AdvancedConceptCore advanced_concept_core) {
        this(id, advanced_concept_core, 0.0);
    }

    /**
     * Constructor with id, advanced concept core and grade in parameters
     * 
     * @param id
     * @param advanced_concept_core
     * @param grade
     */
    public GradedAdvancedConcept(String id,
            AdvancedConceptCore advanced_concept_core, Double grade) {
        super(id, advanced_concept_core);
        this.grade = grade;
    }

    /**
     * @return the grade
     */
    public Double getGrade() {
        return grade;
    }

    /**
     * @param grade
     *            the grade to set
     */
    public void setGrade(Double grade) {
        this.grade = grade;
    }

}
