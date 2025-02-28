package com.fro.qualitativescoutingapp2025;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityMain extends AppCompatActivity {
    // Declare global variables here \/
    TextView startButton;
    ImageButton helpButton;
    HelpPopup helpPopup;
    ConstraintLayout fullPage;
    RowTwoBoxes row1;
    RowThreeBoxes row2;
    RowThreeBoxes row3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hides system bars
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        // Instantiate variables here \/
        startButton = findViewById(R.id.startButton);
        helpButton = findViewById(R.id.main_help_button);
        helpPopup = findViewById(R.id.main_help_popup);
        fullPage = findViewById(R.id.main_full_page);
        row1 = findViewById(R.id.main_row_1);
        row2 = findViewById(R.id.main_row_2);
        row3 = findViewById(R.id.main_row_3);

        // Row 1
        row1.createTypeBox("Main_Scouter_Name", Values.inputType_text,20, Values.left);
        row1.createTypeBox("Main_Match_Number", Values.inputType_number,3, Values.right);

        // Row 2
        row2.createTeamNumberDropdown("Main_Red_Team_1", Values.left);
        row2.createTeamNumberDropdown("Main_Red_Team_2", Values.middle);
        row2.createTeamNumberDropdown("Main_Red_Team_3", Values.right);

        // Row 3
        row3.createTeamNumberDropdown("Main_Blue_Team_1", Values.left);
        row3.createTeamNumberDropdown("Main_Blue_Team_2", Values.middle);
        row3.createTeamNumberDropdown("Main_Blue_Team_3", Values.right);

        // button to go to next page
        startButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ActivityScouting.class)));

        // Set the size and position of help popup
        fullPage.post(new Runnable() {
            @Override
            public void run() {
                // Help popup
                ViewGroup.MarginLayoutParams helpParams = (ViewGroup.MarginLayoutParams) helpPopup.getLayoutParameters();
                helpParams.width = (int) (fullPage.getWidth()/1.25);
                helpParams.height = (int) (fullPage.getHeight()/1.25);
                helpPopup.setLayoutParameters(helpParams);
            }
        });
        // help button
        helpPopup.setVisibility(VISIBLE);
        helpPopup.createHelp(helpButton);
        helpPopup.setRatio(4,5);
    }
}