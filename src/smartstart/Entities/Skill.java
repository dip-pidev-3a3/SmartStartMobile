/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartstart.Entities;

/**
 *
 * @author Marrr
 */
public class Skill {
    private int id;
    private String name;
    public Skill(int id,String name){this.id=id; this.name=name;}
    public Skill(int id){this.id=id;}
     public Skill(){}
    
    
    public int getId(){return id;}
    public String getName(){return name;}
    public void setId(int id){this.id=id;}
    public void setName(String name){this.name=name;}
    
    
    
    @Override
    public String toString()
    {
        return "Skill id :"+id+"Skill name : "+name;
    }

    
}
