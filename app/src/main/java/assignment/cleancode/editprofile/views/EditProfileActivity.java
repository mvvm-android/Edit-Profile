package assignment.cleancode.editprofile.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseUser;

import assignment.cleancode.editprofile.R;
import assignment.cleancode.editprofile.base.BaseActivity;
import assignment.cleancode.editprofile.databinding.ActivityEditProfileBinding;
import assignment.cleancode.editprofile.enums.ViewModelEventsEnum;
import assignment.cleancode.editprofile.models.validationmodels.EditProfileModel;
import assignment.cleancode.editprofile.viewmodels.EditProfileViewModel;

public class EditProfileActivity extends BaseActivity<EditProfileViewModel, ActivityEditProfileBinding> {

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, EditProfileActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public Class getViewModel() {
        return EditProfileViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_edit_profile;
    }

    @Override
    public void onObserve(ViewModelEventsEnum event, Object eventMessage) {
        super.onObserve(event, eventMessage);
        switch (event) {
            case NO_INTERNET_CONNECTION:
                showAlert(binding.parentProfile, getString(R.string.STR_NO_INTERNET_CONNECTIVITY));
                break;
            case ON_API_REQUEST_FAILURE:
                showAlert(binding.parentProfile, eventMessage.toString());
                break;
            case ON_API_CALL_START:
                showProgress();
                setEnableDisable(false);
                break;
            case ON_API_CALL_STOP:
                hideProgress();
                setEnableDisable(true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        binding.setUserInfo(viewModel.getUserInfo(this));
    }

    public void attemptUpdateProfile(View view) {
        boolean isFormValid;
        EditProfileModel editProfile = viewModel.getUserInfo(this);
        editProfile.validate();
        binding.executePendingBindings();
        isFormValid = editProfile.isValid();
        if (isFormValid) {
            setEnableDisable(false);
            viewModel.updateProfile(editProfile.getName(), editProfile.getEmail()).observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(@Nullable FirebaseUser firebaseUser) {
                    showAlert(binding.parentProfile, getString(R.string.STR_UPDATE_USER_PROFILE_SUCCESS));
                }
            });
        }
    }

    public void attemptUpdatePassword(View view) {
        boolean isFormValid;
        EditProfileModel editProfile = viewModel.getUserInfo(this);
        editProfile.validatePassword();
        binding.executePendingBindings();
        isFormValid = editProfile.isValid();
        if (isFormValid) {
            setEnableDisable(false);
            viewModel.updatePassword(editProfile.getPassword()).observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(@Nullable FirebaseUser firebaseUser) {
                    showAlert(binding.parentProfile, getString(R.string.STR_UPDATE_USER_PASSWORD_SUCCESS));
                }
            });
        }
    }

    public void attemptUpdateEmail(View view) {
        boolean isFormValid;
        EditProfileModel editProfile = viewModel.getUserInfo(this);
        editProfile.validateEmail();
        binding.executePendingBindings();
        isFormValid = viewModel.getUserInfo(this).isValid();
        if (isFormValid) {
            setEnableDisable(false);
            viewModel.updateEmail(editProfile.getEmail()).observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(@Nullable FirebaseUser firebaseUser) {
                    showAlert(binding.parentProfile, getString(R.string.STR_UPDATE_USER_EMAIL_SUCCESS));
                }
            });
        }
    }

    private void setEnableDisable(boolean enable) {
        binding.updateProfileBtn.setEnabled(enable);
        binding.updateEmailBtn.setEnabled(enable);
        binding.updatePasswordBtn.setEnabled(enable);

    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackButtonPressed(View view) {
        onSupportNavigateUp();
    }
}
