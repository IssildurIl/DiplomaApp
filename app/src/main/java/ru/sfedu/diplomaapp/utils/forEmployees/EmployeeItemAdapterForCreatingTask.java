package ru.sfedu.diplomaapp.utils.forEmployees;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfedu.diplomaapp.databinding.RvListEmployeeForCreatingTaskBinding;
import ru.sfedu.diplomaapp.interfaces.EmployeeClickListener;
import ru.sfedu.diplomaapp.models.Employee;

public class EmployeeItemAdapterForCreatingTask extends ListAdapter<Employee, EmployeeItemAdapterForCreatingTask.ItemViewHolder> {

    private EmployeeClickListener employeeClickListener;

    public EmployeeItemAdapterForCreatingTask(@NonNull DiffUtil.ItemCallback<Employee> diffCallback,EmployeeClickListener employeeClickListener) {
        super(diffCallback);
        this.employeeClickListener = employeeClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (getCurrentList()!= null) {
            Employee employee = getItem(position);
            holder.bind(employee,employeeClickListener);
        }
        else {
            holder.binding.employeeName.setText("No tasks");
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        RvListEmployeeForCreatingTaskBinding binding;

        public ItemViewHolder(@NonNull RvListEmployeeForCreatingTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public static ItemViewHolder from(@NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            RvListEmployeeForCreatingTaskBinding binding = RvListEmployeeForCreatingTaskBinding.inflate(layoutInflater,parent,false);
            return new ItemViewHolder(binding);
        }

        public void bind(Employee employee, EmployeeClickListener employeeClickListener) {
            binding.setEmployee(employee);
            binding.setClicklistener(employeeClickListener);
            binding.executePendingBindings();
        }
    }
}
