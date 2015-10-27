package zied.curiousbat.fetchnewschallenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import zied.curiousbat.fetchnewschallenge.Logging.L;
import zied.curiousbat.fetchnewschallenge.NTDetailActivity;
import zied.curiousbat.fetchnewschallenge.R;
import zied.curiousbat.fetchnewschallenge.extras.Constants;
import zied.curiousbat.fetchnewschallenge.network.VolleySingleton;
import zied.curiousbat.fetchnewschallenge.pojo.NewsTech;

import zied.curiousbat.fetchnewschallenge.animation.AnimationUtils;


/**
 * Created by Zied on 26/10/2015.
 */
public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolderSearch> {


    private ArrayList<NewsTech> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private static Context context;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int previousPosition = 0;

    public AdapterSearch(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setMovieList(ArrayList<NewsTech> listMovies) {
        this.listMovies = listMovies;
        notifyItemRangeChanged(0, listMovies.size());
    }

    @Override
    public ViewHolderSearch onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.custom_news_tech, parent, false);

        ViewHolderSearch viewHolderSearch = new ViewHolderSearch(view);

        return viewHolderSearch;
    }

    /**
     * Access to data structure
     * Get the current from array list through position
     *
     * @param holder
     * @param position current position in recycler view
     */
    @Override
    public void onBindViewHolder(ViewHolderSearch holder, int position) {
        NewsTech currentMovie = listMovies.get(position);
        holder.techTitle.setText(currentMovie.getTitle());

        Date movieDate = currentMovie.getDate();
        if (movieDate != null) {
            String formatedDate = dateFormat.format(movieDate);
            holder.techDate.setText(formatedDate);
        } else {

            holder.techDate.setText(Constants.NA);
        }

        if (position > previousPosition) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);

        }

        String urlThunmbnail = currentMovie.getImg();
        loadImage(urlThunmbnail, holder);



    }



    private void loadImage(String urlThumbnail, final ViewHolderSearch holder) {
        if (!urlThumbnail.equals(Constants.NA)) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.techImg.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }


     class ViewHolderSearch extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView techImg;
        private TextView techTitle;
        private TextView techDate;


        public ViewHolderSearch(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            techTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            techDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            techImg = (ImageView) itemView.findViewById(R.id.movieThumbnail);
        }

        // Not Complete detail
        @Override
        public void onClick(View v) {
            /*
            Intent intent = new Intent(context, NTDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("DATA", listMovies.get(getAdapterPosition()));
            intent.putExtras(bundle);
            context.startActivity(intent);
            */
        }

    }
}
