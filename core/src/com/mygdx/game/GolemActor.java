package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class GolemActor extends Actor {
    private Texture texture;
    private float speed = 0.4f;
    private Rectangle hitBounds;
    public static final long FIRE_RATE = 2000000000L;
    private float health = 600;

    private Animation<Texture> runAnimation;
    private GolemStateEnum golemState;
    private float runTime;


    public GolemActor() {
        texture = new Texture(Gdx.files.internal("golem/1_golem.png"));
        Array<Texture> runTextures = new Array<>();
        for (int i = 1; i <= 2; i++) {
            Texture texture = new Texture(Gdx.files.internal("golem/" + i + "_golem.png"));
            runTextures.add(texture);
        }
        runAnimation = new Animation<Texture>(0.5f, runTextures, Animation.PlayMode.LOOP_PINGPONG);
        setX(Gdx.graphics.getWidth()-50);
        setY(Gdx.graphics.getHeight()/3*2);
        hitBounds = new Rectangle(1000, 300, 200, 200);
    }

    public Rectangle getHitBounds(){
        return hitBounds;
    }

    @Override
    public void act(float delta) {
        runTime += delta * 2;
//        float currentX = getX();
//        if (getX() > 20){
//            currentX += -speed;
//        }
//        setX(currentX);
//        hitBounds.setX(getX());
//        hitBounds.setY(getY());
        float x = getX();
        float y = getY();

        HeroActor heroActor = getHeroActor();
        if (heroActor != null) {
            float playerX = heroActor.getX();
            float playerY = heroActor.getY();
            if (getY() < playerY) {
                setY(y + speed);
                golemState = GolemStateEnum.RUN;
            }
            if (getY() > playerY) {
                setY(y - speed);
                golemState = GolemStateEnum.RUN;
            }
            if (getX() < playerX) {
                setX(x + speed);
                golemState = GolemStateEnum.RUN;
            }
            if (getX() > playerX) {
                setX(x - speed);
                golemState = GolemStateEnum.RUN;
            }
        }
//        else {
//            if (getY() < 300) {
//                setY(getY() + speed);
//            }
//            if (getY() > 300) {
//                setY(getY() - speed);
//            } else if (getX() > 20 && getY() == 300) {
//                setX(getX() - speed);
//            }
//        }
        hitBounds.setX(getX());
        hitBounds.setY(getY());

        System.out.println(health);
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof HeroActor) {
                HeroActor heroActor1 = (HeroActor) actor;
                Rectangle heroATKHitBounds = heroActor1.getAttackHitBounds();
                if (hitBounds.overlaps(heroATKHitBounds)) {
                    if (System.nanoTime() - heroActor1.lastAttack >= heroActor1.FIRE_RATE && heroActor.attackBtnPressed == true) {
                        health = health - heroActor.getDamage();
                        heroActor1.lastAttack = System.nanoTime();
                        heroActor.attackBtnPressed = false;
                    }
                }
            }
        }
    }

    private HeroActor getHeroActor() {
        Stage stage = getStage();
        if (stage == null) {
            return null;
        }
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof HeroActor) {
                return (HeroActor) actor;
            }
        }
        return null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.draw(texture, getX(), getY(), 200, 200);
        if(golemState == GolemStateEnum.RUN){
            batch.draw(runAnimation.getKeyFrame(runTime), getX(), getY(), 200, 200);
        }
    }

}
