
package backend;

import java.util.Scanner;


public class invertory_test {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("enter product name:");
        String pn = input.nextLine();
        System.out.println("enter price");
        int pr = input.nextInt();
        System.out.println("Enter quantity:");
        int qn = input.nextInt();
        
        Products p = new Products(pn,pr,qn);
        p.display();
        
    }
    
}
