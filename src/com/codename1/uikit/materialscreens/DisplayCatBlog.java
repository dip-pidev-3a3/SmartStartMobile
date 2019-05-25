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

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import java.io.IOException;


import smartstart.Entities.Blogpost;
import smartstart.services.ServiceBlogpost;

/**
 * Represents a user profile in the app, the first form we open after the walkthru
 *
 * @author Mounir Achir
 */
public class DisplayCatBlog extends SideMenuBaseForm {
     Form f;
    TextField tarticlecontent;
    TextField tarticletitle;
    TextField  tPostContent;
    Button btnajout,btnaff,btnreturn;
    Container Cat=new Container(new BoxLayout(BoxLayout.X_AXIS));
    Button M,C,I;
    
   
    
    ComboBox cb=new ComboBox();
    SpanLabel lb;
    public DisplayCatBlog(Resources res,String cat) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("white.png").scaled(300,300);
      
       
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_BOOK);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        
        
        
        
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Blog Posts ", "Title"),
                                    new Label(" ", "SubTitle")
                                )
                            ).add(BorderLayout.WEST, profilePicLabel),
                        GridLayout.encloseIn(2)
                );       
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
          M=new Button("Media Post");
        C=new Button("Cheat Post");
        I=new Button("Instructional Post");
        M.setUIID("LoginButton");
        C.setUIID("LoginButton");
        I.setUIID("LoginButton");
        
        Cat.add(M);
        Cat.add(C);
        Cat.add(I);
        add(Cat);
        C.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                DisplayCatBlog b=new DisplayCatBlog(res,"Cheat Sheet Post");
                b.show();
                
            }
            });
        M.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                DisplayCatBlog b=new DisplayCatBlog(res,"Media Post");
                b.show();
                
            }
            });
        I.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                DisplayCatBlog b=new DisplayCatBlog(res,"Instructional Post");
                b.show();
                
            }
            });
       
        fab.addActionListener(e->{
             f = new Form("Add A Blog");
        tarticletitle = new TextField("","Article Title");
        tarticlecontent= new TextField("","Article Content");
        tPostContent=new TextField("","Post Content");
      
        cb = new ComboBox<String>() ;
        cb.addItem("Instructional Post");
        cb.addItem("Media Post");
        cb.addItem("Cheat Sheet Post");
       
        
        
     
        
        btnajout = new Button("Add  This Blog Post");
        btnreturn=new Button("Return");
       Button btn=new Button("");
        
        f.add(tarticlecontent);
        f.add(tarticletitle);
        f.add(tPostContent);
        f.add(cb);
        Label l=new Label("bijo");
        f.add(btn); 
        f.add(btnajout);
        f.add(btnreturn);
        f.show();
        btnreturn.addActionListener(r->{
              BlogAffichage b=new BlogAffichage(res);
                b.show();
            
        });
        btnajout.addActionListener((x) -> {
            ServiceBlogpost ser = new ServiceBlogpost();
            Blogpost t = new Blogpost();
            if(tarticlecontent.getText().isEmpty()||(tarticlecontent.getText().isEmpty()||(tPostContent.getText().isEmpty())))
            {
                ToastBar.showMessage("Please Fill Out All the Blogpost infos !", FontImage.MATERIAL_CHECK);
            }
            else{
                
           
                
                    t.setArticle_content(tarticlecontent.getText());
                    t.setArticle_title(tarticletitle.getText());
                    t.setPost_content(tPostContent.getText());
                    t.setPost_type((String)cb.getSelectedItem());
                    ser.ajoutTask(t);
                    ToastBar.showMessage("Successfully Added !", FontImage.MATERIAL_CHECK);
                    //ser.sendMailAdd("mounirachir96@gmail.com", (String)cb.getSelectedItem());
                    ser.SendMail("mounirachir96@gmail.com", (String)cb.getSelectedItem());
                    
                    BlogAffichage b=new BlogAffichage(res);
                    b.show();
                
}
            

        }); 
                            

            
        });
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
                        
        add(new Label("Posts", "TodayTitle"));
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
         Container List= new Container(BoxLayout.y());
        ServiceBlogpost cs= new ServiceBlogpost();
        
        for (Blogpost i : cs.findByCat(cat))
    {   Container cphoto=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cdetail=new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container cPost=new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cPost.getAllStyles().setPaddingLeft(30);
        cPost.getAllStyles().setMarginBottom(180);
               cPost.getAllStyles().setFgColor(ColorUtil.LTGRAY);

        ImageViewer I=new ImageViewer(res.getImage("post.png").scaled(1000,600));
        Label user=new Label("   Mounir                          ");
        Label date=new Label("                        "+i.getPost_type());
    
            
         
                MultiButton mb= new MultiButton();
                
               
                mb.setScrollable(true);
                mb.setTextLine1(i.getArticle_title()+"      ");
                mb.setTextLine2(i.getArticle_content());  
                cphoto.add(I);
                cdetail.add(user);
                cdetail.add(date);
                
                cPost.add(cphoto);
                cPost.add(cdetail);
                cPost.add(mb);
                add(cPost);
                
                 mb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("bijooor"+i.getPostId());
                detailBlog b=new detailBlog(res,i.getPostId());
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
     public DisplayCatBlog() {
        
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
