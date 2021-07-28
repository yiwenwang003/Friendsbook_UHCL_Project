/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.faces.context.FacesContext;

/**
 *
 * @author Timi
 */
@Named(value = "userAccount")
@SessionScoped
public class UserAccount implements Serializable {
    

    private String userID;
    private String userPwd;
    private String userGender;
    private String userSchool;
    private String userBirthday;
    
    private String selectedToView;
    
    private ArrayList<String>friends_list;
    private ArrayList<Update>updates_list;
    private List<String> updateID; 
    //sneha
    private String sneha;
    private String snehareceiver;
    
    private String acceptfriend;
    private String temp_accept;
    private String findPP;

   private String temp_matchID;
   private String temp_msg;

    
    //for adding comment  
    private String comment;
    private String tempCom;
   
    
    //for notification
    private ArrayList<String> notificationID;
    private String reply;
    private String temp_sender;
    private String tempvalue_Msg;

   private List<String>list_friendProfile=new ArrayList<String>();
    private List<String>list_updateProfile= new ArrayList<String>();
    private List<String>list_history= new ArrayList<String>(); 
   

    
    private ArrayList<Notifications_msg>notifications_msglist;
    private ArrayList<Notifications_FriendReq>notifications_frireqlist;
 //sneha
    private List<String>list1 = new ArrayList<String>();
    private List<String>list2 = new ArrayList<String>();
 
    
    //for post
    private String postContent;
    
    //for friend function
    private List<String>friends;
    
    //patty added for post created
    private String tempvalue;
    //for update profile
    private String temp_change;
    // for send msg to friend
    private ArrayList<Notifications_msg> msg_history;
    private String nameFormsg;
    private String contentFormsg;
    
    //for send friend request
    private String nameForrequest;
    private String temp_requestInfo;
    private String history;
 
    //for hashtag
    private ArrayList<Hashtag>findHashtage=new ArrayList<Hashtag>();
    private ArrayList<String>hashtagList= new ArrayList<String>();
    private String temp_keyword;
    private String selecedHashtag;
    private ArrayList<String>rankHash=new ArrayList<String>();
    private List<String>list3= new ArrayList<String>();

  //intSelection to be global
   private int intSelection;
   
