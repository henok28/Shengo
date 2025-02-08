package school.project.shengoapp0.ui.autentication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.others.VerificationState;
import school.project.shengoapp0.viewmodels.VerificationFromViewModel;

public class VerificationForm extends Fragment {
    private VerificationFromViewModel verificationFromViewModel;
    private ViewFlipper viewFlipper;
    private static final int PROFILE_IMAGE_REQUEST = 1;
    private static final int ID_IMAGE_REQUEST_FRONT = 2;
    private static final int ID_IMAGE_REQUEST_BACK = 3;
    private File profileImageFile = null;
    private File idImageFile_Front = null;
    private File idImageFile_Back = null;
    private ImageView ivProfilePicture;
    private ImageView ivIdPhotoFront;

    private ImageView ivIdPhotoBack;

    private Button btnPrev;
    private Button btnNext;
    private int totalPages = 2;
    private int currentPage = 1;
    String FirstName, MiddleName, LastName,  PhoneNumber, Gender, Address, City, State, dateOfBirth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = new ContextThemeWrapper(getActivity(), R.style.CustomTextTheme);
        LayoutInflater localInflater = inflater.cloneInContext(context);
        return inflater.inflate(R.layout.fragment_verification_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        RadioGroup gender = view.findViewById(R.id.radio_group_gender);


        initializeViews(view);
        setupListeners();
        updateNavigationButtons();

//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
//                new OnBackPressedCallback(true) {
//                    @Override
//                    public void handleOnBackPressed() {
//                        if (currentPage > 1) {
//                            // If we're on the second page, go back to first page
//                            viewFlipper.showPrevious();
//                            currentPage--;
//                            updateNavigationButtons();
//                        } else {
//                            // If we're on the first page, allow normal back navigation
//                            setEnabled(false);
//                            requireActivity().onBackPressed();
//                        }
//                    }
//                });

        ivProfilePicture = view.findViewById(R.id.iv_profile_picture);
        ivIdPhotoFront = view.findViewById(R.id.front_photo);
        ivIdPhotoBack = view.findViewById(R.id.back_photo);

        // Setup upload buttons
        Button btnUploadProfile = view.findViewById(R.id.btn_upload_profile);
        Button btnUploadId = view.findViewById(R.id.btn_upload_front);
        Button btnUploadIdBack = view.findViewById(R.id.btn_upload_back);

        btnUploadProfile.setOnClickListener(v -> selectImage(PROFILE_IMAGE_REQUEST));
        btnUploadId.setOnClickListener(v -> selectImage(ID_IMAGE_REQUEST_FRONT));
        btnUploadIdBack.setOnClickListener(v->selectImage(ID_IMAGE_REQUEST_BACK));


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_male) {
                    Gender = "Male";
                } else if (checkedId == R.id.radio_female) {
                    Gender = "Female";
                }
            }
        });



    }

    private void selectImage(int requestCode) {
        // Options to display in dialog
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                // User wants to take a photo
                requestCameraPermission(requestCode);
            } else if (options[item].equals("Choose from Gallery")) {
                // User wants to choose from gallery
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, requestCode + 100);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void requestCameraPermission(int requestCode) {
        // Check if we have camera permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission if we don't have it
            requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
        } else {
            // We already have permission, proceed to open camera
            openCamera(requestCode);
        }
    }

    private void requestGalleryPermission(int requestCode) {
        // Check if we have storage permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            // Request permission if we don't have it
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        } else {
            // We already have permission, proceed to open gallery
            openGallery(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == PROFILE_IMAGE_REQUEST || requestCode == ID_IMAGE_REQUEST_FRONT || requestCode == ID_IMAGE_REQUEST_BACK) {
                if (permissions[0].equals(Manifest.permission.CAMERA)) {
                    openCamera(requestCode);
                } else if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    openGallery(requestCode);
                }
            }
        }
    }


    private void openCamera(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create file to store the image
            File photoFile = createImageFile(requestCode);
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "school.project.shengoapp0.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),requestCode
        );
    }


    private File createImageFile(int requestCode) {
        // Create an image file name using timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // Get the directory for storing images
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            // Save the file based on request code
            if (requestCode == PROFILE_IMAGE_REQUEST) {
                profileImageFile = image;
            } else if (requestCode == ID_IMAGE_REQUEST_BACK) {
                idImageFile_Back = image;
            } else {
                idImageFile_Front = image;
            }
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // Handle gallery selection (requestCode + 100)
            if (requestCode == PROFILE_IMAGE_REQUEST + 100) {
                if (data != null && data.getData() != null) {
                    Uri selectedImage = data.getData();
                    ivProfilePicture.setImageURI(selectedImage);
                    profileImageFile = getFileFromUri(selectedImage);
                }
            } else if (requestCode == ID_IMAGE_REQUEST_FRONT + 100) {
                if (data != null && data.getData() != null) {
                    Uri selectedImage = data.getData();
                    ivIdPhotoFront.setImageURI(selectedImage);
                    idImageFile_Front = getFileFromUri(selectedImage);
                }
            }
            else if (requestCode == ID_IMAGE_REQUEST_BACK + 100) {
                if (data != null && data.getData() != null) {
                    Uri selectedImage = data.getData();
                    ivIdPhotoBack.setImageURI(selectedImage);
                    idImageFile_Back = getFileFromUri(selectedImage);
                }
            }
            // Handle camera capture
            else if (requestCode == PROFILE_IMAGE_REQUEST || requestCode == ID_IMAGE_REQUEST_FRONT || requestCode == ID_IMAGE_REQUEST_BACK){
                File imageFile = (requestCode == PROFILE_IMAGE_REQUEST) ? profileImageFile : idImageFile_Front;
                if (requestCode == ID_IMAGE_REQUEST_BACK) {
                    imageFile = idImageFile_Back;
                }
                if (imageFile != null) {
                    Uri photoUri = Uri.fromFile(imageFile);
                    if (requestCode == PROFILE_IMAGE_REQUEST) {
                        ivProfilePicture.setImageURI(photoUri);
                    } else if(requestCode == ID_IMAGE_REQUEST_FRONT){
                        ivIdPhotoFront.setImageURI(photoUri);
                    }else{
                        ivIdPhotoBack.setImageURI(photoUri);
                    }
                }
            }
        }
    }


    private File getFileFromUri(Uri uri) {
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = requireActivity().getContentResolver()
                    .query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                cursor.close();
                return new File(path);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    private void initializeViews(View view) {
        viewFlipper = view.findViewById(R.id.view_flipper);
        btnPrev = view.findViewById(R.id.btn_prev);
        btnNext = view.findViewById(R.id.btn_next);

        // Initially disable prev button
        btnPrev.setEnabled(false);
        btnPrev.setAlpha(0.5f);
    }

    private void setupListeners() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCurrentPage()) {
                    if (currentPage < totalPages) {
                        viewFlipper.showNext();
                        currentPage++;
                        updateNavigationButtons();
                    } else {
                        submitForm();
                    }
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 1) {
                    viewFlipper.showPrevious();
                    currentPage--;
                    updateNavigationButtons();
                }
            }
        });
    }

    private void updateNavigationButtons() {
        btnPrev.setEnabled(currentPage > 1);
        btnPrev.setAlpha(currentPage == 1 ? 0.5f : 1.0f);

        if (currentPage == totalPages) {
            btnNext.setText("Submit");
            if (getContext() != null) {
                btnNext.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.submit_button_color)));
            }
        } else {
            btnNext.setText("Next");
            if (getContext() != null) {
                btnNext.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(getContext(), R.color.next_button_color)));
            }
        }
    }

    private boolean validateCurrentPage() {
        switch (currentPage) {
            case 1:
                return validatePersonalDetails();
            case 2:
                return validateAddressAndPhotos();
            default:
                return true;
        }
    }

    private boolean validatePersonalDetails() {
        EditText firstName = getView().findViewById(R.id.et_full_name);
        EditText middleName = getView().findViewById(R.id.et_middle_name);
        EditText lastName = getView().findViewById(R.id.et_last_name);

        EditText phone = getView().findViewById(R.id.et_phone);
        EditText day = getView().findViewById(R.id.et_birth_day);
        EditText month = getView().findViewById(R.id.et_birth_month);
        EditText year = getView().findViewById(R.id.et_birth_year);
        RadioGroup gender = getView().findViewById(R.id.radio_group_gender);

        if (firstName.getText().toString().trim().isEmpty()) {
            firstName.setError("First name is required");
            return false;
        }
        if (middleName.getText().toString().trim().isEmpty()) {
            middleName.setError("First name is required");
            return false;
        }
        if (lastName.getText().toString().trim().isEmpty()) {
            lastName.setError("First name is required");
            return false;
        }

        if (phone.getText().toString().trim().isEmpty()) {
            phone.setError("Phone number is required");
            return false;
        }
        if (phone.getText().toString().length()<9  || phone.getText().toString().length()>10){
            phone.setError("Invalid Phone format");
            return false;
        }
        if (day.getText().toString().trim().isEmpty() ||
                month.getText().toString().trim().isEmpty() ||
                year.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "date of birth is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (day.getText().toString().length()<2 || day.getText().toString().length()>2) {
            day.setError("Invalid Day format");
            return false;
        }

        if (month.getText().toString().length()<2 || day.getText().toString().length()>2) {
            month.setError("Invalid Month format");
            return false;
        }
        if (year.getText().toString().length()<4 || day.getText().toString().length()>4) {
            year.setError("Invalid Year format");
            return false;
        }

        if (gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Please select gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        FirstName = firstName.getText().toString().trim();
        MiddleName = middleName.getText().toString().trim();
        LastName = lastName.getText().toString().trim();

        PhoneNumber = phone.getText().toString().trim();
        dateOfBirth = String.join(
                "/",
                day.getText().toString().trim(),
                month.getText().toString().trim(),
                year.getText().toString().trim()
        );
        int selectedId = gender.getCheckedRadioButtonId();
        if (selectedId == R.id.radio_male) {
            Gender = "Male";
        } else if (selectedId == R.id.radio_female) {
            Gender = "Female";
        }

        return true;
    }

    private boolean validateAddressAndPhotos() {
        EditText address = getView().findViewById(R.id.et_address);
        EditText city = getView().findViewById(R.id.et_city);
        EditText state = getView().findViewById(R.id.et_state);

        if (address.getText().toString().trim().isEmpty()) {
            address.setError("Address is required");
            return false;
        }

        if (city.getText().toString().trim().isEmpty()) {
            city.setError("City is required");
            return false;
        }

        if (state.getText().toString().trim().isEmpty()) {
            state.setError("State is required");
            return false;
        }
        Address = address.getText().toString();
        City = city.getText().toString();
        State = state.getText().toString();

        return true;
    }

    private void submitForm() {
        if (validateImages()) {
            verificationFromViewModel = new ViewModelProvider(this)
                    .get(VerificationFromViewModel.class);

            verificationFromViewModel.submitForm(
                    FirstName,
                    MiddleName,
                    LastName,
                    PhoneNumber,
                    dateOfBirth,
                    Gender,
                    Address,
                    City,
                    State,
                    profileImageFile,
                    idImageFile_Front,
                    idImageFile_Back
            );

            setupFormObservers();
        }
        Log.d("TAG", "submitForm: "+dateOfBirth);
        Log.d("TAG", "Gen: "+Gender);
    }

    private boolean validateImages() {
        if (profileImageFile == null || idImageFile_Front == null ||idImageFile_Back==null) {
            Toast.makeText(
                    getContext(),
                    "Please upload both profile picture and ID photos",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }
        return true;
    }

    private void setupFormObservers() {
        verificationFromViewModel.getFormResponse().observe(
                getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String verificationFormModal) {

                        Toast.makeText(
                                getContext(),
                                "Success: " + verificationFormModal,
                                Toast.LENGTH_SHORT
                        ).show();
                        ((MainActivity)requireActivity()).swapFragments(new VerificationState());
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("FormStatus", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("hasSubmittedForm", true);
                        editor.apply();
                    }
                }
        );

        verificationFromViewModel.getFormError().observe(
                getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String error) {
                        Toast.makeText(
                                getContext(),
                                "Error: " + error,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }
}