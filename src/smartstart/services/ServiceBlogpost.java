/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartstart.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import smartstart.Entities.Blogpost;
import smartstart.Entities.fos_user;


/**
 *
 * @author bhk
 */
public class ServiceBlogpost {
     ArrayList<Blogpost> listpost = new ArrayList<Blogpost>();
    Blogpost c = new Blogpost();

    public void ajoutTask(Blogpost ta) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_Add/"+ta.getArticle_content()+"/"+ta.getArticle_title()+"/"+ta.getPost_type()+"/"+ta.getPost_content(); //création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public ArrayList<Blogpost> parseListTaskJson(String json) {

        ArrayList<Blogpost> listpost = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

          
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
                       
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            
            for (Map<String, Object> obj : list) {
               
                Blogpost e = new Blogpost();
                
                e.setPostId(Integer.parseInt(obj.get("postId").toString()));
                e.setArticle_content(obj.get("articleContent").toString());               
                e.setArticle_title(obj.get("articleTitle").toString());
                e.setPost_type(obj.get("postType").toString());  
                e.setPost_content(obj.get("postContent").toString());

               
                System.out.println(e);
                
                listpost.add(e);

            }

        } catch (IOException ex) {
        }
        
       
        System.out.println(listpost);
        return listpost;

    }
    
    
    public ArrayList<Blogpost> getList2(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_showAll");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBlogpost ser = new ServiceBlogpost();
                listpost = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listpost;
    }
     public Blogpost detail(int id) {
       ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_show/"+Integer.toString(id));
        String s="http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_show/"+Integer.toString(id);
        System.out.println(s);
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               ServiceBlogpost ser = new ServiceBlogpost();
               
                c = ser.getListTaskaa(new String(con.getResponseData()));
                
                System.out.println(c);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return c;
    }
      public Blogpost getListTaskaa(String json) {

       
            
            
            JSONParser j = new JSONParser();

            Map<String, Object> tasks;
        try {
            tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
             c= (Blogpost) (List<Map<String, Object>>) tasks.get("root");
        } catch (IOException ex) {
            //Logger.getLogger(ColocationServices.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            Blogpost e = new Blogpost();
          
           e.setArticle_title(c.getArticle_title());
            e.setArticle_content(c.getArticle_content());
            
            e.setPost_type(c.getPost_type());
            
            
            System.out.println(e);
            
            
            
            
            
        
        return e;
    }
      public boolean delete(Blogpost u) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl( "http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_Delete/"+u.getPostId());
        connection.addResponseListener(a->{
            //Dialog.show(null, new String(connection.getResponseData()), "Ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        return true;
    }
       public void mod(Blogpost ta) {
       
        ConnectionRequest connection = new ConnectionRequest();
       connection.setUrl( "http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_Update/"+ta.getPostId()+"/"+ta.getArticle_content()+"/"+ta.getArticle_title()+"/"+ta.getPost_type()+"/"+ta.getPost_content());
        
        NetworkManager.getInstance().addToQueue(connection);
        
    

}
         public void SendMail(String mail,String cat)
       {
           ConnectionRequest connection = new ConnectionRequest();
       connection.setUrl( "http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_sendMail/"+mail+"/"+cat);
        
        NetworkManager.getInstance().addToQueue(connection);
           
       }
         public ArrayList<Blogpost> findmy( int id)
         {
              ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_GetMyt/"+id);  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBlogpost ser = new ServiceBlogpost();
                listpost = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listpost;
             
         }
          public ArrayList<Blogpost> findByCat(String cat)
         {
              ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartStartSymfony-master/web/app_dev.php/blog/M_findBycat/"+cat);  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBlogpost ser = new ServiceBlogpost();
                listpost = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listpost;
             
         }
         
       
}