   public UserAccount(String uid, String upwd,String g,String sch,String bd)
   {
        userID=uid;
        userPwd=upwd;
        userGender=g;
        userSchool=sch; 
        userBirthday=bd;
        
        selectedToView="";
       

        friends_list= new ArrayList<String>();
        updates_list=new ArrayList<Update>();
        updateID = new ArrayList<String>();
     
        
        notifications_msglist= new ArrayList<Notifications_msg>();
        notifications_frireqlist=new ArrayList<Notifications_FriendReq>();
        
        //for notification
        notificationID=new ArrayList<String>();
        
        //for send frined msg
        msg_history= new ArrayList<Notifications_msg>();
        
        //for friends function
        friends= new ArrayList<String>();
        //for hashtag      
       
        
        Connection conn= null;
        Statement stat=null;
        ResultSet rs=null;
        try
        {
            updateID.clear();
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn =DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat=conn.createStatement();
            rs=stat.executeQuery("select * from friend_table where id1= '"+userID+"' or id2 = '" + userID + "'");
            //how do we kown it contian id already
            while(rs.next())
            {
                if( userID.equals(rs.getString(1)))
                {
                    System.out.println(rs.getString((2))+" is yr friend");
                    friends_list.add(rs.getString(2));
                }
                if(userID.equals(rs.getString(2)))
                {
                  System.out.println(rs.getString((1))+" is yr frined");
                  friends_list.add(rs.getString(1));
                }
            }

            rs= stat.executeQuery("SELECT * from update_table");
            int updateCount = 0;
            if(rs.last()) //move the most recent one
            {
                if(friends_list.contains(rs.getString(3)))
                {
                    updateCount++;                 
                    updates_list.add(new Update(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                    if(rs.getString(2).equals("1"))
                    {
                         updateID.add(updateCount+"."+" "+rs.getString(3)+" "+"update his/her profile.");
                       
                    }
                    else
                    {
                        updateID.add(updateCount+"."+" "+rs.getString(3)+" "+"update his/her post.");                  
                    }
                }
                while(rs.previous() && updateCount<5)
                {
                    if(friends_list.contains(rs.getString(3)))
                    {
                        updateCount++;
                        updates_list.add(new Update(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                        if(rs.getString(2).equals("1"))
                        {
                            updateID.add(updateCount+"."+" "+rs.getString(3)+" "+"update his/her profile.");
                          
                        }
                        else
                        {
                           updateID.add(updateCount+"."+" "+rs.getString(3)+" "+"update his/her post.");
                      
                        }
                    }
                }
            }
           showNotification();
           showFriends();
           
        }
        catch(SQLException e)
        {
            e.printStackTrace();
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
        
    }

   
  //can use the keyword to identify the frist char either post or profile
    public String showUpdateStatement()
    {       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return "internalError";         
        }
       
//int intSelection= Integer.parseInt(""+selectedToView.charAt(0)); 
        intSelection= Integer.parseInt(""+selectedToView.charAt(0));  
        findPP= selectedToView.substring(selectedToView.lastIndexOf(" ")+1);

        if(findPP.equals("profile."))               
        {         
            return "displayProfile";
        }
        else
        {
           return "displayPost";
        }
            
      //  int intSelection= Integer.parseInt(""+sneha.charAt(0));     
        //  return updates_list.get(intSelection-1).showPost(); 
    }
    //if selection is post it will go to below
    public String test1()
    {
        intSelection= Integer.parseInt(""+selectedToView.charAt(0)); 
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return "internalError";         
        }
        
        return updates_list.get(intSelection-1).showPost(); 
    }
    
    //display all the comment from post
    public  ArrayList<String> showComment()
    {
        intSelection= Integer.parseInt(""+selectedToView.charAt(0)); 
       // int intSelection= Integer.parseInt(""+selectedToView.charAt(0));
        return updates_list.get(intSelection-1).displayComment(); 
       
    }
    //adding the comment
    public String addComment()
    {       
      //  int intSelection= Integer.parseInt(""+selectedToView.charAt(0));
        intSelection= Integer.parseInt(""+selectedToView.charAt(0)); 
        Connection conn = null;  
        Statement stat = null;   
        ResultSet rs = null;   
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat = conn.createStatement();
            rs = stat.executeQuery("Select * from comment_table where PostID = '"
                        + intSelection+ "'");
            if(rs.next())
            {
                int rsAdd_com=stat.executeUpdate("insert into comment_table values" + "('" + rs.getString(1)
                    + "', '" + userID + "', '"+ comment +"')");
                    tempCom="added comment successful";
                    
            }
            else{
                tempCom="Umm....";
            }
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            tempCom="Notification have an exception";
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
        return tempCom;
    }
   
    // if userSelection is profile, it will go this below
    //should use public string test2()
    public void test2()
    {
        intSelection= Integer.parseInt(""+selectedToView.charAt(0)); 
        String abc= updates_list.get(intSelection-1).getUserID(); 
        list_updateProfile.clear();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            //return "internalError";         
            System.out.println("internal error");
        }
        
        Connection conn = null;  
        Statement stat = null;   
        ResultSet rs = null;   
            
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat = conn.createStatement();
            rs = stat.executeQuery("Select * from profile_table where userID = '"+ abc+ "'");
            if(rs.next())
            {
              //  return "Gender: "+rs.getString(3)+"\t "+"School: "+rs.getString(4)+"\t"+"Birthday: "+rs.getString(5);
                String g="Gender: "+rs.getString(3);
                String s="School: "+rs.getString(4);
                String b="Birthday: "+rs.getString(5);
                        
                list_updateProfile.add(g);
                list_updateProfile.add(s);
                list_updateProfile.add(b);
            
            }
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
             System.out.println("SQL exception");
           
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
      
    }
   
//display notifistringstrcation   
    public List<String> showNotification()
    {
      //list1.clear();
      notificationID.clear();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
           notificationID.add("internalError");
           
        }
            
        Connection conn = null;  
        Statement stat = null;   
        ResultSet rs = null;   
        
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat= conn.createStatement();
          //  rs= stat.executeQuery("select * from notification_msgtable");
          String r = "ur";
          rs= stat.executeQuery("select * from notification_msgtable where receiver = '"+userID+"' and status = '"+ r+"'");
//add statment if user==receiver && status unread statment
            while(rs.next())
            {    // user == receiver and  unread status add into arrlist
                if(userID.equals(rs.getString(1))&& rs.getString(4).equals("ur"))
                {
                   notifications_msglist.add(new Notifications_msg(rs.getString(1),rs.getString(2),rs.getString(3)));  
//                  String a =rs.getString(2); Notifications_msg
//              //sneha change
//                //String a = rs.getString(3);
//                   //list1.add(a);
//                   list1.add("You have new message from :"+a);
                }
            }
            for(int i = 0 ; i <list1.size(); i++)
            {
                System.out.println("ggg");
            System.out.println(list1.get(i));
            }
            rs= stat.executeQuery("select * from notification_reqtable");
            while(rs.next())
            {// user==reciver and status not read into arrlist
                if(userID.equals(rs.getString(1))&& rs.getString(3).equals("ur"))
                {
                    notifications_frireqlist.add(new Notifications_FriendReq(rs.getString(1),rs.getString(2)));
//                    String b = "friend request sender from" +" "+rs.getString(2);
//                   list1.add(b);
                }
            }
           // return list1;
            /////add loop to determine how many msg from sender............commented from here
            Statement statMsg= conn.createStatement();
            ResultSet rsMsg= statMsg.executeQuery("select * from notification_msgtable");
            int listIndex=1;
            while(rsMsg.next())
            {//read the userID=recever && the notifi not read
              int num=0;
                  for(int i =0; i < friends_list.size(); i++)
                  {
                       if(userID.equals(rsMsg.getString(1))&& rsMsg.getString(4).equals("ur"))
                       {
                           if(rsMsg.getString(2).equals(friends_list.get(i)))
                            {
                                num++;
                            }
                       }
                   }                
                   if(num!=0)
                   {
                        notificationID.add(listIndex+". You have "+num+" Messge from "+rsMsg.getString(2));
                        listIndex++;
                   }
                  
            }
            
            Statement statReq=conn.createStatement();
            ResultSet rsReq=statReq.executeQuery("select * from notification_reqtable");
            while(rsReq.next())
            {
                if(userID.equals(rsReq.getString(1))&& rsReq.getString(3).equals("ur"))
                {
                    if(!friends_list.contains(rsReq.getString(2)))
                    {
                        
                       // notificationID.add(listIndex+". You have friend request from "+rsReq.getString(2)+"\n");
                        notificationID.add(listIndex+".Friend request from "+rsReq.getString(2));
                       // notificationID.add(listIndex+" testing2");
                      //  notificationID.add(rsReq.getString(2));
                        listIndex++;
                    }
                }
            }
            
            if(listIndex==1)
            {
              notificationID.add( "******No notification******");
            }
            return notificationID;
        }
        
        catch(SQLException e)
        {
            System.out.println("Notification have an exception");
            e.printStackTrace();
            return list2;
            
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
                return list2;
            }
        }
       
    }

  //creat a post
    public String createPost()
    {

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return("internal error");
           
        }
        tempvalue="";
        Connection conn= null;
        Statement stat=null;
        ResultSet rs=null;
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn=DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat=conn.createStatement();
            rs=stat.executeQuery("select * from post_table");
           
           // String content;
           
            Statement stat_post=conn.createStatement();
            ResultSet rs_po =stat_post.executeQuery("select * from post_table");
            int nextPostID = 0;
            while(rs_po.next())
            {
                 //PostID = "" + rs_po.getInt(1);
                 nextPostID= rs_po.getInt(1) + 1;
            }

            int rs_post= stat_post.executeUpdate("insert into post_table values"+ "('"+nextPostID +"','"
                     +userID+"','" +postContent +"')");
            
            Statement stat1= conn.createStatement();
            ResultSet rs1= stat1.executeQuery("select * from hashtag_table ");
            int count=1;
            String []test1 =postContent.split(" ");
            for(int i=0; i < test1.length;i++)
            {
                if(test1[i].contains("#"))
                {
                    ResultSet rs2= stat1.executeQuery("select * from hashtag_table where hashtag ='"+test1[i]+"'");
                    
                    if(rs2.next())
                    {

                       int rs3=stat1.executeUpdate("Update hashtag_table set occur ='" +(Integer.parseInt(rs2.getString(2))+1)+"' where hashtag ='"+test1[i]+"'");         
                    }
                    else
                    {
                        //if not exist, insert into hashtag table 
                        int rs4=stat1.executeUpdate("insert into hashtag_table values "+"('"+test1[i]+"','"+count+"')");
                    
                    }                               
                }
            }
            Statement stat_update=conn.createStatement();
            ResultSet rs_up= stat_update.executeQuery("select * from update_table");
            // find the next num
            int nextUpdateID=0;
            while(rs_up.next())
            {
                nextUpdateID=rs_up.getInt(1)+1;
            }
            String Update_type="2";
            int rs_update= stat_update.executeUpdate("insert into update_table values"+ "('"+nextUpdateID +"','"
                     +Update_type+"','" +userID+"','" + nextPostID+"')");
            
            tempvalue = "created a post successfully";
            
         }
         catch(SQLException e)
         {
             System.out.println("Your post failed because of an exception");
             e.printStackTrace();
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
       
        return "error in post";
    }
    //see friend
    public List<String> showFriends()
    {
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            friends.add("internalError");
           
        }
         
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        try
        {         
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn=DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat=conn.createStatement();

            rs =stat.executeQuery("select * from useraccount_table ");
            
            System.out.println("Enter to see yr frineds' profile");
            
            for(int i =0; i < friends_list.size(); i++)
            {//show the how many friends that user have
                
                friends.add(i+1+":"+friends_list.get(i)+" see her/ his profiles");
            }
  

    return friends;
        }
        catch(SQLException e)
        {
             e.printStackTrace();
            friends.add("Your friend failed because of an exception");
            return friends;
                 
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
   
    }
 // display user' friend profile
    public void display()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
           // return "internalError";
            System.out.println("internal error");
           
        }
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
      //  int intSelection= Integer.parseInt(""+selectedToView.charAt(0));
        intSelection= Integer.parseInt(""+selectedToView.charAt(0)); 
        list_friendProfile.clear();
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn=DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat=conn.createStatement();

            if(intSelection >= 1 && intSelection <= friends_list.size())
            {   
                //var for friends_list in order to match with db
                temp_matchID= friends_list.get(intSelection-1);
                rs =stat.executeQuery("select * from useraccount_table where userID='"+temp_matchID+"'");
                // view the statment
                while(rs.next())
                {
//                    return("Name: "+" "+rs.getString(1)+" "+"Gender: "+ rs.getString(3)+" "+"School: "+rs.getString(4)+
//                            " "+"Birthdays: "+rs.getString(5));
                    String a="Name: "+rs.getString(1);
                    String b="Gender: "+rs.getString(3);
                    String c="School: "+rs.getString(4);
                    list_friendProfile.add(a);
                    list_friendProfile.add(b);
                    list_friendProfile.add(c);
                    
                }

            }
           
        }
        catch(SQLException e)
        {
            e.printStackTrace();          
            //return "error";
             System.out.println("SQL exception");
               
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
                 // return("InternalError");
                   System.out.println("internal error");
             }
        }
        //return"not found";
      
    }
  
