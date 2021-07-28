/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Timi
 */
public class Hashtag {
  private String keyword;
    private int occur;
    
    public Hashtag(String k, int o)
    {
        keyword=k; 
        occur=o;
        
    }
    //get and set

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getOccur() {
        return occur;
    }

    public void setOccur(int occur) {
        this.occur = occur;
    }

    
}
