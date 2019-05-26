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
import smartstart.Entities.Contract;

/**
 *
 * @author Cascada
 */
public class ContractService {

    public ArrayList<Contract> parseListTaskJson(String json) {

        ArrayList<Contract> listTasks = new ArrayList<>();

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
                Contract c = new Contract();

                float id = Float.parseFloat(obj.get("idc").toString());

                c.setId_contract((int) id);
                if(obj.get("start")!=null){
                c.setStart_date(obj.get("start").toString());
                }
                if(obj.get("finish")!=null){
                c.setFinish_date(obj.get("finish").toString());
                }
                if(obj.get("sum")!=null){
                float sum = Float.parseFloat(obj.get("sum").toString());
                c.setSum(sum);
                }
                if(obj.get("payment")!=null){
                c.setPayment_method(obj.get("payment").toString());
                }
                c.setFreelancer(obj.get("freelancer").toString());
                c.setEntreprise(obj.get("entreprise").toString());
                c.setJobTitle(obj.get("jobTitle").toString());
                c.setJobDescription(obj.get("jobDescription").toString());
                listTasks.add(c);

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
    ArrayList<Contract> listContracts = new ArrayList<>();
    
    public ArrayList<Contract> entrepriseContract(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartStart/web/app_dev.php/contract/entrepriseContracts/11");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ContractService ser = new ContractService();
                listContracts = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listContracts;
    }
    
    public ArrayList<Contract> entrepriseLowContracts(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/SmartStart/web/app_dev.php/contract/low/11");  
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ContractService ser = new ContractService();
                listContracts = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listContracts;
    }
    
    public void delete(int id) {
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl( "http://localhost/SmartStart/web/app_dev.php/contract/delete/"+id);
        connection.addResponseListener(a->{
            
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
        
    }
    public void update(int id,String start,String finish,String sum,String payment){
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl( "http://localhost/SmartStart/web/app_dev.php/contract/update/"+id+"/"+start+"/"+finish+"/"+sum+"/"+payment);
        connection.addResponseListener(a->{
            
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
    }
    
    public void print(int id,int idU){
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl( "http://localhost/SmartStart/web/app_dev.php/contract/print/"+id+"/"+idU);
        connection.addResponseListener(a->{
            
        });
        NetworkManager.getInstance().addToQueueAndWait(connection);
    }

}
