package skinapp.luca.com.fragment;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import skinapp.luca.com.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoFragment extends Fragment {

    private int page = 0;

    public static LogoFragment newInstance(int pageNo) {
        LogoFragment f = new LogoFragment();
        f.page = pageNo + 1;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_logo, container, false);

        ImageView ivLogo = (ImageView) v.findViewById(R.id.img_logo);
        Resources resources = getActivity().getApplicationContext().getResources();
        String logoFileName = "img_logo" + page;
        final int resourceId = resources.getIdentifier(logoFileName, "mipmap",
                getActivity().getApplicationContext().getPackageName());
        Drawable logoImg = getResources().getDrawable(resourceId);
        ivLogo.setBackground(logoImg);

        return v;
    }

}