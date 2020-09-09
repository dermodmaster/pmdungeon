package de.fhbielefeld.pmdungeon.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.fhbielefeld.pmdungeon.dungeon.Dungeon;
import de.fhbielefeld.pmdungeon.util.dungeonconverter.Coordinate;

public abstract class Character implements Disposable {

    private static final float RENDERING_OFFSET_X = -0.85f;
    private static final float RENDERING_OFFSET_Y = -0.5f;

    protected SpriteBatch batch;
    protected Dungeon dungeon;
    protected Animation idleAnimation;
    protected Animation runAnimation;
    protected boolean idle = true;
    protected boolean facingLeft = false;
    protected float positionX = 0;
    protected float positionY = 0;

    protected float movementSpeed;
    protected float healthPoints;
    protected float maxHealthPoints;

    protected Character(SpriteBatch batch, Dungeon dungeon) {
        this.batch = batch;
        this.dungeon = dungeon;
    }

    protected float getMovementSpeed() {
        return movementSpeed;
    }

    public void setPosition(Coordinate position) {
        this.positionX = position.getX();
        this.positionY = position.getY();
    }

    public Texture getTexture() {
        if (idle) {
            return this.idleAnimation.getCurrentTexture();
        } else {
            return this.runAnimation.getCurrentTexture();
        }
    }

    public void render() {
        Texture texture = this.getTexture();
        Sprite sprite = new Sprite(texture);
        sprite.flip(facingLeft, false);
        sprite.setSize(1, (float) texture.getHeight() / (float) texture.getWidth());
        sprite.setPosition(positionX + RENDERING_OFFSET_X, positionY + RENDERING_OFFSET_Y);
        sprite.draw(batch);
    }

    protected void move(float targetX, float targetY) {
        if (dungeon.getTileAt((int) targetX, (int) targetY) == Dungeon.Tile.FLOOR || dungeon.getTileAt((int) targetX, (int) targetY) == Dungeon.Tile.DOOR) {
            this.positionX = targetX;
            this.positionY = targetY;
        }
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getHealthPoints() {
        return healthPoints;
    }

    public float getMaxHealthPoints() {
        return maxHealthPoints;
    }

    @Override
    public void dispose() {
        idleAnimation.dispose();
        runAnimation.dispose();
    }
}
