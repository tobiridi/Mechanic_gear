package be.jadoulle.mechanical_gear.Views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.R;

public class GearViewHolder extends RecyclerView.ViewHolder {
    private TextView gearDenomination;
    private ImageView gearRepresentation;

    public GearViewHolder(@NonNull View itemView) {
        super(itemView);
        this.gearDenomination = itemView.findViewById(R.id.tv_recycler_item);
        this.gearRepresentation = itemView.findViewById(R.id.iv_recycler_item);
        itemView.setOnClickListener((View v) -> {
            //TODO : go to details of the gear activity
            Toast.makeText(v.getContext(), "click on item", Toast.LENGTH_SHORT).show();
        });
    }

    public void updateData(GearWithAllObjects gearWithAllObjects) {
        this.gearDenomination.setText(gearWithAllObjects.getGear().getDenomination());
        if(!gearWithAllObjects.getRepresentations().isEmpty()) {
            byte[] picture = gearWithAllObjects.getRepresentations().get(0).getPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0, picture.length);
            this.gearRepresentation.setImageBitmap(bitmap);
            this.gearRepresentation.setPaddingRelative(10,10,10,10);
        }
    }
}
