package dev.bbkb.ime.keyboard.inputboard;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import dev.bbkb.ime.R;
import dev.bbkb.ime.core.shared.Logger;

/**
 * Entry point to BenOS Camera's QR-to-clipboard scanner. The scanner copies the decoded text to
 * the clipboard itself and finishes, so no result is returned to this service; the clipboard
 * history picks the new clip up through its usual primary-clip listener.
 */
public final class QrScanLauncher {

    /** Keycode of the input menu's QR scanner item (see {@link UimMenuOrder#QR_SCAN}). */
    public static final int KEY_CODE = -47;

    private static final String TAG = "QrScanLauncher";

    private static final String QR_SCAN_PACKAGE = "com.light.lightcamera";

    private static final String QR_SCAN_ACTION =
            "com.light.lightcamera.action.SCAN_QR_TO_CLIPBOARD";

    private QrScanLauncher() {
    }

    /** Starts the scanner, or reports that it is unavailable when BenOS Camera cannot be started. */
    public static void launch(Context context) {
        Intent intent = new Intent(QR_SCAN_ACTION)
                .setPackage(QR_SCAN_PACKAGE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException | SecurityException e) {
            Logger.warn(TAG, "launch: " + QR_SCAN_PACKAGE + " not started: " + e);
            Toast.makeText(context, R.string.qr_scanner_unavailable, Toast.LENGTH_SHORT).show();
        }
    }
}
