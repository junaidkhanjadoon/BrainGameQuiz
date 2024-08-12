package com.example.brainbuzz.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.brainbuzz.model.HistoryModel;
import com.example.brainbuzz.model.OngoingQuizModel;
import com.example.brainbuzz.model.QuestionModel;
import com.example.brainbuzz.model.UsersModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "brain_buzz_app";

    // Columns for the Users table
    private static final String TABLE_USERS = "users_table";
    private static final String KEY_ID_USER = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BIO = "bio";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IMAGE_URI = "image_uri"; // New column for image URI

    // Columns for the History table
    private static final String TABLE_HISTORY = "history_table";
    private static final String KEY_ID_HISTORY = "history_id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_SCORES = "scores";
    private static final String KEY_DATE_TIME = "date_time";

    // Columns for the OngoingQuiz table
    private static final String TABLE_ONGOING_QUIZ = "ongoing_quiz_table";
    private static final String KEY_QUIZ_ID = "quiz_id";
    private static final String KEY_ONGOING_USER_ID = "user_id";
    private static final String KEY_SCORE = "score";
    private static final String KEY_ONGOING_CATEGORY = "on_category";
    private static final String KEY_CURRENT_QUESTION_INDEX = "current_question_index";
    private static final String KEY_REMAINING_QUESTIONS = "remaining_questions"; // Store as a JSON string


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT NOT NULL, " +
                KEY_USERNAME + " TEXT NOT NULL, " +
                KEY_EMAIL + " TEXT NOT NULL, " +
                KEY_BIO + " TEXT, " +
                KEY_PASSWORD + " TEXT NOT NULL, " +
                KEY_IMAGE_URI + " TEXT);";  // Include image URI column
        db.execSQL(CREATE_USERS_TABLE);

        // Create the History table
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + " (" +
                KEY_ID_HISTORY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_ID + " INTEGER NOT NULL, " +
                KEY_CATEGORY + " TEXT NOT NULL, " +
                KEY_SCORES + " TEXT, " +
                KEY_DATE_TIME + " TEXT, " +
                "FOREIGN KEY (" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID_USER + "));";
        db.execSQL(CREATE_HISTORY_TABLE);

        // Create the OngoingQuiz table
        String CREATE_ONGOING_QUIZ_TABLE = "CREATE TABLE " + TABLE_ONGOING_QUIZ + " (" +
                KEY_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ONGOING_USER_ID + " INTEGER NOT NULL, " +
                KEY_SCORE + " INTEGER, " +
                KEY_ONGOING_CATEGORY + " TEXT NOT NULL, " +
                KEY_CURRENT_QUESTION_INDEX + " INTEGER, " +
                KEY_REMAINING_QUESTIONS + " TEXT, " + // Store as JSON
                "FOREIGN KEY (" + KEY_ONGOING_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID_USER + "));";
        db.execSQL(CREATE_ONGOING_QUIZ_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ONGOING_QUIZ);
        // Recreate the tables
        onCreate(db);
    }

    // Register a new user
    public boolean register(UsersModel users) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isSuccess = false; // Default to false

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, users.getName());
        values.put(KEY_USERNAME, users.getUserName());
        values.put(KEY_EMAIL, users.getEmail());
        values.put(KEY_BIO, users.getBio());
        values.put(KEY_PASSWORD, users.getPassword());
        values.put(KEY_IMAGE_URI, users.getImg());  // Add image URI

        try {
            // Insert the user data into the database
            long result = db.insert(TABLE_USERS, null, values);

            // Check if the insertion was successful
            if (result != -1) {
                isSuccess = true; // Insertion succeeded
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }

        return isSuccess;
    }

    // Get the highest scores for each user
    public List<HistoryModel> getHighestScoresForUsers() {
        List<HistoryModel> highestScoresList = new ArrayList<>();

        String selectQuery = "SELECT h." + KEY_USER_ID + ", u." + KEY_NAME + ", u." + KEY_IMAGE_URI +
                ", MAX(h." + KEY_SCORES + ") AS max_score " +
                "FROM " + TABLE_HISTORY + " h " +
                "JOIN " + TABLE_USERS + " u ON h." + KEY_USER_ID + " = u." + KEY_ID_USER + " " +
                "GROUP BY h." + KEY_USER_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HistoryModel history = new HistoryModel();
                history.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_ID)));
                history.setScores(cursor.getString(cursor.getColumnIndexOrThrow("max_score")));

                // Assuming you want to set other fields for display purposes
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
                String userImageUri = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE_URI));

                // Create a new UsersModel object or similar to store the user details
                UsersModel user = new UsersModel();
                user.setName(userName);
                user.setImg(userImageUri);

                // You may need to modify the HistoryModel to store or process this data as needed
                // Example: add user details to a custom object or another model

                highestScoresList.add(history);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return highestScoresList;
    }

    // Get all users
    public List<UsersModel> getAllUsers() {
        List<UsersModel> usersList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UsersModel users = new UsersModel();
                users.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_USER)));
                users.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
                users.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERNAME)));
                users.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)));
                users.setBio(cursor.getString(cursor.getColumnIndexOrThrow(KEY_BIO)));
                users.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD)));
                users.setImg(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE_URI))); // Get image URI

                usersList.add(users);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return usersList;
    }
    // Method to get a single user's details by user ID
    public UsersModel getUserById(int userId) {
        UsersModel user = null;
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_ID_USER + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            user = new UsersModel();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_USER)));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
            user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_USERNAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)));
            user.setBio(cursor.getString(cursor.getColumnIndexOrThrow(KEY_BIO)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD)));
            user.setImg(cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE_URI))); // Get image URI
        }

        cursor.close();
        db.close();
        return user;
    }


    // Update user password
    public void updatePassword(int userId, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, newPassword);

        // Update the row where KEY_ID_USER matches the user ID
        db.update(TABLE_USERS, values, KEY_ID_USER + " = ?", new String[]{String.valueOf(userId)});

        db.close();
    }

    // Update user image URI
    public void updateUserImage(int userId, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE_URI, imageUri);

        // Update the row where KEY_ID_USER matches the user ID
        db.update(TABLE_USERS, values, KEY_ID_USER + " = ?", new String[]{String.valueOf(userId)});

        db.close();
    }

    // Insert history record
    public void insertHistory(HistoryModel history) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, history.getUserId());
        values.put(KEY_CATEGORY, history.getCategory());
        values.put(KEY_SCORES, history.getScores());
        values.put(KEY_DATE_TIME, history.getDateTime());

        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    // Get history records by user ID
    public List<HistoryModel> getHistoryByUserId(int userId) {
        List<HistoryModel> historyList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY + " WHERE " + KEY_USER_ID + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                HistoryModel history = new HistoryModel();
                history.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID_HISTORY)));
                history.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_USER_ID)));
                history.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CATEGORY)));
                history.setScores(cursor.getString(cursor.getColumnIndexOrThrow(KEY_SCORES)));
                history.setDateTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE_TIME)));

                historyList.add(history);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return historyList;
    }

    public boolean insertOngoingQuiz(OngoingQuizModel ongoingQuiz) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Gson gson = new Gson();
        String remainingQuestionsJson = gson.toJson(ongoingQuiz.getRemainingQuestions());

        values.put(KEY_ONGOING_USER_ID, ongoingQuiz.getUserId());
        values.put(KEY_SCORE, ongoingQuiz.getScore());
        values.put(KEY_ONGOING_CATEGORY, ongoingQuiz.getCategory());
        values.put(KEY_CURRENT_QUESTION_INDEX, ongoingQuiz.getCurrentQuestionIndex());
        values.put(KEY_REMAINING_QUESTIONS, remainingQuestionsJson);

        long result = db.insert(TABLE_ONGOING_QUIZ, null, values);
        db.close();
        return result != -1;
    }

    // Method to get a list of ongoing quizzes by user ID
    public List<OngoingQuizModel> getOngoingQuizByUserId(int userId) {
        List<OngoingQuizModel> ongoingQuizList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ONGOING_QUIZ, null, KEY_ONGOING_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OngoingQuizModel ongoingQuiz = new OngoingQuizModel();
                ongoingQuiz.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUIZ_ID)));
                ongoingQuiz.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ONGOING_USER_ID)));
                ongoingQuiz.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SCORE)));
                ongoingQuiz.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ONGOING_CATEGORY)));
                ongoingQuiz.setCurrentQuestionIndex(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CURRENT_QUESTION_INDEX)));

                Gson gson = new Gson();
                String remainingQuestionsJson = cursor.getString(cursor.getColumnIndexOrThrow(KEY_REMAINING_QUESTIONS));
                Type questionListType = new TypeToken<ArrayList<QuestionModel>>(){}.getType();
                ArrayList<QuestionModel> remainingQuestions = gson.fromJson(remainingQuestionsJson, questionListType);
                ongoingQuiz.setRemainingQuestions(remainingQuestions);

                ongoingQuizList.add(ongoingQuiz);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return ongoingQuizList;
    }


    // Method to update an ongoing quiz
    public boolean updateOngoingQuiz(OngoingQuizModel ongoingQuiz) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Gson gson = new Gson();
        String remainingQuestionsJson = gson.toJson(ongoingQuiz.getRemainingQuestions());

        values.put(KEY_SCORE, ongoingQuiz.getScore());
        values.put(KEY_ONGOING_CATEGORY, ongoingQuiz.getCategory());
        values.put(KEY_CURRENT_QUESTION_INDEX, ongoingQuiz.getCurrentQuestionIndex());
        values.put(KEY_REMAINING_QUESTIONS, remainingQuestionsJson);

        int result = db.update(TABLE_ONGOING_QUIZ, values, KEY_QUIZ_ID + "=?", new String[]{String.valueOf(ongoingQuiz.getId())});
        db.close();
        return result > 0;
    }

    // Method to delete an ongoing quiz
    public boolean deleteOngoingQuiz(int quizId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ONGOING_QUIZ, KEY_QUIZ_ID + "=?", new String[]{String.valueOf(quizId)});
        db.close();
        return result > 0;
    }

}
