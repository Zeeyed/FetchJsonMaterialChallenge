package zied.curiousbat.fetchnewschallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import zied.curiousbat.fetchnewschallenge.network.VolleySingleton;

/**
 * Created by Zied on 26/10/2015.
 */
public class MyFragment extends Fragment {

    private TextView textView;

    public static MyFragment getInstance(int position){
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.my_fragment, container, false);
        textView = (TextView) layout.findViewById(R.id.position);
        Bundle bundle = getArguments();
        if (bundle !=  null){
            textView.setText("The page selected is "+bundle.getInt("position"));
        }

        return layout;
    }
}
