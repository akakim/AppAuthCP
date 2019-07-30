package net.openid.appauth.browser;

import androidx.annotation.NonNull;

public interface BrowserMatcher {

    boolean mathces(@NonNull BrowserDescriptor descriptor);
}
