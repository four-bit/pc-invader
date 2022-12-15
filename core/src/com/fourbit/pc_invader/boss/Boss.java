package com.fourbit.pc_invader.boss;



import com.badlogic.gdx.utils.Array;




public class Boss {
    private int x, y, speed;
    private Head head;
    private Array<Body> bodies = new Array<>();
    private float angle;

    public Boss(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        head = new Head(
                this.x,
                this.y,
                2,
                0.0f
        );

        for (int i = 0; i < 7; i++) {
            bodies.add(new Body(
                    this.x,
                    this.y,
                    2,
                    0.0f
            ));
        }
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Array<Body> getBodies() {
        return bodies;
    }

    public void setBodies(Array<Body> bodies) {
        this.bodies = bodies;
    }

    public void update() {
        bodies.get(0).update(head.getX(),head.getY());
        head.update();
//        for (Body body : bodies){
//            body.update(head.getX(),head.getY());
//        }
//        if (
//                bodies.get(0).getX() - head.getX() >  bodies.get(0).getTexture().getWidth()/6 ||
//                head.getY() - bodies.get(0).getY() > bodies.get(0).getTexture().getHeight()/10||
//                head.getX() - bodies.get(0).getX()  >  bodies.get(0).getTexture().getWidth()/6 ||
//                bodies.get(0).getY()-head.getY()  > bodies.get(0).getTexture().getHeight()/10
//        ){

//        }

//        if (
//                (bodies.get(0).getX()+200 - head.getX() > 0 &&
//                        head.getY() - bodies.get(0).getY() < 50)||
//                        (bodies.get(0).getX()+200  - head.getX()<0 &&
//                        head.getY() - bodies.get(0).getY() > -50)
//        ){
//            bodies.get(0).update(head.getX(),bodies.get(0).getY());
//        }
////        if (head.getY() - bodies.get(0).getY() > 50||
////                head.getY() - bodies.get(0).getY()  <-50){
////            bodies.get(0).update(head.getX(),head.getY());
////        }
//        else {
//            bodies.get(0).update(head.getX(),head.getY());
//        }
//        if ((bodies.get(0).getX()+200 - head.getX() > 0 || bodies.get(0).getX()+200  - head.getX()<0)){
//            bodies.get(0).update(head.getX(),bodies.get(0).getY());
//        }

//        System.out.println( bodies.get(0).getX() - head.getX());
////        bodies.get(0).update(head.getX(),head.getY());
        for (int i =1; i < bodies.size; i++){
            if (
                    bodies.get(i).getX()  - bodies.get(i-1).getX() >  50||
                            bodies.get(i).getY() - bodies.get(i).getY()  < -75||
                            bodies.get(i-1).getX() - bodies.get(i).getX() >  50 ||
                            bodies.get(i).getY() - bodies.get(i-1).getY()   > 20
            ){
                    bodies.get(i).update(bodies.get(i-1).getX(), bodies.get(i-1).getY());
            }
//            bodies.get(i).update(bodies.get(i-1).getX(),bodies.get(i-1).getY());
        }

    }

    public void dispose() {
        head.dispose();
        for (int i = 0; i < bodies.size; i++) {
            bodies.get(i).dispose();
        }
    }
}



