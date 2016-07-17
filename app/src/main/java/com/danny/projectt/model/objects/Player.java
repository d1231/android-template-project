package com.danny.projectt.model.objects;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Player implements Parcelable {

    public static TypeAdapter<Player> typeAdapter(Gson gson) {

        return new AutoValue_Player.GsonTypeAdapter(gson);
    }

    public abstract String name();

    public abstract String position();

    public abstract String placeOfBirth();

    public abstract String dateOfBirth();

    @SerializedName("teams")
    public abstract List<TeamHistory> teamHistory();

}
