package com.fro.qualitativescoutingapp2025;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Creates a row with one long multicolored box. <br/>
 * Usually, the layout weight of this row is 3 out of a weight sum of 5
 *
 * @requires app:text_1=""
 * @requires app:text_2=""
 * @requires app:text_3=""
 * @requires app:text_4=""
 * @requires app:text_5=""
 * <br> The text that will be displayed in the headers on the left. Numbers are from 1-5, top-bottom</li>
 *
 * @requires app:box_type_1=""
 * @requires app:box_type_2=""
 * @requires app:box_type_3=""
 * @requires app:box_type_4=""
 * @requires app:box_type_5=""
 * <br> The type of component that will be in the boxes. Boxes are from 1-5, top-bottom
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
public class RowVerticalLongBox extends LinearLayout{
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    ComponentFlipper flipper1;
    ComponentFlipper flipper2;
    ComponentFlipper flipper3;
    ComponentFlipper flipper4;
    ComponentFlipper flipper5;

    public RowVerticalLongBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.row_vertical_long_box, this);

        CharSequence text1 = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "text_1");
        CharSequence text2 = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "text_2");
        CharSequence text3 = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "text_3");
        CharSequence text4 =attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "text_4");
        CharSequence text5 =attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "text_5");

        CharSequence box1 = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "box_type_1");
        CharSequence box2 = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "box_type_2");
        CharSequence box3 = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "box_type_3");
        CharSequence box4 =attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "box_type_4");
        CharSequence box5 =attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "box_type_5");

        initComponents();

        flipper1.setPadding(5, 5);
        flipper2.setPadding(5, 5);
        flipper3.setPadding(5, 5);
        flipper4.setPadding(5, 5);
        flipper5.setPadding(5, 5);

        setText1(text1);
        setText2(text2);
        setText3(text3);
        setText4(text4);
        setText5(text5);
        setBox1(box1);
        setBox2(box2);
        setBox3(box3);
        setBox4(box4);
        setBox5(box5);
    }

    private void initComponents() {
        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        textView4 = findViewById(R.id.text4);
        textView5 = findViewById(R.id.text5);
        flipper1 = findViewById(R.id.box1);
        flipper2 = findViewById(R.id.box2);
        flipper3 = findViewById(R.id.box3);
        flipper4 = findViewById(R.id.box4);
        flipper5 = findViewById(R.id.box5);
    }

    public void setText1(CharSequence value) {
        textView1.setText(value);
    }
    public void setText2(CharSequence value) {
        textView2.setText(value);
    }
    public void setText3(CharSequence value) {
        textView3.setText(value);
    }
    public void setText4(CharSequence value) {
        textView4.setText(value);
    }
    public void setText5(CharSequence value) {
        textView5.setText(value);
    }

    public void setBox1(CharSequence value) {
        if (value == null) {return;}
        flipper1.changeTo(value);
    }
    public void setBox2(CharSequence value) {
        if (value == null) {return;}
        flipper2.changeTo(value);
    }
    public void setBox3(CharSequence value) {
        if (value == null) {return;}
        flipper3.changeTo(value);
    }
    public void setBox4(CharSequence value) {
        if (value == null) {return;}
        flipper4.changeTo(value);
    }
    public void setBox5(CharSequence value) {
        if (value == null) {return;}
        flipper5.changeTo(value);
    }

    public void createTypeBox(String name, int inputType, int maxCharacters, int position) {
        if (position == Values.vertical_level_1) {flipper1.createTypeBox(name, inputType, maxCharacters);}
        else if (position == Values.vertical_level_2) {flipper2.createTypeBox(name, inputType, maxCharacters);}
        else if (position == Values.vertical_level_3) {flipper3.createTypeBox(name, inputType, maxCharacters);}
        else if (position == Values.vertical_level_4) {flipper4.createTypeBox(name, inputType, maxCharacters);}
        else if (position == Values.vertical_level_5) {flipper5.createTypeBox(name, inputType, maxCharacters);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in type box "+name, Toast.LENGTH_SHORT).show();
        }
    }
    public void createTextDropdown(String name, ArrayList<String> array, int position) {
        if (position == Values.vertical_level_1) {flipper1.createTextDropdown(name, array);}
        else if (position == Values.vertical_level_2) {flipper2.createTextDropdown(name, array);}
        else if (position == Values.vertical_level_3) {flipper3.createTextDropdown(name, array);}
        else if (position == Values.vertical_level_4) {flipper4.createTextDropdown(name, array);}
        else if (position == Values.vertical_level_5) {flipper5.createTextDropdown(name, array);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in dropdown "+name, Toast.LENGTH_SHORT).show();}
    }

    public void createTeamNumberDropdown(String name, int position) {
        if (position == Values.vertical_level_1) {flipper1.createTeamNumberDropdown(name);}
        else if (position == Values.vertical_level_2) {flipper2.createTeamNumberDropdown(name);}
        else if (position == Values.vertical_level_3) {flipper3.createTeamNumberDropdown(name);}
        else if (position == Values.vertical_level_4) {flipper4.createTeamNumberDropdown(name);}
        else if (position == Values.vertical_level_5) {flipper5.createTeamNumberDropdown(name);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in dropdown "+name, Toast.LENGTH_SHORT).show();}
    }

    public void createToggle(String name, int position) {
        if (position == Values.vertical_level_1) {flipper1.createToggle(name);}
        else if (position == Values.vertical_level_2) {flipper2.createToggle(name);}
        else if (position == Values.vertical_level_3) {flipper3.createToggle(name);}
        else if (position == Values.vertical_level_4) {flipper4.createToggle(name);}
        else if (position == Values.vertical_level_5) {flipper5.createToggle(name);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in toggle "+name, Toast.LENGTH_SHORT).show();}
    }

    public void createCounter(String name, int maxValue, int position) {
        if (position == Values.vertical_level_1) {flipper1.createCounter(name, maxValue);}
        else if (position == Values.vertical_level_2) {flipper2.createCounter(name, maxValue);}
        else if (position == Values.vertical_level_3) {flipper3.createCounter(name, maxValue);}
        else if (position == Values.vertical_level_4) {flipper4.createCounter(name, maxValue);}
        else if (position == Values.vertical_level_5) {flipper5.createCounter(name, maxValue);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in counter "+name, Toast.LENGTH_SHORT).show();}
    }

    public void createStopwatch(String name, int position) {
        if (position == Values.vertical_level_1) {flipper1.createStopwatch(name);}
        else if (position == Values.vertical_level_2) {flipper2.createStopwatch(name);}
        else if (position == Values.vertical_level_3) {flipper3.createStopwatch(name);}
        else if (position == Values.vertical_level_4) {flipper4.createStopwatch(name);}
        else if (position == Values.vertical_level_5) {flipper5.createStopwatch(name);}
        else{
            Toast.makeText(this.getContext(), "ERROR: Invalid position in stopwatch "+name, Toast.LENGTH_SHORT).show();}
    }
}