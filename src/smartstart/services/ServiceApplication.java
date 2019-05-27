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
import com.codename1.util.BigDecimal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import smartstart.Entities.Application;
import smartstart.Entities.Blogpost;
import smartstart.Entities.Opportunity;

/**
 *
 * @author Marrr
 */
public class ServiceApplication {

    ArrayList<Application> listApps = new ArrayList<Application>();
    ArrayList<Opportunity> listOpps = new ArrayList<Opportunity>();
    Application c = new Application();

    public ArrayList<Application> parseListTaskJson(String json) {

        ArrayList<Application> listTasks = new ArrayList<>();

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
                Application e = new Application();

                float id = Float.parseFloat(obj.get("idApplication").toString());

                e.setId((int) id);
                e.getOpportunity().setJob_title(obj.get("idOpportunity").toString());

                e.setState(obj.get("state").toString());

                System.out.println(e);

                listTasks.add(e);

            }

        } catch (IOException ex) {
        }

        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
         */
        System.out.println(listTasks);
        return listTasks;

    }

    public ArrayList<Application> getList2() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartstartSprintSymfony-master/web/app_dev.php/Application/mobileMyApps");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceApplication ser = new ServiceApplication();
                listApps = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listApps;
    }

    public boolean delete(Application u) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl("http://localhost/SmartstartSprintSymfony-master/web/app_dev.php/Application/mobileDelete/" + u.getId());
        connection.addResponseListener(a -> {
            //Dialog.show(null, new String(connection.getResponseData()), "Ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        return true;
    }
    
     public boolean apply(int oppId) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl("http://localhost/SmartstartSprintSymfony-master/web/app_dev.php/Application/apply/" + oppId);
        connection.addResponseListener(a -> {
            //Dialog.show(null, new String(connection.getResponseData()), "Ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        return true;
    }

    public boolean withdraw(Application u) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl("http://localhost/SmartstartSprintSymfony-master/web/app_dev.php/Application/mobileWithdraw/" + u.getId());
        connection.addResponseListener(a -> {
            //Dialog.show(null, new String(connection.getResponseData()), "Ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        return true;
    }
    
    public String acceptanceRate()
    { try{
     ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl("http://localhost/SmartstartSprintSymfony-master/web/app_dev.php/Application/acceptanceRate");
        connection.addResponseListener(a -> {
            //Dialog.show(null, new String(connection.getResponseData()), "Ok", null);
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        String str = new String(connection.getResponseData());
       
    
    System.err.println(str);  //ytala3 fi valeur 8alta
    return str;
    
    } 
    
    
    catch (Exception e) {
    }
        return "";
    
    
    }

}
