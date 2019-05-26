/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.materialscreens;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.stripe.model.Order;
import smartstart.Entities.Contract;
import smartstart.services.PaymentService;

/**
 *
 * @author Cascada
 */
public class PaymentForm extends Form {
    public PaymentForm(Resources theme,Contract c) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Complete your, ", "WelcomeWhite"),
                new Label("Payment Operation", "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
        
        TextField login = new TextField("", "Your Card Number", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Your Password", 20, TextField.PASSWORD) ;
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("Pay");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
            if((login.getText().isEmpty())||(password.getText().isEmpty())){
            Dialog.show("Error","pls verify your card number or your code","ok",null);
            }
            else{
            if (Dialog.show("Complete Your Payment", "  Are you sure for Paying this contract ?", "Confirm", "Cancel")){
                Order order = new Order();
                   long s = (long)c.getSum();
            order.setAmount(s*100);
            PaymentService ps = new PaymentService();
            ps.chargeCreditCard(order,c.getFreelancer(),c.getJobTitle());
            ToastBar.showMessage("Payment Succeeded !", FontImage.MATERIAL_CHECK);
            ContractAffichage ca = new ContractAffichage(theme);
            ca.show();
            }
            }
            
            
        });
        Button cancel = new Button("Cancel");
        cancel.setUIID("LoginButton");
        cancel.addActionListener(p->{
                        ContractAffichage lca = new ContractAffichage(theme);
                        lca.show();
                    });
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton,
                cancel
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
    
}
