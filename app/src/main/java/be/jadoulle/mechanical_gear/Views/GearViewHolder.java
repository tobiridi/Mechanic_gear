package be.jadoulle.mechanical_gear.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.jadoulle.mechanical_gear.DetailsGearActivity;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.MainActivity;
import be.jadoulle.mechanical_gear.R;
import be.jadoulle.mechanical_gear.Utils.ActivityCode;

public class GearViewHolder extends RecyclerView.ViewHolder {
    private TextView gearDenomination;
    private ImageView gearRepresentation;
    private GearWithAllObjects selectedGear;

    public GearViewHolder(@NonNull View itemView) {
        super(itemView);
        this.gearDenomination = itemView.findViewById(R.id.tv_recycler_item);
        this.gearRepresentation = itemView.findViewById(R.id.iv_recycler_item);
        itemView.setOnClickListener((View v) -> {
            Context context = v.getContext();
            if (context instanceof MainActivity) {
                Intent intent = new Intent(context, DetailsGearActivity.class);
                intent.putExtra("selectedGear", this.selectedGear);
                //TODO : optimise
                ((MainActivity) context).startActivityForResult(intent, ActivityCode.MAIN_ACTIVITY_CODE);
            }
        });
    }

    public void updateData(GearWithAllObjects gearWithAllObjects) {
        this.gearDenomination.setText(gearWithAllObjects.getGear().getDenomination());
        if(!gearWithAllObjects.getRepresentations().isEmpty()) {
            byte[] picture = gearWithAllObjects.getRepresentations().get(0).getPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0, picture.length);
            this.gearRepresentation.setImageBitmap(bitmap);
        }

        //bind a "GearWithAllObjects" who will be send to details activity
        this.selectedGear = gearWithAllObjects;
    }

}
