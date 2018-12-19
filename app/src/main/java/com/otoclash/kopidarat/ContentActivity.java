package com.otoclash.kopidarat;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.support.v7.app.AppCompatActivity;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.Button;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.content.ClipData;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import android.net.Uri;
import java.io.File;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.media.SoundPool;
import android.view.View;
import android.widget.AdapterView;
import com.bumptech.glide.Glide;
import android.graphics.Typeface;
import android.content.ClipboardManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class ContentActivity extends AppCompatActivity {
	
	public final int REQ_CD_PICK = 101;
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private double klikAddContent = 0;
	private double posisiPickFile = 0;
	private String strLinkIMG = "";
	private HashMap<String, Object> newMap = new HashMap<>();
	private double klik2x = 0;
	private String strLike = "";
	private String strMension = "";
	private HashMap<String, Object> NewMapAkun = new HashMap<>();
	private double posisiAkunKlik = 0;
	private double SendPush = 0;
	private double nb1 = 0;
	private double posisiAkunNotif = 0;
	private HashMap<String, Object> NewMapPushNotif = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> lisMapContent = new ArrayList<>();
	private ArrayList<String> strPick = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ListMapHidenProfil = new ArrayList<>();
	private ArrayList<String> str = new ArrayList<>();
	private ArrayList<String> strAkun = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ListMapPushNotif = new ArrayList<>();
	private ArrayList<String> strPushNotif = new ArrayList<>();
	
	private LinearLayout linearutama;
	private LinearLayout linear1;
	private LinearLayout footermenu;
	private LinearLayout l_content;
	private LinearLayout l_upload;
	private LinearLayout profilhiden;
	private LinearLayout l_notif;
	private LinearLayout header;
	private ListView listview1;
	private ImageView logo;
	private TextView textview1;
	private LinearLayout linear9;
	private ImageView setting;
	private LinearLayout linear4;
	private LinearLayout linearprogressupload;
	private ImageView gambartambah;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private ProgressBar progressbar1;
	private TextView keteranganprogress;
	private EditText keterangan;
	private Button button_upload;
	private ListView listview2;
	private ListView listnotif;
	private AdView adview1;
	private LinearLayout linear10;
	private LinearLayout linear12;
	private ImageView addcontent;
	private LinearLayout linear13;
	private ImageView home;
	private ImageView cari;
	private ImageView notif;
	private ImageView akun;
	
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private DatabaseReference kopidaratData = _firebase.getReference("kopidaratData");
	private ChildEventListener _kopidaratData_child_listener;
	private Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
	private Calendar cal = Calendar.getInstance();
	private StorageReference KopiDarat = _firebase_storage.getReference("KopiDarat");
	private OnSuccessListener<UploadTask.TaskSnapshot> _KopiDarat_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _KopiDarat_download_success_listener;
	private OnSuccessListener _KopiDarat_delete_success_listener;
	private OnProgressListener _KopiDarat_upload_progress_listener;
	private OnProgressListener _KopiDarat_download_progress_listener;
	private OnFailureListener _KopiDarat_failure_listener;
	private Intent iAkun = new Intent();
	private AlertDialog.Builder MoreDialog;
	private InterstitialAd inters;
	private AdListener _inters_ad_listener;
	private SharedPreferences DataLokalHidenProfil;
	private DatabaseReference kopidaratAkunData = _firebase.getReference("kopidaratAkunData");
	private ChildEventListener _kopidaratAkunData_child_listener;
	private TimerTask timerAdsShow;
	private TimerTask timerLoadingDialog;
	private Intent iLihatProfilTeman = new Intent();
	private SharedPreferences LokalDataKlikTeman;
	private DatabaseReference PushNotif = _firebase.getReference("PushNotif");
	private ChildEventListener _PushNotif_child_listener;
	private DatabaseReference Notif = _firebase.getReference("Notif");
	private ChildEventListener _Notif_child_listener;
	private TimerTask RunCheckNotif;
	private SoundPool notifbell;
	private Intent iNotif = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.content);
		initialize();
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize() {
		
		linearutama = (LinearLayout) findViewById(R.id.linearutama);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		footermenu = (LinearLayout) findViewById(R.id.footermenu);
		l_content = (LinearLayout) findViewById(R.id.l_content);
		l_upload = (LinearLayout) findViewById(R.id.l_upload);
		profilhiden = (LinearLayout) findViewById(R.id.profilhiden);
		l_notif = (LinearLayout) findViewById(R.id.l_notif);
		header = (LinearLayout) findViewById(R.id.header);
		listview1 = (ListView) findViewById(R.id.listview1);
		logo = (ImageView) findViewById(R.id.logo);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		setting = (ImageView) findViewById(R.id.setting);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linearprogressupload = (LinearLayout) findViewById(R.id.linearprogressupload);
		gambartambah = (ImageView) findViewById(R.id.gambartambah);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		keteranganprogress = (TextView) findViewById(R.id.keteranganprogress);
		keterangan = (EditText) findViewById(R.id.keterangan);
		button_upload = (Button) findViewById(R.id.button_upload);
		listview2 = (ListView) findViewById(R.id.listview2);
		listnotif = (ListView) findViewById(R.id.listnotif);
		adview1 = (AdView) findViewById(R.id.adview1);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		addcontent = (ImageView) findViewById(R.id.addcontent);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		home = (ImageView) findViewById(R.id.home);
		cari = (ImageView) findViewById(R.id.cari);
		notif = (ImageView) findViewById(R.id.notif);
		akun = (ImageView) findViewById(R.id.akun);
		auth = FirebaseAuth.getInstance();
		pick.setType("image/*");
		pick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		MoreDialog = new AlertDialog.Builder(this);
		DataLokalHidenProfil = getSharedPreferences("DataLokalHidenProfil", Activity.MODE_PRIVATE);
		LokalDataKlikTeman = getSharedPreferences("LokalDataKlikTeman", Activity.MODE_PRIVATE);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				strLinkIMG = lisMapContent.get((int)_position).get("img").toString();
				strLike = lisMapContent.get((int)_position).get("like").toString();
				strMension = lisMapContent.get((int)_position).get("mension").toString();
				klik2x++;
				if (klik2x == 2) {
					
					final AlertDialog dialog = new AlertDialog.Builder(ContentActivity.this).create();
					LayoutInflater inflater = getLayoutInflater();
					
					View convertView = (View) inflater.inflate(R.layout.like_c, null);
					dialog.setView(convertView);
					dialog.show();
					klik2x = 0;
					timerAdsShow = new TimerTask() {
											@Override
											public void run() {
													runOnUiThread(new Runnable() {
															@Override
															public void run() {
																	dialog.dismiss();
															}
													});
											}
									};
									_timer.schedule(timerAdsShow, (int)(1000));
					kopidaratData.child(str.get((int)(_position))).removeValue();
					newMap = new HashMap<>();
					newMap.put("img", strLinkIMG);
					newMap.put("akun", DataLokalHidenProfil.getString("username", ""));
					newMap.put("mension", strMension);
					newMap.put("like", String.valueOf((long)(Double.parseDouble(strLike) + 1)));
					newMap.put("fotoprofil", DataLokalHidenProfil.getString("fotoprofil", ""));
					newMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
					kopidaratData.push().updateChildren(newMap);
				}
			}
		});
		
		setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final AlertDialog dialog = new AlertDialog.Builder(ContentActivity.this).create();
				LayoutInflater inflater = getLayoutInflater();
				
				View convertView = (View) inflater.inflate(R.layout.ondevelop_c, null);
				dialog.setView(convertView);
				
				
				Button btn1 = (Button) convertView.findViewById(R.id.button1);
				
				btn1.setOnClickListener(new View.OnClickListener(){
					    public void onClick(View v){
						dialog.dismiss();
						       
						    }
				});
				
				dialog.show();
			}
		});
		
		gambartambah.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				posisiPickFile = 0;
				startActivityForResult(pick, REQ_CD_PICK);
			}
		});
		
		button_upload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (strLinkIMG.equals("") || keterangan.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Tidak Boleh Kosong!");
				}
				else {
					kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {
							ListMapHidenProfil = new ArrayList<>();
							try {
								GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
								for (DataSnapshot _data : _dataSnapshot.getChildren()) {
									HashMap<String, Object> _map = _data.getValue(_ind);
									ListMapHidenProfil.add(_map);
								}
							}
							catch (Exception _e) {
								_e.printStackTrace();
							}
							strAkun.clear();
							for (DataSnapshot dshot: _dataSnapshot.getChildren()){
								strAkun.add(dshot.getKey());
							}
							listview2.setAdapter(new Listview2Adapter(ListMapHidenProfil));
							((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
					});
					kopidaratAkunData.child(strAkun.get((int)(posisiAkunKlik))).removeValue();
					newMap = new HashMap<>();
					newMap.put("img", strLinkIMG);
					newMap.put("akun", DataLokalHidenProfil.getString("username", ""));
					newMap.put("mension", keterangan.getText().toString());
					newMap.put("like", "0");
					newMap.put("fotoprofil", DataLokalHidenProfil.getString("fotoprofil", ""));
					newMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
					kopidaratData.push().updateChildren(newMap);
					l_content.setVisibility(View.VISIBLE);
					l_upload.setVisibility(View.GONE);
					klikAddContent = 0;
					gambartambah.setImageResource(R.drawable.uploadimage);
					keterangan.setText("");
					_NewMapAkunTambahPost();
				}
			}
		});
		
		addcontent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				inters = new InterstitialAd(getApplicationContext());
				inters.setAdListener(_inters_ad_listener);
				inters.setAdUnitId("ca-app-pub-4431852343868269/1647312376");
				inters.loadAd(new AdRequest.Builder().addTestDevice("B42241232916E7B456D76CD3501734CC")
				.build());
				l_content.setVisibility(View.GONE);
				l_upload.setVisibility(View.VISIBLE);
				klikAddContent = 1;
			}
		});
		
		notif.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (SendPush == 1) {
					_CelPushNotif();
					PushNotif.child(strPushNotif.get((int)(posisiAkunNotif))).removeValue();
					NewMapPushNotif = new HashMap<>();
					NewMapPushNotif.put("emailakun", FirebaseAuth.getInstance().getCurrentUser().getEmail());
					NewMapPushNotif.put("notif", "0");
					PushNotif.push().updateChildren(NewMapPushNotif);
					iNotif.setAction(Intent.ACTION_VIEW);
					iNotif.setClass(getApplicationContext(), NotifkasiActivity.class);
					iNotif.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(iNotif);
					finish();
				}
				else {
					iNotif.setAction(Intent.ACTION_VIEW);
					iNotif.setClass(getApplicationContext(), NotifkasiActivity.class);
					iNotif.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(iNotif);
					finish();
				}
			}
		});
		
		akun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iAkun.setAction(Intent.ACTION_VIEW);
				iAkun.setClass(getApplicationContext(), AkunActivity.class);
				iAkun.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(iAkun);
				RunCheckNotif.cancel();
				finish();
			}
		});
		
		_kopidaratData_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				kopidaratData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lisMapContent = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lisMapContent.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							str.add(dshot.getKey());
						}
						listview1.setAdapter(new Listview1Adapter(lisMapContent));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				kopidaratData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lisMapContent = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lisMapContent.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							str.add(dshot.getKey());
						}
						listview1.setAdapter(new Listview1Adapter(lisMapContent));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		kopidaratData.addChildEventListener(_kopidaratData_child_listener);
		
		_KopiDarat_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				if (_progressValue < 100) {
					linearprogressupload.setVisibility(View.VISIBLE);
					keteranganprogress.setText("Proses upload....");
				}
				else {
					if (_progressValue == 100) {
						linearprogressupload.setVisibility(View.GONE);
						keteranganprogress.setText("");
					}
				}
			}
		};
		
		_KopiDarat_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_KopiDarat_upload_success_listener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot _param1) {
				final String _downloadUrl = _param1.getDownloadUrl().toString();
				strLinkIMG = _downloadUrl;
				SketchwareUtil.showMessage(getApplicationContext(), "Sukses Terupload");
			}
		};
		
		_KopiDarat_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_KopiDarat_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_KopiDarat_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				SketchwareUtil.showMessage(getApplicationContext(), _message);
			}
		};
		
		_kopidaratAkunData_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapHidenProfil = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapHidenProfil.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strAkun.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strAkun.add(dshot.getKey());
						}
						listview2.setAdapter(new Listview2Adapter(ListMapHidenProfil));
						((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapHidenProfil = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapHidenProfil.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strAkun.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strAkun.add(dshot.getKey());
						}
						listview2.setAdapter(new Listview2Adapter(ListMapHidenProfil));
						((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		kopidaratAkunData.addChildEventListener(_kopidaratAkunData_child_listener);
		
		_PushNotif_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				PushNotif.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapPushNotif = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapPushNotif.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strPushNotif.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strPushNotif.add(dshot.getKey());
						}
						listnotif.setAdapter(new ListnotifAdapter(ListMapPushNotif));
						((BaseAdapter)listnotif.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				PushNotif.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapPushNotif = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapPushNotif.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strPushNotif.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strPushNotif.add(dshot.getKey());
						}
						listnotif.setAdapter(new ListnotifAdapter(ListMapPushNotif));
						((BaseAdapter)listnotif.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				PushNotif.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapPushNotif = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapPushNotif.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strPushNotif.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strPushNotif.add(dshot.getKey());
						}
						listnotif.setAdapter(new ListnotifAdapter(ListMapPushNotif));
						((BaseAdapter)listnotif.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		PushNotif.addChildEventListener(_PushNotif_child_listener);
		
		_Notif_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		Notif.addChildEventListener(_Notif_child_listener);
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
		
		_inters_ad_listener = new AdListener() {
			@Override
			public void onAdLoaded() {
				timerAdsShow = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								inters.show();
							}
						});
					}
				};
				_timer.schedule(timerAdsShow, (int)(3000));
			}
			
			@Override
			public void onAdFailedToLoad(int _param1) {
				final int _errorCode = _param1;
				SketchwareUtil.showMessage(getApplicationContext(), String.valueOf((long)(_errorCode)));
			}
			
			@Override
			public void onAdOpened() {
				
			}
			
			@Override
			public void onAdClosed() {
				
			}
		};
	}
	private void initializeLogic() {
		notifbell = new SoundPool((int)(1), AudioManager.STREAM_MUSIC, 0);
		nb1 = notifbell.load(getApplicationContext(), R.raw.point, 1);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 1);
		keterangan.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		button_upload.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 1);
		Glide.with(getApplicationContext()).load(Uri.parse(DataLokalHidenProfil.getString("fotoprofil", ""))).into(akun);
		adview1.loadAd(new AdRequest.Builder().addTestDevice("B42241232916E7B456D76CD3501734CC")
		.build());
		linearprogressupload.setVisibility(View.GONE);
		kopidaratData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				lisMapContent = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						lisMapContent.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				str.clear();
				for (DataSnapshot dshot: _dataSnapshot.getChildren()){
					str.add(dshot.getKey());
				}
				listview1.setAdapter(new Listview1Adapter(lisMapContent));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		_TarikDataHiden();
		_CelPushNotif();
		_Elevation();
		RunCheckNotif = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (SendPush == 1) {
							notif.setImageResource(R.drawable.notification);
						}
						else {
							notif.setImageResource(R.drawable.bell);
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(RunCheckNotif, (int)(100), (int)(100));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_PICK:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				gambartambah.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get((int)(posisiPickFile)), 1024, 1024));
				KopiDarat.child("img".concat("-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(1), (int)(999)))).concat("-".concat(new SimpleDateFormat("ddMMyyyy").format(cal.getTime()).concat("-".concat(new SimpleDateFormat("hhmmss").format(cal.getTime()).concat(".png")))))))).putFile(Uri.fromFile(new File(_filePath.get((int)(posisiPickFile))))).addOnSuccessListener(_KopiDarat_upload_success_listener).addOnFailureListener(_KopiDarat_failure_listener).addOnProgressListener(_KopiDarat_upload_progress_listener);
			}
			else {
				SketchwareUtil.showMessage(getApplicationContext(), "Upload Dibatalkan");
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (klikAddContent == 1) {
			l_content.setVisibility(View.VISIBLE);
			l_upload.setVisibility(View.GONE);
			klikAddContent = 0;
		}
		else {
			final AlertDialog dialog = new AlertDialog.Builder(ContentActivity.this).create();
			LayoutInflater inflater = getLayoutInflater();
			
			View convertView = (View) inflater.inflate(R.layout.exit_c, null);
			dialog.setView(convertView);
			
			AdView adview_exit = (AdView) convertView.findViewById(R.id.adview_exit);
			
			adview_exit.loadAd(new AdRequest.Builder().build());
			
			Button btn1 = (Button) convertView.findViewById(R.id.button1);
			
			btn1.setOnClickListener(new View.OnClickListener(){
				    public void onClick(View v){
					dialog.dismiss();
					finishAffinity();
					       
					    }
			});
			
			dialog.show();
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			
			lp.copyFrom(dialog.getWindow().getAttributes());
			lp.width = 1100;
			lp.height = 1000;
			lp.x=-100;
			lp.y=100;
			dialog.getWindow().setAttributes(lp);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle _savedInstanceState) {
		super.onPostCreate(_savedInstanceState);
		
		final AlertDialog dialog_loading = new AlertDialog.Builder(ContentActivity.this).create();
		LayoutInflater inflater = getLayoutInflater();
		
		View convertView = (View) inflater.inflate(R.layout.animasiloading_c, null);
		dialog_loading.setView(convertView);
		
		ImageView img_c = (ImageView) convertView.findViewById(R.id.imageview1);
		
		Glide.with(getApplicationContext()).load(Uri.parse("https://cdn.dribbble.com/users/4908/screenshots/2760742/launching-rocket-dribbble.gif")).into(img_c);
		
		dialog_loading.show();
		timerLoadingDialog = new TimerTask() {
								@Override
								public void run() {
										runOnUiThread(new Runnable() {
												@Override
												public void run() {
														dialog_loading.dismiss();
												}
										});
								}
						};
						_timer.schedule(timerLoadingDialog, (int)(7000));
	}
	private void _Elevation () {
		header.setElevation(20f);
		footermenu.setElevation(20f);
	}
	
	
	private void _TarikDataHiden () {
		kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				ListMapHidenProfil = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						ListMapHidenProfil.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				strAkun.clear();
				for (DataSnapshot dshot: _dataSnapshot.getChildren()){
					strAkun.add(dshot.getKey());
				}
				listview2.setAdapter(new Listview2Adapter(ListMapHidenProfil));
				((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	private void _NewMapAkunTambahPost () {
		NewMapAkun.put("username", DataLokalHidenProfil.getString("username", ""));
		NewMapAkun.put("email", DataLokalHidenProfil.getString("email", ""));
		NewMapAkun.put("fotoprofil", DataLokalHidenProfil.getString("fotoprofil", ""));
		NewMapAkun.put("bio", DataLokalHidenProfil.getString("bio", ""));
		NewMapAkun.put("nama", DataLokalHidenProfil.getString("nama", ""));
		NewMapAkun.put("nope", DataLokalHidenProfil.getString("nope", ""));
		NewMapAkun.put("sex", DataLokalHidenProfil.getString("sex", ""));
		NewMapAkun.put("situs", DataLokalHidenProfil.getString("situs", ""));
		NewMapAkun.put("mengikuti", DataLokalHidenProfil.getString("mengikuti", ""));
		NewMapAkun.put("diikuti", DataLokalHidenProfil.getString("diikuti", ""));
		NewMapAkun.put("post", String.valueOf((long)(Double.parseDouble(DataLokalHidenProfil.getString("post", "")) + 1)));
		kopidaratAkunData.push().updateChildren(NewMapAkun);
	}
	
	
	private void _CelPushNotif () {
		PushNotif.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				ListMapPushNotif = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						ListMapPushNotif.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				strPushNotif.clear();
				for (DataSnapshot dshot: _dataSnapshot.getChildren()){
					strPushNotif.add(dshot.getKey());
				}
				listnotif.setAdapter(new ListnotifAdapter(ListMapPushNotif));
				((BaseAdapter)listnotif.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.listview_custom1, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final LinearLayout linear5 = (LinearLayout) _v.findViewById(R.id.linear5);
			final LinearLayout linear8 = (LinearLayout) _v.findViewById(R.id.linear8);
			final LinearLayout linear3 = (LinearLayout) _v.findViewById(R.id.linear3);
			final ImageView logoakun = (ImageView) _v.findViewById(R.id.logoakun);
			final TextView account = (TextView) _v.findViewById(R.id.account);
			final LinearLayout linear9 = (LinearLayout) _v.findViewById(R.id.linear9);
			final TextView email = (TextView) _v.findViewById(R.id.email);
			final ImageView more = (ImageView) _v.findViewById(R.id.more);
			final ImageView image_content = (ImageView) _v.findViewById(R.id.image_content);
			final LinearLayout linear2 = (LinearLayout) _v.findViewById(R.id.linear2);
			final TextView like = (TextView) _v.findViewById(R.id.like);
			final LinearLayout linear4 = (LinearLayout) _v.findViewById(R.id.linear4);
			final LinearLayout linear11 = (LinearLayout) _v.findViewById(R.id.linear11);
			
			final ImageView imageview_loveit = (ImageView) _v.findViewById(R.id.imageview_loveit);
			final ImageView komentar = (ImageView) _v.findViewById(R.id.komentar);
			final ImageView share_button = (ImageView) _v.findViewById(R.id.share_button);
			final ImageView download_button = (ImageView) _v.findViewById(R.id.download_button);
			final LinearLayout linear10 = (LinearLayout) _v.findViewById(R.id.linear10);
			final ImageView bookmark = (ImageView) _v.findViewById(R.id.bookmark);
			final TextView akun2 = (TextView) _v.findViewById(R.id.akun2);
			final LinearLayout linear12 = (LinearLayout) _v.findViewById(R.id.linear12);
			final TextView mension = (TextView) _v.findViewById(R.id.mension);
			final TextView komentarteratas = (TextView) _v.findViewById(R.id.komentarteratas);
			final ImageView fotoakunku = (ImageView) _v.findViewById(R.id.fotoakunku);
			final EditText edittextkirimkomen = (EditText) _v.findViewById(R.id.edittextkirimkomen);
			
			final AdView adview1 = (AdView) _v.findViewById(R.id.adview1);
			email.setText(_data.get((int)_position).get("email").toString());
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("img").toString())).into(image_content);
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("fotoprofil").toString())).into(logoakun);
			account.setText(_data.get((int)_position).get("akun").toString());
			akun2.setText(_data.get((int)_position).get("akun").toString());
			mension.setText(_data.get((int)_position).get("mension").toString());
			like.setText(_data.get((int)_position).get("like").toString().concat(" suka"));
			Glide.with(getApplicationContext()).load(Uri.parse(DataLokalHidenProfil.getString("fotoprofil", ""))).into(fotoakunku);
			account.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 1);
			akun2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 1);
			like.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
			mension.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
			komentarteratas.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
			edittextkirimkomen.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
			adview1.loadAd(new AdRequest.Builder().addTestDevice("B42241232916E7B456D76CD3501734CC")
			.build());
			if (_position == 2) {
				adview1.setVisibility(View.VISIBLE);
			}
			else {
				if (_position == 4) {
					adview1.setVisibility(View.VISIBLE);
				}
				else {
					adview1.setVisibility(View.GONE);
				}
			}
			more.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					MoreDialog.setPositiveButton("Salin Tautan", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _data.get((int)_position).get("img").toString()));
						}
					});
					MoreDialog.setNegativeButton("Kunjungi Profil", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							if (_data.get((int)_position).get("email").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
								SketchwareUtil.showMessage(getApplicationContext(), "ini akun anda");
							}
							else {
								LokalDataKlikTeman.edit().putString("emailteman", _data.get((int)_position).get("email").toString()).commit();
								iLihatProfilTeman.setAction(Intent.ACTION_VIEW);
								iLihatProfilTeman.setClass(getApplicationContext(), LihatprofilActivity.class);
								iLihatProfilTeman.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(iLihatProfilTeman);
								RunCheckNotif.cancel();
								finish();
							}
						}
					});
					MoreDialog.create().show();
				}
			});
			share_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					 Intent i = new Intent(android.content.Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Kopi Darat"); i.putExtra(android.content.Intent.EXTRA_TEXT, _data.get((int)_position).get("mension").toString().concat(" - ".concat(_data.get((int)_position).get("img").toString()))); startActivity(Intent.createChooser(i,"Bagikan Dengan"));
				}
			});
			download_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					final AlertDialog dialog = new AlertDialog.Builder(ContentActivity.this).create();
					LayoutInflater inflater = getLayoutInflater();
					
					View convertView = (View) inflater.inflate(R.layout.ondevelop_c, null);
					dialog.setView(convertView);
					
					
					Button btn1 = (Button) convertView.findViewById(R.id.button1);
					
					btn1.setOnClickListener(new View.OnClickListener(){
						    public void onClick(View v){
							dialog.dismiss();
							       
							    }
					});
					
					dialog.show();
				}
			});
			bookmark.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					final AlertDialog dialog = new AlertDialog.Builder(ContentActivity.this).create();
					LayoutInflater inflater = getLayoutInflater();
					
					View convertView = (View) inflater.inflate(R.layout.ondevelop_c, null);
					dialog.setView(convertView);
					
					
					Button btn1 = (Button) convertView.findViewById(R.id.button1);
					
					btn1.setOnClickListener(new View.OnClickListener(){
						    public void onClick(View v){
							dialog.dismiss();
							       
							    }
					});
					
					dialog.show();
				}
			});
			edittextkirimkomen.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					final AlertDialog dialog = new AlertDialog.Builder(ContentActivity.this).create();
					LayoutInflater inflater = getLayoutInflater();
					
					View convertView = (View) inflater.inflate(R.layout.ondevelop_c, null);
					dialog.setView(convertView);
					
					
					Button btn1 = (Button) convertView.findViewById(R.id.button1);
					
					btn1.setOnClickListener(new View.OnClickListener(){
						    public void onClick(View v){
							dialog.dismiss();
							       
							    }
					});
					
					dialog.show();
				}
			});
			account.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).get("email").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
						SketchwareUtil.showMessage(getApplicationContext(), "ini akun anda");
					}
					else {
						LokalDataKlikTeman.edit().putString("emailteman", _data.get((int)_position).get("email").toString()).commit();
						iLihatProfilTeman.setAction(Intent.ACTION_VIEW);
						iLihatProfilTeman.setClass(getApplicationContext(), LihatprofilActivity.class);
						iLihatProfilTeman.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(iLihatProfilTeman);
						RunCheckNotif.cancel();
						finish();
					}
				}
			});
			logoakun.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).get("email").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
						SketchwareUtil.showMessage(getApplicationContext(), "ini akun anda");
					}
					else {
						LokalDataKlikTeman.edit().putString("emailteman", _data.get((int)_position).get("email").toString()).commit();
						iLihatProfilTeman.setAction(Intent.ACTION_VIEW);
						iLihatProfilTeman.setClass(getApplicationContext(), LihatprofilActivity.class);
						iLihatProfilTeman.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(iLihatProfilTeman);
						RunCheckNotif.cancel();
						finish();
					}
				}
			});
			
			return _v;
		}
	}
	
	public class Listview2Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.hidenprofil_c, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView email = (TextView) _v.findViewById(R.id.email);
			final TextView fotoprofil = (TextView) _v.findViewById(R.id.fotoprofil);
			final TextView username = (TextView) _v.findViewById(R.id.username);
			final TextView nama = (TextView) _v.findViewById(R.id.nama);
			final TextView bio = (TextView) _v.findViewById(R.id.bio);
			final TextView nope = (TextView) _v.findViewById(R.id.nope);
			final TextView sex = (TextView) _v.findViewById(R.id.sex);
			final TextView situs = (TextView) _v.findViewById(R.id.situs);
			final TextView mengikuti = (TextView) _v.findViewById(R.id.mengikuti);
			final TextView diikuti = (TextView) _v.findViewById(R.id.diikuti);
			final TextView post = (TextView) _v.findViewById(R.id.post);
			
			if (_data.get((int)_position).get("email").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
				posisiAkunKlik = _position;
				email.setText(_data.get((int)_position).get("email").toString());
				fotoprofil.setText(_data.get((int)_position).get("fotoprofil").toString());
				username.setText(_data.get((int)_position).get("username").toString());
				nama.setText(_data.get((int)_position).get("nama").toString());
				bio.setText(_data.get((int)_position).get("bio").toString());
				nope.setText(_data.get((int)_position).get("nope").toString());
				sex.setText(_data.get((int)_position).get("sex").toString());
				situs.setText(_data.get((int)_position).get("situs").toString());
				mengikuti.setText(_data.get((int)_position).get("mengikuti").toString());
				diikuti.setText(_data.get((int)_position).get("diikuti").toString());
				post.setText(_data.get((int)_position).get("post").toString());
				DataLokalHidenProfil.edit().putString("email", _data.get((int)_position).get("email").toString()).commit();
				DataLokalHidenProfil.edit().putString("fotoprofil", _data.get((int)_position).get("fotoprofil").toString()).commit();
				DataLokalHidenProfil.edit().putString("username", _data.get((int)_position).get("username").toString()).commit();
				DataLokalHidenProfil.edit().putString("nama", _data.get((int)_position).get("nama").toString()).commit();
				DataLokalHidenProfil.edit().putString("bio", _data.get((int)_position).get("bio").toString()).commit();
				DataLokalHidenProfil.edit().putString("nope", _data.get((int)_position).get("nope").toString()).commit();
				DataLokalHidenProfil.edit().putString("sex", _data.get((int)_position).get("sex").toString()).commit();
				DataLokalHidenProfil.edit().putString("situs", _data.get((int)_position).get("situs").toString()).commit();
				DataLokalHidenProfil.edit().putString("mengikuti", _data.get((int)_position).get("mengikuti").toString()).commit();
				DataLokalHidenProfil.edit().putString("diikuti", _data.get((int)_position).get("diikuti").toString()).commit();
				DataLokalHidenProfil.edit().putString("post", _data.get((int)_position).get("post").toString()).commit();
			}
			else {
				linear1.setVisibility(View.GONE);
			}
			
			return _v;
		}
	}
	
	public class ListnotifAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public ListnotifAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.listnotif_c, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView emailakun = (TextView) _v.findViewById(R.id.emailakun);
			final TextView notif = (TextView) _v.findViewById(R.id.notif);
			
			if (_data.get((int)_position).get("emailakun").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
				posisiAkunNotif = _position;
				emailakun.setText(_data.get((int)_position).get("emailakun").toString());
				notif.setText(_data.get((int)_position).get("notif").toString());
				if (_data.get((int)_position).get("notif").toString().equals("1")) {
					SendPush = 1;
					nb1 = notifbell.play((int)(1), 1.0f, 1.0f, 1, (int)(0), 1.0f);
				}
				else {
					SendPush = 0;
				}
			}
			else {
				linear1.setVisibility(View.GONE);
			}
			
			return _v;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
