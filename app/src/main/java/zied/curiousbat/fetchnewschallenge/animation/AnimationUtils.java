package zied.curiousbat.fetchnewschallenge.animation;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Zied on 26/10/2015.
 */
public class AnimationUtils {

    private static int counter = 0;

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown){

        YoYo.with(Techniques.FadeOutLeft)
                .duration(1000)
                .playOn(holder.itemView);
    }

    public static void animateToolBar(View containerToolBar){

    }
}
