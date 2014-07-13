package com.citifeel.app.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.citifeel.app.R;
import com.citifeel.app.core.RegisterService;
import com.citifeel.app.core.ServerRequestManager;
import com.citifeel.app.model.UserModel;
import com.citifeel.app.ui.FlowLayout.LayoutParams;
import com.citifeel.app.ui.RoundedImageView;
import com.citifeel.app.util.AlertDialogManager;
import com.citifeel.app.util.CropOption;
import com.citifeel.app.util.CropOptionAdapter;
import com.citifeel.app.util.SessionManager;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends Activity {
    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    private EditText emailTextField;
    private EditText passwordTextField;
    private EditText repasswordTextField;
    private EditText userNameTextField;
    private RoundedImageView profilePicView;

    private Uri mImageCaptureUri;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Session Manager
        session = new SessionManager(RegisterActivity.this);

        ViewGroup flowContainer = (ViewGroup) findViewById(R.id.flow_layout);
        TextView statTextView=new TextView(this);
        statTextView.setText(Html.fromHtml("閣下確認註冊CitiFeel帳戶即表示閣下同意本公司之<a href=\"http://www.google.com\">使用條款</a>及<a href=\"http://www.google.com\">私隱聲明</a> 。"));
        statTextView.setMovementMethod(LinkMovementMethod.getInstance());

        userNameTextField= (EditText) findViewById(R.id.usernametextfield);
        emailTextField= (EditText) findViewById(R.id.emailtextfield_reg);
        passwordTextField= (EditText) findViewById(R.id.passwordtextfield_reg);
        repasswordTextField= (EditText) findViewById(R.id.retypepasswordtextfield_reg);
        profilePicView=(RoundedImageView) findViewById(R.id.profileImageView);

        flowContainer.addView(statTextView,
                new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        /*set back icon*/
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(getResources().getDrawable( R.drawable.ic_action_back ));

        //FB session for using fb info
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        Session fbsession = Session.getActiveSession();
        if(fbsession == null) {
            if(savedInstanceState != null) {
                fbsession = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if(fbsession == null) {
                fbsession = new Session(this);
            }
            Session.setActiveSession(fbsession);
            fbsession.addCallback(statusCallback);
        }

        com.facebook.widget.LoginButton fbloginbutton = (com.facebook.widget.LoginButton) findViewById(R.id.usefbinfobutton);
        fbloginbutton.setReadPermissions(Arrays.asList("public_profile", "user_about_me"));
        fbloginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Listener", "fbloginbutton onclick");

                // Check if there is any Active Session, otherwise Open New Session
                Session fbsession = Session.getActiveSession();

                if (!fbsession.isOpened()) {
                    fbsession.openForRead(new Session.OpenRequest(RegisterActivity.this).setCallback(statusCallback));
                } else {
                    Session.openActiveSession(RegisterActivity.this, true, statusCallback);
                }
            }
        });


        //select image to use as profile pic
        final String [] items			= new String [] {"利用相機拍攝", "從圖庫中選取"};
        ArrayAdapter<String> adapter	= new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder		= new AlertDialog.Builder(this);

        builder.setTitle("請選取頭像");
        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int item ) { //pick from camera
                if (item == 0) {
                    Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { //pick from file
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "選取圖庫"), PICK_FROM_FILE);
                }
            }
        } );

        final AlertDialog dialog = builder.create();

        profilePicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.action_confirm_reg:
                register();
                break;
            case R.id.action_settings:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!finishRegister()){
            /* back to login activity */
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            /* actually noy very possible to come to this stage , just for place holder */
            super.onBackPressed();
        }
    }

    /********** Activity Methods **********/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();

                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");

                    profilePicView.setImageBitmap(photo);
                }

                File f = new File(mImageCaptureUri.getPath());

                if (f.exists()) f.delete();

                break;

        }

        Log.d("FbLogin", "Result Code is - " + resultCode +"");
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Save current session
        super.onSaveInstanceState(outState);
        Session fbsession = Session.getActiveSession();
        Session.saveSession(fbsession, outState);
    }

    @Override
    protected void onStart() {
        // TODO Add status callback
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    protected void onStop() {
        // TODO Remove callback
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session fbsession, SessionState state, Exception exception) {
            //make request to server to validate the login by fb
            //post access token to server
            Log.i("fb session state", state.toString());
            if(fbsession!=null&&fbsession.isOpened()) {
                String access_token = fbsession.getAccessToken();
                Log.i("access_token", access_token);

                makeMeRequest(fbsession);


            }

        }
    }

    private boolean finishRegister() {
        return false;
    }

    private void makeMeRequest(Session fbsession) {
        Request request = Request.newMeRequest(fbsession,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (user != null) {
                            new DownloadImageTask((ImageView) profilePicView)
                                    .execute("https://graph.facebook.com/"+user.getId()+"/picture?width=999&height=999");

                            userNameTextField.setText(user.getName());

                        } else if (response.getError() != null) {
                            // handle error
                        }
                    }
                });
        request.executeAsync();

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );

        int size = list.size();

        if (size == 0) {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i 		= new Intent(intent);
                ResolveInfo res	= list.get(0);

                i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title 	= getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon		= getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent= new Intent(intent);

                    co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("選取截圖程式");
                builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int item ) {
                        startActivityForResult( cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel( DialogInterface dialog ) {

                        if (mImageCaptureUri != null ) {
                            getContentResolver().delete(mImageCaptureUri, null, null );
                            mImageCaptureUri = null;
                        }
                    }
                } );

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }

    private void register(){
        File profilepic=null;
        try {
            //create a file to write bitmap data
            File cacheDir = getBaseContext().getCacheDir();
            profilepic = new File(cacheDir, "profilepic.png");
            profilepic.createNewFile();

            //Convert bitmap to byte array
            profilePicView.buildDrawingCache();
            Bitmap bitmap = profilePicView.getDrawingCache();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(profilepic);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

        }catch(Exception e){

        }

        final String email = emailTextField.getText().toString();
        if(email.equals("")){
            alert.showAlertDialog(RegisterActivity.this,"註冊失敗","請輸入有效的電郵！",false);
            return;
        }
        final String password = passwordTextField.getText().toString();
        if(password.equals("")){
            alert.showAlertDialog(RegisterActivity.this,"註冊失敗","請輸入自定密碼！",false);
            return;
        }
        String repassword = repasswordTextField.getText().toString();
        if(!password.equals(repassword)){
            alert.showAlertDialog(RegisterActivity.this,"註冊失敗","兩次輸入的密碼不同！",false);
            return;
        }

        final String username = userNameTextField.getText().toString();

        try {
            RegisterService.register(this, email, password, profilepic, username, new RegisterService.RegisterServiceCallback() {
                @Override
                public void onSuccess() {
                    Log.i("Register","success, now logging in");

                    /* on start progress */
                    final ProgressDialog pd = alert.showProgress(RegisterActivity.this, "登入", "登入中，請稍候...", true, false);
                    ServerRequestManager.login(email, password, new ServerRequestManager.OnLoginCallback() {
                        @Override
                        public void onSuccessLogin(UserModel user) {
                            // Creating user login session
                            pd.dismiss();
                            session.createLoginSession(user.getUserHashMap());

                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailedLogin(String msg) {
                            pd.dismiss();
                            Log.i("error message", msg);
                            alert.showAlertDialog(RegisterActivity.this, "登入失敗..", msg, null);
                        }
                    });

                }

                @Override
                public void onFailure(String msg) {
                    Log.i("reg error message", msg);
                    alert.showAlertDialog(RegisterActivity.this, "注冊失敗..", msg, null);

                }
            });
        } catch (FileNotFoundException e) {
            /* profile not found (if any) */
            e.printStackTrace();
        }

    }
}
