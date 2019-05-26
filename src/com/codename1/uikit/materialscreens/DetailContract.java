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
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.stripe.model.Order;
import smartstart.Entities.Contract;
import smartstart.services.ContractService;
import smartstart.services.PaymentService;
import smartstart.services.ServiceBlogpost;

/**
 *
 * @author ASUS
 */
public class DetailContract extends SideMenuBaseForm {

    Form f;
    TextField content;
    TextField subject;
    TextField mail;
    Button btnajout, btnaff;
    SpanLabel lb;

    public DetailContract(Resources res, int id) {
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
                                new Label("Details Contract", "Title"),
                                new Label("Contract Section", "SubTitle")
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
        ContractService cs = new ContractService();

        Contract c = new Contract();

        for (Contract i : cs.entrepriseContract()) {
            if (i.getId_contract() == id) {
                SpanLabel jobT = new SpanLabel();
                SpanLabel jobD = new SpanLabel();
                SpanLabel freelancer = new SpanLabel();
                SpanLabel sum = new SpanLabel();
                SpanLabel start = new SpanLabel();
                SpanLabel finish = new SpanLabel();
                SpanLabel payment = new SpanLabel();

                jobT.setText("The job title : " + i.getJobTitle());
                jobD.setText("The job déscription : " + i.getJobDescription());
                freelancer.setText("The freelancer name : " + i.getFreelancer());
                sum.setText("The Sum : " + i.getSum());
                start.setText("The start date : " + i.getStart_date());
                finish.setText("The finish date : " + i.getFinish_date());
                payment.setText("The payment method : " + i.getPayment_method());

                add(jobT);
                add(jobD);
                add(freelancer);
                add(sum);
                add(start);
                add(finish);
                add(payment);
                Button delete = new Button("Delete");
                Button pay = new Button("Pay");
                Button print = new Button("Print");
                add(delete);
                add(pay);
                add(print);

                pay.addActionListener((x) -> {
                    PaymentForm pf = new PaymentForm(res,i);
                    pf.show();
                });

                delete.addActionListener(e -> {

                    if (Dialog.show("Remove", "  Are you sure for deleting this contract ?", "Confirm", "Cancel")) {
                        cs.delete(i.getId_contract());
                        ToastBar.showMessage("Successfully Deleted !", FontImage.MATERIAL_CHECK);

                        ContractAffichage ca = new ContractAffichage(res);
                        ca.show();
                    }

                });
                
                print.addActionListener(y->{
                    
                ToastBar.showMessage("PDF Genarated Successfully !", FontImage.MATERIAL_CHECK);
                });

            }
        }
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

    public DetailContract() {

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
