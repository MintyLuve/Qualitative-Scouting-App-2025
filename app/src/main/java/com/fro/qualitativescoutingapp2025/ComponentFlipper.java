package com.fro.qualitativescoutingapp2025;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class ComponentFlipper extends LinearLayout {

    // Instantiates the view flipper
    ViewFlipper flipper;
    // Text type box
    EditText typeBox;
    // Text Dropdown
    Spinner textDropdown;
    // Team Number Dropdown
    AutoCompleteTextView numberDropdown;
    // Toggle
    SwitchCompat toggle;
    // Counter
    ImageButton counterPlus;
    ImageButton counterMinus;
    TextView counterNumber;
    // Stopwatch
    TextView stopwatchTimer;
    TextView stopwatchRestart;
    TextView stopwatchStart;
    TextView stopwatchStop;
    String stopwatchName;
    int stopwatchSeconds, stopwatchMinutes, stopwatchElapsedTimeMillis = 0, stopwatchStartTimeMillis = 0, stopwatchSavedTimeMillis = 0;
    Looper stopwatchLooper = Looper.getMainLooper();
    Handler stopwatchHandler = new Handler(stopwatchLooper);

    private final Runnable stopwatchRunnable = new Runnable() {
        @Override
        public void run() {
            // Difference of what system time time we started the stopwatch and the current system time
            stopwatchElapsedTimeMillis = (int) SystemClock.uptimeMillis() - stopwatchStartTimeMillis;
            // Gets the previous time and adds it to the elapsed time (/1000 to turn into seconds)
            stopwatchSeconds = (int) ((stopwatchSavedTimeMillis + stopwatchElapsedTimeMillis)/1000);
            // Saves seconds in data
            Values.data.put(stopwatchName, stopwatchSeconds);
            // Gets minutes and seconds to print
            stopwatchMinutes = stopwatchSeconds / 60;
            stopwatchSeconds = stopwatchSeconds % 60;
            // Sets the text view to the time in 00:00 format
            String text = stopwatchMinutes + ":" + String.format(Locale.getDefault(), "%02d", stopwatchSeconds);
            stopwatchTimer.setText(text);
            // Uses recursion to run this function forever with no delay
            stopwatchHandler.postDelayed(this, 0);
        }
    };

    public ComponentFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_flipper, this);
        flipper = (ViewFlipper) findViewById(R.id.flipper);
    }

    public void changeTo(CharSequence value) {
        if (value.equals("0")) {while (flipper.getCurrentView() != findViewById(R.id.typeBox)) {flipper.showNext();}}
        if (value.equals("1")) {while (flipper.getCurrentView() != findViewById(R.id.textDropdown)) {flipper.showNext();}}
        if (value.equals("2")) {while (flipper.getCurrentView() != findViewById(R.id.numberDropdown)) {flipper.showNext();}}
        if (value.equals("3")) {while (flipper.getCurrentView() != findViewById(R.id.toggleLayout)) {flipper.showNext();}}
        if (value.equals("4")) {while (flipper.getCurrentView() != findViewById(R.id.counter)) {flipper.showNext();}}
        if (value.equals("5")) {while (flipper.getCurrentView() != findViewById(R.id.stopwatch)) {flipper.showNext();}}
        if (value.equals("6")) {flipper.setVisibility(INVISIBLE);}
    }

    public void setPadding(int horizontalChange, int verticalChange) {
        //Sets the padding to change per screen size
        Context con = this.getContext();
        flipper.post(new Runnable() {
            @Override
            public void run() {
                if (horizontalChange != 0 && verticalChange != 0) {
                    int height = flipper.getHeight();
                    flipper.setPadding(
                            (height / horizontalChange), // left
                            (height / verticalChange), // top
                            (height / horizontalChange),  // right
                            (height / verticalChange)   // bottom
                    );
                } else {
                    Toast.makeText(con, "Error divide by 0: " + String.valueOf(horizontalChange) + " or " + String.valueOf(verticalChange), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void createTypeBox(String name, int type, int maxCharacters) {
        //Creates the data in the hashmap
        if (!Values.data.containsKey(name)) {
            Values.data.put(name, "");
        }

        //Creates the type box
        typeBox = findViewById(R.id.type_box);

        // Sets the input type (number or text)
        if (type == Values.inputType_number) {
            typeBox.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            typeBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        }

        // Sets the max amount of characters
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxCharacters);
        typeBox.setFilters(filters);

        // Set inputted text
        if (Values.data.containsKey(name) && (CharSequence) Values.data.get(name) != null) {
            typeBox.setText((CharSequence) Values.data.get(name));
        }

        // Updates data when text is changed
        typeBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Values.data.put(name, typeBox.getText());
            }
        });
    }

    public void createTextDropdown(String name, ArrayList<String> array) {
        //Creates the data in the hashmap
        if (!Values.data.containsKey(name)) {
            Values.data.put(name, "");
        }

        // Adds 'dropdown' to the beginning of the list to be a default value
        array.add(0, "Dropdown");

        // Creates the dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        textDropdown = findViewById(R.id.text_dropdown);
        textDropdown.setAdapter(adapter);

        // Sets the dropdown to be below the spinner border
        textDropdown.post(new Runnable() {
            @Override
            public void run() {
                textDropdown.setDropDownVerticalOffset(5);
            }
        });

        // Set text dropdown selection
        if (Values.data.containsKey(name) && Values.data.get(name) != null) {
            int index = 0;
            for (String item: array){
                if (item.equals(Values.data.get(name))){
                    index = array.indexOf(item);
                }
            }
            textDropdown.setSelection(index);

//            textDropdown.setSelection((Integer) Values.data.get(name));
        }

        // Updates data when new item is selected in dropdown
        textDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                Values.data.put(name, pos);
                Values.data.put(name, textDropdown.getSelectedItem());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void createTeamNumberDropdown(String name) {
        //Creates the data in the hashmap
        if (!Values.data.containsKey(name)) {
            Values.data.put(name, "");
        }

        // Creates the dropdown
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this.getContext(), R.layout.spinner_dropdown, TeamNumbers.hatboro_horsham);
        numberDropdown = findViewById(R.id.number_dropdown);
        numberDropdown.setThreshold(1);
        numberDropdown.setAdapter(adapter);

        // Sets the dropdown to be below the spinner border
        numberDropdown.post(new Runnable() {
            @Override
            public void run() {
                numberDropdown.setDropDownVerticalOffset(5);
            }
        });

        // Set text to data value
        if (Values.data.containsKey(name)) {
            numberDropdown.setText(String.valueOf(Values.data.get(name)));
        }

        // Updates value when text is changed
        numberDropdown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String text = numberDropdown.getText().toString();
                if (!text.equals("")) {
                    Values.data.put(name, Integer.parseInt(numberDropdown.getText().toString()));
                }
            }
        });
    }

    public void createToggle(String name) {
        // Creates the data in the hashmap
        if (!Values.data.containsKey(name)) {
            Values.data.put(name, false);
        }

        // Creates the toggle
        toggle = findViewById(R.id.toggle);
        LinearLayout toggleLayout = findViewById(R.id.toggleLayout);

        // Sets the track and thumb drawables
        toggle.setTrackResource(R.drawable.toggle_track);
        toggle.setThumbResource(R.drawable.toggle_thumb);

        // Scales the toggle based on the height
        // TODO Fix this scaling later
        toggleLayout.post(new Runnable() {
            @Override
            public void run() {
                float scale = (float) toggleLayout.getHeight() / 200;
                toggle.setScaleX(scale);
                toggle.setScaleY(scale);
            }
        });

        // Set if checked or not
        if (Values.data.containsKey(name) && (Boolean) Values.data.get(name) != null) {
            toggle.setChecked((Boolean) Values.data.get(name));
        }

        // Updates value when checked is changed
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> Values.data.put(name, isChecked));
    }

    public void createCounter(String name, int maxValue) {
        // Creates the data in the hashmap
        if (!Values.data.containsKey(name)) {
            Values.data.put(name, 0);
        }

        // Creates the buttons and text view
        counterMinus = findViewById(R.id.counter_minus);
        counterPlus = findViewById(R.id.counter_plus);
        counterNumber = findViewById(R.id.counter_number);

        // Set text to data value
        if (Values.data.containsKey(name)) {
            counterNumber.setText(String.valueOf(Values.data.get(name)));
        }

        // Updates value when plus is hit
        counterPlus.setOnClickListener(v -> {
            //gets the num from the view
            int num = Integer.parseInt(counterNumber.getText().toString());
            //sets the view to the num + 1
            if (num < maxValue) {
                num++;
                counterNumber.setText(String.valueOf(num));
            }
            Values.data.put(name, num);
        });

        // Updates value when minus is hit
        counterMinus.setOnClickListener(v -> {
            //gets the number from the view
            int num = Integer.parseInt(counterNumber.getText().toString());
            //sets the view to the number + 1
            if (num > 0) {
                num--;
                counterNumber.setText(String.valueOf(num));
            }
            Values.data.put(name, num);
        });
    }

    public void createStopwatch(String name) {
        // Creates the data in the hashmap
        if (!Values.data.containsKey(name)) {Values.data.put(name, 0);}

        // Sets this file's stopwatch name to be the name so the runnable can use it
        stopwatchName = name;

        // Creates the buttons and text view
        stopwatchTimer = findViewById(R.id.stopwatch_timer);
        stopwatchRestart = findViewById(R.id.stopwatch_restart);
        stopwatchStart = findViewById(R.id.stopwatch_start);
        stopwatchStop = findViewById(R.id.stopwatch_stop);

        // Set text to data value
        if (Values.data.containsKey(name)) {
            stopwatchSeconds = Integer.parseInt(Objects.requireNonNull(Values.data.get(name)).toString());
            stopwatchMinutes = stopwatchSeconds / 60;
            stopwatchSeconds = stopwatchSeconds % 60;
            String text = (stopwatchMinutes + ":" + String.format(Locale.getDefault(), "%02d", stopwatchSeconds));
            stopwatchTimer.setText(text);
        }

        stopwatchStart.setOnClickListener(v -> {
            // Gets the start system time
            stopwatchStartTimeMillis = (int) SystemClock.uptimeMillis();
            // Sets the saved time to the time saved in data
            stopwatchSavedTimeMillis = (int) Values.data.get(stopwatchName)*1000;
            // Starts the stopwatch
            stopwatchHandler.postDelayed(stopwatchRunnable, 0);
            // Disables specific buttons
            stopwatchRestart.setClickable(false);
            stopwatchRestart.setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.box_outline_tinted, getContext().getTheme()));
            stopwatchStop.setClickable(true);
            stopwatchStop.setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.box_outline, getContext().getTheme()));
            stopwatchStart.setClickable(false);
            stopwatchStart.setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.box_outline_tinted, getContext().getTheme()));
        });

        stopwatchStop.setOnClickListener(v -> {
            // Saves the current time
            stopwatchSavedTimeMillis += stopwatchElapsedTimeMillis;
            // Pauses the stopwatch
            stopwatchHandler.removeCallbacks(stopwatchRunnable);
            // Disables specific buttons
            stopwatchRestart.setClickable(true);
            stopwatchRestart.setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.box_outline, getContext().getTheme()));
            stopwatchStop.setClickable(false);
            stopwatchStop.setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.box_outline_tinted, getContext().getTheme()));
            stopwatchStart.setClickable(true);
            stopwatchStart.setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.box_outline, getContext().getTheme()));
        });

        stopwatchRestart.setOnClickListener(v ->{
            // Sets all values to 0 to restart timer
            stopwatchElapsedTimeMillis = 0;
            stopwatchStartTimeMillis = 0;
            stopwatchSavedTimeMillis = 0;
            stopwatchSeconds = 0;
            stopwatchMinutes = 0;
            Values.data.put(name, 0);
            stopwatchTimer.setText("0:00");
        });
    }
}