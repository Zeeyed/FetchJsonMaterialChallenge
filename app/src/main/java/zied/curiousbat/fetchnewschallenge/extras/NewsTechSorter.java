package zied.curiousbat.fetchnewschallenge.extras;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import zied.curiousbat.fetchnewschallenge.pojo.NewsTech;

/**
 * Created by Zied on 26/10/2015.
 */
public class NewsTechSorter {
    public void sortNewsTechByName(ArrayList<NewsTech> newsTeches){
        Collections.sort(newsTeches, new Comparator<NewsTech>() {
            @Override
            public int compare(NewsTech lhs, NewsTech rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public void sortNewsTechByDate(ArrayList<NewsTech> newsTeches){
        Collections.sort(newsTeches, new Comparator<NewsTech>() {
            @Override
            public int compare(NewsTech lhs, NewsTech rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });
    }
}
