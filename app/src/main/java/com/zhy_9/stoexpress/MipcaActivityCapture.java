package com.zhy_9.stoexpress;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zhy_9.stoexpress.db.SQLManager;
import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.view.CircleCornerDialog;
import com.zhy_9.stoexpress.view.TitleView;
import com.zhy_9.stoexpress.view.ViewfinderView;
import com.zhy_9.stoexpress.zxing.camera.CameraManager;
import com.zhy_9.stoexpress.zxing.decoding.CaptureActivityHandler;
import com.zhy_9.stoexpress.zxing.decoding.InactivityTimer;

/**
 * Initial the camera
 * 
 * 
 */
public class MipcaActivityCapture extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private TitleView qrTitle;

	private TextView batchScan;
	private TextView openLight;
	private TextView editByHand;
	private SQLManager manager;
	private int flag;
	private String editNum;
	private EditText resultEdit;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_capture);
		CameraManager.init(getApplication());
		manager = new SQLManager(this);

		Intent intent = getIntent();
		flag = intent.getIntExtra(ConstansValues.BUTTON_FLAG, 0);

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		qrTitle = (TitleView) findViewById(R.id.qr_title);
		qrTitle.setTitle("扫一扫");
		qrTitle.setRightTextGone(true);
		qrTitle.setBackgroundColor(getResources().getColor(R.color.black));

		batchScan = (TextView) findViewById(R.id.batch_scan);
		openLight = (TextView) findViewById(R.id.open_flash_light);
		editByHand = (TextView) findViewById(R.id.edit_by_hand);

		openLight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					CameraManager.get().openOrCloseFlashLight();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		editByHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View batchDialog = getLayoutInflater().inflate(
						R.layout.batch_scan_dialog, null);
				final CircleCornerDialog dialog = new CircleCornerDialog(
						MipcaActivityCapture.this, 300, 256, batchDialog,
						R.style.batch_dialog);
				dialog.show();
				resultEdit = (EditText) batchDialog
						.findViewById(R.id.scan_edit);
				batchDialog.findViewById(R.id.scan_dialog_cancel)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
				batchDialog.findViewById(R.id.scan_dialog_ensure)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (TextUtils.isEmpty(resultEdit.getText()
										.toString())) {
									Toast.makeText(MipcaActivityCapture.this,
											"快递单号不能为空", Toast.LENGTH_SHORT)
											.show();
								} else {
									editNum = resultEdit.getText().toString();
									dialog.dismiss();
									Intent intent = new Intent(
											MipcaActivityCapture.this,
											DeliveryActivity.class);
									intent.putExtra("result", editNum);
									intent.putExtra(ConstansValues.BUTTON_FLAG,
											flag);
									startActivity(intent);
									finish();
								}

							}
						});
			}
		});

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 处理扫描结果
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(MipcaActivityCapture.this, "Scan failed!",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(MipcaActivityCapture.this, resultString,
					Toast.LENGTH_SHORT).show();
			// ScanRecord record = new ScanRecord();
			// record.setExpressId(resultString);
			// manager.addScanRecord(record);
			Intent intent = new Intent(MipcaActivityCapture.this,
					DeliveryActivity.class);
			intent.putExtra("result", resultString);
			intent.putExtra(ConstansValues.BUTTON_FLAG, flag);
			startActivity(intent);
			finish();
			// Intent resultIntent = new Intent();
			// // Bundle bundle = new Bundle();
			// // bundle.putString("result", resultString);
			// // bundle.putParcelable("bitmap", barcode);
			// // resultIntent.putExtras(bundle);
			// resultIntent.putExtra("result", resultString);
			// Log.e("RESULT", resultString);
			// // resultIntent.setClass(MipcaActivityCapture.this,
			// WebViewActivity.class);
			// // startActivity(resultIntent);
			// // this.setResult(RESULT_OK, resultIntent);
			// setResult(1, resultIntent);
			// finish();
		}
		// MipcaActivityCapture.this.finish();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}