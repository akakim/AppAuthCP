package net.openid.appauth.browser;

import java.util.Collections;
import java.util.Set;

/**
 *  Browser Matchers를위해 사용되는 Android 상에서 보통 브라우저들이 사용하는 속성의 모음집
 */
public final class Browsers {

    public static final class Chrome{
        public static final String PACKAGE_NAME = "com.android.chrome";
        public static final String SIGNATURE    = "7fmduHKTdHHrlMvldlEqAIlSfii1tl35bxj1OXN5Ve8c4lU6URVu4xtSHc3BVZxS"
                                            + "6WWJnxMDhIfQN0N0K2NDJg==";
        public static final Set<String> SIGNATURE_SET =
                Collections.singleton(SIGNATURE);
//        public static final DelimitedVersion MINIMUM_VERSION_FOR_CUSTOM_TAB =
//                DelimitedVersion.parse("45");

    }
}
