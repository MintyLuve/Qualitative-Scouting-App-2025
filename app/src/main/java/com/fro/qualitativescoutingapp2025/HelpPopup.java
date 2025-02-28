package com.fro.qualitativescoutingapp2025;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class HelpPopup extends LinearLayout {

    LinearLayout helpScreen;
    TextView helpHeader1;
    TextView helpHeader2;
    TextView helpText1;
    TextView helpText2;
    ImageView helpImage;
    ImageButton helpLeftArrow;
    ImageButton helpRightArrow;
    TextView helpPageNumber;
    ConstraintLayout fullPage;

    public HelpPopup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.help_popup, this);

        //init components
        helpHeader1 = findViewById(R.id.help_header_1);
        helpText1 = findViewById(R.id.help_text_1);
        helpHeader2 = findViewById(R.id.help_header_2);
        helpText2 = findViewById(R.id.help_text_2);
        helpImage = findViewById(R.id.help_image);
        helpLeftArrow = findViewById(R.id.help_left_arrow);
        helpRightArrow = findViewById(R.id.help_right_arrow);
        helpPageNumber = findViewById(R.id.help_page_number);
        fullPage = findViewById(R.id.help_full_page);
        helpScreen = findViewById(R.id.help_popup);

    }
    public void createHelp(ImageButton helpButton){
        helpScreen.setVisibility(GONE);
        helpButton.setOnClickListener(v -> {
            helpScreen.setVisibility(VISIBLE);
            fullPage.setClickable(true);
            helpButton.setImageResource(R.drawable.help_button_exit);
            helpButton.setClickable(false);

            fullPage.setOnClickListener(v11 -> {
                helpScreen.setVisibility(GONE);
                helpButton.setClickable(true);
                helpButton.setImageResource(R.drawable.help_button);
                fullPage.setClickable(false);
            });

            //when right arrow is clicked it changes the page
            helpRightArrow.setOnClickListener(v12 -> {
                if (helpPageNumber.getText().toString().equals("Pg. 1")) {
                    helpHeader1.setText(R.string.help_screen_header_1_page_2);
                    helpText1.setText(R.string.help_screen_text_1_page_2);
                    helpHeader2.setText(R.string.help_screen_header_2_page_2);
                    helpText2.setText(R.string.help_screen_text_2_page_2);
                    helpPageNumber.setText("Pg. 2");
                    helpText2.setVisibility(VISIBLE);
                    helpHeader2.setVisibility(VISIBLE);
                    helpImage.setVisibility(GONE);
                    helpRightArrow.setVisibility(VISIBLE);
                    helpLeftArrow.setVisibility(VISIBLE);
                } else if (helpPageNumber.getText().toString().equals("Pg. 2")) {
                    helpHeader1.setText(R.string.help_screen_header_1_page_3);
                    helpText1.setText(R.string.help_screen_text_1_page_3);
                    helpHeader2.setText(R.string.help_screen_header_2_page_3);
                    helpText2.setText(R.string.help_screen_text_2_page_3);
                    helpPageNumber.setText("Pg. 3");
                    helpText2.setVisibility(VISIBLE);
                    helpHeader2.setVisibility(VISIBLE);
                    helpImage.setVisibility(GONE);
                    helpRightArrow.setVisibility(INVISIBLE);
                    helpLeftArrow.setVisibility(VISIBLE);
                }
            });

            //when left arrow is clicked it changes the page
            helpLeftArrow.setOnClickListener(v13 -> {
                if (helpPageNumber.getText().toString().equals("Pg. 2")) {
                    helpHeader1.setText(R.string.help_screen_header_1_page_1);
                    helpText1.setText(R.string.help_screen_text_1_page_1);
                    helpPageNumber.setText("Pg. 1");
                    helpText2.setVisibility(GONE);
                    helpHeader2.setVisibility(GONE);
                    helpImage.setVisibility(VISIBLE);
                    helpRightArrow.setVisibility(VISIBLE);
                    helpLeftArrow.setVisibility(INVISIBLE);
                } else if (helpPageNumber.getText().toString().equals("Pg. 3")) {
                    helpHeader1.setText(R.string.help_screen_header_1_page_2);
                    helpText1.setText(R.string.help_screen_text_1_page_2);
                    helpHeader2.setText(R.string.help_screen_header_2_page_2);
                    helpText2.setText(R.string.help_screen_text_2_page_2);
                    helpPageNumber.setText("Pg. 2");
                    helpText2.setVisibility(VISIBLE);
                    helpHeader2.setVisibility(VISIBLE);
                    helpImage.setVisibility(GONE);
                    helpRightArrow.setVisibility(VISIBLE);
                    helpLeftArrow.setVisibility(VISIBLE);
                }
            });
        });
    }

    /**
     * Top and bottom need to total to 9
     * @param top
     * @param bottom
     */
    public void setRatio(float top, float bottom){
        helpText1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, top));
        helpText2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, bottom));
        helpImage.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 10-top));
    }

    public ViewGroup.LayoutParams getLayoutParameters(){
        return helpScreen.getLayoutParams();
    }

    public void setLayoutParameters(MarginLayoutParams params){
        helpScreen.setLayoutParams(params);
    }
}
