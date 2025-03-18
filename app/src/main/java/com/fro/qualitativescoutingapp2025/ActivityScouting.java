package com.fro.qualitativescoutingapp2025;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityScouting extends AppCompatActivity {
    // Declare global variables here \/
    // Layout
    ConstraintLayout fullPage;
    // Buttons
    ImageButton homeButton;
    TextView submitButton;
    ImageButton helpButton;
    // Submit popup
    TextView confirmText;
    LinearLayout confirmPopup;
    TextView confirmYes;
    TextView confirmNo;
    // Help popup
    HelpPopup helpPopup;
    // Rows
    RowLargeBox row1;
    RowLargeBox row2;
    RowLargeBox row3;
    RowLargeBox row4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting);
        // Hides system bars
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        // Instantiate variables here \/
        // Layouts
        fullPage = findViewById(R.id.scouting_full_page);
        // Buttons
        homeButton = findViewById(R.id.home_button);
        submitButton = findViewById(R.id.submit_button);
        helpButton = findViewById(R.id.help_button);
        // Submit popup
        confirmText = findViewById(R.id.confirm_text);
        confirmPopup = findViewById(R.id.confirm_popup);
        confirmYes = findViewById(R.id.confirm_yes);
        confirmNo = findViewById(R.id.confirm_no);
        // Help popup
        helpPopup = findViewById(R.id.scouting_help_popup);
        // Rows
        row1 = findViewById(R.id.row_1);
        row2 = findViewById(R.id.row_2);
        row3 = findViewById(R.id.row_3);
        row4 = findViewById(R.id.row_4);

        // Input fields
        row1.createTypeBox("General_Match_Notes", Values.inputType_text, 1000);
        row2.createTypeBox("Strengths", Values.inputType_text, 1000);
        row3.createTypeBox("Weaknesses_Failures", Values.inputType_text, 1000);
        row4.createTypeBox("Questionable_Strategy_or_Team_Issues", Values.inputType_text, 1000);

        // Set the size and position of popups
        fullPage.post(new Runnable() {
            @Override
            public void run() {
                // Confirm popup
                ViewGroup.MarginLayoutParams confirmParams = (ViewGroup.MarginLayoutParams) confirmPopup.getLayoutParams();
                confirmParams.width = (int) (fullPage.getWidth()/1.8);
                confirmParams.height = fullPage.getHeight()/3;
                confirmPopup.setLayoutParams(confirmParams);

                // Help popup
                ViewGroup.MarginLayoutParams helpParams = (ViewGroup.MarginLayoutParams) helpPopup.getLayoutParameters();
                helpParams.width = (int) (fullPage.getWidth()/1.1);
                helpParams.height = (int) (fullPage.getHeight()/1.1);
                helpParams.bottomMargin = fullPage.getHeight()/100;
                helpPopup.setLayoutParameters(helpParams);
            }
        });

        // help popup
        helpPopup.setVisibility(VISIBLE);
        helpPopup.createHelp(helpButton);
        helpPopup.setRatio(5f,4f);

        // Switch page to home
        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ActivityMain.class));
        });

        // Open confirmation screen
        submitButton.setOnClickListener(v -> {
            confirmPopup.setVisibility(VISIBLE);
            Values.sortData();
            confirmText.setText(Values.checkData());
        });

        // Submit all data
        confirmYes.setOnClickListener(v -> {
            confirmPopup.setVisibility(GONE);
            Values.exportData(getApplicationContext());
            Values.clearData();
            startActivity(new Intent(getApplicationContext(), ActivityMain.class));
        });

        // Close confirmation screen
        confirmNo.setOnClickListener(v -> {
            confirmPopup.setVisibility(GONE);
        });
    }
}