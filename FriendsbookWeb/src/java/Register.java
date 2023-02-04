
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


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
@RequestScoped
public class Register {
    private String accountId;
    private String password;
    private String gender;
    private String school;
    private String birthday;
      boolean alpha = false;
      boolean num = false;
      boolean special = false;

    public String register()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
   
        }
        catch (Exception e)
        {
            return ("Internal Error! Please try again later.");
        }              
        //general structure
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
        // 3 importane var
        Connection conn= null;
        Statement stat=null;
        ResultSet rs=null;
        try
        {          
            conn = DriverManager.getConnection(DB_URL,"xxxxxx","xxxxxx");
            stat= conn.createStatement();
            rs= stat.executeQuery("Select * from useraccount_table where userID ='"+ accountId +"'");
// how to know it contian id or pwd
            if(rs.next())
            {
                //id is  used
               // System.out.println("Account creation failed because id is used");
                return ("Account creation failed because id is used");
            }
            else
            {
                int l = accountId.length();
                if(l>=3 && l <=10)
                { 
                    char[] pass = accountId.toCharArray();

                    alpha = atleastOneChar(pass);

                    num = atleastOneNum(accountId);

                    special =atleastOneSpecial(pass);

                    if(alpha == true && num == true && special == true)
                    {
                        if(password.equals(accountId))
                        {
                             System.out.println("password same as login id , not acceptable");
                            return("password same as login id , not acceptable");
                        }
                    
                        else
                        {
                        //good to go, insert a record into table

                            int r = stat.executeUpdate("Insert into useraccount_table values('"+accountId+"','"
                                    +password+"','" + gender + "', '"+ school +"','"+birthday+"')");   

            //                System.out.println("FB account creates successfully!");
            //                System.out.println();
                            return ("Registration Successful! Please "
                                     + "return to login your account.");
                        }
                    }
                    else                                  
                    {
                        System.out.println("login id does not satisfy one char , one number and one special character feature");
                        return("login id does not satisfy one char , one number and one special character feature");
                    }
                }
                else
                          
                {

                    System.out.println("the login id is not between 3-10 characters");
                    return("the login id is not between 3-10 characters");

                }
                
            }
        }
            
           
        catch(SQLException e)
        {
            System.out.println("FB account creation failed because of exception");
              return ("Internal Error! Please try again later.");
        }
        
        finally
        { 
            try
            {//cles db
                conn.close();
                stat.close();
                rs.close();
                
            }
            catch (Exception e)
            {
                 
                e.printStackTrace();
                return ("Internal Error! Please try again later.");
            }
        }
        
    }
    public static boolean atleastOneChar(char[] abc)
     {
         char[] array1 = new char[52];
        int index1 = 0;
        for (char c = 'a'; c <= 'z'; c++) 
        {
            array1[index1++] = c;
            
        }
       
        int index2 = 26;
        for (char c = 'A'; c <= 'Z'; c++) 
        {
            array1[index2++] = c;
        }
        for(int i = 0 ; i <abc.length ; i++)
        {
                   
            for(int j = 0 ; j <array1.length ; j++)
            {
                if(abc[i] == array1[j])
                {
                    return true;

                }

            }

        }
        System.out.println("login id does not have one alphabet");
        return false;
     
     }
    public static boolean atleastOneNum(String a)
     {    
        try
        {
            int abc = Integer.parseInt(a.replaceAll("[\\D]", ""));

            return true;
        }
        catch(Exception e)
        {
          
          System.out.println("login id does not have number or digit");
          return false;
          
        }
     
     }
     
    public static boolean atleastOneSpecial(char[] abc)
    {
        char[] array3 = {'#','?','!','*'};
        for(int i = 0 ; i <abc.length ; i++)
        {
            for(int j = 0 ; j <array3.length ; j++)
            {
                if(abc[i] == array3[j])
                {
                    return true;
                }

            }
        }
        System.out.println("login id does not have special character");
        return false;

    }
    
     
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}


        

