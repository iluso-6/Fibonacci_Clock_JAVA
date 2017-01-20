/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fibonacci;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Shay
 */
public class Fibonacci extends Application {
    int [][]hours;
    int [][]mins;
    
    int [][]hours1 =  {{0,0,0,0,0},{1,0,0,0,0},{0,0,1,0,0},{0,0,0,1,0},{1,1,1,0,0},{0,0,0,0,1},{1,0,0,0,1},{0,0,1,0,1},{0,0,0,1,1},{1,0,0,1,1},{0,0,1,1,1},{1,0,1,1,1}};
    int [][]hours2 = {{1,1,1,1,1},{0,1,0,0,0},{1,1,0,0,0},{1,0,1,0,0},{0,1,0,1,0},{0,0,1,1,0},{1,0,1,1,0},{1,1,1,1,0},{1,0,1,0,1},{1,1,1,0,1},{1,1,0,1,1},{0,1,1,1,1}};
    
    int [][]mins1 =  {{0,0,0,0,0},{1,0,0,0,0},{0,0,1,0,0},{0,0,0,1,0},{1,0,0,1,0},{0,0,0,0,1},{1,0,1,1,0},{0,0,1,0,1},{0,0,0,1,1},{1,1,1,0,1},{0,0,1,1,1},{1,0,1,1,1}};  
    int [][]mins2 = {{0,0,0,0,0},{0,1,0,0,0},{1,1,0,0,0},{1,0,1,0,0},{1,1,1,0,0},{0,0,1,1,0},{0,1,0,0,1},{1,1,1,1,0},{1,0,1,0,1},{1,0,0,1,1},{1,1,0,1,1},{0,1,1,1,1}};
    
    private final Color red = Color.web("e26465",1);
    private final Color green = Color.web("82dd87",1);
    private final Color yellow = Color.web("ede89b",1);
    private final Color white = Color.web("e4e5f1",1);        
    
    static Rectangle[] rectangles = {
            new Rectangle(60, 60),
            new Rectangle(60, 60),
            new Rectangle(120, 120),
            new Rectangle(180, 180),
            new Rectangle(300, 300)
    };

    static Line[] borders = {
            new Line(120, 0, 120, 120),
            new Line(0, 120, 180, 120),
            new Line(120, 60, 180, 60),
            new Line(180, 0, 180, 300)
    };
    
    ComboBox cb;
    Timer timer;
    Stage primaryStage;
    
    private  double xOffset;
    private  double yOffset;

    public Fibonacci() {
        this.yOffset = 0;
        this.xOffset = 0;
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        
        
        final Pane pane = new Pane();
        pane.setPrefSize(460, 280);
        
        int[] recX = {120, 120, 0, 0, 180};
        int[] recY = {0, 60, 0, 120, 0};

        for (int i = 0; i < rectangles.length; i++)
        {
            rectangles[i].setX(recX[i]);
            rectangles[i].setY(recY[i]);
            rectangles[i].setFill(white);

           
            
            pane.getChildren().add(rectangles[i]);
        }

        cb = new ComboBox(FXCollections.observableArrayList(
     "HELP","EXIT"));
        
        pane.getChildren().addAll(Arrays.asList(borders));
        pane.getChildren().add(cb);
        cb.setPrefSize(50, 20);
        cb.setStyle("-fx-background-color: slateblue;");
        cb.setOpacity(0);
        cb.setDisable(true);
        cb.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String select) { 
                try {
                    setSelectedItem( select);
                } catch (IOException ex) {
                    Logger.getLogger(Fibonacci.class.getName()).log(Level.SEVERE, null, ex);
                }
                menuClicked();           
            }    
        });
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
            
        });
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              if(event.getClickCount() == 2){
                System.out.println("Double clicked");
                 menuClicked();
            }
               
            }
            
        });        
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });
        Scene scene = new Scene(pane);
       // scene.getStylesheets().add(css);
        scene.setFill(Color.BLACK);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        primaryStage.setResizable(false);
        startClockRefresh();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private void menuClicked(){
        if(cb.getOpacity()==0){
            cb.setValue("");
            FadeTransition to = new FadeTransition(Duration.millis(800), cb);    
            to.setFromValue(0);
            to.setToValue(1); 
            to.play();   
            cb.setDisable(false);
        }else{
            FadeTransition from = new FadeTransition(Duration.millis(800), cb);
            from.setFromValue(1);
            from.setToValue(0);
            from.play();
            cb.setDisable(true);
        }
    }
    
    /**
     *
     * @param primaryStage
     * @throws IOException
     */
    public void showDialogue(final Stage primaryStage) throws IOException {


                final Stage dialog = new Stage();
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initStyle(StageStyle.UTILITY);
                dialog.initOwner(primaryStage);
                Parent root = FXMLLoader.load(getClass().getResource("FXMLModal.fxml"));

                Scene modal = new Scene(root);
                
                dialog.setScene(modal);
                dialog.show();
            }
      
 
    
    private void setSelectedItem(String select) throws IOException {
      System.out.println("result "+ select);
        if
        (select.equals("EXIT"))
        { 
        timer.cancel();
        System.exit(0);
        }
        else if
        (select.equals("HELP"))
        {
         showDialogue(primaryStage );   
        }

      
    }
    
    
    private void startClockRefresh(){
    
         timer = new Timer(true);
         timer.scheduleAtFixedRate(new TimerTask() {

        public void run() {
        changeUpDisplay();
        int thisHour = getTimeVariables()[0];
        int thisMinute = getTimeVariables()[1];
     
        setHourColors(thisHour);
        thisMinute = Math.round(thisMinute / 5);
        setMinuteColors(thisMinute);
        
        }
    }, 0, 5000);
    
    }
    
    private void changeUpDisplay(){

    int select;
    select = (int) (Math.random()+0.5);

        if(select==1){
            hours = hours1;   
        }else{
            hours = hours2;
        }
    select = (int) (Math.random()+0.5);

        if(select==1){
            mins = mins1;  
        }else{
            mins = mins2;
        }

    }
    @SuppressWarnings("empty-statement")
    private int[] getTimeVariables(){
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        
        int thisHr;
        int thisMn;
        int twentyFour;
        thisHr = calendar.get(Calendar.HOUR);
        thisMn = calendar.get(Calendar.MINUTE);
        twentyFour = calendar.get(Calendar.HOUR_OF_DAY);
        return new int[] {thisHr,thisMn,twentyFour};
    }    

    private void setHourColors(int hr){
    
            for (int i = 0; i < hours[hr].length; i++)
        {
            if(hours[hr][i]==1){
            rectangles[i].setFill(red);
            }else{
            rectangles[i].setFill(white);
            }
        }   
    }
    
    @SuppressWarnings("empty-statement")
    private void setMinuteColors(int mn){
            for (int i = 0; i < mins[mn].length; i++)
        {
            if(mins[mn][i]==1){
                if(rectangles[i].getFill()==red){
            rectangles[i].setFill(yellow);    
                }else{
            rectangles[i].setFill(green);
                }
            };
        }   
    }   
    public static void main(String[] args) {
        launch(args);
    }    
    
}
