package net.openid.appauth.browser;

import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class BrowserDescriptor {

    // See: http://stackoverflow.com/a/2816747
    private static final int PRIME_HASH_FACTOR = 92821;

    private static final String DIGEST_SHA_512 = "SHA-512";

    /**
     * 브라우저 앱의 패키명
     */
    public final String packageName;

    /**
     * The set of {@link android.content.pm.Signature signatures} of the browser app,
     * which have been hashed with SHA-512, and Base-64 URL-safe encoded.
     */
    public final Set<String> signatureHashes;

    // 브라우저 얍에서의 버전.
    public final String version;

    /**
     * Whether it is intended that the browser will be used via a custom tab.
     */
    public final Boolean useCustomTab;

    /**
     * Creates a description of a browser from a {@link PackageInfo} object returned from the
     * {@link android.content.pm.PackageManager}. The object is expected to include the
     * signatures of the app, which can be retrieved with the
     * {@link android.content.pm.PackageManager#GET_SIGNATURES GET_SIGNATURES} flag when
     * calling {@link android.content.pm.PackageManager#getPackageInfo(String, int)}.
     */
    public BrowserDescriptor(@NonNull PackageInfo packageInfo, boolean useCustomTab) {
        this(
                packageInfo.packageName,
                generateSignatureHashes(packageInfo.signatures),
                packageInfo.versionName,
                useCustomTab);
    }


    /**
     * Creates a description of a browser from the core properties that are frequently used to
     * decide whether a browser can be used for an authorization flow. In most cases, it is
     * more convenient to use the other variant of the constructor that consumes a
     * {@link PackageInfo} object provided by the package manager.
     *
     * @param packageName
     *     The Android package name of the browser.
     * @param signatureHashes
     *     The set of SHA-512, Base64 url safe encoded signatures for the app. This can be
     *     generated for a signature by calling {@link #generateSignatureHash(Signature)}.
     * @param version
     *     The version name of the browser.
     * @param useCustomTab
     *     Whether it is intended to use the browser as a custom tab.
     */
    public BrowserDescriptor(
            @NonNull String packageName,
            @NonNull Set<String> signatureHashes,
            @NonNull String version,
            boolean useCustomTab) {
        this.packageName = packageName;
        this.signatureHashes = signatureHashes;
        this.version = version;
        this.useCustomTab = useCustomTab;
    }

    /**
     * Generates a SHA-512 hash, Base64 url-safe encoded, from a {@link Signature}.
     */
    @NonNull
    public static String generateSignatureHash(@NonNull Signature signature) {
        try {
            MessageDigest digest = MessageDigest.getInstance(DIGEST_SHA_512);
            byte[] hashBytes = digest.digest(signature.toByteArray());
            return Base64.encodeToString(hashBytes, Base64.URL_SAFE | Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "Platform does not support" + DIGEST_SHA_512 + " hashing");
        }
    }


    @Override
    public int hashCode() {
        int hash = packageName.hashCode();

        hash = PRIME_HASH_FACTOR * hash + version.hashCode();
        hash = PRIME_HASH_FACTOR * hash + (useCustomTab ? 1 : 0);

        for (String signatureHash : signatureHashes) {
            hash = PRIME_HASH_FACTOR * hash + signatureHash.hashCode();
        }

        return hash;
    }
    /**
     * Generates a set of SHA-512, Base64 url-safe encoded signature hashes from the provided
     * array of signatures.
     */
    @NonNull
    public static Set<String> generateSignatureHashes(@NonNull Signature[] signatures) {
        Set<String> signatureHashes = new HashSet<>();
        for (Signature signature : signatures) {
            signatureHashes.add(generateSignatureHash(signature));
        }

        return signatureHashes;
    }
}
