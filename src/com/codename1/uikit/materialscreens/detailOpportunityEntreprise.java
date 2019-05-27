/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
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
import com.codename1.ui.util.Resources;

import java.io.IOException;
import smartstart.Entities.Blogpost;
import smartstart.Entities.Complaint;
import smartstart.Entities.Opportunity;
import smartstart.services.ServiceBlogpost;
import smartstart.services.ServiceComplaints;
import smartstart.services.ServiceOpportunity;
import smartstart.services.ServiceOpportunityEntreprise;

/**
 *
 * @author HP
 */
public class detailOpportunityEntreprise extends SideMenuBaseForm {
     Form f;
    TextField Jobtitlee;
    TextField Jobcat;
    TextField jobdesc;
    TextField  Budgett;
    TextField  duration;
    Button btnajout,btnaff;
    SpanLabel lb;

    public detailOpportunityEntreprise(Resources res, int id) {
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
                                new Label("Opportunity check", "Title"),
                                new Label("Detail Section", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        Container List = new Container(BoxLayout.y());
        ServiceOpportunityEntreprise cs = new ServiceOpportunityEntreprise();
        
        Opportunity col = new Opportunity();
        System.out.println(id);

        for (Opportunity i : cs.getList2()) {
            if (i.getId_Opp()== id) {
                Label Jobtitle = new Label();
                Label Jobcate = new Label();
                Label Budget = new Label();
                Label Dura = new Label();
                Label Jobdesc = new Label();
             //   SpanLabel Jobdesc = new SpanLabel();
        //    SpanLabel p = new SpanLabel();
                Jobtitle.setText("Job Title :"+i.getJob_title()+ "\n\n");
                Jobcate.setText("Job Title :"+i.getJob_category()+ "\n\n");
                Budget.setText("Budget :"+i.getBudget()+ "\n\n");
                Dura.setText("Duration :"+i.getJob_Duration()+ "\n\n");
                Jobdesc.setText("Description :"+i.getJob_description());
    
                add(Jobtitle);
                add(Jobcate);
                add(Budget);
                add(Dura);
                add(Jobdesc);
                
                 Button Delete = new Button("Delete");
        Button Update = new Button("Update");
        add(Delete);
        add(Update);
     
        
        Update.addActionListener((x) -> {
           
            f = new Form("Update an opportunity");
     
           btnajout = new Button("Update");
           Jobtitlee = new TextField("","Opportunity Title");
        Jobcat= new TextField("","Category");
        jobdesc=new TextField("","Description");
        Budgett=new TextField("","Budget");
        duration=new TextField("","Duration");
        
        Jobtitlee.setText(i.getJob_title());
         Jobcat.setText(i.getJob_category());
            jobdesc.setText(i.getJob_description());
            duration.setText(i.getJob_Duration());
        
        f.add(Jobtitlee);
        f.add(Jobcat);
        f.add(jobdesc);
        f.add(duration);
        f.add(btnajout);
        
        f.show();
        btnajout.addActionListener((u) -> {
            ServiceOpportunityEntreprise ser1 = new ServiceOpportunityEntreprise();
            Opportunity t1 = new Opportunity();
            t1.setJob_title(Jobtitlee.getText());
            t1.setJob_category(Jobcat.getText());
            t1.setJob_description(jobdesc.getText());
            t1.setJob_Duration(duration.getText());
            t1.setId(id);
         
           // ser1.mod(t1);
            ToastBar.showMessage("Successfully Updated !", FontImage.MATERIAL_CHECK);

                ComplaintAffichage b=new ComplaintAffichage(res);
                b.show();
            

        });
        });

        Delete.addActionListener(e -> {
 
            if (Dialog.show("Remove", "  Are you sure to delete this Opportunity?", "Confirm", "Cancel")) {
                cs.delete(i);
              ToastBar.showMessage("Successfully Deleted !", FontImage.MATERIAL_CHECK);

                OpportunityAffichageEntreprise b=new OpportunityAffichageEntreprise(res);
                b.show();
            }

        });

            }
        }
       
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
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(), first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

    public detailOpportunityEntreprise() {

        f = new Form();
        lb = new SpanLabel("");
        f.add(lb);
        ServiceBlogpost serviceTask = new ServiceBlogpost();
        lb.setText(serviceTask.getList2().toString());

        f.getToolbar().addCommandToRightBar("back", null, (ev) -> {
            BlogForm h = new BlogForm();
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
