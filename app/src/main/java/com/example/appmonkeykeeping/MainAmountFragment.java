package com.example.appmonkeykeeping;

import static com.example.appmonkeykeeping.annotation.AnnotationCode.ARRAY_MONEY_SAVING;
import static com.example.appmonkeykeeping.annotation.AnnotationCode.DATE_FORMAT;
import static com.example.appmonkeykeeping.annotation.AnnotationCode.MONKEY_TASK;
import static com.example.appmonkeykeeping.annotation.AnnotationCode.REQUEST_PERMISSION_CODE;
import static com.example.appmonkeykeeping.annotation.AnnotationCode.SHARED_TABLE;
import static com.example.appmonkeykeeping.annotation.AnnotationCode.requestChoose;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appmonkeykeeping.annotation.AnnotationCode;
import com.example.appmonkeykeeping.center.DatabaseSystem;
import com.example.appmonkeykeeping.custom.NumberTextWatcherForThousand;
import com.example.appmonkeykeeping.databinding.FragmentMainAmountBinding;
import com.example.appmonkeykeeping.dialog.DialogLocationSuggest;
import com.example.appmonkeykeeping.model.Money;
import com.example.appmonkeykeeping.remote.LocationInserting;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainAmountFragment extends Fragment implements LocationInserting {
    private SharedPreferences shareSavingMoney;
    private FragmentMainAmountBinding binding;
    private boolean isSaved = false;
    private boolean period;
    private Uri imageUri;
    private String targetId;
    private DatabaseSystem databaseSystem;
    public MainAmountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseSystem = DatabaseSystem.getInstance();
        databaseSystem.realmInitialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainAmountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            MainAmountFragmentArgs args = MainAmountFragmentArgs.fromBundle(getArguments());
            targetId = args.getMessageFindingId();
            if(!targetId.equals("undefined")){
                isSaved = true;
            }
            Log.e(getClass().getName(),"The item was saved : "+ isSaved+" ("+ targetId+")");
        }
        init();
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvCateCheck.getText().equals("")){
                    binding.tvCateCheck.setText(requestChoose);
                    return;
                }
                if(binding.tvCateCheck.getText().equals(requestChoose)){
                    Toast.makeText(getActivity(),"You should not hide something for me",Toast.LENGTH_LONG);
                    return;
                }
                if (binding.edtLocation.getText().toString().equals("")){
                    binding.edtLocation.setError("This is required");
                    return;
                }
                if (binding.edtMainMoney.getText().toString().equals("")){
                    binding.edtMainMoney.setError("This is required");
                    return;
                }
                period = binding.boxPeriod.isChecked();
                Money money = new Money();
                money.setTag(AnnotationCode.typesOfRecording[0]);
                money.setDate(binding.tvDate.getText().toString());
                money.setActualCost(Long.parseLong(binding.edtMainMoney.getText().toString().replaceAll(",","").trim()));
                money.setCategory(binding.tvCateCheck.getText().toString().trim());
                money.setDetail(binding.edtDetail.getText().toString().trim().equals("")?"empty":binding.edtDetail.getText().toString().trim());
                money.setLocation(binding.edtLocation.getText().toString().trim());
                money.setUsePeriod(period);
                if(isSaved){
                    money.setId(Long.parseLong(targetId));
                    databaseSystem.updateNoteMoney(money);
                }else{
                    money.setId(databaseSystem.getCategoryMaxId());
                    databaseSystem.insertMoney(money);
                }
                NavController navController = Navigation.findNavController(v);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.statusFragment,true).build();
                navController.navigate(R.id.action_mainAmountFragment_to_statusFragment,null,navOptions);
            }
        });
    }

    private void resetStage() {
        binding.tvDate.setText("");
        binding.tvCateCheck.setText("");
        binding.imgCateCheck.setImageResource(R.drawable.monkey);
        binding.edtMainMoney.setText("");
        binding.edtDetail.setText("");
        binding.edtLocation.setText("");
        binding.boxPeriod.setChecked(false);
        isSaved = false;
    }

    private void  init() {
        period = false;
        Money moneyMessage = new Money();
        if(isSaved){
            Money data = databaseSystem.findNoteMoneyWithId(Long.parseLong(targetId));
            moneyMessage.setId(data.getId());
            moneyMessage.setLocation(data.getLocation());
            moneyMessage.setDetail(data.getDetail());
            moneyMessage.setCategory(data.getCategory());
            moneyMessage.setActualCost(data.getActualCost());
            moneyMessage.setDate(data.getDate());
            moneyMessage.setUsePeriod(data.isUsePeriod());
            moneyMessage.setDiff(data.getDiff());
            moneyMessage.setProjectCost(data.getProjectCost());
        }
        Log.e(getClass().getName(),moneyMessage.toString());
        binding.edtMainMoney.setText((!isSaved)?"":String.valueOf(moneyMessage.getActualCost()));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        if(!isSaved || binding.tvDate.getText().toString().equals("")){
            String dateNow = dateFormat.format(calendar.getTime());
            binding.tvDate.setText(dateNow);
        }else {
            binding.tvDate.setText(moneyMessage.getDate());
        }
        binding.edtMainMoney.addTextChangedListener(new NumberTextWatcherForThousand(binding.edtMainMoney));
        binding.tvCateCheck.setText((!isSaved)?"":moneyMessage.getCategory());
        binding.edtLocation.setText((!isSaved)?"":moneyMessage.getLocation());
        binding.edtLocation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDialogSuggestion();
                return true;
            }
        });
        binding.edtDetail.setText((!isSaved)?"":moneyMessage.getDetail());
        binding.boxPeriod.setChecked((!isSaved)?false:moneyMessage.isUsePeriod());
        if(isSaved){
            switch (binding.tvCateCheck.getText().toString()){
                case "Gas":
                    binding.imgCateCheck.setImageResource(R.drawable.gas);
                    break;
                case "Roser fee":
                    binding.imgCateCheck.setImageResource(R.drawable.rose);
                    break;
                case "Lunch":
                    binding.imgCateCheck.setImageResource(R.drawable.lunch_box);
                    break;
                case "Internet":
                    binding.imgCateCheck.setImageResource(R.drawable.freelance);
                    break;
                case "Groceries":
                    binding.imgCateCheck.setImageResource(R.drawable.grocery_cart);
                    break;
                case "Breakfast":
                    binding.imgCateCheck.setImageResource(R.drawable.coffee_cup);
                    break;
                case "Entertainment":
                    binding.imgCateCheck.setImageResource(R.drawable.popcorn);
                    break;
                case "Electricity":
                    binding.imgCateCheck.setImageResource(R.drawable.electrical_energy);
                    break;
                case "Transport":
                    binding.imgCateCheck.setImageResource(R.drawable.transportation);
                    break;
            }
        }
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.dashBoardFragment,true).build();
                navController.navigate(R.id.action_mainAmountFragment_to_dashBoardFragment,null,navOptions);
                resetStage();
            }
        });
        binding.btnCateBf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.breakfast);
                binding.imgCateCheck.setImageResource(R.drawable.coffee_cup);
            }
        });
        binding.btnCateElectric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.electricity);
                binding.imgCateCheck.setImageResource(R.drawable.electrical_energy);
            }
        });
        binding.btnCateInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.internet);
                binding.imgCateCheck.setImageResource(R.drawable.freelance);
            }
        });
        binding.btnCateEntertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.entertain);
                binding.imgCateCheck.setImageResource(R.drawable.popcorn);
            }
        });
        binding.btnCateGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.gas);
                binding.imgCateCheck.setImageResource(R.drawable.gas);
            }
        });
        binding.btnCateGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.groceries);
                binding.imgCateCheck.setImageResource(R.drawable.grocery_cart);
            }
        });
        binding.btnCateLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.lunch);
                binding.imgCateCheck.setImageResource(R.drawable.lunch_box);
            }
        });
        binding.btnCateTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.transport);
                binding.imgCateCheck.setImageResource(R.drawable.transportation);
            }
        });
        binding.btnCateRoser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvCateCheck.setText(R.string.roser);
                binding.imgCateCheck.setImageResource(R.drawable.rose);
            }
        });
        NumberTextWatcherForThousand.trimCommaOfString(binding.edtMainMoney.getText().toString());
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                calendar.set(Calendar.MINUTE, minute);
                                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                                binding.tvDate.setText(dateFormat.format(calendar.getTime()));
                            }
                        };
                        new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

                    }
                };
                new DatePickerDialog(getContext(), dateListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
        binding.imgCateCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billScanner();
            }
        });
    }

    private void openDialogSuggestion() {
        DialogLocationSuggest dialogSuggest = new DialogLocationSuggest();
        dialogSuggest.setListener(this);
        dialogSuggest.show(getActivity().getSupportFragmentManager(),"dialog support");
    }

    private void billScanner() {
        if ((ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(getContext(),"Camera preparing..",Toast.LENGTH_LONG).show();
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)&&
                ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),Manifest.permission.CAMERA) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(getContext()).setMessage("You need choose permission to do the next step").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }else{
            ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
            dialogGetPic();
        }
    }

    private void dialogGetPic() {
        String[]decisions = {"Camera","Gallery"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Select Image");
        dialog.setItems(decisions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    cameraPicker();
                }
                if(which == 1){
                    galleryPicker();
                }
            }
        });
        dialog.create().show();
    }

    private void cameraPicker(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Pic captured");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Scanner attacked");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        cameraGetForResult.launch(cameraIntent);
    }
    private void galleryPicker(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryPickerForResult.launch(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == MainActivity.RESULT_OK) {
                imageUri = result.getUri();
                Bitmap bitmap = null;
                ContentResolver contentResolver = getActivity().getContentResolver();
                try {
                    if(Build.VERSION.SDK_INT < 28) {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                    } else {
                        ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, imageUri);
                        bitmap = ImageDecoder.decodeBitmap(source);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                binding.imgCateCheck.setImageBitmap(bitmap);
//                TextRecognizer recognizer = new TextRecognizer.Builder(getActivity().getApplicationContext())
//                        .build();
//                if(!recognizer.isOperational()){
//                    Log.e(getClass().getName(),"Error recognizer");
//                }else{
//                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
//                    SparseArray<TextBlock>items = recognizer.detect(frame);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (int i = 0; i < items.size(); i++) {
//                        TextBlock textItem = items.valueAt(i);
//                        stringBuilder.append(textItem.getValue());
//                        stringBuilder.append("\n");
//                        binding.tvDate.setText(stringBuilder.toString());
//                    }
//                }
                InputImage inputImage = InputImage.fromBitmap(bitmap,0);
                TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                Task<Text> resultInput = recognizer.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text text) {
                        StringBuilder result = new StringBuilder();
                        for(Text.TextBlock textBlock : text.getTextBlocks()){
                            String blockText = textBlock.getText();
                            Point[]blockCornerPoint = textBlock.getCornerPoints();
                            Rect blockFrame = textBlock.getBoundingBox();
                            for (Text.Line line : textBlock.getLines()){
                                String lineText = line.getText();
                                Point[]lineCornerPoint = line.getCornerPoints();
                                Rect lineRect = line.getBoundingBox();
                                for (Text.Element element:line.getElements()) {
                                    String elementText = element.getText();
                                    result.append(elementText);
                                }
                                binding.tvDate.setText(blockText);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(getClass().getName(),"Fail to detect");
                    }
                });
            } else if (resultCode ==  CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(), "error: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), "RESULT Okay problem", Toast.LENGTH_SHORT).show();}
        }

        else {
            Toast.makeText(getActivity(), "RequestCode problem", Toast.LENGTH_SHORT).show();}
    }

    ActivityResultLauncher<Intent>cameraGetForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== Activity.RESULT_OK){
                CropImage.activity(imageUri)
                        .start(getContext(), MainAmountFragment.this);
            }
        }
    });
    ActivityResultLauncher<Intent>galleryPickerForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
        }
    });
    private int loadingArraySaved() {
        shareSavingMoney = getActivity().getSharedPreferences(SHARED_TABLE, Context.MODE_PRIVATE);
        int arrayLength = shareSavingMoney.getInt(ARRAY_MONEY_SAVING, 0);
        return arrayLength;
    }

    private void saving(String money) {
        shareSavingMoney = getActivity().getSharedPreferences(SHARED_TABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shareSavingMoney.edit();
        if (loadingArraySaved() == 0) {
            editor.putInt(ARRAY_MONEY_SAVING, 1);
        } else {
            int count = loadingArraySaved();
            editor.putInt(ARRAY_MONEY_SAVING, ++count).commit();
        }
        editor.putString(MONKEY_TASK + loadingArraySaved(), money).commit();
    }
    private ActivityResultLauncher<String>requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if(result){
                        Toast.makeText(getContext(),"Camera preparing..",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getContext(),"Permission denied",Toast.LENGTH_LONG).show();
                    }
                }
            });

    @Override
    public void enterLocation(String locationSuggested) {
        binding.edtLocation.setText(locationSuggested);
    }
}