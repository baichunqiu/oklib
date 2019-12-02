package com.bcq.progress;


import com.bcq.progress.sprite.Sprite;
import com.bcq.progress.style.ChasingDots;
import com.bcq.progress.style.Circle;
import com.bcq.progress.style.CubeGrid;
import com.bcq.progress.style.DoubleBounce;
import com.bcq.progress.style.FadingCircle;
import com.bcq.progress.style.FoldingCube;
import com.bcq.progress.style.MultiplePulse;
import com.bcq.progress.style.MultiplePulseRing;
import com.bcq.progress.style.Pulse;
import com.bcq.progress.style.PulseRing;
import com.bcq.progress.style.RotatingCircle;
import com.bcq.progress.style.RotatingPlane;
import com.bcq.progress.style.ThreeBounce;
import com.bcq.progress.style.WanderingCubes;
import com.bcq.progress.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
