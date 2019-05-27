/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;


import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Date;
import smartstart.Entities.Application;
import smartstart.Entities.Blogpost;
import smartstart.Entities.Complaint;
import smartstart.Entities.Opportunity;
import smartstart.services.ServiceBlogpost;
import smartstart.services.ServiceComplaints;
import smartstart.services.ServiceOpportunity;
import smartstart.services.ServicesApplications;


/**
 *
 * @author HP
 */
public class ApplicationsAffichage extends SideMenuBaseForm {
      Form f;
      int Number ;
   
    public ApplicationsAffichage(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("duke.png");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_BOOK);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        Container remainingTasks = BoxLayout.encloseY(
                        new Label("12", "CenterTitle"),
                        new Label("remaining tasks", "CenterSubTitle")
                );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                        new Label("32", "CenterTitle"),
                        new Label("completed tasks", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");
        
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Opportunities", "Title"),
                                    new Label("", "SubTitle")
                                )
                            ).add(BorderLayout.WEST, profilePicLabel),
                        GridLayout.encloseIn(2, remainingTasks, completedTasks)
                );       
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        fab.addActionListener(e->{
        f = new Form("Add an opportunity");
        
                     

            
        });
        
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
                        
        add(new Label("All your Applicants", "TodayTitle"));
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
         Container List= new Container(BoxLayout.y());
        ServicesApplications cs= new ServicesApplications();
       
        ArrayList<Application> listops = new ArrayList<>();
         listops= cs.getList2();
         
        for (Application i : cs.getList2())
    {
         
            String s = i.getOpportunity().getJob_title();
                MultiButton mb= new MultiButton();    
                mb.setScrollable(true);
                mb.setTextLine1(i.getFreelancer().getUsername());
                mb.setTextLine3(s);
                mb.setTextLine2(i.getState());
                Button bx = new Button("Accept");
                Button bxr = new Button("Reject");
                
                List.add(mb);
                List.add(bx);
                List.add(bxr);
                 
                mb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.err.println("HADHOOOOONA");
                System.out.println(i.getFreelancer().getId());
                UserView uv = new UserView(res,i.getFreelancer().getId());
                uv.show();
                
            }
            });
                 
                 bx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Dialog.show("You are about to accept","Are you sure?","Yes","No"))
                {
                
                ServicesApplications sec = new ServicesApplications();
                sec.Accept(i);  
                ToastBar.showMessage("Your applicant has been accepted", FontImage.MATERIAL_DIRECTIONS_BIKE);
                mb.setTextLine2("ACCEPTED");
                List.removeComponent(bx);
                
                }
           
            }
            });
                 
                 bxr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Dialog.show("You are about to reject","Are you sure?","Yes","No"))
                {   
                
                ServicesApplications sec = new ServicesApplications();
                sec.Accept(i);  
                ToastBar.showMessage("Your applicant has been Rejected..", FontImage.MATERIAL_DIRECTIONS_BIKE);
                mb.setTextLine2("REJECTED");
                List.removeComponent(bxr);
                
                }
             
            }
            });
           
           
          
    }
    add(List);
        
        
//        addButtonBottom(arrowDown, "Finish landing page concept", 0xd997f1, true);
//        addButtonBottom(arrowDown, "Design app illustrations", 0x5ae29d, false);
//        addButtonBottom(arrowDown, "Javascript training ", 0x4dc2ff, false);
//        addButtonBottom(arrowDown, "Surprise Party for Matt", 0xffc06f, false);
        setupSideMenu(res);
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
