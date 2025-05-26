
package backend;

import java.util.Scanner;


public class Categories {
    
    private int categoryid;
    private String categoryname;
    
    public Categories(int categoryid, String categoryname){
        
        this.categoryid = categoryid;
        this.categoryname = categoryname;
        
    }

    public int getCategoryid() {
        return categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }
    
    public void input(){
        
        Scanner input = new Scanner(System.in);
        
        int id = 0;
        id++;
        
        System.out.println("input category name: ");
        String name = input.nextLine();
    }
    
    
    
}
