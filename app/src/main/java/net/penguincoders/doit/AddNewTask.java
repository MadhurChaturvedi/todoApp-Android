//package net.penguincoders.doit;
//
//import android.app.Activity;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//
//import net.penguincoders.doit.Adapters.ToDoAdapter;
//import net.penguincoders.doit.Model.ToDoModel;
//import net.penguincoders.doit.Utils.DatabaseHandler;
//
//import java.util.Objects;
//
//
//public class AddNewTask extends BottomSheetDialogFragment {
//
//    public static final String TAG = "ActionBottomDialog";
//    private EditText newTaskText;
//    private Button newTaskSaveButton;
//
//    private DatabaseHandler db;
//
//    public static AddNewTask newInstance(){
//        return new AddNewTask();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(STYLE_NORMAL, R.style.DialogStyle);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.new_task, container, false);
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        newTaskText = Objects.requireNonNull(getView()).findViewById(R.id.newTaskText);
//        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);
//
//        boolean isUpdate = false;
//
//        final Bundle bundle = getArguments();
//        if(bundle != null){
//            isUpdate = true;
//            String task = bundle.getString("task");
//            newTaskText.setText(task);
//            assert task != null;
//            if(task.length()>0)
//                newTaskSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.btncolor));
//        }
//
//        db = new DatabaseHandler(getActivity());
//        db.openDatabase();
//
//        newTaskText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString().equals("")){
//                    newTaskSaveButton.setEnabled(false);
//                    newTaskSaveButton.setTextColor(Color.GRAY);
//                }
//                else{
//                    newTaskSaveButton.setEnabled(true);
//                    newTaskSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//        final boolean finalIsUpdate = isUpdate;
//        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = newTaskText.getText().toString();
//                if(finalIsUpdate){
//                    db.updateTask(bundle.getInt("id"), text);
//                }
//                else {
//                    ToDoModel task = new ToDoModel();
//                    task.setTask(text);
//                    task.setStatus(0);
//                    db.insertTask(task);
//                }
//                dismiss();
//            }
//        });
//    }
//
//    @Override
//    public void onDismiss(@NonNull DialogInterface dialog){
//        Activity activity = getActivity();
//        if(activity instanceof DialogCloseListener)
//            ((DialogCloseListener)activity).handleDialogClose(dialog);
//    }
//}
package net.penguincoders.doit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import net.penguincoders.doit.Adapters.ToDoAdapter;
import net.penguincoders.doit.Model.ToDoModel;
import net.penguincoders.doit.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskSaveButton;
    private ImageView ivMic; // Added for mic button

    private DatabaseHandler db;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1; // Request code for speech input

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = Objects.requireNonNull(getView()).findViewById(R.id.newTaskText);
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);
        ivMic = getView().findViewById(R.id.iv_mic); // Initialize mic button

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            assert task != null;
            if(task.length()>0)
                newTaskSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.btncolor));
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your task");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTaskText.getText().toString();
                if(finalIsUpdate){
                    db.updateTask(bundle.getInt("id"), text);
                }
                else {
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                    db.insertTask(task);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && !result.isEmpty()) {
                    String spokenText = result.get(0);
                    newTaskText.setText(spokenText); // Set recognized text to EditText
                }
            } else {
                Toast.makeText(getActivity(), "Speech recognition failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }
}
