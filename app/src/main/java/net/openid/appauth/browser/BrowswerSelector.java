package net.openid.appauth.browser;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import androidx.annotation.VisibleForTesting;
import androidx.browser.customtabs.CustomTabsService;

import java.util.Iterator;

/**
 * 브라우저 패키지 명을 얻기위한 유틸클래스
 * {@link net.openid.appauth.AuthorizationService#performAuthorizationRequest(
 * net.openid.appauth.AuthorizationRequest,
 * android.app.PendingIntent)} calls. It prioritizes browsers which support
 * [custom tabs](https://developer.chrome.com/multidevice/android/customtabs).
 * 이상한 엡의 man in the middle 공격을 예방하기 위해서  정해진 URI를 우리는 보낼 것이다.
 * To mitigate
 * man-in-the-middle attacks by malicious apps pretending to be browsers for the specific URI we
 * query, only those which are registered as a handler for _all_ HTTP and HTTPS URIs will be
 * used.
 */
public final class BrowswerSelector {

    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";


    static final String ACTION_CUSTOM_TABS_CONNECTION =
            CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION;

    /**
     * An arbitrary (but unregistrable, per
     * <a href="https://www.iana.org/domains/reserved">IANA rules</a>) web intent used to query
     * for installed web browsers on the system.
     */
    @VisibleForTesting
    static final Intent BROWSER_INTENT = new Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.example.com"));


    private static boolean hasWarmupService(PackageManager pm, String packageName) {
        Intent serviceIntent = new Intent();
        serviceIntent.setAction(ACTION_CUSTOM_TABS_CONNECTION);
        serviceIntent.setPackage(packageName);
        return (pm.resolveService(serviceIntent, 0) != null);
    }

    private static boolean isFullBrowser(ResolveInfo resolveInfo) {
        // The filter must match ACTION_VIEW, CATEGORY_BROWSEABLE, and at least one scheme,
        if (!resolveInfo.filter.hasAction(Intent.ACTION_VIEW)
                || !resolveInfo.filter.hasCategory(Intent.CATEGORY_BROWSABLE)
                || resolveInfo.filter.schemesIterator() == null) {
            return false;
        }

        // The filter must not be restricted to any particular set of authorities
        if (resolveInfo.filter.authoritiesIterator() != null) {
            return false;
        }

        // The filter must support both HTTP and HTTPS.
        boolean supportsHttp = false;
        boolean supportsHttps = false;
        Iterator<String> schemeIter = resolveInfo.filter.schemesIterator();
        while (schemeIter.hasNext()) {
            String scheme = schemeIter.next();
            supportsHttp |= SCHEME_HTTP.equals(scheme);
            supportsHttps |= SCHEME_HTTPS.equals(scheme);

            if (supportsHttp && supportsHttps) {
                return true;
            }
        }

        // at least one of HTTP or HTTPS is not supported
        return false;
    }


}
