/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;


public class Frontier 
{
    /* SINGLETON */
    
    private static Frontier instance = null;
    
    public static Frontier getInstance()
    {
        if(instance == null)
            instance = new Frontier();
        return instance;
    }
    
    private Frontier()
    {
    }

          
            
    /* ATTRIBUTES */
    
    private Actuator actuator = new Actuator();
    private Sensor sensor = new Sensor();
    
    
    
    /* METHODS */

    

}
