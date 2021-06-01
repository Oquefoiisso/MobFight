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

public class WarriorActor extends Actor implements Shape {
    private Texture texture;
    private Rectangle hitBounds;
    public static final long FIRE_RATE = 1000000000L;
    private float speed = 1f;
    private float health = 120;

    private Animation<Texture> runAnimation;
    private WarriorStateEnum warriorState;
    private float runTime;

    public WarriorActor()  {
        texture = new Texture(Gdx.files.internal("warrior/1_warrior.png"));
        Array<Texture> runTextures = new Array<>();
        for (int i = 1; i <= 2; i++) {
            Texture texture = new Texture(Gdx.files.internal("warrior/" + i + "_warrior.png"));
            runTextures.add(texture);
        }
        runAnimation = new Animation<Texture>(0.5f, runTextures, Animation.PlayMode.LOOP_PINGPONG);
        setX(Gdx.graphics.getWidth()-50);
        setY(Gdx.graphics.getHeight()/3);
        setWidth(100);
        setHeight(100);
        hitBounds = new Rectangle(1000, 300, 100, 100);
    }

    public Rectangle getHitBounds() {
        return hitBounds;
    }

    @Override
    public void act(float delta) {
        runTime += delta * 2;

        float x = getX();
        float y = getY();

        HeroActor heroActor = getHeroActor();
        if (heroActor != null) {
            float playerX = heroActor.getX();
            float playerY = heroActor.getY();
            if (getY() < playerY) {
                setY(y + speed);
                warriorState = WarriorStateEnum.RUN;
            }
            if (getY() > playerY) {
                setY(y - speed);
                warriorState = WarriorStateEnum.RUN;
            }
            if (getX() < playerX) {
                setX(x + speed);
                warriorState = WarriorStateEnum.RUN;
            }
            if (getX() > playerX) {
                setX(x - speed);
                warriorState = WarriorStateEnum.RUN;
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

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {

    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
//    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        if(warriorState == WarriorStateEnum.RUN){
            batch.draw(runAnimation.getKeyFrame(runTime), getX(), getY(), getWidth(), getHeight());
        }
    }

//    @Override
//    public void draw() {
//
//    }
}
