package com.josstoh.morpion.modele;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Jocelyn on 26/10/2014.
 */
public class Cellule {

    private int posx;
    private int posy;
    public Image image;

    public Cellule(Drawable d,int x, int y) {
        this.image = new Image(d);
        this.posx = x;
        this.posy = y;
    }

    public void setBounds(int x, int y, int w, int h)
    {
        image.setBounds(x,y,w,h);
    }

    public void addListener(EventListener el)
    {
        image.addListener(el);
    }

    public void setDrawable(Drawable d)
    {
        image.setDrawable(d);
    }

    public void draw(Batch b)
    {
        image.draw(b,1);
    }
    public int getposX() {
        return posx;
    }

    public void setposX(int x) {
        this.posx = x;
    }

    public int getposY() {
        return posy;
    }

    public void setposY(int y) {
        this.posy = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
