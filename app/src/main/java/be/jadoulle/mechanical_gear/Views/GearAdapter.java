package be.jadoulle.mechanical_gear.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.R;

public class GearAdapter extends RecyclerView.Adapter<GearViewHolder> {
    private List<GearWithAllObjects> allGears;

    public GearAdapter(List<GearWithAllObjects> allGears) {
        this.allGears = allGears;
    }

    @NonNull
    @Override
    public GearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent,false);
        return new GearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GearViewHolder holder, int position) {
        holder.updateData(this.allGears.get(position));
    }

    @Override
    public int getItemCount() {
        return this.allGears.size();
    }
}
