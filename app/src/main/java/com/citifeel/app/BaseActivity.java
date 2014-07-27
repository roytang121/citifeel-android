package com.citifeel.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewConfiguration;

import com.citifeel.app.core.Client;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Field;

/**
 * @author roytang
 * Base Level Activity
 */
public abstract class BaseActivity extends Activity {
    protected ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Client configuration
        Client.setEnableCircularRedirect(true);  //allowing circularRedirect to prevent exception

        //init imageLoader if no instance has been given
        if(imageLoader == null){
            imageLoader = ImageLoader.getInstance();
        }

        //disable menu button
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.base, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * return default options for ImageLoader to display
     * @see com.nostra13.universalimageloader.core.DisplayImageOptions
     * @return DisplayImageOptions
     */
    protected DisplayImageOptions getDefaultDisplayImageOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                    //.showImageOnLoading(R.drawable.ic_stub)
                    //.showImageForEmptyUri(R.drawable.ic_empty)
                    //.showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .considerExifParams(true)
                    //.displayer(new RoundedBitmapDisplayer(20))
                    .build();
        return options;
    }

    protected ImageLoader getImageLoaderInstance(){
        return imageLoader;
    }

}
