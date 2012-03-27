/**
 * 
 */
package ac.shared;

/**
 * This class represents a graded advanced state board
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class GradedAdvancedBoardStateRelevantStructure extends
        AdvancedBoardStateRelevantStructure {

    private Double grade;

    /**
     * Default constructor with id parameter
     * 
     * @param id
     */
    public GradedAdvancedBoardStateRelevantStructure() {
        super();
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
