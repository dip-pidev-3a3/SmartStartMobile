/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import smartstart.Entities.Application;
import smartstart.Entities.Blogpost;
import smartstart.services.ServiceApplication;
import smartstart.services.ServiceBlogpost;


/**
 *
 * @author bhk
 */
public class ApplicationForm extends SideMenuBaseForm{

    Form f;
    TextField jobTitle;
    TextField jobCategory;
    TextField  status;
    Button btnWithdraw,btnDelete;

    public ApplicationForm()  {
        
        
    }
    public ApplicationForm(Resources res) {
        super(BoxLayout.y());
        
         Container List= new Container(BoxLayout.y());
        ServiceApplication cs= new ServiceApplication();
       f = new Form("Your Applications");
        jobTitle = new TextField("","Job Title");
        jobCategory= new TextField("","Job Category");
        status=new TextField("","Status");
        btnWithdraw = new Button("Withdraw");
        btnDelete = new Button("Delete");
        
        f.add(jobTitle);
        f.add(jobCategory);
        f.add(status);
        f.add(btnWithdraw);
        f.add(btnDelete);
        f.show();
        btnWithdraw.addActionListener((e) -> {
            ServiceApplication ser = new ServiceApplication();
            Application t = new Application();
            ser.withdraw(t);
            
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
