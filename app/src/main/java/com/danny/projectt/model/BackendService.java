package com.danny.projectt.model;

import com.danny.projectt.model.objects.Player;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface BackendService {

    @GET("/players")
    Observable<List<Player>> getPlayer();
}
