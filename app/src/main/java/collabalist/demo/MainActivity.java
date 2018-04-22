package collabalist.demo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import collabalist.demo.databinding.ActivityMainBinding;
import collabalist.focuswork.FocusWork;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        FocusWork.with(MainActivity.this)
                .createOTPfocusFor(binding.otp1,
                        binding.otp2,
                        binding.otp3,
                        binding.otp4,
                        binding.otp5);
    }
}
