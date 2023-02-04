
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Timi
 */
public class Notifications_msg {
    private String receiver;
    private String sender;    
    private String contents;
    private String status;
    private String reply;
    private ArrayList<Notifications_msg>notifications_list;

    public Notifications_msg(String r, String se,String c)
    {

        receiver= r;
        sender=se;      
        contents= c; 
        status="ur";//set to unread
        
        notifications_list= new ArrayList<Notifications_msg>();     
        
    }
    public String displayMsg(String a)
    {
         try
        {
            Class.forName("com.mysql.jdbc.Driver");                 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ("Internal Error! Please try again later.kkkkk");
        }
        
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        
        try
        {
            
            conn= DriverManager.getConnection(DB_URL,"xxxxxx","xxxxxx");
            stat= conn.createStatement();
            rs= stat.executeQuery("select * from notification_msgtable where sender ='"+ a+"'");
            if(rs.next())
            {
                return "Testinng"+ rs.getString(3);
            }
            else
            {
               // rsProfile=stat.executeQuery("select * from useraccount_table where userID='"+ )
                
                return "error message!!!!";
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
                rs.close();
                stat.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();    
            }
        }
    }
    
    

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public ArrayList<Notifications_msg> getNotifications_list() {
        return notifications_list;
    }

    public void setNotifications_list(ArrayList<Notifications_msg> notifications_list) {
        this.notifications_list = notifications_list;
    }
  
}
