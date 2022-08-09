package com.moutamid.chefdarbari;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.moutamid.chefdarbari.affiliate.AffiliateNavigationActivity;
import com.moutamid.chefdarbari.chef.ChefNavigationActivity;
import com.moutamid.chefdarbari.databinding.ActivityDetailsBinding;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        String type = getIntent().getStringExtra(Constants.PARAMS);

        if (type.equals(Constants.AFFILIATE)) {
            b.topText.setText("Affiliate Partner Information Form");
            b.affiliateCardView.setVisibility(View.VISIBLE);
            b.chefCardView.setVisibility(View.GONE);

        } else {
            b.topText.setText("Candidate Information Form");
            b.chefCardView.setVisibility(View.VISIBLE);
            b.affiliateCardView.setVisibility(View.GONE);

        }

        b.accountTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_account_type,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.savings) {
                            b.accountTypeTv.setText("Savings");
                        }
                        if (menuItem.getItemId() == R.id.current) {
                            b.accountTypeTv.setText("Current");

                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.cityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_cities,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.cityTv.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.languageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_languages,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.languageTv.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });


        b.ageTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_age,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.ageTv.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_education,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.education.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_post,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.post.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_experience,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.experience.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_vehicle,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.vehicle.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_physical,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.physical.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.workyear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_workyear,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.workyear1.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.workyear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DetailsActivity.this, v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_workyear,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.workyear2.setText(menuItem.getTitle().toString());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.submitBtnChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this, ChefNavigationActivity.class)
                        .putExtra(Constants.PARAMS, Constants.CHEF));
            }
        });


        b.submitBtn1.setOnClickListener(v ->
                startActivity(new Intent(DetailsActivity.this,
                        AffiliateNavigationActivity.class)
                        .putExtra(Constants.PARAMS, Constants.AFFILIATE)));

        Objects.requireNonNull(getSupportActionBar()).hide();

    }
}