package model.cart;

import jakarta.servlet.http.HttpSession;
import model.DAO;
import model.customer.Customer;
import model.customer.CustomerDAO;
import model.item.Item;
import model.product.Product;
import model.product.ProductDAO;
import utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CartDAO extends DBContext implements DAO {

    @Override
    public Cart get(int customerId) {
        String sql = "select * from [items] where customer_id = " + customerId;
        Cart cart = new Cart();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Item item = new Item();
                ProductDAO productDAO = new ProductDAO();
                item.setQuantity(resultSet.getInt("quantity"));
                item.setPrice(resultSet.getInt("price"));
                item.setSize(resultSet.getInt("size"));
                Product product = productDAO.get(resultSet.getInt("product_id"));
                item.setProduct(product);
                cart.addItem(item);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return cart;
    }

    @Override
    public List getAll() {
        return null;
    }

    public void addCart(Cart cart, Customer customer) {
        String sql = "insert into [items] values (?,?,?,?,?)";
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        List<Item> itemList = cart.getItems();
        for (Item item : itemList){
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setInt(2, item.getProduct().getId());
            preparedStatement.setInt(3, item.getQuantity());
            preparedStatement.setInt(4, item.getSize());
            preparedStatement.setDouble(5, item.getPrice());
            preparedStatement.executeUpdate();
        }
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public void removeCart(int customerId) {
        String sql = "delete from [items] where customer_id = " + customerId;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
