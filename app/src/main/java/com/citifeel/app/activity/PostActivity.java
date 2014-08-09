package com.citifeel.app.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.citifeel.app.BaseActivity;
import com.citifeel.app.R;
import com.citifeel.app.util.CommonUtils;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;

import java.io.File;

/**
 * Created by ywng on 16/7/14.
 */
public class PostActivity extends BaseActivity implements ImageChooserListener{

    private int chooserType;
    private ImageChooserManager imageChooserManager;
    private String filePath;
    private LinearLayout galleryRow;
    private int imageWidth;

    private boolean fb_share=false;
    private boolean twitter_share=false;
    private boolean instagram_share=false;
    private boolean weibo_share=false;
    private boolean wechat_share=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

         /*set back icon*/
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(getResources().getDrawable( R.drawable.ic_action_back ));

        /* price & score spinners*/
        Spinner scores_spinner = (Spinner) findViewById(R.id.scores_spinner);

        ArrayAdapter<CharSequence> score_spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.scores_array, android.R.layout.simple_spinner_item);
        score_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scores_spinner.setAdapter(score_spinner_adapter);

        Spinner price_spinner = (Spinner) findViewById(R.id.price_spinner);
        ArrayAdapter<CharSequence> price_spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.price_array, android.R.layout.simple_spinner_item);
        price_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price_spinner.setAdapter(price_spinner_adapter);

        /* image picker from library */
        ImageView pickButton = (ImageView) findViewById(R.id.pickImage);
        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooserType = ChooserType.REQUEST_PICK_PICTURE;
                chooseImage();
            }
        });
        galleryRow = (LinearLayout) findViewById(R.id.galleryRow);
        imageWidth = CommonUtils.dp(this, 80);

         /* image picker from camera */
        ImageView pickButton_camera = (ImageView) findViewById(R.id.pickImage_camera);
        pickButton_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooserType=ChooserType.REQUEST_CAPTURE_PICTURE;
                chooseImage();
            }
        });




        /* share buttons */
        final Button fb_shareButton=(Button) findViewById(R.id.share_fb_button);
        fb_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_fb_logo_dark, 0, 0, 0);
        final Button twitter_shareButton=(Button) findViewById(R.id.share_twitter_button);
        twitter_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_twitter_logo_dark, 0, 0, 0);
        final Button instagram_shareButton=(Button) findViewById(R.id.share_instagram_button);
        instagram_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_instagram_logo_dark, 0, 0, 0);
        final Button weibo_shareButton=(Button) findViewById(R.id.share_weibo_button);
        weibo_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_weibo_logo_dark, 0, 0, 0);
        final Button wechat_shareButton=(Button) findViewById(R.id.share_wechat_button);
        wechat_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_wechat_logo_dark, 0, 0, 0);

        fb_shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fb_share==true){
                    //Log.d("share btn:","fb share btn clicked.");
                    fb_share=false;
                    fb_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_fb_logo_dark, 0, 0, 0);
                }else{
                    fb_share=true;
                    fb_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_fb_logo, 0, 0, 0);
                }
            }
        });
        twitter_shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(twitter_share){
                    twitter_share=false;
                    twitter_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_twitter_logo_dark, 0, 0, 0);
                }else{
                    twitter_share=true;
                    twitter_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_twitter_logo, 0, 0, 0);
                }

            }
        });
        instagram_shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instagram_share){
                    instagram_share=false;
                    instagram_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_instagram_logo_dark, 0, 0, 0);
                }else{
                    instagram_share=true;
                    instagram_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_instagram_logo, 0, 0, 0);
                }
            }
        });
        wechat_shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wechat_share){
                    wechat_share=false;
                    wechat_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_wechat_logo_dark, 0, 0, 0);
                }else{
                    wechat_share=true;
                    wechat_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_wechat_logo, 0, 0, 0);
                }

            }
        });
        weibo_shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weibo_share){
                    weibo_share=false;
                    weibo_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_weibo_logo_dark, 0, 0, 0);
                }else{
                    weibo_share=true;
                    weibo_shareButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_weibo_logo, 0, 0, 0);
                }

            }
        });


    }

    private void chooseImage() {
        imageChooserManager = new ImageChooserManager(this,
                chooserType, "citifeel", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_confirm_reg:
                //post();
                break;
            case R.id.action_settings:
                //return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!finishPost()){
            /* back to login activity */
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            /* actually not very possible to come to this stage , just for place holder */
            super.onBackPressed();
        }
    }

    /********** Activity Methods **********/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else {
        }
    }

    @Override
    protected void onStart() {
        // TODO Add status callback
        super.onStart();

    }

    @Override
    protected void onStop() {
        // TODO Remove callback
        super.onStop();

    }



    private boolean finishPost() {
        return false;
    }


    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (image != null) {
                    ImageView im = new ImageView(PostActivity.this);
                  //  im.setLayoutParams(new ViewGroup.LayoutParams(imageWidth, imageWidth));
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageWidth, imageWidth);
                    lp.setMargins(0,0,4,0);
                    im.setLayoutParams(lp);
                    im.setImageURI(Uri.parse(new File(image.getFileThumbnail()).toString()));
                    im.setAdjustViewBounds(true);
                    im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    galleryRow.addView(im, 0);
//                    imageViewThumbnail.setImageURI(Uri.parse(new File(image
//                            .getFileThumbnail()).toString()));
//                    imageViewThumbSmall.setImageURI(Uri.parse(new File(image
//                            .getFileThumbnailSmall()).toString()));
                }
            }
        });
    }

    @Override
    public void onError(String s) {

    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType,
                "citifeel", true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("chooser_type", chooserType);
        outState.putString("media_path", filePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("chooser_type")) {
                chooserType = savedInstanceState.getInt("chooser_type");
            }

            if (savedInstanceState.containsKey("media_path")) {
                filePath = savedInstanceState.getString("media_path");
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }




}
