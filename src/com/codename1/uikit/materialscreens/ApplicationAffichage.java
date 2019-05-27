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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
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
import com.codename1.ui.util.Resources;
import smartstart.Entities.Application;
import smartstart.Entities.Complaint;
import smartstart.services.ServiceApplication;
import smartstart.services.ServiceBlogpost;
import smartstart.services.ServiceComplaints;

/**
 *
 * @author Marrr
 */
public class ApplicationAffichage extends SideMenuBaseForm{
    Form f;
    TextField jobTitle;
    TextField jobCategory;
    TextField  jobDescription;
    TextField  status;
    Button btnWithdraw;
    Button btnDelete;
    SpanLabel lb;
    public ApplicationAffichage(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("BackgroundRect.png");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_BOOK);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        ServiceApplication cx= new ServiceApplication();
        String x=cx.acceptanceRate();
        String firstFourChars;
        if (x.length() > 4)
{
    firstFourChars = x.substring(0, 4);
}
else
{
    firstFourChars = x;
}
        Container remainingTasks = BoxLayout.encloseY(
                        
                new Label(firstFourChars+"%", "CenterTitle"),
                        new Label("ACCEPTANCE RATE", "CenterSubTitle")
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
                                    new Label("Applications", "Title"),
                                    new Label("", "SubTitle")
                                )
                            ).add(BorderLayout.WEST, profilePicLabel),
                        GridLayout.encloseIn(2, remainingTasks, completedTasks)
                );       
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
      
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
                        
        add(new Label("Today", "TodayTitle"));
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
         Container List= new Container(BoxLayout.y());
        ServiceApplication cs= new ServiceApplication();
        for (Application i : cs.getList2())
    {   
            
         
                MultiButton mb= new MultiButton();
                
               
                mb.setScrollable(true);
                mb.setTextLine1(i.getOpportunity().getJob_title());
                mb.setTextLine2(i.getState());
                
                 Button b = new Button("WITHDRAW");
                 Button delete =new Button("DELETE");
                List.add(mb);
                if ((mb.getTextLine2().equals("ACCEPTED")) ||(mb.getTextLine2().equals("REJECTED")) || (mb.getTextLine2().equals("APPLIED")))
                {List.add(b);}
                if (mb.getTextLine2().equals("WITHDRAWN"))
                 {List.add(delete);}
                 mb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
             }
            });
                 
                    b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
              if (Dialog.show("Withdraw","Are you sure?","Yes","No")) {
             cs.withdraw(i);
             ToastBar.showMessage("Successfully Withdrawn !", FontImage.MATERIAL_CHECK);
             mb.setTextLine2("WITHDRAWN");
             List.removeComponent(b);
             List.add(delete);
             
             
             
            }}
            });
           
                     delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
              if (Dialog.show("Delete","Are you sure?","Yes","No")) {
             cs.delete(i);
             
             ToastBar.showMessage("Successfully Deleted !", FontImage.MATERIAL_CHECK);
             ApplicationAffichage x=new ApplicationAffichage(res);
                x.show();
                
             
             
            }}
            });
           
          
    }
    add(List);
        
        
//        addButtonBottom(arrowDown, "Finish landing page concept", 0xd997f1, true);
//        addButtonBottom(arrowDown, "Design app illustrations", 0x5ae29d, false);
//        addButtonBottom(arrowDown, "Javascript training ", 0x4dc2ff, false);
//        addButtonBottom(arrowDown, "Surprise Party for Matt", 0xffc06f, false);
        setupSideMenu(res);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   public ApplicationAffichage() {
        
        f = new Form();
        lb = new SpanLabel("");
        f.add(lb);
        ServiceApplication serviceTask=new ServiceApplication();
        lb.setText(serviceTask.getList2().toString());
        
          f.getToolbar().addCommandToRightBar("back", null, (ev)->{ApplicationForm h=new ApplicationForm();
          h.getF().show();
          });
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
