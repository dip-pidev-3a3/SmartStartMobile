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
import smartstart.Entities.Blogpost;
import smartstart.Entities.Complaint;

/**
 *
 * @author bhk
 */
public class ServiceComplaints {
     ArrayList<Complaint> listpost = new ArrayList<Complaint>();
    Complaint c = new Complaint();

    public void ajoutTask(Complaint ta) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/SmartStartSymfony-master/web/app_dev.php/Complaints/M_Add/"+ta.getSubject()+"/"+ta.getContent()+"/"+ta.getMail_user(); //création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }

    public ArrayList<Complaint> parseListTaskJson(String json) {

        ArrayList<Complaint> listpost = new ArrayList<>();

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

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Complaint e = new Complaint();
       float f=Float.parseFloat(obj.get("idComplaint").toString());
                e.setId_complaint((int) f);

                 e.setContent(obj.get("content").toString());               
                e.setSubject(obj.get("subject").toString());
                e.setMail_user(obj.get("mailUser").toString());
               
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
    
    
   
    
    public ArrayList<Complaint> getList2(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartStartSymfony-master/web/app_dev.php/Complaints/M_showAll/9");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceComplaints ser = new ServiceComplaints();
                listpost = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listpost;
    }
     public Complaint detail(int id) {
       ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartStartSymfony-master/web/app_dev.php/Complaints/M_show/"+Integer.toString(id));
        String s="http://localhost/SmartStartSymfony-master/web/app_dev.php/Complaints/M_show/"+Integer.toString(id);
        System.out.println(s);
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               ServiceComplaints ser = new ServiceComplaints();
               
                c = ser.getListTaskaa(new String(con.getResponseData()));
                
                System.out.println(c);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return c;
    }
      public Complaint getListTaskaa(String json) {

       
            
            
            JSONParser j = new JSONParser();

            Map<String, Object> tasks;
        try {
            tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
             c= (Complaint) (List<Map<String, Object>>) tasks.get("root");
        } catch (IOException ex) {
            //Logger.getLogger(ColocationServices.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            Complaint e = new Complaint();
          
           e.setContent(c.getContent());
            e.setSubject(c.getSubject());
            
            e.setMail_user(c.getMail_user());
            
            
            System.out.println(e);
            
            
            
            
            
        
        return e;
    }
      public boolean delete(Complaint u) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl( "http://localhost/SmartStartSymfony-master/web/app_dev.php/Complaints/M_Delete/"+u.getId_complaint());
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
