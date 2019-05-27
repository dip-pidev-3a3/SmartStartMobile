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
import smartstart.Entities.Blogpost;
import smartstart.Entities.Complaint;
import smartstart.Entities.Opportunity;
import smartstart.services.ServiceApplication;
import smartstart.services.ServiceBlogpost;
import smartstart.services.ServiceComplaints;
import smartstart.services.ServiceOpportunity;

/**
 *
 * @author HP
 */
public class OpportunityAffichage extends SideMenuBaseForm {
      Form f;
      int Number ;
    TextField JobTitle;
    TextField JobCategory;
    TextField JobDescription;
    TextField Budget;
    TextField JobDuration;
    Picker DateExpiry ;
    Button btnajout,btnaff,btnback;
    SpanLabel lb;
    public OpportunityAffichage(Resources res) {
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
        String y=cx.acceptanceRate();
        String firstFourChars;
        if (y.length() > 4)
{
    firstFourChars = y.substring(0, 4);
}
else
{
    firstFourChars = y;
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
        JobTitle= new TextField("","JobTitle");
        JobCategory= new TextField("","Job Category");
        JobDescription=new TextField("","Job Description");
        JobDuration=new TextField("","Job Duration");
        DateExpiry=new Picker();
        DateExpiry.setType(Display.PICKER_TYPE_DATE);
        Budget=new TextField("","Enter your budget");
        
        btnajout = new Button("ajouter");
        btnback = new Button("Back");
        f.add(JobTitle);
        f.add(JobCategory);
        f.add(JobDescription);
        f.add(JobDuration);
        f.add(Budget);
        f.add(DateExpiry);
        f.add(btnajout);
        f.add(btnback);
        f.show();
       btnajout.addActionListener((x) -> {
            ServiceOpportunity ser = new ServiceOpportunity();
            Opportunity t = new Opportunity();
            t.setJob_title(JobTitle.getText());
            t.setJob_category(JobCategory.getText());
            t.setJob_description(JobDescription.getText());
            t.setJob_Duration(JobDuration.getText());
            t.setBudget(Integer.parseInt(Budget.getText()));
            ser.ajoutTask(t);
            
            ToastBar.showMessage("Successfully Added !", FontImage.MATERIAL_CHECK);

                OpportunityAffichage b=new OpportunityAffichage(res);
                b.show();
            

        });
             btnback.addActionListener((x) -> {
            
            ToastBar.showMessage("Maybe Another Time !", FontImage.MATERIAL_CHECK);
                OpportunityAffichage b=new OpportunityAffichage(res);
                b.show();
        });                

            
        });
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
                        
        add(new Label("All Opportunities", "TodayTitle"));
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
         Container List= new Container(BoxLayout.y());
        ServiceOpportunity cs= new ServiceOpportunity();
       
        ArrayList<Opportunity> listops = new ArrayList<>();
         listops= cs.getList2();
         
        for (Opportunity i : cs.getList2())
    {
         
            
                MultiButton mb= new MultiButton();    
                mb.setScrollable(true);
                mb.setTextLine1(i.getJob_title());
                mb.setTextLine2(i.getJob_category());
                
                List.add(mb);
                 mb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                 detailOpportunity b=new detailOpportunity(res,i.getId_Opp());
                b.show();
             
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
     public OpportunityAffichage() {
        
        f = new Form();
        lb = new SpanLabel("");
        f.add(lb);
        ServiceBlogpost serviceTask=new ServiceBlogpost();
        lb.setText(serviceTask.getList2().toString());
        
          f.getToolbar().addCommandToRightBar("back", null, (ev)->{BlogForm h=new BlogForm();
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
