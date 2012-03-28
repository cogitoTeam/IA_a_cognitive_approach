/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import java.util.List;


public class Frontier 
{ 
  
    /* ATTRIBUTES */
   
    private Actuator actuator = new Actuator();
    private Sensor sensor = new Sensor();
    
    
    
    /* METHODS */
    
    // creation
    public Frontier()
    {
    }
    
    // query
    
    public void chooseOption(Option o)
    {
        actuator.performMove(o.getMove());
    }
    
    public List<Option> getOptions()
    {
        return null;
    }
    
    

    

}
