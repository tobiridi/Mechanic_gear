package be.jadoulle.mechanical_gear.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.jadoulle.mechanical_gear.DetailsGearActivity;
import be.jadoulle.mechanical_gear.Entities.DataClasses.GearWithAllObjects;
import be.jadoulle.mechanical_gear.MainActivity;
import be.jadoulle.mechanical_gear.R;
import be.jadoulle.mechanical_gear.Utils.ActivityCode;
import be.jadoulle.mechanical_gear.Utils.Utils;

public class GearViewHolder extends RecyclerView.ViewHolder {
    private TextView gearDenomination;
    private ImageView gearRepresentation;
    private ProgressBar progressBar;
    private GearWithAllObjects selectedGear;
    private double progressState = 0.0;

    public GearViewHolder(@NonNull View itemView) {
        super(itemView);
        this.gearDenomination = itemView.findViewById(R.id.tv_recycler_item);
        this.gearRepresentation = itemView.findViewById(R.id.iv_recycler_item);
        this.progressBar = itemView.findViewById(R.id.pb_item);

        this.itemView.setOnClickListener((View v) -> {
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

    public void updateProgressBar() {
        int nbrRep = this.selectedGear.getRepresentations().size();
        int nbrSignal = this.selectedGear.getSignalTypes().size();
        double progressRatio = (double) this.progressBar.getMax() / (nbrRep + nbrSignal);

        this.progressState += progressRatio;

        if (this.progressState >= this.progressBar.getMax()) {
            this.gearRepresentation.setVisibility(View.VISIBLE);
            this.gearDenomination.setVisibility(View.VISIBLE);
            this.progressBar.setVisibility(View.GONE);
            this.itemView.setClickable(true);
            Utils.showToast(this.itemView.getContext(), this.itemView.getContext().getResources().getString(R.string.gear_list_update_message), Toast.LENGTH_SHORT);
        }
        else {
            this.gearRepresentation.setVisibility(View.GONE);
            this.gearDenomination.setVisibility(View.GONE);
            this.progressBar.setVisibility(View.VISIBLE);
            this.progressBar.setProgress((int) this.progressState);
            this.itemView.setClickable(false);
        }
    }

}
