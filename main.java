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

public class main extends Application{
  public static int width=750;
  public static int height=750;
  public static void main(String[] args){
    launch(args);
  }
  public double findDx(double x, double y, double z, double dt){
    return 10*(y-x)*dt;
    //return (x - x*x*x/3 - y + 0)*dt;
    //return (x*(3-x-2*y))*dt;
    //return y*dt;
    //return (y)*dt;
    //return (y-y*y*y)*dt;
  }public double findDy(double x, double y, double z, double dt){
    return (28*x - y - x*z)*dt;
    //return 0.08*(x+0.7-0.8*y)*dt;
    //return (y*(2-x-y))*dt;
    //return (x-x*x*x)*dt;
    //return (-Math.sin(x))*dt;
    //return (-x-y*y)*dt;
  }public double findDz(double x, double y, double z, double dt){
    return (x*y - 8/3 *z)*dt;
    //return 0.08*(x+0.7-0.8*y)*dt;
    //return (y*(2-x-y))*dt;
    //return (x-x*x*x)*dt;
    //return (-Math.sin(x))*dt;
    //return (-x-y*y)*dt;
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
    final int length=50000;
    double dataset[][]=new double[3][length];
    double dt=0.001;
    double x=1;
    double y=1;
    double z=1;
    dataset[0][0]=x;
    dataset[1][0]=y;
    dataset[2][0]=z;

    for(int i=1;i<length;i++){
      x=dataset[0][i-1];
      y=dataset[1][i-1];
      z=dataset[2][i-1];
      dataset[0][i]=x+findDx(x,y,z,dt);
      dataset[1][i]=y+findDy(x,y,z,dt);
      dataset[2][i]=z+findDz(x,y,z,dt);
      if(i>length-10){
        dataset[0][i]=length-i;
        dataset[1][i]=0;
        dataset[2][i]=0;
      }else if(i>length-20){
          dataset[0][i]=0;
          dataset[1][i]=length-i-10;
          dataset[2][i]=0;
      }else if(i>length-30){
        dataset[0][i]=0;
        dataset[1][i]=0;
        dataset[2][i]=length-i-20;
      }/*
      dataset[0][i]=0;
      dataset[1][i]=0;
      dataset[2][i]=0;
      */
    }
    /*
    dataset[0][0]=10;
    dataset[1][0]=0;
    dataset[2][0]=0;

    dataset[0][1]=0;
    dataset[1][1]=10;
    dataset[2][1]=0;

    dataset[0][2]=0;
    dataset[1][2]=0;
    dataset[2][2]=10;

    dataset[0][3]=5;
    dataset[1][3]=0;
    dataset[2][3]=0;

    dataset[0][4]=0;
    dataset[1][4]=5;
    dataset[2][4]=0;

    dataset[0][5]=0;
    dataset[1][5]=0;
    dataset[2][5]=5;
    */
    final double data[][]=dataset;
    new AnimationTimer(){
      //public double[] x0={0.2,0.20001,0.20002,0.20003};
      //public double[] x=x0;
      //public double L=1;
      //public double f(double x){
      //  return x*x - L;
      //}public double fD(double x){
      //  return 2*x;
      //}
      public double[] w=new double[16*16*16];
      public double[] v=new double[16*16*16];
      public double[] u=new double[16*16*16];
      public double x=0;
      public double y=0;
      public double z=0;
      public double xd=1;
      public double yd=1;
      public double zd=1;
      public double r=-2;

      public double aa=0;
      public double bb=0;

      public double t=Math.atan(1);
      public double a=Math.atan(1);
      public double findDx(double x, double y, double z, double dt){
        return 10*(y-x)*dt;
        //return (x - x*x*x/3 - y + 0)*dt;
        //return (x*(3-x-2*y))*dt;
        //return y*dt;
        //return (y)*dt;
        //return (y-y*y*y)*dt;
      }public double findDy(double x, double y, double z, double dt){
        return (28*x - y - x*z)*dt;
        //return 0.08*(x+0.7-0.8*y)*dt;
        //return (y*(2-x-y))*dt;
        //return (x-x*x*x)*dt;
        //return (-Math.sin(x))*dt;
        //return (-x-y*y)*dt;
      }public double findDz(double x, double y, double z, double dt){
        return (x*y - 8/3 *z)*dt;
        //return 0.08*(x+0.7-0.8*y)*dt;
        //return (y*(2-x-y))*dt;
        //return (x-x*x*x)*dt;
        //return (-Math.sin(x))*dt;
        //return (-x-y*y)*dt;
      }
      //public double x1=1;
      //public double y1=1;

      public void handle(long currentNanoTime){
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(0, 0, height, width);
        double dt=0.001;
        double shift=0.01;
        double shiftb=0.02;
        t+=shift;
        a-=shift;
        double cX=0;
        double cY=10;
        double cZ=20;
        aa+=shiftb;
        gc.setFill(Color.rgb(0,0,0));
        for(int i=0;i<length;i++){
          //Need to correct to be an ellipse
          double min=Math.sin(aa);
          double max=1;
          x=data[0][i]*max*Math.sin(a)-data[1][i]*max*Math.sin(t);
          y=-data[1][i]*min*Math.cos(t)-data[0][i]*min*Math.cos(a)+(Math.cos(aa))*data[2][i];
          double xx=cX*max*Math.sin(a)-cY*max*Math.sin(t);
          double yy=-cY*min*Math.cos(t)-cX*min*Math.cos(a)+(Math.cos(aa))*cZ;

          gc.fillOval(width/2 -10*xx + 10*x,height/2 + 10*yy - 10*y,1,1);
        }
        //r=r+0.01;
        //y=(r+0.7)/0.8;
        //System.out.println(y);
        //gc.fillOval((width/2 + 750/5 *r),(height/2 - 750/5 *y),3,3);
      }
    }.start();
    alphaStage.show();
  }
}
