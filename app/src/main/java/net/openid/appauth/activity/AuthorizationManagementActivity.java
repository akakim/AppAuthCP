package net.openid.appauth.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.dreamsecurity.appauth.R;

/**
 *
 */
public class AuthorizationManagementActivity extends Activity {

//    @VisibleForTesting
    static final String KEY_AUTH_INTENT = "authIntent";

//    @VisibleForTesting
    static final String KEY_AUTH_REQUEST = "authRequest";

//    @VisibleForTesting
    static final String KEY_COMPLETE_INTENT = "completeIntent";

//    @VisibleForTesting
    static final String KEY_CANCEL_INTENT = "cancelIntent";

//    @VisibleForTesting
    static final String KEY_AUTHORIZATION_STARTED = "authStarted";

    private boolean authorizationStarted = false;
    private Intent authIntent;
    private AuthorizationRequest authRequest;
    private PendingIntent completeIntent;
    private PendingIntent cancelIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if( savedInstanceState == null) {
           extractState( getIntent().getExtras() );
       }else {
           extractState( savedInstanceState );
       }
    }

    public void extractState( Bundle instance){

    }
}
