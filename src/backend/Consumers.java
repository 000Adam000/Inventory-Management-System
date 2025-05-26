
package backend;


public class Consumers extends Departments{
    
    private int id;
    private String name;
    
    
    
    public Consumers(int id, String name){
        super.getDepname();
        this.id = id;
        this.name = name;
        
    }

   // public int getId() {
      //  return id;
    //}

    public String getName() {
        return name;
    }
    
    public void input(){
        System.out.println("Enter the id:");
        //int id = input.nextInt();
        System.out.println("Enter the name: ");
        //String name = input.nextLine();
    }
    
    
    
    
    
}
