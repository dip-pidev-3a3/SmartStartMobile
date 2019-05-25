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
import com.codename1.ui.ComboBox;
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
import smartstart.Entities.fos_user;
import smartstart.services.ServiceBlogpost;

/**
 *
 * @author ASUS
 */
public class detailBlog extends SideMenuBaseForm {

    Form f;
    TextField tarticlecontent;
    TextField tarticletitle;
    TextField  tPostContent;
    Button btnajout,btnaff,btnreturn;
    SpanLabel lb;

    public detailBlog(Resources res, int id) {
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
                                new Label("Add A post", "Title"),
                                new Label(" ", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(2)
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        Container List = new Container(BoxLayout.y());
        ServiceBlogpost cs = new ServiceBlogpost();

        Blogpost col = new Blogpost();
        System.out.println(id);

        for (Blogpost i : cs.getList2()) {
            if (i.getPostId() == id) {
                                System.out.println("bijooor"+i.getPostId());

                Label nom = new Label();
                SpanLabel prenom = new SpanLabel();

                nom.setText(i.getArticle_title() + "\n\n");
                prenom.setText(i.getArticle_content());

                add(nom);
                add(prenom);
                 Button Delete = new Button("Delete");
        Button Update = new Button("Update");
        btnreturn = new Button("Cancel");
        ServiceBlogpost c=new ServiceBlogpost();
         
              
        add(Delete);
        add(Update);
        Update.addActionListener((x) -> {
           
            f = new Form("Update A Blog Post");
           
           btnajout = new Button("Update This Blog Post");
           tarticletitle = new TextField("","Article Title");
        tarticlecontent= new TextField("","Article Content");
        tPostContent=new TextField("","Post Content");
         tarticlecontent.setText(i.getArticle_content());
            tarticletitle.setText(i.getArticle_title());
            tPostContent.setText(i.getPost_content());
            
        ComboBox cb = new ComboBox<String>() ;
        cb.addItem("Instructional Post");
        cb.addItem("Media Post");
        cb.addItem("Cheat Sheet Post");
        cb.setSelectedItem(i.getPost_type());
        
        f.add(tarticlecontent);
        f.add(tarticletitle);
        f.add(tPostContent);
         f.add(cb);
         
        f.add(btnajout);
        f.add(btnreturn);
       
        
        f.show();
        btnajout.addActionListener((u) -> {
            ServiceBlogpost ser1 = new ServiceBlogpost();
            Blogpost t1 = new Blogpost();
              if(tarticlecontent.getText().isEmpty()||(tarticlecontent.getText().isEmpty()||(tPostContent.getText().isEmpty())))
            {
                ToastBar.showMessage("Please Fill Out All the Blogpost infos !", FontImage.MATERIAL_CHECK);
            }
            else{
            t1.setArticle_content(tarticlecontent.getText());
            t1.setArticle_title(tarticletitle.getText());
            t1.setPost_content(tPostContent.getText());
            t1.setPost_type("ll");
            t1.setPostId(i.getPostId());
            ser1.mod(t1);
            ToastBar.showMessage("Successfully Added !", FontImage.MATERIAL_CHECK);

                BlogAffichage b=new BlogAffichage(res);
                b.show();}
                
            

        });
           btnreturn.addActionListener(r->{
              BlogAffichage b=new BlogAffichage(res);
                b.show();
            
        });
        });

        Delete.addActionListener(e -> {
 
            if (Dialog.show("Remove", "  Are you sure to delete this Blog Post?", "Confirm", "Cancel")) {
                cs.delete(i);
                                ToastBar.showMessage("Successfully Deleted !", FontImage.MATERIAL_CHECK);

                BlogAffichage b=new BlogAffichage(res);
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

    public detailBlog() {

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
