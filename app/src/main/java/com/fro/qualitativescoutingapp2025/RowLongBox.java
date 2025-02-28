package com.fro.qualitativescoutingapp2025;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Creates a row with one long multicolored box. <br/>
 * Usually, the layout weight of this row is 1 out of a weight sum of 5
 *
 * @requires app:color_pattern=""
 * <br> The color of the background and middle section</li>
 * <ul>
 *     <li> white_grey_white - White background with grey middle section</li>
 *     <li> grey_white_grey - Grey background with white middle section</li>
 * </ul>
 *
 * @requires app:left_text=""
 * <br> The text that will be displayed in the left box header</li>
 * @requires app:middle_text=""
 * <br> The text that will be displayed in the middle box header</li>
 * @requires app:right_text=""
 * <br> The text that will be displayed in the right box header</li>
 *
 * @requires app:left_box_type=""
 * @requires app:middle_box_type=""
 * @requires app:right_box_type=""
 * <br> The type of component that will be in the left/middle/right boxes
 * <ul>
 *     <li>text_type_box - Typed input</li>
 *     <li>number_type_box - Typed input with numbers only</li>
 *     <li>text_dropdown - A dropdown list</li>
 *     <li>team_number_dropdown - A searchable dropdown of team numbers</li>
 *     <li>toggle - An on/off switch</li>
 *     <li>counter - A plus/minus counter starting at 0</li>
 *     <li>stopwatch - A stopwatch with on, off, and reset</li>
 * </ul>
 */
public class RowLongBox extends LinearLayout {

    TextView leftTextView;
    TextView middleTextView;
    TextView rightTextView;
    ComponentFlipper leftFlipper;
    ComponentFlipper middleFlipper;
    ComponentFlipper rightFlipper;

    //Elements to change the box color
    View middleView1;
    View middleView2;
    LinearLayout middleLayout;
    LinearLayout backgroundLayout;

    public RowLongBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.row_long_box, this);

        CharSequence leftText = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "left_text");
        CharSequence middleText = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "middle_text");
        CharSequence rightText = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "right_text");
        CharSequence leftBox =attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "left_box_type");
        CharSequence middleBox =attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "middle_box_type");
        CharSequence rightBox = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "right_box_type");
        CharSequence colorPattern = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "color_pattern");

        initComponents();

        leftFlipper.setPadding(10, 10);
        middleFlipper.setPadding(10,10);
        rightFlipper.setPadding(10,10);

        setLeftText(leftText);
        setMiddleText(middleText);
        setRightText(rightText);
        setLeftBox(leftBox);
        setMiddleBox(middleBox);
        setRightBox(rightBox);
        setColorPattern(colorPattern);
    }

    private void initComponents() {
        leftTextView = findViewById(R.id.leftText);
        middleTextView = findViewById(R.id.middleText);
        rightTextView = findViewById(R.id.rightText);
        leftFlipper = findViewById(R.id.leftFlipper);
        middleFlipper = findViewById(R.id.middleFlipper);
        rightFlipper = findViewById(R.id.rightFlipper);
        middleView1 = findViewById(R.id.middleView1);
        middleView2 = findViewById(R.id.middleView2);
        middleLayout = findViewById(R.id.middleLayout);
        backgroundLayout = findViewById(R.id.backgroundLayout);
    }

    public void setLeftText(CharSequence value) {
        leftTextView.setText(value);
    }

    public void setMiddleText(CharSequence value) {
        middleTextView.setText(value);
    }

    public void setRightText(CharSequence value) {
        rightTextView.setText(value);
    }

    public void setLeftBox(CharSequence value) {
        if (value == null) {return;}
        leftFlipper.changeTo(value);
    }
    public void setMiddleBox(CharSequence value) {
        if (value == null) {return;}
        middleFlipper.changeTo(value);
    }
    public void setRightBox(CharSequence value) {
        if (value == null) {return;}
        rightFlipper.changeTo(value);
    }
    public void setColorPattern(CharSequence value) {
        if (value == null) {return;}
        if (value.equals("0")){
            backgroundLayout.setBackgroundResource(R.drawable.box_background);
            middleView1.setBackgroundResource(R.color.light_grey);
            middleView2.setBackgroundResource(R.color.light_grey);
            middleLayout.setBackgroundResource(R.color.light_grey);
        }
        if (value.equals("1")){
            backgroundLayout.setBackgroundResource(R.drawable.box_background_grey);
            middleView1.setBackgroundResource(R.color.white);
            middleView2.setBackgroundResource(R.color.white);
            middleLayout.setBackgroundResource(R.color.white);
        }
    }

    public void createTypeBox(String name, int inputType, int maxCharacters, int position) {
        if (position == Values.left) {leftFlipper.createTypeBox(name, inputType, maxCharacters);}
        else if (position == Values.middle) {middleFlipper.createTypeBox(name, inputType, maxCharacters);}
        else if (position == Values.right) {rightFlipper.createTypeBox(name, inputType, maxCharacters);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in type box "+name, Toast.LENGTH_SHORT).show();
        }
    }

    public void createTextDropdown(String name, ArrayList<String> array, int position) {
        if (position == Values.left) {leftFlipper.createTextDropdown(name, array);}
        else if (position == Values.middle) {middleFlipper.createTextDropdown(name, array);}
        else if (position == Values.right) {rightFlipper.createTextDropdown(name, array);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in dropdown "+name, Toast.LENGTH_SHORT).show();}
    }

    public void createTeamNumberDropdown(String name, int position) {
        if (position == Values.left) {leftFlipper.createTeamNumberDropdown(name);}
        else if (position == Values.middle) {middleFlipper.createTeamNumberDropdown(name);}
        else if (position == Values.right) {rightFlipper.createTeamNumberDropdown(name);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in dropdown "+name, Toast.LENGTH_SHORT).show();}
    }

    public void createToggle(String name, int position) {
        if (position == Values.left) {leftFlipper.createToggle(name);}
        else if (position == Values.middle) {middleFlipper.createToggle(name);}
        else if (position == Values.right) {rightFlipper.createToggle(name);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in toggle "+name, Toast.LENGTH_SHORT).show();}
    }

    public void createCounter(String name, int maxValue, int position) {
        if (position == Values.left) {leftFlipper.createCounter(name, maxValue);}
        else if (position == Values.middle) {middleFlipper.createCounter(name, maxValue);}
        else if (position == Values.right) {rightFlipper.createCounter(name, maxValue);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in counter "+name, Toast.LENGTH_SHORT).show();}
    }

    public void createStopwatch(String name, int position) {
        if (position == Values.left) {leftFlipper.createStopwatch(name);}
        else if (position == Values.middle) {middleFlipper.createStopwatch(name);}
        else if (position == Values.right) {rightFlipper.createStopwatch(name);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in counter "+name, Toast.LENGTH_SHORT).show();}
    }
}