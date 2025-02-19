package com.jmonkeygamesinc.gameshop.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetKey;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jmonkeygamesinc.gameshop.global.CurrencyMeshSingleton;
import com.jmonkeygamesinc.gameshop.graphics.GameShopCurrencyMesh;
import com.jmonkeygamesinc.gameshop.graphics.GameShopCurrencySurface;

import java.util.ArrayList;

/**
 *
 * @author Lynden Jay Evans of JMonkeyGames Inc.
 */
public class Selector {

    public GameShopCurrencyMesh selectedCM;
    public GameShopCurrencySurface selectedCS;

    public ArrayList<Geometry> selectors;
    public Geometry selectedSelector;

    public ArrayList<Geometry> movers;
    public Geometry selectedMover;
    public boolean moveEnabled = false;
    SimpleApplication app;
    public String mode = "NONE"; //NONE, CURRENCYMESH, CURRENCYSURFACE, VECTOR3

    public String action = "SELECT"; //SELECT MOVE OPEN ANIMATE
    public Selector(SimpleApplication app){

        this.app = app;
        selectors = new ArrayList<>();
        movers = new ArrayList<>();

        makeSelection();

        initKeys();
    }

    public void resetSelection(){

        for (Geometry g: selectors){

            Material material = new Material(app.getAssetManager().loadAsset(new AssetKey<>("Common/MatDefs/Misc/Unshaded.j3md")));
            material.setColor("Color", ColorRGBA.Red);
            g.setMaterial(material);

        }
    }

    public void clearSelection(){

        for (Geometry g: selectors){

            app.getRootNode().detachChild(g);
        }
        selectors.clear();

    }

    public void resetMovers(){

        for (Geometry g: movers){

            Material material = new Material(app.getAssetManager().loadAsset(new AssetKey<>("Common/MatDefs/Misc/Unshaded.j3md")));
            material.setColor("Color", ColorRGBA.Red);
            g.setMaterial(material);

        }
    }

    public void clearMovers(){

        for (Geometry g: movers){

            app.getRootNode().detachChild(g);
        }
        movers.clear();
    }
    public void makeSelection(){

        clearSelection();
        if (mode.equals("NONE")) {

            int i = 0;
            for (GameShopCurrencyMesh cm : CurrencyMeshSingleton.getInstance().cMeshes) {

                Box box = new Box(.5f, .5f, .5f);
                Geometry geom = new Geometry("SelectBox " + i, box);
                Material material = new Material(app.getAssetManager().loadAsset(new AssetKey<>("Common/MatDefs/Misc/Unshaded.j3md")));
                material.setColor("Color", ColorRGBA.Red);
                geom.setMaterial(material);
                geom.setLocalTranslation(cm.gspSurfaces[0].vInfinitesimals[cm.gspSurfaces[0].vInfinitesimals.length / 2].infinitesimals[cm.gspSurfaces[0].vInfinitesimals[cm.gspSurfaces[0].vInfinitesimals.length / 2].infinitesimals.length / 2]);
                app.getRootNode().attachChild(geom);
                selectors.add(geom);
                i++;
            }
        } else if (mode.equals("CURRENCYMESH")){

            int i = 0;
            for (GameShopCurrencyMesh cm : CurrencyMeshSingleton.getInstance().cMeshes) {

                for (GameShopCurrencySurface cs: cm.gspSurfaces) {
                    Box box = new Box(.5f, .5f, .5f);
                    Geometry geom = new Geometry("SelectBox " + i, box);
                    Material material = new Material(app.getAssetManager().loadAsset(new AssetKey<>("Common/MatDefs/Misc/Unshaded.j3md")));
                    material.setColor("Color", ColorRGBA.Orange);
                    geom.setMaterial(material);
                    geom.setLocalTranslation(cm.gspSurfaces[0].vInfinitesimals[cm.gspSurfaces[0].vInfinitesimals.length / 2].infinitesimals[cm.gspSurfaces[0].vInfinitesimals[cm.gspSurfaces[0].vInfinitesimals.length / 2].infinitesimals.length / 2]);
                    app.getRootNode().attachChild(geom);
                    selectors.add(geom);
                    i++;
                }
        }




        } else if (mode.equals("CURRENCYSURFACE")){

            int i = 0;

        } else if (mode.equals("VECTOR3")){


        }
    }

