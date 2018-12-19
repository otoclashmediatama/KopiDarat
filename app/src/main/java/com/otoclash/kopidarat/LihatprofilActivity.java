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
import android.widget.Button;
import android.app.Activity;
import android.content.SharedPreferences;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import android.net.Uri;
import java.io.File;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.view.View;
import com.bumptech.glide.Glide;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class LihatprofilActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private double ukuranfile = 0;
	private String strEmail = "";
	private String strFotoProfil = "";
	private String strUsername = "";
	private String strNama = "";
	private String strBio = "";
	private String strNope = "";
	private String strSex = "";
	private String strSitus = "";
	private String strMengikuti = "";
	private String strDiikuti = "";
	private String strPost = "";
	private String strEmailSaya = "";
	private String strFotoProfilSaya = "";
	private String strUsernameSaya = "";
	private String strNamaSaya = "";
	private String strBioSaya = "";
	private String strNopeSaya = "";
	private String strSexSaya = "";
	private String strSitusSaya = "";
	private String strMengikutiSaya = "";
	private String strDiikutiSaya = "";
	private String strPostSaya = "";
	private double posisiSaya = 0;
	private double posisiTeman = 0;
	private HashMap<String, Object> NewMapTeman = new HashMap<>();
	private HashMap<String, Object> NewMapSaya = new HashMap<>();
	private HashMap<String, Object> MapPengikutBaru = new HashMap<>();
	private HashMap<String, Object> MapMengikutiBaru = new HashMap<>();
	private double klikPushNotif = 0;
	private double readyNotifData = 0;
	private HashMap<String, Object> NewMapPushNotif = new HashMap<>();
	private HashMap<String, Object> NewMapNotif = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> ListMapAkunTeman = new ArrayList<>();
	private ArrayList<String> strAkun = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ListMapKontenTeman = new ArrayList<>();
	private ArrayList<String> strKonten = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ListMapAkunSaya = new ArrayList<>();
	private ArrayList<String> strAkunSaya = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ListMapPushNotif = new ArrayList<>();
	private ArrayList<String> strPushNotif = new ArrayList<>();
	private ArrayList<String> strMengikutiUser = new ArrayList<>();
	
	private LinearLayout linearpembagi;
	private LinearLayout linear2;
	private LinearLayout linearlistviewhiden;
	private LinearLayout linearlistviewsaya;
	private LinearLayout l_pushnotif;
	private LinearLayout linear29;
	private LinearLayout linear37;
	private LinearLayout linear8;
	private ListView listcontent_teman;
	private LinearLayout linear30;
	private LinearLayout linear3;
	private ImageView back;
	private TextView namaakun;
	private ImageView fotoprofilteman;
	private LinearLayout linear31;
	private LinearLayout linear32;
	private LinearLayout linear33;
	private LinearLayout linear34;
	private LinearLayout linear35;
	private LinearLayout linear36;
	private TextView postingan;
	private TextView textview2;
	private TextView pengikut;
	private TextView textview4;
	private TextView mengikuti;
	private TextView textview6;
	private Button follow;
	private Button dm;
	private TextView bio;
	private TextView site;
	private LinearLayout linear4;
	private Button mailto;
	private Button call;
	private Button gotosite;
	private ListView listprofil_teman;
	private ListView listviewsaya;
	private ListView list_pushnotif;
	
	private SharedPreferences LokalDataKlikTeman;
	private DatabaseReference kopidaratData = _firebase.getReference("kopidaratData");
	private ChildEventListener _kopidaratData_child_listener;
	private DatabaseReference kopidaratAkunData = _firebase.getReference("kopidaratAkunData");
	private ChildEventListener _kopidaratAkunData_child_listener;
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private StorageReference kopiDarat = _firebase_storage.getReference("kopiDarat");
	private OnSuccessListener<UploadTask.TaskSnapshot> _kopiDarat_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _kopiDarat_download_success_listener;
	private OnSuccessListener _kopiDarat_delete_success_listener;
	private OnProgressListener _kopiDarat_upload_progress_listener;
	private OnProgressListener _kopiDarat_download_progress_listener;
	private OnFailureListener _kopiDarat_failure_listener;
	private Calendar cal = Calendar.getInstance();
	private TimerTask timerLoadingDialog;
	private Intent iMail = new Intent();
	private Intent iCall = new Intent();
	private Intent iSite = new Intent();
	private Intent iBack = new Intent();
	private DatabaseReference DataPengikut = _firebase.getReference("DataPengikut");
	private ChildEventListener _DataPengikut_child_listener;
	private DatabaseReference DataMengikuti = _firebase.getReference("DataMengikuti");
	private ChildEventListener _DataMengikuti_child_listener;
	private DatabaseReference PushNotif = _firebase.getReference("PushNotif");
	private ChildEventListener _PushNotif_child_listener;
	private DatabaseReference Notif = _firebase.getReference("Notif");
	private ChildEventListener _Notif_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.lihatprofil);
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
		
		linearpembagi = (LinearLayout) findViewById(R.id.linearpembagi);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linearlistviewhiden = (LinearLayout) findViewById(R.id.linearlistviewhiden);
		linearlistviewsaya = (LinearLayout) findViewById(R.id.linearlistviewsaya);
		l_pushnotif = (LinearLayout) findViewById(R.id.l_pushnotif);
		linear29 = (LinearLayout) findViewById(R.id.linear29);
		linear37 = (LinearLayout) findViewById(R.id.linear37);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		listcontent_teman = (ListView) findViewById(R.id.listcontent_teman);
		linear30 = (LinearLayout) findViewById(R.id.linear30);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		back = (ImageView) findViewById(R.id.back);
		namaakun = (TextView) findViewById(R.id.namaakun);
		fotoprofilteman = (ImageView) findViewById(R.id.fotoprofilteman);
		linear31 = (LinearLayout) findViewById(R.id.linear31);
		linear32 = (LinearLayout) findViewById(R.id.linear32);
		linear33 = (LinearLayout) findViewById(R.id.linear33);
		linear34 = (LinearLayout) findViewById(R.id.linear34);
		linear35 = (LinearLayout) findViewById(R.id.linear35);
		linear36 = (LinearLayout) findViewById(R.id.linear36);
		postingan = (TextView) findViewById(R.id.postingan);
		textview2 = (TextView) findViewById(R.id.textview2);
		pengikut = (TextView) findViewById(R.id.pengikut);
		textview4 = (TextView) findViewById(R.id.textview4);
		mengikuti = (TextView) findViewById(R.id.mengikuti);
		textview6 = (TextView) findViewById(R.id.textview6);
		follow = (Button) findViewById(R.id.follow);
		dm = (Button) findViewById(R.id.dm);
		bio = (TextView) findViewById(R.id.bio);
		site = (TextView) findViewById(R.id.site);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		mailto = (Button) findViewById(R.id.mailto);
		call = (Button) findViewById(R.id.call);
		gotosite = (Button) findViewById(R.id.gotosite);
		listprofil_teman = (ListView) findViewById(R.id.listprofil_teman);
		listviewsaya = (ListView) findViewById(R.id.listviewsaya);
		list_pushnotif = (ListView) findViewById(R.id.list_pushnotif);
		LokalDataKlikTeman = getSharedPreferences("LokalDataKlikTeman", Activity.MODE_PRIVATE);
		auth = FirebaseAuth.getInstance();
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iBack.setAction(Intent.ACTION_VIEW);
				iBack.setClass(getApplicationContext(), ContentActivity.class);
				iBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(iBack);
				finish();
			}
		});
		
		follow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_TarikDataAkunTeman();
				kopidaratAkunData.child(strAkun.get((int)(posisiTeman))).removeValue();
				NewMapTeman = new HashMap<>();
				NewMapTeman.put("username", strUsername);
				NewMapTeman.put("email", strEmail);
				NewMapTeman.put("fotoprofil", strFotoProfil);
				NewMapTeman.put("bio", strBio);
				NewMapTeman.put("nama", strNama);
				NewMapTeman.put("nope", strNope);
				NewMapTeman.put("sex", strSex);
				NewMapTeman.put("situs", strSitus);
				NewMapTeman.put("mengikuti", strMengikuti);
				NewMapTeman.put("diikuti", String.valueOf((long)(Double.parseDouble(strDiikuti) + 1)));
				NewMapTeman.put("post", strPost);
				kopidaratAkunData.push().updateChildren(NewMapTeman);
				_TarikAkunSaya();
				kopidaratAkunData.child(strAkunSaya.get((int)(posisiSaya))).removeValue();
				NewMapSaya = new HashMap<>();
				NewMapSaya.put("username", strUsernameSaya);
				NewMapSaya.put("email", strEmailSaya);
				NewMapSaya.put("fotoprofil", strFotoProfilSaya);
				NewMapSaya.put("bio", strBioSaya);
				NewMapSaya.put("nama", strNamaSaya);
				NewMapSaya.put("nope", strNopeSaya);
				NewMapSaya.put("sex", strSexSaya);
				NewMapSaya.put("situs", strSitusSaya);
				NewMapSaya.put("mengikuti", String.valueOf((long)(Double.parseDouble(strMengikutiSaya) + 1)));
				NewMapSaya.put("diikuti", strDiikutiSaya);
				NewMapSaya.put("post", strPostSaya);
				kopidaratAkunData.push().updateChildren(NewMapSaya);
				_TambahDataPengikut();
				_TambahDataMengikuti();
				_CekPushNotif();
				if (readyNotifData == 1) {
					PushNotif.child(strPushNotif.get((int)(klikPushNotif))).removeValue();
					NewMapPushNotif = new HashMap<>();
					NewMapPushNotif.put("emailakun", strEmail);
					NewMapPushNotif.put("notif", "1");
					PushNotif.push().updateChildren(NewMapPushNotif);
				}
				else {
					NewMapPushNotif = new HashMap<>();
					NewMapPushNotif.put("emailakun", strEmail);
					NewMapPushNotif.put("notif", "1");
					PushNotif.push().updateChildren(NewMapPushNotif);
				}
				NewMapNotif = new HashMap<>();
				NewMapNotif.put("emailku", strEmail);
				NewMapNotif.put("emailteman", strEmailSaya);
				NewMapNotif.put("usernameteman", strUsernameSaya);
				NewMapNotif.put("fotoprofilteman", strFotoProfilSaya);
				NewMapNotif.put("keterangannotif", "Mulai mengikuti anda");
				Notif.push().updateChildren(NewMapNotif);
			}
		});
		
		dm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		mailto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iMail.setAction(Intent.ACTION_VIEW);
				iMail.setData(Uri.parse("mailto:".concat(strEmail)));
				startActivity(iMail);
			}
		});
		
		call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (strNope.equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Nomer telpon tidak tersedia");
				}
				else {
					iCall.setAction(Intent.ACTION_DIAL);
					iCall.setData(Uri.parse("tel:".concat(strNope)));
					startActivity(iCall);
				}
			}
		});
		
		gotosite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (strSitus.equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Situs tidak tersedia");
				}
				else {
					iSite.setAction(Intent.ACTION_VIEW);
					iSite.setData(Uri.parse(strSitus));
					startActivity(iSite);
				}
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
						ListMapKontenTeman = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapKontenTeman.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strKonten.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strKonten.add(dshot.getKey());
						}
						listcontent_teman.setAdapter(new Listcontent_temanAdapter(ListMapKontenTeman));
						((BaseAdapter)listcontent_teman.getAdapter()).notifyDataSetChanged();
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
						ListMapKontenTeman = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapKontenTeman.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strKonten.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strKonten.add(dshot.getKey());
						}
						listcontent_teman.setAdapter(new Listcontent_temanAdapter(ListMapKontenTeman));
						((BaseAdapter)listcontent_teman.getAdapter()).notifyDataSetChanged();
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
		
		_kopidaratAkunData_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapAkunTeman = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapAkunTeman.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strAkun.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strAkun.add(dshot.getKey());
						}
						listprofil_teman.setAdapter(new Listprofil_temanAdapter(ListMapAkunTeman));
						((BaseAdapter)listprofil_teman.getAdapter()).notifyDataSetChanged();
						Glide.with(getApplicationContext()).load(Uri.parse(strFotoProfil)).into(fotoprofilteman);
						namaakun.setText(strUsername);
						bio.setText(strBio);
						site.setText(strSitus);
						postingan.setText(strPost);
						pengikut.setText(strDiikuti);
						mengikuti.setText(strMengikuti);
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapAkunSaya = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapAkunSaya.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strAkunSaya.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strAkunSaya.add(dshot.getKey());
						}
						listviewsaya.setAdapter(new ListviewsayaAdapter(ListMapAkunSaya));
						((BaseAdapter)listviewsaya.getAdapter()).notifyDataSetChanged();
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
						ListMapAkunTeman = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapAkunTeman.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strAkun.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strAkun.add(dshot.getKey());
						}
						listprofil_teman.setAdapter(new Listprofil_temanAdapter(ListMapAkunTeman));
						((BaseAdapter)listprofil_teman.getAdapter()).notifyDataSetChanged();
						Glide.with(getApplicationContext()).load(Uri.parse(strFotoProfil)).into(fotoprofilteman);
						namaakun.setText(strUsername);
						bio.setText(strBio);
						site.setText(strSitus);
						postingan.setText(strPost);
						pengikut.setText(strDiikuti);
						mengikuti.setText(strMengikuti);
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapAkunSaya = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapAkunSaya.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						strAkunSaya.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							strAkunSaya.add(dshot.getKey());
						}
						listviewsaya.setAdapter(new ListviewsayaAdapter(ListMapAkunSaya));
						((BaseAdapter)listviewsaya.getAdapter()).notifyDataSetChanged();
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
		
		_kopiDarat_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_kopiDarat_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				SketchwareUtil.showMessage(getApplicationContext(), "Download dimulai, harap tunggu!");
			}
		};
		
		_kopiDarat_upload_success_listener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot _param1) {
				final String _downloadUrl = _param1.getDownloadUrl().toString();
				
			}
		};
		
		_kopiDarat_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				ukuranfile = _totalByteCount / 1000;
				
				final AlertDialog dialog_loading = new AlertDialog.Builder(LihatprofilActivity.this).create();
				LayoutInflater inflater = getLayoutInflater();
				
				View convertView = (View) inflater.inflate(R.layout.download_c, null);
				dialog_loading.setView(convertView);
				
				TextView size = (TextView) convertView.findViewById(R.id.size);
				
				size.setText(String.valueOf(ukuranfile).concat(" Kb"));
				
				ImageView img_c = (ImageView) convertView.findViewById(R.id.imageview1);
				
				Glide.with(getApplicationContext()).load(Uri.parse("https://cdn.dribbble.com/users/116465/screenshots/2551579/download-button.gif")).into(img_c);
				
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
								_timer.schedule(timerLoadingDialog, (int)(5000));
			}
		};
		
		_kopiDarat_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_kopiDarat_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				SketchwareUtil.showMessage(getApplicationContext(), _message);
			}
		};
		
		_DataPengikut_child_listener = new ChildEventListener() {
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
		DataPengikut.addChildEventListener(_DataPengikut_child_listener);
		
		_DataMengikuti_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				strMengikutiUser.add(_childValue.get("EmailMengikuti").toString());
				if (strMengikutiUser.contains(LokalDataKlikTeman.getString("emailteman", ""))) {
					follow.setText("Sudah Mengikuti");
					follow.setEnabled(false);
				}
				else {
					follow.setText("Ikuti");
					follow.setEnabled(true);
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				strMengikutiUser.add(_childValue.get("EmailMengikuti").toString());
				if (strMengikutiUser.contains(LokalDataKlikTeman.getString("emailteman", ""))) {
					follow.setText("Sudah Mengikuti");
					follow.setEnabled(false);
				}
				else {
					follow.setText("Ikuti");
					follow.setEnabled(true);
				}
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
		DataMengikuti.addChildEventListener(_DataMengikuti_child_listener);
		
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
						list_pushnotif.setAdapter(new List_pushnotifAdapter(ListMapPushNotif));
						((BaseAdapter)list_pushnotif.getAdapter()).notifyDataSetChanged();
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
						list_pushnotif.setAdapter(new List_pushnotifAdapter(ListMapPushNotif));
						((BaseAdapter)list_pushnotif.getAdapter()).notifyDataSetChanged();
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
	}
	private void initializeLogic() {
		_TarikDataAkunTeman();
		_TarikDataKontenTeman();
		_TarikAkunSaya();
		_CekPushNotif();
		_Round();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		iBack.setAction(Intent.ACTION_VIEW);
		iBack.setClass(getApplicationContext(), ContentActivity.class);
		iBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(iBack);
		finish();
	}
	private void _TarikDataAkunTeman () {
		kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				ListMapAkunTeman = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						ListMapAkunTeman.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				strAkun.clear();
				for (DataSnapshot dshot: _dataSnapshot.getChildren()){
					strAkun.add(dshot.getKey());
				}
				listprofil_teman.setAdapter(new Listprofil_temanAdapter(ListMapAkunTeman));
				((BaseAdapter)listprofil_teman.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	private void _TarikDataKontenTeman () {
		kopidaratData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				ListMapKontenTeman = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						ListMapKontenTeman.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				strKonten.clear();
				for (DataSnapshot dshot: _dataSnapshot.getChildren()){
					strKonten.add(dshot.getKey());
				}
				listcontent_teman.setAdapter(new Listcontent_temanAdapter(ListMapKontenTeman));
				((BaseAdapter)listcontent_teman.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	private void _Round () {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor("#FF03A9F4"));
		gd.setCornerRadius(25);
		follow.setBackground(gd);
		
		android.graphics.drawable.GradientDrawable gd1 = new android.graphics.drawable.GradientDrawable();
		gd1.setColor(Color.parseColor("#FF009688"));
		gd1.setCornerRadius(25);
		dm.setBackground(gd1);
		
	}
	
	
	private void _TarikAkunSaya () {
		kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				ListMapAkunSaya = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						ListMapAkunSaya.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				strAkunSaya.clear();
				for (DataSnapshot dshot: _dataSnapshot.getChildren()){
					strAkunSaya.add(dshot.getKey());
				}
				listviewsaya.setAdapter(new ListviewsayaAdapter(ListMapAkunSaya));
				((BaseAdapter)listviewsaya.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	private void _TambahDataPengikut () {
		MapPengikutBaru = new HashMap<>();
		MapPengikutBaru.put("EmailAkun", strEmail);
		MapPengikutBaru.put("EmailPengikut", strEmailSaya);
		MapPengikutBaru.put("FotoProfilPengikut", strFotoProfilSaya);
		MapPengikutBaru.put("UsernamePengikut", strUsernameSaya);
		DataPengikut.push().updateChildren(MapPengikutBaru);
	}
	
	
	private void _TambahDataMengikuti () {
		MapMengikutiBaru = new HashMap<>();
		MapMengikutiBaru.put("EmailAkun", strEmailSaya);
		MapMengikutiBaru.put("EmailMengikuti", strEmail);
		MapMengikutiBaru.put("FotoProfilMengikuti", strFotoProfil);
		MapMengikutiBaru.put("UsernameMengikuti", strUsername);
		DataMengikuti.push().updateChildren(MapMengikutiBaru);
	}
	
	
	private void _CekPushNotif () {
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
				list_pushnotif.setAdapter(new List_pushnotifAdapter(ListMapPushNotif));
				((BaseAdapter)list_pushnotif.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	public class Listcontent_temanAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listcontent_temanAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
				_v = _inflater.inflate(R.layout.listpunyateman_c, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final LinearLayout linear4 = (LinearLayout) _v.findViewById(R.id.linear4);
			final ImageView img = (ImageView) _v.findViewById(R.id.img);
			final LinearLayout linear6 = (LinearLayout) _v.findViewById(R.id.linear6);
			final LinearLayout linear5 = (LinearLayout) _v.findViewById(R.id.linear5);
			final TextView mension = (TextView) _v.findViewById(R.id.mension);
			final ImageView bagikan = (ImageView) _v.findViewById(R.id.bagikan);
			final ImageView unduh = (ImageView) _v.findViewById(R.id.unduh);
			
			if (_data.get((int)_position).get("email").toString().equals(LokalDataKlikTeman.getString("emailteman", ""))) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("img").toString())).into(img);
				mension.setText(_data.get((int)_position).get("mension").toString());
			}
			else {
				linear1.setVisibility(View.GONE);
			}
			bagikan.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Intent i = new Intent(android.content.Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Kopi Darat"); i.putExtra(android.content.Intent.EXTRA_TEXT, _data.get((int)_position).get("mension").toString().concat(" - ".concat(_data.get((int)_position).get("img").toString()))); startActivity(Intent.createChooser(i,"Bagikan Dengan"));
				}
			});
			unduh.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_firebase_storage.getReferenceFromUrl(_data.get((int)_position).get("img").toString()).getFile(new File("/storage/emulated/0/kopidarat/".concat(_data.get((int)_position).get("akun").toString().concat(new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(cal.getTime()).concat(".png"))))).addOnSuccessListener(_kopiDarat_download_success_listener).addOnFailureListener(_kopiDarat_failure_listener).addOnProgressListener(_kopiDarat_download_progress_listener);
				}
			});
			
			return _v;
		}
	}
	
	public class Listprofil_temanAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listprofil_temanAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
			
			if (_data.get((int)_position).get("email").toString().equals(LokalDataKlikTeman.getString("emailteman", ""))) {
				posisiTeman = _position;
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
				strEmail = _data.get((int)_position).get("email").toString();
				strFotoProfil = _data.get((int)_position).get("fotoprofil").toString();
				strUsername = _data.get((int)_position).get("username").toString();
				strNama = _data.get((int)_position).get("nama").toString();
				strBio = _data.get((int)_position).get("bio").toString();
				strNope = _data.get((int)_position).get("nope").toString();
				strSex = _data.get((int)_position).get("sex").toString();
				strSitus = _data.get((int)_position).get("situs").toString();
				strMengikuti = _data.get((int)_position).get("mengikuti").toString();
				strDiikuti = _data.get((int)_position).get("diikuti").toString();
				strPost = _data.get((int)_position).get("post").toString();
			}
			else {
				linear1.setVisibility(View.GONE);
			}
			
			return _v;
		}
	}
	
	public class ListviewsayaAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public ListviewsayaAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
				posisiSaya = _position;
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
				strEmailSaya = _data.get((int)_position).get("email").toString();
				strFotoProfilSaya = _data.get((int)_position).get("fotoprofil").toString();
				strUsernameSaya = _data.get((int)_position).get("username").toString();
				strNamaSaya = _data.get((int)_position).get("nama").toString();
				strBioSaya = _data.get((int)_position).get("bio").toString();
				strNopeSaya = _data.get((int)_position).get("nope").toString();
				strSexSaya = _data.get((int)_position).get("sex").toString();
				strSitusSaya = _data.get((int)_position).get("situs").toString();
				strMengikutiSaya = _data.get((int)_position).get("mengikuti").toString();
				strDiikutiSaya = _data.get((int)_position).get("diikuti").toString();
				strPostSaya = _data.get((int)_position).get("post").toString();
			}
			else {
				linear1.setVisibility(View.GONE);
			}
			
			return _v;
		}
	}
	
	public class List_pushnotifAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public List_pushnotifAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
			
			if (_data.get((int)_position).get("emailakun").toString().equals(LokalDataKlikTeman.getString("emailteman", ""))) {
				klikPushNotif = _position;
				emailakun.setText(_data.get((int)_position).get("emailakun").toString());
				notif.setText(_data.get((int)_position).get("notif").toString());
				readyNotifData = 1;
			}
			else {
				linear1.setVisibility(View.GONE);
				readyNotifData = 0;
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
