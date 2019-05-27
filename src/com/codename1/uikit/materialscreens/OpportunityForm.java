/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import smartstart.Entities.Blogpost;
import smartstart.Entities.Opportunity;
import smartstart.services.ServiceBlogpost;
import smartstart.services.ServiceOpportunity;
import smartstart.services.ServiceOpportunityEntreprise;

/**
 *
 * @author HP
 */
public class OpportunityForm extends SideMenuBaseForm{
    
    Form f;
    TextField JobTitle;
    TextField JobCategory;
    TextField JobDescription;
    TextField Budget;
    TextField JobDuration;
    
    Button btnajout,btnaff;

    public OpportunityForm()  {
        
        
    }
    public OpportunityForm(Resources res) {
        super(BoxLayout.y());
        
         Container List= new Container(BoxLayout.y());
        ServiceBlogpost cs= new ServiceBlogpost();
       f = new Form("Add An oppotunity");
        JobTitle = new TextField("","Opportunity Title");
        JobCategory= new TextField("","Opportunity Category");
        JobDescription=new TextField("","Opportunity Description");
        JobDuration=new TextField("","Opportunity Duration");
        Budget =new TextField("","Opportunity budget");
        btnajout = new Button("ajouter");
        
        f.add(JobTitle);
        f.add(JobCategory);
        f.add(JobDescription);
        f.add(JobDuration);
        f.add(Budget);
        f.add(btnajout);
        f.show();
        btnajout.addActionListener((e) -> {
            
            ServiceOpportunityEntreprise ser = new ServiceOpportunityEntreprise();
             
            Opportunity t = new Opportunity();
            //ser.ajoutTask(t);
            

        });
       
        
//        addButtonBottom(arrowDown, "Finish landing page concept", 0xd997f1, true);
//        addButtonBottom(arrowDown, "Design app illustrations", 0x5ae29d, false);
//        addButtonBottom(arrowDown, "Javascript training ", 0x4dc2ff, false);
//        addButtonBottom(arrowDown, "Surprise Party for Matt", 0xffc06f, false);
        setupSideMenu(res);
    }
    
    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(),  first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }
    
    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if(first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }
    
      public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }


    
}
