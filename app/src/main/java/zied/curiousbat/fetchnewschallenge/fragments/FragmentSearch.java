package zied.curiousbat.fetchnewschallenge.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import zied.curiousbat.fetchnewschallenge.Logging.L;
import zied.curiousbat.fetchnewschallenge.R;
import zied.curiousbat.fetchnewschallenge.adapters.AdapterSearch;
import zied.curiousbat.fetchnewschallenge.core.MyApplication;
import zied.curiousbat.fetchnewschallenge.extras.Constants;
import zied.curiousbat.fetchnewschallenge.extras.Keys;
import zied.curiousbat.fetchnewschallenge.extras.NewsTechSorter;
import zied.curiousbat.fetchnewschallenge.extras.SortListener;
import zied.curiousbat.fetchnewschallenge.network.VolleySingleton;
import zied.curiousbat.fetchnewschallenge.pojo.NewsTech;

import static zied.curiousbat.fetchnewschallenge.extras.UrlEndpoints.*;
import static zied.curiousbat.fetchnewschallenge.extras.Keys.EndpointBoxOffice.*;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment implements SortListener {


    private static final String STATE_NEWS = "stat_news";

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<NewsTech> listMovies = new ArrayList<>();

    // Date
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    // Adapter
    private AdapterSearch adapterSearch;
    //The list
    private RecyclerView listMovieRecy;
    // Text for handling Network errors
    private TextView textVolleyError;

    private NewsTechSorter newsTechSorter = new NewsTechSorter();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSearch newInstance(String param1, String param2) {
        FragmentSearch fragment = new FragmentSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public static String getRequestUrl() {
        return URL_NEW_TECH;
    }

    public FragmentSearch() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_NEWS, listMovies);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        sendJsonRequest();
    }

    private void sendJsonRequest() {

        JsonArrayRequest request = new JsonArrayRequest(
                getRequestUrl(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                        //L.t(getActivity(), response.toString());
                        textVolleyError.setVisibility(View.GONE);
                        listMovies = parseJSONResponse(response);
                        adapterSearch.setMovieList(listMovies);
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);
            }
        });
        requestQueue.add(request);
    }


    /**
     *  Catch Network Errors via VolleyError instance
     * @param error
     */
    private void handleVolleyError(VolleyError error) {
        textVolleyError.setVisibility(View.VISIBLE);

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            textVolleyError.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            textVolleyError.setText(R.string.error_auth_fail);
        } else if (error instanceof ServerError) {
            textVolleyError.setText(R.string.error_server);

        } else if (error instanceof NetworkError) {
            textVolleyError.setText(R.string.error_network);

        } else if (error instanceof ParseError) {
            textVolleyError.setText(R.string.error_parse);

        }
    }

    private ArrayList<NewsTech> parseJSONResponse(JSONArray response)  throws JSONException {

        ArrayList<NewsTech> listMovies = new ArrayList<>();
        if (response != null || response.length() > 0) {



            for (int i = 0; i < response.length(); i++) {
                try {

                    JSONObject parseJsonObj = response.getJSONObject(i);


                    String title = Constants.NA;
                    String releaseDate = Constants.NA;
                    String urlThunmnail = Constants.NA;


                    if (parseJsonObj.has(KEY_TITLE) && !parseJsonObj.isNull(KEY_TITLE)) {
                        title = parseJsonObj.getString(KEY_TITLE);
                    }


                    if (parseJsonObj.has(KEY_DATE) && parseJsonObj != null && !parseJsonObj.isNull(KEY_DATE)) {

                        releaseDate = parseJsonObj.getString(KEY_DATE);
                    }


                    if (parseJsonObj.has(KEY_IMAGE) && !parseJsonObj.isNull(KEY_IMAGE) && parseJsonObj != null) {
                        urlThunmnail = parseJsonObj.getString(KEY_IMAGE);
                    }


                    //data.append(id+ " "+ title+" "+ releaseDate+ "\n");
                    NewsTech newsTech = new NewsTech();

                    newsTech.setTitle(title);
                    Date date = null;
                    try {
                        date = dateFormat.parse(releaseDate);
                    } catch (ParseException e) {
                    }
                    newsTech.setDate(date);
                    newsTech.setImg(urlThunmnail);


                    if (!title.equals(Constants.NA)) {

                        listMovies.add(newsTech);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }



        }
        return listMovies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
        listMovieRecy = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieRecy.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterSearch = new AdapterSearch(getActivity());
        listMovieRecy.setAdapter(adapterSearch);

        if (savedInstanceState != null) {
            listMovies = savedInstanceState.getParcelableArrayList(STATE_NEWS);
            adapterSearch.setMovieList(listMovies);
        } else {

            sendJsonRequest();
        }

        // Inflate the layout for this fragment
        return view;
    }


    /**
     * Filter RecyclerView by Name via FAB
     */
    @Override
    public void onSortByName() {

        L.t(getActivity(), "sort news by name");
        newsTechSorter.sortNewsTechByName(listMovies);
        adapterSearch.notifyDataSetChanged();
    }

    /**
     * Filter RecyclerView vy Date via FAB
     */
    @Override
    public void onSortByDate() {
        L.t(getActivity(), "sort by date");
        newsTechSorter.sortNewsTechByDate(listMovies);
        adapterSearch.notifyDataSetChanged();
    }
}
