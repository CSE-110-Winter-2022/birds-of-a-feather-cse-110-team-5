package com.example.birdsoffeather_team5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MockMessageListener extends MessageListener {
    private static final String MOCKMESSAGELISTENER = "MockMessageListener";
    private final MessageListener messageListener;
    private Context context;
    private AlertDialog.Builder builder;
    private String userInput = "";

    public MockMessageListener(MessageListener realMessageListener, Context context) {
        this.messageListener = realMessageListener;
        this.context = context;
    }

    @Override
    public void onFound(@NonNull Message message) {
        super.onFound(message);
        if(new String(message.getContent()).equals("mock")) {
            createBuilder(this.context);
        } else {
            Log.d(MOCKMESSAGELISTENER + " onFound called", new String(message.getContent()));
            this.messageListener.onFound(message);
        }
    }

    @Override
    public void onLost(@NonNull Message message) {
        Log.d(MOCKMESSAGELISTENER + " onLost called", new String(message.getContent()));
        this.messageListener.onLost(message);
    }

    public void createBuilder(Context place) {
        builder = new AlertDialog.Builder(place);
        final EditText input = new EditText(place);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Save button onClick called", input.getText().toString());
                userInput = input.getText().toString();
                callOnFound();
                createBuilder(place);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(MOCKMESSAGELISTENER, "cancel button clicked");
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void callOnFound() {
        Scanner sc = new Scanner(userInput);
        Gson gson = new Gson();

        while(sc.hasNext()) {
            Log.i("Reading Data", "next exists");
            Student stu = extractSingleUser(sc);
            Log.i("Reading Data", "extracted data");

            String messageStr = gson.toJson(stu);
            Message message = new Message(messageStr.getBytes(StandardCharsets.UTF_8));
            Log.d("MessageListener onFound called", new String(message.getContent()));
            this.messageListener.onFound(message);
        }
    }

    public Student extractSingleUser(Scanner sc) {

        Log.d("extractSingleUser called", "getting student");
        //get name
        String name = sc.nextLine(); name = name.substring(0, name.length() - 4);
        //get url
        String url = sc.nextLine(); url = url.substring(0, url.length() - 4);

        //get classes
        List<ClassData> classes = new ArrayList<>();
        while(sc.hasNextLine()) {
            String currClass = sc.nextLine();
            String[] currClassSplit = currClass.split(",");
            ClassData c = new BOFClassData(
                    Integer.parseInt(currClassSplit[0]),
                    currClassSplit[1],
                    currClassSplit[2],
                    currClassSplit[3],
                    currClassSplit[4]);
            classes.add(c);
        }
        Log.d("Checking classes size in extractSingleUser", String.valueOf(classes.size()));
        Student stu = new BOFStudent(name, url, classes);
        Log.i("Mock", "name: " + stu.getName() + " url: " + stu.getURL());
        Log.i("Mock", stu.getClassData().toString());
        return stu;
    }
}
