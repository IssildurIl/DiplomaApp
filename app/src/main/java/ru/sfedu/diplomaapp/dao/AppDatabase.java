package ru.sfedu.diplomaapp.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.sfedu.diplomaapp.models.Developer;
import ru.sfedu.diplomaapp.models.DevelopersTask;
import ru.sfedu.diplomaapp.models.Employee;
import ru.sfedu.diplomaapp.models.Project;
import ru.sfedu.diplomaapp.models.Task;
import ru.sfedu.diplomaapp.models.Tester;
import ru.sfedu.diplomaapp.models.TestersTask;

@Database(entities = {Employee.class, Developer.class, Tester.class, Task.class, DevelopersTask.class,
        TestersTask.class, Project.class}, version = 7,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmployeeDao employeeDao();
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();
    private static final String DB_NAME = "data.db";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 20;
    static public final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static public AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static AppDatabase create(Context context, boolean memoryOnly) {
        Builder<AppDatabase> builder;
        if (memoryOnly) {
            builder = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                    AppDatabase.class);
        } else {
            builder = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    DB_NAME)
                    .fallbackToDestructiveMigration();
        }
        return (builder.build());
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                ProjectDao dao = INSTANCE.projectDao();
                dao.deleteAll();

                Project project;
                for (int i = 0; i < 30; i++) {
                    project = new Project("Project " + i);
                    dao.insertProject(project);
                }
            });
        }
    };

}
