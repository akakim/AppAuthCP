package net.openid.appauth.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 *
 *
 * Activity that receives the redirect Uri sent by the OpenID endpoint. It forwards the data
 * received as part of this redirect to {@link AuthorizationManagementActivity}, which
 * destroys the browser tab before returning the result to the completion
 * {@link android.app.PendingIntent}
 * provided to {@link AuthorizationService#performAuthorizationRequest}.
 *
 * 앱 개발자는 이 라이브러리를 사용할 때 반드시 appAuthResirectScheme
 * App developers using this library must override the `appAuthRedirectScheme`
 * property in their `build.gradle` to specify the custom scheme that will be used for
 * the OAuth2 redirect. If custom scheme redirect cannot be used with the identity provider
 * you are integrating with, then a custom intent filter should be defined in your
 * application manifest instead. For example, to handle
 * `https://www.example.com/oauth2redirect`:
 *
 * ```xml
 * <intent-filter>
 *   <action android:name="android.intent.action.VIEW"/>
 *   <category android:name="android.intent.category.DEFAULT"/>
 *   <category android:name="android.intent.category.BROWSABLE"/>
 *   <data android:scheme="https"
 *          android:host="www.example.com"
 *          android:path="/oauth2redirect" />
 * </intent-filter>
 * ```
 */


public class RedirectUriReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
