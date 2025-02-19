package com.jmonkeygamesinc.gameshop.niftygui;

import androidx.annotation.NonNull;

import com.jme3.app.Application;
import com.jme3.math.Vector3f;
import com.jmonkeygamesinc.gameshop.global.CurrencyMeshSingleton;
import com.jmonkeygamesinc.gameshop.graphics.GameShopCurrencyLine;
import com.jmonkeygamesinc.gameshop.graphics.GameShopCurrencyMesh;
import com.jmonkeygamesinc.gameshop.graphics.GameShopCurrencySurface;
import com.jmonkeygamesinc.gameshop.ui.Selector;

import java.util.List;
import java.util.Objects;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.controls.listbox.ListBoxControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;

/**
 * A ScreenController for the "start" screen defined in
 * "Interfaces/Nifty/HelloJme.xml", which is used in the TestAppStates and
 * TestNiftyGui applications.
 */
public class StartScreenController implements ScreenController {

    final private Application application;
    public Selector selector;
    public Nifty nifty;
    public Screen screen;
    /**
     * Instantiate a ScreenController for the specified Application.
     *
     * @param app the Application
     */
    public StartScreenController(Application app, Selector selector) {
        this.application = app;
        this.selector = selector;
    }

    /**
     * Nifty invokes this method when the screen gets enabled for the first
     * time.
     *
     * @param nifty (not null)
     * @param screen (not null)
     */
    @Override
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind(" + screen.getScreenId() + ")");
        this.nifty = nifty;
        this.screen = screen;
       fillMyListBox();



    }

    /**
     * Nifty invokes this method each time the screen starts up.
     */
    @Override
    public void onStartScreen() {
        System.out.println("onStartScreen");
        //fillMyListBox();
    }

    /**
     * Nifty invokes this method each time the screen shuts down.
     */
    @Override
    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    /**
     * Fill the listbox with items. In this case with Strings.
     */
    public void fillMyListBox() {
        ListBox listBox = screen.findNiftyControl("myListBox", ListBox.class);
//        listBox.addItem("a");
//        listBox.addItem("b");
//        listBox.addItem("c");
//        listBox.addItem("a");
//        listBox.addItem("b");
//        listBox.addItem("c");

        for (GameShopCurrencyMesh cm: CurrencyMeshSingleton.getInstance().cMeshes){

            listBox.addItem("[CurrencyMesh] " + cm.name);

            for (GameShopCurrencySurface cs: cm.gspSurfaces){

                listBox.addItem("    " + "[CurrencySurface] " + cs.name);

                int i = 0;
                for (GameShopCurrencyLine cl: cs.currencyLines){

                    listBox.addItem("        " + "[CurrencyLine] " + i);

                    int j = 0;
                    for (Vector3f v: cl.points){

                        listBox.addItem("            " + "[Vector3f]" + j);
                        j++;
                    }
                    i++;
                }
            }

        }
    }

    /**
     * When the selection of the ListBox changes this method is called.
     */
    @NiftyEventSubscriber(id="myListBox")
    public void onMyListBoxSelectionChanged(final String id, final ListBoxSelectionChangedEvent<String> event) {
        List<String> selection = event.getSelection();
        for (String selectedItem : selection) {
            System.out.println("listbox selection [" + selectedItem + "]");
        }
    }


    /**
     * Stop the Application. Nifty invokes this method (via reflection) after
     * the user clicks on the flashing orange panel.
     */
    public void quit() {
        //application.stop();
    }

    public void save(){

        //System.out.println("SAVE");
    }

    public void load(){

    }

    public void move(){

        selector.action = "MOVE";
        selector.enableMove();
    }

    public void select(){

        selector.action = "SELECT";

    }

    public void open(){

        selector.action = "OPEN";
    }

    public void animate(){

        selector.action = "ANIMATE";

    }
}
