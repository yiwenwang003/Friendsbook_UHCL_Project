/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Timi
 */
public class Post {
    private String postID;
    private String userid;
    private String contents;
  
    
    public Post( String uid, String c)
    {
        //postID=pid;
        userid=uid;
        contents=c;
    
        
    }
    //get and set

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


}
