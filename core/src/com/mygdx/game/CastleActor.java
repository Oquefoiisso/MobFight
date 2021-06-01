package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import java.text.CharacterIterator;

public class CastleActor extends Actor {
    private Texture texture;
    private Texture coin;
    private Rectangle hitBounds;
    private int durability = 40;

    public CastleActor() {
        texture = new Texture(Gdx.files.internal("castle1.png"));
//        coin = new Texture(Gdx.files.internal("coin.png"));
        hitBounds = new Rectangle(184, 120, 200, 230);
        setDebug(true);

    }

    public int getDurability() {
        return durability;
    }

    @Override
    public void act(float delta) {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor: actors){
            if(actor instanceof GolemActor){
                GolemActor golemActor = (GolemActor) actor;
                Rectangle golemHitBounds = golemActor.getHitBounds();
                if(hitBounds.overlaps(golemHitBounds)){
                    golemActor.remove();
                    durability--;
                }
            }
            if(actor instanceof WarriorActor){
                WarriorActor warriorActor = (WarriorActor) actor;
                Rectangle warriorHitBounds = warriorActor.getHitBounds();
                if(hitBounds.overlaps(warriorHitBounds)){
                    warriorActor.remove();
                    durability--;
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 20,300);
//        batch.draw(coin, 1050,650);
    }
}
