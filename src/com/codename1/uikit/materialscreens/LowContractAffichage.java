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
import com.codename1.ui.ComboBox;
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
import java.io.IOException;
import smartstart.Entities.Blogpost;
import smartstart.Entities.Complaint;
import smartstart.Entities.Contract;
import smartstart.services.ContractService;
import smartstart.services.ServiceBlogpost;
import smartstart.services.ServiceComplaints;
import java.util.Date;

/**
 * Represents a user profile in the app, the first form we open after the
 * walkthru
 *
 * @author Mounir Achir
 */
public class LowContractAffichage extends SideMenuBaseForm {

    public LowContractAffichage(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("fileStart.png");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_BOOK);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        Button btn = new Button("My Contracts", "CenterTitle");
        Container myContracts = BoxLayout.encloseY(
                btn
        );
        myContracts.setUIID("");
        btn.addActionListener(l -> {
            new ContractAffichage(res).show();
        });

        Button btn1 = new Button("My Low Contracts", "CenterTitle");
        Container completedTasks = BoxLayout.encloseY(
                btn1
        );
        completedTasks.setUIID("");
        btn1.addActionListener(l -> {
            new LowContractAffichage(res).show();
        });

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("My Contracts", "Title")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(2, myContracts, completedTasks)
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);

        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));

        add(new Label("Contracts", "TodayTitle"));

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        Container List = new Container(BoxLayout.y());
        ContractService cs = new ContractService();
        for (Contract i : cs.entrepriseLowContracts()) {
            MultiButton mb = new MultiButton();
            mb.setScrollable(true);
            mb.setTextLine1("The job title : " + i.getJobTitle());
            mb.setTextLine2("The freelancer name : " + i.getFreelancer());
            List.add(mb);
            mb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {

                    SpanLabel jobT = new SpanLabel();
                    SpanLabel jobD = new SpanLabel();
                    SpanLabel freelancer = new SpanLabel();
                    Container c= new Container(BoxLayout.y());

                    Picker start = new Picker();
                    start.setType(Display.PICKER_TYPE_DATE);
                    Picker finish = new Picker();
                    finish.setType(Display.PICKER_TYPE_DATE);
                    TextField sum = new TextField("","Enter The Sum Here");
                    ComboBox<String> payment = new ComboBox<String>();
                    Button update = new Button("Update");
                    Button cancel = new Button("Cancel");
                    Form f = new Form("Complete your Contract");
                    jobT.setText("The job title : " + i.getJobTitle());
                    jobD.setText("The job déscription : " + i.getJobDescription());
                    freelancer.setText("The freelancer name : " + i.getFreelancer());
                    payment.addItem("Online");
                    payment.addItem("Cash");
                    payment.addItem("Cheque");
                    cancel.setUIID("LoginButton");
                    update.setUIID("LoginButton");
                    f.add(jobT);
                    f.add(jobD);
                    f.add(freelancer);
                    f.add(sum);
                    c.add(start);
                    c.add(finish);
                    c.add(payment);
                    //c.add(update);
                    //c.add(cancel);
                    f.add(c);
                    f.add(update);
                    f.add(cancel);
                    
                    update.addActionListener(y -> {    
                        try {
                            float s = Float.parseFloat(sum.getText());
                            cs.update(i.getId_contract(), start.getValue().toString(), finish.getValue().toString(), sum.getText(), payment.getSelectedItem());
                            ToastBar.showMessage("Contract Successfully Updated !", FontImage.MATERIAL_CHECK);
                            LowContractAffichage lca = new LowContractAffichage(res);
                            lca.show();
                        } catch (Exception e) {
                            Dialog.show("Error", "The Sum must be A positive number", "ok",null);
                        }

                    });
                    cancel.addActionListener(p->{
                        LowContractAffichage lca = new LowContractAffichage(res);
                        lca.show();
                    });
                    f.show();

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

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

}