    public void enableMove(){

//        Material materialMatch = new Material(app.getAssetManager().loadAsset(new AssetKey<>("Common/MatDefs/Misc/Unshaded.j3md")));
//        materialMatch.setColor("Color", ColorRGBA.Blue);
        for (Geometry g: selectors){

            if (g.equals(selectedSelector)){

                if (!moveEnabled){
                    for (int i = 0; i < 6; i++){
                        Box box = new Box(.25f, .25f, .25f);
                        Geometry geom = new Geometry("MoveBox " + i, box);
                        Material material = new Material(app.getAssetManager().loadAsset(new AssetKey<>("Common/MatDefs/Misc/Unshaded.j3md")));
                        material.setColor("Color", ColorRGBA.Red);
                        geom.setMaterial(material);
                        geom.setLocalTranslation(g.getLocalTranslation());
                        //LEFT RIGHT TOP BOTTOM FORWARD BACKWARD
                        if (i == 0){

                            geom.move(-1,0,0);

                        } else if (i == 1){

                            geom.move(1,0,0);
                        } else if (i == 2){

                            geom.move(0,1,0);
                        } else if (i == 3){

                            geom.move(0,-1,0);
                        } else if (i == 4) {

                            geom.move(0,0,-1);
                        } else if (i == 5) {
                            geom.move(0,0,1);
                        }

                        //geom.setLocalTranslation(cm.gspSurfaces[0].vInfinitesimals[cm.gspSurfaces[0].vInfinitesimals.length / 2].infinitesimals[cm.gspSurfaces[0].vInfinitesimals[cm.gspSurfaces[0].vInfinitesimals.length / 2].infinitesimals.length / 2]);
                        app.getRootNode().attachChild(geom);
                        movers.add(geom);
                       // i++;
                    }
                    moveEnabled = true;
                }

            }
        }

    }

    //public void
    /** Declaring the "Shoot" action and mapping to its triggers. */
    private void initKeys() {
        app.getInputManager().addMapping("pick target",
                 new TouchTrigger(TouchInput.ALL)); // trigger 2: left-button click
        app.getInputManager().addListener(touchListener, "pick target");
    }
    /** Defining the "Shoot" action: Determine what was hit and how to respond. */
    final private TouchListener touchListener = new TouchListener() {
        @Override
        public void onTouch(String name, TouchEvent event, float tpf) {
            CollisionResults results = new CollisionResults();
            if (name.equals("pick target")) {

                if (event.getType() == TouchEvent.Type.TAP) {

                    Vector2f click2d = new Vector2f(event.getX(), event.getY());
                    Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                    Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                    // Aim the ray from the clicked spot forwards.
                    Ray ray = new Ray(click3d, dir);

                    app.getRootNode().collideWith(ray, results);

                    if (action.equals("SELECT")){

                        // 4. Print the results
                        System.out.println("----- Collisions? " + results.size() + "-----");
                        int count = 0;
                        for (int i = 0; i < results.size(); i++){

                            String hit = results.getCollision(i).getGeometry().getName();

                            if (hit.contains("SelectBox")){
                                count++;
                            }

                        }

                        if (count == 0){

                            resetSelection();
                            if (mode.equals("NONE")) {
                                selectedCM = null;
                            }

                        }

                        for (int i = 0; i < results.size(); i++) {
                            String hit = results.getCollision(i).getGeometry().getName();

                            if (hit.contains("SelectBox")) {
                                // For each hit, we know distance, impact point, name of geometry.
                                float dist = results.getCollision(i).getDistance();
                                Vector3f pt = results.getCollision(i).getContactPoint();

                                System.out.println("* Collision #" + i);
                                System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");

                                if (mode.equals("NONE")) {
                                    selectedCM = CurrencyMeshSingleton.getInstance().cMeshes.get(Integer.parseInt(hit.split(" ")[1]));

                                    Material material = new Material(app.getAssetManager().loadAsset(new AssetKey<>("Common/MatDefs/Misc/Unshaded.j3md")));
                                    material.setColor("Color", ColorRGBA.Blue);

                                    selectors.get(Integer.parseInt(hit.split(" ")[1])).setMaterial(material);
                                    selectedSelector = selectors.get(Integer.parseInt(hit.split(" ")[1]));
                                } else if (mode.equals("CURRENCYMESH")){


                                } else if (mode.equals("CURRENCYSURFACE")){


                                } else if (mode.equals("VECTOR3")){


                                }
                                //mode = "CURRENCYMESH";
                            }
                            break;
                        }
                    }

                    if (action.equals("MOVE")){

                    }

                    if (action.equals("OPEN")){

                    }

                    if (action.equals("ANIMATE")){

                    }
                }
            }
        }
    };

}
