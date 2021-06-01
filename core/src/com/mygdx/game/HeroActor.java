package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class HeroActor extends Actor {

    private Texture texture;
    private Texture sword;
    private Rectangle hitBounds;
    private Rectangle attackHitBounds;
    private float health = 100;
    private float damage = 40;
    public static final long FIRE_RATE = 1000000000L;
    public boolean attackBtnPressed;
    public boolean heroRun = false;
    public boolean heroDied = false;

    private Animation<Texture> runAnimation;
    private Animation<Texture> attackAnimation;
    private HeroStateEnum heroState;
    private float runTime;
    private int direction;

    public long lastShot;
    public long lastAttack;

    //21; 22
    public HeroActor() {
        texture = new Texture(Gdx.files.internal("hero/2_hero.png"));
        sword = new Texture(Gdx.files.internal("sword/1_sword.png"));
        Array<Texture> runTextures = new Array<>();
        for (int i = 1; i <= 2; i++) {
            Texture texture = new Texture(Gdx.files.internal("hero/" + i + "_hero.png"));
            runTextures.add(texture);
        }
        runAnimation = new Animation<Texture>(0.5f, runTextures, Animation.PlayMode.LOOP_PINGPONG);
        Array<Texture> attackTextures = new Array<>();
        for (int i = 1; i <= 2; i++) {
            Texture texture = new Texture(Gdx.files.internal("sword/" + i + "_sword.png"));
            attackTextures.add(texture);
        }
        attackAnimation = new Animation<Texture>(0.3f, attackTextures, Animation.PlayMode.LOOP_PINGPONG);
        setX(50);
        setY(Gdx.graphics.getHeight() / 2);
        setWidth(100);
        setHeight(100);
        setOriginX(texture.getWidth() / 2);
        hitBounds = new Rectangle(0, 0, 100, 100);
        attackHitBounds = new Rectangle(0, 0, 120, 120);

    }

    public float getDamage() {
        return damage;
    }

    public float getHealth() {
        return health;
    }

    public Rectangle getAttackHitBounds() {
        return attackHitBounds;
    }

    public Texture getSword() {
        return sword;
    }

    @Override
    public void act(float delta) {
        runTime += delta * 2;

        super.act(delta);

        float x = getX();
        float y = getY();
//        heroState = HeroStateEnum.IDLE;

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            setY(y + 2);
            heroState = HeroStateEnum.RUN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            setY(y - 2);
            heroState = HeroStateEnum.RUN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setX(x + 2);
            heroState = HeroStateEnum.RUN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setX(x - 2);
            heroState = HeroStateEnum.RUN;
        }
        hitBounds.setX(getX());
        hitBounds.setY(getY());
        attackHitBounds.setX(getX());
        attackHitBounds.setY(getY());

        if(getX() < 0 || getX() > Gdx.graphics.getWidth()-50 || getY() < 0 || getY() > Gdx.graphics.getHeight()-50){
            remove();
            heroDied = true;
        }

        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof WarriorActor) {
                WarriorActor warriorActor = (WarriorActor) actor;
                Rectangle warriorHitBounds = warriorActor.getHitBounds();
                if (hitBounds.overlaps(warriorHitBounds)) {
                    if (System.nanoTime() - lastShot >= warriorActor.FIRE_RATE) {
                        health = health - 10;
                        lastShot = System.nanoTime();
                    }
                }
            }
            if (actor instanceof GolemActor) {
                GolemActor golemActor = (GolemActor) actor;
                Rectangle golemHitBounds = golemActor.getHitBounds();
                if (hitBounds.overlaps(golemHitBounds)) {
                    if (System.nanoTime() - lastShot >= golemActor.FIRE_RATE) {
                        health = health - 50;
                        lastShot = System.nanoTime();
                        System.out.println("da");
                    }
                }
            }
//            System.out.println("PIZDAAAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
    }

    public Rectangle getHitBounds() {
        return hitBounds;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        if (heroState == HeroStateEnum.RUN) {
            batch.draw(runAnimation.getKeyFrame(runTime), getX(), getY(), getWidth(), getHeight());
        } else if (heroState == HeroStateEnum.IDLE) {
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }
        if (attackBtnPressed) {
            batch.draw(attackAnimation.getKeyFrame(runTime), getX() + getWidth() / 2, getY() + getHeight() / 2, 80, 80);
//            if (System.nanoTime() - lastShot >= FIRE_RATE){
//                lastShot = System.nanoTime();
//                attackBtnPressed = false;
//            }
        } else {
            batch.draw(sword, getX() + getWidth() / 2, getY() + getHeight() / 2, 80, 80);
        }


    }




    public void setDataFromJoyStick(float knobPercentX, float knobPercentY) {
        float newX = getX() + knobPercentX * 3;
        setX(newX);

        float newY = getY() + knobPercentY * 3;
        setY(newY);

        Vector2 vector2 = new Vector2(knobPercentX, knobPercentY);
        float angle = vector2.angleDeg();
        setRotation(angle);

        if (knobPercentX != 0 || knobPercentY != 0) {
            heroState = HeroStateEnum.RUN;
        } else {
            heroState = HeroStateEnum.IDLE;
        }
//        if  (knobPercentX < 0){
//            direction = -1;
//        } else if (knobPercentX > 0){
//            direction = 1;
//        }
    }
}
