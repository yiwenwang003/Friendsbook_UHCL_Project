
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Timi
 */
@ManagedBean
@SessionScoped
public class Login  implements Serializable{
    private String id;
    private String pwd;
    private UserAccount userAccount;
    
    String selectedNotification;

    public String getSelectedNotification() {
        return selectedNotification;
    }

    public void setSelectedNotification(String selectedNotification) {
        this.selectedNotification = selectedNotification;
    }
    
    

    public String login1()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");        
            
        }
        catch (Exception e)
        {e.printStackTrace();
            return ("Internal Error! Please try again later.kkkkk");
        }
        
        System.out.print("loging test");
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
        Connection connection = null;  
        Statement statement = null;    
        ResultSet resultSet = null; 
        try
        {      
            //connect to the database with user name and password
            connection = DriverManager.getConnection(DATABASE_URL,"wangy8693","1616764");
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery("Select * from useraccount_table "
                    + "where userID = '" + 
                    id + "'" );
            //class code start from below
            if(resultSet.next())
            {
                // id is found
                if(pwd.equals(resultSet.getString(2)))
                {
                 //create user account obj
                 //then go to welcome webpage
                 //     userAccount= new UserAccount(resultSet.getString(1),pwd);
                      userAccount= new UserAccount(resultSet.getString(1),pwd,resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
                //    userAccount= new UserAccount(id,pwd);
                    return "welcome";
                   
                }
                else
                {
                    //pwd incorrected
                    id="";
                    pwd="";
                    return "loginNotOk";
                }
            }
            else
            {
                id="";
                pwd="";
                // id is not found;
                return "loginNotOk";
                        
            }
             
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return ("internalError");
        }
        finally
        {
            try
            {
                resultSet.close();
                statement.close();
                connection.close();
                 
            }
            catch (Exception e)
            {
                e.printStackTrace();    
            }
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    
    
//    public String showMessage()
//    {
//        
//        //return "notificationDetail.xhtml";
//       // return userAccount.showMessagelNotification(selectedNotification);
//    }
}
