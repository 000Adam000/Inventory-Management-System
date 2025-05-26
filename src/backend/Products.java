
package backend;


public class Products extends Users{
    
    private String productname;
    private int quantity;
    private int price;
    
public Products(String productname,int quantity,int price){
    
    
    this.productname=productname;
    this.price = price;
    this.quantity= quantity;    
    
}

    public String getProductname() {
        return productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
    
    
    public void display(){
        System.out.println("product name: "+ getProductname());
        System.out.println("price: "+ getPrice());
        System.out.println("quantity"+ getQuantity());
    }
    
    
    
    
}




