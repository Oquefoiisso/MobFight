package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class BuyMenuActor extends Button {
    Texture texture;
    Stage buyStage;
    private int height = Gdx.graphics.getHeight();
    private int wight = Gdx.graphics.getWidth();
    private int Y = height / 2 - 168;
    private int X = wight / 2 - 440;
    ImageButton addWarriorButton;
    boolean buttonClicked = true;
//    Dialog buyMenu;
//    Skin buyMenuSkin;


    public BuyMenuActor() {
        buyStage = new Stage();

        texture = new Texture(Gdx.files.internal("buyMenuBG.png"));

        Gdx.input.setInputProcessor(getStage());

        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("buttonCoinIdle.png")));
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("buttonCoinChecked.png")));

        addWarriorButton = new ImageButton(upDrawable, downDrawable);

//        buyStage.addActor(addWarriorButton);

        addWarriorButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("isklik");
                Stage stage = getStage();
                Array<Actor> actors = stage.getActors();
                for (Actor actor: actors){
                    if(!(actor instanceof WarriorActor)){
                        WarriorActor warriorActor = (WarriorActor) actor;
                        Rectangle warriorHitBounds = warriorActor.getHitBounds();
                        stage.addActor(warriorActor);
                    }
                }
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, X, Y);
        addWarriorButton.setX(500);
        addWarriorButton.setY(300);
        addWarriorButton.draw(batch, parentAlpha);
    }
}
