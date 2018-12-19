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
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.BaseAdapter;
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
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.content.ClipData;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import java.io.File;
import android.view.View;
import android.widget.AdapterView;
import com.bumptech.glide.Glide;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class PengaturanakunActivity extends AppCompatActivity {
	
	public final int REQ_CD_PICK = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private double posisi = 0;
	private double posisiPickFile = 0;
	private String strLinkIMG = "";
	private String strSEX = "";
	private HashMap<String, Object> NewMap = new HashMap<>();
	private String strMengikuti = "";
	private String strDiikuti = "";
	private String strPost = "";
	
	private ArrayList<HashMap<String, Object>> ListMapHidenProfil = new ArrayList<>();
	private ArrayList<String> str = new ArrayList<>();
	private ArrayList<String> SpinnerSTR = new ArrayList<>();
	
	private LinearLayout linearpembagi;
	private LinearLayout linear1;
	private LinearLayout linearlistviewakun;
	private LinearLayout linear2;
	private ScrollView vscroll2;
	private ImageView batal;
	private LinearLayout linear3;
	private ImageView update;
	private TextView textview2;
	private LinearLayout linear8;
	private LinearLayout linear4;
	private TextView textview3;
	private LinearLayout linear6;
	private TextView textview4;
	private LinearLayout linear7;
	private TextView textview5;
	private LinearLayout linear9;
	private TextView textview6;
	private LinearLayout linear10;
	private TextView textview12;
	private TextView textview7;
	private LinearLayout linear11;
	private TextView textview8;
	private LinearLayout linear12;
	private TextView textview9;
	private LinearLayout linear13;
	private LinearLayout linearuploadfoto;
	private LinearLayout linearloading_upload;
	private ImageView fotoprofil;
	private TextView gantifoto;
	private ImageView loading_upload;
	private LinearLayout linear14;
	private TextView textketeranganupload;
	private TextView persenprogress;
	private EditText nama;
	private EditText username;
	private EditText situs;
	private EditText bio;
	private TextView email;
	private EditText nope;
	private Spinner spinnerjeniskelamin;
	private ListView listview1;
	
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private DatabaseReference kopidaratAkunData = _firebase.getReference("kopidaratAkunData");
	private ChildEventListener _kopidaratAkunData_child_listener;
	private SharedPreferences DataLokalHidenProfil;
	private Intent iAkun = new Intent();
	private Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference kopidaratFotoProfil = _firebase_storage.getReference("kopidaratFotoProfil");
	private OnSuccessListener<UploadTask.TaskSnapshot> _kopidaratFotoProfil_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _kopidaratFotoProfil_download_success_listener;
	private OnSuccessListener _kopidaratFotoProfil_delete_success_listener;
	private OnProgressListener _kopidaratFotoProfil_upload_progress_listener;
	private OnProgressListener _kopidaratFotoProfil_download_progress_listener;
	private OnFailureListener _kopidaratFotoProfil_failure_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.pengaturanakun);
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
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linearlistviewakun = (LinearLayout) findViewById(R.id.linearlistviewakun);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		vscroll2 = (ScrollView) findViewById(R.id.vscroll2);
		batal = (ImageView) findViewById(R.id.batal);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		update = (ImageView) findViewById(R.id.update);
		textview2 = (TextView) findViewById(R.id.textview2);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		textview3 = (TextView) findViewById(R.id.textview3);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		textview4 = (TextView) findViewById(R.id.textview4);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		textview5 = (TextView) findViewById(R.id.textview5);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		textview6 = (TextView) findViewById(R.id.textview6);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		textview12 = (TextView) findViewById(R.id.textview12);
		textview7 = (TextView) findViewById(R.id.textview7);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		textview8 = (TextView) findViewById(R.id.textview8);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		textview9 = (TextView) findViewById(R.id.textview9);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linearuploadfoto = (LinearLayout) findViewById(R.id.linearuploadfoto);
		linearloading_upload = (LinearLayout) findViewById(R.id.linearloading_upload);
		fotoprofil = (ImageView) findViewById(R.id.fotoprofil);
		gantifoto = (TextView) findViewById(R.id.gantifoto);
		loading_upload = (ImageView) findViewById(R.id.loading_upload);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		textketeranganupload = (TextView) findViewById(R.id.textketeranganupload);
		persenprogress = (TextView) findViewById(R.id.persenprogress);
		nama = (EditText) findViewById(R.id.nama);
		username = (EditText) findViewById(R.id.username);
		situs = (EditText) findViewById(R.id.situs);
		bio = (EditText) findViewById(R.id.bio);
		email = (TextView) findViewById(R.id.email);
		nope = (EditText) findViewById(R.id.nope);
		spinnerjeniskelamin = (Spinner) findViewById(R.id.spinnerjeniskelamin);
		listview1 = (ListView) findViewById(R.id.listview1);
		auth = FirebaseAuth.getInstance();
		DataLokalHidenProfil = getSharedPreferences("DataLokalHidenProfil", Activity.MODE_PRIVATE);
		pick.setType("image/*");
		pick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		batal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iAkun.setAction(Intent.ACTION_VIEW);
				iAkun.setClass(getApplicationContext(), AkunActivity.class);
				iAkun.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(iAkun);
				finish();
			}
		});
		
		update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
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
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							str.add(dshot.getKey());
						}
						listview1.setAdapter(new Listview1Adapter(ListMapHidenProfil));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				kopidaratAkunData.child(str.get((int)(posisi))).removeValue();
				DataLokalHidenProfil.edit().putString("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()).commit();
				DataLokalHidenProfil.edit().putString("fotoprofil", strLinkIMG).commit();
				DataLokalHidenProfil.edit().putString("username", username.getText().toString()).commit();
				DataLokalHidenProfil.edit().putString("nama", nama.getText().toString()).commit();
				DataLokalHidenProfil.edit().putString("bio", bio.getText().toString()).commit();
				DataLokalHidenProfil.edit().putString("nope", nope.getText().toString()).commit();
				DataLokalHidenProfil.edit().putString("sex", strSEX).commit();
				DataLokalHidenProfil.edit().putString("situs", situs.getText().toString()).commit();
				DataLokalHidenProfil.edit().putString("mengikuti", strMengikuti).commit();
				DataLokalHidenProfil.edit().putString("diikuti", strDiikuti).commit();
				DataLokalHidenProfil.edit().putString("post", strPost).commit();
				NewMap = new HashMap<>();
				NewMap.put("bio", bio.getText().toString());
				NewMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
				NewMap.put("fotoprofil", strLinkIMG);
				NewMap.put("nama", nama.getText().toString());
				NewMap.put("nope", nope.getText().toString());
				NewMap.put("sex", strSEX);
				NewMap.put("situs", situs.getText().toString());
				NewMap.put("username", username.getText().toString());
				NewMap.put("mengikuti", strMengikuti);
				NewMap.put("diikuti", strDiikuti);
				NewMap.put("post", strPost);
				kopidaratAkunData.push().updateChildren(NewMap);
				iAkun.setAction(Intent.ACTION_VIEW);
				iAkun.setClass(getApplicationContext(), AkunActivity.class);
				iAkun.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(iAkun);
				finish();
			}
		});
		
		gantifoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				posisiPickFile = 0;
				startActivityForResult(pick, REQ_CD_PICK);
			}
		});
		
		spinnerjeniskelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 1) {
					strSEX = "Laki-laki";
				}
				else {
					if (_position == 2) {
						strSEX = "Perempuan";
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Anda harus menentukan jenis kelamin");
					}
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
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
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							str.add(dshot.getKey());
						}
						listview1.setAdapter(new Listview1Adapter(ListMapHidenProfil));
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
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							str.add(dshot.getKey());
						}
						listview1.setAdapter(new Listview1Adapter(ListMapHidenProfil));
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
		kopidaratAkunData.addChildEventListener(_kopidaratAkunData_child_listener);
		
		_kopidaratFotoProfil_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				persenprogress.setText(String.valueOf((long)(_progressValue)).concat(" ℅"));
				linearuploadfoto.setVisibility(View.GONE);
			}
		};
		
		_kopidaratFotoProfil_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_kopidaratFotoProfil_upload_success_listener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot _param1) {
				final String _downloadUrl = _param1.getDownloadUrl().toString();
				linearuploadfoto.setVisibility(View.VISIBLE);
				strLinkIMG = _downloadUrl;
			}
		};
		
		_kopidaratFotoProfil_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_kopidaratFotoProfil_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				SketchwareUtil.showMessage(getApplicationContext(), "Foto lama dihapus");
			}
		};
		
		_kopidaratFotoProfil_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				SketchwareUtil.showMessage(getApplicationContext(), _message);
			}
		};
		
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
				str.clear();
				for (DataSnapshot dshot: _dataSnapshot.getChildren()){
					str.add(dshot.getKey());
				}
				listview1.setAdapter(new Listview1Adapter(ListMapHidenProfil));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		Glide.with(getApplicationContext()).load(Uri.parse("https://cdn.dribbble.com/users/419257/screenshots/1724076/scanningwoohoo.gif")).into(loading_upload);
		_TARIKMANG();
		_SPINNER();
		_FONT();
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
				_firebase_storage.getReferenceFromUrl(DataLokalHidenProfil.getString("fotoprofil", "")).delete().addOnSuccessListener(_kopidaratFotoProfil_delete_success_listener).addOnFailureListener(_kopidaratFotoProfil_failure_listener);
				FileUtil.cropBitmapFileFromCenter(_filePath.get((int)(posisiPickFile)), "/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg")), 512, 512);
				FileUtil.resizeBitmapFileToCircle("/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg")), "/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg")));
				kopidaratFotoProfil.child(username.getText().toString().concat(".jpg")).putFile(Uri.fromFile(new File("/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg"))))).addOnSuccessListener(_kopidaratFotoProfil_upload_success_listener).addOnFailureListener(_kopidaratFotoProfil_failure_listener).addOnProgressListener(_kopidaratFotoProfil_upload_progress_listener);
				fotoprofil.setImageBitmap(FileUtil.decodeSampleBitmapFromPath("/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg")), 1024, 1024));
			}
			else {
				SketchwareUtil.showMessage(getApplicationContext(), "Upload dibatalkan");
			}
			break;
			default:
			break;
		}
	}
	
	private void _TARIKMANG () {
		Glide.with(getApplicationContext()).load(Uri.parse(DataLokalHidenProfil.getString("fotoprofil", ""))).into(fotoprofil);
		strLinkIMG = DataLokalHidenProfil.getString("fotoprofil", "");
		nama.setText(DataLokalHidenProfil.getString("nama", ""));
		username.setText(DataLokalHidenProfil.getString("username", ""));
		situs.setText(DataLokalHidenProfil.getString("situs", ""));
		bio.setText(DataLokalHidenProfil.getString("bio", ""));
		email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
		nope.setText(DataLokalHidenProfil.getString("nope", ""));
		if (DataLokalHidenProfil.getString("sex", "").equals("Laki-laki")) {
			spinnerjeniskelamin.setSelection((int)(1));
		}
		else {
			if (DataLokalHidenProfil.getString("sex", "").equals("Perempuan")) {
				spinnerjeniskelamin.setSelection((int)(2));
			}
			else {
				spinnerjeniskelamin.setSelection((int)(0));
			}
		}
	}
	
	
	private void _SPINNER () {
		SpinnerSTR.add("Belum diterapkan");
		SpinnerSTR.add("Laki-laki");
		SpinnerSTR.add("Perempuan");
		spinnerjeniskelamin.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerSTR));
		((ArrayAdapter)spinnerjeniskelamin.getAdapter()).notifyDataSetChanged();
	}
	
	
	private void _FONT () {
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview12.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview7.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview8.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview9.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		gantifoto.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textketeranganupload.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		nama.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		username.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		situs.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		bio.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		email.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		nope.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		persenprogress.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
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
				posisi = _position;
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
