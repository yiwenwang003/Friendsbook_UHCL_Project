
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Timi
 */
public class Update {
     //private String postID;
    private String type;
    private String userID;
    private String updateID;
    private String postID;
//    private ArrayList<String> allComment;
//    //
    private List<String>all;
   public Update(String upid,String t, String uid,String pid)
    {

        updateID=upid;
        type=t;
        userID=uid;
        postID=pid;
        
    }
//   public String showPost(ArrayList p)
    public String showPost()
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
            rs= stat.executeQuery("select * from post_table where PostID ='"+ postID+"'");
            if(rs.next())
            {
                return rs.getString(3);
            }
            else
            {
//                Statement stat1= conn.createStatement();
//                ResultSet rsProfile= stat1.executeQuery("select * from profile_table where userID='"+userID+"'");             
//                
//                return rsProfile.getString(2)+rsProfile.getString(3)+rsProfile.getString(4)+rsProfile.getString(5);
                return "no post here";
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
////    
    public ArrayList<String> displayComment()
    {
       // allComment= new ArrayList<String>();
        all= new ArrayList<String>();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            all.add("internalError");
            
        }
        Connection conn = null;  
        Statement stat = null;   
        ResultSet rs = null;   
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn= DriverManager.getConnection(DB_URL,"xxxxxx","xxxxxx");
            stat = conn.createStatement();
            
            ResultSet rsPost = stat.executeQuery("Select * from post_table where PostID = '"+postID+"'");
           
          
            if(rsPost.next())
            {
               // System.out.printf("%s: %s\n", rsPost.getString(2), rsPost.getString(3));
                Statement stat3 = conn.createStatement();
                ResultSet rsCom = stat3.executeQuery("Select * from comment_table where PostID = '"
                        + rsPost.getString(1) + "'");
                
                while(rsCom.next())
                {
                   // System.out.printf("\t%s: %s\n", rsCom.getString(2), rsCom.getString(3));
                   all.add("Comment from \t "+rsCom.getString(2)+" : "+" "+rsCom.getString(3));
                   System.out.println("here");
                }
                
              
            }
     
        }
        
        catch(SQLException e)
        {
            
            e.printStackTrace();
            all.add ("Notification have an exception");
        }
        finally
        {
            try
            {
                conn.close();
                stat.close();
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
                
            }
        }
        ArrayList<String> listOfStrings = new ArrayList<>(all.size());
        listOfStrings.addAll(all);
      return listOfStrings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUpdateID() {
        return updateID;
    }

    public void setUpdateID(String updateID) {
        this.updateID = updateID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    
       
}
