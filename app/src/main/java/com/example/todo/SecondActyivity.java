package com.example.todo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SecondActyivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY = 12;
    private static final int THUMBNAIL_WITHD = 500;
    private static final int THUMBNAIL_HEIGHT = 500;
    private static final int GALLERY_REQUEST_CODE = 123;

    public static final String NAME_DATA = "NAME";
    public static final String SURNAME_DATA = "SURNAME";
    public static final String MODE_DATA = "MODE";
    public static final String IMAGE_DATA = "IMAGE";


    private EditText edtNameInfo;
    private EditText edtSurnameInfo;
    private RadioGroup modeInfo;
    private ImageView profileImageInfo;
    private Button createButton;


    private Uri imageUri;
    private RadioButton selectedRadioButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        edtNameInfo = findViewById(R.id.nameEdt_id);
        edtSurnameInfo = findViewById(R.id.surnNameEdt_id);
        modeInfo = findViewById(R.id.myRadioGroup);
        profileImageInfo = findViewById(R.id.profileImage);
        selectedRadioButton = findViewById(modeInfo.getCheckedRadioButtonId());

        profileImageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasGalleryPermission()) {
                    //open gallery
                    openGallery();
                } else {
                    //reqest permission
                    requestPermission();
                }

            }
        });

        createButton = findViewById(R.id.create_id);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canSave()) {
                    Intent intent = new Intent();
                    intent.putExtra(NAME_DATA, edtNameInfo.getText().toString());
                    intent.putExtra(SURNAME_DATA, edtSurnameInfo.getText().toString());
                    intent.putExtra(MODE_DATA, selectedRadioButton.getText().toString());
                    intent.putExtra(IMAGE_DATA, imageUri.toString());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(SecondActyivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        modeInfo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                    selectedRadioButton = findViewById(checkedId);
                                                }
                                            }
        );
    }

    private boolean hasGalleryPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_IMAGE_GALLERY);
    }

    private void requestPermission() {
        String[] permssions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permssions, GALLERY_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                onSelectFromGalleryResult(data);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
//        Bitmap bm = null;
//        if (data != null) {
//            try {
//                galleryImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
//                thumbnailBitmap = ThumbnailUtils.extractThumbnail(galleryImage, THUMBNAIL_WITHD, THUMBNAIL_HEIGHT);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.pmjrintStackTrace();
//            }
//        }
        imageUri = data.getData();
        profileImageInfo.setImageURI(data.getData());
    }

    private boolean canSave() {
        String nameInfo = edtNameInfo.getText().toString();
        String surnnameInfo = edtSurnameInfo.getText().toString();

        if (!TextUtils.isEmpty(nameInfo) && !TextUtils.isEmpty(surnnameInfo)
                && imageUri != null && selectedRadioButton != null) {
            return true;
        }
        return false;
    }
}
