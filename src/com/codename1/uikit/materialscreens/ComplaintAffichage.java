/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.codename1.uikit.materialscreens;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
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
import smartstart.services.ServiceBlogpost;
import smartstart.services.ServiceComplaints;

/**
 * Represents a user profile in the app, the first form we open after the walkthru
 *
 * @author Mounir Achir
 */
public class ComplaintAffichage extends SideMenuBaseForm {
     Form f;
    TextField subject;
    TextField content;
    TextField  mail;
    Button btnajout,btnaff;
    SpanLabel lb;
    public ComplaintAffichage(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("white.png").scaled(300,300);
      
       
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");

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
                                    new Label("Complaints", "Title"),
                                    new Label("", "SubTitle")
                                )
                            ).add(BorderLayout.WEST, profilePicLabel),
                        GridLayout.encloseIn(2, remainingTasks, completedTasks)
                );       
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        fab.addActionListener(e->{
             f = new Form("Add a Complaint");
        content= new TextField("","Content of the complaint");
        subject= new TextField("","Topic of the complaint");
        mail=new TextField("","Enter your Email");
        
        btnajout = new Button("ajouter");
        
        f.add(subject);
        f.add(content);
        f.add(mail);
        f.add(btnajout);
        f.show();
        btnajout.addActionListener((x) -> {
            if(content.getText().isEmpty()||subject.getText().isEmpty()||mail.getText().isEmpty())
             {
                Dialog.show("Error","You have an Empty Field" , "ok",null);
            }
            else{
            ServiceComplaints ser = new ServiceComplaints();
            Complaint t = new Complaint();
            t.setSubject(subject.getText());
            t.setContent(content.getText());
            t.setMail_user(mail.getText());
            ser.ajoutTask(t);
            ToastBar.showMessage("Successfully Added !", FontImage.MATERIAL_CHECK);

                ComplaintAffichage b=new ComplaintAffichage(res);
                b.show();}
            

        });
                            

            
        });
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
                        
        add(new Label("Today", "TodayTitle"));
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
         Container List= new Container(BoxLayout.y());
        ServiceComplaints cs= new ServiceComplaints();
        for (Complaint i : cs.getList2())
    {   
            
         
                MultiButton mb= new MultiButton();
                
               
                mb.setScrollable(true);
                mb.setTextLine1(i.getSubject());
                mb.setTextLine2(i.getContent());
                mb.setTextLine1(i.getMail_user());
                List.add(mb);
                 mb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                detailComplaint b=new detailComplaint(res,i.getId_complaint());
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
     public ComplaintAffichage() {
        
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
