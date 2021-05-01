package ru.sfedu.diplomaapp.utils.otherUtils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import ru.sfedu.diplomaapp.R;
import ru.sfedu.diplomaapp.models.Task;

public class TaskBindingUtils {
    @BindingAdapter("taskImage")
    public static void setImpImage(ImageView imageView, Task task) {
        int resImp;
        switch ((int) task.getStatus()) {
            case 0: resImp = R.drawable.ic_tasks_create; break;
            case 1: resImp = R.drawable.ic_tasks_resume; break;
            case 2: resImp = R.drawable.ic_tasks_finish; break;
            default: resImp = R.drawable.ic_tasks;
        }
        imageView.setImageResource(resImp);
    }
}
