package alborz.rad.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainPage extends AppCompatActivity {
    private final ArrayList<Integer> choosedOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView showPassTexView = findViewById(R.id.showPass);
        Button generate = findViewById(R.id.generate);
        ImageView copyContentImage = findViewById(R.id.copyImage);
        EditText passwordLength = findViewById(R.id.passworLength);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (5 <= Integer.parseInt(passwordLength.getText().toString()) &&
                            20 >= Integer.parseInt(passwordLength.getText().toString())) {
                        showPassTexView.setText(generatePassword(Integer.parseInt(passwordLength.getText().toString())));
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter A Valid Length", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Length Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyContentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!showPassTexView.getText().toString().equals("")) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("For Copy The Content", showPassTexView.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getApplicationContext(), "Copied To Clipboard", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Field Is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String generatePassword(int passwordLength) {
        SwitchCompat lowerCaseSwitch = findViewById(R.id.lowerCase);
        SwitchCompat upperCaseSwitch = findViewById(R.id.upperCase);
        SwitchCompat numbersSwitch = findViewById(R.id.numbers);
        SwitchCompat specialLettersSwitch = findViewById(R.id.specialLetters);
        StringBuilder password = new StringBuilder("");
        int operator = -1;
        if(lowerCaseSwitch.isChecked()) choosedOptions.add(1);
        if(upperCaseSwitch.isChecked()) choosedOptions.add(2);
        if(numbersSwitch.isChecked()) choosedOptions.add(3);
        if(specialLettersSwitch.isChecked()) choosedOptions.add(4);

        try {
            for (int i = 1; i <= passwordLength; i++) {
                operator = ((int) (Math.random() * (choosedOptions.size()))) ;
                switch (choosedOptions.get(operator)) {
                    case 1:
                        password.append((char) (((int) (Math.random() * (122 - 97 + 1))) + 97));
                        break;
                    case 2:
                        password.append((char) (((int) (Math.random() * (90 - 65 + 1))) + 65));
                        break;
                    case 3:
                        password.append((char) (((int) (Math.random() * (57 - 48 + 1))) + 48));
                        break;
                    case 4:
                        password.append((char) (((int) (Math.random() * (47 - 33 + 1))) + 33));
                        break;
                }
            }
        } catch (Exception e){}
        choosedOptions.clear();
        return password.toString();
    }
}