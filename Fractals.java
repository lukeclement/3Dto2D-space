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
import java.io.*;
import javax.imageio.*;
import javafx.embed.swing.*;

public class Fractals extends Application{
  public static int width=750;
  public static int height=750;
  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage alphaStage){
    alphaStage.setTitle("Fractals YAH");
    Group root=new Group();
    Scene scene=new Scene(root);
    Scanner scan=new Scanner(System.in);

    alphaStage.setScene(scene);

    Canvas canvas=new Canvas(width,height);
    root.getChildren().add(canvas);

    GraphicsContext gc=canvas.getGraphicsContext2D();
    final long startNanoTime=System.nanoTime();

    List<Integer> mouseInputX=new ArrayList<>();
    List<Integer> mouseInputY=new ArrayList<>();
    scene.setOnMousePressed(new EventHandler<MouseEvent>(){
        public void handle(MouseEvent mE){
            mouseInputX.add((int)(mE.getX()));
            mouseInputY.add((int)(mE.getY()));
            System.out.println("Added!"+mE.getScreenX()+"<-Screen;Canvas->"+mouseInputX.get(mouseInputX.size()-1));
        }
    });

    new AnimationTimer(){
      public double[] add(double[] z, double[] c){
        double[] output=new double[2];
        output[0]=z[0]+c[0];
        output[1]=z[1]+c[1];
        return output;
      }public double[] times(double[] z, double[] c){
        double[] output=new double[2];
        //z=(a+bi)*(c+di)=(ac+adi+bci-bd)=(ac-bd)+(ad+bc)i;
        output[0]=z[0]*c[0]-z[1]*c[1];
        output[1]=z[0]*c[1]+z[1]*c[0];
        return output;
      }

      public int findLoops(double[] c){
        int output=0;
        double[] z=new double[2];
        z[0]=0;
        z[1]=0;
        while(z[0]<1000 && z[1]<1000 && output<255*5){
          z=add(times(z,z),c);
          output++;
        }
        return output;
      }

      double xMax= 2;
      double xMin=-2;
      double yMax= 2;
      double yMin=-2;
      //double x=0;
      double[] c=new double[2];
      double q=0;
      boolean ran=false;
      double currentCount=0;
      double cX=-0.6;
      double cY=0.44;
      double alpha=2;
      int loops=0;

      public void handle(long currentNanoTime){
        //gc.setFill(Color.ALICEBLUE);
        //gc.fillRect(0, 0, height, width);
        //gc.clearRect(0, 0, height, width);
        //x=(q*(xMax-xMin))/width + xMin;
        if(mouseInputX.size()>=2 && ran){
          ran=false;
          currentCount=mouseInputX.size();
          gc.setFill(Color.ALICEBLUE);
          gc.fillRect(0, 0, height, width);

          int size=mouseInputX.size();
          double a=mouseInputX.get(size-2);
          double b=mouseInputX.get(size-1);
          double c=mouseInputY.get(size-2);
          double d=mouseInputY.get(size-1);
          double maxCornerX=b;
          double minCornerX=a;
          double maxCornerY=d;
          double minCornerY=c;
          if(a>b){
            maxCornerX=a;
            minCornerX=b;
          }
          if(c>d){
            maxCornerY=c;
            minCornerY=d;
          }
          System.out.println(maxCornerY);
          System.out.println(minCornerY);
          double xMaxT=xMax;
          xMaxT=maxCornerX * ((xMax-xMin)/width) + xMin;
          xMin=minCornerX * ((xMax-xMin)/width) + xMin;
          xMax=xMaxT;

          double yMaxT=yMax;
          yMaxT=-(yMax-yMin)*(-1 + (minCornerY/height)) + yMin;
          yMin=-(yMax-yMin)*(-1 + (maxCornerY/height)) + yMin;
          yMax=yMaxT;


          if(yMax-yMin>xMax-xMin){
            yMax=yMin+(xMax-xMin);
          }else{
            xMax=xMin+(yMax-yMin);
          }
          System.out.println(xMax);
          System.out.println(xMin);
          System.out.println(yMax);
          System.out.println(yMin);

        }
        if(q<1){
          for(double x=xMin;x<xMax;x+=(xMax-xMin)/width){
            for(double y=yMax;y>yMin;y-=((yMax-yMin)/height)){
              c[0]=x;
              c[1]=y;
              loops=findLoops(c);
              //Phycadelic
              gc.setFill(Color.rgb((int)(255/2 + (255/2)*Math.sin(loops/5)),(int)(255/2 + (255/2)*Math.sin(loops/5-Math.PI/2)),(int)(255/2 + (255/2)*Math.sin(loops/5+Math.PI/2))));
              if(loops==255*5){
                gc.setFill(Color.rgb(0,0,0));
              }
              //Grayscale
              //gc.setFill(Color.rgb(255-loops/5,255-loops/5,255-loops/5));
              gc.fillOval((x-xMin)*width/(xMax-xMin),height-(y-yMin)*height/(yMax-yMin),1,1);
            }
          }
        }

        q++;
        if(q>=0 && currentCount<=mouseInputX.size()-2){
          q=0;
          gc.setFill(Color.ALICEBLUE);
          gc.fillRect(0, 0, height, width);
          ran=true;
        }
        /*alpha=alpha/1.1;
        xMax=cX+alpha;
        xMin=cX-alpha;
        yMax=cY+alpha;
        yMin=cY-alpha;
        System.out.println(alpha);*/
        /*WritableImage wim = new WritableImage(width, height);
        canvas.snapshot(null,wim);
        try{
          ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", new File("TestFractal"+(int)q+".png"));
          System.out.println("Saved!");
        }
        catch(Exception ex){

        }*/
      }
    }.start();
    alphaStage.show();
  }
}
