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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.graphics.Typeface;
import com.google.gson.Gson;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class AuthActivity extends AppCompatActivity {
	
	public final int REQ_CD_PICK = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private double klikberalihregister = 0;
	private double posisiPickFile = 0;
	private String urlFotoProfil = "";
	private HashMap<String, Object> NewMap = new HashMap<>();
	private double klikhideshowsandi = 0;
	private double klikshowhidesandi = 0;
	
	private ArrayList<String> filePick = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ListMapTarikData = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout loginpage;
	private LinearLayout registerpage;
	private LinearLayout linear_listviewakun;
	private TextView judul;
	private LinearLayout linear22;
	private LinearLayout linear24;
	private LinearLayout linear26;
	private LinearLayout linear31;
	private LinearLayout linear32;
	private LinearLayout linear29;
	private ImageView imageview8;
	private EditText emailmasuk;
	private LinearLayout linear30;
	private ImageView imageview9;
	private EditText sandimasuk;
	private ImageView hideshowsandi;
	private Button loginbutton;
	private TextView textview5;
	private TextView beralihkedaftar;
	private LinearLayout linear7;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear12;
	private LinearLayout linear13;
	private LinearLayout linear15;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear16;
	private LinearLayout linear8;
	private ImageView imageview2;
	private EditText username;
	private LinearLayout linear11;
	private ImageView imageview3;
	private EditText emailregister;
	private LinearLayout linear14;
	private ImageView imageview4;
	private EditText sandiregister;
	private ImageView showhidesandi;
	private LinearLayout linear_ambilfile;
	private LinearLayout ambilfile;
	private LinearLayout linearprogressupload;
	private ImageView imageviewprofil;
	private TextView textview1;
	private ProgressBar progressbar1;
	private Button registerbutton;
	private ListView listview1;
	
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private Intent iContent = new Intent();
	private Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference kopidaratFotoProfil = _firebase_storage.getReference("kopidaratFotoProfil");
	private OnSuccessListener<UploadTask.TaskSnapshot> _kopidaratFotoProfil_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _kopidaratFotoProfil_download_success_listener;
	private OnSuccessListener _kopidaratFotoProfil_delete_success_listener;
	private OnProgressListener _kopidaratFotoProfil_upload_progress_listener;
	private OnProgressListener _kopidaratFotoProfil_download_progress_listener;
	private OnFailureListener _kopidaratFotoProfil_failure_listener;
	private DatabaseReference kopidaratAkunData = _firebase.getReference("kopidaratAkunData");
	private ChildEventListener _kopidaratAkunData_child_listener;
	private SharedPreferences DataLokalListAkun;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.auth);
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
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		loginpage = (LinearLayout) findViewById(R.id.loginpage);
		registerpage = (LinearLayout) findViewById(R.id.registerpage);
		linear_listviewakun = (LinearLayout) findViewById(R.id.linear_listviewakun);
		judul = (TextView) findViewById(R.id.judul);
		linear22 = (LinearLayout) findViewById(R.id.linear22);
		linear24 = (LinearLayout) findViewById(R.id.linear24);
		linear26 = (LinearLayout) findViewById(R.id.linear26);
		linear31 = (LinearLayout) findViewById(R.id.linear31);
		linear32 = (LinearLayout) findViewById(R.id.linear32);
		linear29 = (LinearLayout) findViewById(R.id.linear29);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		emailmasuk = (EditText) findViewById(R.id.emailmasuk);
		linear30 = (LinearLayout) findViewById(R.id.linear30);
		imageview9 = (ImageView) findViewById(R.id.imageview9);
		sandimasuk = (EditText) findViewById(R.id.sandimasuk);
		hideshowsandi = (ImageView) findViewById(R.id.hideshowsandi);
		loginbutton = (Button) findViewById(R.id.loginbutton);
		textview5 = (TextView) findViewById(R.id.textview5);
		beralihkedaftar = (TextView) findViewById(R.id.beralihkedaftar);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		username = (EditText) findViewById(R.id.username);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		emailregister = (EditText) findViewById(R.id.emailregister);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		sandiregister = (EditText) findViewById(R.id.sandiregister);
		showhidesandi = (ImageView) findViewById(R.id.showhidesandi);
		linear_ambilfile = (LinearLayout) findViewById(R.id.linear_ambilfile);
		ambilfile = (LinearLayout) findViewById(R.id.ambilfile);
		linearprogressupload = (LinearLayout) findViewById(R.id.linearprogressupload);
		imageviewprofil = (ImageView) findViewById(R.id.imageviewprofil);
		textview1 = (TextView) findViewById(R.id.textview1);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		registerbutton = (Button) findViewById(R.id.registerbutton);
		listview1 = (ListView) findViewById(R.id.listview1);
		auth = FirebaseAuth.getInstance();
		pick.setType("image/*");
		pick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		DataLokalListAkun = getSharedPreferences("DataLokalListAkun", Activity.MODE_PRIVATE);
		
		hideshowsandi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (klikhideshowsandi == 0) {
					hideshowsandi.setImageResource(R.drawable.ic_visibility_off_black);
					sandimasuk.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
					klikhideshowsandi = 1;
				}
				else {
					if (klikhideshowsandi == 1) {
						hideshowsandi.setImageResource(R.drawable.ic_visibility_black);
						sandimasuk.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
						klikhideshowsandi = 0;
					}
				}
			}
		});
		
		loginbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (emailmasuk.getText().toString().equals("") || sandimasuk.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Tidak Boleh Kosong!");
				}
				else {
					auth.signInWithEmailAndPassword(emailmasuk.getText().toString(), sandimasuk.getText().toString()).addOnCompleteListener(AuthActivity.this, _auth_sign_in_listener);
				}
			}
		});
		
		beralihkedaftar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				loginpage.setVisibility(View.GONE);
				registerpage.setVisibility(View.VISIBLE);
				klikberalihregister = 1;
			}
		});
		
		showhidesandi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (klikshowhidesandi == 0) {
					showhidesandi.setImageResource(R.drawable.ic_visibility_off_black);
					sandiregister.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
					klikshowhidesandi = 1;
				}
				else {
					if (klikshowhidesandi == 1) {
						showhidesandi.setImageResource(R.drawable.ic_visibility_black);
						sandiregister.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
						klikshowhidesandi = 0;
					}
				}
			}
		});
		
		linear_ambilfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (DataLokalListAkun.getString("akun", "").contains(username.getText().toString())) {
					SketchwareUtil.showMessage(getApplicationContext(), "Nama Pengguna Sudah Digunakan!");
				}
				else {
					posisiPickFile = 0;
					startActivityForResult(pick, REQ_CD_PICK);
				}
			}
		});
		
		ambilfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (DataLokalListAkun.getString("akun", "").contains(username.getText().toString())) {
					SketchwareUtil.showMessage(getApplicationContext(), "Nama Pengguna Sudah Digunakan!");
				}
				else {
					posisiPickFile = 0;
					startActivityForResult(pick, REQ_CD_PICK);
				}
			}
		});
		
		registerbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (username.getText().toString().equals("") || (emailregister.getText().toString().equals("") || (sandiregister.getText().toString().equals("") || urlFotoProfil.equals("")))) {
					SketchwareUtil.showMessage(getApplicationContext(), "Tidak Boleh Kosong!");
				}
				else {
					auth.createUserWithEmailAndPassword(emailregister.getText().toString(), sandiregister.getText().toString()).addOnCompleteListener(AuthActivity.this, _auth_create_user_listener);
				}
			}
		});
		
		_kopidaratFotoProfil_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				linearprogressupload.setVisibility(View.VISIBLE);
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
				urlFotoProfil = _downloadUrl;
				linearprogressupload.setVisibility(View.INVISIBLE);
				registerbutton.setVisibility(View.VISIBLE);
				SketchwareUtil.showMessage(getApplicationContext(), "Foto Profil Terupload");
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
				
			}
		};
		
		_kopidaratFotoProfil_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				linearprogressupload.setVisibility(View.INVISIBLE);
				SketchwareUtil.showMessage(getApplicationContext(), _message);
			}
		};
		
		_kopidaratAkunData_child_listener = new ChildEventListener() {
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
		kopidaratAkunData.addChildEventListener(_kopidaratAkunData_child_listener);
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					NewMap = new HashMap<>();
					NewMap.put("username", username.getText().toString());
					NewMap.put("email", emailregister.getText().toString());
					NewMap.put("fotoprofil", urlFotoProfil);
					NewMap.put("bio", "");
					NewMap.put("nama", "");
					NewMap.put("nope", "");
					NewMap.put("sex", "");
					NewMap.put("situs", "");
					NewMap.put("mengikuti", "0");
					NewMap.put("diikuti", "0");
					NewMap.put("post", "0");
					kopidaratAkunData.push().updateChildren(NewMap);
					klikberalihregister = 0;
					loginpage.setVisibility(View.VISIBLE);
					registerpage.setVisibility(View.GONE);
					SketchwareUtil.showMessage(getApplicationContext(), "Pendaftaran Berhasil");
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					iContent.setAction(Intent.ACTION_VIEW);
					iContent.setClass(getApplicationContext(), ContentActivity.class);
					iContent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(iContent);
					finish();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
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
		_FONT();
		_BUATFOLDER();
		_TARIKDATAAKUN();
		linearprogressupload.setVisibility(View.INVISIBLE);
		registerbutton.setVisibility(View.INVISIBLE);
		klikberalihregister = 0;
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			iContent.setAction(Intent.ACTION_VIEW);
			iContent.setClass(getApplicationContext(), ContentActivity.class);
			iContent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(iContent);
			finish();
		}
		else {
			
			final AlertDialog dialog = new AlertDialog.Builder(AuthActivity.this).create();
			LayoutInflater inflater = getLayoutInflater();
			
			View convertView = (View) inflater.inflate(R.layout.wellcome_c, null);
			dialog.setView(convertView);
			
			
			Button btn1 = (Button) convertView.findViewById(R.id.button1);
			
			btn1.setOnClickListener(new View.OnClickListener(){
				    public void onClick(View v){
					dialog.dismiss();
					       
					    }
			});
			
			dialog.show();
		}
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
				FileUtil.cropBitmapFileFromCenter(_filePath.get((int)(posisiPickFile)), "/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg")), 512, 512);
				FileUtil.resizeBitmapFileToCircle("/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg")), "/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg")));
				kopidaratFotoProfil.child(username.getText().toString().concat(".jpg")).putFile(Uri.fromFile(new File("/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg"))))).addOnSuccessListener(_kopidaratFotoProfil_upload_success_listener).addOnFailureListener(_kopidaratFotoProfil_failure_listener).addOnProgressListener(_kopidaratFotoProfil_upload_progress_listener);
				imageviewprofil.setImageBitmap(FileUtil.decodeSampleBitmapFromPath("/storage/emulated/0/kopidarat/".concat(username.getText().toString().concat(".jpg")), 1024, 1024));
			}
			else {
				SketchwareUtil.showMessage(getApplicationContext(), "Dibatalkan");
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (klikberalihregister == 1) {
			loginpage.setVisibility(View.VISIBLE);
			registerpage.setVisibility(View.GONE);
			klikberalihregister = 0;
		}
		else {
			finish();
		}
	}
	private void _FONT () {
		judul.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/magenta.ttf"), 0);
		emailmasuk.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		sandimasuk.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		loginbutton.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		beralihkedaftar.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		username.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		emailregister.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		sandiregister.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
		registerbutton.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 0);
	}
	
	
	private void _BUATFOLDER () {
		if (FileUtil.isExistFile("/storage/emulated/0/kopidarat")) {
			
		}
		else {
			FileUtil.makeDir("/storage/emulated/0/kopidarat");
		}
	}
	
	
	private void _TARIKDATAAKUN () {
		kopidaratAkunData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				ListMapTarikData = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						ListMapTarikData.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				listview1.setAdapter(new Listview1Adapter(ListMapTarikData));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
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
				_v = _inflater.inflate(R.layout.custom_list_akun, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
			
			textview1.setText(_data.get((int)_position).get("username").toString());
			DataLokalListAkun.edit().putString("akun", new Gson().toJson(_data)).commit();
			
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
