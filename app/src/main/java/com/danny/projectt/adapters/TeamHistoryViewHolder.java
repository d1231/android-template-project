package com.danny.projectt.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.danny.projectt.R;
import com.danny.projectt.model.objects.Stats;
import com.danny.projectt.model.objects.TeamHistory;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TeamHistoryViewHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.team_history_start_year)
    TextView teamHistoryStartYear;

    @Bind(R.id.team_history_end_year)
    TextView teamHistoryEndYear;

    @Bind(R.id.team_history_name)
    TextView teamHistoryName;

    @Bind(R.id.team_history_apps)
    TextView teamHistoryApps;

    public TeamHistoryViewHolder(View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(TeamHistory teamHistory) {

        teamHistoryStartYear.setText(String.format("%d", teamHistory.startYear()));
        teamHistoryEndYear.setText(String.format("%d", teamHistory.endYear()));

        teamHistoryName.setText(teamHistory.teamName());

        final Stats leagueStats = teamHistory.leagueStats();
        teamHistoryApps.setText(String.format("%d (%d)", leagueStats.apps(), leagueStats.goals()));
    }
}
