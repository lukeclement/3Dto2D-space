import java.util.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.scene.paint.*;
import javafx.scene.effect.*;
import java.util.*;
import java.lang.Math;

public class Bif extends Application{
  public static int width=750;
  public static int height=750;
  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage alphaStage){
    alphaStage.setTitle("Graphing");
    Group root=new Group();
    Scene scene=new Scene(root);
    Scanner scan=new Scanner(System.in);

    alphaStage.setScene(scene);

    Canvas canvas=new Canvas(width,height);
    root.getChildren().add(canvas);

    GraphicsContext gc=canvas.getGraphicsContext2D();
    final long startNanoTime=System.nanoTime();
    final int length=20000;
    double dataset[][]=new double[3][length];
    double dt=0.001;

    final double data[][]=dataset;
    new AnimationTimer(){
      public double x=0.5;
      public double y=0;
      //X
      public double t=0;
      //Y
      public double q=0;
      public double minR=0;
      public double maxR=4;
      public double a=0;
      public double minA=0;
      public double maxA=10;
      public double zoomX=width/(maxR-minR);
      public double zoomY=height/20E200;
      public void handle(long currentNanoTime){
        //gc.setFill(Color.ALICEBLUE);
        //gc.fillRect(0, 0, height, width);
        //System.out.println(x);
        //for(int j=0;j<750*5;j++){
          y=(double)t/(1000) * (maxR-minR) +minR;
          x=0.1;
          for(int i=0;i<300;i++){
            //x=y*x*(1-x);
            //System.out.println(x);
            //x=Math.exp(-a*x*x)+y;
            //x=2*x%1;
            if(x<0.5){
              x=y*x;
            }if(x>0.5){
              x=y-y*x;
            }
          }
          for(int i=0;i<300;i++){
            //x=Math.exp(-a*x*x)+y;
            //x=y*x*(1-x);
            if(x==y){
              gc.setFill(Color.rgb(255,0,0));
            }else{
              gc.setFill(Color.rgb(0,0,0));
            }
            gc.fillOval((y-minR)*zoomX ,height-x*zoomY-750/2,1,1);
            //x=2*x%1;
            if(x<0.5){
              x=y*x;
            }if(x>=0.5){
              x=y-y*x;
            }
          }
        //}
        t++;
        if(y/(maxR-minR) <width/750){
          System.out.println(x);
        }
        a=(double)t/1000 + minA;

      }
    }.start();
    alphaStage.show();
  }
}
