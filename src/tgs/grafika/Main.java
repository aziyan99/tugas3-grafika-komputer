package tgs.grafika;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Basic control
 * A -> Move the light to left
 * D -> Move the light to right
 * W -> Move the light forward
 * S -> Move the light backward
 */
 
public class Main extends Application {
    /**
     * set the width and height of the scene
     * also set it to final (constant)
     */
    private final float WIDTH = 1000;
    private final float HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /**
         * @var object
         * init object var with Box type an fill it with initWooden function
         */
        Box object = initWooden();
        
        /**
         * @var group
         * init variable group with type Group and instance it with Group class
         */
        Group group = new Group();
        
        /**
         * @var sun
         * init variable sun with type Group and instance it with static class Sun which is also
         * instance class from Group class
         */
        Group sun = new Sun();

        /**
         * attach initSun function to the children of sun group
         * and also to initLighting function
         */
        sun.getChildren().add(initSun());
        sun.getChildren().add(initLighting());

        /**
         * attach object variable (group) to the main group
         * also same with sun object
         */
        group.getChildren().add(object);
        group.getChildren().add(sun);

        /**
         * set the group position to 0, 0, 0 (x,y,z -> axis)
         */
        group.translateXProperty().set(0);
        group.translateYProperty().set(0);
        group.translateZProperty().set(0);

        /**
         * init the camera as instance from perspective camera with the type of variable camera
         */
        Camera camera = new PerspectiveCamera(true);
        
        /**
         * Set the max distance that camera can see
         */
        camera.setFarClip(800);
        
        /**
         * set the min distance that camera can see
         */
        camera.setNearClip(0.1);
        
        /**
         * the camera by default will tak place 0,0,0 in the 3d world
         * so to make the camera can see the object we move it little bit
         * in this case we move  the camera at Y axis (up) and z axis (away from object)
         */
        camera.translateZProperty().set(-150);
        camera.translateYProperty().set(-22);

        /**
         * in javafx all that object behavior is happen in the scene
         * so finally we attach the group(set or list of our object) to the scene so we can see it
         *
         * also we can set what material (in this case color) we want to the world
         */
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setFill(Color.rgb(178, 191, 190));
        
        /**
         * to se the object we attach the camera to scene
         */
        scene.setCamera(camera);

        /**
         * in the code below we attach event handler to handle which keyboard pressed will the app open
         * and set some key when pressed will have it own behavior
         *
         * in this case we add event if the key pressed is A,D,W or S we will move the sun object
         * in order to move the object we can doing translate the axis property and set it to
         * what ever the current axis value and added or subtract it by 1
         */
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case A:
                    /**
                     * move to -X axis (left)
                     */
                    sun.translateXProperty().set(sun.getTranslateX() - 1);
                    break;
                case D:
                    /**
                     * move to X axis (right)
                     */
                    sun.translateXProperty().set(sun.getTranslateX() + 1);
                    break;
                case W:
                    /**
                     * move to -Z axis (forward)
                     */
                    sun.translateZProperty().set(sun.getTranslateZ() + 1);
                    break;
                case S:
                    /**
                     * move to Z axis (backward)
                     */
                    sun.translateZProperty().set(sun.getTranslateZ() - 1);
                    break;
            }
        });

        /**
         * in the last we must bring all the object that we has wrapped it by scene
         * to staging area and presentation it to screen (bring window up)
         *
         * also we can set the title :v
         */
            primaryStage.setTitle("Tugas Grafika");
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    /**
     * we separate to init the wooden (object in middle) from primary function
     * the reason is it will make two to handle the object more easy
     *
     * like what color we want to apply to it or what material we wan to attach it
     *
     * in the last we can simply return the object that we has paint (wooden->apply material or color)
     * to the pieces of code that call this function
     * @return
     */
    private Box initWooden(){
        
        /**
         * instance the PhongMaterial class to the material variable
         */
        PhongMaterial material = new PhongMaterial();
        
        /**
         * Apply the material to material variable
         */
        material.setDiffuseMap(new Image(getClass()
                .getResourceAsStream("/resources/wood.jpg")));

        /**
         * create a box with Class Box and define what box we want look like
         * with pattern (width, height, depth)
         */
        Box object = new Box(80, 5, 50);
        
        /**
         * attach the material that we has apply before to the box object
         */
        object.setMaterial(material);

        /**
         * Finally we return the object that we has paint to it (apply material, set width, height and dept)
         */
        return object;
    }

    /**
     * By default in javaFx, if do not specify the lighting JavaFx will give we some
     * amount of lighting
     *
     * @return
     */
    private Node initLighting(){
        /**
         * init the lighting and define which lighting we want to use
         *
         * in Javafx we have two type of lighting it pointLighting (lighting that show from one way/direction)
         * and AmbientLighting (the lighting that some from all direction)
         */
        PointLight pointLight = new PointLight();
        /**
         * we can apply what color the lighting show up
         */
        pointLight.setColor(Color.WHITE);
        /**
         * and we can transform the position of lighting in our world
         * by default it will be define in 0,0,0 (x,y,z) axis (left upper from the window)
         */
        pointLight.getTransforms().add(new Translate(0, -35, -100));
        pointLight.setRotationAxis(Rotate.X_AXIS);

        /**
         * last we simply return the lighting that we have define what color we want,
         * what position and so on to the some piece of code from this program who call this function
         */
        return pointLight;
    }

    /**
     * When we init the lighting, setting it up and bring it up to our scene
     * the lighting actually does'nt have ho is it look like it just light or bright to the object
     * so the representing the lighting we create a initSun function that will return the sphere
     * with position following by the lighting position
     * @return
     */
    private Sphere initSun(){
        /**
         * init var variable as instance from sphere class
         * width the size 1
         */
        Sphere sphere = new Sphere(1);
        /**
         * set the position by following the light position
         */
        sphere.translateYProperty().set(initLighting().getTranslateY());
        sphere.translateZProperty().set(initLighting().getTranslateZ());

        /**
         * Finally return the sphere variable with following the lighting position
         */
        return sphere;
    }


    /**
     * create a class Sun and instance it from Group class
     *
     * The main reason we create this class is to wrapping the sun and lighting
     * so in order to moving the sun (sun + lighting) we just simply moving this class
     * and the object that has wrapping by this class will just following it
     */
    static class Sun extends Group{}


    public static void main(String[] args) {
        launch(args);
    }
}
