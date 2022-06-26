package com.example.mechanic_gear.asynchronous_task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.example.mechanic_gear.AddGearActivity;
import com.example.mechanic_gear.java_beans.Gear;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SaveNewGearTaskAsync extends AsyncTask<Gear, Void, Gear> {

    private final Activity activity;

    public SaveNewGearTaskAsync(AddGearActivity addGearActivity){
        this.activity = addGearActivity;
    }

    @Override
    protected Gear doInBackground(Gear...gears) {
        String pathFile = this.activity.getFilesDir().getPath() + File.separator + "save.json";
        FileReader reader = null;
        FileWriter writer = null;
        File saveFile = new File(pathFile);
        Gson gson = new Gson();     //dependency (gradle)

        //serialize the data to a json
        String json = gson.toJson(gears[0]);

        //verify if the file exist
        if(saveFile.exists()){
            try {
                //write a new line between []
                String newLine = json + "]";

                // TODO : non fonctionnel
                Scanner scanner = new Scanner(saveFile).useDelimiter("]");
                while (scanner.hasNext()){
                    System.out.println("scanner : " + scanner.next());
                }
                scanner.close();

                writer = new FileWriter(saveFile,true);
                writer.write(json + "]");
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        else {
            //create file + create array + insert the first value in the array
            try {
                // TODO : can be optimized (indentation)
                writer = new FileWriter(pathFile);
                writer.write("[" + json + "\n]");
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


//        try {
//            reader = new FileReader(pathFile);
//            //insert the new value
//            if(reader.ready()){
//                /*
//                 * [ => 91
//                 * ] => 93
//                 * { => 123
//                 * } => 125
//                 */
//                System.out.println((char)reader.read());
//            }
//            reader.close();
//
//            //writer = new FileWriter(this.activity.getFilesDir() + File.separator + "save.json");
//            //writer.close();

        /**
         * ✔ verif si le fichier existe, sinon le créer et mettre des []
         * ✔ verif que l'architecture soit en tableau []
         * ✔ insérer une valeur
         * s'il y a une valeur alors mettre une "," après les {}
         */

        //create or write a file, can raise an exception if the file is not found
        //Ouvrez un fichier privé associé au package d'application de ce contexte pour l'écriture. Crée le fichier s'il n'existe pas déjà.
        //Aucune autorisation supplémentaire n'est requise pour que l'application appelante puisse lire ou écrire le fichier renvoyé.
//        try {
//            /*
//            mode list :
//            MODE_APPEND => if file already exists, write the data to the end or create file and write the data
//            MODE_PRIVATE => file creation mode, can only be accessed by the calling application (or all applications sharing the same user ID).
//             */
//
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(json.getBytes());
//            fos.close();
//
//            FileInputStream fis2 = new FileInputStream(this.activity.getFilesDir() + "/save.json");
//

    }

    @Override
    protected void onPostExecute(Gear gear) {
        super.onPostExecute(gear);
        //this.addGearActivity;
    }
}
