package com.appmatrixinc.amauto;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by jennaharris on 5/6/14.
 */
public class Flashlight extends Fragment {

    private Camera camera;
    private boolean isLightOn = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.flashlight, container, false);
        ImageView flashlight = (ImageView) view.findViewById(R.id.flashlight_control);

        View header = view.findViewById(R.id.header);
        ImageButton menu = (ImageButton) header.findViewById(R.id.menu_button);
        menu.setOnClickListener(MenuButton.menuToggle());

        //Begin camera controls
        final PackageManager pm = getActivity().getPackageManager();

        camera = Camera.open();
        if(camera == null) {
            Toast.makeText(getActivity(), "No camera detected", Toast.LENGTH_LONG);
        }
        else{

            final Camera.Parameters p = camera.getParameters();

            flashlight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        Toast.makeText(getActivity(), "No camera detected", Toast.LENGTH_LONG);
                    }
                    else {

                        if(isLightOn) {
                            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camera.setParameters(p);
                            camera.stopPreview();
                            isLightOn = false;
                        }
                        else {
                            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(p);
                            camera.startPreview();
                            isLightOn = true;
                        }
                    }
                }
            });
        }

        return view;
    }
}
