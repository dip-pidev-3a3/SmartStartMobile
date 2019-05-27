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
import java.util.Date;
import java.util.List;
import java.util.Map;
import smartstart.Entities.Application;
import smartstart.Entities.Complaint;
import smartstart.Entities.Opportunity;
import smartstart.Entities.fos_user;

/**
 *
 * @author HP
 */
public class ServicesApplications {
      ArrayList<Application> listpost = new ArrayList<Application>();
    Opportunity c = new Opportunity();

    public void ajoutTask(Opportunity ta) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/smartstart/web/app_dev.php/opportunity/add/"+ta.getJob_title()+"/"+ta.getJob_category()+"/"+ta.getJob_description()+"/"+ta.getBudget()+"/"+ta.getJob_Duration(); //création de l'URL
      con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public ArrayList<Application> parseListTaskJson(String json) {

        ArrayList<Application> listpost = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
            */
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
                       
            
            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
            */
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
             Date d = new Date();
             
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Application e = new Application();
                float id=Float.parseFloat(obj.get("idApplication").toString());
                e.setId((int) id);
                
                e.setState(obj.get("state").toString());
                e.getOpportunity().setJob_title(obj.get("idOpportunity").toString());
                    e.getFreelancer().setUsername(obj.get("idFreelancer").toString());
                System.out.println(e);
               
                listpost.add(e);

            }

        } catch (IOException ex) {
        }
        
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        System.out.println(listpost);
        return listpost;

    }
    
    
   
    
    public ArrayList<Application> getList2(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/smartstart/web/app_dev.php/opportunity/allApps");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServicesApplications ser = new ServicesApplications();
                                listpost = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listpost;
    }
    
     
    public boolean Accept(Application u) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl("http://localhost/smartstart/web/app_dev.php/opportunity/Accept/" + u.getId());
        connection.addResponseListener(a -> {
            //Dialog.show(null, new String(connection.getResponseData()), "Ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        return true;
    } 
     public boolean Reject(Application u) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl("http://localhost/smartstart/web/app_dev.php/opportunity/Reject/" + u.getId());
        connection.addResponseListener(a -> {
            //Dialog.show(null, new String(connection.getResponseData()), "Ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        return true;
    } 
     public Opportunity detail(int id) {
       ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/smartstart/web/app_dev.php/opportunity/show/"+Integer.toString(id));
        String s="http://localhost/smartstart/web/app_dev.php/opportunity/show/"+Integer.toString(id);
        System.out.println(s);
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               ServiceOpportunity ser = new ServiceOpportunity();
                c = ser.getListTaskaa(new String(con.getResponseData()));
                System.out.println(c);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return c;
    }
      public Opportunity getListTaskaa(String json) {

       
            
            
            JSONParser j = new JSONParser();

            Map<String, Object> tasks;
        try {
            tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
             c= (Opportunity) (List<Map<String, Object>>) tasks.get("root");
        } catch (IOException ex) {
            //Logger.getLogger(ColocationServices.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            Opportunity e = new Opportunity();
          
           e.setJob_title(c.getJob_title());
            e.setJob_category(c.getJob_category());
            e.setJob_description(c.getJob_description());
            e.setBudget(c.getBudget());
            
            
            System.out.println(e);
            
        return e;
    }
      
      public boolean delete(Opportunity u) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl( "http://localhost/smartstart/web/app_dev.php/opportunity/delete/"+u.getId_Opp());
        connection.addResponseListener(a->{
            //Dialog.show(null, new String(connection.getResponseData()), "Ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        return true;
    }
       public void mod(Complaint ta) {
       
        ConnectionRequest connection = new ConnectionRequest();
       connection.setUrl( "http://localhost/SmartStartSymfony-master/web/app_dev.php/Complaints/M_Update/"+ta.getSubject()+"/"+ta.getContent()+"/"+ta.getMail_user()+"/"+ta.getId_complaint());
        
        NetworkManager.getInstance().addToQueue(connection);
        
    

}
    
}