//hashtag
    public ArrayList<String> hashtag()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            rankHash.add("internalError");
           //return ("internalError");
        }
       
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        rankHash.clear();
        try
        {
            conn = DriverManager.getConnection(DB_URL, "wangy8693","1616764");
            stat= conn.createStatement();
            rs= stat.executeQuery("select * from hashtag_table");
            while(rs.next())
            {
                findHashtage.add(new Hashtag(rs.getString(1),Integer.parseInt(rs.getString(2))));
            }

            for(Hashtag aTag:findHashtage)
            {
                if(!hashtagList.contains(aTag.getKeyword()))
                {
                     hashtagList.add(aTag.getKeyword());
                } 

            }
            for(int i =0; i < hashtagList.size()-1;i++)
            {
                for(int j =i+1; j < hashtagList.size();j++)
                {
                    Hashtag temp =new Hashtag("",0);
                    if(findHashtage.get(i).getOccur() < findHashtage.get(j).getOccur())
                    { // compare i and j occur, and change the position
                        temp= findHashtage.get(i);
                        findHashtage.set(i, findHashtage.get(j));
                        findHashtage.set(j, temp);                   
                    }
                }
            }
        
            int index=1;

                for(int k=0; k < 3; k++)
                {               
                    //System.out.printf("%d: %s  \n",k+1,findHashtage.get(k).getKeyword());
                    rankHash.add(k+1+". "+findHashtage.get(k).getKeyword());
                }
          
            return rankHash;
          
            
        }
        catch(SQLException e)
        {
            
            e.printStackTrace();
           rankHash.add("you hashtag failed becasue of an exception");
           return rankHash;
         //return ("you hashtag failed becasue of an exception");
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
        
    }
    public String seeHashtag()
    {   
        list3.clear();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            System.out.println("internal error");
            return "internalError";    
           
        }
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null; 
       // int intSelection= Integer.parseInt(""+selecedHashtag.charAt(0));
        intSelection= Integer.parseInt(""+selecedHashtag.charAt(0)); 
        try
        {
            conn = DriverManager.getConnection(DB_URL, "wangy8693","1616764");
            Statement statPost=conn.createStatement();
            ResultSet rsPost=statPost.executeQuery("select * from post_table");
            boolean hasTag=false;
            selecedHashtag=findHashtage.get(intSelection-1).getKeyword();
            while(rsPost.next())
            {
                if(friends_list.contains(rsPost.getString(2)))
                {
                    if(rsPost.getString(3).contains(selecedHashtag))
                    {
                      
                        //list3.add("Testing*****"+rsPost.getString(2)+"  said: "+rsPost.getString(3));
                        String y=rsPost.getString(2)+"  said: "+rsPost.getString(3);
                        list3.add(y);
                        hasTag=true;
                       
                    }
                }

            }
            if(hasTag==false)
            {
                    //System.out.println("no hashtag from yr friend");
                // System.out.println("no hashtag from your friend");
                return "no hashtag from your friend";
               
            }
           
           
       }     
        
        catch(SQLException e)
        {
            e.printStackTrace();
            return "Notification have an exception";
            //System.out.println("SQLException");
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
  return "";
    }
    
    //signout
    public String signOut()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";

    }
    
     private boolean isInteger(String i)
    {
        try
        {
            // if is not integer the exception will be throw out
            int j = Integer.parseInt(i);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

   
   
    public String sneha1()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return "internalError";
           
        }
        Scanner input = new Scanner(System.in);
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        try
        {
            conn = DriverManager.getConnection(DB_URL, "wangy8693","1616764");
            stat= conn.createStatement();
            char[] abc = sneha.toCharArray();
            String d = "";
            int c = sneha.length();
            if(c >= 15)               
            {
                for(int i = 0 ; i <= 15; i++)
                {
                    d = d + abc[i];
                }
            }
            
            System.out.println("****" +d);
            if(d.contains("Friend request"))               
            {
             
              return("friend1");
            }
            else               
            {
              //return("message");
                return "notificationMsg";
            }
            
        }
        catch(SQLException e)
        {
            
            e.printStackTrace();
            return("you notification failed becasue of an exception");
        }
        finally
        {
            try
            {
                conn.close();
                stat.close();
                if (rs!= null)
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
     public String processFriendRequest()
     {       
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return("internal error");
           
        }       
        
        Connection conn= null;
        Statement stat=null;
        ResultSet rs=null;
        
        acceptfriend= sneha.substring(sneha.lastIndexOf(" ")+1);
        
        //int intSelection= Integer.parseInt(""+sneha.charAt(0));
        intSelection= Integer.parseInt(""+sneha.charAt(0));
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat= conn.createStatement();
            rs= stat.executeQuery("select * from friend_table");
            
            Statement stat_addFriend=conn.createStatement();
            int rsadd_Friend=stat_addFriend.executeUpdate("insert into friend_table values"+"('"+acceptfriend+ "', '"+ userID +"')");
            
                        
            Statement stat3= conn.createStatement();
            int rs_Req=stat3.executeUpdate("UPDATE notification_reqtable set status = 'r' where sender = '"+
                          notifications_frireqlist.get(intSelection-1).getSender()+"'and receiver= '"+notifications_frireqlist.get(intSelection-1).getReceiver()+"'");
             
            temp_accept="You are frineds now!";
             return temp_accept;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return("Accepting wrong");
            
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

     }
    

     // notification msg 
    public String msgMethod()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return "internalError";    
        }
      
        intSelection= Integer.parseInt(""+sneha.charAt(0));
        
        
        return notifications_msglist.get(intSelection-1).getContents();
       
    }
     public void processMsg()
     {
        intSelection= Integer.parseInt(""+sneha.charAt(0));
        String kk;
        kk= notifications_msglist.get(intSelection-1).getContents();
       
        Connection conn= null;
        Statement stat=null;
        ResultSet rs=null;
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat= conn.createStatement();
            
            int rs_reply=stat.executeUpdate("UPDATE notification_msgtable set status = 'r' where sender = '"+
                                   notifications_msglist.get(intSelection-1).getSender()+
                    "'and receiver='"+notifications_msglist.get(intSelection-1).getReceiver()+"'and contents='"+kk+"'");
           
        }
        catch(SQLException e)
        {
            e.printStackTrace();
           System.out.println("Msg wrong");
            
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
      
     }
    
    public String updateProfile()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return "internalError";    
        }
 
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        try
        {
            temp_change="";
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat= conn.createStatement();
            rs= stat.executeQuery("select * from useraccount_table");
            
            int nextUpdateID=0;
            int nextProfileID=0;
            
            String type="1";
            String gender="";
            String school="";
            String birthday="";

       
            Statement stat_profile=conn.createStatement();
            Statement stat_update=conn.createStatement();
            ResultSet rs_profile=stat_profile.executeQuery("select * from profile_table");
            ResultSet rs_update=stat_update.executeQuery("select * from update_table"); 

            boolean hasChanged=false; 
            String varGender="";
            String varSchool="";
            String varBirthday="";

            if(rs_update.last())
            {
                nextUpdateID=rs_update.getInt(1)+1;
            }

            if(rs_profile.last())
            {
                nextProfileID=rs_profile.getInt(1)+1;
            }

            if(rs.next())
            {

                if(!rs.getString(3).equals(userGender)) 
                {
                    int r_profile= stat_update.executeUpdate("Update useraccount_table set gender ='"+ userGender+"'where userID='"+userID+"'");
                   // int rs1= stat_profile.executeUpdate("insert into profile_table values"+"('"+nextProfileID+"','"+ userID+"','"+userGender+"','"+school+"','"+birthday+"')");
                    varGender=userGender;
                    hasChanged=true;
                    System.out.println("gender");
                }
                if(!rs.getString(4).equals(userSchool)) 
                {
                    int r_profile= stat_update.executeUpdate("Update useraccount_table set school ='"+ userSchool+"'where userID='"+userID+"'"); 
                  //  int rs1= stat_profile.executeUpdate("insert into profile_table values"+"('"+nextProfileID+"','"+ userID+"','"+gender+"','"+userSchool+"','"+birthday+"')");
                    varSchool=userSchool;
                    hasChanged=true;
                    System.out.println("school");


                }

                if(!rs.getString(5).equals(userBirthday)) 
                {
                    int r_profile= stat_update.executeUpdate("Update useraccount_table set birthday ='"+ userBirthday+"'where userID='"+userID+"'");
                    //int rs1= stat_profile.executeUpdate("insert into profile_table values"+"('"+nextProfileID+"','"+ userID+"','"+gender+"','"+school+"','"+userBirthday+"')");
                    varBirthday=userBirthday;
                    hasChanged=true;
                    System.out.println("bir");
                }
                if(hasChanged==false)
                {
                    return "no update in the file";
                }
                else
                {
                    int rs1= stat_profile.executeUpdate("insert into profile_table values"+"('"+nextProfileID+"','"+ userID+"','"+varGender+"','"+varSchool+"','"+varBirthday+"')");
                }


                int rs_Renew=stat_update.executeUpdate("insert into update_table values"+"('"+nextUpdateID +"','"+type+"','" +userID+"','" + nextProfileID+"')");
                temp_change = "changed successfuly";   
            }

          
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return("friend request failed because of exception");
            
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
        return"error in update profile";
    }
    public void sendMsg()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            System.out.println("internal error");
           // return "internalError"; 
            
        }
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
        Connection conn =null;
        Statement stat=null;
        ResultSet rs= null;
        
        try
        {
            list_history.clear();
            msg_history.clear();
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat= conn.createStatement();
            rs= stat.executeQuery("select * from notification_msgtable");
             
            boolean hasResult=false;
            //below its to do the hsitory conversation
            while(rs.next())
            {
                if((userID.equals(rs.getString(1)) && nameFormsg.equals(rs.getString(2))) || (userID.equals(rs.getString(2))&& nameFormsg.equals(rs.getString(1))))
                {
                // create arrList String name msg_history           
                    msg_history.add(new Notifications_msg(rs.getString(1),rs.getString(2),rs.getString(3)));

                    hasResult=true;
                }
            }
            if(hasResult ==false)
            {
                System.out.println("******You have no history MSG******");
               
               // history.add("******You have no history MSG******");
               // return "*You have no history MSG*";
            }
            else
            {
                for(int i =0; i < msg_history.size();i++)
                {
                 
                  //create new List to store history msg
                  //history=history+"History msg:"+"*** "+msg_history.get(i).getSender()+" : "+msg_history.get(i).getContents();
                  String s = (msg_history.get(i).getSender()+" : "+ msg_history.get(i).getContents());
                  
                  list_history.add(s);
                }
             // return ""+list_history;
            }
            
           
//            Statement stat_msg= conn.createStatement();
//            int rs_msg=stat_msg.executeUpdate("INSERT INTO notification_msgtable values"+ "('" + nameFormsg
//                                         + "', '" + userID + "', '"+ contentFormsg + "', '"+"ur"+"')");
       
             
        }
        catch(SQLException e)
        {
            System.out.println("Send msg failed because of execption");
            e.printStackTrace();
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
       // return "error in sending msg";
    }
    public void updateMsg()
    {
        
        Connection conn =null;
        Statement stat=null;
        ResultSet rs= null;
       
        
        try
        {
            final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat= conn.createStatement();
            rs= stat.executeQuery("select * from notification_msgtable");

            Statement stat_msg= conn.createStatement();
            int rs_msg=stat_msg.executeUpdate("INSERT INTO notification_msgtable values"+ "('" + nameFormsg
                                     + "', '" + userID + "', '"+ contentFormsg + "', '"+"ur"+"')"); 
            tempvalue_Msg="sent the message!!";
        }
           
        catch(SQLException e)
        {
            System.out.println("Send msg failed because of execption");
            e.printStackTrace();
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
        
    }
    public String sendReq()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return "internalError";    
        }
        final String DB_URL="jdbc:mysql://mis-sql.uhcl.edu/wangy8693";
        Connection conn =null;
        Statement stat=null;
        ResultSet rs= null;
        
        try
        {
            temp_requestInfo="";
            conn= DriverManager.getConnection(DB_URL,"wangy8693","1616764");
            stat= conn.createStatement();
            rs= stat.executeQuery("select * from notification_reqtable ");
        
                //insert into nofi_frerequest
                Statement stat_friReq= conn.createStatement();
                int rs_friReq=stat_friReq.executeUpdate("INSERT INTO notification_reqtable values"+ "('" + nameForrequest
                                                         + "', '" + userID  + "', '"+"ur"+"')");
                System.out.println("Sent it!");
                System.out.println("x: Finsihed the situlautaion");
                temp_requestInfo="Sent it";
                
            
        }
        catch(SQLException e)
        {
            System.out.println("Send request failed because of execption");
            e.printStackTrace();
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
        return "error in sending request";
        
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getSelectedToView() {
        return selectedToView;
    }

    public void setSelectedToView(String selectedToView) {
        this.selectedToView = selectedToView;
    }

    public ArrayList<String> getFriends_list() {
        return friends_list;
    }

    public void setFriends_list(ArrayList<String> friends_list) {
        this.friends_list = friends_list;
    }

    public ArrayList<Update> getUpdates_list() {
        return updates_list;
    }

    public void setUpdates_list(ArrayList<Update> updates_list) {
        this.updates_list = updates_list;
    }

    public List<String> getUpdateID() {
        return updateID;
    }

    public void setUpdateID(List<String> updateID) {
        this.updateID = updateID;
    }

    public String getSneha() {
        return sneha;
    }

    public void setSneha(String sneha) {
        this.sneha = sneha;
    }

    public String getSnehareceiver() {
        return snehareceiver;
    }

    public void setSnehareceiver(String snehareceiver) {
        this.snehareceiver = snehareceiver;
    }

    public String getAcceptfriend() {
        return acceptfriend;
    }

    public void setAcceptfriend(String acceptfriend) {
        this.acceptfriend = acceptfriend;
    }

    public String getTemp_accept() {
        return temp_accept;
    }

    public void setTemp_accept(String temp_accept) {
        this.temp_accept = temp_accept;
    }

    public String getFindPP() {
        return findPP;
    }

    public void setFindPP(String findPP) {
        this.findPP = findPP;
    }

    public String getTemp_matchID() {
        return temp_matchID;
    }

    public void setTemp_matchID(String temp_matchID) {
        this.temp_matchID = temp_matchID;
    }

    public String getTemp_msg() {
        return temp_msg;
    }

    public void setTemp_msg(String temp_msg) {
        this.temp_msg = temp_msg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTempCom() {
        return tempCom;
    }

    public void setTempCom(String tempCom) {
        this.tempCom = tempCom;
    }

    public ArrayList<String> getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(ArrayList<String> notificationID) {
        this.notificationID = notificationID;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getTemp_sender() {
        return temp_sender;
    }

    public void setTemp_sender(String temp_sender) {
        this.temp_sender = temp_sender;
    }

    public ArrayList<Notifications_msg> getNotifications_msglist() {
        return notifications_msglist;
    }

    public void setNotifications_msglist(ArrayList<Notifications_msg> notifications_msglist) {
        this.notifications_msglist = notifications_msglist;
    }

    public ArrayList<Notifications_FriendReq> getNotifications_frireqlist() {
        return notifications_frireqlist;
    }

    public void setNotifications_frireqlist(ArrayList<Notifications_FriendReq> notifications_frireqlist) {
        this.notifications_frireqlist = notifications_frireqlist;
    }

    public List<String> getList1() {
        return list1;
    }

    public void setList1(List<String> list1) {
        this.list1 = list1;
    }

    public List<String> getList2() {
        return list2;
    }

    public void setList2(List<String> list2) {
        this.list2 = list2;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getTempvalue() {
        return tempvalue;
    }

    public void setTempvalue(String tempvalue) {
        this.tempvalue = tempvalue;
    }

    public String getTemp_change() {
        return temp_change;
    }

    public void setTemp_change(String temp_change) {
        this.temp_change = temp_change;
    }

    public ArrayList<Notifications_msg> getMsg_history() {
        return msg_history;
    }

    public void setMsg_history(ArrayList<Notifications_msg> msg_history) {
        this.msg_history = msg_history;
    }

    public String getNameFormsg() {
        return nameFormsg;
    }

    public void setNameFormsg(String nameFormsg) {
        this.nameFormsg = nameFormsg;
    }

    public String getContentFormsg() {
        return contentFormsg;
    }

    public void setContentFormsg(String contentFormsg) {
        this.contentFormsg = contentFormsg;
    }

    public String getNameForrequest() {
        return nameForrequest;
    }

    public void setNameForrequest(String nameForrequest) {
        this.nameForrequest = nameForrequest;
    }

    public String getTemp_requestInfo() {
        return temp_requestInfo;
    }

    public void setTemp_requestInfo(String temp_requestInfo) {
        this.temp_requestInfo = temp_requestInfo;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public ArrayList<Hashtag> getFindHashtage() {
        return findHashtage;
    }

    public void setFindHashtage(ArrayList<Hashtag> findHashtage) {
        this.findHashtage = findHashtage;
    }

    public ArrayList<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(ArrayList<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

    public String getTemp_keyword() {
        return temp_keyword;
    }

    public void setTemp_keyword(String temp_keyword) {
        this.temp_keyword = temp_keyword;
    }

    public String getSelecedHashtag() {
        return selecedHashtag;
    }

    public void setSelecedHashtag(String selecedHashtag) {
        this.selecedHashtag = selecedHashtag;
    }

    public ArrayList<String> getRankHash() {
        return rankHash;
    }

    public void setRankHash(ArrayList<String> rankHash) {
        this.rankHash = rankHash;
    }

    public List<String> getList3() {
        return list3;
    }

    public void setList3(List<String> list3) {
        this.list3 = list3;
    }

    public List<String> getList_friendProfile() {
        return list_friendProfile;
    }

    public void setList_friendProfile(List<String> list_friendProfile) {
        this.list_friendProfile = list_friendProfile;
    }

    public List<String> getList_updateProfile() {
        return list_updateProfile;
    }

    public void setList_updateProfile(List<String> list_updateProfile) {
        this.list_updateProfile = list_updateProfile;
    }

    public List<String> getList_history() {
        return list_history;
    }

    public void setList_history(List<String> list_history) {
        this.list_history = list_history;
    }

    public String getTempvalue_Msg() {
        return tempvalue_Msg;
    }

    public void setTempvalue_Msg(String tempvalue_Msg) {
        this.tempvalue_Msg = tempvalue_Msg;
    }

    public int getIntSelection() {
        return intSelection;
    }

    public void setIntSelection(int intSelection) {
        this.intSelection = intSelection;
    }
    
    
    
}
