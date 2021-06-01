package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

public class MyGdxGame extends ApplicationAdapter {
    private Stage stage;
    //    private Batch batch;
    Label durabilityLabel;
    Label healthLabel;
    CastleActor castleActor;
    HeroActor heroActor;
    GolemActor golemActor;
    WarriorActor warriorActor;
    ImageButton imageButton;
    BuyMenuActor buyMenuActor;
    boolean buttonClicked = true;
    Window window;
    Window.WindowStyle buyMenuStyle;
    ImageButton closeButton;
    ProgressBar healthBar;
    ImageButton addWarriorButton;
    Texture buyMenuBG;
    Texture healthBarTexture;
    SpriteBatch bat;
    float HPWidthT = 310;
    float HPHeightT = 100;
    private Touchpad touchpad;
    ImageButton attackButton;
    boolean actorAdded;
    public int wave = 1;
    Shape shapeWarrior;
    //10:31

    @Override
    public void create() {
        stage = new Stage();

        MapActor mapActor = new MapActor();
        stage.addActor(mapActor);

        heroActor = new HeroActor();
        stage.addActor(heroActor);

        castleActor = new CastleActor();
//        stage.addActor(castleActor);
        BitmapFont durabilityBitmapFont = new BitmapFont();
        Label.LabelStyle durabilityLabelStyle = new Label.LabelStyle(durabilityBitmapFont, Color.BLACK);
        durabilityLabel = new Label("40", durabilityLabelStyle);
//        stage.addActor(durabilityLabel);

        golemActor = new GolemActor();
//        stage.addActor(golemActor);

        warriorActor = new WarriorActor();
//        stage.addActor(warriorActor);

        buyMenuActor = new BuyMenuActor();

        Gdx.input.setInputProcessor(stage);


//        final TextureRegionDrawable buyMenuBG = new TextureRegionDrawable(new Texture(Gdx.files.internal("buyMenuBG.png")));
        TextureRegionDrawable upDrawableWarrior = new TextureRegionDrawable(new Texture(Gdx.files.internal("buttonCoinIdle.png")));
        TextureRegionDrawable downDrawableWarrior = new TextureRegionDrawable(new Texture(Gdx.files.internal("buttonCoinChecked.png")));
        addWarriorButton = new ImageButton(upDrawableWarrior, downDrawableWarrior);
        addWarriorButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
//                stage.addActor(getWarriorActor(1000, 20, 100, 100));

            }
        });
        addWarriorButton.setPosition(500, 500);

        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("buyIconIdle.png")));
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new Texture(Gdx.files.internal("buyIconPressed.png")));

        imageButton = new ImageButton(upDrawable, downDrawable);
//        stage.addActor(imageButton);

        imageButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (buttonClicked) {
                    stage.addActor(addWarriorButton);

                } else {
                    addWarriorButton.remove();
                }
                buttonClicked = !buttonClicked;
            }
        });
        stage.addActor(warriorActor);
        stage.addActor(golemActor);
        if (warriorActor.getStage() == null) {
            System.out.println("da");
        }
//            warriorActor.addAction(Actions.removeActor());

        Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.CLEAR);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = drawable;
        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.RED);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        progressBarStyle.knob = drawable;
        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.RED);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        progressBarStyle.knobBefore = drawable;

        healthBar = new ProgressBar(0.00f, 1.00f, 0.01f, false, progressBarStyle);
        healthBar.setValue(heroActor.getHealth());
        healthBar.setAnimateDuration(0.01f);
        healthBar.setBounds(60, Gdx.graphics.getHeight() - 40, 100, 20);
        stage.addActor(healthBar);

        healthBarTexture = new Texture(Gdx.files.internal("healthBar.png"));

        BitmapFont healthBitmapFont = new BitmapFont();
        Label.LabelStyle healthLabelStyle = new Label.LabelStyle(healthBitmapFont, Color.RED);
        healthLabel = new Label("100", healthLabelStyle);
//        stage.addActor(healthLabel);

        bat = new SpriteBatch();

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        Texture texture = new Texture(Gdx.files.internal("joystick-background.png"));
        touchpadStyle.background = new TextureRegionDrawable(texture);

        Texture textureKnob = new Texture(Gdx.files.internal("joystick-knob.png"));
        touchpadStyle.knob = new TextureRegionDrawable(textureKnob);

        touchpad = new Touchpad(1, touchpadStyle);
        touchpad.setPosition(150, 50);
        touchpad.setBounds(150, 50, 128, 128);

//        if (touchpad.isTouched()){
//            heroActor.heroRun = true;
//        }
        stage.addActor(touchpad);

        TextureRegionDrawable upAttackBT = new TextureRegionDrawable(new Texture(Gdx.files.internal("AttackButton.png")));
        TextureRegionDrawable downAttackBT = new TextureRegionDrawable(new Texture(Gdx.files.internal("AttackButton.png")));
        attackButton = new ImageButton(upAttackBT, downAttackBT);
        stage.addActor(attackButton);
        attackButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                heroActor.attackBtnPressed = true;
            }
        });

        ShapeFactory shapeFactory = new ShapeFactory();
        shapeWarrior = shapeFactory.getShape("WARRIOR");

    }

    @Override
    public void render() {
        stage.act();
        stage.draw();

//        shapeWarrior.draw(bat, 0);
//        warriorActor.draw();
//
        durabilityLabel.setText("Прочность: " + String.valueOf(castleActor.getDurability()));
        if (castleActor.getDurability() == 0) {
            castleActor.remove();
        }
        durabilityLabel.setX(1100);
        durabilityLabel.setY(650);


        healthLabel.setText("Здоровье: " + String.valueOf(heroActor.getHealth()));
        healthLabel.setX(70);
        healthLabel.setY(670);

        if (heroActor.getHealth() <= 0) {
            heroActor.remove();
        }
        if (warriorActor.getHealth() <= 0) {
            warriorActor.remove();
        }

        imageButton.setWidth(87);
        imageButton.setHeight(96);
        imageButton.setX(Gdx.graphics.getWidth() - 200);
        imageButton.setY(Gdx.graphics.getHeight() - imageButton.getHeight());

        healthBar.setValue(heroActor.getHealth() / 100);

        attackButton.setPosition(Gdx.graphics.getWidth()-200, 50);
        attackButton.setSize(100, 100);

        bat.begin();
        bat.draw(healthBarTexture, 10, Gdx.graphics.getHeight() - 60, 155, 50);
        bat.end();

        float knobPercentX = touchpad.getKnobPercentX();
        float knobPercentY = touchpad.getKnobPercentY();
        heroActor.setDataFromJoyStick(knobPercentX, knobPercentY);
//        System.out.println(knobPercentX + " " + knobPercentY);
    }


}
