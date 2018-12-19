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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import java.io.File;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class AkunActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private HashMap<String, Object> item = new HashMap<>();
	
	private ArrayList<String> str = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ListMapContent = new ArrayList<>();
	
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear8;
	private LinearLayout pembagilistdangrid;
	private ImageView imageview1;
	private LinearLayout linear7;
	private TextView namaakun;
	private Button editprofil;
	private LinearLayout linear4;
	private ImageView list_layout;
	private ImageView grid_layout;
	private ImageView list_teman;
	private ImageView bookmark;
	private LinearLayout linear_listview2;
	private LinearLayout linear1;
	private LinearLayout linear_listviewteman;
	private ListView listview2;
	private ListView listviewteman;
	
	private DatabaseReference kopidaratData = _firebase.getReference("kopidaratData");
	private ChildEventListener _kopidaratData_child_listener;
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private SharedPreferences lokaldatagrid;
	private Intent iContent = new Intent();
	private TimerTask runCheck;
	private SharedPreferences DataLokalHidenProfil;
	private StorageReference KopiDarat = _firebase_storage.getReference("KopiDarat");
	private OnSuccessListener<UploadTask.TaskSnapshot> _KopiDarat_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _KopiDarat_download_success_listener;
	private OnSuccessListener _KopiDarat_delete_success_listener;
	private OnProgressListener _KopiDarat_upload_progress_listener;
	private OnProgressListener _KopiDarat_download_progress_listener;
	private OnFailureListener _KopiDarat_failure_listener;
	private Intent iEditProfil = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.akun);
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
		
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		pembagilistdangrid = (LinearLayout) findViewById(R.id.pembagilistdangrid);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		namaakun = (TextView) findViewById(R.id.namaakun);
		editprofil = (Button) findViewById(R.id.editprofil);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		list_layout = (ImageView) findViewById(R.id.list_layout);
		grid_layout = (ImageView) findViewById(R.id.grid_layout);
		list_teman = (ImageView) findViewById(R.id.list_teman);
		bookmark = (ImageView) findViewById(R.id.bookmark);
		linear_listview2 = (LinearLayout) findViewById(R.id.linear_listview2);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear_listviewteman = (LinearLayout) findViewById(R.id.linear_listviewteman);
		listview2 = (ListView) findViewById(R.id.listview2);
		listviewteman = (ListView) findViewById(R.id.listviewteman);
		auth = FirebaseAuth.getInstance();
		lokaldatagrid = getSharedPreferences("lokaldatagrid", Activity.MODE_PRIVATE);
		DataLokalHidenProfil = getSharedPreferences("DataLokalHidenProfil", Activity.MODE_PRIVATE);
		
		editprofil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				iEditProfil.setAction(Intent.ACTION_VIEW);
				iEditProfil.setClass(getApplicationContext(), PengaturanakunActivity.class);
				iEditProfil.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(iEditProfil);
				finish();
			}
		});
		
		list_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linear_listview2.setVisibility(View.VISIBLE);
				linear1.setVisibility(View.GONE);
				linear_listviewteman.setVisibility(View.GONE);
			}
		});
		
		grid_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				linear_listview2.setVisibility(View.GONE);
				linear1.setVisibility(View.VISIBLE);
				linear_listviewteman.setVisibility(View.GONE);
			}
		});
		
		_kopidaratData_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				lokaldatagrid.edit().putString("gridview", new Gson().toJson(_childValue)).commit();
				item = new Gson().fromJson(lokaldatagrid.getString("gridview", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
				listmap.add(item);
				kopidaratData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapContent = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapContent.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							str.add(dshot.getKey());
						}
						listview2.setAdapter(new Listview2Adapter(ListMapContent));
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
				kopidaratData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						ListMapContent = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								ListMapContent.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						str.clear();
						for (DataSnapshot dshot: _dataSnapshot.getChildren()){
							str.add(dshot.getKey());
						}
						listview2.setAdapter(new Listview2Adapter(ListMapContent));
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
		kopidaratData.addChildEventListener(_kopidaratData_child_listener);
		
		_KopiDarat_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
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
				SketchwareUtil.showMessage(getApplicationContext(), "Sukses Dihapus");
			}
		};
		
		_KopiDarat_failure_listener = new OnFailureListener() {
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
		namaakun.setText(DataLokalHidenProfil.getString("username", ""));
		Glide.with(getApplicationContext()).load(Uri.parse(DataLokalHidenProfil.getString("fotoprofil", ""))).into(imageview1);
		
		GridView grid = new GridView(this);
		grid.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT));
		grid.setBackgroundColor(Color.WHITE);
		grid.setNumColumns(2);
		grid.setColumnWidth(GridView.AUTO_FIT);
		grid.setVerticalSpacing(5);
		grid.setHorizontalSpacing(5);
		grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		
		grid.setAdapter(new Listview1Adapter(listmap));
		linear1.addView(grid);
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view, int position, long id) {
				showMessage(Integer.toString(position));
			}});
		
		
	}
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
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
		public HashMap<String, Object> getItem(int _i) {
			
			return _data.get(_i);
			
		}
		
		@Override
		public long getItemId(int _i) {
			
			return _i;
			
		}
		
		 @Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View _v = _view;
			
			if (_v == null) {
				
				_v = _inflater.inflate(R.layout.gridview_c, null);
				
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			
			final ImageView imageview1 = (ImageView) _v.findViewById(R.id.imageview1);
			
			Glide.with(getApplicationContext()).load(Uri.parse(listmap.get((int)_position).get("img").toString())).into(imageview1);
			
			return _v;
			
		}
		
	}
	
	public void nothing() {
		
		
		kopidaratData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				ListMapContent = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						ListMapContent.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				str.clear();
				for (DataSnapshot dshot: _dataSnapshot.getChildren()){
					str.add(dshot.getKey());
				}
				listview2.setAdapter(new Listview2Adapter(ListMapContent));
				((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
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
		iContent.setAction(Intent.ACTION_VIEW);
		iContent.setClass(getApplicationContext(), ContentActivity.class);
		iContent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(iContent);
		finish();
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
				_v = _inflater.inflate(R.layout.listakunku_c, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final LinearLayout linear4 = (LinearLayout) _v.findViewById(R.id.linear4);
			final LinearLayout linear6 = (LinearLayout) _v.findViewById(R.id.linear6);
			final ImageView img = (ImageView) _v.findViewById(R.id.img);
			final LinearLayout linear5 = (LinearLayout) _v.findViewById(R.id.linear5);
			final ImageView bagikan = (ImageView) _v.findViewById(R.id.bagikan);
			final ImageView hapus = (ImageView) _v.findViewById(R.id.hapus);
			final TextView mension = (TextView) _v.findViewById(R.id.mension);
			
			if (_data.get((int)_position).get("email").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("img").toString())).into(img);
				mension.setText(_data.get((int)_position).get("mension").toString());
				mension.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/acme.ttf"), 1);
				bagikan.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						 Intent i = new Intent(android.content.Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Kopi Darat"); i.putExtra(android.content.Intent.EXTRA_TEXT, _data.get((int)_position).get("mension").toString().concat(" - ".concat(_data.get((int)_position).get("img").toString()))); startActivity(Intent.createChooser(i,"Bagikan Dengan"));
					}
				});
				hapus.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						final AlertDialog dialog = new AlertDialog.Builder(AkunActivity.this).create();
						LayoutInflater inflater = getLayoutInflater();
						
						View convertView = (View) inflater.inflate(R.layout.hapus_c, null);
						dialog.setView(convertView);
						
						ImageView img_c = (ImageView) convertView.findViewById(R.id.imageview1);
						
						Glide.with(getApplicationContext()).load(Uri.parse("https://cdn.dribbble.com/users/358447/screenshots/3066984/delete-animation.gif")).into(img_c);
						
						Button btn1 = (Button) convertView.findViewById(R.id.button1);
						
						btn1.setOnClickListener(new View.OnClickListener(){
							    public void onClick(View v){
								
								_firebase_storage.getReferenceFromUrl(_data.get((int)_position).get("img").toString()).delete().addOnSuccessListener(_KopiDarat_delete_success_listener).addOnFailureListener(_KopiDarat_failure_listener);
													kopidaratData.child(str.get((int)(_position))).removeValue();
								dialog.dismiss();
								       
								    }
						});
						
						
						Button btn2 = (Button) convertView.findViewById(R.id.button2);
						
						btn2.setOnClickListener(new View.OnClickListener(){
							    public void onClick(View v){
								dialog.dismiss();
								       
								    }
						});
						
						dialog.show();
					}
				});
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
